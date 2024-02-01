import React, {useEffect, useState} from 'react';
import {Button, Card, Col, Row, Skeleton, Typography, message} from 'antd';
import '../styles/main.scss';
import OfferCard, { OfferCardProps } from '../Components/offerCard';
import {User} from "../models/User";
import {getClaims, getUserById, getUserByUrl} from "../api/userApi";
import {Offer} from "../models/Offer";
import {deleteOffer, getOffersByUser} from "../api/offerApi";
import NotFound404 from "./404";
import {Trip} from "../models/Trip";
import {getPublicationById, getPublicationByUrl, getTripByUrl} from "../api/tripApi";
import {useTranslation} from "react-i18next";
import { useNavigate } from 'react-router-dom';

const { Title } =Typography;



const SentOffers: React.FC = () => {

    const {t} = useTranslation();
    const router = useNavigate();

    const [ user, setUser] = useState<User>();
    const [ offers, setOffers] = useState<OfferCardProps[]>([]);
    const [ isLoading, setIsLoading] = useState<boolean>(true);

    /*
export type OfferCardProps = {
    from: string;
    to: string;
    dateFrom: Date;
    dateTo: Date;
    userImg: string;
    reviewScore: number;
    reviewNumber: number;
    price: number;
};
    */

    async function cancelOffer(id: number){
        try{
            deleteOffer(id);
            setOffers(offers.filter((offer) => offer.id !== id));
            message.success('Offer deleted successfully');
        }
        catch (e){
            message.error('Error deleting offer')
        }
    }

    useEffect(() => {
        const claims = getClaims();

        if(claims == null)
            router('/login')

        getUserByUrl(claims!.userURL).then((user) => {
            getOffersByUser(user.id).then((offers) => {
                const userPromises = offers.map((offer) => {
                    return getUserByUrl(offer.userUrl)
                        .then((user) => {
                            return getPublicationByUrl(offer.tripUrl)
                                .then((trip) => {
                                    return {
                                        from: trip.origin,
                                        to: trip.destination,
                                        dateFrom: trip.departureDate,
                                        dateTo: trip.arrivalDate,
                                        userImg: user.imageUrl,
                                        reviewScore: user.rating,
                                        price: offer.price,
                                        id: offer.id,
                                        description: offer.description,
                                        onCancel: cancelOffer
                                    };
                                });
                        });
                });

                Promise.all(userPromises).then((offers) => {
                    setOffers(offers);
                    setIsLoading(false);

                })
            })
        })
    }, [])
    

    return (
        <Skeleton loading={isLoading}>
            <Row gutter={20} className='flex-center w-100'>
                {offers.length === 0 ? <Title level={3}>{t('noOffers')}</Title> : 
                    <>
                        <Col span={18}>
                            <Title  level={3}>Sent Offers</Title>
                        </Col>
                        {offers.map((offer) => (
                            <Col span={18}>
                                <OfferCard from={offer.from} to={offer.to} dateFrom={offer.dateFrom} dateTo={offer.dateTo} userImg={offer.userImg} reviewScore={offer.reviewScore} price={offer.price} id={offer.id} onCancel={cancelOffer} description={offer.description}></OfferCard>
                            </Col>
                        ))}
                    </>
                }
            </Row>
        </Skeleton>
    )
    
}

export default SentOffers;