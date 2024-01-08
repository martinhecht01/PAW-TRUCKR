import React, { useState } from 'react';
import type { MenuProps } from 'antd';
import { Image, Menu } from 'antd';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';

const sections = [
  {
    label: 'Browse Trips',
    key: 'trips',
  },
  {
    label: 'Browse Cargo',
    key: 'cargo',
  }
];

const NavBar: React.FC = () => {
  const [current, setCurrent] = useState('recorded');

  const {t} = useTranslation();

  const navigate = useNavigate();

  const onClick: MenuProps['onClick'] = (e) => {
    navigate(`/${e.key}`)
    setCurrent(e.key);
  };

return (
    <Menu mode="horizontal" style={{display: 'flex', alignItems: 'center', backgroundColor: 'white'}}>
        <Menu.Item key="" style={{width: 200, display: 'flex',  justifyContent: 'center'}} onClick={() => navigate('/')} ><Image preview={false} src='https://i.ibb.co/JmB4xhT/Truckr-Logo.png'></Image></Menu.Item>
        {sections.map((item) => (
        item == null ? null :  <Menu.Item key={item.key} onClick={onClick}>{item.label}</Menu.Item>
        ))}
        <Menu.Item key="login" style={{marginLeft: 'auto'}} onClick={() => navigate('/login')}>Ingresar</Menu.Item>
        <Menu.Item key="register" style={{fontWeight: 'normal'}} onClick={() => navigate('/register')}>Crear cuenta</Menu.Item>
    </Menu>
);
}

export default NavBar;
