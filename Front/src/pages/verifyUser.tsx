import React, { useEffect } from "react";
import {  Skeleton, message } from "antd";
import {useNavigate, useSearchParams} from 'react-router-dom';
import { loginUser } from "../api/userApi";
import { useTranslation } from "react-i18next";

const VerifyAccount: React.FC = () => {
    const [searchParams] = useSearchParams();
    const token = searchParams.get('token');
    const cuit = searchParams.get('cuit');

    const router = useNavigate();
    const { t } = useTranslation();
    

    useEffect(() => {

        if(cuit == null || token == null){
            message.error(t('message.invalidLink'));
            router('/login');
            return;
        }

        loginUser(cuit, token).then(() => {
            message.success(t('message.accountVerified'));
            router('/login');
        }).catch(() => {
            message.error(t('message.invalidLink'));
            router('/login');
        });

    }, [])

    return (
        <Skeleton active/>
    );
};

export default VerifyAccount;
