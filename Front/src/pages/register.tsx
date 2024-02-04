import React from 'react';
import { Button, Card, Col, Form, Input, Radio, Typography, message } from 'antd';
import { useNavigate } from 'react-router-dom';
import '../styles/main.scss';
import { createUser } from '../api/userApi';
import { User } from '../models/User';
import { Alert } from '../models/Alert';
import { AxiosError } from 'axios';

const Register: React.FC = () => {
    const { Link, Text, Title } = Typography;
    const [form] = Form.useForm();
    const router = useNavigate();

    async function createAccountAction(values: any) {
        try {
            const { name, email, cuit, password, confirmPassword, role } = values;
            await createUser(new User(0, name, email, cuit, password, confirmPassword, 0, 0, role, '', [], [], [], '', new Alert(0,0,'0',0,0,new Date,new Date, ''), 0));
            message.success('Account created successfully');
            router('/login');
        } catch (error) {
            if (error instanceof AxiosError && error.response) {
                message.error(`${error.response.data.message}`);
            } else{
                message.error('Unexpected error');
            }
        }
    }

    return (
        <>
            <div style={{ width: '100%', height: '60vh', display: 'flex', justifyContent: 'center', alignContent: 'center' }}>
                <Col span={6} style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                    <Card className='w-100 mt-5'>
                        <Form form={form} layout="vertical" onFinish={createAccountAction} className='w-100'>
                            <div style={{ width: '100%', textAlign: 'center' }}>
                                <Title level={2}>Create Account</Title>
                                <Text>Already a user? <Link href='/login'>Login</Link></Text>
                            </div>
                            
                            <Form.Item name="cuit" rules={[
                                { required: true, message: 'Please input your CUIT!' },
                                { pattern: new RegExp('^(20|23|24|25|26|27|30)-[0-9]{8}-[0-9]$'), message: 'Invalid CUIT format!' }
                            ]}>
                                <Input placeholder='CUIT 00-00000000-0' />
                            </Form.Item>
                            
                            <Form.Item name="name" rules={[
                                { required: true, message: 'Please input your name!' },
                                { pattern: new RegExp('^[A-Za-z]+(\\s[A-Za-z]*)+$'), message: 'Invalid name format!' }
                            ]}>
                                <Input placeholder='Name' />
                            </Form.Item>
                            
                            <Form.Item name="email" rules={[
                                { required: true, message: 'Please input your email!' },
                                { type: 'email', message: 'The input is not a valid email!' }
                            ]}>
                                <Input placeholder='Email' type='email' />
                            </Form.Item>
                            
                            <Form.Item name="role" rules={[{ required: true, message: 'Please select your role!' }]}>
                                <Radio.Group>
                                    <Radio.Button value={'TRUCKER'}>Trucker</Radio.Button>
                                    <Radio.Button value={'PROVIDER'}>Provider</Radio.Button>
                                </Radio.Group>
                            </Form.Item>
                            <Form.Item name="password" rules={[{ required: true, message: 'Please input your password!' }, { min: 6, message: 'Password must be at least 6 characters long!' }]}>
                                <Input.Password placeholder='Password' />
                            </Form.Item>
                            
                            <Form.Item name="confirmPassword" dependencies={['password']} rules={[
                                { required: true, message: 'Please confirm your password!' },
                                ({ getFieldValue }) => ({
                                    validator(_, value) {
                                        if (!value || getFieldValue('password') === value) {
                                            return Promise.resolve();
                                        }
                                        return Promise.reject(new Error('The two passwords that you entered do not match!'));
                                    },
                                }),
                            ]}>
                                <Input.Password placeholder='Confirm Password' />
                            </Form.Item>
                                                
                            <Form.Item>
                                <Button type='primary' htmlType="submit" style={{ width: '100%' }}>Create Account</Button>
                            </Form.Item>
                        </Form>
                    </Card>
                </Col>
            </div>
        </>
    );
}

export default Register;
