import React from 'react';
import {Typography} from 'antd';
import {StarFilled} from "@ant-design/icons";

const { Title } =Typography;

const ReviewContainer: React.FC<{ avgRating:number, comment:string }> = ({ avgRating, comment}) => {
    return (
        <div style={{marginBottom:'2vh'}}>
            <div style={{display:'flex'}}>
                <StarFilled></StarFilled>
                <Title style={{ margin:'0vh'}} level={3}>{avgRating}</Title>
            </div>
            <div>
                <Title style={{margin:'0vh'}} level={5}>{comment}</Title>
            </div>
        </div>

    );
}
export default ReviewContainer;