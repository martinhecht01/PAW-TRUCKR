import React, { useEffect } from "react";
import {  Skeleton, message } from "antd";
import {useNavigate, useSearchParams} from 'react-router-dom';
import { loginUser } from "../api/userApi";
import { useTranslation } from "react-i18next";
import { useAuthContext } from "../hooks/authProvider";

const VerifyAccount: React.FC = () => {
    const [searchParams] = useSearchParams();
    const token = searchParams.get('token');
    const cuit = searchParams.get('cuit');
    
    console.log(searchParams.get('token'))

    const router = useNavigate();
    const { t } = useTranslation();

    document.title = t('pageTitles.verifyAccount');

    const auth = useAuthContext();
    

    useEffect(() => {

        if(cuit == null || token == null){
            message.error(t('common.invalidLink'));
            router('/login');
            return;
        }

        loginUser(cuit, token).then(() => {
            if(token == null) {
                message.error(t('common.invalidLink'));
                router('/login');
            }else{
                message.success(t('common.accountVerified'));
                auth.login(token);
                router('/profile');
            }
            
        }).catch(() => {
            message.error(t('common.invalidLink'));
            router('/login');
        });

    }, [])

    return (
        <Skeleton active/>
    );
};

export default VerifyAccount;
