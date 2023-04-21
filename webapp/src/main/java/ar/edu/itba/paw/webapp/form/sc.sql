INSERT INTO users(cuit,email,name)
VALUES ('20-43724688-3','tomigayba02yt@gmail.com', 'Julian Marenco'),
       ('20-43724699-3','tgaybare@itba.edu.ar','Fanny Cremonesse')

INSERT INTO trips(userid, licenseplate, availableweight, availablevolume, departuredate, arrivaldate, origin, destination, type, price, acceptuserid)
VALUES
(11, 'ABC158', 500, 2000, '2023-05-01 10:00:00', '2023-05-01 15:00:00', 'Rosario', 'Cordoba', 'Normal', 10000, NULL),
(11, 'XYZ362', 1000, 3000, '2023-05-02 12:00:00', '2023-05-02 18:00:00', 'Buenos Aires', 'Mendoza', 'Refrigerada', 25000, NULL),
(12, 'DEF000', 700, 2500, '2023-05-03 08:00:00', '2023-05-03 14:00:00', 'Mar del Plata', 'San Miguel de Tucuman', 'Peligrosa', 20000, NULL),
(12, 'GHI231', 900, 2800, '2023-05-04 09:00:00', '2023-05-04 16:00:00', 'La Plata', 'Salta', 'Normal', 15000, NULL),
(11, 'JKL785', 600, 2200, '2023-05-05 07:00:00', '2023-05-05 12:00:00', 'Cordoba', 'Rosario', 'Refrigerada', 12000, NULL),
(12, 'MNO090', 800, 2700, '2023-05-06 11:00:00', '2023-05-06 17:00:00', 'San Juan', 'Buenos Aires', 'Normal', 18000, NULL),
(12, 'PQR542', 650, 2300, '2023-05-07 13:00:00', '2023-05-07 18:00:00', 'Santa Fe', 'La Plata', 'Peligrosa', 14000, NULL),
(12, 'STU009', 850, 2900, '2023-05-08 15:00:00', '2023-05-08 20:00:00', 'Neuquen', 'Mendoza', 'Normal', 17000, NULL),
(11, 'VWX333', 750, 2600, '2023-05-09 10:00:00', '2023-05-09 16:00:00', 'San Miguel de Tucuman', 'Rosario', 'Refrigerada', 13000, NULL),
(11, 'YZA328', 950, 3200, '2023-05-10 11:00:00', '2023-05-10 17:00:00', 'Salta', 'Buenos Aires', 'Peligrosa', 19000, NULL);

