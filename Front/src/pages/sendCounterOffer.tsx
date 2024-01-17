import React from 'react';
import {Typography, Row, Col, Card, Input, Button, Avatar, InputNumber} from 'antd';
import '../styles/main.scss';
import {useTranslation} from "react-i18next";

export type OriginalOfferProps = {
    description: string,
    offeredPrice: number,
    userPhoto: string,
    userName: string
}

const { Title, Text } =Typography;

const { TextArea } = Input;
const SendCounterOffer: React.FC = () => {

    const {t} = useTranslation();

    const props : OriginalOfferProps = {description:"uenas",offeredPrice:15,userPhoto:"http://t3.gstatic.com/images?q=tbn:ANd9GcSDOfreJh67Zm_asl0jKHzSciAEeqdvsmJ4off_OGDTwOORaTRSTEbaNuINJKXZTPHOTgjUcA",userName:"Julian Alvarez"};
    //TODO: get offer data from backend
    return (
        <div>
            <Row>
                <Col span={3}></Col>
                <Col span={8}>
                    <Card title="Counter-offer">
                        <Title className='m-0' level={5}>{t('manage.description')}</Title>
                        <TextArea
                            autoSize={{ minRows: 2 }}
                        />
                        <Title level={5}>{t('common.offeredPrice')}</Title>
                        <InputNumber className='w-100' min={1}></InputNumber>
                        <Button className='mt-2vh' type="primary">Send Offer</Button>
                    </Card>
                </Col>
                <Col span={1}></Col>
                <Col span={8}>
                    <Card title='Original Offer'>
                        <div className='flex-row'>
                            <Avatar size='large' src={props.userPhoto}></Avatar>
                            <Title level={4} className='m-0'>{props.userName}</Title>
                        </div>
                        <div>
                            <Title level={5}>{t('manage.description')}</Title>
                        </div>
                        <div>
                            <Text>{props.description}</Text>
                        </div>
                        <div className='mt-2vh'>
                            <Title level={5}>{t('common.offeredPrice')}</Title>
                        </div>
                        <div>
                            <Text>{props.offeredPrice}</Text>
                        </div>
                    </Card>
                </Col>
                <Col span={3}></Col>
            </Row>
        </div>
    );
}

export default SendCounterOffer;