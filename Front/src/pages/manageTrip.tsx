import React from 'react';
import {Card, Col, Divider, Image, Row, Typography, Avatar} from 'antd';
import '../styles/main.scss';
import '../styles/profile.scss';
import {useTranslation} from "react-i18next";
import ProposalCard from "../Components/proposalCard.tsx";
import {StarFilled} from "@ant-design/icons";


const { Title, Text } = Typography;

export type ProposalProps = {
    description: string,
    offeredPrice: number,
    userPhoto: string,
    userName: string,
    userMail?: string
}

const ManageTrip: React.FC = () => {

    const {t} = useTranslation();

    let offerCount = 1;
    //TODO: get offer data from backend
    const offers : ProposalProps[] = [
        {description:"uenas",offeredPrice:15,userPhoto:"http://t3.gstatic.com/images?q=tbn:ANd9GcSDOfreJh67Zm_asl0jKHzSciAEeqdvsmJ4off_OGDTwOORaTRSTEbaNuINJKXZTPHOTgjUcA",userName:"Julian Alvarez"}
    ];

    const acceptedOffer : ProposalProps | null = null;

    return (
        <div >
            <div className="flex-center" style={{alignItems:'flex-start', margin: '3vh'}}>
                <Card style={{width: '30%', marginRight:'1vh'}} headStyle={{fontSize: '2.5vh', color: '#142d4c'}}
                      title={t("manage.manageTrip")}>
                    <div className='flex-center'>
                        <Image
                            src="https://pm1.aminoapps.com/6535/8e5958d3b7755e9429e476c408a06e5387358b0f_00.jpg"></Image>
                    </div>
                    <Row gutter={16} className='mt-2vh'>
                        <Col span={12}>
                            <Title className="m-1vh" level={5}>{t("common.cargoType")}</Title>
                        </Col>
                        <Col span={12}>
                            <p className="m-1vh">dfsajk</p>
                        </Col>
                    </Row>
                    <Divider className='m-0'/>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Title className="m-1vh" level={5}>{t('manage.origDest')}</Title>
                        </Col>
                        <Col span={12}>
                            <p className="m-1vh">dfsajk</p>
                        </Col>
                    </Row>
                    <Divider className='m-0'/>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Title className="m-1vh" level={5}>{t('common.licensePlate')}</Title>
                        </Col>
                        <Col span={12}>
                            <p className="m-1vh">dfsajk</p>
                        </Col>
                    </Row>
                    <Divider className='m-0'/>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Title className="m-1vh" level={5}>{t('manage.depArr')}</Title>
                        </Col>
                        <Col span={12}>
                            <p className="m-1vh">dfsajk</p>
                        </Col>
                    </Row>
                    <Divider className='m-0'/>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Title className="m-1vh" level={5}>{t('common.availableVolume')}</Title>
                        </Col>
                        <Col span={12}>
                            <p className="m-1vh">dfsajk</p>
                        </Col>
                    </Row>
                    <Divider className='m-0'/>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Title className="m-1vh" level={5}>{t('common.availableWeight')}</Title>
                        </Col>
                        <Col span={12}>
                            <p className="m-1vh">dfsajk</p>
                        </Col>
                    </Row>
                    <Divider className='m-0'/>
                    <Row gutter={16}>
                        <Col span={12}>
                            <Title className="m-1vh" level={5}>{t('common.suggestedPrice')}</Title>
                        </Col>
                        <Col span={12}>
                            <p className="m-1vh">dfsajk</p>
                        </Col>
                    </Row>
                </Card>
                <div style={{marginLeft:'1vh', width:'30%'}}>
                    { offerCount == 0 && acceptedOffer == null && <Card>
                        <Title className='m-0' level={5}>{t('manage.noOffers')}</Title>
                    </Card>}
                    { offerCount > 0 && acceptedOffer == null &&
                        offers.map((offer) => (
                            <ProposalCard counterOffered={true} description={offer.description} offeredPrice={offer.offeredPrice} userName={offer.userName} userPhoto={offer.userPhoto}></ProposalCard>
                        ))
                    }
                    { acceptedOffer != null &&
                        <Card>
                            <div className='flex-center space-evenly'>
                                <Avatar src='https://101db.com.ar/12971-Productos/guitarra-electrica-fender-squier-mini-stratocaster-black.jpg' className='m-1vh' style={{marginRight:'2vh'}}/>
                                <div>
                                    <div className='flex-center space-around'>
                                        <Title className='m-0' level={4}>{acceptedOffer.userName}</Title>
                                        <div className='flex-center space-between'>
                                            <StarFilled style={{marginLeft:'3vh'}}></StarFilled>
                                            <Text  style={{marginLeft:'0.5em'}}>4.5 - (3 {t("review.reviews")})</Text>
                                        </div>
                                    </div>
                                    <Text className='m-0'>{acceptedOffer.userMail}</Text>
                                </div>
                            </div>
                        </Card>
                    }
                </div>
            </div>
        </div>
    );
};

export default ManageTrip;