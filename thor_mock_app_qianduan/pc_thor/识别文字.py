import os
import re

from pywinauto import findwindows, Application
import win32gui
import win32con
import win32api
import time
import pyperclip  # 需要安装：pip install pyperclip

from loguru import logger

sati_dingding_robot_api = 'https://oapi.dingtalk.com/robot/send?access_token=071eca85ad3fe5b9fad2e9d8c3c956073272d0ad7522f37b3c4febc834716c13'
youtube_robot_api = 'https://oapi.dingtalk.com/robot/send?access_token=9d54960b332a1052ab66a7aed1f6c9c627b1b7f172eb48e85f32f01fb827e3b0'

import requests


def dingding(robot_url, content):
    r = requests.post(
        url=robot_url,
        headers={'Content-Type': 'application/json'},
        json={
            "text": {
                "content": content
            },
            "msgtype": "text"
        })

    logger.info(f"钉钉发送结果: {r.text}")


# 配置
WINDOW_TITLE = "X-DOWN"


def get_xdown_handle():
    """动态获取X-DOWN窗口句柄"""
    try:
        handle = findwindows.find_window(title=WINDOW_TITLE, backend="uia")
        return handle
    except:
        logger.info(f"未找到窗口: {WINDOW_TITLE}")
        return None


def ensure_window_foreground(handle):
    """确保窗口在前台"""
    try:
        win32gui.ShowWindow(handle, win32con.SW_RESTORE)
        win32gui.ShowWindow(handle, win32con.SW_SHOW)
        win32gui.BringWindowToTop(handle)
        win32gui.SetForegroundWindow(handle)
        return True
    except:
        return False


def find_text_in_window(handle, target_text):
    """在窗口中查找指定文字并返回中心坐标"""
    try:
        app = Application(backend="uia").connect(handle=handle)
        window = app.window(handle=handle)

        window_rect = win32gui.GetWindowRect(handle)
        window_left, window_top = window_rect[0], window_rect[1]

        text_elements = []

        def find_text_recursive(element):
            try:
                element_rect = element.rectangle()
                element_text = ""

                # 尝试多种方式获取文本
                try:
                    element_text = element.window_text()
                except:
                    pass

                if not element_text:
                    try:
                        element_text = element.texts()[0] if element.texts() else ""
                    except:
                        pass

                if not element_text:
                    try:
                        element_text = element.element_info.name
                    except:
                        pass

                # 如果找到目标文字
                if element_text and target_text in element_text.strip():
                    # 计算中心坐标
                    center_x = element_rect.left + element_rect.width() // 2 - window_left
                    center_y = element_rect.top + element_rect.height() // 2 - window_top

                    text_elements.append({
                        'text': element_text.strip(),
                        'center_x': center_x,
                        'center_y': center_y,
                        'width': element_rect.width(),
                        'height': element_rect.height()
                    })

                # 递归查找子元素
                try:
                    children = element.children()
                    for child in children:
                        find_text_recursive(child)
                except:
                    pass

            except:
                pass

        find_text_recursive(window)

        # 返回最匹配的元素（文字最相似的）
        if text_elements:
            # 优先返回完全匹配的
            exact_match = [e for e in text_elements if e['text'] == target_text]
            if exact_match:
                return exact_match[0]
            # 否则返回第一个包含目标文字的
            return text_elements[0]

        return None

    except Exception as e:
        logger.info(f"查找文字时出错: {e}")
        return None


def click_at_window_position(handle, x, y):
    """在窗口相对坐标位置点击"""
    try:
        window_rect = win32gui.GetWindowRect(handle)
        window_left, window_top = window_rect[0], window_rect[1]

        # 转换为屏幕绝对坐标
        abs_x = window_left + x
        abs_y = window_top + y

        # 执行点击
        win32api.SetCursorPos((abs_x, abs_y))
        win32api.mouse_event(win32con.MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0)
        time.sleep(0.1)
        win32api.mouse_event(win32con.MOUSEEVENTF_LEFTUP, 0, 0, 0, 0)
        return True
    except Exception as e:
        logger.info(f"点击时出错: {e}")
        return False


