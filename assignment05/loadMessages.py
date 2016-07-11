import boto3
import json
import time

import csv
import pymysql.cursors
import config

sqs = boto3.resource('sqs')

# Get the queue. This returns an SQS.Queue instance
queue = sqs.get_queue_by_name(QueueName='cnu2016_ketan_patil_log_queue')

#connect to database
connection = pymysql.connect(host = config.host,
	port = config.port,
    user = config.user,
    passwd = config.passwd,
    db='cnu2016_ketan_patil')
cursor = connection.cursor()

# You can now access identifiers and attributes
print(queue.url)

while 1 :
    for message in queue.receive_messages():
        data = json.loads(message.body)

        query = """INSERT INTO `polled_messages`(`timestamp`,`url`,`parameter`,`responseCode`,`ipAddress`,`diff`) VALUES (%s, %s, %s, %s, %s)"""

        cursor.execute(query, [data['timestamp'],data['url'] ,data['parameter'] ,data['responseCode'] , data['ipAddress'],data['diff']])


        # Let the queue know that the message is processed
        message.delete()
    time.sleep(0.5)


connection.commit()
connection.close()