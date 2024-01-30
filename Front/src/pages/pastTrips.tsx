import React, {useEffect, useState} from 'react';
import {Col, Pagination, Row, Typography} from 'antd';
import '../styles/main.scss';
import '../styles/profile.scss';
import {useTranslation} from "react-i18next";
import PastTripCard, { TripCardProps } from '../Components/pastTripCard';
import {Trip} from "../models/Trip";
import {getUserById} from "../api/userApi";
import {User} from "../models/User";
import {getTrips} from "../api/tripApi";

const PastTrips: React.FC = () => {

    const {t} = useTranslation();

    const [user, setUser] = useState<User>();
    const [trips, setTrips] = useState<Trip[]>([]);

    useEffect(() => {
        getUserById(2).then((user) => {
            setUser(user);
        });

        getTrips("PAST").then(r => {
            r.forEach((trip) => {
                setTrips((prevTrips) => [...prevTrips, trip]);
            })
        })

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