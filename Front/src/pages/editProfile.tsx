import { UploadOutlined, UserOutlined } from "@ant-design/icons";
import { Avatar, Button, Card, Col, Input, Row, Typography, Upload, UploadProps, message } from "antd";
import { useTranslation } from "react-i18next";
import '../styles/profile.scss';
import '../styles/main.scss';
import { useState } from "react";
import React from "react";


const {Title, Text} = Typography;

const EditProfile: React.FC = () => {
    
    const {t} = useTranslation();

    const props: UploadProps = {
        name: 'file',
        action: 'https://run.mocky.io/v3/435e224c-44fb-4773-9faf-380c5e6a2188',
        headers: {
          authorization: 'authorization-text',
        },
        maxCount: 1,
        onChange(info) {
          if (info.file.status !== 'uploading') {
            console.log(info.file, info.fileList);
          }
          if (info.file.status === 'done') {
            message.success(`${info.file.name} file uploaded successfully`);
          } else if (info.file.status === 'error') {
            message.error(`${info.file.name} file upload failed.`);
          }
        },
      };

    return (
        <div className="space-evenly">
                <Row className='w-80 space-evenly' style={{alignItems: 'start'}}>
                    <Col span={12}>
                        <Card className='w-100' title={<Title level={3}>{t("profile.profile")}</Title>}>
                            <div className='flex-center'>
                                <Avatar size={124} icon={<UserOutlined />} />
                            </div>
                            {/*TRADUCIR!!!!*/}
                            <Title level={5}>Profile Picture</Title>
                            <Upload {...props}>
                                <Button icon={<UploadOutlined />}>Click to Upload</Button>
                            </Upload>
                            <Title level={5}>{t("profile.name")}</Title>
                            <Input defaultValue="Manuel"></Input>
                            <Title level={5}>{t('profile.cuit')}</Title>
                            <Input disabled defaultValue="123456789"></Input>
                            <Title level={5}>{t('profile.email')}</Title>
                            <Input defaultValue="johndoe@gmail.com" disabled></Input>

                            <Button style={{width:'100%', marginTop:'5vh'}} type='primary'>Save</Button>
                        </Card>
                    </Col>
                </Row>
            </div>
    )
}

export default EditProfile;