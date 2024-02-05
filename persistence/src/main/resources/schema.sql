CREATE TABLE IF NOT EXISTS images (
        imageid SERIAL PRIMARY KEY,
        image BYTEA NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
        userid SERIAL PRIMARY KEY,
        cuit VARCHAR(255) UNIQUE,
        email VARCHAR(255),
        name VARCHAR(255),
        role VARCHAR(255),
        password VARCHAR(255),
        accountverified BOOLEAN,
        imageid INT REFERENCES images(imageid)
);

CREATE TABLE IF NOT EXISTS passwordresets(
        userid INT REFERENCES users(userid),
        hash INT PRIMARY KEY,
        createdate TIMESTAMP,
        completed VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS securetokens(
        userid INT REFERENCES users(userid),
        token INT PRIMARY KEY,
        expiredate TIMESTAMP
);

CREATE TABLE IF NOT EXISTS cities (
        id SERIAL PRIMARY KEY,
        name TEXT UNIQUE
);

CREATE TABLE IF NOT EXISTS trips (
        trip_id SERIAL PRIMARY KEY,
        provider_id INT REFERENCES users(userid),
        trucker_id INT REFERENCES users(userid),
        licenseplate VARCHAR(30),
        weight INT,
        volume INT,
        departure_date TIMESTAMP,
        arrival_date TIMESTAMP,
        origin VARCHAR(50),
        destination VARCHAR(50),
        type VARCHAR(50),
        price INT,
        trucker_confirmation BOOLEAN DEFAULT FALSE,
        provider_confirmation BOOLEAN DEFAULT FALSE,
        confirmation_date TIMESTAMP,
        imageid INT references images(imageid)
);

CREATE TABLE IF NOT EXISTS proposals (
        proposal_id SERIAL PRIMARY KEY,
        trip_id INT NOT NULL REFERENCES trips(trip_id),
        user_id INT NOT NULL REFERENCES users(userid),
        description VARCHAR(300),
        price INT NOT NULL DEFAULT 0
);




CREATE TABLE IF NOT EXISTS reviews (
        reviewid SERIAL PRIMARY KEY,
        userid INT ,
        tripid INT ,
        rating FLOAT CHECK(rating<=5 AND rating>=0),
        review VARCHAR(400),
        FOREIGN KEY (userid) REFERENCES users(userid),
        FOREIGN KEY (tripid) REFERENCES trips(trip_id)
);

CREATE TABLE IF NOT EXISTS cities(
        id SERIAL PRIMARY KEY,
        name TEXT UNIQUE
);

CREATE TABLE IF NOT EXISTS cargotypes(
        id SERIAL PRIMARY KEY,
        name TEXT UNIQUE
);

INSERT INTO cargotypes (id,name) VALUES
        (1,'Refrigerated'),
        (2,'Hazardous'),
        (3,'Normal');

INSERT INTO cities (name) VALUES
    ('Azul'),
    ('Bahía Blanca'),
    ('Buenos Aires'),
    ('Cafayate'),
    ('Catamarca'),
    ('Chajari'),
    ('Chivilcoy'),
    ('Choele Choel'),
    ('Chos Malal'),
    ('Comodoro Rivadavia'),
    ('Concepción del Uruguay'),
    ('Concordia'),
    ('Córdoba'),
    ('Corrientes'),
    ('El Bolsón'),
    ('El Calafate'),
    ('El Dorado'),
    ('Esquel'),
    ('Formosa'),
    ('General Pico'),
    ('General Roca'),
    ('Goya'),
    ('Gualeguaychú'),
    ('Jujuy'),
    ('Junín'),
    ('La Plata'),
    ('La Quiaca'),
    ('La Rioja'),
    ('Las Heras'),
    ('Las Lomitas'),
    ('Las Termas de Río Hondo'),
    ('Lincoln'),
    ('Lomas de Zamora'),
    ('Los Antiguos'),
    ('Mar del Plata'),
    ('Mendoza'),
    ('Mercedes'),
    ('Miramar'),
    ('Monte Caseros'),
    ('Neuquén'),
    ('Oberá'),
    ('Orán'),
    ('Paraná'),
    ('Paso de los Libres'),
    ('Pergamino'),
    ('Pinamar'),
    ('Posadas'),
    ('Presidencia Roque Sáenz Peña'),
    ('Puerto Deseado'),
    ('Puerto Iguazú'),
    ('Puerto Madryn'),
    ('Rawson'),
    ('Resistencia'),
    ('Río Cuarto'),
    ('Río Gallegos'),
    ('Río Grande'),
    ('Río Tercero'),
    ('Rivadavia'),
    ('Rosario'),
    ('Salta'),
    ('San Carlos de Bariloche'),
    ('San Fernando del Valle de Catamarca'),
    ('San Francisco'),
    ('San Isidro'),
    ('San Juan'),
    ('San Justo'),
    ('San Luis'),
    ('San Martín'),
    ('San Miguel de Tucumán'),
    ('San Nicolás de los Arroyos'),
    ('San Pedro'),
    ('San Rafael'),
    ('San Salvador de Jujuy'),
    ('Santa Fe'),
    ('Santa Rosa'),
    ('Santiago del Estero'),
    ('Tandil'),
    ('Tartagal'),
    ('Trelew'),
    ('Tres Arroyos'),
    ('Ushuaia'),
    ('Venado Tuerto'),
    ('Viedma'),
    ('Villa Ángela'),
    ('Villa Carlos Paz'),
    ('Villa Constitución'),
    ('Villa Dolores'),
    ('Villa María'),
    ('Villa Mercedes'),
    ('Zárate');

