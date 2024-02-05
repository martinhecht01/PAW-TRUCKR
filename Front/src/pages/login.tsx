import React from 'react';
import { Button, Card, Col, Form, Input, Typography, message } from 'antd';
import { useNavigate } from 'react-router-dom';
import '../styles/main.scss';
import { loginUser } from '../api/userApi';
import { useAuthContext } from '../hooks/authProvider';
import { useTranslation } from 'react-i18next';

const { Text, Title } = Typography;

const Login: React.FC = () => {
    const [form] = Form.useForm();
    const auth = useAuthContext();
    const router = useNavigate();
    const { t } = useTranslation();

    async function loginAction(values: any) {
        const { cuit, password } = values;
        loginUser(cuit, password).then((token) => {
            if(token == null) {
                message.error(t('login.invalidCredentials'));
            } else {
                auth.login(token);
                router('/profile');
            }
        }).catch(() => {
            message.error(t('login.loginFailed'));
        });
    }

    return (
        <>
            <div style={{ height: '60vh' }} className='w-100 flex-center'>
                <Col span={8} style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                    <Card className='w-100'>
                        <Form form={form} layout="vertical" onFinish={loginAction}>
                            <div className='w-100 text-center'>
                                <Title level={2}>{t('login.title')}</Title>
                                <Text>{t('login.newToTruckr')} <a onClick={()=> router('/register')}>{t('login.signUpNow')}</a></Text>
                            </div>
                            
                            <Form.Item name="cuit" className='mt-2vh' rules={[
                                { required: true, message: t('login.cuitRequired') },
                                { pattern: new RegExp('^(20|23|24|25|26|27|30)-[0-9]{8}-[0-9]$'), message: t('login.invalidCUITFormat') }
                            ]}>
                                <Input 
                                    placeholder={t('login.cuitPlaceholder')}
                                    data-testid='cuit-login'
                                />
                            </Form.Item>
                            
                            <Form.Item name="password" rules={[{ required: true, message: t('login.passwordRequired') }]}>
                                <Input.Password 
                                    placeholder={t('login.passwordPlaceholder')}
                                    data-testid='password-login'
                                />
                            </Form.Item>
                            
                            <Form.Item>
                                <a onClick={()=> router('/resetPasswordRequest')} >{t('login.forgotPassword')}</a>
                            </Form.Item>
                            
                            <Form.Item>
                                <Button type='primary' htmlType="submit" className='w-100' data-testid='button-login'>{t('login.loginButton')}</Button>
                            </Form.Item>
                        </Form>
                    </Card>
                </Col>
            </div>
        </>
    );
}

export default Login;
