import React from 'react';
import {Button, Card, Input, Popover, Typography, Row, Col, DatePicker} from 'antd';
import '../styles/main.scss';
import '../styles/myAlert.scss';
import {useTranslation} from "react-i18next";

const { Title } = Typography;


const myAlert: React.FC = () => {

    const {t} = useTranslation();
    const {RangePicker} = DatePicker;
    const popoverContent = (
        <div>
            <p>{t('myalert.alertExplanation')}</p>
        </div>
    )

    return (
        <div>
            <div className='flex-center'>
                <Card title="My alert" className='w-50' headStyle={{fontSize:'175%'}}>
                    <Row className='infoRow'>
                        <Col span={7}>
                                <Title level={5}>{t('common.origin')}</Title>
                                <Input disabled defaultValue={"Buenos Aires"}></Input>
                        </Col>
                        <Col span={3}/>
                        <Col span={14}>
                                <Title level={5}>{t('common.from')}-{t('common.to')}</Title>
                                <RangePicker disabled></RangePicker>
                        </Col>
                    </Row>
                    <Row className='infoRow'>
                        <Col span={7}>
                                <Title level={5}>{t('common.maxWeight')}</Title>
                                <Input disabled defaultValue={"1000"} suffix={'Kg'}></Input>
                        </Col>
                        <Col span={7}>
                                <Title level={5}>{t('common.maxVolume')}</Title>
                                <Input disabled defaultValue={"1000"} suffix={'m3'}></Input>
                        </Col>
                        <Col span={7}>
                                <Title level={5}>{t('common.cargoType')}</Title>
                                <Input disabled defaultValue={"Fragile"}></Input>
                        </Col>
                    </Row>
                </Card>
            </div>

            <div className="flex-center">
                <div className='w-50 space-between mt-5'>
                    <Button danger  type="dashed">{t('common.delete')}</Button>
                    <Popover content={popoverContent} title={t('myalert.alertQuestion')}>
                        <Button type="primary">{t('myalert.help')}</Button>
                    </Popover>
                </div>
            </div>

        </div>

    );
};

export default myAlert;