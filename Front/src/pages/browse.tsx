import {
    Button,
    Card,
    Col,
    DatePicker,
    Divider,
    Input,
    Pagination,
    Row,
    Select,
    Skeleton,
    Slider,
    Typography
} from "antd"
import { useEffect, useState } from "react";
import TripCard, { TripCardProps } from "../Components/tripCard";
import '../styles/main.scss';
import { getPublications } from "../api/tripApi";
import dayjs, { Dayjs } from "dayjs";
import { getCities } from "../api/citiesApi";
import { getCargoTypes } from "../api/cargoTypeApi";
import { useTranslation } from "react-i18next";
import {useNavigate, useSearchParams} from "react-router-dom";

const {Text, Title} = Typography;

const formatter = (value: number | undefined) => `$${value}`;

const {RangePicker} = DatePicker;

type RangeValue = [Dayjs | null, Dayjs | null] | null;


interface BrowseTripsProps {
    tripOrRequest: 'TRIP' | 'REQUEST';
}


const BrowseTrips: React.FC<BrowseTripsProps> = ({tripOrRequest}) => {

    const {t} = useTranslation();

    const router = useNavigate();

    document.title = tripOrRequest === 'TRIP' ? t('pageTitles.browseTrips') : t('pageTitles.browseRequests');

    const sortOptions = ['departureDate ASC', 'departureDate DESC', 'arrivalDate ASC', 'arrivalDate DESC', 'price ASC', 'price DESC']

    const [cities, setCities] = useState<Array<string>>([]);
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [cargoTypes, setCargoTypes] = useState<Array<string>>([]);
    const [trips, setTrips] = useState<Array<TripCardProps>>([]);

    const [searchParams] = useSearchParams();

    const [origin, setOrigin] = useState<string>(searchParams.get('origin')?? '');
    const [destination, setDestination] = useState<string>(searchParams.get('destination') ?? '');
    const [weight, setWeight] = useState<string>(searchParams.get('weight') ?? '1');
    const [volume, setVolume] = useState<string>(searchParams.get('volume') ?? '1');
    const [type, setType] = useState<string>(searchParams.get('type') ?? '');
    const [departureDate, setDepartureDate] = useState<string>(searchParams.get('departureDate') ?? '');
    const [arrivalDate, setArrivalDate] = useState<string>(searchParams.get('arrivalDate') ?? '');
    const [minPrice, setMinPrice] = useState<string>(searchParams.get('minPrice') ?? '0');
    const [maxPrice, setMaxPrice] = useState<string>(searchParams.get('maxPrice') ?? '1000000');
    const [page, setPage] = useState<string>(searchParams.get('page') ?? '1');
    const [pageSize, setPageSize] = useState<string>(searchParams.get('pageSize') ?? '12');
    const [maxPage, setMaxPage] = useState<string>(searchParams.get('maxPage') ?? '0');
    const [sortBy, setSortBy] = useState<string>(searchParams.get('sortBy') ?? sortOptions[0]);

    const handleOriginChange = (value: string) => {if (value == undefined){setOrigin(''); return;}setOrigin(value)};
    const handleDestinationChange = (value: string) => {if (value == undefined){setDestination(''); return;}setDestination(value)};
    const handleWeightChange = (e: React.ChangeEvent<HTMLInputElement>) => setWeight(e.target.value);
    const handleVolumeChange = (e: React.ChangeEvent<HTMLInputElement>) => setVolume(e.target.value);
    const handleCargoTypeChange = (value: string) => {if (value == undefined){setType(''); return;}setType(value)};

    const handleSortByChange = (value: string) => {if (value == undefined){setSortBy(sortOptions[0]); return;}setSortBy(value)};

    const handlePriceRangeChange = (value: number | number[]) => {
        if (Array.isArray(value) && value.length === 2) {
            setMinPrice(value[0].toString());
            setMaxPrice(value[1].toString());
        }
    };

    const handleDatesChange = (dates: RangeValue) => {
        if (dates == null){
            setDepartureDate('');
            setArrivalDate('');
            return;
        }
        if(dates[0] && dates[1]){
            setDepartureDate(dates[0].format('YYYY-MM-DDTHH:MM:ss'));
            setArrivalDate(dates[1].format('YYYY-MM-DDTHH:MM:ss'));
        }
        else if (dates[0]){
            setDepartureDate(dates[0].format('YYYY-MM-DDTHH:MM:ss'));
        }
        else if (dates[1]){
            setArrivalDate(dates[1].format('YYYY-MM-DDTHH:MM:ss'));
        }
    }

    const handlePaginationChange = (pagex: number, pageSizex?: number) => {
        setPage(pagex.toString());
        if (pageSizex) setPageSize(pageSizex.toString());
    };

    useEffect(() => {
        setPage('1');
    }, [tripOrRequest])

    useEffect(() => {
        setIsLoading(true);

        getCities().then((cities) => {
            const citiesArray = cities.map((city) => city.cityName);
            setCities(citiesArray);
        });

        getCargoTypes().then((cargoTypes) => {
            setCargoTypes(cargoTypes);
        });

        getPublications('' ,tripOrRequest, departureDate, arrivalDate, 'ACTIVE', Number(volume), Number(weight), type, origin, destination, Number(minPrice), Number(maxPrice), Number(page), Number(pageSize), sortBy).then((publications) => {
            setTrips(publications.map((publication) => {
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
            if(publications.length > 0)
                setMaxPage(publications[0].maxPage ? publications[0].maxPage : '1');
            setIsLoading(false);
        })

        if (tripOrRequest === 'TRIP'){
            router('/trips?origin='+origin+'&destination='+destination+'&weight='+weight+'&volume='+volume+'&type='+type+'&departureDate='+departureDate+'&arrivalDate='+arrivalDate+'&minPrice='+minPrice+'&maxPrice='+maxPrice+'&page='+page+'&pageSize='+pageSize+'&maxPage='+maxPage+'&sortBy='+sortBy);
        }
        else{
            router('/cargo?origin='+origin+'&destination='+destination+'&weight='+weight+'&volume='+volume+'&type='+type+'&departureDate='+departureDate+'&arrivalDate='+arrivalDate+'&minPrice='+minPrice+'&maxPrice='+maxPrice+'&page='+page+'&pageSize='+pageSize+'&maxPage='+maxPage+'&sortBy='+sortBy);
        }
    }, [origin, destination, weight, volume, minPrice, maxPrice, sortBy, page, pageSize, departureDate, arrivalDate, type])

    function resetFilters(){
        setOrigin('');
        setDestination('');
        setWeight('1');
        setVolume('1');
        setType('');
        setDepartureDate('');
        setArrivalDate('');
        setMinPrice('0');
        setMaxPrice('1000000');
        setPage('1');
        setPageSize('12');
        setMaxPage('0');
        setSortBy(sortOptions[0]);
    }


    return (
        <Row>
            <Col xxl={4} xl={4} lg={8} md={24} sm={24} xs={24}>
                <Card style={{margin: 25, marginBottom:10}}>
                    <Title level={3} style={{marginTop: 0}}>{t('filters.title')}</Title>
                    <Divider></Divider>
                    <Text>{t('filters.origin')}:</Text>
                    <Select placeholder="-" className="w-100" onChange={handleOriginChange} value={origin} showSearch allowClear>
                        {cities.map((city, index) => (
                            <Select.Option key={index} value={city}>{city}</Select.Option>
                        ))}
                    </Select>
                    <div className="m-10"></div>
                    <Text>{t('filters.destination')}:</Text>
                    <Select placeholder="-" className="w-100" value={destination} onChange={handleDestinationChange} showSearch allowClear>
                        {cities.map((city, index) => (
                            <Select.Option key={index} value={city}>{city}</Select.Option>
                        ))}
                    </Select>
                    <div className="m-10"></div>
                    <Text>{t('filters.weight')}:</Text>
                    <Input type="number" placeholder="-" min={0} value={weight != '1' ? weight:undefined} onChange={handleWeightChange} suffix='Kg' allowClear></Input>
                    <div className="m-10"></div>
                    <Text>{t('filters.volume')}:</Text>
                    <Input type="number" placeholder="-" min={0}  onChange={handleVolumeChange} value={volume != '1' ? volume : undefined} suffix='M3' allowClear></Input>
                    <div className="m-10"></div>
                    <Text>{t('filters.price')}:</Text>
                    <Slider range min={0} max={1000000} value={[Number(minPrice), Number(maxPrice)]}  onChange={handlePriceRangeChange} tooltip={{formatter}}></Slider>
                    <div className="m-10"></div>
                    <Text>{t('filters.cargoType')}:</Text>
                    <Select placeholder="-" className="w-100" value={type} onChange={handleCargoTypeChange} allowClear>
                        {cargoTypes.map((cargoType, index) => (
                            <Select.Option key={index} value={cargoType}>{t('cargoType.'+cargoType.toLocaleLowerCase())}</Select.Option>
                        ))}
                    </Select>
                    <div className="m-10"></div>
                    <Text>{t('filters.dateRange')}</Text>
                    <RangePicker className="w-100"
                        onChange={(dates) => {
                            handleDatesChange(dates);
                        }}
                         value={departureDate != '' && arrivalDate != '' ? [dayjs(departureDate), dayjs(arrivalDate)] : undefined}
                        allowClear
                        placeholder={[t('common.from'), t('common.to')]}

                    ></RangePicker>
                    <div className="m-10"></div>
                    <Text>{t('filters.sortBy')}:</Text>
                    <Select placeholder="-" className="w-100" value={sortBy != ''? sortBy : undefined} onChange={handleSortByChange}>
                        {sortOptions.map((option, index) => (
                            <Select.Option key={index} value={option}>{option}</Select.Option>
                        ))}
                    </Select>                                      
                    <div className="m-10"></div>                    
                </Card>
                <Button style={{margin: 25}} className='m-0' type={"primary"} onClick={resetFilters}>{t('filters.reset')}</Button>
            </Col>

            <Col xxl={20} xl={20} lg={16} md={24} sm={24} xs={24}>
                <Skeleton loading={isLoading}>
                    <div style={{display: "flex", flexDirection: 'column'}}>
                        <Row gutter={15}>
                            {trips.length === 0 && <Col span={24} className="text-center"><Title level={3}>{t(`landing.no${tripOrRequest.toLowerCase()}Found`)}</Title></Col>}
                            {trips.map((trip, index) => (
                                <Col xxl={6} xl={6} lg={8} md={12} sm={22} xs={22} key={index}>
                                    <TripCard {...trip}></TripCard>
                                </Col>
                            )
                            )}
                        </Row>
                        {trips.length === 0 ? null : 
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

            </Col>
                            
            
        </Row>
        
        

    )
}

export default BrowseTrips