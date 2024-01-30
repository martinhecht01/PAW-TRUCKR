import React, {useEffect, useState} from 'react';
import {Button, Card, Typography} from 'antd';
import '../styles/main.scss';
import OfferCard from '../components/offerCard';
import {User} from "../models/User";
import {getUserById} from "../api/userApi";
import {Offer} from "../models/Offer";
import {getOffersByUser} from "../api/offerApi";
import NotFound404 from "./404";
import {Trip} from "../models/Trip";
import {getTripByUrl} from "../api/tripApi";
import {useTranslation} from "react-i18next";

const { Title } =Typography;



const SentOffers: React.FC = () => {

    const {t} = useTranslation();

    const [ user, setUser] = useState<User>();
    const [ offers, setOffers] = useState<Offer[]>([]);
    const [ trips, setTrips ] = useState<Trip[]>([]);
    const [ tripUsers, setTripUsers ] = useState<User[]>([]);

    useEffect(() => {
        getUserById(76).then((userx) => {
            setUser(userx);
            getOffersByUser(76).then((offersx) => {
                setOffers(offersx);
                console.log(offersx);
                offersx.forEach((offer, i) => {
                    getTripByUrl(offer.tripUrl).then((trip) => {
                        console.log("VA UN TRIP")
                        console.log(trip)
                        setTrips((prevState) => [...prevState, trip]);
                        if (trip.trucker != null){
                            getUserById(Number(trip.trucker)).then((trucker) => {
                                setTripUsers((prevState) => [...prevState, trucker])
                            })
                        }
                        else{
                            getUserById(Number(trip.provider)).then((provider) => {
                                setTripUsers((prevState) => [...prevState, provider])
                            })
                        }
                    })})
            })
        })
    }, [])
    
    if (user != undefined && offers.length == 0) {
        return (
            <Card title={t('sentOffers.sentOffers')}>
                <div className="flex-center">
                    <div className='flex-column' style={{textAlign:'center'}}>
                        <Title className='m-0' level={5}>{t('sentOffers.noOffers')}</Title>
                        <Button style={{marginTop:'2vh'}} type='primary'>{t('common.browse')}</Button>
                    </div>
                </div>
            </Card>)}
    else if (user != undefined && offers.length > 0) {
        return (
            <div>
                <Title className='ml-10' level={3}>Sent Offers</Title>
                {offers.map((offer, index) => (
                    <OfferCard from={trips[index].origin} to={trips[index].destination} reviewNumber={tripUsers[index].ratingCount} reviewScore={tripUsers[index].rating} price={offer.price}
                               dateTo={trips[index].arrivalDate} dateFrom={trips[index].departureDate} userImg={tripUsers[index].imageUrl}></OfferCard>
                ))}

            </div>)
    }
    else{
        return (
            <NotFound404></NotFound404>
        );
        }
}

export default SentOffers;