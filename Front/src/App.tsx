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
import EditProfile from './pages/editProfile.tsx';
import PublicationDetails from './pages/publicationDetails.tsx';
import SearchTrips from './pages/searchTrips.tsx';
import ResetPassword from './pages/resetPassword.tsx';
import ResetPasswordRequest from './pages/resetPasswordRequest.tsx';
import SendCounterOffer from "./pages/sendCounterOffer.tsx";
import CustomLayout from './Components/customLayout.tsx';
import CreateAlert from "./pages/createAlert";
import SentOffers from "./pages/sentOffers";
import Tester from './pages/endpointTester.tsx';
import AuthProvider from './hooks/authProvider.tsx';


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
      <AuthProvider>
        <CustomLayout>
          <Routes>
            <Route path="/" element={<Landing/>}/>
            <Route path="/trips" element={<BrowseTrips tripOrRequest='TRIP'/>}/>
            <Route path="/cargo" element={<BrowseTrips tripOrRequest='REQUEST'/>}/>
            <Route path="/login" element={<Login/>} />
            <Route path="/register" element={<Register/>} />
            <Route path="/myItinerary" element={<MyItinerary/>} />
            <Route path="/myAlert" element={<MyAlert/>} />
            <Route path="/profile/:userId" element={<Profile/>} />
            <Route path="/profile" element={<Profile/>} />
            <Route path="/profile/edit" element={<EditProfile/>} />
            <Route path="/pastTrips" element={<PastTrips/>}></Route>
            <Route path="/sentOffers" element={<SentOffers/>}></Route>
            <Route path="/myPublications" element={<MyPublications/>}></Route>
            <Route path="/trips/create" element={<CreateTrip/>}></Route>
            <Route path="/trips/manage/:tripId" element={<ManageTrip/>}></Route>
            <Route path="/createAlert" element={<CreateAlert/>}></Route>
            <Route path="/trips/:tripId" element={<PublicationDetails/>}></Route>
            <Route path="/searchTrips" element={<SearchTrips/>}></Route>
            <Route path="/resetPassword" element={<ResetPassword/>}></Route>
            <Route path="/resetPasswordRequest" element={<ResetPasswordRequest/>}></Route>
            <Route path="/sendCounterOffer" element={<SendCounterOffer/>}></Route>
            <Route path='/test' element={<Tester/>}/>
            <Route path="*" element={<NotFound404/>} />
            <Route path="/404" element={<NotFound404/>} />
          </Routes>
        </CustomLayout>
      </AuthProvider>
    </Router>
  </ConfigProvider>
  );
};

export default WebApp;