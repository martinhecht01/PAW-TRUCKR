import React, { useState } from "react";
import { Button, Card, Col, Form, Input, Typography, message } from "antd";
import Link from "antd/es/typography/Link";
import {useNavigate, useSearchParams} from 'react-router-dom';
import { resetPassword } from "../api/userApi";

const { Text, Title } = Typography;

const ResetPassword: React.FC = () => {
    const [form] = Form.useForm();
    const [searchParams] = useSearchParams();
    const hash = searchParams.get('hash');
    const cuit = searchParams.get('cuit');
    const userId = searchParams.get('userid');
    const [ResetState, setResetState] = useState(false);
    const router = useNavigate();
    async function handleResetPassword(values: any) {
        const { password} = values;
        if (cuit == null || hash == null || userId == null) {
            message.error('Please request a valid reset password link');
            return;
        }
        try {
            await resetPassword(cuit, password, hash, userId);
            setResetState(true);
        }
        catch (e) {
            message.error('Link expired or already used. Please request a new one');
        }
    }

    return (
        <div style={{height: '60vh'}} className='w-100 flex-center'>
            <Col span={8} style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                <Card className='w-100'>
                    <div className='w-100 text-center'>
                        <Title level={2}>Reset Password</Title>
                    </div>
                    {!ResetState ? (
                        <Form form={form} layout="vertical" onFinish={handleResetPassword}>
                            <Form.Item
                                name="password"
                                rules={[
                                    { required: true, message: 'Please input your new password!' },
                                    { min: 6, message: 'Password must be at least 6 characters long!' }
                                ]}
                            >
                                <Input.Password placeholder='Password' data-testid='password-resetPassword' />
                            </Form.Item>
                            <Form.Item
                                name="confirmPassword"
                                dependencies={['password']}
                                hasFeedback
                                rules={[
                                    { required: true, message: 'Please confirm your new password!' },
                                    ({ getFieldValue }) => ({
                                        validator(_, value) {
                                            if (!value || getFieldValue('password') === value) {
                                                return Promise.resolve();
                                            }
                                            return Promise.reject(new Error('The two passwords that you entered do not match!'));
                                        },
                                    }),
                                ]}
                            >
                                <Input.Password placeholder='Confirm Password' data-testid='repeatPassword-resetPassword' />
                            </Form.Item>
                            <Form.Item>
                                <Button type='primary' htmlType="submit" data-testid='button-resetPassword' className='w-100'>Reset Password</Button>
                            </Form.Item>
                        </Form>
                    ) : (
                        <Text data-testid='success-resetPassword' className='w-100 text-center'> Password changed successfully! <Link onClick={()=> router('/login')}>Login back</Link></Text>
                    )}
                </Card>
            </Col>
        </div>
    );
};

export default ResetPassword;
