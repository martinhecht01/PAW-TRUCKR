import React, { useState } from 'react';
import type { MenuProps } from 'antd';
import { Image, Menu } from 'antd';

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

  const onClick: MenuProps['onClick'] = (e) => {
    //router(`/${e.key}`)
    console.log('click ', e);
    setCurrent(e.key);
  };

return (
    <Menu mode="horizontal" style={{display: 'flex', alignItems: 'center'}}>
        <Menu.Item key="" style={{width: 200, display: 'flex',  justifyContent: 'center'}} ><Image preview={false} src='https://i.ibb.co/JmB4xhT/Truckr-Logo.png'></Image></Menu.Item>
        {sections.map((item) => (
        item == null ? null :  <Menu.Item key={item.key}>{item.label}</Menu.Item>
        ))}
        <Menu.Item key="login" style={{marginLeft: 'auto'}}>Ingresar</Menu.Item>
        <Menu.Item key="register" style={{fontWeight: 'normal'}}>Crear cuenta</Menu.Item>
    </Menu>
);
}

export default NavBar;
