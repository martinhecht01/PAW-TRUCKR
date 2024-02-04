import React from 'react';
import { Button, Card, Col, Form, Input, Typography, message } from 'antd';
import { useNavigate } from 'react-router-dom';
import '../styles/main.scss';
import { loginUser } from '../api/userApi';
import { useAuthContext } from '../hooks/authProvider';

const { Text, Link, Title } = Typography;

const Login: React.FC = () => {
    const [form] = Form.useForm();
    const auth = useAuthContext();
    const router = useNavigate();

    async function loginAction(values: any) {
        const { cuit, password } = values;
        loginUser(cuit, password).then((token) => {
            if(token == null) {
                message.error('Invalid credentials or user not verified');
            } else {
                auth.login(token);
                router('/profile');
            }
        }).catch(err => {
            // Handle error if necessary
            message.error('Login failed. Please try again.');
        });
    }

    return (
        <>
            <div style={{ height: '60vh' }} className='w-100 flex-center'>
                <Col span={8} style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                    <Card className='w-100'>
                        <Form form={form} layout="vertical" onFinish={loginAction}>
                            <div className='w-100 text-center'>
                                <Title level={2}>Login</Title>
                                <Text>New to Truckr? <Link href='/register'>Sign up now</Link></Text>
                            </div>
                            
                            <Form.Item name="cuit" className='mt-2vh' rules={[
                                { required: true, message: 'Please input your CUIT!' },
                                { pattern: new RegExp('^(20|23|24|25|26|27|30)-[0-9]{8}-[0-9]$'), message: 'Invalid CUIT format!' }
                            ]}>
                                <Input 
                                    placeholder='CUIT 00-00000000-0'
                                    data-testid='cuit-login'
                                />
                            </Form.Item>
                            
                            <Form.Item name="password" rules={[{ required: true, message: 'Please input your password!' }]}>
                                <Input.Password 
                                    placeholder='Password'
                                    data-testid='password-login'
                                />
                            </Form.Item>
                            
                            <Form.Item>
                                <Link href='/resetPasswordRequest'>Forgot your password?</Link>
                            </Form.Item>
                            
                            <Form.Item>
                                <Button type='primary' htmlType="submit" className='w-100' data-testid='button-login'>Login</Button>
                            </Form.Item>
                        </Form>
                    </Card>
                </Col>
            </div>
        </>
    );
}

export default Login;
