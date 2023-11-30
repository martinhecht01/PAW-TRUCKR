import { Avatar, Button, Card, Col, Divider, Rate, Row, Tabs, Typography } from "antd";
import '../styles/main.scss';
import { ArrowRightOutlined } from "@ant-design/icons";

const {Text, Title} = Typography;

const MyItinerary: React.FC = () => {

    const ongoingTrips: {origin: string, destination: string, departure: Date, arrival: Date, url: string, name: string, role: string, rating: number}[] = [];
    
    ongoingTrips.push({origin: 'Helsinki', destination: 'Tampere', departure: new Date(), arrival: new Date(), url: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNHkjak0XBtavkkM8z1vJo-BMjmjxfOEaU7pyFAGDc&s', name: 'John Doe', role: 'Driver', rating: 4.5})
    ongoingTrips.push({origin: 'Helsinki', destination: 'Tampere', departure: new Date(), arrival: new Date(), url: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNHkjak0XBtavkkM8z1vJo-BMjmjxfOEaU7pyFAGDc&s', name: 'John Doe', role: 'Driver', rating: 4.5})
    ongoingTrips.push({origin: 'Helsinki', destination: 'Tampere', departure: new Date(), arrival: new Date(), url: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNHkjak0XBtavkkM8z1vJo-BMjmjxfOEaU7pyFAGDc&s', name: 'John Doe', role: 'Driver', rating: 4.5})
    ongoingTrips.push({origin: 'Helsinki', destination: 'Tampere', departure: new Date(), arrival: new Date(), url: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNHkjak0XBtavkkM8z1vJo-BMjmjxfOEaU7pyFAGDc&s', name: 'John Doe', role: 'Driver', rating: 4.5})
    ongoingTrips.push({origin: 'Helsinki', destination: 'Tampere', departure: new Date(), arrival: new Date(), url: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNHkjak0XBtavkkM8z1vJo-BMjmjxfOEaU7pyFAGDc&s', name: 'John Doe', role: 'Driver', rating: 4.5})
    
    return(
        <Tabs type="line" tabBarExtraContent={{right: <Button type="link">View Past Trips</Button>}}>
            <Tabs.TabPane tab="Ongoing Trips" key="1">
                <Row className="w-100 flex-center">
                    <Col span={15}>
                        {ongoingTrips.map((trip) => (
                            <Card className="w-100 mt-1vh">
                                <Row>
                                    <Col span={15}>
                                        <Row>
                                            <Col span={10} className="text-center">
                                                <Title level={4}>{trip.origin}</Title>
                                                <Text>{trip.departure.toDateString()}</Text>
                                            </Col>
                                            <Col span={4} className="h-100" style={{display: 'flex', height: 100}}>
                                                <ArrowRightOutlined style={{fontSize: 35, display: 'flex', margin: 'auto'}}/>
                                            </Col>
                                            <Col span={10} className="text-center">
                                                <Title level={4}>{trip.destination}</Title>
                                                <Text>{trip.arrival.toDateString()}</Text>
                                            </Col>
                                        </Row>
                                    </Col>
                                    <Col span={8}>
                                        <div className="w-100 flex-center space-evenly h-100">
                                            <div className="text-center">
                                                <Title level={4} style={{margin:0}}>{trip.name}</Title>
                                                <Rate disabled defaultValue={trip.rating} allowHalf/> 
                                            </div>
                                            <div>
                                                <Avatar src={trip.url} size={80}></Avatar>
                                            </div>
                                        </div>
                                    </Col>
                                </Row>
                            </Card> 
                        ))}
                        
                    </Col>
                </Row>
            </Tabs.TabPane>
            <Tabs.TabPane tab="Future Trips" key="2">
                Content of Tab Pane 2
            </Tabs.TabPane>
        </Tabs>
    )
}

export default MyItinerary;