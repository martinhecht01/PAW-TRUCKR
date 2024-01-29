import { Avatar, Badge, Button, Card, Col, Divider, Image, InputNumber, Row, Skeleton, Slider, Table, Typography, message } from "antd";
import '../styles/main.scss'
import { ArrowRightOutlined, EnvironmentOutlined, StarFilled } from "@ant-design/icons";
import TextArea from "antd/es/input/TextArea";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Publication } from "../models/Publication";
import { getPublicationById } from "../api/tripApi";
import { getClaims, getToken, getUserByUrl } from "../api/userApi";
import { User } from "../models/User";
import { createOffer } from "../api/offerApi";
import { Offer } from "../models/Offer";

const {Title, Text} = Typography;

const PublicationDetails: React.FC = () => {

    const {tripId} = useParams();
    const [publication, setPublication] = useState<Publication>();
    const [user, setUser] = useState<User>();
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [description, setDescription] = useState<string>('');

    const router = useNavigate()

    useEffect(() => {

        getPublicationById(tripId!).then((publication) => {
            setPublication(publication);
            setInputValue(publication.price);
            getUserByUrl(publication.creator).then((user) => {
                setUser(user);
                setIsLoading(false);
            })
        }).catch((err) => {
            router('/404')
        })
    }, []);

    const [inputValue, setInputValue] = useState(1);

    const onChange = (newValue: number | null) => {
      setInputValue(newValue ? newValue : 0);
    };

    async function sendOffer(){
        const claims = getClaims();

        if(claims == null){
            router('/login')
        }

        getUserByUrl(claims!.userURL).then((user) => {
            createOffer(Number.parseInt(tripId!), inputValue, description ).then((offer) => {
                message.success('Offer sent successfully')
            }
        )
    })
        
    }

    const formatter = (value: number | undefined) => `$${value}`;

    return (
            <div className="w-100 flex-center">
            <Row style={{justifyContent: 'space-evenly'}} className="w-80">
                <Skeleton loading={isLoading}>
                    <Col span={10}>
                        <div>
                            <Badge.Ribbon text={<Title level={5} style={{color: 'white', margin: 3}}>{publication?.type}</Title>}  color="blue">
                                        <img
                                        src={publication?.image}
                                        style={{width: '100%', height: '100%', objectFit: 'cover', overflow: 'hidden'}}
                                        alt="A giant squid swimming deep in the sea"
                                        />
                            </Badge.Ribbon>
                        </div>
                        <Card className="mt-5">
                            <Row className="space-evenly">
                                <Col span={8} className="text-center">
                                    <Title level={4}>{publication?.origin}</Title>
                                    <Text>{publication?.departureDate ? new Date(publication.departureDate).toDateString() : ''}</Text>
                                </Col>
                                <Col span={8} className="flex-center flex-column">
                                    <div className="w-100 flex-center"><Image height={45} preview={false} src="https://i.pinimg.com/originals/e0/8b/14/e08b1415885d4d2ddd7fd3f75967da29.png"></Image></div>
                                    <div className="w-100 flex-center">
                                        <div style={{border: '1px solid black', width: '100%'}}/><ArrowRightOutlined/>
                                    </div>                            
                                </Col>
                                <Col span={8}  className="text-center">
                                    <Title level={4}>{publication?.destination}</Title>
                                    <Text>{publication?.arrivalDate ? new Date(publication.arrivalDate).toDateString() : ''}</Text>
                                </Col>
                            </Row>
                        </Card>
                        <Row className="mt-5 space-between">
                            <Col span={11}>
                                <Card className="w-100 text-center">
                                    <Title level={3}>{publication?.weight} Kg</Title>
                                    <Text>Weight</Text>
                                </Card>
                            </Col>
                            <Col span={11}>
                                <Card className="w-100 text-center">
                                    <Title level={3}>{publication?.volume} M3</Title>
                                    <Text>Volume</Text>
                                </Card>
                            </Col>
                        </Row>
                    </Col>
                    <Col span={8}>
                        <Card>
                            <div className="w-100 space-between">
                                <Avatar size={64} src={user?.imageUrl}></Avatar>
                                <Title level={3}>{user?.name}</Title>
                                <div>
                                    <Title level={4}><StarFilled/>{user?.rating == 0 ? '-' : user?.rating }</Title>
                                </div>
                            </div>
                        </Card>
                        <Card className="mt-5">
                            <Title level={5}>Description</Title>
                            <TextArea rows={4} className="w-100 mt-5" onChange={(value) => setDescription(value.target.value)}></TextArea>
                            <Row className="mt-5">
                                <Col span={16}>
                                    <Slider
                                    min={1}
                                    max={100000}
                                    onChange={onChange}
                                    tooltipVisible={false}
                                    value={typeof inputValue === 'number' ? inputValue : 0}
                                    />
                                </Col>
                                <Col span={4}>
                                    <InputNumber
                                    min={1}
                                    max={100000}
                                    style={{ margin: '0 16px' }}
                                    value={inputValue}
                                    prefix={'$'}
                                    onChange={onChange}
                                    />
                                </Col>
                            </Row>
                            <Button className="mt-5" type="primary" block onClick={sendOffer}>Send Offer</Button>
                        </Card>
                    </Col>
                </Skeleton>
            </Row>
        </div>
    );
}

export default PublicationDetails;