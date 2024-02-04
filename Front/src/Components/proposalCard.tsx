import { Card, Button, Typography, Divider, Row, Col, Avatar, Skeleton, message } from "antd";
import { useTranslation } from "react-i18next";
import '../styles/main.scss';
import '../styles/proposals.scss';
import { useNavigate } from 'react-router-dom';
import { StarFilled, UserOutlined } from "@ant-design/icons";
import { useEffect, useState } from "react";
import { Offer } from "../models/Offer";
import { deleteOffer, getOfferByUrl } from "../api/offerApi";

const { Title, Text } = Typography;

export type ProposalProps = {
    id: string,
    description: string,
    offeredPrice: number,
    userPhoto: string,
    userName: string,
    counterOffer: string,
    acceptAction: (id: string, action: 'ACCEPT' | 'REJECT') => void,
    tripId: string,
    rating: number,
    userId: string
}

const ProposalCard = (props: ProposalProps) => {
    const { t } = useTranslation();
    const router = useNavigate();

    const [counterOfferObject, setCounterOfferObject] = useState<Offer>();
    const [loading, setIsLoading] = useState<boolean>(true);

    useEffect(() => {
        if (props.counterOffer) {
            getOfferByUrl(props.counterOffer).then((offer) => {
                setCounterOfferObject(offer);
                setIsLoading(false);
            })
        } else {
            setIsLoading(false);
        }
    }, [props.counterOffer])

    async function cancelOffer(id: number){
        try{
            await deleteOffer(id);
            setCounterOfferObject(undefined);
            message.success(t('messages.counterOfferCanceledSuccess'));
        }
        catch (e){
            message.error(t('messages.errorDeletingOffer'));
        }
    }

    return (
        <div>
            <Card className='mb-2vh'>
                <Row gutter={16}>
                    <Col span={24} onClick={() => router(`/profile/${props.userId}`)}>
                        <Row className="space-between">
                            <Avatar icon={<UserOutlined />} src={props.userPhoto} size={64} />
                            <Title level={5} className='m-0'>{props.userName}</Title>
                            <Title level={5} className='m-0'><StarFilled/> {props.rating === 0 ? '-' : Number(props.rating).toFixed(1)}</Title>
                        </Row>
                    </Col>
                    <Col span={24}>
                        <Title level={5}>{t('manage.description')}</Title>
                    </Col>
                    <Col span={24}>
                        <Text>{props.description}</Text>
                    </Col>
                    <Col span={24} className='mt-2vh'>
                        <Title level={5}>{t('common.offeredPrice')}</Title>
                    </Col>
                    <Col span={24}>
                        <Text>{props.offeredPrice}</Text>
                    </Col>
                    <Skeleton loading={loading}>
                    {!counterOfferObject &&
                        <div className='space-around w-100'>
                            <Col span={6} className='flex-center'>
                                <Button className='m-1vh acceptButton' onClick={() => props.acceptAction(props.id, 'ACCEPT')}>{t('manage.accept')}</Button>
                            </Col>
                            <Col span={6} className='flex-center'>                                
                                <Button className='m-1vh counterOfferButton' onClick={() => router(`/sendCounterOffer?tripId=${props.tripId}&offerId=${props.id}`)}>{t('manage.counterOffer')}</Button>
                            </Col>
                            <Col span={6} className='flex-center' >
                                <Button className='m-1vh rejectButton' onClick={() => props.acceptAction(props.id, 'REJECT')}>{t('manage.reject')}</Button>
                            </Col>
                        </div>
                    }
                    {counterOfferObject &&
                            <Col span={24}>
                                <Divider />
                                <Title level={5}>{t('manage.counterOffer')}</Title>
                                <Col span={24}>
                                    <Title level={5}>{t('manage.description')}</Title>
                                </Col>
                                <Col span={24}>
                                    <Text>{counterOfferObject?.description}</Text>
                                </Col>
                                <Col span={24} className='mt-2vh'>
                                    <Title level={5}>{t('common.offeredPrice')}</Title>
                                </Col>
                                <Col span={24}>
                                    <Text>{counterOfferObject?.price}</Text>
                                </Col>
                                <Col span={24} className='mt-2vh flex-center'>
                                    <Button className='m-1vh' danger onClick={() => cancelOffer(counterOfferObject.id)}>{t('manage.cancel')}</Button>
                                </Col>
                            </Col>
                    }
                    </Skeleton>
                </Row>
            </Card>
        </div>
    );
}

export default ProposalCard;
