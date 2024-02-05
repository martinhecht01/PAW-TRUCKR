import React, { useEffect, useState } from 'react';
import {Card, Col, Image, Row, Typography, Avatar, Skeleton, Badge, message, Button, Rate, Pagination, Form, Popconfirm} from 'antd';
import '../styles/main.scss';
import '../styles/profile.scss';
import ProposalCard, { ProposalProps } from "../Components/proposalCard.tsx";
import {ArrowRightOutlined, CheckCircleFilled, CheckCircleTwoTone, MinusCircleTwoTone, StarFilled, UserOutlined} from "@ant-design/icons";
import { Trip } from '../models/Trip.tsx';
import { User } from '../models/User.tsx';
import { useNavigate, useParams } from 'react-router-dom';
import { confirmTrip, deletePublication, getTripById } from '../api/tripApi.tsx';
import { acceptOffer, getOffersByTrip } from '../api/offerApi.tsx';
import { getClaims, getUserByUrl } from '../api/userApi.tsx';
import TextArea from 'antd/es/input/TextArea';
import { createReview } from '../api/reviewApi.tsx';
import { Review } from '../models/Review.tsx';
import { useTranslation } from 'react-i18next';
import { getCargoTypeColor } from '../Components/cargoTypeColor.tsx';


const { Title, Text } = Typography;

