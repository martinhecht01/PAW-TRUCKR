import React from 'react';
import { Button, Card, Col, Form, Input, Radio, Typography, message } from 'antd';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import '../styles/main.scss';
import { createUser } from '../api/userApi';
import { User } from '../models/User';
import { Alert } from '../models/Alert';
import { AxiosError } from 'axios';

const Register: React.FC = () => {
    const [form] = Form.useForm();
    const router = useNavigate();
    const { t } = useTranslation();

    const { Title, Text } = Typography;

    async function createAccountAction(values: any) {
        try {
            const { name, email, cuit, password, confirmPassword, role } = values;
            await createUser(new User(0, name, email, cuit, password, confirmPassword, 0, 0, role, '', [], [], [], '', new Alert(0,0,'0',0,0,new Date,new Date, ''), 0));
            message.success(t('register.accountCreatedSuccessfully'));
            router('/login');
        } catch (error) {
            if (error instanceof AxiosError && error.response) {
                message.error(`${error.response.data.message}`);
            } else {
                message.error(t('register.unexpectedError'));
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
                                <Title level={2}>{t('register.createAccount')}</Title>
                                <Text>{t('register.alreadyAUser')} <a onClick={()=> router('/login')}>{t('register.login')}</a></Text>
                            </div>

                            <Form.Item
                            className='mt-2vh'
                                name="cuit"
                                rules={[
                                    { required: true, message: t('register.pleaseInputYourCUIT') },
                                    { pattern: new RegExp('^(20|23|24|25|26|27|30)-[0-9]{8}-[0-9]$'), message: t('register.invalidCUITFormat') }
                                ]}
                            >
                                <Input placeholder={t('register.cuitPlaceholder')} />
                            </Form.Item>

                            <Form.Item
                                name="name"
                                rules={[
                                    { required: true, message: t('register.pleaseInputYourName') },
                                ]}
                            >
                                <Input placeholder={t('register.namePlaceholder')} />
                            </Form.Item>

                            <Form.Item
                                name="email"
                                rules={[
                                    { required: true, message: t('register.pleaseInputYourEmail') },
                                    { type: 'email', message: t('register.inputNotValidEmail') }
                                ]}
                            >
                                <Input placeholder={t('register.emailPlaceholder')} type='email' />
                            </Form.Item>

                            <Form.Item
                                name="role"
                                rules={[{ required: true, message: t('register.pleaseSelectYourRole') }]}
                            >
                                <Radio.Group>
                                    <Radio.Button value={'TRUCKER'}>{t('register.trucker')}</Radio.Button>
                                    <Radio.Button value={'PROVIDER'}>{t('register.provider')}</Radio.Button>
                                </Radio.Group>
                            </Form.Item>

                            <Form.Item
                                name="password"
                                rules={[
                                    { required: true, message: t('register.pleaseInputYourPassword') },
                                    { min: 6, message: t('register.passwordAtLeastCharacters') }
                                ]}
                            >
                                <Input.Password placeholder={t('register.passwordPlaceholder')} />
                            </Form.Item>
                            
                            <Form.Item
                                name="confirmPassword"
                                dependencies={['password']}
                                rules={[
                                    { required: true, message: t('register.pleaseConfirmYourPassword') },
                                    ({ getFieldValue }) => ({
                                        validator(_, value) {
                                            if (!value || getFieldValue('password') === value) {
                                                return Promise.resolve();
                                            }
                                            return Promise.reject(new Error(t('register.passwordsDoNotMatch')));
                                        },
                                    }),
                                ]}
                            >
                                <Input.Password placeholder={t('register.confirmPasswordPlaceholder')} />
                            </Form.Item>
                                                
                            <Form.Item>
                                <Button type='primary' htmlType="submit" style={{ width: '100%' }}>{t('register.createAccountButton')}</Button>
                            </Form.Item>
                        </Form>
                    </Card>
                </Col>
            </div>
        </>
    );
}

export default Register;
