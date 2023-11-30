import { ConfigProvider } from 'antd';
import { useTranslation } from 'react-i18next';
import { useEffect } from 'react';
import './i18n';
import CustomLayout from './components/customLayout';
import { BrowserRouter as Router, Route, Routes, useNavigate } from 'react-router-dom';
import Landing from './pages/landing';
import BrowseTrips from './pages/browse';
import Login from './pages/login';
import Register from './pages/register';
import NotFound404 from './pages/404';


const WebApp = () => {

  const { t, i18n } = useTranslation();

  useEffect(() => {
    const lng = navigator.language;
    console.log(lng);
    i18n.changeLanguage(lng);
  }, [])

  return(
  <ConfigProvider
    theme={{
      
        token: {
          "colorPrimary": "#142d4c",
          "colorInfo": "#142d4c",
          "colorLink": "#385170"
        }
      
  }}>
    <Router>
      <CustomLayout>
        <Routes>
          <Route path="/" element={<Landing/>}/>
          <Route path="/trips" element={<BrowseTrips/>}/>
          <Route path="/cargo" element={<BrowseTrips/>}/>
          <Route path="/login" element={<Login/>} />
          <Route path="/register" element={<Register/>} />
          <Route path="*" element={<NotFound404/>} />
        </Routes>
      </CustomLayout>
    </Router>
  </ConfigProvider>
  );
};

export default WebApp;