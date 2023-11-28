import { Content, Footer, Header } from 'antd/es/layout/layout';
import NavBar from './components/navbar';
import { App, ConfigProvider, Image, Layout } from 'antd';
import Landing from './pages/landing';
import Login from './pages/login';
import TripCard from './components/tripCard';
import BrowseTrips from './pages/browse';

const WebApp = () => (
  <ConfigProvider
    theme={{
      
        token: {
          "colorPrimary": "#142d4c",
          "colorInfo": "#142d4c",
          "colorLink": "#385170",
          "colorBgBase": "#ececec"
        }
      
  }}>
    <App>
      <Layout style={{minHeight: '100vh', justifyItems: 'center'}}>
        <Header style={{padding: 0}}>
          <NavBar></NavBar>
        </Header>
        <Content className="site-layout" style={{ padding: '40px 50px' }}>
          <BrowseTrips></BrowseTrips>
        </Content>
        <Footer style={{ textAlign: 'left', paddingLeft: '5%', backgroundColor: 'white', height: 'auto' }}>
          <Image src="https://i.ibb.co/JmB4xhT/Truckr-Logo.png" height={24} style={{paddingRight: 15}}></Image>© 2023 Truckr, Inc
        </Footer>
      </Layout>
    </App>
  </ConfigProvider>
  
);

export default WebApp;