import { Button, Card, Checkbox, Col, Input, Typography } from 'antd';
import React from 'react';
import '../styles/main.scss';

const {Text, Link, Title} = Typography;

const Login: React.FC = () => {
return (
    <>
        <div style={{height: '60vh'}} className='w-100 flex-center'>
            <Col span={7} style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                <Card>
                    <div className='w-100 text-center'>
                        <Title level={2}>Login</Title>
                        <Text>New to Truckr? <Link>Sign up now</Link></Text>
                    </div>
                    
                    <Input placeholder='CUIT 00-00000000-0' className='mb-1vh mt-1vh'></Input>
                    <Input.Password placeholder='Password' className='mb-1vh'></Input.Password>
                    <Link>Forgot your password?</Link>
                    <br></br>
                    <Checkbox className='mb-1vh'><Text>Remember me</Text></Checkbox>
                    <br></br>
                    
                    <Button type='primary' className='w-100'>Login</Button>
                </Card>
            </Col>

        </div>
    </>
);
}

export default Login;
