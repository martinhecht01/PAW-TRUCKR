import React from 'react';
import {Avatar, Button, Card, Col, Row, Typography} from 'antd';
import '../styles/main.scss';
import '../styles/profile.scss';
import {useTranslation} from "react-i18next";
import {StarFilled, UserOutlined} from "@ant-design/icons";
import ReviewContainer from '../Components/reviewContainer';
import {getUserById} from "../api/userApi";
import {User} from "../models/User";
import {getReview, getReviewsByUser} from "../api/reviewApi";


const { Title, Text } = Typography;

const profile: React.FC = async () => {

    const {t} = useTranslation();

    const [, setContainer] = React.useState<HTMLDivElement | null>(null);

    const user: User = await getUserById(6);
    //TODO: nombre, cuit y mail vienen por el endpoint de users. Despues me manda links de los trips que hizo y hacer array.len(). Tambien me manda links para los reviews que hizo y hacer array.len(). El rating es el promedio de los reviews que hizo.

    const n: number = user.reviews.length;
    let avg : number;

    for (let i = 0; i < n; i++) {
        const rat : number = (await getReview(user.reviews[i])).rating;
        avg += rat;
    }

    const reviews = await getReviewsByUser(user.id);

    avg = avg / n;


    let compTrips : number = 0;
    if (user.role == 'PROVIDER'){
        compTrips = user.providerTrips.length;
    }
    else{
        compTrips = user.truckerTrips.length;
    }

    const cardTitle = " " +avg+"- ("+n+" " + t("review.reviews") + ")";


    return (
        <div>
            <div className="space-evenly">
                <Row className='w-80 space-evenly' style={{alignItems: 'start'}}>
                    <Col span={12}>
                        <Card className='w-100' title={<Title level={3}>{t("profile.profile")}</Title>}>
                            <div className='flex-center'>
                                <Avatar size={124} icon={<UserOutlined/>}/>
                            </div>
                            <Title level={5}>{t("profile.name")}</Title>
                            <Text>{user.name}</Text>
                            <Title level={5}>{t('profile.cuit')}</Title>
                            <Text>{user.cuit}</Text>
                            <Title level={5}>{t('profile.email')}</Title>
                            <Text>{user.email}</Text>
                            <Button style={{width: '100%', marginTop: '5vh'}}
                                    type='primary'>{t("profile.editProfile")}</Button>
                        </Card>
                    </Col>
                    <Col span={7}>
                        <Card title={<Title level={3}>{t("profile.completedTrips")}</Title>}>
                            <div className='w-100 text-center'>
                                <Title level={3}>${compTrips}</Title>
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
                                {reviews.map((item, index) => (
                                    // Each item in the array is mapped to a JSX element
                                    <ReviewContainer avgRating={item.rating} comment={item.review}></ReviewContainer>
                                ))}
                                {/*<ReviewContainer avgRating={5} comment={'Muy Bueno'}></ReviewContainer>*/}
                                {/*<ReviewContainer avgRating={5} comment={'Muy Bueno'}></ReviewContainer>*/}
                                {/*<ReviewContainer avgRating={5} comment={'Muy Bueno'}></ReviewContainer>*/}
                                {/*<ReviewContainer avgRating={5} comment={'Muy Bueno'}></ReviewContainer>*/}
                                {/*<ReviewContainer avgRating={5} comment={'Muy Bueno'}></ReviewContainer>*/}
                                {/*<ReviewContainer avgRating={5} comment={'Muy Bueno'}></ReviewContainer>*/}
                                {/*<ReviewContainer avgRating={5} comment={'Muy Bueno'}></ReviewContainer>*/}
                                {/*<ReviewContainer avgRating={5} comment={'Muy Bueno'}></ReviewContainer>*/}
                            </div>
                        </Card>
                    </Col>
                </Row>
            </div>
        </div>
    );
};

export default profile;