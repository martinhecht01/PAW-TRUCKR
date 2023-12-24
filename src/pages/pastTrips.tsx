import React, {useEffect, useState} from 'react';
import {Col, Pagination, Row, Typography} from 'antd';
import '../styles/main.scss';
import '../styles/profile.scss';
import {useTranslation} from "react-i18next";
import PastTripCard, {TripCardProps} from "../Components/pastTripCard.tsx";


const { Title, Text } = Typography;

const PastTrips: React.FC = () => {

    const {t} = useTranslation();

    const [trips, setTrips] = useState<Array<TripCardProps>>([
        {
            type: 'trip',
            from: 'Helsinki',
            to: 'Tampere',
            fromDate: new Date(),
            toDate: new Date(),
            lastUpdate: new Date(),
            price: 1000,
            image: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNHkjak0XBtavkkM8z1vJo-BMjmjxfOEaU7pyFAGDc&s',
        },
    ]);

    useEffect(() => {
        const newTrip: TripCardProps = {
            type: 'trip',
            from: 'New Origin',
            to: 'New Destination',
            fromDate: new Date(),
            toDate: new Date(),
            lastUpdate: new Date(),
            price: 1500,
            image: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNHkjak0XBtavkkM8z1vJo-BMjmjxfOEaU7pyFAGDc&s',
        };

        // Create a new array with the existing trips and the new trip
        setTrips((prevTrips) => [...prevTrips, newTrip]);
    }, []); // The empty dependency array ensures that this effect runs only once

    return (
        <div style={{display: "flex", flexDirection: 'column'}}>
            <Row gutter={15} className='flex-center'>
                {trips.map((trip,index) => (
                        <Col xxl={5} xl={8} lg={12} md={12} sm={22} xs={22} key={index}>
                            <PastTripCard {...trip}></PastTripCard>
                        </Col>
                    )
                )}
            </Row>
            <Pagination className="text-center mt-2vh" defaultCurrent={1} total={50} />
        </div>
    );
};

export default PastTrips;