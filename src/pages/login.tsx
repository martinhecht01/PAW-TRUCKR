import { Button, Card, Checkbox, Col, Input } from 'antd';
import React from 'react';

const Login: React.FC = () => {
return (
    <>
        <div style={{width: '100%', height: '60vh', display: 'flex', justifyContent: 'center', alignContent: 'center'}}>
            <Col span={7} style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                <Card>
                    <div style={{width: '100%', textAlign: 'center'}}>
                        <h2>Login</h2>
                        <span>New to Truckr? <a>Sign up now</a></span>
                    </div>
                    
                    <Input placeholder='CUIT 00-00000000-0' style={{marginBottom: '1vh', marginTop: '1vh' }}></Input>
                    <Input.Password placeholder='Password' style={{marginBottom: '1vh' }}></Input.Password>
                    <a>Forgot your password?</a>
                    <br></br>
                    <Checkbox style={{marginBottom: '1vh' }}>Remember me</Checkbox>
                    <br></br>
                    
                    <Button type='primary' style={{width: '100%'}}>Login</Button>
                </Card>
            </Col>

        </div>
    </>
);
}

export default Login;
