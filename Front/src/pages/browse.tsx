import { Button, Card, Col, DatePicker, Divider, Grid, Input, Pagination, Row, Select, Skeleton, Slider, Switch, Typography } from "antd"
import { useEffect, useState } from "react";
import TripCard, { TripCardProps } from "../Components/tripCard";
import CloseOutlined from '@ant-design/icons/CloseOutlined';
import '../styles/main.scss';
import { getPublications } from "../api/tripApi";
import { Dayjs } from "dayjs";
import { getCities } from "../api/citiesApi";
import { getCargoTypes } from "../api/cargoTypeApi";

const {Text, Title} = Typography;

const formatter = (value: number | undefined) => `$${value}`;

const {RangePicker} = DatePicker;

type RangeValue = [Dayjs | null, Dayjs | null] | null;

interface BrowseTripsProps {
    tripOrRequest: 'TRIP' | 'REQUEST';
}


const BrowseTrips: React.FC<BrowseTripsProps> = ({tripOrRequest}) => {

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
            setIsLoading(false);
        })
    }, [origin, destination, weight, volume, priceRange, sortBy, page, pageSize, tripOrRequest, dateRange, cargoType])

    return (
        <Row>
            <Col xxl={4} xl={4} lg={8} md={24} sm={24} xs={24}>
                <Card style={{margin: 25}}>
                    <Title level={3} style={{marginTop: 0}}>Filters</Title>
                    <Divider></Divider>
                    <Text>Origin:</Text>
                    <Select placeholder="-" className="w-100" onChange={handleOriginChange}>
                        {cities.map((city, index) => (
                            <Select.Option key={index} value={city}>{city}</Select.Option>
                        ))}
                    </Select>
                    <div className="m-10"></div>
                    <Text>Destination:</Text>
                    <Select placeholder="-" className="w-100" onChange={handleDestinationChange}>
                        {cities.map((city, index) => (
                            <Select.Option key={index} value={city}>{city}</Select.Option>
                        ))}
                    </Select>
                    <div className="m-10"></div>
                    <Text>Weight:</Text>
                    <Input type="number" placeholder="-" min={0} onChange={handleWeightChange} suffix='Kg'></Input>
                    <div className="m-10"></div>
                    <Text>Volume:</Text>
                    <Input type="number" placeholder="-" min={0} onChange={handleVolumeChange} suffix='M3'></Input>
                    <div className="m-10"></div>
                    <Text>Price:</Text>
                    <Slider range min={0} max={100000} value={priceRange} onChange={handlePriceRangeChange} tooltip={{formatter}}></Slider>
                    <div className="m-10"></div>
                    <Text>Cargo type:</Text>
                    <Select placeholder="-" className="w-100" onChange={handleCargoTypeChange}>
                        {cargoTypes.map((cargoType, index) => (
                            <Select.Option key={index} value={cargoType}>{cargoType}</Select.Option>
                        ))}
                    </Select>
                    <div className="m-10"></div>
                    <Text>Date Range</Text>
                    <RangePicker className="w-100"
                        onChange={(val) => {
                            setDateRange(val);
                        }}   
                    ></RangePicker>
                    <div className="m-10"></div>
                    <Text>Sort by:</Text>
                    <Select placeholder="-" className="w-100" onChange={handleSortByChange}>
                        {sortOptions.map((option, index) => (
                            <Select.Option key={index} value={option}>{option}</Select.Option>
                        )
                        )}
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
                                total={50} // Update this total with the actual total number of items
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