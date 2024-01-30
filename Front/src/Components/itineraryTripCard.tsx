import { ArrowRightOutlined } from "@ant-design/icons";
import { Avatar, Card, Col, Rate, Row, Typography } from "antd"

const {Title, Text} = Typography;

export type ItineraryTripCardProps = {
    origin: string,
    destination: string, 
    departure: Date,
    arrival: Date,
    userUrl: string,
    name: string,
    role: string,
    rating: number,
    id: number
}

const ItineraryTripCard = (trip: ItineraryTripCardProps) => {

    return (
        <Card className="w-100 mt-1vh">
            <Row>
                <Col span={15}>
                    <Row>
                        <Col span={10} className="text-center">
                            <Title level={4}>{trip.origin}</Title>
                            <Text>{(new Date(trip.departure.toString())).toDateString()}</Text>
                        </Col>
                        <Col span={4} className="h-100" style={{display: 'flex', height: 100}}>
                            <ArrowRightOutlined style={{fontSize: 35, display: 'flex', margin: 'auto'}}/>
                        </Col>
                        <Col span={10} className="text-center">
                            <Title level={4}>{trip.destination}</Title>
                            <Text>{(new Date(trip.arrival.toString())).toDateString()}</Text>
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
                            <Avatar src={trip.userUrl} size={80}></Avatar>
                        </div>
                    </div>
                </Col>
            </Row>
        </Card> 
    )
}

export default ItineraryTripCard;