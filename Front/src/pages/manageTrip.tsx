import React from 'react';
import {Card, Col, Divider, Image, Row, Typography} from 'antd';
import '../styles/main.scss';
import '../styles/profile.scss';
import {useTranslation} from "react-i18next";


const { Title, Text } = Typography;

const ManageTrip: React.FC = () => {

    const {t} = useTranslation();

    return (
        <div>
            <div className="flex-center">
                <Card style={{width: '30%', margin: '3vh'}} headStyle={{fontSize: '2.5vh', color: '#142d4c'}}
                      title={t("manage.manageTrip")}>
                    <div className='flex-center'>
                        <Image
                            src="https://pm1.aminoapps.com/6535/8e5958d3b7755e9429e476c408a06e5387358b0f_00.jpg"></Image>
                    </div>
                    <Row gutter={16} className='mt-2vh'>
                        <Col span={12}>
                            <Title className="m-1vh" level={5}>{t("common.cargoType")}</Title>
                        </Col>
                        <Col span={12}>
                            <p className="m-1vh">dfsajk</p>
                        </Col>
                    </Row>
                    <Divider className='m-0'/>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Title className="m-1vh" level={5}>{t('manage.origDest')}</Title>
                        </Col>
                        <Col span={12}>
                            <p className="m-1vh">dfsajk</p>
                        </Col>
                    </Row>
                    <Divider className='m-0'/>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Title className="m-1vh" level={5}>{t('common.licensePlate')}</Title>
                        </Col>
                        <Col span={12}>
                            <p className="m-1vh">dfsajk</p>
                        </Col>
                    </Row>
                    <Divider className='m-0'/>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Title className="m-1vh" level={5}>{t('manage.depArr')}</Title>
                        </Col>
                        <Col span={12}>
                            <p className="m-1vh">dfsajk</p>
                        </Col>
                    </Row>
                    <Divider className='m-0'/>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Title className="m-1vh" level={5}>{t('common.availableVolume')}</Title>
                        </Col>
                        <Col span={12}>
                            <p className="m-1vh">dfsajk</p>
                        </Col>
                    </Row>
                    <Divider className='m-0'/>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Title className="m-1vh" level={5}>{t('common.availableWeight')}</Title>
                        </Col>
                        <Col span={12}>
                            <p className="m-1vh">dfsajk</p>
                        </Col>
                    </Row>
                    <Divider className='m-0'/>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Title className="m-1vh" level={5}>{t('common.suggestedPrice')}</Title>
                        </Col>
                        <Col span={12}>
                            <p className="m-1vh">dfsajk</p>
                        </Col>
                    </Row>
                </Card>
                <Card style={{width: '25%'}}>
                    <Title className='m-0' level={5}>{t('manage.noOffers')}</Title>
                </Card>
            </div>


        </div>
    );
};

export default ManageTrip;