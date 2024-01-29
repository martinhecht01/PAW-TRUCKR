import { Button, Col, Pagination, Row, Skeleton, Tabs, Typography } from "antd";
import '../styles/main.scss';
import { useNavigate } from "react-router-dom";
import TripCard, { TripCardProps } from "../Components/tripCard";
import { useEffect, useState } from "react";
import { getPublications } from "../api/tripApi";
import { getClaims, getUserByUrl } from "../api/userApi";

const {Title, Text} = Typography;

const MyPublications: React.FC = () => {

    const navigate = useNavigate();

    const [activeTrips, setActiveTrips] = useState(Array<TripCardProps>());
    const [expiredTrips, setExpiredTrips] = useState(Array<TripCardProps>());

    const [ activePage, setActivePage ] = useState<number>(1);
    const [ expiredPage, setExpiredPage ] = useState<number>(1);

    const [isLoading, setIsLoading] = useState<boolean>(true);


    
    useEffect(() => {
        setIsLoading(true);

        getUserByUrl(getClaims()?.userURL || '').then((user) => {
            getPublications(user.id.toString(), 'TRIP', '', '', 'ACTIVE', 1, 1, '', '', '', 0, 0, activePage, 12, 'departureDate ASC').then((trips) => {
                setActiveTrips(trips.map((publication) => {
                    return {
                        type: 'trip',
                        from: publication.origin,
                        to: publication.destination,
                        fromDate: publication.departureDate,
                        toDate: publication.arrivalDate,
                        weight: publication.weight,
                        volume: publication.volume,
                        price: publication.price,
                        image: publication.image
                    }
                }))
    
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
                            image: publication.image
                        }
                    }))
                    setIsLoading(false);
                })
    
            })
        })
    }, [activePage, expiredPage])

    return(
        <Tabs type="line">
            <Tabs.TabPane tab="Active Publications" key="1">
                <Skeleton loading={isLoading}>
                    {activeTrips.length == 0 ? 
                        <div className="w-100 flex-center flex-column">
                            <Title level={3}>You have no active publications</Title>
                            <Button type="primary" onClick={() => navigate('/trips')}>Create Publication</Button>
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
                                <Pagination className="text-center mt-2vh" onChange={(page) => setActivePage(page)} defaultCurrent={1} total={50}/>
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
                                <Pagination className="text-center mt-2vh" onChange={(page) => setExpiredPage(page)} defaultCurrent={1} total={50}/>
                            </Row>
                        </div>
                    }
                </Skeleton>
            </Tabs.TabPane>
        </Tabs>
    )
}

export default MyPublications;