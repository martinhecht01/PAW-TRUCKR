import { Content, Footer, Header } from 'antd/es/layout/layout';
import NavBar from './components/navbar';
import { App, ConfigProvider, Layout } from 'antd';
import Landing from './pages/landing';
import Login from './pages/login';
import Register from './pages/register';

const WebApp = () => (
  <ConfigProvider
    theme={{
      
        token: {
          "colorPrimary": "#142d4c",
          "colorInfo": "#142d4c",
          "colorLink": "#9fd3c7",
          "colorBgBase": "#ececec"
        }
      
  }}>
    <App>
      <Layout style={{minHeight: '100vh', justifyItems: 'center'}}>
        <Header style={{padding: 0}}>
          <NavBar></NavBar>
        </Header>
        <Content className="site-layout" style={{ padding: '40px 50px' }}>
          <Landing></Landing>
        </Content>
        <Footer style={{ textAlign: 'left', paddingLeft: '5%' }}>© 2023 Truckr, Inc</Footer>
      </Layout>
    </App>
  </ConfigProvider>
  
);

export default WebApp;