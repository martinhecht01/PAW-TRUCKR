import React, { useEffect, useState } from 'react';
import {
    Button,
    Card,
    DatePicker,
    Input,
    InputNumber,
    message,
    Upload,
    UploadProps,
    Row,
    Col,
    Select,
    Skeleton,
    Form
} from 'antd';
import '../styles/main.scss';
import '../styles/profile.scss';
import { useTranslation } from "react-i18next";
import { UploadOutlined } from "@ant-design/icons";
import { getCities } from '../api/citiesApi';
import { getCargoTypes } from '../api/cargoTypeApi';
import { useNavigate } from 'react-router-dom';
import { createTrip } from '../api/tripApi';
import { uploadImage } from '../api/imageApi';
import { getClaims } from '../api/userApi';
import dayjs from 'dayjs';

const { RangePicker } = DatePicker;
const { Option } = Select;


const CreateTrip: React.FC = () => {
    const {t} = useTranslation();
    const router = useNavigate();
    const [form] = Form.useForm();

    document.title = t("pageTitles.createTrip");

    const claims = getClaims();

    const [selectedFile, setSelectedFile] = useState<File | null>(null);
    const [loading, setLoading] = useState<boolean>(true); // Start with loading true
    const [cities, setCities] = useState<string[]>([]);
    const [cargoTypes, setCargoTypes] = useState<string[]>([]);

    useEffect(() => {
        try{
            const cities = getCities();
            const cargoTypes = getCargoTypes();

            Promise.all([cities, cargoTypes]).then((values) => {
                setCities(values[0].map((city) => city.cityName));
                setCargoTypes(values[1]);
                setLoading(false);
            })
        } catch (error){
            router('/404')
        }


    }, [])

    /*
    "create": {
      "uploadClick": "Click to Upload",
      "uploadSuccessful": "File uploaded successfully",
      "uploadError": "File uploaded failed",
      "notPng": "File is not a png",
      "requireImageForPublication": "An image is required for the publication",
      "errorCreatingPublication": "Error creating publication"
    },
    */

    async function createTripAction(values: any) {
        try {
            var image = '';

            if(!selectedFile){
                message.error(t("create.requireImageForPublication"));
                return;
            }

            if (selectedFile)
                image = await uploadImage(selectedFile);

            const { licensePlate, cargoType, origin, destination, dateRange, availableVolume, availableWeight, price } = values;
            const trip = await createTrip(licensePlate, availableWeight, availableVolume, price, dateRange[0].format('YYYY-MM-DDTHH:mm:ss'), dateRange[1].format('YYYY-MM-DDTHH:mm:ss'), cargoType, origin, destination, image);
            router('/trips/manage/' + trip.tripId);
        } catch (error) {
            message.error(t("create.errorCreatingPublication"));
        }
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
            message.success(t("create.uploadSuccessful"));
            if (info.file.originFileObj instanceof Blob) {
              setSelectedFile(info.file.originFileObj as File);
          }
          } else if (info.file.status === 'error') {
            message.error(t("create.uploadError"));
          }
        },
    };




    return (
        <div className="flex-center">
            <Skeleton loading={loading}>
                <Form form={form} layout="vertical" onFinish={createTripAction}>
                    <Card title={t("trip.createPublication")} className="w-100">
                        <div className='w-100 text-center flex-center'>
                            <Upload {...props}>
                                <Button className='w-100' icon={<UploadOutlined />}>{t('create.uploadClick')}</Button>
                            </Upload>
                        </div>
                        <Row gutter={16} className='mt-2vh'>
                            {claims?.role === 'TRUCKER' ?
                            <>
                            <Col span={12}>
                                <Form.Item
                                    name="licensePlate"
                                    rules={[
                                        { required: true, message: t('common.licensePlate') },
                                        { pattern: /^([A-Za-z]{3}\\d{3})|([A-Za-z]{2}\\d{3}[A-Za-z]{2})$/, message: t('common.invalidLicensePlate') }
                                    ]}
                                >
                                    <Input placeholder={t("common.licensePlate")} />
                                </Form.Item>
                            </Col>
                            <Col span={12}>
                                <Form.Item
                                    name="cargoType"
                                    rules={[{ required: true, message: t('common.selectCargoType') }]}
                                >
                                    <Select placeholder={t("common.cargoType")}>
                                        {cargoTypes.map((type) => (
                                            <Option key={type} value={type}>{type}</Option>
                                        ))}
                                    </Select>
                                </Form.Item>
                            </Col>
                            </>
                            :
                            <Col span={24}>
                                <Form.Item
                                    name="cargoType"
                                    rules={[{ required: true, message: t('common.selectCargoType') }]}
                                >
                                    <Select placeholder={t("common.cargoType")}>
                                        {cargoTypes.map((type) => (
                                            <Option key={type} value={type}>{type}</Option>
                                        ))}
                                    </Select>
                                </Form.Item>
                            </Col>        
                            }
                        </Row>
                        <Row gutter={16}>
                            <Col span={12}>
                                <Form.Item
                                    name="origin"
                                    rules={[{ required: true, message: t('common.selectOrigin') }]}
                                >
                                    <Select placeholder={t("common.origin")} showSearch>
                                        {cities.map((city) => (
                                            <Option key={city} value={city}>{city}</Option>
                                        ))}
                                    </Select>
                                </Form.Item>
                            </Col>
                            <Col span={12}>
                                <Form.Item
                                    name="destination"
                                    rules={[{ required: true, message: t('common.selectDestination') }]}
                                >
                                    <Select placeholder={t('common.destination')} showSearch>
                                        {cities.map((city) => (
                                            <Option key={city} value={city}>{city}</Option>
                                        ))}
                                    </Select>
                                </Form.Item>
                            </Col>
                        </Row>
                        <Form.Item
                            name="dateRange"
                            rules={[{ required: true, message: t('common.selectDateRange') }]}
                        >
                            <RangePicker disabledDate={current => current && current.isBefore(dayjs().startOf('day'))} className='w-100' placeholder={[t('common.from'), t('common.to')]} />
                        </Form.Item>
                        <Row gutter={16}>
                            <Col span={8}>
                                <Form.Item
                                    name="availableVolume"
                                    rules={[{ required: true, message: t('common.enterVolume'), type: 'number', min: 1 }]}
                                >
                                    <InputNumber placeholder={t("common.availableVolume")} className='w-100' min={1} max={1000} />
                                </Form.Item>
                            </Col>
                            <Col span={8}>
                                <Form.Item
                                    name="availableWeight"
                                    rules={[{ required: true, message: t('common.enterWeight'), type: 'number', min: 50 }]}
                                >
                                    <InputNumber placeholder={t('common.availableWeight')} className='w-100' min={50} max={100000} />
                                </Form.Item>
                            </Col>
                            <Col span={8}>
                                <Form.Item
                                    name="price"
                                    rules={[{ required: true, message: t('common.enterPrice'), type: 'number', min: 1, max: 1000000 }]}
                                >
                                    <InputNumber placeholder={t("common.suggestedPrice")} className='w-100' min={1} max={1000000} />
                                </Form.Item>
                            </Col>
                        </Row>
                        <Form.Item>
                            <Button type="primary" htmlType="submit" className='w-100'>{t("trip.create")}</Button>
                        </Form.Item>
                    </Card>
                </Form>
            </Skeleton>
        </div>
    );
};

export default CreateTrip;