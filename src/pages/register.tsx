import { Button, Card, Checkbox, Col, Input, Radio, Switch } from 'antd';
import React from 'react';

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
                    
                    <Input placeholder='CUIT 00-00000000-0' style={{marginBottom: '1vh', marginTop: '1vh' }}></Input>
                    <Input placeholder='Name' style={{marginBottom: '1vh'}}></Input>
                    <Input placeholder='Email' type='email' style={{marginBottom: '1vh'}}></Input>
                    
                    <Radio.Group onChange={(e) => {e.target.value}} style={{marginBottom: '1vh'}}>
                        <Radio.Button value={1}>Trucker</Radio.Button>
                        <Radio.Button value={2}>Provider</Radio.Button>
                    </Radio.Group>
                    
                    <Input.Password placeholder='Password' style={{marginBottom: '1vh' }}></Input.Password>
                    <Input.Password placeholder='Confirm Password' style={{marginBottom: '1vh' }}></Input.Password>
                                        
                    <Button type='primary' style={{width: '100%'}}>Create Account</Button>
                </Card>
            </Col>

        </div>
    </>
);
}

export default Register;