def contains_text(handle, target_text):
    """判断窗口是否包含指定文字片段"""
    try:
        app = Application(backend="uia").connect(handle=handle)
        window = app.window(handle=handle)

        found = False

        def find_text_recursive(element):
            nonlocal found
            if found:  # 如果已经找到，就不继续搜索了
                return

            try:
                element_text = ""

                # 尝试多种方式获取文本
                try:
                    element_text = element.window_text()
                except:
                    pass

                if not element_text:
                    try:
                        element_text = element.texts()[0] if element.texts() else ""
                    except:
                        pass

                if not element_text:
                    try:
                        element_text = element.element_info.name
                    except:
                        pass

                # 检查是否包含目标文字
                if element_text and target_text in element_text.strip():
                    found = True
                    return

                # 递归查找子元素
                if not found:
                    try:
                        children = element.children()
                        for child in children:
                            if found:
                                break
                            find_text_recursive(child)
                    except:
                        pass

            except:
                pass

        find_text_recursive(window)
        return found

    except Exception as e:
        logger.info(f"检查文字时出错: {e}")
        return False


def show_all_clickable_texts(handle):
    """显示窗口中所有可点击的文字及其中心坐标"""
    try:
        app = Application(backend="uia").connect(handle=handle)
        window = app.window(handle=handle)

        window_rect = win32gui.GetWindowRect(handle)
        window_left, window_top = window_rect[0], window_rect[1]

        logger.info(f"窗口位置: ({window_left}, {window_top})")
        logger.info("=" * 50)

        text_elements = []

        def find_text_recursive(element):
            try:
                element_rect = element.rectangle()
                element_text = ""

                try:
                    element_text = element.window_text()
                except:
                    pass

                if not element_text:
                    try:
                        element_text = element.texts()[0] if element.texts() else ""
                    except:
                        pass

                if not element_text:
                    try:
                        element_text = element.element_info.name
                    except:
                        pass

                if element_text and element_text.strip():
                    # 计算中心坐标
                    center_x = element_rect.left + element_rect.width() // 2 - window_left
                    center_y = element_rect.top + element_rect.height() // 2 - window_top

                    text_elements.append({
                        'text': element_text.strip(),
                        'center_x': center_x,
                        'center_y': center_y
                    })

                try:
                    children = element.children()
                    for child in children:
                        find_text_recursive(child)
                except:
                    pass

            except:
                pass

        find_text_recursive(window)

        # 去重并显示
        unique_elements = {}
        for element in text_elements:
            text = element['text']
            if text not in unique_elements:
                unique_elements[text] = element

        logger.info("所有可点击文字的中心坐标:")
        logger.info("-" * 40)
        for text, element in sorted(unique_elements.items(), key=lambda x: (x[1]['center_y'], x[1]['center_x'])):
            logger.info(f"{text} {element['center_x']},{element['center_y']}")

    except Exception as e:
        logger.info(f"显示文字时出错: {e}")


