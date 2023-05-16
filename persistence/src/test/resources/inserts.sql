
INSERT INTO users(userid, cuit, email, name, role, password, accountverified, imageid)
VALUES (1, '20-12345678-9', 'martinh563@email.com' , 'Testing Testalez', 'PROVIDER','1234567890', false, null);

INSERT INTO passwordresets(userid, hash, createdate, completed)
VALUES (1, '1234567890', CURRENT_TIMESTAMP , false);

INSERT INTO trips(trip_id, provider_id, trucker_id, licenseplate, weight, volume, departure_date, arrival_date, origin, destination, type, price, trucker_confirmation, provider_confirmation, confirmation_date, imageid)
VALUES (1, 1, null, 'ABC123', 1000, 1000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Buenos Aires', 'Cordoba', 'REFRIGERATED', 1000, false, false, null, null);

INSERT INTO trips(trip_id, provider_id, trucker_id, licenseplate, weight, volume, departure_date, arrival_date, origin, destination, type, price, trucker_confirmation, provider_confirmation, confirmation_date, imageid)
VALUES (2, 1, 1, 'ABC545', 1000, 1000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Buenos Aires', 'Chivilcoy', 'REFRIGERATED', 1000, false, false, null, null);

INSERT INTO trips(trip_id, provider_id, trucker_id, licenseplate, weight, volume, departure_date, arrival_date, origin, destination, type, price, trucker_confirmation, provider_confirmation, confirmation_date, imageid)
VALUES (3, 1, 1, 'ABC575', 1000, 1000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Rosario', 'Chivilcoy', 'REFRIGERATED', 1000, false, false, null, null);

INSERT INTO trips(trip_id, provider_id, trucker_id, licenseplate, weight, volume, departure_date, arrival_date, origin, destination, type, price, trucker_confirmation, provider_confirmation, confirmation_date, imageid)
VALUES (4, null, 1, 'ABC509', 1000, 1000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Rosario', 'Buenos Aires', 'REFRIGERATED', 1000, false, false, null, null);

INSERT INTO trips(trip_id, provider_id, trucker_id, licenseplate, weight, volume, departure_date, arrival_date, origin, destination, type, price, trucker_confirmation, provider_confirmation, confirmation_date, imageid)
VALUES (5, 1, 1, 'ABC999', 1000, 1000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Chivilcoy', 'Buenos Aires', 'NORMAL', 1000, false, false, null, null);

INSERT INTO reviews(userid, tripid, rating, review)
VALUES (1,1,5,'Excelente viaje, muy recomendable');
INSERT INTO reviews(userid, tripid, rating, review)
VALUES (1,2,3,'Bien. Cumplio con lo acordado.');
INSERT INTO reviews(userid, tripid, rating, review)
VALUES (1,3,1,'No cumplio con su palabra.');
INSERT INTO reviews(userid, tripid, rating, review)
VALUES (1,4,2,'Mediocre trabajo. No llego a tiempo.');

INSERT INTO images(imageid, image)
VALUES(1, X'A0010203040506');

INSERT INTO proposals(proposal_id, trip_id, user_id, description)
VALUES (1,4,1,'Quiero que lleves mi carga.')



