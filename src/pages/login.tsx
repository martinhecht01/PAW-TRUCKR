import { Button, Card, Checkbox, Col, Input, Typography } from 'antd';
import React from 'react';

const {Text, Link, Title} = Typography;

const Login: React.FC = () => {
return (
    <>
        <div style={{width: '100%', height: '60vh', display: 'flex', justifyContent: 'center', alignContent: 'center'}}>
            <Col span={7} style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                <Card>
                    <div style={{width: '100%', textAlign: 'center'}}>
                        <Title level={2}>Login</Title>
                        <Text>New to Truckr? <Link>Sign up now</Link></Text>
                    </div>
                    
                    <Input placeholder='CUIT 00-00000000-0' style={{marginBottom: '1vh', marginTop: '1vh' }}></Input>
                    <Input.Password placeholder='Password' style={{marginBottom: '1vh' }}></Input.Password>
                    <Link>Forgot your password?</Link>
                    <br></br>
                    <Checkbox style={{marginBottom: '1vh' }}><Text>Remember me</Text></Checkbox>
                    <br></br>
                    
                    <Button type='primary' style={{width: '100%'}}>Login</Button>
                </Card>
            </Col>

        </div>
    </>
);
}

export default Login;
