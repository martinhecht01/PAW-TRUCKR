import { Avatar, Badge, Button, Card, Col, Divider, Image, InputNumber, Row, Slider, Table, Typography } from "antd";
import '../styles/main.scss'
import { ArrowRightOutlined, EnvironmentOutlined, StarFilled } from "@ant-design/icons";
import TextArea from "antd/es/input/TextArea";
import { useState } from "react";

const {Title, Text} = Typography;

const PublicationDetails: React.FC = () => {

    const departureDate = new Date();
    const arrivalDate = new Date();

    const [inputValue, setInputValue] = useState(5000);

    const onChange = (newValue: number | null) => {
      setInputValue(newValue ? newValue : 0);
    };

    const formatter = (value: number | undefined) => `$${value}`;

    return (
            <div className="w-100 flex-center">
            <Row style={{justifyContent: 'space-evenly'}} className="w-80">
                <Col span={10}>
                    <div>
                        <Badge.Ribbon text={<Title level={5} style={{color: 'white', margin: 3}}>Refrigated</Title>}  color="blue">
                                    <img
                                    src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNHkjak0XBtavkkM8z1vJo-BMjmjxfOEaU7pyFAGDc&s"
                                    style={{width: '100%', height: '100%', objectFit: 'cover', overflow: 'hidden'}}
                                    alt="A giant squid swimming deep in the sea"
                                    />
                        </Badge.Ribbon>
                    </div>
                    <Card className="mt-5">
                        <Row className="space-evenly">
                            <Col span={8} className="text-center">
                                <Title level={4}>Helsinki</Title>
                                <Text>{departureDate.toLocaleDateString()}</Text>
                            </Col>
                            <Col span={8} className="flex-center flex-column">
                                <div className="w-100 flex-center"><Image height={45} preview={false} src="https://i.pinimg.com/originals/e0/8b/14/e08b1415885d4d2ddd7fd3f75967da29.png"></Image></div>
                                <div className="w-100 flex-center">
                                    <div style={{border: '1px solid black', width: '100%'}}/><ArrowRightOutlined/>
                                </div>                            
                            </Col>
                            <Col span={8}  className="text-center">
                                <Title level={4}>Tampere</Title>
                                <Text>{arrivalDate.toLocaleDateString()}</Text>
                            </Col>
                        </Row>
                    </Card>
                    <Row className="mt-5 space-between">
                        <Col span={11}>
                            <Card className="w-100 text-center">
                                <Title level={3}>100kg</Title>
                                <Text>Weight</Text>
                            </Card>
                        </Col>
                        <Col span={11}>
                            <Card className="w-100 text-center">
                                <Title level={3}>100m3</Title>
                                <Text>Volume</Text>
                            </Card>
                        </Col>
                    </Row>
                </Col>
                <Col span={8}>
                    <Card>
                        <div className="w-100 space-between">
                            <Avatar size={64} src="https://www.pngitem.com/pimgs/m/146-1468479_my-profile-icon-blank-profile-picture-circle-hd.png"></Avatar>
                            <Title level={3}>John Doe</Title>
                            <div>
                                <Title level={4}><StarFilled/> 4.5 (100)</Title>
                            </div>
                        </div>
                    </Card>
                    <Card className="mt-5">
                        <Title level={5}>Description</Title>
                        <TextArea rows={4} className="w-100 mt-5"></TextArea>
                        <Row className="mt-5">
                            <Col span={16}>
                                <Slider
                                min={1}
                                max={10000}
                                onChange={onChange}
                                tooltipVisible={false}
                                value={typeof inputValue === 'number' ? inputValue : 0}
                                />
                            </Col>
                            <Col span={4}>
                                <InputNumber
                                min={1}
                                max={10000}
                                style={{ margin: '0 16px' }}
                                value={inputValue}
                                prefix={'$'}
                                onChange={onChange}
                                />
                            </Col>
                        </Row>
                        <Button className="mt-5" type="primary" block>Send Offer</Button>
                    </Card>
                </Col>
            </Row>
        </div>
    );
}

export default PublicationDetails;