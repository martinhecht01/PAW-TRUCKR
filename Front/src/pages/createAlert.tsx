import React, {useEffect, useState} from 'react';
import {
    Button,
    Card,
    DatePicker, Input, message, Select,
    Typography
} from 'antd';
import '../styles/main.scss';
import '../styles/profile.scss';
import {useTranslation} from "react-i18next";
import {getCargoTypes} from "../api/cargoTypeApi.tsx";
import {getCities} from "../api/citiesApi.tsx";
import {createAlert} from "../api/alertApi.tsx";
import {useNavigate} from "react-router-dom";
import {Dayjs} from "dayjs";


const { Title } = Typography;

const {RangePicker} = DatePicker;

type RangeValue = [Dayjs | null, Dayjs | null] | null;

const CreateAlert: React.FC = () => {

    const {t} = useTranslation();
    const router = useNavigate();



    const [cargoOptions, setCargoOptions] = useState<{label:string,value:string}[]>([]);
    const [cityOptions, setCityOptions] = useState<{label:string,value:string}[]>([])

    const [selectedCity, setSelectedCity] = useState<string>();
    const [selectedCargoType, setSelectedCargoType] = useState<string>();
    const [maxVolume, setMaxVolume] = useState<string>();
    const [maxWeight, setMaxWeight] = useState<string>();
    const [dateRange, setDateRange] = useState<RangeValue>(null);

    async function handleAlertCreation(city: string | undefined, cargoType: string | undefined, maxVolume: string | undefined, maxWeight:string | undefined, departureDate:string | undefined, arrivalDate:string | undefined){
        // if (departureDate == undefined && city == undefined){
        //     message.error("Departure Date and City must be specified")
        //     return;
        // }
        // else if (departureDate == undefined){
        //     message.error("Departure Date must be specified")
        //     return;
        // }
        // else if (city == undefined){
        //     message.error("City must be specified")
        //     return;
        // }

        // if (Number(maxWeight) > 10000 || Number(maxWeight) < 0){
        //     message.error("Max Weight should be between 0 and 10000")
        //     return;
        // }
        // if (Number(maxVolume) > 1000 || Number(maxVolume) < 1){
        //     message.error("Max Volume should be between 1 and 1000")
        //     return;
        // }

        try{
            await createAlert(Number(maxWeight), Number(maxVolume),departureDate, arrivalDate, city, cargoType);
            router('/myAlert');
        }
        catch (e: any) {
            // console.log(e)
            message.error("Unexpected error. Try again.")

        }
    }

    useEffect( () => {
        getCargoTypes().then((cargoTypes) => {
            setCargoOptions(cargoTypes.map((type) => {
                return {
                    label: t(`cargoType.${type.toLowerCase()}`),
                    value: type
                }
            }))
            getCities().then((cities) =>{
                setCityOptions(cities.map((city) => {
                    return {
                        label: city.cityName,
                        value: city.cityName
                    }
                }))
            })
        });


    },[]);


    return (
        <div className="flex-center">
            <Card title={t("myAlert.create")} className="w-50 m-0">
                <div className="flex-center space-around">
                    <div>
                        <Title level={5} className="m-0">{t("common.cargoType")}</Title>
                        <Select className='w-100' options={cargoOptions} onChange={(value) => { setSelectedCargoType(value) }}
                        />

                    </div>
                    <div>
                        <Title level={5} className="m-0">{t("common.origin")}*</Title>
                        <Select className='w-100'
                            onChange={(value) => {setSelectedCity(value)}}
                            options={cityOptions}
                        />
                    </div>
                </div>
                <div className="flex-column space-around">
                    <Title level={5} className=''>{t("common.departureDate")}* - {t("common.arrivalDate")}</Title>
                    <RangePicker className="w-80"
                                 onChange={(val) => {
                                     setDateRange(val);
                                 }}
                                 allowEmpty={[false,true]}
                    ></RangePicker>
                </div>
                <div className="flex-center space-around">
                    <div>
                        <Title level={5}>{t("common.maxVolume")}</Title>
                        <Input
                            className="w-100"
                            type="number"
                            onChange={(e) => setMaxVolume(e.target.value)}
                            maxLength={3}
                            suffix="M3"
                        />
                    </div>
                    <div>
                        <Title level={5}>{t('common.maxWeight')}</Title>
                        <Input
                            type='number'
                            className="w-100"
                            maxLength={6}
                            onChange={(e) => setMaxWeight(e.target.value)}
                            suffix='kg'
                        />
                    </div>
                </div>
                <div className="flex-center" style={{marginTop: '5vh'}}>
                    <Button type="primary" onClick={() => handleAlertCreation(selectedCity,selectedCargoType,maxVolume,maxWeight,dateRange?.[0] ? dateRange[0].format('YYYY-MM-DDTHH:MM:ss') : undefined, dateRange?.[1] ? dateRange[1].format('YYYY-MM-DDTHH:MM:ss'): undefined)}>{t("myAlert.create")}</Button>
                </div>
            </Card>
        </div>
    );
};

export default CreateAlert;