import React, { useEffect, useState } from 'react';
import type { MenuProps } from 'antd';
import { Avatar, Col, Image, Menu, Row, Typography } from 'antd';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getClaims } from '../api/userApi';

import '../styles/main.scss'
import { LogoutOutlined, UserOutlined } from '@ant-design/icons';
import { useAuthContext } from '../hooks/authProvider';

const noAuth = [
  {
    label: 'Browse Trips',
    key: 'trips',
  },
  {
    label: 'Browse Cargo',
    key: 'cargo',
  }
];

const trucker = [
  {
    label: 'Browse Cargo',
    key: 'cargo',
  },
  {
    label: 'My Itinerary',
    key: 'myItinerary',
  },
  {
    label: 'My Publications',
    key: 'myPublications',
  },
  {
    label: 'Sent Offers',
    key: 'sentOffers',
  },
  {
    label: 'My Alert',
    key: 'myAlert',
  }
]

const provider = [
  {
    label: 'Search Trips',
    key: 'searchTrips'
  },
  {
    label: 'My Itinerary',
    key: 'myItinerary',
  },
  {
    label: 'My Publications',
    key: 'myPublications',
  },
  {
    label: 'Sent Offers',
    key: 'sentOffers',
  }
]

const NavBar: React.FC = () => {
  const [current, setCurrent] = useState('/');
  const [role, setRole] = useState<string>('');

  const {t} = useTranslation();
  const auth = useAuthContext();

  const navigate = useNavigate();

  useEffect(() => {
    if (auth.isAuthenticated) {
      const claims = getClaims();
      if (claims) {
        setRole(claims.role);
      }
    } else {
      setRole('');
    }
  }, [auth.isAuthenticated]);


  const onClick: MenuProps['onClick'] = (e) => {
    navigate(`/${e.key}`)
    setCurrent(e.key);
  };

  function logout(){
    auth.logout();
    navigate('/');
  }

return (
    <Menu mode="horizontal" style={{display: 'flex', alignItems: 'center', backgroundColor: 'white'}}>
      <Menu.Item key="" style={{width: 200, display: 'flex',  justifyContent: 'center'}} onClick={() => navigate('/')} ><Image preview={false} src='https://i.ibb.co/JmB4xhT/Truckr-Logo.png'></Image></Menu.Item>
      {
        role === 'TRUCKER' ? 
        <>
          {trucker.map((item) => (
              item == null ? null : <Menu.Item key={item.key} onClick={() => navigate(`/${item.key}`)}>{item.label}</Menu.Item>
          ))}
          <Menu.Item key="profile" style={{marginLeft: 'auto'}} onClick={() => navigate('/profile')}>Profile</Menu.Item>
          <Menu.Item key="logout" style={{fontWeight: 'normal'}} onClick={logout}><LogoutOutlined/></Menu.Item>
        </>
                   
        : 
        role === 'PROVIDER' ? 
        <>
            {provider.map((item) => (
              item == null ? null : <Menu.Item key={item.key} onClick={() => navigate(`/${item.key}`)}>{item.label}</Menu.Item>
            ))}
            <Menu.Item key="profile" style={{marginLeft: 'auto'}} onClick={() => navigate('/profile')}>Profile</Menu.Item>
            <Menu.Item key="logout" style={{fontWeight: 'normal'}} onClick={logout}><LogoutOutlined/></Menu.Item>
        </>
        : 
          <>
            {noAuth.map((item) => (
              item == null ? null : <Menu.Item key={item.key} onClick={() => navigate(`/${item.key}`)}>{item.label}</Menu.Item>
            ))}
            <Menu.Item key="login" style={{marginLeft: 'auto'}} onClick={() => navigate('/login')}>Ingresar</Menu.Item>
            <Menu.Item key="register" style={{fontWeight: 'normal'}} onClick={() => navigate('/register')}>Crear cuenta</Menu.Item>
          </>
      }
      
    </Menu>
);
}

export default NavBar;
