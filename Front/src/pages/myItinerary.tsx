import { Button, Col, Pagination, Row, Skeleton, Tabs, Typography } from "antd";
import '../styles/main.scss';
import { useNavigate } from "react-router-dom";
import ItineraryTripCard, { ItineraryTripCardProps } from "../Components/itineraryTripCard";
import { useEffect, useState } from "react";
import { getTrips } from "../api/tripApi";
import { getClaims, getUserByUrl } from "../api/userApi";
import TripCard from "../Components/tripCard";

const MyItinerary: React.FC = () => {

    const {Title} = Typography;

    const claims = getClaims();

    const navigate = useNavigate();

    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [ongoingTrips, setOngoingTrips] = useState<Array<ItineraryTripCardProps>>([]);
    const [futureTrips, setFutureTrips] = useState<Array<ItineraryTripCardProps>>([]);

    useEffect(() => {
        getTrips('ONGOING', '1', '12').then((trips) => {
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
            }).then(() => {
                getTrips('FUTURE', '1', '12').then((trips) => {
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
                        setIsLoading(false);
                    });
                });
            });
        });
    }, []);
    

    return(
        <Tabs type="line" tabBarExtraContent={{right: <Button type="link" onClick={() => navigate('/pastTrips')}>View Past Trips</Button>}}>
            
            <Tabs.TabPane tab="Ongoing Trips" key="1">
                <Skeleton loading={isLoading}>
                    <Row className="w-100 flex-center">
                        <Col span={15}>
                            {ongoingTrips.map((trip) => (
                                <ItineraryTripCard {...trip}/>
                            ))}
                            
                        </Col>
                    </Row>
                    {ongoingTrips.length === 0 ? <Row className="w-100 flex-center">
                        <Title level={3}>No Ongoing Trips</Title> </Row>:
                    <Row className="w-100 flex-center">
                        <Pagination className="text-center mt-2vh" defaultCurrent={1} total={50}/>
                    </Row>
                    }
                </Skeleton>
            </Tabs.TabPane>
            <Tabs.TabPane tab="Future Trips" key="2">
                <Skeleton loading={isLoading}>
                    <Row className="w-100 flex-center">
                        <Col span={15}>
                            {futureTrips.map((trip) => (
                                <ItineraryTripCard {...trip}/>
                            ))}
                            
                        </Col>
                    </Row>
                    {futureTrips.length === 0 ? <Row className="w-100 flex-center">
                        <Title level={3}>No Future Trips</Title> </Row>:
                        <Row className="w-100 flex-center">
                            <Pagination className="text-center mt-2vh" defaultCurrent={1} total={50}/>
                        </Row>
                    }
                </Skeleton>
            </Tabs.TabPane>
        </Tabs>
    )
}

export default MyItinerary;