# ==================== 清空文本框函数 (已根据您的最新需求修改) ====================
def clear_textbox_content(handle, x, y, up_presses=10, down_presses=10):
    """
    清空指定位置（x, y）的文本框内容。
    假设文本框已获得焦点。通过模拟键盘 Shift + Up/Down + Delete 实现。
    此函数会执行两次删除操作：
    1. Shift + Up (up_presses 次) 然后 Delete
    2. Shift + Down (down_presses 次) 然后 Delete
    :param handle: 窗口句柄
    :param x: 文本框的相对X坐标，用于重新点击确保焦点
    :param y: 文本框的相对Y坐标，用于重新点击确保焦点
    :param up_presses: 向上按 Shift + Up 的次数
    :param down_presses: 向下按 Shift + Down 的次数
    """
    logger.info(f"尝试清空坐标 ({x}, {y}) 处的文本框内容...")
    try:
        # 确保文本框获得焦点
        click_at_window_position(handle, x, y)
        time.sleep(0.2)  # 给点击操作一点时间

        # --- 第一次删除：Shift + Up 然后 Delete ---
        logger.info(f"执行第一次删除：Shift + Up {up_presses} 次...")
        win32api.keybd_event(win32con.VK_SHIFT, 0, 0, 0)  # Press Shift
        time.sleep(0.05)

        for _ in range(up_presses):
            win32api.keybd_event(win32con.VK_UP, 0, 0, 0)
            time.sleep(0.05)
            win32api.keybd_event(win32con.VK_UP, 0, win32con.KEYEVENTF_KEYUP, 0)
            time.sleep(0.05)

        win32api.keybd_event(win32con.VK_SHIFT, 0, win32con.KEYEVENTF_KEYUP, 0)  # Release Shift
        time.sleep(0.1)

        win32api.keybd_event(win32con.VK_DELETE, 0, 0, 0)  # Press Delete
        time.sleep(0.1)
        win32api.keybd_event(win32con.VK_DELETE, 0, win32con.KEYEVENTF_KEYUP, 0)
        time.sleep(0.2)  # 给删除操作一点时间
        logger.info("第一次删除完成。")

        # --- 第二次删除：Shift + Down 然后 Delete ---
        # 再次点击以确保焦点和光标位置可能重置
        click_at_window_position(handle, x, y)
        time.sleep(0.2)

        logger.info(f"执行第二次删除：Shift + Down {down_presses} 次...")
        win32api.keybd_event(win32con.VK_SHIFT, 0, 0, 0)  # Press Shift
        time.sleep(0.05)

        for _ in range(down_presses):
            win32api.keybd_event(win32con.VK_DOWN, 0, 0, 0)
            time.sleep(0.05)
            win32api.keybd_event(win32con.VK_DOWN, 0, win32con.KEYEVENTF_KEYUP, 0)
            time.sleep(0.05)

        win32api.keybd_event(win32con.VK_SHIFT, 0, win32con.KEYEVENTF_KEYUP, 0)  # Release Shift
        time.sleep(0.1)

        win32api.keybd_event(win32con.VK_DELETE, 0, 0, 0)  # Press Delete
        time.sleep(0.1)
        win32api.keybd_event(win32con.VK_DELETE, 0, win32con.KEYEVENTF_KEYUP, 0)
        time.sleep(0.2)  # 给删除操作一点时间
        logger.info("第二次删除完成。")

        logger.info("文本框内容清空成功。")
        return True
    except Exception as e:
        logger.info(f"清空文本框时出错: {e}")
        return False


# ==================== 复制和粘贴函数 ====================
def get_url_and_copy_to_clipboard(url):
    """
    将指定的URL复制到系统剪贴板。
    :param url: 要复制的URL字符串
    """
    try:
        pyperclip.copy(url)
        logger.info(f"URL '{url}' 已复制到剪贴板。")
        return True
    except Exception as e:
        logger.info(f"复制URL到剪贴板时出错: {e}")
        return False


def paste_content():
    """
    模拟Ctrl+V操作，将剪贴板内容粘贴到当前焦点位置。
    """
    logger.info("正在执行粘贴操作 (Ctrl+V)...")
    try:
        # Press Ctrl
        win32api.keybd_event(win32con.VK_CONTROL, 0, 0, 0)
        time.sleep(0.05)
        # Press V
        win32api.keybd_event(ord('V'), 0, 0, 0)
        time.sleep(0.05)
        # Release V
        win32api.keybd_event(ord('V'), 0, win32con.KEYEVENTF_KEYUP, 0)
        time.sleep(0.05)
        # Release Ctrl
        win32api.keybd_event(win32con.VK_CONTROL, 0, win32con.KEYEVENTF_KEYUP, 0)
        time.sleep(0.2)  # 给粘贴操作一点时间
        logger.info("粘贴操作完成。")
        return True
    except Exception as e:
        logger.info(f"粘贴时出错: {e}")
        return False


# ==================== XDownWindowController 类 ====================

