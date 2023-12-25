# Project Janus

## Description

The goal of this project is to build a flexible platform for agnostic time-series sensor data. The platform allows to store and retrieve various sensor data, based on your needs.

## Usage

To run the platform run on the sensor_repository folder:

```console
docker-compose up
```

This will start the platform and the message bus.

To emulate a sensor, try running in the sensors folder:

```console
pip install -r requirements.txt
python mock_numerical_sensor.py
```

This will send a single numerical sensor data point to the platform through the message bus.

To see the data in the platform it can be done a REST request to the platform API. In sensor_repository/postman there is a Postman collection available to use the API.


## Contributing

To contribute to this repository, feel free to [open an issue](https://github.com/diogodanielsoaresferreira/project-janus/issues) or create a [PR](https://github.com/diogodanielsoaresferreira/project-janus/pulls).

## License

This project uses the MIT License.