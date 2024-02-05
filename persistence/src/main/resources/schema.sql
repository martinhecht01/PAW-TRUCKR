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
        trip_id INT NOT NULL REFERENCES trips(trip_id) on delete cascade,
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

