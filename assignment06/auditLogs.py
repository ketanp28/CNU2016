from flask import Flask, jsonify, abort, request
from flask_sqlalchemy import SQLAlchemy
from sqlalchemy import *;


import config


auditApi = Flask(__name__)
auditApi.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://'+config.user+':'+config.passwd+'@'+config.host+':'+config.port+'/cnu2016_ketan_patil'
db = SQLAlchemy(auditApi)


class polled_messages(db.Model):
    __tablename__ = 'polled_messages'
    timestamp = db.Column(db.DateTime,primary_key=True)
    url = db.Column(db.String(80))
    parameters = db.Column(db.String(120))
    response_code = db.Column(db.Integer)
    request_ip_address = db.Column(db.String(80))
    request_type = db.Column(db.String(80))
    request_duration_ms = db.Column(db.Integer)


    def __init__(self, timestamp,url,parameters,response_code,request_ip_address,request_type,request_duration_ms):
        self.timestamp = timestamp
        self.url = url
        self.request_type = request_type
        self.parameters = parameters
        self.request_duration_ms = request_duration_ms
        self.response_code = response_code
        self.request_ip_address = request_ip_address

    def serialize(self):
        return {
            'timestamp': self.timestamp,
            'url': self.url,
            'request_type' : self.request_type,
            'parameters': self.parameters,
            'request_duration_ms': self.request_duration_ms,
            'response_code': self.response_code,
            'request_ip_address': self.request_ip_address
        }


@auditApi.route('/api/auditLogs',methods = ['GET'])
def getlogs():
    startTime = request.args.get('startTime')
    endTime = request.args.get('endTime')

    offset = request.args.get('offset')
    limit_max = request.args.get('limit')

    if startTime is not None:
        polled_messages.query = polled_messages.query.filter(polled_messages.timestamp >= startTime)
    if endTime is not None:
        polled_messages.query = polled_messages.query.filter(polled_messages.timestamp <= endTime)
    if offset is None:
        offset = 0
    if (limit_max is None) or (int(limit_max) > 10 or (int(limit_max) <= 0)):
        limit_max = 10

    return jsonify({'data': [a.serialize() for a in polled_messages.query.order_by(polled_messages.timestamp.desc()).limit(limit_max).offset(offset).all()]})


if __name__ == '__main__':
   auditApi.run(debug = True)



