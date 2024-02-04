import React, { useEffect, useState } from "react";
import { Avatar, Button, Card, Col, Form, Input, message, Row, Typography, Upload, UploadProps } from "antd";
import { UploadOutlined, UserOutlined } from "@ant-design/icons";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import '../styles/profile.scss';
import '../styles/main.scss';
import { getClaims, getUserByUrl, updateUser } from "../api/userApi";
import { uploadImage } from "../api/imageApi";
import { User } from "../models/User";

const { Title } = Typography;

const EditProfile: React.FC = () => {
    const { t } = useTranslation();
    const [form] = Form.useForm();
    const [user, setUser] = useState<User>();
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [imageUrl, setImageUrl] = useState<string>('');
    const [selectedFile, setSelectedFile] = useState<File | null>(null);
    const router = useNavigate();

    useEffect(() => {
        const claims = getClaims();

        if (claims == null) {
            router('/login');
        }

        getUserByUrl(claims?.userURL!).then((user) => {
            setUser(user);
            form.setFieldsValue({
                name: user.name,
            });
            setImageUrl(user.imageUrl);
            setIsLoading(false);
        });
    }, [form, router]);

    async function save(values: any) {
        const { name } = values;
        const action = selectedFile
            ? async () => uploadImage(selectedFile)
            : async () => '';

        action().then((imageId) => {
            updateUser(name, imageId, user!.id).then(() => {
                message.success(t("profile.updateSuccess"));
                router('/profile');
            });
        });
    }

    const uploadProps: UploadProps = {
        name: 'file',
        action: 'https://run.mocky.io/v3/435e224c-44fb-4773-9faf-380c5e6a2188',
        headers: {
            authorization: 'authorization-text',
        },
        maxCount: 1,
        onChange(info) {
            if (info.file.status === 'done') {
                message.success(t("profile.fileUploaded"));
                if (info.file.originFileObj instanceof Blob) {
                    const newImageUrl = URL.createObjectURL(info.file.originFileObj);
                    setImageUrl(newImageUrl);
                    setSelectedFile(info.file.originFileObj as File);
                }
            } else if (info.file.status === 'error') {
                message.error(t("profile.fileUploadError"));
            }
        },
    };

    return (
        <div className="space-evenly">
            <Row className='w-80 space-evenly' style={{ alignItems: 'start' }}>
                <Col span={12}>
                    <Card className='w-100' title={<Title level={3}>{t("profile.profile")}</Title>} loading={isLoading}>
                        <Form form={form} layout="vertical" onFinish={save}>
                            <div className='flex-center'>
                                <Avatar size={124} icon={<UserOutlined />} src={imageUrl} />
                            </div>
                            <Title level={5}>{t('profile.picture')}</Title>
                            <Upload {...uploadProps}>
                                <Button icon={<UploadOutlined />}>{t("common.upload")}</Button>
                            </Upload>
                            <Title level={5}>{t("profile.name")}</Title>
                            <Form.Item
                                name="name"
                                rules={[
                                    { required: true, message: 'Please input your name!' },
                                    { pattern: /^[A-Za-z]+(\s[A-Za-z]*)+$/, message: 'Invalid name format!' }
                                ]}
                            >
                                <Input placeholder={t("profile.name")} />
                            </Form.Item>
                            <Title level={5}>{t('profile.cuit')}</Title>
                            <Input disabled defaultValue={user?.cuit!} />
                            
                            <Form.Item>
                                <Button style={{ width: '100%', marginTop: '5vh' }} type='primary' htmlType="submit">{t("common.save")}</Button>
                            </Form.Item>
                        </Form>
                    </Card>
                </Col>
            </Row>
        </div>
    );
};

export default EditProfile;
