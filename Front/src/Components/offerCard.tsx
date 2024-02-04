import React, { useEffect, useState } from 'react';
import {Avatar, Button, Card, Col, Divider, Row, Skeleton, Typography, message} from 'antd';
import {ArrowRightOutlined, StarFilled, UserOutlined} from "@ant-design/icons";

import '../styles/main.scss'
import TextArea from 'antd/es/input/TextArea';
import { useNavigate } from 'react-router';
import { Offer } from '../models/Offer';
import { acceptOffer, getOfferByUrl } from '../api/offerApi';
import { getUserByUrl } from '../api/userApi';
import { useTranslation } from 'react-i18next';

const { Title, Text } =Typography;

export type OfferCardProps = {
    from: string;
    to: string;
    dateFrom: Date;
    description: string;
    dateTo: Date;
    price: number;
    id: number;
    tripId: number;
    parentOffer: string;
    counterOffer: string;
    onCancel: (id: number) => void;
    tripCreator: string;
};



const OfferCard: React.FC<OfferCardProps> = ({ from,to,dateFrom,dateTo, price, id, onCancel, description, tripId, parentOffer, counterOffer, tripCreator}) => {
    const { t } = useTranslation(); // Initialize useTranslation hook
    const router = useNavigate();

    const [parentOfferObject, setParentOffer] = useState<Offer>();
    const [counterOfferObject, setCounterOffer] = useState<Offer>();
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [userImg, setUserImg] = useState<string>('');
    const [reviewScore, setReviewScore] = useState<number>(0);
    const [name, setName] = useState<string>('');
    const [actionEvent, setAction] = useState<number>(0);


    useEffect(() => {
        if(parentOffer){
            getOfferByUrl(parentOffer).then((offer) => {
                setParentOffer(offer);
                getUserByUrl(offer.userUrl).then((user) => {
                    setUserImg(user.imageUrl);
                    setReviewScore(user.rating);
                    setName(user.name);
                })
            }).catch(() => {
                message.error(t('offerCard.errorGettingOffer'));
            }).finally(() => {
                setIsLoading(false);
            })
        }

        if(counterOffer){
            getOfferByUrl(counterOffer).then((offer) => {
                setCounterOffer(offer);
            }).catch(() => {
                message.error(t('offerCard.errorGettingCounterOffer'));
                setIsLoading(false);
            }).finally(() => {
                setIsLoading(false);
            })
        }

        if(!parentOffer){
            getUserByUrl(tripCreator).then((user) => {
                setUserImg(user.imageUrl);
                setReviewScore(user.rating);
                setName(user.name);
            }).finally(() => {
                setIsLoading(false);
            })
        }

    }, [actionEvent]);

    async function acceptOfferAction(id: string, action: 'ACCEPT' | 'REJECT' ){
        setIsLoading(true);
        acceptOffer(id, action).then(() => {
            message.success(t('offerCard.offerAccepted'));
            if(action === 'ACCEPT')
                router('/trips/manage'+id);
            else
                setAction(actionEvent + 1);
        }).catch(() => {
            message.error(t('offerCard.errorAcceptingOffer'));
        }).finally (() =>{
            setIsLoading(false);
        })
    }

    return (
        <Card bordered className='mt-1vh' hoverable onClick={() => parentOffer ? router(`/trips/manage/${tripId}`) : router(`/trips/${tripId}`)}>
            <Skeleton loading={isLoading}>
                <div style={{display:"flex", alignItems:"center",justifyContent:"space-between",textAlign:'center',width:'70%',margin:'0 auto'}}>
                    <div style={{display:'block'}}>
                        <Title style={{marginTop:'0vh'}} level={5} className='offerTitle'>{from}</Title>
                        <p style={{margin:'0vh'}}>{(new Date(dateFrom.toString())).toDateString()}</p>
                    </div>
                    <ArrowRightOutlined style={{fontSize:'175%'}}></ArrowRightOutlined>
                    <div style={{display:'block'}}>
                        <Title style={{marginTop:'0vh'}} level={5} className='offerTitle'>{to}</Title>
                        <p style={{margin:'0vh'}}>{(new Date(dateTo.toString())).toDateString()}</p>
                    </div>
                    <Avatar size={64} src={userImg} icon={<UserOutlined/>}/>
                    <div style={{display:'block'}}>
                        <div style={{display:'flex',alignItems:'center'}}>
                            <StarFilled style={{margin:'0vh', marginRight:'1vh'}}/>
                            <Text>{reviewScore === 0 ? '-' : new Number(reviewScore).toFixed(1)}</Text>
                        </div>
                    </div>
                    <div style={{display:'block'}}>
                        <Title level={5} style={{margin:'0vh'}}>{name}</Title>
                        </div>
                </div>
                {parentOfferObject ?
                    <>
                    <Divider/>
                    <Row className='w-100 space-around mt-2vh'>
                        <Col span={4} className='text-center'>
                            <Title level={5}>{t('offerCard.originalOffer')}: </Title>
                        </Col>
                        <Col span={10}>
                            <TextArea value={parentOfferObject.description} disabled/>
                        </Col>
                        <Col span={2}></Col>
                        <Col span={4}>
                            <Title level={5} style={{margin:'0vh'}}>${parentOfferObject.price}</Title>
                        </Col>
                        <Col span={4}></Col>
                    </Row>              
                    </>
                    :
                    null
                }
                <Divider/>
                <Row className='w-100 space-around mt-2vh'>
                    <Col span={4} className='text-center'>
                        <Title level={5}>{t('offerCard.yourOffer')}: </Title>
                    </Col>
                    <Col span={10}>
                        <TextArea value={description} disabled/>
                    </Col>
                    <Col span={2}></Col>
                    <Col span={4}>
                        <Title level={5} style={{margin:'0vh'}}>${price}</Title>
                    </Col>
                    <Col span={4}>
                        {!counterOfferObject ?
                        <Button type='dashed' danger onClick={() => onCancel(id)}>{t('offerCard.cancel')}</Button>
                        :
                        null}
                    </Col>
                </Row>  
                {counterOfferObject ?
                    <>
                    <Divider/>
                    <Row className='w-100 space-around mt-2vh'>
                        <Col span={4} className='text-center'> 
                            <Title level={5}>{t('offerCard.counterOffer')}: </Title>
                        </Col>
                        <Col span={10}>
                            <TextArea value={counterOfferObject.description} disabled/>
                        </Col>
                        <Col span={2}></Col>
                        <Col span={4}>
                            <Title level={5} style={{margin:'0vh'}}>${counterOfferObject.price}</Title>
                        </Col>
                        <Col span={4} className='space-between'>
                            <Button type='dashed' danger onClick={() => acceptOfferAction(counterOfferObject.id.toString(), 'REJECT')}>Reject</Button>
                            <Button type='primary' onClick={() => acceptOffer(counterOfferObject.id.toString(), 'ACCEPT')}>Accept</Button>
                        </Col>
                        <Col span={4} className='space-between'>
                            <Button type='dashed' danger onClick={() => acceptOfferAction(counterOfferObject.id.toString(), 'REJECT')}>{t('offerCard.reject')}</Button>
                            <Button type='primary' onClick={() => acceptOfferAction(counterOfferObject.id.toString(), 'ACCEPT')}>{t('offerCard.accept')}</Button>
                        </Col>
                    </Row>              
                    </>
                    :
                    null
                }
            </Skeleton>
        </Card>
    );
}
export default OfferCard;