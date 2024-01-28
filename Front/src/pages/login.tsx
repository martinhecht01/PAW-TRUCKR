import React, { useState } from 'react';
import { Button, Card, Checkbox, Col, Input, Typography, message,  } from 'antd';
import '../styles/main.scss';
import { useNavigate } from 'react-router-dom';
import { loginUser } from '../api/userApi';
import { useAuthContext } from '../hooks/authProvider';

const { Text, Link, Title } = Typography;

const Login: React.FC = () => {
    const [cuit, setCuit] = useState('');
    const [pass, setPass] = useState('');

    const auth = useAuthContext();

    const router = useNavigate();

    function loginAction() {
        loginUser(cuit, pass).then((token) => {
            if(token == null)
                message.error('Invalid credentials')
            else{
                auth.login(token);
                router('/profile');
            }
        });
    }

    return (
        <>
            <div style={{ height: '60vh' }} className='w-100 flex-center'>
                <Col span={7} style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                    <Card>
                        <div className='w-100 text-center'>
                            <Title level={2}>Login</Title>
                            <Text>New to Truckr? <Link href='/register'>Sign up now</Link></Text>
                        </div>
                        
                        <Input 
                            placeholder='CUIT 00-00000000-0' 
                            className='mb-1vh mt-1vh'
                            value={cuit}
                            onChange={(e) => setCuit(e.target.value)}
                        />
                        <Input.Password 
                            placeholder='Password' 
                            className='mb-1vh'
                            value={pass}
                            onChange={(e) => setPass(e.target.value)}
                        />
                        <Link>Forgot your password?</Link>
                        <br></br>
                        {/* <Checkbox className='mb-1vh'><Text>Remember me</Text></Checkbox>
                        <br></br> */}
                        
                        <Button type='primary' className='w-100' onClick={loginAction}>Login</Button>
                    </Card>
                </Col>
            </div>
        </>
    );
}

export default Login;
