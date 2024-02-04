import { Button, Result, Typography } from "antd"
import { useNavigate } from "react-router-dom"

const {Title, Text} = Typography;
const InternalError500 = () => {
    const navigate = useNavigate();
    return (
        <Result
        status="500"
        title={<Title>500</Title>}
        subTitle={<Text>Ups, something unexpected happend. If it persists, please contact support.</Text>}
        extra={<Button type="primary" onClick={() => navigate(-1)}>Back</Button>}
      />
    )
}

export default InternalError500