import React, { useEffect, useState } from 'react';
import {Card, Col, Image, Row, Typography, Avatar, Skeleton, Badge, message, Button, Rate, Pagination} from 'antd';
import '../styles/main.scss';
import '../styles/profile.scss';
import {useTranslation} from "react-i18next";
import ProposalCard, { ProposalProps } from "../Components/proposalCard.tsx";
import {ArrowRightOutlined, CheckCircleTwoTone, MinusCircleTwoTone, StarFilled, UserOutlined} from "@ant-design/icons";
import { Trip } from '../models/Trip.tsx';
import { User } from '../models/User.tsx';
import { useNavigate, useParams } from 'react-router-dom';
import { confirmTrip, getTripById } from '../api/tripApi.tsx';
import { acceptOffer, getOffersByTrip } from '../api/offerApi.tsx';
import { getClaims, getUserByUrl } from '../api/userApi.tsx';
import TextArea from 'antd/es/input/TextArea';
import { createReview } from '../api/reviewApi.tsx';
import { Review } from '../models/Review.tsx';


const { Title, Text } = Typography;

const ManageTrip: React.FC = () => {

    // const {t} = useTranslation();
    const router = useNavigate();

    const {tripId} = useParams();

    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [publication, setPublication] = useState<Trip>();
    const [offers, setOffers] = useState<ProposalProps[]>([]);
    const [user, setUser] = useState<User>();
    const [reviewSubmitted, setReviewSubmitted] = useState<boolean>(false);
    const [tripConfirmed, setTripConfirmed] = useState<boolean>(false);
    const [offerAccepted, setOfferAccepted] = useState<boolean>(false);

    const [proposalLoading, setProposalLoading] = useState<boolean>(true);

    const [offersPage, setOffersPage] = useState<number>(1);
    const [offersMaxPage, setOffersMaxPage] = useState<number>(0);

    const [rating, setRating] = useState<number>(0);
    const [review, setReview] = useState<string>('');

    const claims = getClaims();


    async function submitReview(){
        createReview(new Review(0, tripId!, rating, review, '')).then(() => {
            message.success('Review submitted');
            setReviewSubmitted(true);
        }).catch(() => {
            message.error('Error submitting review');
        })
    }

    async function confirmTripAction(){
        confirmTrip(tripId!).then(() => {
            message.success('Trip confirmed');
            setTripConfirmed(true);
        }).catch(() => {
            message.error('Error confirming trip');
        })
    }

    useEffect(() => {
        // Function to fetch trip details and related data
        setProposalLoading(true);
        const fetchTripDetails = async () => {
            if (!tripId) {
                router('/404');
                return;
            }
    
            try {
                const trip = await getTripById(tripId);
                if (!trip) {
                    router('/404');
                    return;
                }
    
                setPublication(trip);
    
                const userUrl = claims?.role === 'PROVIDER' ? trip.trucker : trip.provider;
                const userData = await getUserByUrl(userUrl);
                setUser(userData);

                if (!trip.provider || !trip.trucker) {
                    const offersData = await getOffersByTrip(tripId, offersPage.toString(), '3');
                    const offersPromises = offersData.map(async (offer) => {
                        const user = await getUserByUrl(offer.userUrl);
                        return {
                            id: offer.id.toString(),
                            description: offer.description,
                            offeredPrice: offer.price,
                            userPhoto: user.imageUrl,
                            userName: user.name,
                            userMail: user.email,
                            counterOffer: offer.conterOfferUrl,
                            acceptAction: acceptOfferAction,
                            tripId: tripId
                        };
                    });
                    setOffersMaxPage(offersData[0].maxPage ? Number.parseInt(offersData[0].maxPage) : 0);
                    await Promise.all(offersPromises).then((offers) => 
                        setOffers(offers)
                    );
                }
            } catch (error) {
                message.error('Error loading data');
            } finally {
                setIsLoading(false);
                setProposalLoading(false);
            }
        };

        fetchTripDetails();

    }, [reviewSubmitted, tripConfirmed, offerAccepted, offersPage]);

    async function acceptOfferAction(id: string, action: 'ACCEPT' | 'REJECT' ){
        setIsLoading(true);
        acceptOffer(id, action).then(() => {
            message.success('Success');
            setOfferAccepted(true);
        }).catch(() => {
            message.error('Error accepting offer');
        }).finally (() =>{
            setIsLoading(false);
        })
    }
    

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

                    {publication?.provider && publication?.trucker ?
                        <>
                            <Card onClick={() => router('/profile/' + user?.id)} hoverable>
                                <div className="w-100 space-between">
                                    <Avatar size={64} src={user?.imageUrl} icon={<UserOutlined/>}></Avatar>
                                    <Title level={3}>{user?.name}</Title>
                                    <div>
                                        <Title level={4}><StarFilled/>{user?.rating == 0 ? '-' : new Number(user?.rating).toFixed(1)}</Title>
                                    </div>
                                </div>
                            </Card>
                            <Card className="mt-5">
                                { (!publication.providerConfirmation || !publication.truckerConfirmation) ?
                                    claims?.role === 'PROVIDER' ? 

                                        <>
                                            <div className='mt-1vh'>
                                                <Text>{publication.providerConfirmation ? <CheckCircleTwoTone twoToneColor='#56F000'/> : <MinusCircleTwoTone twoToneColor='#A4ABB6'/>} {publication.providerConfirmation ? ' You recieved the cargo' : ` You didn't receive the cargo`}</Text>
                                            </div>
                                            <div className='mt-1vh'>
                                                <Text>{publication.truckerConfirmation ? <CheckCircleTwoTone twoToneColor='#56F000'/> : <MinusCircleTwoTone twoToneColor='#A4ABB6'/>} {publication.truckerConfirmation ? ' Trucker completed trip' : ` Trucker didn't complete the trip`}</Text>
                                            </div>
                                            {!publication.providerConfirmation ? <Button type="primary" className="w-100 mt-2vh" onClick={confirmTripAction}>Recieved Cargo</Button> : null}

                                        </> 
                                        :
                                        <>
                                            <div className='mt-1vh'>
                                                <Text>{publication.providerConfirmation ? <CheckCircleTwoTone twoToneColor='#56F000'/> : <MinusCircleTwoTone twoToneColor='#A4ABB6'/>} {publication.providerConfirmation ? ' Provider recieved the cargo!' : ` Provider didn't recieve the cargo yet`}</Text>
                                            </div>
                                            <div className='mt-1vh'>
                                                <Text>{publication.truckerConfirmation ? <CheckCircleTwoTone twoToneColor='#56F000'/> : <MinusCircleTwoTone twoToneColor='#A4ABB6'/>} {publication.truckerConfirmation ? ' You completed the trip' : ` You didn't complete the trip`}</Text>
                                            </div>
                                            {!publication.truckerConfirmation ? <Button type="primary" className="w-100 mt-2vh" onClick={confirmTripAction}>Finished Trip</Button> : null}
                                            
                                        </>
                                    

                                    : 
                                    
                                    <div className='w-100'>
                                        <Title level={4}><CheckCircleTwoTone twoToneColor='#56F000' size={50} className='mr-5'/> Completed trip</Title>
                                    </div>

                                }
                                
                            </Card>
                            {
                                ((claims?.role === 'PROVIDER' && !publication.providerSubmittedHisReview ) || (claims?.role === 'TRUCKER' && !publication.truckerSubmittedHisReview)) && publication.providerConfirmation && publication.truckerConfirmation ?

                                <Card className="mt-5">
                                    <Rate allowHalf defaultValue={0} onChange={(value) => setRating(value)}/>
                                    <TextArea rows={4} className="mt-2vh" placeholder="Write a review" onChange={(e) => setReview(e.target.value)}/>
                                    <Button type="primary" className="w-100 mt-2vh" onClick={submitReview}>Submit Review</Button>
                                </Card>

                                : (claims?.role === 'PROVIDER' && publication.providerSubmittedHisReview) || (claims?.role === 'TRUCKER' && publication.truckerSubmittedHisReview) ?

                                <Card className="mt-5">
                                    <Title level={4}>You already submitted your review</Title>
                                </Card> 
                                
                                : null

                            }
                        </>
                        :

                        <>
                            {
                                offers.length != 0 ?
                                <>
                                    <Skeleton loading={proposalLoading}>
                                        {offers.map((offer) => (
                                            <ProposalCard {...offer}></ProposalCard>
                                        ))}
                                    </Skeleton>
                                    <div className='w-100 mt-2vh flex-center'>
                                            <Pagination onChange={(page) => setOffersPage(page)} total={offersMaxPage*3} pageSize={3}/>
                                    </div>
                                </>

                                :
                                <Card>
                                    <Title level={4}>No offers yet</Title>
                                </Card>
                            
                            }
                        </>
                        
                        
                    }
                </Col>
            </Skeleton>
        </Row>
    </div>

    );
};

export default ManageTrip;