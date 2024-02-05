import { ConfigProvider } from 'antd';
import { useTranslation } from 'react-i18next';
import {lazy, Suspense, useEffect} from 'react';
import './i18n';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { CustomRoute } from './Components/PrivateRoute.tsx';


const Landing = lazy(() => import( './pages/landing'));
const BrowseTrips = lazy(() => import('./pages/browse'));
const Login = lazy(() => import('./pages/login'));
const Register = lazy(() => import('./pages/register'));
const NotFound404 = lazy(() => import('./pages/404'));
const MyItinerary = lazy(() => import('./pages/myItinerary'));
const MyAlert = lazy(() => import('./pages/myAlert'));
const Profile = lazy(() => import('./pages/profile'));
const PastTrips = lazy(() => import('./pages/pastTrips.tsx'));
const MyPublications = lazy(() => import('./pages/myPublications.tsx'));
const CreateTrip = lazy(() => import('./pages/createTrip.tsx'));
const ManageTrip = lazy(() => import('./pages/manageTrip.tsx'));
const EditProfile = lazy(() => import('./pages/editProfile.tsx'));
const PublicationDetails = lazy(() => import('./pages/publicationDetails.tsx'));
const SearchTrips = lazy(() => import('./pages/searchTrips.tsx'));
const ResetPassword = lazy(() => import('./pages/resetPassword.tsx'));
const ResetPasswordRequest = lazy(() => import('./pages/resetPasswordRequest.tsx'));
const SendCounterOffer = lazy(() => import('./pages/sendCounterOffer.tsx'));
const CustomLayout = lazy(() => import('./Components/customLayout.tsx'));
const CreateAlert = lazy(() => import('./pages/createAlert'));
const SentOffers = lazy(() => import('./pages/sentOffers'));
const AuthProvider = lazy(() => import('./hooks/authProvider.tsx'));
const InternalError500 = lazy(() => import('./pages/500.tsx'));
const AccessDenied403 = lazy(() => import('./pages/403.tsx'));
const VerifyAccount = lazy(() => import('./pages/verifyUser.tsx'));

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
    <Router basename='/paw-2023a-08'>
      <AuthProvider>
        <CustomLayout>
          <Suspense fallback={<div>Loading...</div>}>
          <Routes>
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
            <Route path="/verifyAccount" element={<CustomRoute render={() => <VerifyAccount/>} noAuth/>}></Route>
            <Route path="/trips/:tripId" element={<PublicationDetails/>}></Route>
            
            <Route path="/searchTrips" element={<CustomRoute render={() => <SearchTrips/>} possibleRoles={['PROVIDER']}/>}></Route>
            
            <Route path="/resetPassword" element={<CustomRoute render={() => <ResetPassword/>} noAuth/>}></Route>
            <Route path="/resetPasswordRequest" element={<CustomRoute render={() => <ResetPasswordRequest/>} noAuth/>}></Route>
            <Route path="/sendCounterOffer" element={<CustomRoute render={() => <SendCounterOffer/>} possibleRoles={['TRUCKER', 'PROVIDER']}/>}></Route>
            
            <Route path="*" element={<NotFound404/>} />
            <Route path="/404" element={<NotFound404/>} />
            <Route path='/500' element={<InternalError500/>}/>
            <Route path='/403' element={<AccessDenied403/>}/>
          </Routes>
          </Suspense>
        </CustomLayout>
      </AuthProvider>
    </Router>
  </ConfigProvider>
  );
};

export default WebApp;