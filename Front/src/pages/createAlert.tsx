import React, {useState} from 'react';
import {
    Button,
    Card, Cascader,
    DatePicker, Image,
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
import {RcFile} from "antd/es/upload";


const { Title, Text } = Typography;


const CreateAlert: React.FC = () => {

    const {t} = useTranslation();

    const [uploadPreviewSrc, setUploadPreviewSrc] = useState('');

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

    return (
        <div className="flex-center">
            <Card title={t("alert.create")} className="w-50 m-0">
                <div className="flex-center space-around">
                    <div>
                        <Title level={5} className="m-0">{t("common.cargoType")}</Title>
                        <Cascader
                            options={cargoOptions}
                        />
                    </div>
                    <div>
                        <Title level={5} className="m-0">{t("common.origin")}</Title>
                        <Cascader
                            options={cityOptions}
                            showSearch={{filter}}
                        />
                    </div>
                </div>
                <div className="flex-center space-around">
                    <div>
                        <Title level={5}>{t("common.departureDate")}</Title>
                        <DatePicker showTime></DatePicker>
                    </div>
                    <div>
                        <Title level={5}>{t("common.arrivalDate")}</Title>
                        <DatePicker showTime></DatePicker>
                    </div>
                </div>
                <div className="flex-center space-around">
                    <div>
                        <Title level={5}>{t("common.maxVolume")}</Title>
                        <InputNumber
                            className="w-100"
                            min={0}
                            formatter={(value) => `${value} m3`}
                            parser={(value) => value!.replace('m3', '')}
                        />
                    </div>
                    <div>
                        <Title level={5}>{t('common.maxWeight')}</Title>
                        <InputNumber
                            className="w-100"
                            min={1}
                            formatter={(value) => `${value} kg`}
                            parser={(value) => value!.replace('kg', '')}
                        />
                    </div>
                </div>
                <div className="flex-center" style={{marginTop: '5vh'}}>
                    <Button type="primary">{t("alert.create")}</Button>
                </div>
            </Card>
        </div>
    );
};

export default CreateAlert;