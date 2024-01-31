import React, {useEffect, useState} from 'react';
import {Button, Card, Input, Popover, Typography, Row, Col} from 'antd';
import '../styles/main.scss';
import '../styles/myAlert.scss';
import {useTranslation} from "react-i18next";
import {Alert} from "../models/Alert";
import {getAlert} from "../api/alertApi";
import {getUserById} from "../api/userApi";
import {User} from "../models/User";

const { Title } = Typography;


const myAlert: React.FC = () => {

    const {t} = useTranslation();
    const popoverContent = (
        <div>
            <p>{t('myAlert.alertExplanation')}</p>
        </div>
    )

    const [user, setUser] = useState<User>();
    const [myAlert, setMyAlert] = useState<Alert>();

    useEffect(() => {
        getUserById(2).then((user) => {
            setUser(user);
        });

        getAlert().then((alert) => {
            setMyAlert(alert);
        })
    });

    if (user?.alert != undefined) {
        return (
            <div>
                <div className='flex-center'>
                    <Card title={t('myAlert.myAlert')} className='w-50' headStyle={{fontSize: '175%'}}>
                        <Row className='infoRow'>
                            <Col span={7}>
                                <Title level={5}>{t('common.origin')}</Title>
                                <Input disabled defaultValue={myAlert?.city}></Input>
                            </Col>
                            <Col span={3}/>
                            <Col span={14}>
                                <Title level={5}>{t('common.from')}-{t('common.to')}</Title>
                                <Input disabled
                                       defaultValue={`${myAlert?.fromDate.toDateString()} - ${myAlert?.fromDate.toDateString()}`}></Input>
                            </Col>
                        </Row>
                        <Row className='infoRow'>
                            <Col span={7}>
                                <Title level={5}>{t('common.maxWeight')}</Title>
                                <Input disabled defaultValue={myAlert?.maxWeight} suffix={'Kg'}></Input>
                            </Col>
                            <Col span={7}>
                                <Title level={5}>{t('common.maxVolume')}</Title>
                                <Input disabled defaultValue={myAlert?.maxVolume} suffix={'m3'}></Input>
                            </Col>
                            <Col span={7}>
                                <Title level={5}>{t('common.cargoType')}</Title>
                                <Input disabled defaultValue={myAlert?.cargoType}></Input>
                            </Col>
                        </Row>
                    </Card>
                </div>

                <div className="flex-center">
                    <div className='w-50 space-between mt-5'>
                        <Button danger type="dashed">{t('common.delete')}</Button>
                        <Popover content={popoverContent} title={t('myAlert.alertQuestion')}>
                            <Button type="primary">{t('myAlert.help')}</Button>
                        </Popover>
                    </div>
                </div>

            </div>

        );
    } else {
        return (
            <Card title={t('myAlert.myAlert')}>
                <div className="flex-center">
                    <div className='flex-column' style={{textAlign:'center'}}>
                        <Title className='m-0' level={5}>{t('myAlert.noAlert')}</Title>
                        <Button style={{marginTop:'2vh'}} href='/createAlert' type='primary'>{t('myAlert.create')}</Button>
                    </div>
                </div>
            </Card>
        );
    }
};

export default myAlert;