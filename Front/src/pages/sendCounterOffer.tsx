import { Avatar, Badge, Button, Card, Col, Form, Image, Input, InputNumber, Row, Skeleton, Typography, message } from "antd";
import { useEffect, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { User } from "../models/User";
import { Publication } from "../models/Publication";
import { Offer } from "../models/Offer";
import { useTranslation } from "react-i18next";
import { getPublicationById } from "../api/tripApi";
import { getClaims, getUserByUrl } from "../api/userApi";
import { createCounterOffer, getOffer } from "../api/offerApi";
import { ArrowRightOutlined, StarFilled, UserOutlined } from "@ant-design/icons";

const { TextArea } = Input;
const { Title, Text } = Typography;

const SendCounterOffer: React.FC = () => {
    const [form] = Form.useForm();
    const [searchParams] = useSearchParams();
    const [publication, setPublication] = useState<Publication>();
    const [user, setUser] = useState<User>();
    const [offer, setOffer] = useState<Offer>();
    const [isLoading, setIsLoading] = useState<boolean>(true);

    const { t } = useTranslation();
    const router = useNavigate();

    useEffect(() => {
        const tripId = searchParams.get('tripId');
        const offerId = searchParams.get('offerId');
        
        if (!tripId || !offerId) {
            router('/404');
        }

        getPublicationById(tripId!).then((publication) => {
            setPublication(publication);
            getUserByUrl(publication.creator).then((user) => {
                setUser(user);
                getOffer(Number.parseInt(offerId!)).then((offer) => {
                    setOffer(offer);
                    form.setFieldsValue({
                        price: offer.price,
                        description: ''
                    });
                    setIsLoading(false);
                });
            });
        });
    }, [form, router, searchParams]);

    const onFinish = async (values: { description: string; price: number }) => {
        const claims = getClaims();

        if (claims == null) {
            router('/login');
        }

        await createCounterOffer(Number.parseInt(searchParams.get('tripId')!), values.price, values.description, Number.parseInt(searchParams.get('offerId')!)).then(() => {
            message.success('Offer sent successfully');
            router('/sentOffers');
        }).catch(() => {
            message.error('Failed to send offer');
        });
    };

    return (
         <div>
         <Row className='space-around'>
             <Skeleton loading={isLoading}>
                 <Col span={8}>
                     <div>
                         <Badge.Ribbon text={<Title level={5} style={{color: 'white', margin: 3}}>{publication?.type}</Title>}  color="blue">
                                <Image
                                    style={{ width: '100%',
                                    height: '450px',
                                    objectFit: 'cover',
                                    objectPosition: 'center center'}}
                                    alt="example"
                                    src={publication?.image}
                                    preview={false}
                                />
                         </Badge.Ribbon>
                     </div>
                     <Card className="mt-5">
                         <Row className="space-evenly">
                             <Col span={8} className="text-center">
                                 <Title level={4}>{publication?.origin}</Title>
                                 <Text>{publication?.departureDate ? new Date(publication.departureDate).toDateString() : ''}</Text>
                             </Col>
                             <Col span={8} className="flex-center flex-column">
                                 <div className="w-100 flex-center">
                                     <div style={{border: '1px solid black', width: '100%'}}/><ArrowRightOutlined/>
                                 </div>                            
                             </Col>
                             <Col span={8}  className="text-center">
                                 <Title level={4}>{publication?.destination}</Title>
                                 <Text>{publication?.arrivalDate ? new Date(publication.arrivalDate).toDateString() : ''}</Text>
                             </Col>
                         </Row>
                     </Card>
                     <Row className="mt-5 space-between">
                         <Col span={11}>
                             <Card className="w-100 text-center">
                                 <Title level={3}>{publication?.weight} Kg</Title>
                                 <Text>Weight</Text>
                             </Card>
                         </Col>
                         <Col span={11}>
                             <Card className="w-100 text-center">
                                 <Title level={3}>{publication?.volume} M3</Title>
                                 <Text>Volume</Text>
                             </Card>
                         </Col>
                     </Row>
                 </Col>
                 <Col span={8}>
                     <Card hoverable onClick={() => router(`/profile/${user?.id}`)}>
                         <Row className='space-around aling-center'>
                             <Col span={8} className='flex-center'>
                                 <Avatar size='large' src={user?.imageUrl} icon={<UserOutlined/>}></Avatar>
                             </Col>
                             <Col span={8} className='flex-center'>
                                 <Title level={4}>{user?.name}</Title>
                             </Col>
                             <Col span={8} className='flex-center'>
                                 <Title level={4}><StarFilled/> {user?.rating == 0 ? '-' : Number(user?.rating ? user?.rating : 0).toFixed(1)}</Title>
                             </Col>
                         </Row>

                         <div>
                             <Title level={5}>{t('manage.description')}</Title>
                             <TextArea value={offer?.description} disabled/>
                         </div>
                         <div className='mt-2vh'>
                             <Title level={5}>{t('common.offeredPrice')}</Title>
                             <Title level={5}>${offer?.price}</Title>
                         </div>
                     </Card>
                     <Form form={form} onFinish={onFinish}>
                            <Card title="Counter-offer" className='mt-2vh w-100'>
                                <Form.Item
                                    name="description"
                                    rules={[
                                        { required: true, message: t('validation.NotNull') },
                                        { min: 1, max: 250, message: t('validation.Description') }
                                    ]}
                                >
                                    <TextArea rows={4} className="w-100" placeholder={t('manage.description')} />
                                </Form.Item>
                                <Form.Item
                                    name="price"
                                    rules={[
                                        { required: true, message: t('validation.NotNull') },
                                        { type: 'number', min: 1, message: t('validation.Price.Min') }
                                    ]}
                                >
                                    <InputNumber
                                        className="w-100"
                                        min={1}
                                        max={100000}
                                        placeholder={t('common.offeredPrice')}
                                        prefix="$"
                                    />
                                </Form.Item>
                                <Button type="primary" htmlType="submit" className='mt-2vh'>{t('common.sendOffer')}</Button>
                            </Card>
                        </Form>
                 </Col>
             </Skeleton>
         </Row>
     </div>
    );
}

export default SendCounterOffer;
