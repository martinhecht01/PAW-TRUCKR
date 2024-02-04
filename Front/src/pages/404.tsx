import { Button, Result, Typography } from "antd"
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom"

const {Title, Text} = Typography;
const NotFound404 = () => {
    const navigate = useNavigate();
    const {t} = useTranslation();
    return (
        <Result
        status="404"
        title={<Title>{t('errorPages.error404')}</Title>}
        subTitle={<Text>{t('errorPages.error404Message')}</Text>}
        extra={<Button type="primary" onClick={() => navigate('/')}>{t('errorPages.back')}</Button>}
      />
    )
}

export default NotFound404