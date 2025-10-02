#!/usr/bin/env python3
import frida
from flask import Flask, request, jsonify

app = Flask(__name__)
script = None


def init_frida():
    global script
    device = frida.get_usb_device()
    
    # 🔥 修复：优先连接现有进程，如果没有则spawn新进程
    try:
        # 首先尝试连接现有的Thor应用进程
        processes = device.enumerate_processes()
        thor_process = None
        for process in processes:
            if 'thor' in process.name.lower():
                thor_process = process
                break
        
        if thor_process:
            print(f"✅ 找到运行中的Thor进程: {thor_process.name} (PID: {thor_process.pid})")
            session = device.attach(thor_process.pid)
        else:
            print("🚀 未找到运行中的Thor进程，启动新进程...")
            pid = device.spawn('com.carsystems.thor.app')
            session = device.attach(pid)
            device.resume(pid)
            print("✅ 新Thor进程已启动")
    
    except Exception as e:
        print(f"❌ Frida连接失败: {e}")
        raise

    # 读取hook脚本文件
    import os
    script_path = os.path.join(os.path.dirname(__file__), 'hook_jiami_api.js')
    with open(script_path, 'r') as f:
        script_code = f.read()
    
    script = session.create_script(script_code)
    script.load()
    print("✅ Frida hook脚本已加载")


@app.route('/init', methods=['POST'])
def init():
    try:
        if not script:
            return jsonify({'success': False, 'error': 'Frida脚本未初始化'})
            
        data = request.get_json() or {}
        iv = data.get('iv', "0123456789ABCDEF0123456789ABCDEF")
        hw = data.get('hw', 1)
        fw = data.get('fw', 1)
        sn = data.get('sn', 123456)

        print(f"🔄 初始化加密器: IV长度={len(iv)//2}, HW={hw}, FW={fw}, SN={sn}")
        result = script.exports_sync.init(iv, hw, fw, sn)
        print(f"✅ 初始化结果: {result}")
        return jsonify(result)
    except Exception as e:
        error_msg = f"API初始化失败: {str(e)}"
        print(f"❌ {error_msg}")
        return jsonify({'success': False, 'error': error_msg})


@app.route('/encrypt', methods=['POST'])
def encrypt():
    try:
        if not script:
            return jsonify({'success': False, 'error': 'Frida脚本未初始化'})
            
        data = request.get_json() or {}
        hex_data = data.get('hex', '')
        
        if not hex_data:
            return jsonify({'success': False, 'error': '加密数据为空'})

        print(f"🔐 加密数据长度: {len(hex_data)//2}字节")
        result = script.exports_sync.jiami(hex_data)
        
        if result.get('success'):
            print(f"✅ 加密成功: {len(result.get('encrypted', ''))//2}字节")
        else:
            print(f"❌ 加密失败: {result.get('error')}")
            
        return jsonify(result)
    except Exception as e:
        error_msg = f"API加密失败: {str(e)}"
        print(f"❌ {error_msg}")
        return jsonify({'success': False, 'error': error_msg})


if __name__ == '__main__':
    try:
        print("🚀 正在初始化Frida...")
        init_frida()
        print("✅ Frida初始化完成")
        print("🌐 API服务器启动在 http://localhost:8000")
        app.run(host='0.0.0.0', port=8000, debug=True)
    except Exception as e:
        print(f"❌ 服务器启动失败: {e}")
        import traceback
        traceback.print_exc()