import { ConfigProvider } from 'antd';
import { useTranslation } from 'react-i18next';
import { useEffect } from 'react';
import './i18n';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Landing from './pages/landing';
import BrowseTrips from './pages/browse';
import Login from './pages/login';
import Register from './pages/register';
import NotFound404 from './pages/404';
import MyItinerary from './pages/myItinerary';
import MyAlert from "./pages/myAlert";
import Profile from "./pages/profile";
import PastTrips from "./pages/pastTrips.tsx";
import MyPublications from './pages/myPublications.tsx';
import CreateTrip from "./pages/createTrip.tsx";
import ManageTrip from "./pages/manageTrip.tsx";
import ResetPasswordRequest from './pages/resetPasswordRequest.tsx';
import ResetPassword from './pages/resetPassword.tsx';
import SearchTrips from './pages/searchTrips.tsx';
import PublicationDetails from './pages/publicationDetails.tsx';
import EditProfile from './pages/editProfile.tsx';
import CustomLayout from './Components/customLayout.tsx';


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
          <Route path="/myItinerary" element={<MyItinerary/>} />
          <Route path="/myAlert" element={<MyAlert/>} />
          <Route path="/profile" element={<Profile/>} />
          <Route path="/pastTrips" element={<PastTrips/>}></Route>
          <Route path="/myPublications" element={<MyPublications/>}></Route>
          <Route path="/trips/create" element={<CreateTrip/>}></Route>
          <Route path="/trips/manageTrip" element={<ManageTrip/>}></Route>
          <Route path="/resetPasswordRequest" element={<ResetPasswordRequest/>}/>
          <Route path="/resetPassword" element={<ResetPassword/>}/>
          <Route path="/searchTrips" element={<SearchTrips/>}/>
          <Route path="/details" element={<PublicationDetails/>}/>
          <Route path="/profile/edit" element={<EditProfile/>}/>
          <Route path="*" element={<NotFound404/>} />

        </Routes>
      </CustomLayout>
    </Router>
  </ConfigProvider>
  );
};

export default WebApp;