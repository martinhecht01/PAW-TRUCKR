import React, { useEffect } from 'react';
import {
  Button,
  Card,
  DatePicker,
  Input,
  Form,
  Select,
  Typography,
  message
} from 'antd';
import '../styles/main.scss';
import '../styles/profile.scss';
import { useTranslation } from "react-i18next";
import { getCargoTypes } from "../api/cargoTypeApi.tsx";
import { getCities } from "../api/citiesApi.tsx";
import { createAlert } from "../api/alertApi.tsx";
import { useNavigate } from "react-router-dom";
import { Dayjs } from "dayjs";

const { Title } = Typography;
const { RangePicker } = DatePicker;

type RangeValue = [Dayjs | null, Dayjs | null] | null;

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
                        rules={[{ required: true, message: t("validation.cargoType.Required") }]}
                    >
                        <Select options={cargoOptions} />
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
                        <RangePicker allowEmpty={[false, true]} className='w-100'/>
                    </Form.Item>

                    <Form.Item
                        name="maxVolume"
                        label={t("common.maxVolume")}
                        rules={[
                            { required: true, message: t("validation.Volume.Required") },
                            { type: 'number', min: 1, max: 1000, message: t("validation.Volume.Range"), transform: value => Number(value) }
                        ]}
                    >
                        <Input type="number" suffix="M3" max={1000} min={1} />
                    </Form.Item>

                    <Form.Item
                        name="maxWeight"
                        label={t('common.maxWeight')}
                        rules={[
                            { required: true, message: t("validation.Weight.Required") },
                            { type: 'number', min: 1, max: 100000, message: t("validation.Weight.Range"), transform: value => Number(value) }
                        ]}
                    >
                        <Input type="number" suffix="kg" max={100000}/>
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
