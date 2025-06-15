CREATE TABLE user_passengers (
    user_id INTEGER NOT NULL,
    passenger_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, passenger_id),
    CONSTRAINT fk_user
        FOREIGN KEY(user_id)
	    REFERENCES users(user_id)
	    ON DELETE CASCADE,
    CONSTRAINT fk_passenger
        FOREIGN KEY(passenger_id)
        REFERENCES passengers(passenger_id)
        ON DELETE CASCADE
);