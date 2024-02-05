import { Button, Result, Typography } from "antd"
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom"

const {Title, Text} = Typography;
const InternalError500 = () => {
    const navigate = useNavigate();
    const {t} = useTranslation();

    document.title = t('pageTitles.error500');

    return (
        <Result
        status="500"
        title={<Title>{t('errorPages.error500')}</Title>}
        subTitle={<Text>{t('errorPages.error500Message')}</Text>}
        extra={<Button type="primary" onClick={() => navigate('/')}>{t('errorPages.back')}</Button>}
      />
    )
}

export default InternalError500