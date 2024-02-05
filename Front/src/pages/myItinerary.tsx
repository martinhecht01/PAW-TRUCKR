import { Button, Col, Pagination, Row, Skeleton, Tabs, Typography } from "antd";
import '../styles/main.scss';
import { useNavigate } from "react-router-dom";
import ItineraryTripCard, { ItineraryTripCardProps } from "../Components/itineraryTripCard";
import { useEffect, useState } from "react";
import { getTrips } from "../api/tripApi";
import { getClaims, getUserByUrl } from "../api/userApi";
import { useTranslation } from "react-i18next";

const MyItinerary: React.FC = () => {

    const {Title} = Typography;

    const {t} = useTranslation();

    document.title = t('pageTitles.myItinerary')

    const claims = getClaims();

    const navigate = useNavigate();

    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [ongoingTrips, setOngoingTrips] = useState<Array<ItineraryTripCardProps>>([]);
    const [futureTrips, setFutureTrips] = useState<Array<ItineraryTripCardProps>>([]);

    const [ongoingPage, setOngoingPage] = useState<number>(1);
    const [maxOngoingPage, setMaxOngoingPage] = useState<number>(0)

    const [futurePage, setFuturePage] = useState<number>(1);
    const [maxFuturePage, setMaxFuturePage] = useState<number>(0)

    useEffect(() => {
        getTrips('ONGOING', ongoingPage.toString(), '12').then((trips) => {
            const userPromises = trips.map((trip) => {
                return getUserByUrl(claims?.role === 'PROVIDER' ? trip.trucker : trip.provider)
                    .then((user) => {
                        return {
                            origin: trip.origin,
                            destination: trip.destination,
                            departure: trip.departureDate,
                            arrival: trip.arrivalDate,
                            userUrl: user.imageUrl,
                            name: user.name,
                            role: user.role,
                            rating: user.rating,
                            id: trip.tripId
                        };
                    });
            });
    
            Promise.all(userPromises).then((ongoingTrips) => {
                setOngoingTrips(ongoingTrips);
                if(trips.length > 0)
                    setMaxOngoingPage(Number.parseInt(trips[0].maxPage ? trips[0].maxPage : '1'))
            }).then(() => {
                getTrips('FUTURE', futurePage.toString(), '12').then((trips) => {
                    const futureUserPromises = trips.map((trip) => {
                        return getUserByUrl(claims?.role === 'PROVIDER' ? trip.trucker : trip.provider)
                            .then((user) => {
                                return {
                                    origin: trip.origin,
                                    destination: trip.destination,
                                    departure: trip.departureDate,
                                    arrival: trip.arrivalDate,
                                    userUrl: user.imageUrl,
                                    name: user.name,
                                    role: user.role,
                                    rating: user.rating,
                                    id: trip.tripId
                                };
                            });
                    });
    
                    Promise.all(futureUserPromises).then((futureTrips) => {
                        setFutureTrips(futureTrips);
                        if(trips.length > 0)
                            setMaxFuturePage(Number.parseInt(trips[0].maxPage ? trips[0].maxPage : '1'))
                        setIsLoading(false);
                    });
                });
            });
        });
    }, [ongoingPage, futurePage]);
    

    return(
        <Tabs type="line" tabBarExtraContent={{right: <Button type="link" onClick={() => navigate('/pastTrips')}>{t('myItinerary.viewPastTrips')}</Button>}}>
            
            <Tabs.TabPane tab={t('myItinerary.ongoingTrips')} key="1">
                <Skeleton loading={isLoading}>
                    <Row className="w-100 flex-center">
                        <Col span={15}>
                            {ongoingTrips.map((trip) => (
                                <ItineraryTripCard {...trip}/>
                            ))}
                            
                        </Col>
                    </Row>
                    {ongoingTrips.length === 0 ? <Row className="w-100 flex-center">
                        <Title level={3}>{t('myItinerary.noOngoingTrips')}</Title> </Row>:
                    <Row className="w-100 flex-center">
                        <Pagination className="text-center mt-2vh" current={ongoingPage} pageSize={12} total={maxOngoingPage*12} onChange={(page) => setOngoingPage(page)}/>
                    </Row>
                    }
                </Skeleton>
            </Tabs.TabPane>
            <Tabs.TabPane tab={t('myItinerary.futureTrips')} key="2">
                <Skeleton loading={isLoading}>
                    <Row className="w-100 flex-center">
                        <Col span={15}>
                            {futureTrips.map((trip) => (
                                <ItineraryTripCard {...trip}/>
                            ))}
                            
                        </Col>
                    </Row>
                    {futureTrips.length === 0 ? <Row className="w-100 flex-center">
                        <Title level={3}>{t('myItinerary.noFutureTrips')}</Title> </Row>:
                        <Row className="w-100 flex-center">
                            <Pagination className="text-center mt-2vh" defaultCurrent={futurePage} total={maxFuturePage*12} pageSize={12} onChange={(page) => setFuturePage(page)}/>
                        </Row>
                    }
                </Skeleton>
            </Tabs.TabPane>
        </Tabs>
    )
}

export default MyItinerary;