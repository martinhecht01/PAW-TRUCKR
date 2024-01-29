import { Button, Col, Pagination, Row, Tabs } from "antd";
import '../styles/main.scss';
import { useNavigate } from "react-router-dom";
import ItineraryTripCard, { ItineraryTripCardProps } from "../Components/itineraryTripCard";

const MyItinerary: React.FC = () => {

    const navigate = useNavigate();

    const ongoingTrips: ItineraryTripCardProps[] = [];
    
    ongoingTrips.push({origin: 'Helsinki', destination: 'Tampere', departure: new Date(), arrival: new Date(), url: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNHkjak0XBtavkkM8z1vJo-BMjmjxfOEaU7pyFAGDc&s', name: 'John Doe', role: 'Driver', rating: 4.5})
    ongoingTrips.push({origin: 'Helsinki', destination: 'Tampere', departure: new Date(), arrival: new Date(), url: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNHkjak0XBtavkkM8z1vJo-BMjmjxfOEaU7pyFAGDc&s', name: 'John Doe', role: 'Driver', rating: 4.5})
    ongoingTrips.push({origin: 'Helsinki', destination: 'Tampere', departure: new Date(), arrival: new Date(), url: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNHkjak0XBtavkkM8z1vJo-BMjmjxfOEaU7pyFAGDc&s', name: 'John Doe', role: 'Driver', rating: 4.5})
    ongoingTrips.push({origin: 'Helsinki', destination: 'Tampere', departure: new Date(), arrival: new Date(), url: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNHkjak0XBtavkkM8z1vJo-BMjmjxfOEaU7pyFAGDc&s', name: 'John Doe', role: 'Driver', rating: 4.5})
    ongoingTrips.push({origin: 'Helsinki', destination: 'Tampere', departure: new Date(), arrival: new Date(), url: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNHkjak0XBtavkkM8z1vJo-BMjmjxfOEaU7pyFAGDc&s', name: 'John Doe', role: 'Driver', rating: 4.5})
    

    
    const futureTrips: ItineraryTripCardProps[] = [];
    
    futureTrips.push({origin: 'Helsinki', destination: 'Tampere', departure: new Date(), arrival: new Date(), url: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNHkjak0XBtavkkM8z1vJo-BMjmjxfOEaU7pyFAGDc&s', name: 'John Doe', role: 'Driver', rating: 4.5})
    futureTrips.push({origin: 'Helsinki', destination: 'Tampere', departure: new Date(), arrival: new Date(), url: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNHkjak0XBtavkkM8z1vJo-BMjmjxfOEaU7pyFAGDc&s', name: 'John Doe', role: 'Driver', rating: 4.5})
    futureTrips.push({origin: 'Helsinki', destination: 'Tampere', departure: new Date(), arrival: new Date(), url: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNHkjak0XBtavkkM8z1vJo-BMjmjxfOEaU7pyFAGDc&s', name: 'John Doe', role: 'Driver', rating: 4.5})

    return(
        <Tabs type="line" tabBarExtraContent={{right: <Button type="link" onClick={() => navigate('/pastTrips')}>View Past Trips</Button>}}>
            <Tabs.TabPane tab="Ongoing Trips" key="1">
                <Row className="w-100 flex-center">
                    <Col span={15}>
                        {ongoingTrips.map((trip) => (
                            <ItineraryTripCard {...trip}/>
                        ))}
                        
                    </Col>
                </Row>
                <Row className="w-100 flex-center">
                    <Pagination className="text-center mt-2vh" defaultCurrent={1} total={50}/>
                </Row>
            </Tabs.TabPane>
            <Tabs.TabPane tab="Future Trips" key="2">
                <Row className="w-100 flex-center">
                    <Col span={15}>
                        {futureTrips.map((trip) => (
                            <ItineraryTripCard {...trip}/>
                        ))}
                        
                    </Col>
                </Row>
                <Row className="w-100 flex-center">
                    <Pagination className="text-center mt-2vh" defaultCurrent={1} total={50}/>
                </Row>
            </Tabs.TabPane>
        </Tabs>
    )
}

export default MyItinerary;