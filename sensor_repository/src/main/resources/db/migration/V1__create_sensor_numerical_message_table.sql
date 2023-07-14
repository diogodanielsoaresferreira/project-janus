CREATE TABLE IF NOT EXISTS sensor_numerical_message (
    id SERIAL,
    sensor_id TEXT NOT NULL,
    message_timestamp TIMESTAMPTZ NOT NULL,
    message_value numeric NOT NULL,
    PRIMARY KEY(id, message_timestamp)
);

SELECT create_hypertable('sensor_numerical_message','message_timestamp');
CREATE INDEX sensor_numerical_message_sensor_id_timestamp ON sensor_numerical_message (sensor_id, message_timestamp ASC);
