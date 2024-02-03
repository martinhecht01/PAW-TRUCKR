import React, {useEffect, useState} from 'react';
import {Col, Pagination, Row, Skeleton, Typography} from 'antd';
import '../styles/main.scss';
import '../styles/profile.scss';
import {useTranslation} from "react-i18next";

import {getTrips} from "../api/tripApi";
import TripCard, {TripCardProps} from "../Components/tripCard.tsx";

const PastTrips: React.FC = () => {

    const {t} = useTranslation();

    const {Title} = Typography;

    const [trips, setTrips] = useState<TripCardProps[]>([]);
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [page, setPage] = useState<number>(1);
    const [maxPage, setMaxPage] = useState<number>(0);
    const [pageSize] = useState<number>(12);

    useEffect(() => {
        setIsLoading(true);
        getTrips('PAST', page.toString(), pageSize.toString()).then((trips) => {
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
            if(trips.length > 0)
                setMaxPage(trips[0].maxPage ? Number.parseInt(trips[0].maxPage) : 1)
            setIsLoading(false);
        });

    }, [page]); // The empty dependency array ensures that this effect runs only once

    return (
        <div style={{display: "flex", flexDirection: 'column'}}>
            <Skeleton loading={isLoading}>
                <Row gutter={15} className='flex-center'>
                    {trips.map((trip,index) => (
                            <Col xxl={6} xl={6} lg={8} md={12} sm={22} xs={22} key={index}>
                                <TripCard {...trip}></TripCard>
                            </Col>
                        )
                    )}
                </Row>
                {trips.length === 0 ?
                    <Row gutter={15} className='flex-center'>
                        <Title level={3}>{t('pastTrips.noPastTrips')}</Title>
                    </Row>:
                    <Pagination className="text-center mt-2vh" current={page} total={pageSize*maxPage} pageSize={pageSize} onChange={(page) => setPage(page)} />
                }
            </Skeleton>
        </div>
    );
};

export default PastTrips;