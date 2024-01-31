import React, { useEffect, useState } from 'react';
import {Card, Col, Divider, Image, Row, Typography, Avatar, Skeleton, Badge} from 'antd';
import '../styles/main.scss';
import '../styles/profile.scss';
import {useTranslation} from "react-i18next";
import ProposalCard from "../Components/proposalCard.tsx";
import {ArrowRightOutlined, StarFilled} from "@ant-design/icons";
import { Trip } from '../models/Trip.tsx';
import { User } from '../models/User.tsx';
import { useNavigate, useParams } from 'react-router-dom';
import { getTripById } from '../api/tripApi.tsx';
import { Offer } from '../models/Offer.tsx';
import { getOffersByTrip } from '../api/offerApi.tsx';
import { getClaims, getUserByUrl } from '../api/userApi.tsx';


const { Title, Text } = Typography;

export type ProposalProps = {
    description: string,
    offeredPrice: number,
    userPhoto: string,
    userName: string,
    userMail?: string
}

const ManageTrip: React.FC = () => {

    const {t} = useTranslation();
    const router = useNavigate();

    const {tripId} = useParams();

    let offerCount = 1;
    //TODO: get offer data from backend

    const acceptedOffer : ProposalProps | null = null;
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [publication, setPublication] = useState<Trip>();
    const [offers, setOffers] = useState<Offer[]>([]);
    const [user, setUser] = useState<User>();

    useEffect(()=>{

        const claims = getClaims();

        if(tripId == null){
            router('/404')
        }

        getTripById(tripId!).catch((err) => {router('/404')}).then((trip) => {
            setPublication(trip!);
            getOffersByTrip(tripId!).then((offers) => {
                setOffers(offers);
                setIsLoading(false);
            })
        })
        
    }, [])

    return (
        <div className="w-100 flex-center">
        <Row style={{justifyContent: 'space-evenly'}} className="w-80">
            <Skeleton loading={isLoading}>
                <Col span={10}>
                    <div>
                        <Badge.Ribbon text={<Title level={5} style={{color: 'white', margin: 3}}>{publication?.type}</Title>}  color="blue">
                                    <img
                                    src={publication?.image}
                                    style={{width: '100%', height: '100%', objectFit: 'cover', overflow: 'hidden'}}
                                    alt="A giant squid swimming deep in the sea"
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
                                <div className="w-100 flex-center"><Image height={45} preview={false} src="https://i.pinimg.com/originals/e0/8b/14/e08b1415885d4d2ddd7fd3f75967da29.png"></Image></div>
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
                    <Card>
                        <div className="w-100 space-between">
                            <Avatar size={64} src={user?.imageUrl}></Avatar>
                            <Title level={3}>{user?.name}</Title>
                            <div>
                                <Title level={4}><StarFilled/>{user?.rating == 0 ? '-' : user?.rating }</Title>
                            </div>
                        </div>
                    </Card>
                    
                </Col>
            </Skeleton>
        </Row>
    </div>

    );
};

export default ManageTrip;


/*

        <div >
            <div className="flex-center" style={{alignItems:'flex-start', margin: '3vh'}}>
                <Card style={{width: '30%', marginRight:'1vh'}} headStyle={{fontSize: '2.5vh', color: '#142d4c'}}
                      title={t("manage.manageTrip")}>
                    <div className='flex-center'>
                        <Image
                            src="https://pm1.aminoapps.com/6535/8e5958d3b7755e9429e476c408a06e5387358b0f_00.jpg"></Image>
                    </div>
                    <Row gutter={16} className='mt-2vh'>
                        <Col span={12}>
                            <Title className="m-1vh" level={5}>{t("common.cargoType")}</Title>
                        </Col>
                        <Col span={12}>
                            <p className="m-1vh">dfsajk</p>
                        </Col>
                    </Row>
                    <Divider className='m-0'/>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Title className="m-1vh" level={5}>{t('manage.origDest')}</Title>
                        </Col>
                        <Col span={12}>
                            <p className="m-1vh">dfsajk</p>
                        </Col>
                    </Row>
                    <Divider className='m-0'/>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Title className="m-1vh" level={5}>{t('common.licensePlate')}</Title>
                        </Col>
                        <Col span={12}>
                            <p className="m-1vh">dfsajk</p>
                        </Col>
                    </Row>
                    <Divider className='m-0'/>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Title className="m-1vh" level={5}>{t('manage.depArr')}</Title>
                        </Col>
                        <Col span={12}>
                            <p className="m-1vh">dfsajk</p>
                        </Col>
                    </Row>
                    <Divider className='m-0'/>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Title className="m-1vh" level={5}>{t('common.availableVolume')}</Title>
                        </Col>
                        <Col span={12}>
                            <p className="m-1vh">dfsajk</p>
                        </Col>
                    </Row>
                    <Divider className='m-0'/>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Title className="m-1vh" level={5}>{t('common.availableWeight')}</Title>
                        </Col>
                        <Col span={12}>
                            <p className="m-1vh">dfsajk</p>
                        </Col>
                    </Row>
                    <Divider className='m-0'/>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Title className="m-1vh" level={5}>{t('common.suggestedPrice')}</Title>
                        </Col>
                        <Col span={12}>
                            <p className="m-1vh">dfsajk</p>
                        </Col>
                    </Row>
                </Card>
                <div style={{marginLeft:'1vh', width:'30%'}}>
                    { offerCount == 0 && acceptedOffer == null && <Card>
                        <Title className='m-0' level={5}>{t('manage.noOffers')}</Title>
                    </Card>}
                    { offerCount > 0 && acceptedOffer == null &&
                        offers.map((offer) => (
                            <ProposalCard counterOffered={true} description={offer.description} offeredPrice={offer.offeredPrice} userName={offer.userName} userPhoto={offer.userPhoto}></ProposalCard>
                        ))
                    }
                    { acceptedOffer != null &&
                        <Card>
                            <div className='flex-center space-evenly'>
                                <Avatar src='https://101db.com.ar/12971-Productos/guitarra-electrica-fender-squier-mini-stratocaster-black.jpg' className='m-1vh' style={{marginRight:'2vh'}}/>
                                <div>
                                    <div className='flex-center space-around'>
                                        <Title className='m-0' level={4}>{acceptedOffer.userName}</Title>
                                        <div className='flex-center space-between'>
                                            <StarFilled style={{marginLeft:'3vh'}}></StarFilled>
                                            <Text  style={{marginLeft:'0.5em'}}>4.5 - (3 {t("review.reviews")})</Text>
                                        </div>
                                    </div>
                                    <Text className='m-0'>{acceptedOffer.userMail}</Text>
                                </div>
                            </div>
                        </Card>
                    }
                </div>
            </div>
        </div>
        */