import {Button, Col, Flex, Image, Row, Tabs, TabsProps} from 'antd';
import React from 'react';
import {DownOutlined} from "@ant-design/icons";
import { Typography } from 'antd';
import LandingCard from '../Components/landingCard';
import '../styles/main.scss';
import { useNavigate } from 'react-router-dom';
import {useTranslation} from "react-i18next";
import { getClaims } from '../api/userApi';

const Landing: React.FC = () => {

    const {t} = useTranslation();
    const router = useNavigate();

    document.title = t('pageTitles.landing');

    const contentForProviders: () => React.ReactNode = () => {
        /*
    "browseTrip": "1. Explorar viajes",
    "browseTripSubtitle": "Explora viajes disponibles. Filtra de la manera que te sea más conveniente.",
    "selectTrucker": "2. Selecciona la mejor opción de transportista para ti",
    "selectTruckerSubtitle": "Selecciona el transportista con el que deseas enviar y haz una oferta.",
    "oneMoreStep": "3. Un paso más",
    "oneMoreStepSubtitle": "Espera a que el transportista confirme tu oferta. Siempre puedes modificarla o enviar una contraoferta si lo deseas.",
    "shipIt": "4. ¡Envíalo!",
    "shipItSubtitle": "Una vez confirmado el pedido, ponte en contacto con el transportista y envía la carga.",
        */
        return <div style={{width:'100%',display:'flex',flexDirection:'column',alignItems:'center'}}>
            <LandingCard  title={t('landing.browseTrip')} subtitle={t('landing.browseTripSubtitle')}></LandingCard>
            <LandingCard title={t('landing.selectTrucker')} subtitle={t('landing.selectTruckerSubtitle')}></LandingCard>
            <LandingCard title={t('landing.oneMoreStep')} subtitle={t('landing.oneMoreStepSubtitle')}></LandingCard>
            <LandingCard title={t('landing.shipIt')} subtitle={t('landing.shipItSubtitle')}></LandingCard>
            { !getClaims() ?
            <Button style={{width:'50%', marginTop:'2vh'}} type='primary' size={"large"} onClick={() => router('/trips')}>{t('landing.BrowseTrips')}</Button>
                :null}
            </div>;
    };

    //TODO: change the content for providers
    const contentForTruckers: () => React.ReactNode = () => {
        return <div style={{width:'100%',display:'flex',flexDirection:'column',alignItems:'center'}}>
            <LandingCard  title={'1. ' + t('landing.BrowseCargo')} subtitle={t('landing.NewLanding1')}></LandingCard>
            <LandingCard title={'2. ' + t('landing.NewLanding2')} subtitle={t('landing.NewLanding3')}></LandingCard>
            <LandingCard title={'3. ' + t('landing.OneMoreStep')} subtitle={t('landing.OneMoreStepMessage')}></LandingCard>
            <LandingCard title={'4. ' + t('landing.ShipitE')} subtitle={t('landing.NewLanding5')}></LandingCard>
            { !getClaims() ?
            <Button style={{width:'50%', marginTop:'2vh'}} type='primary' size={"large"} onClick={() => router('/cargo')}>{t('landing.BrowseCargo')}</Button>
            : null}
        </div>;
    };

    const items: TabsProps['items'] = [
        {
            key: '1',
            label: t('landing.ForTrucker'),
            children: contentForTruckers(),
        },
        {
            key: '2',
            label: t('landing.ForProvider'),
            children: contentForProviders(),
        },
    ];

    const {Title} = Typography;


    return (
    <>
        <Row style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
            <Col span={12}>
                <Title style={{fontWeight: 'bold', fontSize: 60}}>Truckr.</Title>
                <Title level={2}>{t('landing.LandingMainMessage')}</Title>
                <Title level={4} style={{fontWeight: 'normal'}}>{t('landing.LandingMainSubMessage')}</Title>
                <Flex gap='middle'>
                    <Row className='w-80 space-around mt-2vh'>
                        {!getClaims() ?
                            <>
                            <Col span={10}>
                                <Button type='primary' className='w-100' size='large' onClick={() => router('/trips')}>{t('landing.BookATrucker')}</Button>
                            </Col>
                            <Col span={10}>
                                <Button type='primary' className='w-100' size='large' onClick={() => router('/cargo')}>{t('landing.DriveWithTruckr')}</Button>
                            </Col>
                            </>
                        : null }
                    </Row>
                </Flex>
            </Col>
            <Col span={8} style={{display: 'flex', justifyContent: 'center'}}>
                <Image preview={false} style={{height: 600, objectFit: 'cover', width: 400}} 
                    src="https://hips.hearstapps.com/hmg-prod/images/volvo-vnr-electric-6x2-with-reefer-trailer-passenger-side-view-on-the-road-daytime-shot-1607106606.jpg?crop=0.643xw:0.988xh;0.204xw,0&resize=1200:*">
                </Image>
            </Col>
        </Row>
        <div style={{width:'100%', display:'flex',justifyContent:'center',marginTop:'8vh'}}>
            <DownOutlined style={{fontSize:'250%'}} />

        </div>
        <div style={{width:'100%',display:'flex',flexDirection:'column',alignItems:'center',marginTop:'20vh'}}>
            <Title style={{marginBottom:'0vh'}}><b>{t('landing.HowTruckrWorks')}</b></Title>
            <br></br>
            <Tabs style={{width:'100%',display:'flex',flexDirection:'column',alignItems:'center'}} size="large" defaultActiveKey="1" items={items} />
        </div>

    </>
);
}

export default Landing;
