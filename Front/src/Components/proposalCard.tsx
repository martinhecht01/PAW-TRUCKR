import { Card,Button, Typography, Divider } from "antd";
import {useTranslation} from "react-i18next";
import '../styles/main.scss';
import '../styles/proposals.scss';

const {Title, Text} = Typography;



export type ProposalProps = {
    description: string,
    offeredPrice: number,
    userPhoto: string,
    userName: string,
    counterOffered: boolean,
    userMail?: string
}

const ProposalCard = (props: ProposalProps) => {

    const {t} = useTranslation();

    return(
        <div>
            <Card>
                <div>
                    <Title level={4} className='m-0'>{props.userName}</Title>
                </div>
                <div>
                    <Title level={5}>{t('manage.description')}</Title>
                </div>
                <div>
                    <Text>{props.description}</Text>
                </div>
                <div className='mt-2vh'>
                    <Title level={5}>{t('common.offeredPrice')}</Title>
                </div>
                <div>
                    <Text>{props.offeredPrice}</Text>
                </div>
                {!props.counterOffered &&
                <div className='flex-center mt-2vh'>
                    <Button className='m-1vh acceptButton'>{t('manage.accept')}</Button>
                    <Button className='m-1vh counterOfferButton'>{t('manage.counterOffer')}</Button>
                    <Button className='m-1vh rejectButton'>{t('manage.reject')}</Button>
                </div>
                }
                {props.counterOffered &&
                    <div>
                        <Divider/>
                        <Title level={4}>{t('manage.counterOffer')}</Title>
                        <div>
                            <Title level={5}>{t('manage.description')}</Title>
                        </div>
                        <div>
                            <Text>{props.description}</Text>
                        </div>
                        <div className='mt-2vh'>
                            <Title level={5}>{t('common.offeredPrice')}</Title>
                        </div>
                        <div>
                            <Text>{props.offeredPrice}</Text>
                        </div>
                        <div className='mt-2vh'>
                            <Button className='m-1vh' danger>{t('manage.cancel')}</Button>
                        </div>
                    </div>


                }
            </Card>
        </div>
    )
}

export default ProposalCard;