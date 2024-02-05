import { Button, Card, Col, DatePicker, Divider, Input, Pagination, Row, Select, Skeleton, Slider, Typography } from 'antd';
import '../styles/main.scss';
import { ArrowRightOutlined } from '@ant-design/icons';
import { useEffect, useState } from 'react';
import { getCities } from '../api/citiesApi';
import TripCard, { TripCardProps } from '../Components/tripCard';
import { getPublications } from '../api/tripApi';
import dayjs, { Dayjs } from 'dayjs';
import { getCargoTypes } from '../api/cargoTypeApi';
import { useTranslation } from 'react-i18next';
import {useNavigate, useSearchParams} from "react-router-dom";


const {Title, Text} = Typography;
const SearchTrips: React.FC = () => {

    const formatter = (value: number | undefined) => `$${value}`;

    const {t} = useTranslation();

    document.title = t('pageTitles.searchTrips');

    type RangeValue = [Dayjs | null, Dayjs | null] | null;

    const router = useNavigate();

    //Ver que onda el tema type
    
    const [search, setSearch] = useState<boolean>(true);
    const [cargoTypes, setCargoTypes] = useState<Array<string>>([]);
    const [cities, setCities] = useState<Array<string>>([]);
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [publications, setPublications] = useState<Array<TripCardProps>>([]);


    const [searchParams] = useSearchParams();
    let origin = searchParams.get('origin')?? '';
    let destination = searchParams.get('destination') ?? '';
    let weight = searchParams.get('weight') ?? '1';
    let volume = searchParams.get('volume') ?? '1';
    let type = searchParams.get('type') ?? '';
    let departureDate = searchParams.get('departureDate') ?? '';
    let arrivalDate = searchParams.get('arrivalDate') ?? '';
    let minPrice = searchParams.get('minPrice') ?? '0';
    let maxPrice = searchParams.get('maxPrice') ?? '1000000';
    let page = searchParams.get('page') ?? '1';
    let pageSize = searchParams.get('pageSize') ?? '12';
    let maxPage = searchParams.get('maxPage') ?? '0';

    const handleOriginChange = (value: string) => origin = value;
    const handleDestinationChange = (value: string) => destination = value;
    const handleWeightChange = (e: React.ChangeEvent<HTMLInputElement>) => weight = e.target.value;
    const handleVolumeChange = (e: React.ChangeEvent<HTMLInputElement>) => volume = e.target.value;
    const handleCargoTypeChange = (value: string) => type = value;

    const handlePriceRangeChange = (value: number | number[]) => {
        if (Array.isArray(value) && value.length === 2) {
            // setPriceRange(value as [number, number]);
            minPrice = value[0].toString();
            maxPrice = value[1].toString();
        }
    };

    const handleDatesChange = (dates: RangeValue) => {
        if (dates == null){
            return;
        }
        if(dates[0] && dates[1]){
            departureDate = dates[0].format('YYYY-MM-DDTHH:MM:ss');
            arrivalDate = dates[1].format('YYYY-MM-DDTHH:MM:ss');
        }
        else if (dates[0]){
            departureDate = dates[0].format('YYYY-MM-DDTHH:MM:ss');
        }
        else if (dates[1]){
            arrivalDate = dates[1].format('YYYY-MM-DDTHH:MM:ss');
        }
    }

    const handlePaginationChange = (pagex: number, pageSizex?: number) => {
        page = pagex.toString();
        if (pageSizex) pageSize = pageSizex.toString();
    };

    
    useEffect(() => {
        setIsLoading(true);
        if(cities.length === 0)
            getCities().then((cities) => {
                setCities(cities.map((city) => city.cityName));
                getCargoTypes().then((cargoTypes) => {
                    setCargoTypes(cargoTypes);
                    setIsLoading(false);
                })
            })
        else
            searchAction()
    }, [page, pageSize, origin, destination, weight, volume, type, departureDate, arrivalDate, minPrice, maxPrice])

    async function searchAction(){
        setIsLoading(true);
        
        if(weight == null || Number(weight) < 1){
            weight = '1';
        }

        if(volume == null || Number(volume) < 1){
            volume = '1';
        }

        getPublications('', 'TRIP', departureDate,arrivalDate, 'ACTIVE', Number(volume), Number(weight), type, origin, destination, Number(minPrice), Number(maxPrice), Number(page), Number(pageSize), 'departureDate ASC').then((trips) => {
            setPublications(trips.map((publication) => {
                return {
                    type: 'trip',
                    from: publication.origin,
                    to: publication.destination,
                    fromDate: publication.departureDate,
                    toDate: publication.arrivalDate,
                    weight: publication.weight,
                    volume: publication.volume,
                    price: publication.price,
                    image: publication.image,
                    cargoType: publication.type,
                    id: publication.tripId,
                    clickUrl: '/trips'
                }
            }))
            if(trips.length > 0)
                maxPage = trips[0].maxPage ? trips[0].maxPage : '1';
            setSearch(false)
            setIsLoading(false);
        })
        router('/searchTrips?origin='+origin+'&destination='+destination+'&weight='+weight+'&volume='+volume+'&type='+type+'&departureDate='+departureDate+'&arrivalDate='+arrivalDate+'&minPrice='+minPrice+'&maxPrice='+maxPrice+'&page='+page+'&pageSize='+pageSize+'&maxPage='+maxPage)
    }

    function resetSearch(){
        setSearch(true)
    }

    function resetFilters(){
        origin = '';
        destination = '';
        weight = '1';
        volume = '1';
        type = '';
        departureDate = '';
        arrivalDate = '';
        minPrice = '0';
        maxPrice = '1000000';
        page = '1';
        pageSize = '12';
        maxPage = '0';
    }

    function viewAll() {
        resetFilters()
        searchAction()
    }
    
    if(search)
        return (
            <div>
                <Row className='flex-center w-100'>
                    <Col span={12} className='flex-center'>
                        <Card className='w-100'>
                            <Title level={4}>{t('landing.SearchTrips')}</Title>
                            <Divider></Divider>
                            <Row className='w-100 space-between'>
                                <Col span={11}>
                                    <Select placeholder={t('filters.origin')} className='w-100 mb-1vh' onChange={handleOriginChange} allowClear showSearch>
                                        {cities.map((city, index) => (
                                            <Select.Option key={index} value={city}>{city}</Select.Option>
                                        ))}
                                    </Select>
                                </Col>
                                <Col span={2} className='flex-center mb-1vh'>
                                    <ArrowRightOutlined/>
                                </Col>
                                <Col span={11}>
                                    <Select placeholder={t('filters.destination')} className='w-100 mb-1vh' onChange={handleDestinationChange} allowClear showSearch>
                                        {cities.map((city, index) => (
                                            <Select.Option key={index} value={city}>{city}</Select.Option>
                                        ))}
                                    </Select>
                                </Col>
                            </Row>
                            <DatePicker.RangePicker className='w-100 mb-1vh' disabledDate={current => current && current.isBefore(dayjs().startOf('day'))} onChange={(dates) => handleDatesChange(dates)} allowClear placeholder={[t('common.from'), t('common.to')]}></DatePicker.RangePicker>
                            <Select placeholder={t('filters.cargoType')} className='w-100 mb-1vh' onChange={handleCargoTypeChange} allowClear>
                                {cargoTypes.map((cargoType, index) => (
                                    <Select.Option key={index} value={cargoType}>{t('cargoType.'+cargoType.toLowerCase())}</Select.Option>
                                ))}
                            </Select>
                            <Row className='w-100 space-between'>
                                <Col span={11}>
                                    <Input type='number' placeholder={t('filters.weight')} className='mb-1vh' onChange={handleWeightChange} suffix={'Kg'} allowClear></Input>
                                </Col>
                                <Col span={12}>
                                    <Input type='number' placeholder={t('filters.volume')} className='mb-1vh' onChange={handleVolumeChange} suffix={'M3'} allowClear></Input>
                                </Col>
                            </Row>
                            <Row className='w-100 flex-center'>
                                <Col span={12}>
                                    <Text>{t('filters.price')}</Text>
                                    <Slider range min={0} max={1000000} className='mb-1vh' tooltip={{formatter}} onChange={handlePriceRangeChange}></Slider>
                                </Col>
                            </Row>
                            <div className='w-100 flex-center pt-5'>
                                <Button type='primary' className='w-50' onClick={searchAction}>{t('common.search')}</Button>
                            </div>
                        </Card>

                    </Col>
                </Row>
                <Row className='flex-center w-100'>
                    <Col span={12} style={{textAlign: 'end', textDecoration: 'underline'}} className='mt-5' onClick={viewAll}>
                        <Text><a>{t('common.viewAllTrips')}</a></Text>
                    </Col>            
                </Row>
            </div>
        );
    else
        return (
            <Skeleton loading={isLoading}>
                <div style={{display: "flex", flexDirection: 'column'}}>
                    <Row gutter={15}>
                        <Button type='dashed' onClick={resetSearch}>{t('common.searchAgain')}</Button>
                    </Row>
                    <Row gutter={15}>
                        {publications.length === 0 && <Col span={24} className="text-center"><Title level={3}>{t('common.noTripsFound')}</Title></Col>}
                        {publications.map((trip, index) => (
                            <Col xxl={6} xl={6} lg={8} md={12} sm={22} xs={22} key={index}>
                                <TripCard {...trip}></TripCard>
                            </Col>
                        )
                        )}
                    </Row>
                    {publications.length === 0 ? null : 
                        <Pagination 
                            className="text-center mt-2vh" 
                            defaultCurrent={1}
                            total={Number(page)*Number(pageSize)}
                            current={Number(page)}
                            pageSize={Number(pageSize)}
                            onChange={handlePaginationChange}
                        />
                    }
                </div>
            </Skeleton>
        );
}

export default SearchTrips;