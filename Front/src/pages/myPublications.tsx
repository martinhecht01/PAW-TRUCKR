import { Button, Col, Pagination, Row, Tabs, Typography } from "antd";
import '../styles/main.scss';
import { useNavigate } from "react-router-dom";
import TripCard, { TripCardProps } from "../components/tripCard";
import { useEffect, useState } from "react";

const {Title, Text} = Typography;

const MyPublications: React.FC = () => {

    const navigate = useNavigate();

    const [trips, setTrips] = useState(Array<TripCardProps>());
    
    useEffect(() => {
        setTrips(
            [
                {type: 'trip', from: 'Helsinki', to: 'Tampere', fromDate: new Date(), toDate: new Date(), weight: 1000, volume: 1000, price: 1000, image: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNHkjak0XBtavkkM8z1vJo-BMjmjxfOEaU7pyFAGDc&s'},
                {type: 'trip', from: 'Helsinki', to: 'Tampere', fromDate: new Date(), toDate: new Date(), weight: 1000, volume: 1000, price: 1000, image: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNHkjak0XBtavkkM8z1vJo-BMjmjxfOEaU7pyFAGDc&s'},
                {type: 'trip', from: 'Helsinki', to: 'Tampere', fromDate: new Date(), toDate: new Date(), weight: 1000, volume: 1000, price: 1000, image: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNHkjak0XBtavkkM8z1vJo-BMjmjxfOEaU7pyFAGDc&s'},
                {type: 'trip', from: 'Helsinki', to: 'Tampere', fromDate: new Date(), toDate: new Date(), weight: 1000, volume: 1000, price: 1000, image: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNHkjak0XBtavkkM8z1vJo-BMjmjxfOEaU7pyFAGDc&s'},
                {type: 'trip', from: 'Helsinki', to: 'Tampere', fromDate: new Date(), toDate: new Date(), weight: 1000, volume: 1000, price: 1000, image: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNHkjak0XBtavkkM8z1vJo-BMjmjxfOEaU7pyFAGDc&s'},
            ]
        )
        
    }, [])

    return(
        <Tabs type="line" tabBarExtraContent={{right: <Button type="link" onClick={() => navigate('/pastTrips')}>View Past Trips</Button>}}>
            <Tabs.TabPane tab="Active Publications" key="1">
                {trips.length == 0 ? 
                    <div className="w-100 flex-center flex-column">
                        <Title level={3}>You have no active publications</Title>
                        <Button type="primary" onClick={() => navigate('/trips')}>Create Publication</Button>
                    </div>
                             
                            
                : 
                    <div className="display-flex flex-column w-100" >
                        <div style={{display: "flex", flexDirection: 'column'}}>
                            <Row gutter={15}>
                                {trips.map((trip, index) => (
                                    <Col xxl={6} xl={8} lg={12} md={12} sm={22} xs={22} key={index}>
                                        <TripCard {...trip}></TripCard>
                                    </Col>
                                )
                                )}
                            </Row>
                        </div>
                        <Row className="w-100 flex-center">
                            <Pagination className="text-center mt-2vh" defaultCurrent={1} total={50}/>
                        </Row>
                    </div>
                }
            </Tabs.TabPane>
            <Tabs.TabPane tab="Expired Publications" key="2">
                {trips.length == 0 ? 
                        <div className="w-100 flex-center flex-column">
                            <Title level={3}>You have no expired publications</Title>
                        </div>
                                
                                
                    : 
                    <div className="display-flex flex-column w-100" >
                        <div style={{display: "flex", flexDirection: 'column'}}>
                            <Row gutter={15}>
                                {trips.map((trip, index) => (
                                    <Col xxl={6} xl={8} lg={12} md={12} sm={22} xs={22} key={index}>
                                        <TripCard {...trip}></TripCard>
                                    </Col>
                                )
                                )}
                            </Row>
                        </div>
                        <Row className="w-100 flex-center">
                            <Pagination className="text-center mt-2vh" defaultCurrent={1} total={50}/>
                        </Row>
                    </div>
                }
            </Tabs.TabPane>
        </Tabs>
    )
}

export default MyPublications;