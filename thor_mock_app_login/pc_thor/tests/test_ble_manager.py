#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
BleManager 单元测试
"""

import os
import sys
import logging
import pytest
import asyncio
from unittest.mock import MagicMock, patch, AsyncMock

# 将父目录加入到模块搜索路径
sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from ble_manager import BleManager
from events import EventBus, BluetoothScanResultEvent, BluetoothDeviceConnectionChangedEvent

# 配置日志
logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger("TestBleManager")

class MockBleakDevice:
    """模拟 Bleak 设备"""
    
    def __init__(self, address="00:11:22:33:44:55", name="MockDevice", rssi=-60):
        self.address = address
        self.name = name
        self.rssi = rssi
        self.metadata = {"uuids": []}

class MockBleakClient:
    """模拟 Bleak 客户端"""
    
    def __init__(self, address, **kwargs):
        self.address = address
        self.is_connected = False
        self.services = {}
        self.callbacks = {}
        self.mtu_size = 100
    
    async def connect(self, **kwargs):
        self.is_connected = True
        return True
    
    async def disconnect(self):
        self.is_connected = False
        return True
    
    async def get_services(self):
        return self.services
    
    async def start_notify(self, char_uuid, callback):
        self.callbacks[char_uuid] = callback
        return True
    
    async def stop_notify(self, char_uuid):
        if char_uuid in self.callbacks:
            del self.callbacks[char_uuid]
        return True
    
    async def write_gatt_char(self, char_uuid, data, response=False):
        return True
    
    async def read_gatt_char(self, char_uuid):
        return b"\x01\x02\x03\x04"
    
    def trigger_notification(self, char_uuid, data):
        """触发通知回调"""
        if char_uuid in self.callbacks:
            self.callbacks[char_uuid](char_uuid, data)

class MockBleakScanner:
    """模拟 Bleak 扫描器"""
    
    @staticmethod
    async def discover(timeout=5.0, **kwargs):
        """模拟发现设备"""
        devices = [
            MockBleakDevice(address="00:11:22:33:44:55", name="THOR_123456", rssi=-60),
            MockBleakDevice(address="AA:BB:CC:DD:EE:FF", name="OtherDevice", rssi=-75)
        ]
        return devices
    
    @staticmethod
    async def find_device_by_address(address, timeout=5.0, **kwargs):
        """模拟通过地址查找设备"""
        if address == "00:11:22:33:44:55":
            return MockBleakDevice(address=address, name="THOR_123456", rssi=-60)
        return None

@pytest.fixture
def event_bus():
    """事件总线治具"""
    EventBus._instance = None
    return EventBus.get_instance()

@pytest.fixture
def ble_manager():
    """蓝牙管理器治具"""
    BleManager._instance = None
    return BleManager.get_instance()

@pytest.mark.asyncio
async def test_scan(ble_manager, event_bus, monkeypatch):
    """测试扫描功能"""
    # 模拟 BleakScanner.discover
    monkeypatch.setattr("bleak.BleakScanner.discover", MockBleakScanner.discover)
    
    # 创建事件监听器
    scan_results = []
    
    def on_scan_result(event):
        scan_results.append(event.device)
    
    # 注册事件监听器
    event_bus.register(BluetoothScanResultEvent, on_scan_result)
    
    # 执行扫描
    await ble_manager.start_scan(duration=1)
    
    # 验证结果
    assert len(scan_results) == 2
    assert scan_results[0].name == "THOR_123456"
    assert scan_results[0].address == "00:11:22:33:44:55"
    assert scan_results[1].name == "OtherDevice"
    
    # 清理
    event_bus.unregister(BluetoothScanResultEvent, on_scan_result)

@pytest.mark.asyncio
async def test_connect_disconnect(ble_manager, event_bus, monkeypatch):
    """测试连接和断开功能"""
    # 模拟 BleakScanner.find_device_by_address 和 BleakClient
    monkeypatch.setattr("bleak.BleakScanner.find_device_by_address", MockBleakScanner.find_device_by_address)
    
    # 创建连接状态变化监听器
    connection_events = []
    
    def on_connection_changed(event):
        connection_events.append(event)
    
    # 注册事件监听器
    event_bus.register(BluetoothDeviceConnectionChangedEvent, on_connection_changed)
    
    # 创建模拟客户端
    mock_client = MockBleakClient("00:11:22:33:44:55")
    
    # 使用补丁替换 BleakClient 类
    with patch("bleak.BleakClient", return_value=mock_client):
        # 连接设备
        success = await ble_manager.connect("00:11:22:33:44:55")
        
        # 验证连接成功
        assert success is True
        assert ble_manager.is_connected() is True
        
        # 验证连接事件
        assert len(connection_events) == 1
        assert connection_events[0].is_connected is True
        assert connection_events[0].device_address == "00:11:22:33:44:55"
        
        # 断开连接
        await ble_manager.disconnect()
        
        # 验证断开连接
        assert ble_manager.is_connected() is False
        
        # 验证断开连接事件
        assert len(connection_events) == 2
        assert connection_events[1].is_connected is False
        assert connection_events[1].device_address == "00:11:22:33:44:55"
    
    # 清理
    event_bus.unregister(BluetoothDeviceConnectionChangedEvent, on_connection_changed)

@pytest.mark.asyncio
async def test_execute_command(ble_manager, monkeypatch):
    """测试执行命令功能"""
    # 创建模拟客户端
    mock_client = MockBleakClient("00:11:22:33:44:55")
    
    # 使用补丁替换 BleakClient 类
    with patch("bleak.BleakClient", return_value=mock_client):
        # 连接设备
        await ble_manager.connect("00:11:22:33:44:55")
        
        # 模拟写入和读取特征值
        mock_write = AsyncMock(return_value=True)
        mock_client.write_gatt_char = mock_write
        
        # 模拟通知回调
        async def mock_notify():
            # 延迟模拟设备响应
            await asyncio.sleep(0.1)
            mock_client.trigger_notification(
                BleManager.NOTIFY_CHARACTERISTIC,
                b"\x01\x02\x03\x04\x05\x06\x07\x08"
            )
        
        # 执行命令
        with patch("ble_command.BleCommands.create_hardware_command", return_value=b"\x01\x02\x03\x04"):
            # 启动模拟通知任务
            asyncio.create_task(mock_notify())
            
            # 执行硬件命令
            result = await ble_manager.execute_hardware_command()
            
            # 验证写入被调用
            mock_write.assert_called_once()
            
            # 验证结果
            assert result is not None
        
        # 断开连接
        await ble_manager.disconnect()

@pytest.mark.asyncio
async def test_get_battery_level(ble_manager, monkeypatch):
    """测试获取电池电量功能"""
    # 创建模拟客户端
    mock_client = MockBleakClient("00:11:22:33:44:55")
    
    # 使用补丁替换 BleakClient 类
    with patch("bleak.BleakClient", return_value=mock_client):
        # 连接设备
        await ble_manager.connect("00:11:22:33:44:55")
        
        # 模拟写入和读取特征值
        mock_write = AsyncMock(return_value=True)
        mock_client.write_gatt_char = mock_write
        
        # 模拟通知回调
        async def mock_notify():
            # 延迟模拟设备响应
            await asyncio.sleep(0.1)
            # 模拟电池电量响应：状态OK，电量85%
            mock_client.trigger_notification(
                BleManager.NOTIFY_CHARACTERISTIC,
                b"\x01\x00\x55\x00"
            )
        
        # 执行命令
        with patch("ble_command.BleCommands.create_get_battery_level_command", return_value=b"\x01\x02\x03\x04"):
            # 启动模拟通知任务
            asyncio.create_task(mock_notify())
            
            # 获取电池电量
            result = await ble_manager.execute_get_battery_level()
            
            # 验证写入被调用
            mock_write.assert_called_once()
            
            # 验证结果
            assert result['status'] == 'success'
            assert result['battery_level'] == 85
        
        # 断开连接
        await ble_manager.disconnect()

if __name__ == "__main__":
    pytest.main(["-v", __file__]) 