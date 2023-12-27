import {Card} from "antd";
import React from "react";
import { Typography } from 'antd';
const {Title} = Typography;

const LandingCard: React.FC<{ title: string; subtitle: string }> = ({ title, subtitle }) => {
    return (
        <Card style={{ textAlign: 'center', width: '80%', marginBottom:'1vh' }}>
            <Title style={{ marginTop: '0vh' }} level={2}>
                <b>{title}</b>
            </Title>
            <Title level={5}>{subtitle}</Title>
        </Card>
    );
};

export default LandingCard;