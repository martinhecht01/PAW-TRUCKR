import React from 'react';
import {Button, Card, Popover, Typography} from 'antd';
import '../styles/main.scss';
import '../styles/myAlert.scss';
import {useTranslation} from "react-i18next";

const { Title } = Typography;


const myAlert: React.FC = () => {

    const {t} = useTranslation();
    const popoverContent = (
        <div>
            <p>{t('myalert.alertExplanation')}</p>
        </div>
    )

    return (
        <div>
            <div className='flex-center'>
                <Card title="My alert" style={{width:"60%"}} headStyle={{fontSize:'175%'}}>
                    <div className='infoRow'>
                        <div style={{display:"inline-block"}}>
                            <Title level={5}>{t('common.origin')}</Title>
                            <Card size={"small"} className='infoDisplay'>dfafdasdf</Card>
                        </div>
                        <div style={{display:"inline-block"}}>
                            <Title level={5}>{t('common.from')}</Title>
                            <Card size={"small"} className='infoDisplay'>dfafdasdf</Card>
                        </div>
                        <div style={{display:"inline-block"}}>
                            <Title level={5}>{t('common.to')}</Title>
                            <Card size={"small"} className='infoDisplay'>dfafdasdf</Card>
                        </div>
                    </div>
                    <div style={{ marginBottom:'4vh'}} className='infoRow'>
                        <div style={{display:"inline-block"}}>
                            <Title level={5}>{t('common.maxWeight')}</Title>
                            <Card size={"small"} className='infoDisplay'>dfafdasdf</Card>
                        </div>
                        <div style={{display:"inline-block"}}>
                            <Title level={5}>{t('common.maxVolume')}</Title>
                            <Card size={"small"} className='infoDisplay'>dfafdasdf</Card>
                        </div>
                        <div style={{display:"inline-block"}}>
                            <Title level={5}>{t('common.cargoType')}</Title>
                            <Card size={"small"} className='infoDisplay'>dfafdasdf</Card>
                        </div>
                    </div>
                </Card>



            </div>

            <div className="flex-center">
                <div style={{ marginTop:'5vh', justifyContent:'space-between', width:'60%', display:"flex", alignContent:"center"}}>
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