import { ArrowRightOutlined } from "@ant-design/icons";
import { Badge, Card, Divider, Typography } from "antd";
import Meta from "antd/es/card/Meta";
import { useNavigate } from "react-router-dom";

const {Title, Text} = Typography;

export type TripCardProps = {
    id: number;
    type: 'trip' | 'cargo';
    from: string;
    to: string;
    fromDate: Date;
    toDate: Date;
    weight: number;
    volume: number;
    price: number;
    image: string;
    cargoType: string
}   



const TripCard = (props: TripCardProps) => {
    const router = useNavigate();
    return(
       
            <Card style={{width: 'auto', background: 'white', marginTop: '2vh'}} hoverable
                cover={
                    <Badge.Ribbon text={<Title level={5} style={{color: 'white', margin: 3}}>{props.cargoType}</Title>}  color="blue" >
                        <img
                            style={{width: '100%'}}
                            alt="example"
                            src={props.image}
                        />
                    </Badge.Ribbon>
                }

                onClick={() => router(`/trips/${props.id}`)}
            >
                <Meta
                    title={
                        <div style={{display: 'flex', textAlign: 'center'}}>
                            <div style={{width: '40%'}}>
                                <Title level={5} style={{margin: 0}}>{props.from}</Title>
                                <Text>{(new Date(props.fromDate.toString())).toDateString()}</Text>
                            </div>
                            <div style={{width: '20%', display: 'flex', justifyContent: 'center'}}>
                                <ArrowRightOutlined/>
                            </div>
                            <div style={{width: '40%'}}>
                                <Title level={5} style={{margin: 0}}>{props.to}</Title>
                                <Text>{(new Date(props.toDate.toString())).toDateString()}</Text>
                            </div>
                        </div>
                    }
                    description={
                        <div style={{textAlign: 'center'}}>
                            <Divider style={{margin: 7}}></Divider>
                            <div style={{display: 'flex'}}>
                                <div style={{width: '50%'}}>
                                    <Title level={5} style={{margin: 0}}>{props.weight} kg</Title>
                                    <Text>Avl. Weight</Text>
                                </div>
                                <div style={{width: '50%'}}>
                                    <Title level={5} style={{margin: 0}}>{props.volume} m3</Title>
                                    <Text>Avl. Volume</Text>
                                </div>
                            </div>
                            <Divider style={{margin: 7}}></Divider>
                            <Title level={4} style={{margin: 7}}>${props.price}</Title>
                        </div>
                    }
                />
            </Card>
        
    )
}

export default TripCard;