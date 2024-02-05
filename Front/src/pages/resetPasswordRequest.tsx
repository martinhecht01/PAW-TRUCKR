import React, { useState } from 'react';
import { Button, Card, Col, Form, Input, Typography, message } from 'antd';
import { useTranslation } from 'react-i18next';

import '../styles/main.scss';
import { resetPasswordRequest } from "../api/userApi";

const ResetPasswordRequest: React.FC = () => {
    const [ResetState, setResetState] = useState(false);
    const [form] = Form.useForm();
    const { t } = useTranslation(); // Initialize useTranslation hook

    async function sendRequest(values: any) {
        const { cuit } = values;
        try {
            await resetPasswordRequest(cuit);
            setResetState(true);
            message.success(t('resetPasswordRequest.emailSentConfirmation'));
        } catch (e) {
            message.error(t('resetPasswordRequest.invalidCUIT'));
        }
    }

    const { Title, Text } = Typography;

    return (
        <div style={{height: '60vh'}} className='w-100 flex-center'>
            <Col span={8} style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                <Card className='w-100'>
                    <div className='w-100 text-center'>
                        <Title level={2}>{t('resetPasswordRequest.title')}</Title>
                    </div>
                    {!ResetState ? (
                        <Form form={form} layout="vertical" onFinish={sendRequest}>
                            <Form.Item
                                name="cuit"
                                rules={[
                                    { required: true, message: t('resetPasswordRequest.cuitRequired') },
                                    { pattern: new RegExp('^(20|23|24|25|26|27|30)-[0-9]{8}-[0-9]$'), message: t('resetPasswordRequest.invalidCUITFormat') }
                                ]}
                            >
                                <Input placeholder={t('resetPasswordRequest.cuitPlaceholder')} data-testid='cuit-resetPasswordRequest' />
                            </Form.Item>
                            <Form.Item>
                                <Button type='primary' htmlType="submit" className='w-100' data-testid='button-resetPasswordRequest'>{t('resetPasswordRequest.resetPasswordButton')}</Button>
                            </Form.Item>
                        </Form>
                    ) : (
                        <Text className='w-100 text-center' data-testid='sent-resetPasswordRequest' type='success'>{t('resetPasswordRequest.emailSentMessage')}</Text>
                    )}
                </Card>
            </Col>
        </div>
    );
}

export default ResetPasswordRequest;
