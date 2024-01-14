import React from 'react';
import {Avatar, Button, Card, Col, Row, Typography} from 'antd';
import '../styles/main.scss';
import '../styles/profile.scss';
import {useTranslation} from "react-i18next";
import {StarFilled, UserOutlined} from "@ant-design/icons";
import ReviewContainer from '../Components/reviewContainer';

const { Title, Text } = Typography;

const profile: React.FC = () => {

    const {t} = useTranslation();

    const [, setContainer] = React.useState<HTMLDivElement | null>(null);

    const cardTitle = ' 4.5 - (3 ' + t("review.reviews") + ")";
    //TODO: get user data from backend

    return (
        <div>
            <div className="space-evenly">
                <Row className='w-80 space-evenly' style={{alignItems: 'start'}}>
                    <Col span={12}>
                        <Card className='w-100' title={<Title level={3}>{t("profile.profile")}</Title>}>
                            <div className='flex-center'>
                                <Avatar size={124} icon={<UserOutlined />} />
                            </div>
                            <Title level={5}>{t("profile.name")}</Title>
                            <Text>dfkjdf</Text>
                            <Title level={5}>{t('profile.cuit')}</Title>
                            <Text>dfkjdf</Text>
                            <Title level={5}>{t('profile.email')}</Title>
                            <Text>dfkjdf</Text>
                            <Button style={{width:'100%', marginTop:'5vh'}} type='primary'>{t("profile.editProfile")}</Button>
                        </Card>
                    </Col>
                    <Col span={7}>
                        <Card title={<Title level={3}>{t("profile.completedTrips")}</Title>}>
                            <div className='w-100 text-center'> 
                                <Title level={3}>0</Title>
                            </div>
                        </Card>
                    </Col>
                </Row>
            </div>
            <div className='space-evenly'>
                <Row className='w-80 space-evenly mt-5' style={{alignItems: 'start'}}>
                    <Col span={20}>
                        <Card
                            title={
                                <Title level={3}><StarFilled></StarFilled>{cardTitle}</Title>
                            }
                        >
                            <div className='reviewsContainerStyle' ref={setContainer}>
                                <ReviewContainer avgRating={5} comment={'Muy Bueno'}></ReviewContainer>
                                <ReviewContainer avgRating={5} comment={'Muy Bueno'}></ReviewContainer>
                                <ReviewContainer avgRating={5} comment={'Muy Bueno'}></ReviewContainer>
                                <ReviewContainer avgRating={5} comment={'Muy Bueno'}></ReviewContainer>
                                <ReviewContainer avgRating={5} comment={'Muy Bueno'}></ReviewContainer>
                                <ReviewContainer avgRating={5} comment={'Muy Bueno'}></ReviewContainer>
                                <ReviewContainer avgRating={5} comment={'Muy Bueno'}></ReviewContainer>
                                <ReviewContainer avgRating={5} comment={'Muy Bueno'}></ReviewContainer>
                                <ReviewContainer avgRating={5} comment={'Muy Bueno'}></ReviewContainer>
                            </div>
                        </Card>
                    </Col>
                </Row>
            </div>
        </div>
    );
};

export default profile;