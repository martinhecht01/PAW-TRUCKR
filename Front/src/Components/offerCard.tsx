import React from 'react';
import {Avatar, Button, Card, Typography} from 'antd';
import {ArrowRightOutlined, StarFilled, UserOutlined} from "@ant-design/icons";

import '../styles/main.scss'
import TextArea from 'antd/es/input/TextArea';

const { Title, Text } =Typography;

export type OfferCardProps = {
    from: string;
    to: string;
    dateFrom: Date;
    description: string;
    dateTo: Date;
    userImg: string;
    reviewScore: number;
    price: number;
    id: number;
    onCancel: (id: number) => void;
};


const OfferCard: React.FC<OfferCardProps> = ({ from,to,dateFrom,dateTo,userImg ,reviewScore,price, id, onCancel, description}) => {

    return (
        <Card bordered className='mt-1vh'>
            <div style={{display:"flex", alignItems:"center",justifyContent:"space-between",textAlign:'center',width:'70%',margin:'0 auto'}}>
                <div style={{display:'block'}}>
                    <Title style={{marginTop:'0vh'}} level={5} className='offerTitle'>{from}</Title>
                    <p style={{margin:'0vh'}}>{(new Date(dateFrom.toString())).toDateString()}</p>
                </div>
                <ArrowRightOutlined style={{fontSize:'175%'}}></ArrowRightOutlined>
                <div style={{display:'block'}}>
                    <Title style={{marginTop:'0vh'}} level={5} className='offerTitle'>{to}</Title>
                    <p style={{margin:'0vh'}}>{(new Date(dateTo.toString())).toDateString()}</p>
                </div>
                <Avatar size={64} src={userImg} icon={<UserOutlined/>}/>
                <div style={{display:'block'}}>
                    <div style={{display:'flex',alignItems:'center'}}>
                        <StarFilled style={{margin:'0vh', marginRight:'1vh'}}/>
                        <Text>{reviewScore === 0 ? '-' : reviewScore}</Text>
                    </div>
                </div>
                <Title level={5} style={{margin:'0vh'}}>${price}</Title>
                <Button type="dashed" danger={true} onClick={() => onCancel(id)}>Cancel</Button>
            </div>
            <div className='w-100 flex-center mt-2vh'>
                <div className='w-50'>
                    <TextArea value={description} disabled/>
                </div>
            </div>
        </Card>
    );
}
export default OfferCard;