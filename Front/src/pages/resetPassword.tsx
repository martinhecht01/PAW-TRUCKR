import {Button, Card, Col, Input, message, Typography} from "antd";
import Link from "antd/es/typography/Link";
import { useState} from "react";
import {useSearchParams} from 'react-router-dom';
import {resetPassword} from "../api/userApi.tsx";

const {Text, Title} = Typography;

const ResetPassword: React.FC = () => {

    const [searchParams, setSearchParams] = useSearchParams()
    const hash = searchParams.get('hash');
    const cuit = searchParams.get('cuit');
    const userId = searchParams.get('userid');

    const [ResetState, setResetState] = useState(false);
    const [password, setPassword] = useState('')
    const [confirmPassword, setConfirmPassword] = useState('')

    async function ResetPassword() {
        if (password !== confirmPassword) {
            message.error('Passwords do not match');
            return;
        }
        if (cuit == null || hash == null || userId == null) {
            message.error('Please request a valid reset password link');
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
        <Col span={7} style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
            <Card className='w-100'>
                <div className='w-100 text-center'>
                    <Title level={2}>Reset Password</Title>
                </div>
                {!ResetState ? 
                    <div>
                        <Input.Password placeholder='Password' className='mb-1vh' onChange={(e) => setPassword(e.target.value)}></Input.Password>
                        <Input.Password placeholder='Confirm Password' className='mb-1vh' onChange={(e) => setConfirmPassword(e.target.value)}></Input.Password>
                        <Button type='primary' className='w-100' onClick={ResetPassword}>Reset Password</Button>
                    </div>

                    :

                    <Text className='w-100 text-center'> Password change successfully! <Link href='/login'>Login back</Link></Text>
            
                }
                
            </Card>
        </Col>

    </div>
    );
}

export default ResetPassword;