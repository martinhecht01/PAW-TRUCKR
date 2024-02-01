import React, {useEffect, useState} from 'react';
import {Col, Pagination, Row, Typography} from 'antd';
import '../styles/main.scss';
import '../styles/profile.scss';
import {useTranslation} from "react-i18next";

import {getTrips} from "../api/tripApi";
import TripCard, {TripCardProps} from "../Components/tripCard.tsx";

const PastTrips: React.FC = () => {

    const {t} = useTranslation();

    const {Title} = Typography;

    const [trips, setTrips] = useState<TripCardProps[]>([]);

    useEffect(() => {
        getTrips('PAST', '1', '12').then((trips) => {
            setTrips(trips.map((trip) => {
                return {
                    id: trip.tripId,
                    type: 'trip',
                    from: trip.origin,
                    to: trip.destination,
                    fromDate: trip.departureDate,
                    toDate: trip.arrivalDate,
                    weight: trip.weight,
                    volume: trip.volume,
                    price: trip.price,
                    image: trip.image,
                    cargoType: trip.type,
                    clickUrl: '/trips/manage'
                }
            }));
        });

    }, []); // The empty dependency array ensures that this effect runs only once

    return (
        <div style={{display: "flex", flexDirection: 'column'}}>
            <Row gutter={15} className='flex-center'>
                {trips.map((trip,index) => (
                        <Col xxl={5} xl={8} lg={12} md={12} sm={22} xs={22} key={index}>
                            <TripCard {...trip}></TripCard>
                        </Col>
                    )
                )}
            </Row>
            {trips.length === 0 ?
                <Row gutter={15} className='flex-center'>
                    <Title level={3}>{t('trips.noPastTrips')}</Title>
                </Row>:
            <Pagination className="text-center mt-2vh" defaultCurrent={1} total={50} />
            }
        </div>
    );
};

export default PastTrips;