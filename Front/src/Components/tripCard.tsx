import { ArrowRightOutlined } from "@ant-design/icons";
import { Badge, Card, Divider, Image, Typography } from "antd";
import Meta from "antd/es/card/Meta";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import { getCargoTypeColor } from "./cargoTypeColor";

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
    cargoType: string;
    clickUrl: string;
    notifications?: number;
}   



const TripCard = (props: TripCardProps) => {
    const router = useNavigate();
    const {t} = useTranslation()
    return(
       
            <Card style={{width: 'auto', background: 'white', marginTop: '2vh'}} 
                cover={
                    <>
                        <Badge.Ribbon text={<Title level={5} style={{color: 'white', margin: 3}}>{props.cargoType}</Title>} placement="start" color={getCargoTypeColor(props.cargoType.toLowerCase())} >
                            {props.notifications ? <Badge size="default" count={props.notifications} > 
                                    <Image
                                        style={{ width: '100%',
                                        height: '350px',
                                        objectFit: 'cover',
                                        objectPosition: 'center center'}}
                                        alt="example"
                                        src={props.image}
                                        preview={false}
                                    />
                                </Badge> :
                                    <Image
                                    style={{ width: '100%',
                                    height: '350px',
                                    objectFit: 'cover',
                                    objectPosition: 'center center'}}
                                    alt="example"
                                    src={props.image}
                                    preview={false}
                                />
                    }

                        </Badge.Ribbon>
                    </>
                }

                hoverable={props.clickUrl != '' ? true : false}

                onClick={() => props.clickUrl != '' ? router(`${props.clickUrl}/${props.id}`) : null}
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
                                    <Text>{t('common.avlWeight')}</Text>
                                </div>
                                <div style={{width: '50%'}}>
                                    <Title level={5} style={{margin: 0}}>{props.volume} m3</Title>
                                    <Text>{t('common.avlVolume')}</Text>
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