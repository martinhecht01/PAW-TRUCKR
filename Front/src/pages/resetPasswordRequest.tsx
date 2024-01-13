import { Button, Card, Col, Input, Typography } from 'antd';
import React, { useState } from 'react';

import '../styles/main.scss';

const ResetPasswordRequest: React.FC = () => {

const [ResetState, setResetState] = useState(false);

function sendRequest() {
    setResetState(true);
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
                        <Input placeholder='CUIT 00-00000000-0' className='mb-1vh mt-1vh'></Input>                    
                        <Button type='primary' className='w-100' onClick={sendRequest}>Reset Password</Button>
                    </div>

                    :

                    <Text className='w-100 text-center' type='success'>Email sent! Please check your inbox</Text>
            
                }
                
            </Card>
        </Col>

    </div>
);
}

export default ResetPasswordRequest;