#!/usr/bin/env python
import pytz
import pika
import sys
from datetime import datetime
import json


RABBITMQ_URL = "localhost"
EXCHANGE = "sensor_message"

message = {
    "event": "sensor_message",
    "sensor_id": "mock_sensor_1",
    "timestamp": datetime.now(pytz.utc).isoformat(),
    "value_type": "numerical",
    "value": 2
}

routing_key = f'{message["sensor_id"]}.{message["value_type"]}'

connection = pika.BlockingConnection(pika.ConnectionParameters(host=RABBITMQ_URL))
channel = connection.channel()
channel.basic_publish(exchange=EXCHANGE, routing_key=routing_key, body=json.dumps(message))
print("Sent %r:%r" % (routing_key, message))

connection.close()
