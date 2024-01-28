import { Button, Card, Col, DatePicker, Divider, Dropdown, Input, Pagination, Row, Select, Skeleton, Slider, Typography } from 'antd';
import '../styles/main.scss';
import { ArrowRightOutlined } from '@ant-design/icons';
import { useEffect, useState } from 'react';
import { getCities } from '../api/citiesApi';
import TripCard, { TripCardProps } from '../Components/tripCard';
import { getPublications } from '../api/tripApi';


const {Title, Text} = Typography;
const SearchTrips: React.FC = () => {

    const formatter = (value: number | undefined) => `$${value}`;
    
    const [search, setSearch] = useState<boolean>(true);
    const [cities, setCities] = useState<Array<string>>([]);
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [publications, setPublications] = useState<Array<TripCardProps>>([]);

    const [origin, setOrigin] = useState<string>('');
    const [destination, setDestination] = useState<string>('');
    const [weight, setWeight] = useState<number>(1);
    const [volume, setVolume] = useState<number>(1);
    const [priceRange, setPriceRange] = useState<[number, number]>([0, 1000000]);
    const [type, setCargoType] = useState<string>('');

    const [page, setPage] = useState<number>(1);
    const [pageSize, setPageSize] = useState<number>(12);

    const handleOriginChange = (value: string) => setOrigin(value);
    const handleDestinationChange = (value: string) => setDestination(value);
    const handleWeightChange = (e: React.ChangeEvent<HTMLInputElement>) => setWeight(+e.target.value);
    const handleVolumeChange = (e: React.ChangeEvent<HTMLInputElement>) => setVolume(+e.target.value);
    const handleCargoTypeChange = (value: string) => setCargoType(value);

    const handlePriceRangeChange = (value: number | number[]) => {
        if (Array.isArray(value) && value.length === 2) {
            setPriceRange(value as [number, number]);
        }
    };

    const handlePaginationChange = (page: number, pageSize?: number) => {
        setPage(page);
        if (pageSize) setPageSize(pageSize);
    };

    useEffect(() => {
        setIsLoading(true);
        getCities().then((cities) => {
            setCities(cities.map((city) => city.cityName));
            setIsLoading(false);
        })
    }, [])

    async function searchAction(){
        //ACA FALTA EL TYPE!!
        setIsLoading(true);
        getPublications('', 'TRIP', 'ACTIVE', volume, weight, origin, destination, priceRange[0], priceRange[1], page, pageSize, 'departureDate ASC').then((trips) => {
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
                    image: publication.image
                }
            }))
            setSearch(false)
            setIsLoading(false);
        })
    }

    function resetSearch(){
        setSearch(true)
        setCargoType('')
        setOrigin('')
        setDestination('')
        setWeight(1)
        setVolume(1)
        setPriceRange([0, 1000000])
        setPage(1)
        setPageSize(12)
    }
    
    if(search)
        return (
            <Row className='flex-center w-100'>
                <Col span={12} className='flex-center'>
                    <Card className='w-100'>
                        <Title level={4}>Search Trips</Title>
                        <Divider></Divider>
                        <Row className='w-100 space-between'>
                            <Col span={11}>
                                <Select placeholder="Origin" className='w-100 mb-1vh' onChange={handleOriginChange}>
                                    {cities.map((city, index) => (
                                        <Select.Option key={index} value={city}>{city}</Select.Option>
                                    ))}
                                </Select>
                            </Col>
                            <Col span={2} className='flex-center mb-1vh'>
                                <ArrowRightOutlined/>
                            </Col>
                            <Col span={11}>
                                <Select placeholder="Destination" className='w-100 mb-1vh' onChange={handleDestinationChange}>
                                    {cities.map((city, index) => (
                                        <Select.Option key={index} value={city}>{city}</Select.Option>
                                    ))}
                                </Select>
                            </Col>
                        </Row>
                        <DatePicker.RangePicker className='w-100 mb-1vh'></DatePicker.RangePicker>
                        <Select placeholder='Cargo Type' className='w-100 mb-1vh' onChange={handleCargoTypeChange}></Select>
                        <Row className='w-100 space-between'>
                            <Col span={11}>
                                <Input type='number' placeholder='Weight' className='mb-1vh' onChange={handleWeightChange} suffix={'Kg'}></Input>
                            </Col>
                            <Col span={12}>
                                <Input type='number' placeholder='Volume' className='mb-1vh' onChange={handleVolumeChange} suffix={'M3'}></Input>
                            </Col>
                        </Row>
                        <Row className='w-100 flex-center'>
                            <Col span={12}>
                                <Text>Price</Text>
                                <Slider range min={0} max={1000000} className='mb-1vh' tooltip={{formatter}} onChange={handlePriceRangeChange}></Slider>
                            </Col>
                        </Row>
                        <div className='w-100 flex-center pt-5'>
                            <Button type='primary' className='w-50' onClick={searchAction}>Search</Button>
                        </div>
                    </Card>
                </Col>
            </Row>
        );
    else
        return (
            <Skeleton loading={isLoading}>
                <div style={{display: "flex", flexDirection: 'column'}}>
                    <Row gutter={15}>
                        <Button type='dashed' onClick={resetSearch}>Search again</Button>
                    </Row>
                    <Row gutter={15}>
                        {publications.length === 0 && <Col span={24} className="text-center"><Title level={3}>No trips found</Title></Col>}
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
                            total={50}
                            current={page}
                            pageSize={pageSize}
                            onChange={handlePaginationChange}
                        />
                    }
                </div>
            </Skeleton>
        );
}

export default SearchTrips;