import React, { useEffect } from 'react';
import {
  Button,
  Card,
  DatePicker,
  Input,
  Form,
  Select,
  message
} from 'antd';
import '../styles/main.scss';
import '../styles/profile.scss';
import { useTranslation } from "react-i18next";
import { getCargoTypes } from "../api/cargoTypeApi.tsx";
import { getCities } from "../api/citiesApi.tsx";
import { createAlert } from "../api/alertApi.tsx";
import { useNavigate } from "react-router-dom";
import dayjs, { Dayjs } from 'dayjs';

const { RangePicker } = DatePicker;


const CreateAlertForm: React.FC = () => {
    const { t } = useTranslation();
    const router = useNavigate();
    const [form] = Form.useForm();

    const [cargoOptions, setCargoOptions] = React.useState<{ label: string, value: string }[]>([]);
    const [cityOptions, setCityOptions] = React.useState<{ label: string, value: string }[]>([]);

    useEffect(() => {
        async function fetchData() {
            const cargoTypes = await getCargoTypes();
            setCargoOptions(cargoTypes.map(type => ({ label: t(`cargoType.${type.toLowerCase()}`), value: type })));

            const cities = await getCities();
            setCityOptions(cities.map(city => ({ label: city.cityName, value: city.cityName })));
        }
        fetchData();
    }, [t]);

    const handleAlertCreation = async (values: any) => {
        const { selectedCity, selectedCargoType, maxVolume, maxWeight, dateRange } = values;
        const departureDate = dateRange[0] ? dateRange[0].format('YYYY-MM-DDTHH:MM:ss') : undefined;
        const arrivalDate = dateRange[1] ? dateRange[1].format('YYYY-MM-DDTHH:MM:ss') : undefined;

        if((maxVolume && maxVolume < 1) || (maxWeight && maxWeight < 1)){
            message.error("Invalid value for max volume or max weight");
            return;
        }
        try {
            await createAlert(Number(maxWeight), Number(maxVolume), departureDate, arrivalDate, selectedCity, selectedCargoType);
            router('/myAlert');
        } catch (e) {
            message.error("Unexpected error. Try again.");
        }
    };

    return (
        <div className="flex-center">
            <Card title={t("myAlert.create")} className="w-50 m-0">
                <Form
                    form={form}
                    layout="vertical"
                    onFinish={handleAlertCreation}
                >
                    <Form.Item
                        name="selectedCargoType"
                        label={t("common.cargoType")}
                        rules={[{ message: t("validation.cargoType.Required") }]}
                    >
                        <Select options={cargoOptions} allowClear />
                    </Form.Item>

                    <Form.Item
                        name="selectedCity"
                        label={t("common.origin") + "*"}
                        rules={[{ required: true, message: t("validation.origin.Required") }]}
                    >
                        <Select
                            showSearch
                            options={cityOptions}
                        />
                    </Form.Item>

                    <Form.Item
                        name="dateRange"
                        label={t("common.departureDate") + "* - " + t("common.arrivalDate")}
                        rules={[{ type: 'array', required: true, message: t("validation.dateRange.Required") }]}
                    >
                        <RangePicker disabledDate={current => current && current.isBefore(dayjs().startOf('day'))}  allowEmpty={[false, true]} className='w-100'/>
                    </Form.Item>

                    <Form.Item
                        name="maxVolume"
                        label={t("common.maxVolume")}
                        rules={[
                            { 
                                required: false, 
                                message: t("validation.Volume.Required"),
                                validator: (_, value) => {
                                    if (!value) {
                                        return Promise.resolve();
                                    }
                                    const numberValue = Number(value);
                                    if (numberValue >= 1 && numberValue <= 1000) {
                                        return Promise.resolve();
                                    }
                                    return Promise.reject(new Error(t("validation.Volume.Range")));
                                }
                            }
                        ]}
                    >
                        <Input type="number" suffix="M3" allowClear />
                    </Form.Item>

                    <Form.Item
                        name="maxWeight"
                        label={t('common.maxWeight')}
                        rules={[
                            { 
                                required: false, 
                                message: t("validation.Weight.Required"),
                                validator: (_, value) => {
                                    if (!value) {
                                        return Promise.resolve();
                                    }
                                    const numberValue = Number(value);
                                    if (numberValue >= 1 && numberValue <= 100000) {
                                        return Promise.resolve();
                                    }
                                    return Promise.reject(new Error(t("validation.Weight.Range")));
                                }
                            }
                        ]}
                    >
                        <Input type="number" suffix="kg" allowClear />
                    </Form.Item>


                    <Form.Item>
                        <Button type="primary" htmlType="submit">{t("myAlert.create")}</Button>
                    </Form.Item>
                </Form>
            </Card>
        </div>
    );
};

export default CreateAlertForm;
