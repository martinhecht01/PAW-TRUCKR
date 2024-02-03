import React, { useEffect, useState } from 'react';
import {Typography, Row, Col, Card, Input, Button, Avatar, InputNumber, Badge, Slider, message, Skeleton} from 'antd';
import '../styles/main.scss';
import {useTranslation} from "react-i18next";
import { useNavigate, useSearchParams } from 'react-router-dom';
import { User } from '../models/User';
import { Publication } from '../models/Publication';
import { getPublicationById } from '../api/tripApi';
import { getClaims, getUserByUrl } from '../api/userApi';
import { createCounterOffer, getOffer } from '../api/offerApi';
import { Offer } from '../models/Offer';
import { ArrowRightOutlined, StarFilled, UserOutlined } from '@ant-design/icons';

export type OriginalOfferProps = {
    description: string,
    offeredPrice: number,
    userPhoto: string,
    userName: string
}

const { Title, Text } =Typography;


const { TextArea } = Input;
const SendCounterOffer: React.FC = () => {

    const [searchParams] = useSearchParams();

    const [publication, setPublication] = useState<Publication>();
    const [user, setUser] = useState<User>();
    const [offer, setOffer] = useState<Offer>();
    const [isLoading, setIsLoading] = useState<boolean>(true);

    const [inputValue, setInputValue] = useState(1);
    const [description, setDescription] = useState<string>('');

    const {t} = useTranslation();
    const router = useNavigate();

    useEffect(() => {
        const tripId = searchParams.get('tripId');
        const offerId = searchParams.get('offerId');        

        if(!tripId || !offerId){
            router('/404');
        }

        getPublicationById(tripId!).then((publication) => {
            setPublication(publication);
            getUserByUrl(publication.creator).then((user) => {
                setUser(user);
                getOffer(Number.parseInt(offerId!)).then((offer) => {
                    setOffer(offer);
                    setInputValue(offer.price);
                    setIsLoading(false);
                })
            })
        })

    }, [])

    const onChange = (newValue: number | null) => {
        setInputValue(newValue ? newValue : 0);
    };

    async function sendOffer(){
        const claims = getClaims();

        if(claims == null){
            router('/login')
        }

        createCounterOffer(Number.parseInt(searchParams.get('tripId')!), inputValue, description, Number.parseInt(searchParams.get('offerId')!)).then(() => {
            message.success('Offer sent successfully')
            router('/sentOffers')
        })
    }

    //TODO: get offer data from backend
    return (
        <div>
            <Row className='space-around'>
                <Skeleton loading={isLoading}>
                    <Col span={8}>
                        <div>
                            <Badge.Ribbon text={<Title level={5} style={{color: 'white', margin: 3}}>{publication?.type}</Title>}  color="blue">
                                <img
                                src={publication?.image}
                                style={{width: '100%', height: '100%', objectFit: 'cover', overflow: 'hidden'}}
                                />
                            </Badge.Ribbon>
                        </div>
                        <Card className="mt-5">
                            <Row className="space-evenly">
                                <Col span={8} className="text-center">
                                    <Title level={4}>{publication?.origin}</Title>
                                    <Text>{publication?.departureDate ? new Date(publication.departureDate).toDateString() : ''}</Text>
                                </Col>
                                <Col span={8} className="flex-center flex-column">
                                    <div className="w-100 flex-center">
                                        <div style={{border: '1px solid black', width: '100%'}}/><ArrowRightOutlined/>
                                    </div>                            
                                </Col>
                                <Col span={8}  className="text-center">
                                    <Title level={4}>{publication?.destination}</Title>
                                    <Text>{publication?.arrivalDate ? new Date(publication.arrivalDate).toDateString() : ''}</Text>
                                </Col>
                            </Row>
                        </Card>
                        <Row className="mt-5 space-between">
                            <Col span={11}>
                                <Card className="w-100 text-center">
                                    <Title level={3}>{publication?.weight} Kg</Title>
                                    <Text>Weight</Text>
                                </Card>
                            </Col>
                            <Col span={11}>
                                <Card className="w-100 text-center">
                                    <Title level={3}>{publication?.volume} M3</Title>
                                    <Text>Volume</Text>
                                </Card>
                            </Col>
                        </Row>
                    </Col>
                    <Col span={8}>
                        <Card hoverable onClick={() => router(`/profile/${user?.id}`)}>
                            <Row className='space-around aling-center'>
                                <Col span={8} className='flex-center'>
                                    <Avatar size='large' src={user?.imageUrl} icon={<UserOutlined/>}></Avatar>
                                </Col>
                                <Col span={8} className='flex-center'>
                                    <Title level={4}>{user?.name}</Title>
                                </Col>
                                <Col span={8} className='flex-center'>
                                    <Title level={4}><StarFilled/> {user?.rating == 0 ? '-' : Number(user?.rating ? user?.rating : 0).toFixed(1)}</Title>
                                </Col>
                            </Row>

                            <div>
                                <Title level={5}>{t('manage.description')}</Title>
                                <TextArea value={offer?.description} disabled/>
                            </div>
                            <div className='mt-2vh'>
                                <Title level={5}>{t('common.offeredPrice')}</Title>
                                <Title level={5}>${offer?.price}</Title>
                            </div>
                        </Card>
                        <Card title="Counter-offer" className='mt-2vh'>
                            <Title className='m-0' level={5}>{t('manage.description')}</Title>
                            <TextArea rows={4} className="w-100 mt-5" onChange={(value) => setDescription(value.target.value)}></TextArea>
                            <Row className="mt-5">
                                <Col span={16}>
                                    <Slider
                                    min={1}
                                    max={100000}
                                    onChange={onChange}
                                    tooltipVisible={false}
                                    value={typeof inputValue === 'number' ? inputValue : 0}
                                    />
                                </Col>
                                <Col span={4}>
                                    <InputNumber
                                    min={1}
                                    max={100000}
                                    style={{ margin: '0 16px' }}
                                    value={inputValue}
                                    prefix={'$'}
                                    onChange={onChange}
                                    />
                                </Col>
                            </Row>
                            <Button className='mt-2vh' type="primary" onClick={sendOffer}>Send Offer</Button>
                        </Card>
                    </Col>
                </Skeleton>
            </Row>
        </div>
    );
}

export default SendCounterOffer;