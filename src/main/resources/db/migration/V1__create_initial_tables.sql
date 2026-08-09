CREATE TABLE movie (
                       id VARCHAR(255) PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       description TEXT,
                       duration INT NOT NULL
);

CREATE TABLE "user" (
                        id VARCHAR(255) PRIMARY KEY,
                        username VARCHAR(255) NOT NULL,
                        email VARCHAR(255) NOT NULL
);

CREATE TABLE room (
                      id VARCHAR(255) PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      capacity INT NOT NULL
);

CREATE TABLE seat (
                      id VARCHAR(255) PRIMARY KEY,
                      room_id VARCHAR(255) NOT NULL,
                      seat_number VARCHAR(50) NOT NULL,
                      CONSTRAINT fk_room FOREIGN KEY (room_id) REFERENCES room(id)
);

CREATE TABLE reservation (
                             id VARCHAR(255) PRIMARY KEY,
                             user_id VARCHAR(255) NOT NULL,
                             movie_id VARCHAR(255) NOT NULL,
                             reservation_date TIMESTAMP NOT NULL,
                             CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES "user"(id),
                             CONSTRAINT fk_movie FOREIGN KEY (movie_id) REFERENCES movie(id)
);