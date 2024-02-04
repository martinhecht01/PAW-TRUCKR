import React, { useEffect, useState } from 'react';
import {Avatar, Button, Card, Col, Pagination, Row, Skeleton, Typography} from 'antd';
import '../styles/main.scss';
import '../styles/profile.scss';
import {useTranslation} from "react-i18next";
import {StarFilled, UserOutlined} from "@ant-design/icons";
import ReviewContainer from '../Components/reviewContainer';
import {getClaims, getUserById, getUserByUrl} from "../api/userApi";
import {User} from "../models/User";
import {getReviewsByUser} from "../api/reviewApi";
import { Review } from '../models/Review';
import NotFound404 from './404';
import { useNavigate, useParams } from 'react-router-dom';


const { Title, Text } = Typography;

const Profile: React.FC = () => {

    const {t} = useTranslation();

    const [ user, setUser] = useState<User>();
    const [ reviews, setReviews] = useState<Review[]>([]);
    const [ completedTrips, setCompletedTrips] = useState<number>(0);
    const [isLoading, setIsLoading] = useState<boolean>(true);
    
    const [page, setPage] = useState<number>(1);
    const [pageSize] = useState<number>(4);
    const [maxPage, setMaxPage] = useState<number>(0);
    const [loadingReviews, setLoadingReviews] = useState<boolean>(true);

    const {userId} = useParams();

    const router = useNavigate();

    useEffect(() => {

        setLoadingReviews(true);
        const claims = getClaims();

        if (claims === null && userId === undefined){
            router('/login');
            return;
        }

        userId ? getUserById(userId).then((user) => {

            setUser(user);
            setCompletedTrips(user.completedTripsCount);
        
            getReviewsByUser(user.id, page.toString(), pageSize.toString()).then((reviews) => {
                setReviews(reviews);
                if(reviews.length > 0)
                    setMaxPage(reviews[0].maxPage ? Number.parseInt(reviews[0].maxPage) : 0);
                setIsLoading(false);
            })

            setIsLoading(false);
            setLoadingReviews(false);
        })

        :

        getUserByUrl(claims!.userURL).then((user) => {
            setUser(user);
            setCompletedTrips(user.completedTripsCount);
        
            getReviewsByUser(user.id, page.toString(), pageSize.toString()).then((reviews) => {
                setReviews(reviews);
                if(reviews.length > 0)
                    setMaxPage(reviews[0].maxPage ? Number.parseInt(reviews[0].maxPage) : 0);
                setIsLoading(false);
            })

            setIsLoading(false);
            setLoadingReviews(false);
        })

    }, [page])
    
    if( isLoading ){
        return (
            <Row className='w-100 space-evenly'>
                <Skeleton loading={isLoading} active avatar paragraph={{ rows: 4 }}/>
            </Row>
        )
    }
    
    if( user != null && user != undefined ){
        return (
            <div>
                <div className="space-evenly">
                    <Row className='w-80 space-evenly' style={{alignItems: 'start'}}>
                        <Col span={12}>
                            <Card className='w-100' title={<Title level={3}>{t("profile.profile")}</Title>}>
                                <div className='flex-center'>
                                    <Avatar size={124} icon={<UserOutlined/>} src={user!.imageUrl}/>
                                </div>
                                <Title level={5}>{t("profile.name")}</Title>
                                <Text>{user!.name}</Text>
                                <Title level={5}>{t('profile.cuit')}</Title>
                                <Text>{user!.cuit}</Text>
                                {/* <Title level={5}>{t('profile.email')}</Title>
                                <Text>{user!.email}</Text> */}
                                { !userId ?
                                <Button style={{width: '100%', marginTop: '5vh'}} type='primary' onClick={() => router('./edit')}>{t("profile.editProfile")}</Button>
                                : null
                                }
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
                            <Card>
                                <Skeleton loading={loadingReviews}>
                                    {
                                        reviews && reviews.length > 0 ?  
                                            <Title level={3}>
                                                <StarFilled /> 
                                                {" "}{new Number(user.rating).toFixed(1)}
                                            </Title>
                                        : <Title level={3}>No reviews yet</Title>
                                    }
                                    { reviews && reviews.length > 0 ?
                                        <div>
                                            {reviews.map((review) => (
                                                // Each item in the array is mapped to a JSX element
                                                <ReviewContainer avgRating={review.rating} comment={review.review}></ReviewContainer>
                                            ))}
                                            <div className='w-100 flex-center mt-2vh'>
                                                <Pagination current={page} total={maxPage*pageSize} pageSize={pageSize} onChange={(page) => setPage(page)} />
                                            </div>
                                        </div>
                                        : <></>
                                    }
                                </Skeleton>
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