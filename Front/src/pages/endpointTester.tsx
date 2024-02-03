import { Button, Typography } from "antd"
import { useState } from "react";
import { loginUser } from "../api/userApi";

const Tester: React.FC = () => {

    const {Text, Title} = Typography;

    const [result, setResult] = useState<string>('');
    const [token, setToken] = useState<string | null>('');
    

    async function test(){
        loginUser('20-43988795-5', '12345678').then((user) => {
            setToken(localStorage.getItem("token"));
        })
    }

    function claim(token: string ) {
        // Splitting the token into parts
        const parts = token.split('.');
        if(parts.length !== 3) {
            throw new Error('Invalid token: must contain three parts separated by dots');
        }
    
        // Base64 URL decoding for the payload (second part)
        const payload = parts[1].replace(/-/g, '+').replace(/_/g, '/');
        const decodedPayload = atob(decodeURIComponent(payload));
    
        // Optionally, parse the JSON string
        try {
            const parsedPayload = JSON.parse(decodedPayload);
            // Do something with the parsed payload
            console.log(parsedPayload);
        } catch (e) {
            console.error('Invalid payload: not a valid JSON string');
        }

        setResult(decodedPayload);
    }
    

    return (
        <div>
            <Title>Endpoint Tester</Title>
            <Button onClick={test} type="primary">Test</Button>
            <Title>Token:</Title>
            <Text>{token}</Text><br></br> <br></br>
            <Button onClick={() => claim(token!)} type="primary">Test</Button>
            <Title>Claims:</Title>
            <Text>{result}</Text>
        </div>
    )

}

export default Tester