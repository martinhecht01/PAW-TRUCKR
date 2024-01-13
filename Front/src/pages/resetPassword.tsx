import { Button, Card, Col, Input, Typography } from "antd";
import Link from "antd/es/typography/Link";
import { useState} from "react";

const {Text, Title} = Typography;

const ResetPassword: React.FC = () => {

    const [ResetState, setResetState] = useState(false);

    const ResetPassword = () => {
        setResetState(true);
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
                        <Input.Password placeholder='Password' className='mb-1vh'></Input.Password>
                        <Input.Password placeholder='Confirm Password' className='mb-1vh'></Input.Password>                  
                        <Button type='primary' className='w-100' onClick={ResetPassword}>Reset Password</Button>
                    </div>

                    :

                    <Text className='w-100 text-center'> Password change successfully! <Link>Login back</Link></Text>
            
                }
                
            </Card>
        </Col>

    </div>
    );
}

export default ResetPassword;