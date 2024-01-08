import {Button, Col, Flex, Image, Row, Tabs, TabsProps} from 'antd';
import React from 'react';
import {DownOutlined} from "@ant-design/icons";
import { Typography } from 'antd';
import LandingCard from '../components/landingCard';

const { Title } =Typography;

const Landing: React.FC = () => {

    const contentForTruckers: () => React.ReactNode = () => {
        return <div style={{width:'100%',display:'flex',flexDirection:'column',alignItems:'center'}}>
            <LandingCard  title='1. Browse Cargo' subtitle='Browse the cargo that is available. Filter however is more convinient for you.'></LandingCard>
            <LandingCard title='2. Select the best cargo option for you' subtitle='Select the cargo that you want to ship and send an offer.'></LandingCard>
            <LandingCard title='3. One more step' subtitle='Wait for the cargo provider to confirm your offer. You can always modify it or send a counteroffer if you want.'></LandingCard>
            <LandingCard title='4. Ship it!' subtitle='Once the order is confirmed, get in touch with the provider and ship the cargo.'></LandingCard>
            <Button style={{width:'50%', marginTop:'2vh'}} type='primary' size={"large"}>Browse Cargo</Button>
        </div>;
    };

    //TODO: change the content for providers
    const contentForProviders: () => React.ReactNode = () => {
        return <div style={{width:'100%',display:'flex',flexDirection:'column',alignItems:'center'}}>
            <LandingCard  title='1. Browse Cargo' subtitle='Browse the cargo that is available. Filter however is more convinient for you.'></LandingCard>
            <LandingCard title='2. Select the best cargo option for you' subtitle='Select the cargo that you want to ship and send an offer.'></LandingCard>
            <LandingCard title='3. One more step' subtitle='Wait for the cargo provider to confirm your offer. You can always modify it or send a counteroffer if you want.'></LandingCard>
            <LandingCard title='4. Ship it!' subtitle='Once the order is confirmed, get in touch with the provider and ship the cargo.'></LandingCard>
            <Button style={{width:'50%', marginTop:'2vh'}} type='primary' size={"large"}>Browse Trips</Button>
        </div>;
    };

    const items: TabsProps['items'] = [
        {
            key: '1',
            label: 'For Truckers',
            children: contentForTruckers(),
        },
        {
            key: '2',
            label: 'For Providers',
            children: contentForProviders(),
        },
    ];




    return (
    <>
        <Row style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
            <Col span={12}>
                <h1>Truckr.</h1>
                <h3>The platform that connects drivers and cargo providers.</h3>
                <span>Choose a role as trucker or provider and start trucking!</span>
                <Flex gap='middle'>
                    <Button type='primary'>Book a trucker</Button>
                    <Button type='primary'>Drive with Truckr</Button>
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
            <Title style={{marginBottom:'0vh'}}><b>How does Truckr work?</b></Title>
            <br></br>
            <Tabs style={{width:'100%',display:'flex',flexDirection:'column',alignItems:'center'}} size="large" defaultActiveKey="1" items={items} />
        </div>

    </>
);
}

export default Landing;
