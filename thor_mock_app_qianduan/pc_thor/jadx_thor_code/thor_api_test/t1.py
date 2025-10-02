#!/usr/bin/env python3
"""
Thor API测试 - 使用HTTP API调用
"""

import requests
import json


def test_thor_api():
    try:
        api_base = "http://localhost:8000"
        
        print("🌐 连接API服务器...")

        print("\n=== 开始测试 ===")

        # 测试1: 初始化AES
        print("\n1. 测试初始化AES...")
        init_data = {
            "iv": "0123456789ABCDEF0123456789ABCDEF",
            "hw": 1,
            "fw": 1,
            "sn": 123456
        }
        response1 = requests.post(f"{api_base}/init", json=init_data)
        result1 = response1.json()
        print(f"初始化结果: {result1}")

        # 测试2: 加密 "Hello"
        print("\n2. 测试加密 'Hello'...")
        encrypt_data2 = {"hex": "48656C6C6F"}
        response2 = requests.post(f"{api_base}/encrypt", json=encrypt_data2)
        result2 = response2.json()
        print(f"加密结果: {result2}")

        # 测试3: 加密 "Hello World!"
        print("\n3. 测试加密 'Hello World!'...")
        encrypt_data3 = {"hex": "48656C6C6F20576F726C6421"}
        response3 = requests.post(f"{api_base}/encrypt", json=encrypt_data3)
        result3 = response3.json()
        print(f"加密结果: {result3}")

        # 测试4: 加密固定数据
        print("\n4. 测试加密固定数据...")
        encrypt_data4 = {"hex": "0123456789ABCDEF"}
        response4 = requests.post(f"{api_base}/encrypt", json=encrypt_data4)
        result4 = response4.json()
        print(f"加密结果: {result4}")

        print("\n✅ 测试完成!")

        input("\n按Enter键退出...")

    except requests.exceptions.ConnectionError:
        print("❌ 无法连接到API服务器，请确保服务器正在运行在 http://localhost:8000")
    except Exception as e:
        print(f"❌ 错误: {e}")


if __name__ == '__main__':
    test_thor_api()