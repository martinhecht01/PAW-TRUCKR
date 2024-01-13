import { Button, Card, Col, DatePicker, Divider, Dropdown, Input, Row, Select, Slider, Typography } from 'antd';
import '../styles/main.scss';
import { ArrowRightOutlined } from '@ant-design/icons';


const {Title, Text} = Typography;
const SearchTrips: React.FC = () => {

    const formatter = (value: number | undefined) => `$${value}`;

    return (
        <Row className='flex-center w-100'>
            <Col span={12} className='flex-center'>
                <Card className='w-100'>
                    <Title level={4}>Search Trips</Title>
                    <Divider></Divider>
                    <Row className='w-100 space-between'>
                        <Col span={11}>
                            <Select placeholder='Origin' className='mb-1vh w-100'></Select>
                        </Col>
                        <Col span={2} className='flex-center'>
                            <ArrowRightOutlined/>
                        </Col>
                        <Col span={11}>
                            <Select placeholder='Destination' className='mb-1vh w-100'></Select>
                        </Col>
                    </Row>
                    <DatePicker.RangePicker className='w-100 mb-1vh'></DatePicker.RangePicker>
                    <Select placeholder='Cargo Type' className='w-100 mb-1vh'></Select>
                    <Row className='w-100 space-between'>
                        <Col span={11}>
                            <Input type='number' placeholder='Weight' className='mb-1vh' suffix={'Kg'}></Input>
                        </Col>
                        <Col span={12}>
                            <Input type='number' placeholder='Volume' className='mb-1vh' suffix={'M3'}></Input>
                        </Col>
                    </Row>
                    <Row className='w-100 flex-center'>
                        <Col span={12}>
                            <Text>Price</Text>
                            <Slider range min={0} max={1000000} className='mb-1vh' tooltip={{formatter}}></Slider>
                        </Col>
                    </Row>
                    <div className='w-100 flex-center pt-5'>
                        <Button type='primary' className='w-50' >Search</Button>
                    </div>
                </Card>
            </Col>
        </Row>
    );
}

export default SearchTrips;