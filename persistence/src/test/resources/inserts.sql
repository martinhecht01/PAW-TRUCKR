INSERT INTO users(userid, cuit, email, name, role, password, accountverified, imageid)
VALUES (1, '20-12345678-9', 'martinh563@email.com' , 'Testing Testalez', 'PROVIDER','1234567890', false, null);

INSERT INTO passwordresets(userid, hash, createdate, completed)
VALUES (1, '1234567890', CURRENT_TIMESTAMP , false);


INSERT INTO trips(trip_id, provider_id, trucker_id, licenseplate, weight, volume, departure_date, arrival_date, origin, destination, type, price, trucker_confirmation, provider_confirmation, confirmation_date, imageid)
VALUES (1, 1, 1, 'ABC123', 1000, 1000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Buenos Aires', 'Cordoba', 'REFRIGERATED', 1000, false, false, null, null);
