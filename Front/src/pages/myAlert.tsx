import React, {useEffect, useState} from 'react';
import {Button, Card, Input, Popover, Typography, Row, Col, message, Skeleton} from 'antd';
import '../styles/main.scss';
import '../styles/myAlert.scss';
import {useTranslation} from "react-i18next";
import {Alert} from "../models/Alert";
import {deleteAlert, getAlert} from "../api/alertApi";
import {useNavigate} from "react-router-dom";

const { Title } = Typography;


const myAlert: React.FC = () => {

    const {t} = useTranslation();
    const router = useNavigate();
    const popoverContent = (
        <div>
            <p>{t('myAlert.alertExplanation')}</p>
        </div>
    )



    const [myAlert, setMyAlert] = useState<Alert>();
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        getAlert().then((alert) => {
            setMyAlert(alert);
            setLoading(false);
        })
    },[]);

    async function handleAlertDelete(){
        const id = myAlert?.id
        if (id == undefined){
            return;
        }
        try{
            await deleteAlert(id);
            setMyAlert(undefined);
        }
        catch (e) {
            message.error("Unexpected error. Try again.");
        }
    }
    

    if(loading){
        return (
            <Card title={t('myAlert.myAlert')}>
                <Skeleton active/>
            </Card>
        );
    }


    if (myAlert?.id !== undefined) {
        return (
            <div>
                <div className='flex-center'>
                    <Card title={t('myAlert.myAlert')} className='w-50' headStyle={{fontSize: '175%'}}>
                        <Row className='infoRow'>
                            <Col span={7}>
                                <Title level={5}>{t('common.origin')}</Title>
                                <Input disabled defaultValue={myAlert.city}></Input>
                            </Col>
                            <Col span={3}/>
                            <Col span={14}>
                                <Title level={5}>{t('common.from')}-{t('common.to')}</Title>
                                <Input disabled
                                       defaultValue={`${myAlert.fromDate} - ${myAlert.fromDate}`}></Input>
                            </Col>
                        </Row>
                        <Row className='infoRow'>
                            <Col span={7}>
                                <Title level={5}>{t('common.maxWeight')}</Title>
                                <Input disabled defaultValue={myAlert.maxWeight} suffix={'Kg'}></Input>
                            </Col>
                            <Col span={7}>
                                <Title level={5}>{t('common.maxVolume')}</Title>
                                <Input disabled defaultValue={myAlert.maxVolume} suffix={'m3'}></Input>
                            </Col>
                            <Col span={7}>
                                <Title level={5}>{t('common.cargoType')}</Title>
                                <Input disabled defaultValue={myAlert.cargoType}></Input>
                            </Col>
                        </Row>
                    </Card>
                </div>

                <div className="flex-center">
                    <div className='w-50 space-between mt-5'>
                        <Button danger type="dashed" onClick={() => handleAlertDelete()}>{t('common.delete')}</Button>
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
                    <div className='flex-column' style={{textAlign: 'center'}}>
                        <Title className='m-0' level={5}>{t('myAlert.noAlert')}</Title>
                        <Button style={{marginTop: '2vh'}} onClick={() => router('/createAlert')}
                                type='primary'>{t('myAlert.create')}</Button>
                    </div>
                </div>
            </Card>
        );
    }
};

export default myAlert;