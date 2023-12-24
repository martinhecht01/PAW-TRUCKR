import { ArrowRightOutlined } from "@ant-design/icons";
import { Badge, Card, Divider, Typography } from "antd";
import Meta from "antd/es/card/Meta";
import {useTranslation} from "react-i18next";

const {Title, Text} = Typography;



export type TripCardProps = {
    type: 'trip' | 'cargo';
    from: string;
    to: string;
    fromDate: Date;
    toDate: Date;
    lastUpdate: Date;
    price: number;
    image: string;
}

const PastTripCard = (props: TripCardProps) => {

    const {t} = useTranslation();

    return(

        <Card style={{width: 'auto', background: 'white', marginTop: '2vh'}} hoverable
              cover={
                  <Badge.Ribbon text={<Title level={5} style={{color: 'white', margin: 3}}>Refrigated</Title>}  color="blue" >
                      <img
                          style={{width: '100%'}}
                          alt="example"
                          src={props.image}
                      />
                  </Badge.Ribbon>
              }
        >
            <Meta
                title={
                    <div style={{display: 'flex', textAlign: 'center'}}>
                        <div style={{width: '40%'}}>
                            <Title level={5} style={{margin: 0}}>{props.from}</Title>
                            <Text>{props.fromDate.toDateString()}</Text>
                        </div>
                        <div style={{width: '20%', display: 'flex', justifyContent: 'center'}}>
                            <ArrowRightOutlined/>
                        </div>
                        <div style={{width: '40%'}}>
                            <Title level={5} style={{margin: 0}}>{props.to}</Title>
                            <Text>{props.toDate.toDateString()}</Text>
                        </div>
                    </div>
                }
                description={
                    <div style={{textAlign: 'center'}}>
                        <Divider style={{margin: 7}}></Divider>
                        <div style={{display: 'flex'}}>
                            <div style={{width: '100%'}}>
                                <Title level={5} style={{margin: 0}}>{props.lastUpdate.toUTCString()}</Title>
                                <Text>{t('pastTrips.lastUpdate')}</Text>
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

export default PastTripCard;