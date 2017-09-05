#!/usr/bin/env python
# -*- coding: utf-8 -*-
# File: rmq_listener.py
# Author: Clement Trosa <me@trosa.io>
# Date: 05/09/2017 09:11:00 PM
# Last Modified Date: 05/09/2017 09:21:41 PM
# Last Modified By: Clement Trosa <me@trosa.io>

import pika

credentials = pika.PlainCredentials('trosa', 'w00t')
parameters = pika.ConnectionParameters('192.168.0.20', 5672, '/', credentials)
connection = pika.BlockingConnection(parameters)
channel = connection.channel()
channel.queue_declare(queue='hello')

def callback(ch, method, properties, body):
    print(" [x] Received %r" % body)

channel.basic_consume(callback,
                      queue='hello',
                      no_ack=True)

print(' [*] Waiting for messages. To exit press CTRL+C')
channel.start_consuming()
