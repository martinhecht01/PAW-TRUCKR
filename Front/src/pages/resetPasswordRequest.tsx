import {Button, Card, Col, Input, message, Typography} from 'antd';
import React, { useState } from 'react';

import '../styles/main.scss';
import {resetPasswordRequest} from "../api/userApi.tsx";

const ResetPasswordRequest: React.FC = () => {

    const [cuit, setCuit] = useState('');

    const [ResetState, setResetState] = useState(false);

    async function sendRequest() {
        try {
            await resetPasswordRequest(cuit);
            setResetState(true);
        }
        catch (e) {
            message.error('Invalid cuit')
        }
    }

    const {Title, Text} = Typography;

    return (
        <div style={{height: '60vh'}} className='w-100 flex-center'>
            <Col span={7} style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                <Card className='w-100'>
                    <div className='w-100 text-center'>
                        <Title level={2}>Reset Password</Title>
                    </div>
                    {!ResetState ?
                        <div>
                            <Input placeholder='CUIT 00-00000000-0' data-testid='cuit-resetPasswordRequest' className='mb-1vh mt-1vh' onChange={(e) => setCuit(e.target.value)}></Input>
                            <Button type='primary' className='w-100' data-testid='button-resetPasswordRequest' onClick={sendRequest}>Reset Password</Button>
                        </div>

                        :

                    <Text className='w-100 text-center' data-testid='sent-ResetPasswordRequest' type='success'>Email sent! Please check your inbox</Text>
            
                }
                
            </Card>
        </Col>

        </div>
    );
}

export default ResetPasswordRequest;