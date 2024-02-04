import { UploadOutlined, UserOutlined } from "@ant-design/icons";
import { Avatar, Button, Card, Col, Input, Row, Typography, Upload, UploadProps, message } from "antd";
import { useTranslation } from "react-i18next";
import '../styles/profile.scss';
import '../styles/main.scss';
import { useEffect, useState } from "react";
import React from "react";
import { User } from "../models/User";
import { getClaims, getUserByUrl, updateUser } from "../api/userApi";
import { useNavigate } from "react-router-dom";
import { uploadImage } from "../api/imageApi";


const {Title} = Typography;

const EditProfile: React.FC = () => {
    
    const {t} = useTranslation();

    const [user, setUser] = useState<User>()
    const [name, setName] = useState<string>('');
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [imageUrl, setImageUrl] = useState<string>('');
    const [selectedFile, setSelectedFile] = useState<File | null>(null);

    const router = useNavigate();

    useEffect(() => {
      const claims = getClaims();

      if (claims == null){
        router('/login');
      }

      getUserByUrl(claims?.userURL!).then((user) => {
        setUser(user);
        setName(user.name);
        setImageUrl(user.imageUrl);
        setIsLoading(false);

      })
    }, [])

    async function save(){
      selectedFile? uploadImage(selectedFile).then((id) => 
        updateUser(name ? name : '', id, user!.id).then(() => {
          message.success('Profile updated successfully');
          router('/profile');
        })
      ) : updateUser(name ? name : '', '', user!.id).then(() => {
        message.success('Profile updated successfully');
        router('/profile');
      })
    }

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
          if (info.file.originFileObj instanceof Blob) {
            const newImageUrl = URL.createObjectURL(info.file.originFileObj);
            setImageUrl(newImageUrl);
            setSelectedFile(info.file.originFileObj as File);
        }
        } else if (info.file.status === 'error') {
          message.error(`${info.file.name} file upload failed.`);
        }
      },
    };

    return (
        <div className="space-evenly">
                <Row className='w-80 space-evenly' style={{alignItems: 'start'}}>
                    <Col span={12}>
                        <Card className='w-100' title={<Title level={3}>{t("profile.profile")}</Title>} loading={isLoading}>
                            <div className='flex-center'>
                                <Avatar size={124} icon={<UserOutlined />}  src={imageUrl}/>
                            </div>
                            {/*TRADUCIR!!!!*/}
                            <Title level={5}>Profile Picture</Title>
                            <Upload {...props}>
                                <Button icon={<UploadOutlined />}>Click to Upload</Button>
                            </Upload>
                            <Title level={5}>{t("profile.name")}</Title>
                            <Input value={name} onChange={(e) => setName(e.target.value)}></Input>
                            <Title level={5}>{t('profile.cuit')}</Title>
                            <Input disabled defaultValue={user?.cuit!}></Input>
                            {/* <Title level={5}>{t('profile.email')}</Title>
                            <Input defaultValue="johndoe@gmail.com" disabled></Input> */}

                            <Button style={{width:'100%', marginTop:'5vh'}} type='primary' onClick={save}>Save</Button>
                        </Card>
                    </Col>
                </Row>
            </div>
    )
}

export default EditProfile;