class XDownWindowController:
    """X-DOWN窗口控制器类"""

    def __init__(self, handle=None):
        """初始化控制器"""
        self.handle = handle
        self.window_title = "X-DOWN"

    def get_handle(self):
        """获取或更新窗口句柄"""
        if not self.handle:
            self.handle = get_xdown_handle()
        return self.handle

    def refresh_handle(self):
        """刷新窗口句柄（如果窗口重启了）"""
        self.handle = get_xdown_handle()
        return self.handle

    def is_valid(self):
        """检查句柄是否有效"""
        if not self.handle:
            return False
        try:
            win32gui.GetWindowText(self.handle)
            return True
        except:
            return False

    def ensure_foreground(self):
        """确保窗口在前台"""
        if not self.handle:
            return False
        return ensure_window_foreground(self.handle)

    def find_text(self, target_text):
        """判断窗口是否包含指定文字"""
        if not self.handle:
            return False
        return contains_text(self.handle, target_text)

    def click_at_position(self, x, y):
        """在指定相对坐标位置点击"""
        if not self.handle:
            return False
        return click_at_window_position(self.handle, x, y)

    def clear_textbox(self, x, y, up_presses=20, down_presses=10):
        """
        清空指定位置（x, y）的文本框内容。
        :param x: 文本框的相对X坐标
        :param y: 文本框的相对Y坐标
        :param up_presses: 向上按 Shift + Up 的次数
        :param down_presses: 向下按 Shift + Down 的次数
        """
        if not self.handle:
            return False
        return clear_textbox_content(self.handle, x, y, up_presses, down_presses)

    def paste_content(self):
        """
        执行粘贴操作。
        """
        return paste_content()

    def find_text_and_click(self, target_text):
        """
        点击窗口中包含指定文字的第一个元素。
        这是一个通用接口。
        """
        if not self.handle:
            logger.info("窗口句柄无效，无法点击文本。")
            return False
        if not self.ensure_foreground():
            logger.info("无法将窗口切换到前台，无法点击文本。")
            return False
        element = find_text_in_window(self.handle, target_text)
        if element:
            logger.info(f"找到包含文字 '{target_text}' 的元素在位置: ({element['center_x']}, {element['center_y']})")
            return self.click_at_position(element['center_x'], element['center_y'])
        else:
            logger.info(f"未找到包含文字: '{target_text}' 的元素。")
            return False

    def find_text_and_sleep_then_click(self, target_text):
        """
        点击窗口中包含指定文字的第一个元素。
        这是一个通用接口。
        """
        if not self.handle:
            logger.info("窗口句柄无效，无法点击文本。")
            return False
        if not self.ensure_foreground():
            logger.info("无法将窗口切换到前台，无法点击文本。")
            return False

        time.sleep(1)
        element = find_text_in_window(self.handle, target_text)
        if element:
            logger.info(f"找到包含文字 '{target_text}' 的元素在位置: ({element['center_x']}, {element['center_y']})")
            self.click_at_position(element['center_x'], element['center_y'])

            return True
        else:
            logger.info(f"未找到包含文字: '{target_text}' 的元素。")
            return False

    def close_top_download_window(self):
        for index in range(10):
            if self.find_text('请输入地址') or self.find_text('地址错误') or self.find_text_and_click(
                    '已在下载队列中') or self.find_text('请检查'):
                self.find_text_and_sleep_then_click('取消')
            else:
                logger.info('下载窗口已经关闭 开始正常流程')
                break

    def delete_all_task(self):
        self.close_top_download_window()
        while True:
            if not self.find_text_and_sleep_then_click('停止'):
                break
        while True:
            if not self.find_text_and_sleep_then_click('移除'):
                break
def delete_all_file():
    for subdir in os.listdir('download'):
        import shutil
        remove_path = os.path.join('download', subdir)
        logger.info('delete {}'.format(remove_path))
        shutil.rmtree(remove_path)

def upload_3d_model(target_url):
    upload_key_map_localpath = []

    local_path_list = []
    matchs = re.findall('[a-f0-9]{32}', target_url)

    if matchs:
        model_id = matchs[0]
        for download_subdir in os.listdir('download'):
            model_dir = os.path.join('download', download_subdir)

            if os.path.isdir(model_dir):  # 确保这是一个有效的目录
                for root, _, files in os.walk(model_dir):
                    for file_name in files:
                        file_path = os.path.join(root, file_name)
                        local_path_list.append(file_path)  # 将文件的完整路径添加到列表中

            for local_path in local_path_list:
                new_model_dir = local_path.replace(model_dir, '').replace('\\', '/').lstrip('/')
                upload_key = 'sketchfab_3d/{}/{}'.format(
                    model_id,
                    new_model_dir,
                )
                upload_key_map_localpath.append({
                    'upload_key': upload_key,
                    'local_path': local_path,
                })

            for one in upload_key_map_localpath:
                logger.info(one)
                if os.path.exists(one['local_path']):
                    with open(one['local_path'], 'rb') as f:
                        import oss_utils
                        oss_utils.put_object_bytes(one['upload_key'], f.read())

            import shutil
            logger.info('delete_dir {}'.format(model_dir))




