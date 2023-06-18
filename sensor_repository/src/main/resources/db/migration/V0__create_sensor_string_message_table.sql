CREATE TABLE IF NOT EXISTS sensor_string_message (
    id SERIAL,
    sensor_id TEXT NOT NULL,
    message_timestamp TIMESTAMPTZ NOT NULL,
    message_value TEXT NOT NULL,
    PRIMARY KEY(id, message_timestamp)
);

SELECT create_hypertable('sensor_string_message','message_timestamp');
CREATE INDEX sensor_id_timestamp ON sensor_string_message (sensor_id, message_timestamp ASC);
