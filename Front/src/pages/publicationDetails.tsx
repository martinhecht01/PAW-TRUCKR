import { Avatar, Badge, Button, Card, Col, Form, Image, Row, Skeleton, Slider, Typography, message } from "antd";
import '../styles/main.scss'
import { ArrowRightOutlined, StarFilled, UserOutlined } from "@ant-design/icons";
import TextArea from "antd/es/input/TextArea";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Publication } from "../models/Publication";
import { getPublicationById } from "../api/tripApi";
import { getClaims, getUserByUrl } from "../api/userApi";
import { User } from "../models/User";
import { createOffer } from "../api/offerApi";
import { useTranslation } from "react-i18next";

const {Title, Text} = Typography;

const formatter = (value: number | undefined) => `$${value}`;


const PublicationDetails: React.FC = () => {

    const {tripId} = useParams();
    const [publication, setPublication] = useState<Publication>();
    const [user, setUser] = useState<User>();
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [form] = Form.useForm();

    const router = useNavigate()

    const {t} = useTranslation();

    useEffect(() => {
        const claims = getClaims();
        getPublicationById(tripId!).then((publication) => {
            setPublication(publication);
            form.setFieldsValue({ inputValue: publication.price });
            getUserByUrl(publication.creator).then((user) => {
                if(claims && (user.role === claims.role)){
                    router('/404')
                    return;
                }
                setUser(user);
                setIsLoading(false);
            })
        }).catch(() => {
            router('/404')
        })
    }, []);

    const sendOffer = async (values: {
        inputValue: number,
        description: string
    }) => {
        const { inputValue, description } = values;
        const claims = getClaims();

        if(claims == null){
            router('/login')
            return;
        }

        getUserByUrl(claims!.userURL).then(() => {
            createOffer(Number.parseInt(tripId!), inputValue, description).then(() => {
                message.success('Offer sent successfully');
                router('/sentOffers');
            });
        });
    };

    return (
        <div className="w-100 flex-center">
            <Row style={{justifyContent: 'space-evenly'}} className="w-80">
                <Skeleton loading={isLoading}>
                    <Col span={10}>
                        <div>
                            <Badge.Ribbon text={<Title level={5} style={{color: 'white', margin: 3}}>{publication?.type}</Title>} color="blue">
                                <Image
                                    style={{ width: '100%',
                                    height: '450px',
                                    objectFit: 'cover',
                                    objectPosition: 'center center'}}
                                    alt="example"
                                    src={publication?.image}
                                    preview={false}
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
                                <Col span={8} className="text-center">
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
                        <Card onClick={() => router('/profile/' + user?.id)} hoverable>
                            <div className="w-100 space-between">
                                <Avatar size={64} src={user?.imageUrl} icon={<UserOutlined/>}></Avatar>
                                <Title level={3}>{user?.name}</Title>
                                <div>
                                    <Title level={4}><StarFilled/>{user?.rating === 0 ? '-' : user?.rating.toFixed(1) }</Title>
                                </div>
                            </div>
                        </Card>
                        <Card className="mt-2vh">
                            <Title level={4}>{t('common.sendOffer')}</Title>
                            <Form form={form} onFinish={sendOffer}>
                                <Form.Item
                                    name="description"
                                    rules={[
                                        { required: true, message: 'Please input your description!' },
                                        { min: 1, max: 250, message: 'Description must be between 1 and 250 characters' }
                                    ]}
                                >
                                    <TextArea rows={4} className="w-100 mt-5" placeholder="Enter your description"/>
                                </Form.Item>
                                <Form.Item
                                    name="inputValue"
                                    rules={[
                                        { required: true, message: 'Please input your offer!' },
                                        { type: 'number', min: 1, max: 1000000, message: 'Offer must be between $1 and $100000' }
                                    ]}
                                >
                                    <Row>
                                        <Slider
                                            min={1}
                                            max={100000}
                                            tipFormatter={formatter}
                                            tooltipVisible
                                            onChange={value => form.setFieldsValue({ inputValue: value })}
                                            className="w-100 mt-10"
                                        />
                                    </Row>
                                </Form.Item>
                                <Form.Item>
                                    <Button type="primary" htmlType="submit" block className="mt-5">{t('common.sendOffer')}</Button>
                                </Form.Item>
                            </Form>
                        </Card>
                    </Col>
                </Skeleton>

            </Row>
        </div>
    );
}

export default PublicationDetails;
