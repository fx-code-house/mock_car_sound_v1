#!/usr/bin/env python3
import frida
from flask import Flask, request, jsonify

app = Flask(__name__)
script = None


def init_frida():
    global script
    device = frida.get_usb_device()
    pid = device.spawn('com.carsystems.thor.app')
    session = device.attach(pid)

    with open('hook_jiami_api.js') as f:
        script = session.create_script(f.read())
    script.load()
    device.resume(pid)


@app.route('/init', methods=['POST'])
def init():
    data = request.get_json() or {}
    iv = data.get('iv', "0123456789ABCDEF0123456789ABCDEF")
    hw = data.get('hw', 1)
    fw = data.get('fw', 1)
    sn = data.get('sn', 123456)

    result = script.exports_sync.init(iv, hw, fw, sn)
    return jsonify(result)


@app.route('/encrypt', methods=['POST'])
def encrypt():
    data = request.get_json() or {}
    hex_data = data.get('hex', '')

    result = script.exports_sync.jiami(hex_data)
    return jsonify(result)


if __name__ == '__main__':
    init_frida()
    app.run(host='0.0.0.0', port=8000)