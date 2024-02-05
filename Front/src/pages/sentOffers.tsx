import React, {useEffect, useState} from 'react';
import {Col, Pagination, Row, Skeleton, Typography, message} from 'antd';
import '../styles/main.scss';
import OfferCard, { OfferCardProps } from '../Components/offerCard';
import {getClaims, getUserByUrl} from "../api/userApi";
import {deleteOffer, getOffersByUser} from "../api/offerApi";
import {getPublicationByUrl} from "../api/tripApi";
import {useTranslation} from "react-i18next";
import { useNavigate } from 'react-router-dom';

const { Title } =Typography;



const SentOffers: React.FC = () => {

    const {t} = useTranslation();
    const router = useNavigate();
    const [ offers, setOffers] = useState<OfferCardProps[]>([]);
    const [ isLoading, setIsLoading] = useState<boolean>(true);

    const [page, setPage] = useState<number>(1);
    const [maxPage, setMaxPage] = useState<number>(0);

    async function cancelOffer(id: number){
        try{
            deleteOffer(id);
            setOffers(offers.filter((offer) => offer.id !== id));
            message.success(t('offerCard.offerCanceled'));
        }
        catch (e){
            message.error(t('offerPage.errorCancelingOffer'));
        }
    }

    useEffect(() => {
        const claims = getClaims();

        setIsLoading(true);

        if(claims == null)
            router('/login')

        getUserByUrl(claims!.userURL).then((user) => {
            getOffersByUser(user.id, page.toString(), '6').then((offersRet) => {
                const userPromises = offersRet.map((offer) => {
                    return getPublicationByUrl(offer.tripUrl).then((trip) => {
                                return {
                                    from: trip.origin,
                                    to: trip.destination,
                                    dateFrom: trip.departureDate,
                                    dateTo: trip.arrivalDate,
                                    price: offer.price,
                                    id: offer.id,
                                    description: offer.description,
                                    tripId: trip.tripId,
                                    parentOffer: offer.parentOffer,
                                    counterOffer: offer.conterOfferUrl,
                                    tripCreator: trip.creator,
                                    onCancel: cancelOffer
                                };
                    });
                });

                Promise.all(userPromises).then((offers) => {
                    setOffers(offers);
                    if(offers.length > 0)
                        setMaxPage(offersRet[0].maxPage ? Number.parseInt(offersRet[0].maxPage) : 1);
                    setIsLoading(false);
                })
            })
        })
    }, [page])
    

    return (
        <Skeleton loading={isLoading}>
            <Row gutter={20} className='flex-center w-100'>
                {offers.length === 0 ? <Title level={3}>{t('sentOffers.noOffers')}</Title> : 
                    <>
                        <Col span={18}>
                            <Title  level={3}>{t('sentOffers.sentOffers')}</Title>
                        </Col>
                        {offers.map((offer) => (
                            <Col span={18}>
                                <OfferCard from={offer.from} to={offer.to} dateFrom={offer.dateFrom} dateTo={offer.dateTo} price={offer.price} id={offer.id} onCancel={cancelOffer} description={offer.description} tripId={offer.tripId} key={offer.id} parentOffer={offer.parentOffer} counterOffer={offer.counterOffer} tripCreator={offer.tripCreator}></OfferCard>
                            </Col>
                        ))}
                    </>
                }

            </Row>
            {offers.length > 0 ?
            <Row className='w-100 flex-center mt-2vh'>
                <Pagination 
                    onChange={(page) => {setPage(page)}}
                    total={6*maxPage}
                    pageSize={6}
                    current={page}
                />  
            </Row>
            :
            null
            }
        </Skeleton>
    )
    
}

export default SentOffers;