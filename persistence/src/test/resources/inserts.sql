
INSERT INTO users(userid, cuit, email, name, role, password, accountverified, imageid)
VALUES (1, '20-12345678-9', 'martinh563@email.com' , 'Testing Testalez', 'TRUCKER','1234567890', false, 0);

INSERT INTO users(userid, cuit, email, name, role, password, accountverified, imageid)
VALUES (2, '20-12345679-9', 'martinh563@email.com' , 'Testing Testalez', 'PROVIDER','1234567890', false, 0);

INSERT INTO passwordresets(userid, hash, createdate, completed)
VALUES (1, '1234567890', CURRENT_TIMESTAMP , false);

INSERT INTO trips(trip_id, provider_id, trucker_id, licenseplate, weight, volume, departure_date, arrival_date, origin, destination, type, price, trucker_confirmation, provider_confirmation, confirmation_date, imageid)
VALUES (1, 2, 1, 'ABC123', 1000, 1000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Buenos Aires', 'Cordoba', 'REFRIGERATED', 1000, false, false, null, null);

INSERT INTO trips(trip_id, provider_id, trucker_id, licenseplate, weight, volume, departure_date, arrival_date, origin, destination, type, price, trucker_confirmation, provider_confirmation, confirmation_date, imageid)
VALUES (2, 1, null, 'ABC545', 1000, 1000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Buenos Aires', 'Chivilcoy', 'REFRIGERATED', 1000, false, false, null, null);

INSERT INTO trips(trip_id, provider_id, trucker_id, licenseplate, weight, volume, departure_date, arrival_date, origin, destination, type, price, trucker_confirmation, provider_confirmation, confirmation_date, imageid)
VALUES (3, 1, 1, 'ABC575', 1000, 1000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Rosario', 'Chivilcoy', 'REFRIGERATED', 1000, false, false, null, null);

INSERT INTO trips(trip_id, provider_id, trucker_id, licenseplate, weight, volume, departure_date, arrival_date, origin, destination, type, price, trucker_confirmation, provider_confirmation, confirmation_date, imageid)
VALUES (4, null, 1, 'ABC509', 1000, 1000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Rosario', 'Buenos Aires', 'REFRIGERATED', 1000, false, false, null, null);

INSERT INTO trips(trip_id, provider_id, trucker_id, licenseplate, weight, volume, departure_date, arrival_date, origin, destination, type, price, trucker_confirmation, provider_confirmation, confirmation_date, imageid)
VALUES (5, 1, 1, 'ABC999', 1000, 1000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Chivilcoy', 'Buenos Aires', 'NORMAL', 1000, false, false, null, null);

INSERT INTO reviews(reviewid, userid, tripid, rating, review)
VALUES (2, 1, 2, 3, 'Good. Met the agreed terms.');


INSERT INTO reviews(reviewid, userid, tripid, rating, review)
VALUES (3, 1, 3, 1, 'Did not keep their word.');


INSERT INTO reviews(reviewid, userid, tripid, rating, review)
VALUES (4, 1, 4, 2, 'Average job. Did not arrive on time.');


INSERT INTO reviews(reviewid, userid, tripid, rating, review)
VALUES (5, 1, 5, 4, 'Excelente viaje, muy recomendable');

INSERT INTO images(imageid, image)
VALUES(2, X'A0010203040506');
INSERT INTO images(imageid, image)
VALUES(0, X'A001029903040506');

INSERT INTO proposals(proposal_id, trip_id, user_id, description)
VALUES (1,2,1,'Quiero que lleves mi carga.');
