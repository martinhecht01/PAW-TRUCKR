import { Button, Result, Typography } from "antd"
import { useNavigate } from "react-router-dom"

const {Title, Text} = Typography;
const NotFound404 = () => {
    const navigate = useNavigate();
    return (
        <Result
        status="404"
        title={<Title>404</Title>}
        subTitle={<Text>Sorry, the page you visited does not exist.</Text>}
        extra={<Button type="primary" onClick={() => navigate(-1)}>Back</Button>}
      />
    )
}

export default NotFound404