def put_url(target_url):
    delete_all_file()
    # 方式1: 让类自动获取句柄
    controller = XDownWindowController()
    if not controller.get_handle():
        logger.info("无法获取窗口句柄")
        exit(1)
    # 刚开始下载 我肯定需要干净的文件夹
    controller.delete_all_task()

    # 在这里获取窗口尺寸并计算新的坐标
    window_rect = win32gui.GetWindowRect(controller.handle)
    window_width = window_rect[2] - window_rect[0]
    window_height = window_rect[3] - window_rect[1]

    # 根据比例重新计算 x 和 y
    textbox_or_confirm_x = int(window_width * 0.50)  # 宽度50%
    textbox_or_confirm_y = int(window_height * 0.40)  # 高度40%

    # 步骤1: 将URL复制到剪贴板
    if not get_url_and_copy_to_clipboard(target_url):
        logger.info("无法将URL复制到剪贴板，退出。")
        exit(1)

    logger.info("=== 使用类方式操作窗口 ===")

    # 检查句柄是否有效
    if controller.is_valid():
        logger.info("✅ 窗口句柄有效")
    else:
        logger.info("❌ 窗口句柄无效，尝试刷新...")
        controller.refresh_handle()
        if not controller.is_valid():
            logger.info("❌ 刷新句柄后仍然无效，请确保X-DOWN窗口已打开。")
            exit(1)

    if not controller.find_text_and_sleep_then_click("新建下载"):
        logger.info('新建下载没有弹窗??')
        return

    if controller.find_text('请输入地址') or controller.find_text('请检查'):
        if controller.click_at_position(textbox_or_confirm_x, textbox_or_confirm_y):
            logger.info(f"成功点击指定位置 ({textbox_or_confirm_x}, {textbox_or_confirm_y})。")
            time.sleep(0.5)  # 等待焦点设置
            controller.clear_textbox(textbox_or_confirm_x, textbox_or_confirm_y)

    if controller.find_text('请输入地址') or controller.find_text('请检查'):
        if controller.click_at_position(textbox_or_confirm_x, textbox_or_confirm_y):
            time.sleep(1)
            if controller.paste_content():
                logger.info("内容已成功粘贴。")
                time.sleep(1)  # 等待1秒，让内容完全显示

    controller.find_text_and_sleep_then_click('确定')

    if controller.find_text('已在下载队列中') or controller.find_text('地址错误'):
        controller.find_text_and_click('取消')
        logger.info('已在下载队列中....中止下载流程')
        return
    controller.find_text_and_sleep_then_click('开始下载')

    done = False
    for sec in range(200):
        logger.info('wait {}'.format(sec))
        time.sleep(1)
        if not controller.find_text('下载中') and not controller.find_text('解析中'):
            controller.find_text_and_sleep_then_click('开始下载')
            logger.info('居然没有显示下载中 点击开始下载')
        if not controller.find_text('停止') and not controller.find_text('移除'):
            logger.info('下载全部结束了？？？')
            upload_3d_model(target_url)
            done = True
            break
    if not done:
        logger.info('通知-下载失败 {}'.format(target_url.strip()))
        dingding(sati_dingding_robot_api, '通知-下载失败 {}'.format(target_url.strip()))


def start_download_task():
    import redis
    queue_name = 'your_zset_key'
    r = redis.Redis(host='43.135.147.67', port=8379, password='zhaozhenjie', decode_responses=True, db=0)
    fail = 0
    while True:
        try:
            s = r.zrange(queue_name, 0, 0)
            if s:
                r.zrem(queue_name, s[0])
                vals = s[0].split(',')
                target_url = vals[1]
                logger.info('开始处理 {}'.format(target_url))
                put_url(target_url)
            time.sleep(1)
        except:
            import traceback
            traceback.print_exc()
            fail += 1
            # if fail > 10:
            #     import socket
            #     dingding(sati_dingding_robot_api, '通知 {} 累计错误次数太多 退出'.format(socket.gethostname()))
            #     return


if __name__ == "__main__":
    start_download_task()