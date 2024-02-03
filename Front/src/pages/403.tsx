import { Button, Result, Typography } from "antd"
import { useNavigate } from "react-router-dom"

const {Title, Text} = Typography;
const AccessDenied403 = () => {
    const navigate = useNavigate();
    return (
        <Result
        status="403"
        title={<Title>403</Title>}
        subTitle={<Text>Sorry, you can't access this page</Text>}
        extra={<Button type="primary" onClick={() => navigate(-1)}>Back</Button>}
      />
    )
}

export default AccessDenied403