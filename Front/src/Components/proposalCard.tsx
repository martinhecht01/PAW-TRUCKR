import React from 'react';
import { Card, Button, Typography, Divider, Row, Col } from "antd";
import { useTranslation } from "react-i18next";
import '../styles/main.scss';
import '../styles/proposals.scss';

const { Title, Text } = Typography;

export type ProposalProps = {
    id: string,
    description: string,
    offeredPrice: number,
    userPhoto: string,
    userName: string,
    userMail?: string,
    counterOffer: string,
    acceptAction: (id: string, action: 'ACCEPT' | 'REJECT') => void,
}


const ProposalCard = (props: ProposalProps) => {
    const { t } = useTranslation();

    return (
        <div>
            <Card className='mb-2vh'>
                <Row gutter={16}>
                    <Col span={24}>
                        <Title level={4} className='m-0'>{props.userName}</Title>
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
                    {!props.counterOffer &&
                        <div className='space-around w-100'>
                            <Col span={6} className='flex-center'>
                                <Button className='m-1vh acceptButton' onClick={() => props.acceptAction(props.id, 'ACCEPT')}>{t('manage.accept')}</Button>
                            </Col>
                            <Col span={6} className='flex-center'>                                
                                <Button className='m-1vh counterOfferButton'>{t('manage.counterOffer')}</Button>
                            </Col>
                            <Col span={6} className='flex-center' >
                                <Button className='m-1vh rejectButton' onClick={() => props.acceptAction(props.id, 'REJECT')}>{t('manage.reject')}</Button>
                            </Col>
                        </div>
                    }
                    {props.counterOffer &&
                        <Col span={24}>
                            <Divider />
                            <Title level={4}>{t('manage.counterOffer')}</Title>
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
                            <Col span={24} className='mt-2vh'>
                                <Button className='m-1vh' danger>{t('manage.cancel')}</Button>
                            </Col>
                        </Col>
                    }
                </Row>
            </Card>
        </div>
    );
}

export default ProposalCard;
