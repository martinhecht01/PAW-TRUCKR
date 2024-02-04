import { Card, Col, DatePicker, Divider, Input, Pagination, Row, Select, Skeleton, Slider, Typography } from "antd"
import { useEffect, useState } from "react";
import TripCard, { TripCardProps } from "../Components/tripCard";
import '../styles/main.scss';
import { getPublications } from "../api/tripApi";
import { Dayjs } from "dayjs";
import { getCities } from "../api/citiesApi";
import { getCargoTypes } from "../api/cargoTypeApi";
import { useTranslation } from "react-i18next";

const {Text, Title} = Typography;

const formatter = (value: number | undefined) => `$${value}`;

const {RangePicker} = DatePicker;

type RangeValue = [Dayjs | null, Dayjs | null] | null;

interface BrowseTripsProps {
    tripOrRequest: 'TRIP' | 'REQUEST';
}


const BrowseTrips: React.FC<BrowseTripsProps> = ({tripOrRequest}) => {

    const {t} = useTranslation();

    const sortOptions = ['departureDate ASC', 'departureDate DESC', 'arrivalDate ASC', 'arrivalDate DESC', 'price ASC', 'price DESC']

    const [cities, setCities] = useState<Array<string>>([]);
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [trips, setTrips] = useState<Array<TripCardProps>>([]);
    const [origin, setOrigin] = useState<string>('');
    const [destination, setDestination] = useState<string>('');
    const [weight, setWeight] = useState<number>(1);
    const [volume, setVolume] = useState<number>(1);
    const [priceRange, setPriceRange] = useState<[number, number]>([0, 1000000]);
    const [sortBy, setSortBy] = useState<string>(sortOptions[0]);
    const [page, setPage] = useState<number>(1);
    const [pageSize, setPageSize] = useState<number>(12);
    const [maxPage, setMaxPage] = useState<number>(0);
    const [cargoTypes, setCargoTypes] = useState<Array<string>>([]);
    const [cargoType, setCargoType] = useState<string>('');
    const [dateRange, setDateRange] = useState<RangeValue>(null);

    const handleOriginChange = (value: string) => setOrigin(value);
    const handleDestinationChange = (value: string) => setDestination(value);
    const handleWeightChange = (e: React.ChangeEvent<HTMLInputElement>) => setWeight(+e.target.value);
    const handleVolumeChange = (e: React.ChangeEvent<HTMLInputElement>) => setVolume(+e.target.value);

    const handlePriceRangeChange = (value: number | number[]) => {
        if (Array.isArray(value) && value.length === 2) {
            setPriceRange(value as [number, number]);
        }
    };

    const handlePaginationChange = (page: number, pageSize?: number) => {
        setPage(page);
        if (pageSize) setPageSize(pageSize);
    };

    const handleSortByChange = (value: string) => setSortBy(value);
    const handleCargoTypeChange = (value: string) => setCargoType(value);

    useEffect(() => {
        setPage(1);
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

        getPublications('' ,tripOrRequest, dateRange?.[0] ? dateRange[0].format('YYYY-MM-DDTHH:MM:ss') : '', dateRange?.[1] ? dateRange[1].format('YYYY-MM-DDTHH:MM:ss'): '', 'ACTIVE', volume, weight, cargoType, origin, destination, priceRange[0], priceRange[1], page, pageSize, sortBy).then((publications) => {
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
                setMaxPage(Number.parseInt(publications[0].maxPage ? publications[0].maxPage : '1'));
            setIsLoading(false);
        })
    }, [origin, destination, weight, volume, priceRange, sortBy, page, pageSize, tripOrRequest, dateRange, cargoType])


    return (
        <Row>
            <Col xxl={4} xl={4} lg={8} md={24} sm={24} xs={24}>
                <Card style={{margin: 25}}>
                    <Title level={3} style={{marginTop: 0}}>{t('filters.title')}</Title>
                    <Divider></Divider>
                    <Text>{t('filters.origin')}:</Text>
                    <Select placeholder="-" className="w-100" onChange={handleOriginChange} showSearch allowClear>
                        {cities.map((city, index) => (
                            <Select.Option key={index} value={city}>{city}</Select.Option>
                        ))}
                    </Select>
                    <div className="m-10"></div>
                    <Text>{t('filters.destination')}:</Text>
                    <Select placeholder="-" className="w-100" onChange={handleDestinationChange} showSearch allowClear> 
                        {cities.map((city, index) => (
                            <Select.Option key={index} value={city}>{city}</Select.Option>
                        ))}
                    </Select>
                    <div className="m-10"></div>
                    <Text>{t('filters.weight')}:</Text>
                    <Input type="number" placeholder="-" min={0} onChange={handleWeightChange} suffix='Kg' allowClear></Input>
                    <div className="m-10"></div>
                    <Text>{t('filters.volume')}:</Text>
                    <Input type="number" placeholder="-" min={0} onChange={handleVolumeChange} suffix='M3' allowClear></Input>
                    <div className="m-10"></div>
                    <Text>{t('filters.price')}:</Text>
                    <Slider range min={0} max={100000} value={priceRange} onChange={handlePriceRangeChange} tooltip={{formatter}}></Slider>
                    <div className="m-10"></div>
                    <Text>{t('filters.cargoType')}:</Text>
                    <Select placeholder="-" className="w-100" onChange={handleCargoTypeChange} allowClear>
                        {cargoTypes.map((cargoType, index) => (
                            <Select.Option key={index} value={cargoType}>{t('cargoType.'+cargoType.toLocaleLowerCase())}</Select.Option>
                        ))}
                    </Select>
                    <div className="m-10"></div>
                    <Text>{t('filters.dateRange')}</Text>
                    <RangePicker className="w-100"
                        onChange={(val) => {
                            setDateRange(val);
                        }}    
                        allowClear
                    ></RangePicker>
                    <div className="m-10"></div>
                    <Text>{t('filters.sortBy')}:</Text>
                    <Select placeholder="-" className="w-100" onChange={handleSortByChange} allowClear>
                        {sortOptions.map((option, index) => (
                            <Select.Option key={index} value={option}>{option}</Select.Option>
                        ))}
                    </Select>                                      
                    <div className="m-10"></div>                    
                </Card>
            </Col>

            <Col xxl={20} xl={20} lg={16} md={24} sm={24} xs={24}>
                <Skeleton loading={isLoading}>
                    <div style={{display: "flex", flexDirection: 'column'}}>
                        <Row gutter={15}>
                            {trips.length === 0 && <Col span={24} className="text-center"><Title level={3}>No {tripOrRequest.toLocaleLowerCase()} found</Title></Col>}
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
                                total={maxPage*pageSize}
                                current={page}
                                pageSize={pageSize}
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