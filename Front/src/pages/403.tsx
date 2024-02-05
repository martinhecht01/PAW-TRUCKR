import { Button, Result, Typography } from "antd"
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom"

const {Title, Text} = Typography;
const AccessDenied403 = () => {
    const navigate = useNavigate();
    const {t} = useTranslation();

    document.title = t('pageTitles.error403');

    return (
        <Result
        status="403"
        title={<Title>{t('errorPages.error403')}</Title>}
        subTitle={<Text>{t('errorPages.error403Message')}</Text>}
        extra={<Button type="primary" onClick={() => navigate('/')}>{t('errorPages.back')}</Button>}
      />
    )
}

export default AccessDenied403