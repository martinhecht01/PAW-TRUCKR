import React, { useEffect, useState } from 'react';
import { Image, Menu } from 'antd';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { getClaims } from '../api/userApi';

import '../styles/main.scss'
import { LogoutOutlined } from '@ant-design/icons';
import { useAuthContext } from '../hooks/authProvider';

// Ensure these labels are translated in your JSON file
const noAuth = [
  {
    label: 'BrowseTrips',
    key: 'trips',
  },
  {
    label: 'BrowseCargo',
    key: 'cargo',
  }
];

const trucker = [
  {
    label: 'BrowseCargo',
    key: 'cargo',
  },
  {
    label: 'myItinerary',
    key: 'myItinerary',
  },
  {
    label: 'MyPublications',
    key: 'myPublications',
  },
  {
    label: 'SentOffers',
    key: 'sentOffers',
  },
  {
    label: 'MyAlert',
    key: 'myAlert',
  }
]

const provider = [
  {
    label: 'SearchTrips',
    key: 'searchTrips'
  },
  {
    label: 'myItinerary',
    key: 'myItinerary',
  },
  {
    label: 'MyPublications',
    key: 'myPublications',
  },
  {
    label: 'SentOffers',
    key: 'sentOffers',
  }
]

const NavBar: React.FC = () => {
  const [role, setRole] = useState<string>('');

  const { t } = useTranslation();
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
              item == null ? null : <Menu.Item key={item.key} data-testid='trucker-navBar' onClick={() => navigate(`/${item.key}`)}>{t("landing." + item.label)}</Menu.Item>
          ))}
          <Menu.Item key="profile" style={{marginLeft: 'auto'}} onClick={() => navigate('/profile')}>{getClaims()?.cuit}</Menu.Item>
          <Menu.Item key="logout" style={{fontWeight: 'normal'}} onClick={logout}><LogoutOutlined/></Menu.Item>
        </>
        : 
        role === 'PROVIDER' ? 
        <>
            {provider.map((item) => (
              item == null ? null : <Menu.Item key={item.key} data-testid='provider-navBar' onClick={() => navigate(`/${item.key}`)}>{t("landing."+item.label)}</Menu.Item>
            ))}
            <Menu.Item key="profile" style={{marginLeft: 'auto'}} onClick={() => navigate('/profile')}>{getClaims()?.cuit}</Menu.Item>
            <Menu.Item key="logout" style={{fontWeight: 'normal'}} onClick={logout}><LogoutOutlined/></Menu.Item>
        </>
        : 
          <>
            {noAuth.map((item) => (
              item == null ? null : <Menu.Item data-testid='noAuth-navBar' key={item.key} onClick={() => navigate(`/${item.key}`)}>{t('landing.' + item.label)}</Menu.Item>
            ))}
            <Menu.Item key="login" style={{marginLeft: 'auto'}} onClick={() => navigate('/login')}>{t('common.login')}</Menu.Item>
            <Menu.Item key="register" style={{fontWeight: 'normal'}} onClick={() => navigate('/register')}>{t('common.register')}</Menu.Item>
          </>
      }
      
    </Menu>
);
}

export default NavBar;
