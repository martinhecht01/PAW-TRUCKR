import React, { useState } from 'react';
import { Button, Card, Col, Form, Input, Typography, message } from 'antd';

import '../styles/main.scss';
import { resetPasswordRequest } from "../api/userApi";

const ResetPasswordRequest: React.FC = () => {
    const [ResetState, setResetState] = useState(false);
    const [form] = Form.useForm();

    async function sendRequest(values: any) {
        const { cuit } = values;
        try {
            await resetPasswordRequest(cuit);
            setResetState(true);
            message.success('If an account with this CUIT exists, a reset email has been sent.');
        } catch (e) {
            message.error('Invalid CUIT');
        }
    }

    const { Title, Text } = Typography;

    return (
        <div style={{height: '60vh'}} className='w-100 flex-center'>
            <Col span={8} style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                <Card className='w-100'>
                    <div className='w-100 text-center'>
                        <Title level={2}>Reset Password</Title>
                    </div>
                    {!ResetState ? (
                        <Form form={form} layout="vertical" onFinish={sendRequest}>
                            <Form.Item
                                name="cuit"
                                rules={[
                                    { required: true, message: 'Please input your CUIT!' },
                                    { pattern: new RegExp('^(20|23|24|25|26|27|30)-[0-9]{8}-[0-9]$'), message: 'Invalid CUIT format!' }
                                ]}
                            >
                                <Input placeholder='CUIT 00-00000000-0' data-testid='cuit-resetPasswordRequest' />
                            </Form.Item>
                            <Form.Item>
                                <Button type='primary' htmlType="submit" className='w-100' data-testid='button-resetPasswordRequest'>Reset Password</Button>
                            </Form.Item>
                        </Form>
                    ) : (
                        <Text className='w-100 text-center' data-testid='sent-ResetPasswordRequest' type='success'>Email sent! Please check your inbox.</Text>
                    )}
                </Card>
            </Col>
        </div>
    );
}

export default ResetPasswordRequest;
