import React, { useEffect, useState } from 'react';
import {
    Button,
    Card, Cascader,
    DatePicker, Image,
    Input,
    InputNumber,
    message,
    Typography,
    Upload,
    UploadProps,
    Row,
    Col,
    Select,
    Skeleton
} from 'antd';
import '../styles/main.scss';
import '../styles/profile.scss';
import { useTranslation } from "react-i18next";
import { UploadOutlined } from "@ant-design/icons";
import { RcFile } from "antd/es/upload";
import { getCities } from '../api/citiesApi';
import { getCargoTypes } from '../api/cargoTypeApi';
import { useNavigate } from 'react-router-dom';
import { createTrip } from '../api/tripApi';
import { Trip } from '../models/Trip';
import { uploadImage } from '../api/imageApi';
import {Dayjs} from 'dayjs';
import axios, { AxiosError } from 'axios';

const { Title, Text } = Typography;

const {RangePicker} = DatePicker;


type RangeValue = [Dayjs | null, Dayjs | null] | null;


const CreateTrip: React.FC = () => {
    const {t} = useTranslation();
    const router = useNavigate();

    const [imageUrl, setImageUrl] = useState<string>('');
    const [selectedFile, setSelectedFile] = useState<File | null>(null);
    const [loading, setLoading] = useState<boolean>(true); // Start with loading true
    const [cities, setCities] = useState<string[]>([]);
    const [cargoTypes, setCargoTypes] = useState<string[]>([]);
    const [licensePlate, setLicensePlate] = useState<string>('');
    const [cargoType, setCargoType] = useState<string>('');
    const [origin, setOrigin] = useState<string>('');
    const [destination, setDestination] = useState<string>('');
    const [dateRange, setDateRange] = useState<RangeValue>(null);
    const [availableVolume, setAvailableVolume] = useState<number>(0);
    const [availableWeight, setAvailableWeight] = useState<number>(0);
    const [price, setPrice] = useState<number>(0);

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

    async function createTripAction(){
        try{
            var image = '';

            if(selectedFile)
                image = await uploadImage(selectedFile);

            const trip = await createTrip(licensePlate, availableWeight, availableVolume, price, dateRange?.[0] ? dateRange[0].format('YYYY-MM-DDTHH:MM:ss') : '', dateRange?.[1] ? dateRange[1].format('YYYY-MM-DDTHH:MM:ss') : '', cargoType, origin, destination, image);
            router('/trips/manage/' + trip.tripId);
        } catch (error) {
            if (axios.isAxiosError(error)) {
                // Log the error for debugging
                console.log(error);
    
                if (error.response && Array.isArray(error.response.data)) {
                    error.response.data.forEach(err => {
                        message.error(err.message);
                    });
                } else if (error.response && error.response.data && typeof error.response.data === 'object') {
                    // Handle single error object
                    message.error(error.response.data.message || "An error occurred, but the message was not specified.");
                } else {
                    // Handle other cases where error format is unexpected
                    message.error("An unexpected error occurred");
                }
            } else {
                // Non-Axios error
                console.error("A non-Axios error occurred:", error);
                message.error("An unexpected error occurred");
            }
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
        <div className="flex-center">
            <Skeleton loading={loading}>
                <Card title={t("trip.create")} className="w-50">
                    <Row className="w-100 flex-center mt-1vh">
                        <Col span={6}>
                            <Upload {...props}>
                            <Button className='w-100' icon={<UploadOutlined />}>Click to Upload Image</Button>
                            </Upload>
                        </Col>
                    </Row>
                    <Row className="w-100 space-around">
                        <Col span={11}>
                            <Title level={5}>{t("common.licensePlate")}</Title>
                            <Input className='w-100' onChange={(e) => setLicensePlate(e.target.value)}/>
                        </Col>
                        <Col span={11}>
                            <Title level={5}>{t("common.cargoType")}</Title>
                            <Select className='w-100' onChange={(value) => setCargoType(value)}>
                                {cargoTypes.map((cargoType) => <Select.Option value={cargoType}>{cargoType}</Select.Option>)}
                            </Select>
                        </Col>
                    </Row>
                    <Row className="w-100 space-around">
                        <Col span={11}>
                            <Title level={5}>{t("common.origin")}</Title>
                            <Select className='w-100' onChange={(value) => setOrigin(value)}>
                                {cities.map((city) => <Select.Option value={city}>{city}</Select.Option>)}
                            </Select>
                        </Col>
                        <Col span={11}>
                            <Title level={5}>{t('common.destination')}</Title>
                            <Select className='w-100' onChange={(value) => setDestination(value)}>
                                {cities.map((city) => <Select.Option value={city}>{city}</Select.Option>)}
                            </Select>
                        </Col>
                    </Row>
                    <Row className="w-100 space-around">
                        <Col span={23}>
                            <Title level={5}>{t("common.departureDate")}</Title>
                            <RangePicker className='w-100' onChange={(dates) => setDateRange(dates)}/>
                        </Col>
                    </Row>
                    <Row className="w-100 space-around">
                        <Col span={7}>
                            <Title level={5}>{t("common.availableVolume")}</Title>
                            <InputNumber type='number' className='w-100' min={0} suffix='M3' onChange={(value) => setAvailableVolume(value ? value : 1)}/>
                        </Col>
                        <Col span={7}>
                            <Title level={5}>{t('common.availableWeight')}</Title>
                            <InputNumber type='number' className='w-100' min={1} suffix='Kg' onChange={(value) => setAvailableWeight(value ? value : 1)} />
                        </Col>
                        <Col span={7}>
                            <Title level={5}>{t("common.suggestedPrice")}</Title>
                            <InputNumber type='number' className='w-100' prefix='$' min={1} onChange={(value) => setPrice(value ? value : 0)} />
                        </Col>
                    </Row>
                    <Row className="flex-center w-100" style={{marginTop: '5vh'}}>
                        <Col span={6}>
                            <Button type="primary" className='w-100' onClick={createTripAction}>{t("trip.create")}</Button>
                        </Col>
                    </Row>
                </Card>
            </Skeleton>
        </div>
    );
};

export default CreateTrip;
