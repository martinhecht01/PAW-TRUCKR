import React from 'react';
import {
    Button,
    Card, Cascader,
    DatePicker,
    Input,
    InputNumber,
    message,
    Typography,
    Upload,
    UploadProps
} from 'antd';
import '../styles/main.scss';
import '../styles/profile.scss';
import {useTranslation} from "react-i18next";
import {UploadOutlined} from "@ant-design/icons";
import type { DefaultOptionType } from 'antd/es/cascader';


const { Title, Text } = Typography;

const CreateTrip: React.FC = () => {

    const {t} = useTranslation();


    const filter = (inputValue: string, path: DefaultOptionType[]) =>
        path.some(
            (option) => (option.label as string).toLowerCase().indexOf(inputValue.toLowerCase()) > -1,
        );

    interface Option {
        value: string;
        label: string;
        children?: Option[];
        disabled?: boolean;
    }

    //TODO: llenar con ciudades de backend
    const cityOptions: Option[] = [
        {
            value: 'Buenos Aires',
            label: 'Buenos Aires',
        },
        {
            value: 'Cordoba',
            label: 'Cordoba',
        },
        {
            value: 'Rosario',
            label: 'Rosario',
        }
    ];


    const cargoOptions: Option[] = [
        {
            value: 'Refrigerated',
            label: t('cargoType.refrigerated'),
        },
        {
            value: 'Normal',
            label: t("cargoType.normal"),
        },
        {
            value: 'Hazardous',
            label: t('cargoType.hazardous'),
        },
    ];

    const props: UploadProps = {
        name: 'file',
        action: 'https://run.mocky.io/v3/435e224c-44fb-4773-9faf-380c5e6a2188',
        headers: {
            authorization: 'authorization-text',
        },
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
        <div className="flex-center">
            <Card title={t("trip.create")} className="w-50">
                <div className="flex-center space-around">
                    <Upload {...props}>
                        <Button icon={<UploadOutlined/>}>Click to Upload</Button>
                    </Upload>
                </div>
                <div className="flex-center space-around">
                    <div>
                        <Title level={5}>{t("common.licensePlate")}</Title>
                        <Input placeholder={t("common.licensePlate")}/>
                    </div>
                    <div>
                        <Title level={5}>{t("common.cargoType")}</Title>
                        <Cascader
                            options={cargoOptions}
                        />
                    </div>
                </div>
                <div className="flex-center space-around">
                    <div>
                        <Title level={5}>{t("common.origin")}</Title>
                        <Cascader
                            options={cityOptions}
                            showSearch={{filter}}
                        />
                    </div>
                    <div>
                        <Title level={5}>{t('common.destination')}</Title>
                        <Cascader
                            options={cityOptions}
                            showSearch={{ filter }}
                        />
                    </div>
                </div>
                <div className="flex-center space-around">
                    <div>
                        <Title level={5}>{t("common.departureDate")}</Title>
                        <DatePicker></DatePicker>
                    </div>
                    <div>
                        <Title level={5}>{t("common.arrivalDate")}</Title>
                        <DatePicker></DatePicker>
                    </div>
                </div>
                <div className="flex-center space-around">
                    <div>
                        <Title level={5}>{t("common.availableVolume")}</Title>
                        <InputNumber
                            min={0}
                            formatter={(value) => `${value} m3`}
                            parser={(value) => value!.replace('m3', '')}
                        />
                    </div>
                    <div>
                        <Title level={5}>{t('common.availableWeight')}</Title>
                        <InputNumber
                            min={0}
                            formatter={(value) => `${value} kg`}
                            parser={(value) => value!.replace('kg', '')}
                        />
                    </div>
                    <div>
                        <Title level={5}>{t("common.suggestedPrice")}</Title>
                        <InputNumber
                            formatter={(value) => `$ ${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')}
                            parser={(value) => value!.replace(/\$\s?|(,*)/g, '')}
                            min={1}
                        />
                    </div>
                </div>
                <div className="flex-center" style={{marginTop: '5vh'}}>
                    <Button type="primary">{t("trip.create")}</Button>
                </div>
            </Card>
        </div>
    );
};

export default CreateTrip;