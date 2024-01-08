import { Button, Card, Col, Input, Radio } from 'antd';
import React from 'react';
import '../styles/main.scss';

const Register: React.FC = () => {
return (
    <>
        <div style={{width: '100%', height: '60vh', display: 'flex', justifyContent: 'center', alignContent: 'center'}}>
            <Col span={7} style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                <Card>
                    <div style={{width: '100%', textAlign: 'center'}}>
                        <h2>Create Account</h2>
                        <span>Already a user? <a>Login</a></span>
                    </div>
                    
                    <Input placeholder='CUIT 00-00000000-0' className='mb-1vh mt-1vh'></Input>
                    <Input placeholder='Name' className='mb-1vh'></Input>
                    <Input placeholder='Email' type='email'className='mb-1vh'></Input>
                    
                    <Radio.Group onChange={(e) => {e.target.value}} className='mb-1vh'>
                        <Radio.Button value={1}>Trucker</Radio.Button>
                        <Radio.Button value={2}>Provider</Radio.Button>
                    </Radio.Group>
                    
                    <Input.Password placeholder='Password' className='mb-1vh'></Input.Password>
                    <Input.Password placeholder='Confirm Password' className='mb-1vh'></Input.Password>
                                        
                    <Button type='primary' style={{width: '100%'}}>Create Account</Button>
                </Card>
            </Col>

        </div>
    </>
);
}

export default Register;
