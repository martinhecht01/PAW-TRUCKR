import React from 'react';
import {Avatar, Button, Card, Typography} from 'antd';
import '../styles/main.scss';
import '../styles/profile.scss';
import {useTranslation} from "react-i18next";
import {StarFilled, UserOutlined} from "@ant-design/icons";
import ReviewContainer from '../components/reviewContainer';

const { Title, Text } = Typography;

const profile: React.FC = () => {

    const {t} = useTranslation();

    const [, setContainer] = React.useState<HTMLDivElement | null>(null);

    const cardTitle = ' 4.5 - (3 ' + t("review.reviews") + ")";
    //TODO: get user data from backend

    return (
        <div>
        <div className="flex-center">
            <Card style={{width:'30%',margin:'3vh'}} headStyle={{fontSize:'3vh', color:'#142d4c'}} title={t("profile.profile")}>
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
            <Card headStyle={{fontSize:'3vh', color:'#142d4c'}} title={t("profile.completedTrips")}>
                <Text>0</Text>
            </Card>
        </div>
        <div className='flex-center'>
            <Card headStyle={{fontSize:'2.5vh', color:'#142d4c'}} style={{width:'60%'}} title={
                <div style={{justifyContent:'center',alignItems:'center', justifyItems:'center'}}>
                    <StarFilled></StarFilled>
                    {cardTitle}
                </div>
            }>
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
        </div>
        </div>
    );
};

export default profile;