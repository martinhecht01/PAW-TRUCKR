import React from 'react';
import {Avatar, Button, Card, Popover, Typography} from 'antd';
import '../styles/main.scss';
import '../styles/myAlert.scss';
import {useTranslation} from "react-i18next";
import {UserOutlined} from "@ant-design/icons";

const { Title, Text } = Typography;


const profile: React.FC = () => {

    const {t} = useTranslation();


    return (
        <div>
        <div className="flex-center">
            <Card style={{width:'30%',margin:'3vh'}} title={t("profile.profile")}>
                <div className='flex-center'>
                    <Avatar size={124} icon={<UserOutlined />} />
                </div>
                <Title level={5}>{t("profile.name")}</Title>
                <Text>dfkjdf</Text>
                <Title level={5}>{t('profile.cuit')}</Title>
                <Text>dfkjdf</Text>
                <Title level={5}>{t('profile.email')}</Title>
                <Text>dfkjdf</Text>
                <Button style={{width:'100%', marginTop:'5vh'}} type='primary'>{t("profile.editProfile")}</Button>
            </Card>
            <Card title={t("profile.completedTrips")}>
                <Text>0</Text>
            </Card>
        </div>
        <div>
            <Card title='star - (0 {reviews})'>

            </Card>
        </div>
        </div>
    );
};

export default profile;