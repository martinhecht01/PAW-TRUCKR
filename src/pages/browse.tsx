import { Button, Card, Col, DatePicker, Divider, Grid, Input, Pagination, Row, Select, Slider, Switch, Typography } from "antd"
import { useState } from "react";
import TripCard, { TripCardProps } from "../components/tripCard";
import CloseOutlined from '@ant-design/icons/CloseOutlined';
const {Text, Title} = Typography;

const formatter = (value: number) => `$${value}`;

const {RangePicker} = DatePicker;

const BrowseTrips: React.FC = () => {
    const [trips, setTris] = useState(Array<TripCardProps>());
    
    trips.push({type: 'trip', from: 'Helsinki', to: 'Tampere', fromDate: new Date(), toDate: new Date(), weight: 1000, volume: 1000, price: 1000, image: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNHkjak0XBtavkkM8z1vJo-BMjmjxfOEaU7pyFAGDc&s'})
    trips.push({type: 'trip', from: 'Helsinki', to: 'Tampere', fromDate: new Date(), toDate: new Date(), weight: 1000, volume: 1000, price: 1000, image: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNHkjak0XBtavkkM8z1vJo-BMjmjxfOEaU7pyFAGDc&s'})
    trips.push({type: 'trip', from: 'Helsinki', to: 'Tampere', fromDate: new Date(), toDate: new Date(), weight: 1000, volume: 1000, price: 1000, image: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNHkjak0XBtavkkM8z1vJo-BMjmjxfOEaU7pyFAGDc&s'})
    trips.push({type: 'trip', from: 'Helsinki', to: 'Tampere', fromDate: new Date(), toDate: new Date(), weight: 1000, volume: 1000, price: 1000, image: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNHkjak0XBtavkkM8z1vJo-BMjmjxfOEaU7pyFAGDc&s'})
    trips.push({type: 'trip', from: 'Helsinki', to: 'Tampere', fromDate: new Date(), toDate: new Date(), weight: 1000, volume: 1000, price: 1000, image: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNHkjak0XBtavkkM8z1vJo-BMjmjxfOEaU7pyFAGDc&s'})
    trips.push({type: 'trip', from: 'Helsinki', to: 'Tampere', fromDate: new Date(), toDate: new Date(), weight: 1000, volume: 1000, price: 1000, image: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNHkjak0XBtavkkM8z1vJo-BMjmjxfOEaU7pyFAGDc&s'})


    return (
        <Row>
            <Col xxl={4} xl={4} lg={8} md={24} sm={24} xs={24}>
                <Card style={{margin: 25}}>
                    <Title level={3} style={{marginTop: 0}}>Filters</Title>
                    <Divider></Divider>
                    <Text>Origin:</Text>
                    <Select placeholder="-" style={{width: '100%'}}></Select>
                    <div style={{margin: 10}}></div>
                    <Text>Destination:</Text>
                    <Select placeholder="-" style={{width: '100%'}}></Select>
                    <div style={{margin: 10}}></div>
                    <Text>Weight:</Text>
                    <Input type="number" placeholder="-" min={0}></Input>
                    <div style={{margin: 10}}></div>
                    <Text>Volume:</Text>
                    <Input type="number" placeholder="-" min={0}></Input>
                    <div style={{margin: 10}}></div>
                    <Text>Price:</Text>
                    <Slider range min={0} max={1000000} tooltip={{formatter}}></Slider>
                    <div style={{margin: 10}}></div>
                    <Text>Cargo type:</Text>
                    <Select placeholder="-" style={{width: '100%'}}></Select>
                    <div style={{margin: 10}}></div>
                    <Text>Date Range</Text>
                    <RangePicker></RangePicker>
                    <div style={{margin: 10}}></div>
                    <Text>Sort by:</Text>
                    <Select placeholder="-" style={{width: '100%'}}></Select>                                        
                    <div style={{margin: 10}}></div>
                    <div>
                        <Button type="primary" style={{width: '72%', marginRight: '3%'}}>Apply</Button>
                        <Button type="dashed" style={{width: '25%'}}><CloseOutlined /></Button>
                    </div>
                    
                </Card>
            </Col>
            <Col xxl={20} xl={20} lg={16} md={24} sm={24} xs={24}>
                <div style={{display: "flex", flexDirection: 'column'}}>
                    <Row gutter={15}>
                        {trips.map((trip, index) => (
                            <Col xxl={5} xl={8} lg={12} md={12} sm={22} xs={22} key={index}>
                                <TripCard {...trip}></TripCard>
                            </Col>
                        )
                        )}
                    </Row>
                    <Pagination style={{marginTop: '2vh', textAlign: 'center'}} defaultCurrent={1} total={50} />
                </div>
            </Col>
                            
            
        </Row>
        
        

    )
}

export default BrowseTrips