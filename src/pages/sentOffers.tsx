import React from 'react';
import {Typography} from 'antd';
import '../styles/main.scss';
import OfferCard from '../components/offerCard';

const { Title } =Typography;

const SentOffers: React.FC = () => {

    const testDate = new Date();
    testDate.setDate(testDate.getDate());

    return (
        <div >
            <Title className='ml-10' level={3}>Sent Offers</Title>
            <OfferCard from='Tres Arroyos' to='Tres Arroyos' reviewNumber={0} reviewScore={0} price={0} dateTo={testDate} dateFrom={testDate} userImg="https://www.google.com/url?sa=i&url=https%3A%2F%2Fencrypted-tbn2.gstatic.com%2Flicensed-image%3Fq%3Dtbn%3AANd9GcTY5JbgSoX1j2qfxzrBwg8ySDSZ9hs82oDcqjygRZPmOxa42bXDulMaWSPLxKyPnj6V2JP7vw9ouP6b5ik&psig=AOvVaw3pwzd0z27A8sOIJciAhnio&ust=1701373319635000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCNizrNP76YIDFQAAAAAdAAAAABAK"></OfferCard>
        </div>
    );
}

export default SentOffers;