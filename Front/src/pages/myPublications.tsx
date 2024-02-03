import { Button, Col, Pagination, Row, Skeleton, Tabs, Typography } from "antd";
import '../styles/main.scss';
import { useNavigate } from "react-router-dom";
import TripCard, { TripCardProps } from "../Components/tripCard";
import { useEffect, useState } from "react";
import { getPublications } from "../api/tripApi";
import { getClaims, getUserByUrl } from "../api/userApi";
import { getOffersByTrip } from "../api/offerApi";

const {Title} = Typography;

const MyPublications: React.FC = () => {

    const navigate = useNavigate();

    const [activeTrips, setActiveTrips] = useState(Array<TripCardProps>());
    const [expiredTrips, setExpiredTrips] = useState(Array<TripCardProps>());

    const [ activePage, setActivePage ] = useState<number>(1);
    const [ maxActivePage, setMaxActivePage ] = useState<number>(0)

    const [ expiredPage, setExpiredPage ] = useState<number>(1);
    const [ maxExpiredPage, setMaxExpiredPage ] = useState<number>(0)

    const [isLoading, setIsLoading] = useState<boolean>(true);


    
    useEffect(() => {
        setIsLoading(true);

        getUserByUrl(getClaims()?.userURL || '').then((user) => {
            getPublications(user.id.toString(), 'TRIP', '', '', 'ACTIVE', 1, 1, '', '', '', 0, 0, activePage, 12, 'departureDate ASC').then((trips) => {
                // Convert each trip to a promise that resolves to the trip details including offers
                const tripPromises = trips.map((publication) => {
                    return getOffersByTrip(publication.tripId.toString(), '1', '10').then((offers) => {
                        // Return a new object for each trip with the required properties
                        return {
                            type: 'trip',
                            from: publication.origin,
                            to: publication.destination,
                            fromDate: publication.departureDate,
                            toDate: publication.arrivalDate,
                            weight: publication.weight,
                            volume: publication.volume,
                            price: publication.price,
                            image: publication.image,
                            cargoType: publication.type,
                            id: publication.tripId,
                            clickUrl: '/trips/manage',
                            notifications: offers.length
                        };
                    });
                });
            
                // Wait for all promises to resolve, then set the active trips state with the resolved values
                Promise.all(tripPromises).then((resolvedTrips) => {
                    setActiveTrips(resolvedTrips as TripCardProps[]);
                }).catch(error => {
                    console.error("Failed to load trips or offers", error);
                    // Handle any errors or failed requests here
                }).finally(() => {
                    setIsLoading(false);
                })
            

                if(trips.length > 0)
                    setMaxActivePage(Number.parseInt(trips[0].maxPage ? trips[0].maxPage : '1'))
    
                getPublications(user.id.toString(), 'TRIP', '', '', 'EXPIRED', 1, 1, '', '', '', 0, 0, expiredPage, 12, 'departureDate ASC').then((trips) => {
                    setExpiredTrips(trips.map((publication) => {
                        return {
                            type: 'trip',
                            from: publication.origin,
                            to: publication.destination,
                            fromDate: publication.departureDate,
                            toDate: publication.arrivalDate,
                            weight: publication.weight,
                            volume: publication.volume,
                            price: publication.price,
                            image: publication.image,
                            cargoType: publication.type,
                            id: publication.tripId,
                            clickUrl: '',

                        }
                    }))
                    if(trips.length > 0)
                        setMaxExpiredPage(Number.parseInt(trips[0].maxPage ? trips[0].maxPage : '1'))
                    
                })
    
            })
        })
    }, [activePage, expiredPage])

    return(
        <Tabs type="line"  tabBarExtraContent={activeTrips.length > 0 ? {right: <Button type="primary" onClick={() => navigate('/trips/create')}>Create publication</Button>} : null}>
            <Tabs.TabPane tab="Active Publications" key="1">
                <Skeleton loading={isLoading}>
                    {activeTrips.length == 0 ? 
                        <div className="w-100 flex-center flex-column">
                            <Title level={3}>You have no active publications</Title>
                            <Button type="primary" onClick={() => navigate('/trips/create')}>Create Publication</Button>
                        </div>
                                
                                
                    : 
                        <div className="display-flex flex-column w-100" >
                            <div style={{display: "flex", flexDirection: 'column'}}>
                                <Row gutter={15}>
                                    {activeTrips.map((trip, index) => (
                                        <Col xxl={6} xl={6} lg={8} md={12} sm={22} xs={22} key={index}>
                                            <TripCard {...trip}></TripCard>
                                        </Col>
                                    )
                                    )}
                                </Row>
                            </div>
                            <Row className="w-100 flex-center">
                                <Pagination className="text-center mt-2vh" 
                                onChange={(page) => setActivePage(page)} 
                                current={activePage}
                                pageSize={12}
                                total={maxActivePage*12}/>
                            </Row>
                        </div>
                    }
                </Skeleton>
            </Tabs.TabPane>
            <Tabs.TabPane tab="Expired Publications" key="2">
                <Skeleton loading={isLoading}>
                    {expiredTrips.length == 0 ? 
                            <div className="w-100 flex-center flex-column">
                                <Title level={3}>You have no expired publications</Title>
                            </div>
                                    
                                    
                        : 
                        <div className="display-flex flex-column w-100" >
                            <div style={{display: "flex", flexDirection: 'column'}}>
                                <Row gutter={15}>
                                    {expiredTrips.map((trip, index) => (
                                        <Col xxl={6} xl={6} lg={8} md={12} sm={22} xs={22} key={index}>
                                            <TripCard {...trip}></TripCard>
                                        </Col>
                                    )
                                    )}
                                </Row>
                            </div>
                            <Row className="w-100 flex-center">
                                <Pagination className="text-center mt-2vh"
                                 onChange={(page) => setExpiredPage(page)} 
                                 current={expiredPage}
                                 pageSize={12}
                                 total={maxExpiredPage*12}/>
                            
                            </Row>
                        </div>
                    }
                </Skeleton>
            </Tabs.TabPane>
        </Tabs>
    )
}

export default MyPublications;