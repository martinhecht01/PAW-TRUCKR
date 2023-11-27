import { Button, Col, Flex, Grid, Image, Row } from 'antd';
import React from 'react';


const Landing: React.FC = () => {
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
    </>
);
}

export default Landing;
