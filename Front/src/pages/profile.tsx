import React, { useEffect, useState } from 'react';
import {Avatar, Button, Card, Col, Row, Typography} from 'antd';
import '../styles/main.scss';
import '../styles/profile.scss';
import {useTranslation} from "react-i18next";
import {StarFilled, UserOutlined} from "@ant-design/icons";
import ReviewContainer from '../Components/reviewContainer';
import {getUserById} from "../api/userApi";
import {User} from "../models/User";
import {getReview, getReviewsByUser} from "../api/reviewApi";
import { Review } from '../models/Review';
import NotFound404 from './404';


const { Title, Text } = Typography;

const Profile: React.FC = () => {

    const {t} = useTranslation();

    const [ user, setUser] = useState<User>();
    const [ reviews, setReviews] = useState<Review[]>([]);
    const [ completedTrips, setCompletedTrips] = useState<number>(0);

    useEffect(() => {
        getUserById(2).then((user) => {
            setUser(user);
            if (user.role == 'PROVIDER'){
                setCompletedTrips(user.providerTrips ? user.providerTrips.length : 0);
            }
            else{
                setCompletedTrips(user.truckerTrips ? user.truckerTrips.length : 0);
            }
        
        })

        getReviewsByUser(2).then((reviews) => {
            setReviews(reviews);
        })
    }, [])
    
    if( user != null && user != undefined ){
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
                                <Text>{user!.name}</Text>
                                <Title level={5}>{t('profile.cuit')}</Title>
                                <Text>{user!.cuit}</Text>
                                {/* <Title level={5}>{t('profile.email')}</Title>
                                <Text>{user!.email}</Text> */}
                                <Button style={{width: '100%', marginTop: '5vh'}}
                                        type='primary'>{t("profile.editProfile")}</Button>
                            </Card>
                        </Col>
                        <Col span={7}>
                            <Card title={<Title level={3}>{t("profile.completedTrips")}</Title>}>
                                <div className='w-100 text-center'>
                                    <Title level={3}>{completedTrips}</Title>
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
                                    reviews && reviews.length > 0 ?  
                                        <Title level={3}>
                                            <StarFilled /> 
                                            {" "}{reviews.reduce((a, b) => a + b.rating, 0) / reviews.length} ({reviews.length} review{reviews.length !== 1 ? 's' : ''})
                                        </Title>
                                    : <Title level={3}>No reviews yet</Title>
                                }
                            >
                                { reviews && reviews.length > 0 ?
                                    <div className='reviewsContainerStyle'>
                                        {reviews.map((review, index) => (
                                            // Each item in the array is mapped to a JSX element
                                            <ReviewContainer avgRating={review.rating} comment={review.review}></ReviewContainer>
                                        ))}
                                    </div>
                                    : <></>
                                 }
                            </Card>
                        </Col>
                    </Row>
                </div>
            </div>
        );
    } else{
        return(<NotFound404></NotFound404>)
    }
};

export default Profile;