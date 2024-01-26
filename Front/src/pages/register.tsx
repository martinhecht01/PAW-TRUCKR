import React, { useState } from 'react';
import { Button, Card, Col, Input, Radio, Typography, message } from 'antd';
import { useNavigate } from 'react-router-dom';
import '../styles/main.scss';
import { createUser } from '../api/userApi';
import { User } from '../models/User';
import { Alert } from '../models/Alert';
import { Axios, AxiosError } from 'axios';

const Register: React.FC = () => {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [cuit, setCuit] = useState('');
    const [pass, setPass] = useState('');
    const [confirmPass, setConfirmPass] = useState('');
    const [role, setRole] = useState('');

    const { Link, Text, Title } = Typography;
    const router = useNavigate();

    async function createAccountAction() {
        try {
            // Assuming User constructor takes the parameters in the order: id, name, email, cuit, password, confirmPassword, other parameters...
            await createUser(new User(0, name, email, cuit, pass, confirmPass, 0, 0, role, '', [], [], [], '',    new Alert(0,0,'0',0,0,new Date,new Date, '')))
            message.success('User created successfully');
            router('/login');
        } catch (error) {
            const axiosError = error as AxiosError;
            if (axiosError.response && axiosError.response.data) {
                // Access the error message here, adjust according to your error response structure
                let errorMessage = axiosError.response.data.message;
                message.error(errorMessage);
            } else {
                message.error('An unexpected error occurred');
            }
            
            console.log(error);
        }
    }

    return (
        <>
            <div style={{ width: '100%', height: '60vh', display: 'flex', justifyContent: 'center', alignContent: 'center' }}>
                <Col span={7} style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                    <Card>
                        <div style={{ width: '100%', textAlign: 'center' }}>
                            <Title level={2}>Create Account</Title>
                            <Text>Already a user? <Link href='/login'>Login</Link></Text>
                        </div>
                        
                        <Input placeholder='CUIT 00-00000000-0' className='mb-1vh' value={cuit} onChange={e => setCuit(e.target.value)} />
                        <Input placeholder='Name' className='mb-1vh' value={name} onChange={e => setName(e.target.value)} />
                        <Input placeholder='Email' type='email' className='mb-1vh' value={email} onChange={e => setEmail(e.target.value)} />
                        
                        <Radio.Group onChange={e => setRole(e.target.value)} className='mb-1vh'>
                            <Radio.Button value={'TRUCKER'}>Trucker</Radio.Button>
                            <Radio.Button value={'PROVIDER'}>Provider</Radio.Button>
                        </Radio.Group>
                        
                        <Input.Password placeholder='Password' className='mb-1vh' value={pass} onChange={e => setPass(e.target.value)} />
                        <Input.Password placeholder='Confirm Password' className='mb-1vh' value={confirmPass} onChange={e => setConfirmPass(e.target.value)} />
                                            
                        <Button type='primary' style={{ width: '100%' }} onClick={createAccountAction}>Create Account</Button>
                    </Card>
                </Col>
            </div>
        </>
    );
}

export default Register;
