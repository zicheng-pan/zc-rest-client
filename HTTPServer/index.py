from flask import Flask, request

app = Flask(__name__)
import json


@app.route('/services/get/public/<code>/products/group/<store>')
def testGet(code, store):
    data = {"code": code, "store": store}
    return json.dumps(data)


@app.route('/services/post/public/<code>/products/group/<store>', methods=['post'])
def testPost(code, store):
    if not request.data:
        return ('fail')
    data = request.data.decode('utf-8')
    return data


@app.route('/services/actuator/shutdown', methods=['post','get'])
def shutDown():
    return "receive client call shut down"


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5000)
