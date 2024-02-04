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
import { CustomRoute } from './Components/PrivateRoute.tsx';


const WebApp = () => {

  const { i18n } = useTranslation();

  useEffect(() => {
    const lng = navigator.language;
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

            <Route path='/test' element={<Tester/>}/>
            <Route path="/" element={<Landing/>}/>
            <Route path="/trips" element={<CustomRoute render={() => <BrowseTrips tripOrRequest='TRIP'/>} noAuth possibleRoles={['PROVIDER']}></CustomRoute>}/>
            <Route path="/cargo" element={<CustomRoute render={() => <BrowseTrips tripOrRequest='REQUEST'/>} noAuth possibleRoles={['TRUCKER']}></CustomRoute>}/>
            <Route path="/login" element={<CustomRoute render={() => <Login/>} noAuth/>} />
            <Route path="/register" element={<CustomRoute render={() => <Register/>} noAuth/>} />
            <Route path="/myItinerary" element={<CustomRoute render={() => <MyItinerary/>} possibleRoles={['TRUCKER', 'PROVIDER']}/>} />
            <Route path="/myAlert" element={<CustomRoute render={() => <MyAlert/>} possibleRoles={['TRUCKER']}/>} />
            <Route path="/profile/:userId" element={<Profile/>} />
            <Route path="/profile" element={<Profile/>}/>
            <Route path="/profile/edit" element={<CustomRoute render={() => <EditProfile/>} possibleRoles={['TRUCKER', 'PROVIDER']}/>}/>
            
            
            <Route path="/pastTrips" element={<CustomRoute render={() => <PastTrips/>} possibleRoles={['TRUCKER', 'PROVIDER']}/>}></Route>
            <Route path="/sentOffers" element={<CustomRoute render={() => <SentOffers/>} possibleRoles={['TRUCKER', 'PROVIDER']}/>}></Route>
            <Route path="/myPublications" element={<CustomRoute render={() => <MyPublications/>} possibleRoles={['TRUCKER', 'PROVIDER']}/>}></Route>
            <Route path="/trips/create" element={<CustomRoute render={() => <CreateTrip/>} possibleRoles={['TRUCKER', 'PROVIDER']}/>}></Route>
            <Route path="/trips/manage/:tripId" element={<CustomRoute render={() => <ManageTrip/>} possibleRoles={['TRUCKER', 'PROVIDER']}/>}></Route>
            <Route path="/createAlert" element={<CustomRoute render={() => <CreateAlert/>} possibleRoles={['TRUCKER']}/>}></Route>

            <Route path="/trips/:tripId" element={<PublicationDetails/>}></Route>
            
            <Route path="/searchTrips"element={<CustomRoute render={() => <SearchTrips/>} possibleRoles={['PROVIDER']}/>}></Route>
            
            <Route path="/resetPassword" element={<CustomRoute render={() => <ResetPassword/>} noAuth/>}></Route>
            <Route path="/resetPasswordRequest" element={<CustomRoute render={() => <ResetPasswordRequest/>} noAuth/>}></Route>
            <Route path="/sendCounterOffer" element={<CustomRoute render={() => <SendCounterOffer/>} possibleRoles={['TRUCKER', 'PROVIDER']}/>}></Route>
            
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