const ManageTrip: React.FC = () => {

    const router = useNavigate();
    const {t} = useTranslation();

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

    const [form] = Form.useForm();

    const claims = getClaims();


    const submitReview = async (values: { review: string; rating: number }) => {
        const { review, rating } = values;
        createReview(new Review(0, tripId!, rating, review, '')).then(() => {
            message.success(t('manage.reviewSubmitted'));
            setReviewSubmitted(true);
        }).catch(() => {
            message.error(t('manage.errorSubmittingReview'));
        })
    }

    async function confirmTripAction(){
        confirmTrip(tripId!).then(() => {
            message.success(t('manage.tripConfirmed'));
            setTripConfirmed(true);
        }).catch(() => {
            message.error(t('manage.errorConfirmingTrip'));
        })
    }

    useEffect(() => {
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
                            tripId: tripId,
                            rating: user.rating,
                            userId: user.id.toString()
                        };
                    });
                    if(offersData.length > 0)
                        setOffersMaxPage(offersData[0].maxPage ? Number.parseInt(offersData[0].maxPage) : 0);
                    await Promise.all(offersPromises).then((offers) => 
                        setOffers(offers)
                    );
                }
            } catch (error) {
                console.log(error);
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
            message.success(action == 'ACCEPT' ? t('offerCard.offerAccepted') : t('offerCard.offerRejected'));
            setOfferAccepted(true);
        }).catch(() => {
            message.error(t('offerCard.errorAcceptingOffer'));
        }).finally (() =>{
            setIsLoading(false);
        })
    }

    async function confirmDelete(){
        deletePublication(tripId!).then(() => {
            message.success(t('manage.publicationDeleted'));
            router('/myPublications');
        }).catch(() => {
            message.error(t('manage.errorDeletingPublication'));
        })
    }
    

    return (
        <div className="w-100 flex-center">
        <Row style={{justifyContent: 'space-evenly'}} className="w-80">
            <Skeleton loading={isLoading}>
                {!publication?.provider || !publication?.trucker  ?
                    <Col span={2}>
                    <Popconfirm
                        title={t('manage.deletePublicationPop.title')}
                        description={t('manage.deletePublicationPop.message')}
                        onConfirm={confirmDelete}
                        okText={t('manage.deletePublicationPop.delete')}
                        cancelText={t('manage.deletePublicationPop.cancel')}
                    >
                        <Button danger>{t('manage.deletePublicationPop.title')}</Button>
                    </Popconfirm>
                    
                    </Col>
                    : null
                }
                <Col span={10}>
                    <div>
                        <Badge.Ribbon text={<Title level={5} style={{color: 'white', margin: 3}}>{publication?.type}</Title>}  color={getCargoTypeColor(publication?.type.toLowerCase())}>
                                <Image
                                    style={{ width: '100%',
                                    height: '450px',
                                    objectFit: 'cover',
                                    objectPosition: 'center center'}}
                                    alt="example"
                                    src={publication?.image}
                                    preview={false}
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
                                <Text>{t('common.weight')}</Text>
                            </Card>
                        </Col>
                        <Col span={11}>
                            <Card className="w-100 text-center">
                                <Title level={3}>{publication?.volume} M3</Title>
                                <Text>{t('common.volume')}</Text>
                            </Card>
                        </Col>
                    </Row>
                </Col>
                <Col span={8}>

                {publication?.provider && publication?.trucker ?
                    <>
                        <Card onClick={() => router('/profile/' + user?.id)} hoverable>
                            <div className="w-100 space-between">
                                <Avatar size={64} src={user?.imageUrl} icon={<UserOutlined />}></Avatar>
                                <Title level={3}>{user?.name}</Title>
                                <div>
                                    <Title level={4}><StarFilled />{user?.rating === 0 ? '-' : new Number(user?.rating).toFixed(1)}</Title>
                                </div>
                            </div>
                        </Card>
                        <Card className="mt-5">
                            {(!publication.providerConfirmation || !publication.truckerConfirmation) ?
                                claims?.role === 'PROVIDER' ?
                                    <>
                                        <Text>{publication.providerConfirmation ? t('publication.confirmed') : t('publication.notConfirmed')}</Text>
                                        <Text>{publication.truckerConfirmation ? t('truckerConfirmation.completed') : t('truckerConfirmation.notCompleted')}</Text>
                                        {!publication.providerConfirmation ? <Button type="primary" className="w-100 mt-2vh" onClick={confirmTripAction}>{t('actions.confirmReceived')}</Button> : null}
                                    </>
                                    :
                                    <>
                                        <Text>{publication.providerConfirmation ? t('providerConfirmation.confirmed') : t('providerConfirmation.notConfirmed')}</Text>
                                        <Text>{publication.truckerConfirmation ? t('publication.completed') : t('publication.notCompleted')}</Text>
                                        {!publication.truckerConfirmation ? <Button type="primary" className="w-100 mt-2vh" onClick={confirmTripAction}>{t('actions.confirmCompleted')}</Button> : null}
                                    </>
                                : <Title level={4}><CheckCircleTwoTone twoToneColor='#00ff00'/> {t('actions.completed')}</Title>
                            }
                        </Card>
                        {((claims?.role === 'PROVIDER' && !publication.providerSubmittedHisReview) || (claims?.role === 'TRUCKER' && !publication.truckerSubmittedHisReview)) && publication.providerConfirmation && publication.truckerConfirmation ?
                            <Card className="mt-5">
                                <Form form={form} onFinish={submitReview} layout="vertical">
                                    <Form.Item name="rating" rules={[{ required: true, message: t('validation.ratingRequired') }]} >
                                        <Rate allowHalf />
                                    </Form.Item>
                                    <Form.Item name="review" rules={[{ required: true, message: t('validation.reviewRequired') }]} >
                                        <TextArea rows={4} placeholder={t('manage.writeReview')} />
                                    </Form.Item>
                                    <Form.Item>
                                        <Button type="primary" htmlType="submit" className="w-100">{t('actions.submitReview')}</Button>
                                    </Form.Item>
                                </Form>
                            </Card>
                            : null
                        }
                    </>
                    :
                    <>
                        {offers.length !== 0 ?
                            <>
                                <Skeleton loading={proposalLoading}>
                                    {offers.map((offer) => (
                                        <ProposalCard key={offer.id} {...offer} />
                                    ))}
                                </Skeleton>
                                <div className='w-100 mt-2vh flex-center'>
                                    <Pagination onChange={(page) => setOffersPage(page)} total={offersMaxPage * 10} pageSize={10} />
                                </div>
                            </>
                            :
                            <Card>
                                <Title level={4}>{t('manage.noOffers')}</Title>
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