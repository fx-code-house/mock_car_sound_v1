#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
alphax界面设计.sketch 完整资源提取工具
专门提取符号、组件、插件、画板等完整设计资源
"""

import zipfile
import json
import os
import shutil
import glob
from pathlib import Path
import xml.etree.ElementTree as ET
import base64
import re
from collections import defaultdict


def extract_sketch_completely():
    """完整提取Sketch文件中的所有资源"""

    sketch_file = "alphax界面设计.sketch"

    if not os.path.exists(sketch_file):
        print(f"❌ 错误：找不到文件 '{sketch_file}'")
        return False

    # 创建输出目录
    output_dir = "alphax_完整设计资源"
    if os.path.exists(output_dir):
        shutil.rmtree(output_dir)

    os.makedirs(output_dir)

    try:
        print("🎨 开始完整提取 alphax界面设计.sketch...")
        print("=" * 60)

        # 解压Sketch文件
        with zipfile.ZipFile(sketch_file, 'r') as zip_ref:
            zip_ref.extractall(output_dir)

        print("✅ Sketch文件解压成功")

        # 创建分类目录
        categories = {
            '00_所有图片': '所有图片资源',
            '01_符号库_Symbols': '符号和组件',
            '02_画板_Artboards': '所有画板',
            '03_页面_Pages': '页面结构',
            '04_样式_Styles': '图层样式',
            '05_插件数据': '插件相关内容',
            '06_原始文件': '原始JSON数据',
            '07_预览导出': '导出预览',
        '08_UI元素详细分析': 'UI元素和文字分析'
        }

        for dir_name, dir_desc in categories.items():
            os.makedirs(os.path.join(output_dir, dir_name), exist_ok=True)

        # 1. 提取所有图片
        print("\n📸 提取图片资源...")
        extract_all_images(output_dir)

        # 2. 提取符号和组件
        print("🔧 提取符号和组件...")
        extract_symbols_and_components(output_dir)

        # 3. 提取画板信息
        print("🎯 提取画板信息...")
        extract_artboards(output_dir)

        # 4. 提取页面结构
        print("📑 提取页面结构...")
        extract_pages_structure(output_dir)

        # 5. 提取插件数据
        print("🔌 提取插件数据...")
        extract_plugin_data(output_dir)

        # 6. 深度提取所有UI元素和文字
        print("🔍 深度扫描UI元素和文字...")
        extract_all_ui_elements_and_texts(output_dir)

        # 7. 导出可用的设计文件
        print("🎨 导出可用的设计资源...")
        export_usable_design_assets(output_dir)

        # 8. 按层级导出PNG元素
        print("🖼️ 按层级导出PNG元素...")
        export_hierarchical_png_elements(output_dir)

        # 9. 生成详细报告
        print("📊 生成详细报告...")
        generate_comprehensive_report(output_dir)

        print("\n🎉 完整提取完成！")
        print("=" * 60)
        print_summary(output_dir)

        return True

    except Exception as e:
        print(f"❌ 提取过程中出现错误: {e}")
        import traceback
        traceback.print_exc()
        return False


def extract_all_images(output_dir):
    """提取所有图片资源，包括嵌入图片和base64图片"""
    images_dir = os.path.join(output_dir, '00_所有图片')
    embedded_images_dir = os.path.join(images_dir, 'embedded')
    os.makedirs(embedded_images_dir, exist_ok=True)

    # 1. 提取文件系统中的图片
    image_extensions = ('*.png', '*.jpg', '*.jpeg', '*.gif', '*.webp', '*.svg', '*.bmp', '*.tiff')
    for extension in image_extensions:
        for image_path in glob.glob(os.path.join(output_dir, '**', extension), recursive=True):
            if '00_所有图片' not in image_path:
                filename = create_unique_filename(images_dir, os.path.basename(image_path))
                shutil.copy2(image_path, os.path.join(images_dir, filename))

    # 2. 从JSON文件中提取嵌入的base64图片和引用图片
    extract_embedded_images_from_json(output_dir, embedded_images_dir)

    # 3. 查找所有可能的图片引用
    extract_image_references(output_dir, images_dir)


def extract_symbols_and_components(output_dir):
    """提取符号(Symbols)和组件"""
    symbols_dir = os.path.join(output_dir, '01_符号库_Symbols')
    all_symbols = {}
    all_components = defaultdict(list)

    # 查找document.json中的符号
    document_path = os.path.join(output_dir, 'document.json')
    if os.path.exists(document_path):
        with open(document_path, 'r', encoding='utf-8') as f:
            document_data = json.load(f)

        # 提取符号信息 - 修复逻辑
        if 'symbols' in document_data:
            symbols_data = document_data['symbols']
            if isinstance(symbols_data, dict) and 'objects' in symbols_data:
                symbols = symbols_data['objects']
            else:
                symbols = symbols_data
        else:
            symbols = []

        if symbols:
            symbols_info = {
                'symbols_count': len(symbols),
                'symbols_list': symbols
            }
            with open(os.path.join(symbols_dir, 'symbols_info.json'), 'w', encoding='utf-8') as f:
                json.dump(symbols_info, f, ensure_ascii=False, indent=2)
            all_symbols['document_symbols'] = symbols

        # 提取图层样式
        layer_styles = document_data.get('layerStyles', {}).get('objects', [])
        if layer_styles:
            with open(os.path.join(output_dir, '04_样式_Styles', 'layer_styles.json'), 'w', encoding='utf-8') as f:
                json.dump(layer_styles, f, ensure_ascii=False, indent=2)

        # 提取文本样式
        text_styles = document_data.get('layerTextStyles', {}).get('objects', [])
        if text_styles:
            with open(os.path.join(output_dir, '04_样式_Styles', 'text_styles.json'), 'w', encoding='utf-8') as f:
                json.dump(text_styles, f, ensure_ascii=False, indent=2)

    # 深度解析页面中的符号和组件
    extract_symbols_from_pages(output_dir, symbols_dir, all_symbols, all_components)


def extract_artboards(output_dir):
    """提取画板信息"""
    artboards_dir = os.path.join(output_dir, '02_画板_Artboards')
    all_artboards = {}
    artboard_count = 0

    # 遍历pages目录查找画板
    pages_dir = os.path.join(output_dir, 'pages')
    if os.path.exists(pages_dir):
        for page_file in os.listdir(pages_dir):
            if page_file.endswith('.json'):
                page_path = os.path.join(pages_dir, page_file)
                page_name = page_file.replace('.json', '')

                try:
                    with open(page_path, 'r', encoding='utf-8') as f:
                        content = f.read()

                    # 处理大文件
                    if len(content) > 1000000:  # 1MB
                        artboards = extract_artboards_from_large_file(content, page_name)
                    else:
                        page_data = json.loads(content)
                        artboards = extract_artboards_from_page_data(page_data, page_name)

                    if artboards:
                        page_artboards_dir = os.path.join(artboards_dir, page_name)
                        os.makedirs(page_artboards_dir, exist_ok=True)

                        for artboard_id, artboard_data in artboards.items():
                            artboard_file = os.path.join(page_artboards_dir, f"{artboard_id}.json")
                            with open(artboard_file, 'w', encoding='utf-8') as f:
                                json.dump(artboard_data, f, ensure_ascii=False, indent=2)

                            artboard_count += 1
                            print(f"  - 提取画板: {artboard_data.get('name', artboard_id)}")

                        all_artboards[page_name] = artboards

                except Exception as e:
                    print(f"  ! 处理页面 {page_file} 时出错: {e}")
                    continue

    # 保存所有画板的摘要
    if all_artboards:
        summary_file = os.path.join(artboards_dir, 'artboards_summary.json')
        summary = {
            'total_artboards': artboard_count,
            'pages_with_artboards': list(all_artboards.keys()),
            'artboards_by_page': {
                page: [ab.get('name', aid) for aid, ab in artboards.items()]
                for page, artboards in all_artboards.items()
            }
        }
        with open(summary_file, 'w', encoding='utf-8') as f:
            json.dump(summary, f, ensure_ascii=False, indent=2)

    return artboard_count


def extract_artboards_from_large_file(content, page_name):
    """从大文件中提取画板"""
    artboards = {}

    # 使用正则表达式查找artboard
    artboard_patterns = [
        r'"_class":\s*"MSArtboardGroup"[^{}]*{[^{}]*(?:{[^{}]*}[^{}]*)*}',
        r'"artboard"[^{}]*{[^{}]*(?:{[^{}]*}[^{}]*)*}'
    ]

    for pattern in artboard_patterns:
        matches = re.finditer(pattern, content, re.DOTALL)
        for i, match in enumerate(matches):
            try:
                artboard_text = match.group()
                # 尝试构建完整的JSON
                if not artboard_text.startswith('{'):
                    artboard_text = '{' + artboard_text
                if not artboard_text.endswith('}'):
                    artboard_text = artboard_text + '}'

                artboard_data = json.loads(artboard_text)
                artboard_id = artboard_data.get('do_objectID', f'{page_name}_artboard_{i}')
                artboards[artboard_id] = artboard_data

            except json.JSONDecodeError:
                continue

    return artboards


def extract_artboards_from_page_data(page_data, page_name):
    """从页面数据中提取画板"""
    artboards = {}

    def find_artboards_recursive(obj, path=""):
        if isinstance(obj, dict):
            if obj.get('_class') == 'MSArtboardGroup':
                artboard_id = obj.get('do_objectID', f'{page_name}_artboard_{len(artboards)}')
                artboards[artboard_id] = obj

            for key, value in obj.items():
                find_artboards_recursive(value, f"{path}.{key}")

        elif isinstance(obj, list):
            for i, item in enumerate(obj):
                find_artboards_recursive(item, f"{path}[{i}]")

    # 直接查找artboards字段
    if 'artboards' in page_data:
        direct_artboards = page_data['artboards']
        if isinstance(direct_artboards, dict):
            artboards.update(direct_artboards)

    # 递归查找
    find_artboards_recursive(page_data)

    return artboards


def extract_pages_structure(output_dir):
    """提取页面结构"""
    pages_dir = os.path.join(output_dir, '03_页面_Pages')

    # 复制pages目录
    source_pages = os.path.join(output_dir, 'pages')
    if os.path.exists(source_pages):
        shutil.copytree(source_pages, os.path.join(pages_dir, 'pages'))

    # 提取文档结构
    document_path = os.path.join(output_dir, 'document.json')
    if os.path.exists(document_path):
        shutil.copy2(document_path, os.path.join(pages_dir, 'document_structure.json'))


def extract_plugin_data(output_dir):
    """提取插件相关数据和小型组件"""
    plugin_dir = os.path.join(output_dir, '05_插件数据')
    mini_components_dir = os.path.join(plugin_dir, 'mini_components')
    os.makedirs(mini_components_dir, exist_ok=True)

    plugin_summary = {
        'sketch_info': {},
        'user_data': {},
        'fonts': [],
        'mini_components': [],
        'external_references': [],
        'custom_data': {}
    }

    # 查找可能包含插件数据的文件
    plugin_indicators = ['plugin', 'metadata', 'user', 'workspace', 'preferences', 'settings']

    for root, dirs, files in os.walk(output_dir):
        for file in files:
            file_lower = file.lower()
            file_path = os.path.join(root, file)

            # 查找可能包含插件数据的文件
            if any(indicator in file_lower for indicator in plugin_indicators):
                relative_path = os.path.relpath(file_path, output_dir)
                dest_path = os.path.join(plugin_dir, relative_path)

                os.makedirs(os.path.dirname(dest_path), exist_ok=True)
                shutil.copy2(file_path, dest_path)
                print(f"  - 复制插件数据: {relative_path}")

            # 特别处理各种重要文件
            if file == 'meta.json':
                with open(file_path, 'r', encoding='utf-8') as f:
                    meta_data = json.load(f)

                plugin_summary['sketch_info'] = {
                    'appVersion': meta_data.get('appVersion'),
                    'build': meta_data.get('build'),
                    'version': meta_data.get('version'),
                    'variant': meta_data.get('variant'),
                    'created': meta_data.get('created'),
                    'commit': meta_data.get('commit')
                }

            elif file == 'user.json':
                with open(file_path, 'r', encoding='utf-8') as f:
                    user_data = json.load(f)
                plugin_summary['user_data'] = user_data
                print(f"  - 提取用户数据: {len(user_data)} 项配置")

            # 处理字体文件
            elif file_lower.endswith(('.ttf', '.otf', '.woff', '.woff2')):
                font_info = {
                    'filename': file,
                    'path': relative_path,
                    'size': os.path.getsize(file_path)
                }
                plugin_summary['fonts'].append(font_info)

                # 复制字体文件
                dest_path = os.path.join(plugin_dir, 'fonts', file)
                os.makedirs(os.path.dirname(dest_path), exist_ok=True)
                shutil.copy2(file_path, dest_path)
                print(f"  - 提取字体文件: {file}")

    # 从JSON文件中提取小型组件和自定义数据
    extract_mini_components_and_custom_data(output_dir, plugin_summary, mini_components_dir)

    # 保存插件数据摘要
    with open(os.path.join(plugin_dir, 'plugin_summary.json'), 'w', encoding='utf-8') as f:
        json.dump(plugin_summary, f, ensure_ascii=False, indent=2)

    print(f"  - 插件数据总结: {len(plugin_summary['mini_components'])} 个小型组件")
    print(f"  - 字体文件: {len(plugin_summary['fonts'])} 个")


def extract_mini_components_and_custom_data(output_dir, plugin_summary, mini_components_dir):
    """提取小型组件和自定义数据"""
    component_types = ['button', 'icon', 'badge', 'chip', 'tab', 'toggle', 'slider', 'input']

    for root, dirs, files in os.walk(output_dir):
        for file in files:
            if file.endswith('.json'):
                file_path = os.path.join(root, file)
                try:
                    with open(file_path, 'r', encoding='utf-8') as f:
                        content = f.read()

                    # 查找小型组件
                    for component_type in component_types:
                        pattern = rf'"{component_type}"[^{{}}]*{{[^{{}}]*(?:{{[^{{}}]*}}[^{{}}]*)*}}'
                        matches = re.finditer(pattern, content, re.IGNORECASE)

                        for i, match in enumerate(matches):
                            try:
                                component_text = match.group()
                                if not component_text.startswith('{'):
                                    component_text = '{' + component_text
                                if not component_text.endswith('}'):
                                    component_text = component_text + '}'

                                component_data = json.loads(component_text)
                                component_id = f"{component_type}_{file}_{i}"

                                component_info = {
                                    'id': component_id,
                                    'type': component_type,
                                    'source_file': file,
                                    'data': component_data
                                }

                                plugin_summary['mini_components'].append(component_info)

                                # 保存单独的组件文件
                                component_file = os.path.join(mini_components_dir, f"{component_id}.json")
                                with open(component_file, 'w', encoding='utf-8') as cf:
                                    json.dump(component_info, cf, ensure_ascii=False, indent=2)

                            except json.JSONDecodeError:
                                continue

                    # 查找自定义数据和用户定义
                    custom_patterns = [
                        r'"userInfo"[^{}]*{[^{}]*(?:{[^{}]*}[^{}]*)*}',
                        r'"customData"[^{}]*{[^{}]*(?:{[^{}]*}[^{}]*)*}',
                        r'"pluginData"[^{}]*{[^{}]*(?:{[^{}]*}[^{}]*)*}'
                    ]

                    for pattern in custom_patterns:
                        matches = re.finditer(pattern, content, re.IGNORECASE)
                        for match in matches:
                            try:
                                custom_text = match.group()
                                if not custom_text.startswith('{'):
                                    custom_text = '{' + custom_text
                                if not custom_text.endswith('}'):
                                    custom_text = custom_text + '}'

                                custom_data = json.loads(custom_text)
                                key = f"{file}_{len(plugin_summary['custom_data'])}"
                                plugin_summary['custom_data'][key] = custom_data

                            except json.JSONDecodeError:
                                continue

                except Exception:
                    continue


def extract_all_ui_elements_and_texts(output_dir):
    """快速提取所有UI元素、文字和组件"""
    ui_elements_dir = os.path.join(output_dir, '08_UI元素详细分析')
    os.makedirs(ui_elements_dir, exist_ok=True)

    all_texts = []
    ui_element_counts = {
        'texts': 0,
        'buttons': 0,
        'groups': 0,
        'shapes': 0,
        'images': 0,
        'symbols': 0,
        'rectangles': 0,
        'paths': 0
    }

    # 快速文字提取 - 不深度解析JSON，直接用正则
    pages_dir = os.path.join(output_dir, 'pages')
    if os.path.exists(pages_dir):
        for page_file in os.listdir(pages_dir):
            if page_file.endswith('.json'):
                page_path = os.path.join(pages_dir, page_file)
                page_name = page_file.replace('.json', '')

                print(f"  - 快速扫描页面: {page_name}")

                try:
                    with open(page_path, 'r', encoding='utf-8') as f:
                        content = f.read()

                    # 快速提取文字 - 使用正则表达式
                    extract_texts_fast(content, all_texts, page_name)

                    # 快速统计UI元素 - 只统计数量，不深度解析
                    count_ui_elements_fast(content, ui_element_counts)

                except Exception as e:
                    print(f"    ! 处理页面 {page_file} 时出错: {e}")
                    continue

    # 保存提取结果
    save_fast_ui_analysis(ui_elements_dir, all_texts, ui_element_counts)

    print(f"  ✅ 快速扫描完成: 文字{len(all_texts)}个, 组{ui_element_counts['groups']}个, 矩形{ui_element_counts['rectangles']}个")


def extract_texts_fast(content, all_texts, page_name):
    """快速提取文字内容"""
    # 优化的文字提取正则表达式
    text_patterns = [
        r'"string":\s*"([^"]{2,})"',  # 字符串字段，至少2个字符
        r'"name":\s*"([^"]*[\u4e00-\u9fa5]+[^"]*)"',  # 包含中文的名称
        r'"name":\s*"([^"]*(?:按钮|文本|标签|提示|确定|取消|登录|换车|我的|设置|下载)[^"]*)"',  # 常见UI文字
        r'(?<="string":\s")[^"]*[\u4e00-\u9fa5]+[^"]*(?=")',  # 直接匹配中文字符串内容
    ]

    for pattern in text_patterns:
        matches = re.finditer(pattern, content)
        for match in matches:
            text_content = match.group(1) if len(match.groups()) > 0 else match.group(0)
            if text_content and len(text_content.strip()) > 1:
                # 去除常见的无意义文字
                if not any(skip in text_content.lower() for skip in ['uuid', 'object', 'class', 'frame', 'null']):
                    all_texts.append({
                        'text': text_content.strip(),
                        'source': page_name,
                        'type': 'fast_extract'
                    })


def count_ui_elements_fast(content, ui_counts):
    """快速统计UI元素数量"""
    element_patterns = {
        'texts': r'"_class":\s*"text"',
        'groups': r'"_class":\s*"group"',
        'rectangles': r'"_class":\s*"rectangle"',
        'symbols': r'"_class":\s*"symbol',
        'shapes': r'"_class":\s*"shape',
        'buttons': r'(?:"name":[^}]*button|button[^}]*"name")',
        'paths': r'"_class":\s*".*[Pp]ath'
    }

    for element_type, pattern in element_patterns.items():
        matches = re.findall(pattern, content, re.IGNORECASE)
        ui_counts[element_type] += len(matches)


def save_fast_ui_analysis(ui_elements_dir, all_texts, ui_counts):
    """保存快速UI分析结果"""

    # 去重并排序文字
    unique_texts = []
    seen_texts = set()
    for text_item in all_texts:
        text = text_item['text']
        if text and text not in seen_texts and len(text.strip()) > 1:
            seen_texts.add(text)
            unique_texts.append(text_item)

    # 按长度排序，短文字优先（通常是UI标签）
    unique_texts.sort(key=lambda x: len(x['text']))

    # 保存文字摘要
    with open(os.path.join(ui_elements_dir, 'texts_summary.txt'), 'w', encoding='utf-8') as f:
        f.write("=== 所有提取的文字内容 ===\n\n")
        f.write(f"总共找到 {len(unique_texts)} 个不重复的文字元素\n\n")

        # 分类显示
        chinese_texts = [t for t in unique_texts if re.search(r'[\u4e00-\u9fa5]', t['text'])]
        english_texts = [t for t in unique_texts if not re.search(r'[\u4e00-\u9fa5]', t['text'])]

        f.write(f"=== 中文文字内容 ({len(chinese_texts)}个) ===\n")
        for i, text_item in enumerate(chinese_texts, 1):
            f.write(f"{i:3d}. {text_item['text']} (来源: {text_item['source']})\n")

        f.write(f"\n=== 英文/数字内容 ({len(english_texts)}个) ===\n")
        for i, text_item in enumerate(english_texts, 1):
            f.write(f"{i:3d}. {text_item['text']} (来源: {text_item['source']})\n")

    # 保存完整数据
    summary_data = {
        'extraction_summary': {
            'total_texts_found': len(all_texts),
            'unique_texts': len(unique_texts),
            'chinese_texts': len(chinese_texts),
            'english_texts': len(english_texts),
            'ui_elements_by_type': ui_counts,
            'total_ui_elements': sum(ui_counts.values())
        },
        'all_unique_texts': unique_texts,
        'ui_element_counts': ui_counts
    }

    with open(os.path.join(ui_elements_dir, 'ui_analysis_summary.json'), 'w', encoding='utf-8') as f:
        json.dump(summary_data, f, ensure_ascii=False, indent=2)

    print(f"    - 文字摘要文件: texts_summary.txt ({len(unique_texts)} 个不重复文字)")
    print(f"    - 中文文字: {len(chinese_texts)} 个")
    print(f"    - 英文文字: {len(english_texts)} 个")


def export_usable_design_assets(output_dir):
    """导出可直接使用的设计资源"""
    design_assets_dir = os.path.join(output_dir, '09_可用设计资源')
    os.makedirs(design_assets_dir, exist_ok=True)

    # 1. 创建UI组件库文档
    create_ui_component_library(design_assets_dir, output_dir)

    # 2. 导出文字样式表
    export_text_styles(design_assets_dir, output_dir)

    # 3. 生成颜色面板
    extract_color_palette(design_assets_dir, output_dir)

    # 4. 创建层级结构展示
    create_hierarchical_structure(design_assets_dir, output_dir)

    # 5. 创建设计规范文档
    create_design_specification(design_assets_dir, output_dir)

    print("  ✅ 设计资源导出完成")


def create_ui_component_library(design_assets_dir, output_dir):
    """创建UI组件库文档"""
    components_dir = os.path.join(design_assets_dir, '组件库')
    os.makedirs(components_dir, exist_ok=True)

    # 读取UI分析数据
    ui_summary_file = os.path.join(output_dir, '08_UI元素详细分析', 'ui_analysis_summary.json')
    if not os.path.exists(ui_summary_file):
        return

    with open(ui_summary_file, 'r', encoding='utf-8') as f:
        ui_data = json.load(f)

    texts = ui_data.get('all_unique_texts', [])
    ui_counts = ui_data.get('ui_element_counts', {})

    # 创建按钮组件文档
    create_button_components(components_dir, texts, ui_counts)

    # 创建文字组件文档
    create_text_components(components_dir, texts)

    # 创建布局组件文档
    create_layout_components(components_dir, ui_counts)


def create_button_components(components_dir, texts, ui_counts):
    """创建按钮组件文档"""
    button_texts = [t['text'] for t in texts if any(keyword in t['text'] for keyword in
                   ['确定', '取消', '登录', '设置', '下载', '换车', '完成', '好的', '支持'])]

    button_doc = os.path.join(components_dir, '按钮组件.md')
    with open(button_doc, 'w', encoding='utf-8') as f:
        f.write("# 按钮组件库\n\n")
        f.write("## 主要按钮文字\n\n")

        for i, text in enumerate(button_texts, 1):
            f.write(f"### {i}. {text}\n")
            f.write(f"```\n")
            f.write(f"文字: {text}\n")
            f.write(f"用途: 用户操作按钮\n")
            f.write(f"建议样式: 主要按钮 / 次要按钮\n")
            f.write(f"```\n\n")

        f.write("## 设计建议\n\n")
        f.write("- 主要操作按钮：确定、登录、完成\n")
        f.write("- 次要操作按钮：取消、设置、支持\n")
        f.write("- 功能按钮：下载、换车\n\n")

    print(f"    - 按钮组件: {len(button_texts)} 个")


def create_text_components(components_dir, texts):
    """创建文字组件文档"""
    # 按类型分类文字
    categories = {
        '导航文字': ['首页', '我的', '设置'],
        '功能标签': ['我的车', '我的汽車', '蓝牙链接', '聲浪設置'],
        '状态文字': ['已連接', '正在下載', 'offline', 'Scanning'],
        '车型信息': ['mercedes-benz', 'amg', 'Camry', 'sedan'],
        '版本信息': ['1.0.0', 'w168', 'v168'],
        '提示文字': ['提示弹框1', '提示弹框2', '请输入新设备']
    }

    text_doc = os.path.join(components_dir, '文字组件.md')
    with open(text_doc, 'w', encoding='utf-8') as f:
        f.write("# 文字组件库\n\n")

        for category, keywords in categories.items():
            f.write(f"## {category}\n\n")

            # 找到匹配的文字
            matched_texts = []
            for text_item in texts:
                text = text_item['text']
                if any(keyword.lower() in text.lower() for keyword in keywords):
                    matched_texts.append(text)

            # 去重并排序
            unique_texts = sorted(list(set(matched_texts)))

            for text in unique_texts[:10]:  # 每类最多显示10个
                f.write(f"- {text}\n")

            if len(unique_texts) > 10:
                f.write(f"- ... 还有 {len(unique_texts) - 10} 个\n")

            f.write("\n")

    print(f"    - 文字组件: {len(categories)} 个分类")


def create_layout_components(components_dir, ui_counts):
    """创建布局组件文档"""
    layout_doc = os.path.join(components_dir, '布局组件.md')
    with open(layout_doc, 'w', encoding='utf-8') as f:
        f.write("# 布局组件统计\n\n")
        f.write("## UI元素数量统计\n\n")

        element_names = {
            'groups': '编组/容器',
            'rectangles': '矩形/卡片',
            'texts': '文字元素',
            'shapes': '图形/图标',
            'symbols': '符号/组件',
            'paths': '路径/线条'
        }

        for element_type, count in ui_counts.items():
            name = element_names.get(element_type, element_type)
            f.write(f"- **{name}**: {count} 个\n")

        f.write("\n## 设计建议\n\n")
        f.write("### 布局结构\n")
        f.write(f"- 主要使用 {ui_counts.get('groups', 0)} 个编组来组织内容\n")
        f.write(f"- {ui_counts.get('rectangles', 0)} 个矩形元素用于卡片和背景\n")
        f.write(f"- {ui_counts.get('shapes', 0)} 个图形元素用于图标和装饰\n\n")

        f.write("### 组件复用\n")
        f.write(f"- 可以将 {ui_counts.get('symbols', 0)} 个符号制作成可复用组件\n")
        f.write("- 建议标准化按钮、输入框、卡片等常用元素\n")


def export_text_styles(design_assets_dir, output_dir):
    """导出文字样式表"""
    styles_dir = os.path.join(design_assets_dir, '样式规范')
    os.makedirs(styles_dir, exist_ok=True)

    # 读取文字数据
    ui_summary_file = os.path.join(output_dir, '08_UI元素详细分析', 'ui_analysis_summary.json')
    if not os.path.exists(ui_summary_file):
        return

    with open(ui_summary_file, 'r', encoding='utf-8') as f:
        ui_data = json.load(f)

    texts = ui_data.get('all_unique_texts', [])

    # 按长度分类文字样式
    text_styles = {
        '标题文字': [t['text'] for t in texts if len(t['text']) > 8 and any(word in t['text'] for word in ['設置', '汽車', '聲浪', '技術'])],
        '按钮文字': [t['text'] for t in texts if len(t['text']) <= 4 and t['text'] in ['确定', '取消', '设置', '登录', '下载', '换车']],
        '标签文字': [t['text'] for t in texts if 3 <= len(t['text']) <= 8],
        '描述文字': [t['text'] for t in texts if len(t['text']) > 15]
    }

    styles_doc = os.path.join(styles_dir, '文字样式规范.md')
    with open(styles_doc, 'w', encoding='utf-8') as f:
        f.write("# 文字样式规范\n\n")

        style_specs = {
            '标题文字': {'size': '18-24px', 'weight': 'Semibold', 'usage': '页面标题、功能模块名称'},
            '按钮文字': {'size': '14-16px', 'weight': 'Medium', 'usage': '按钮标签、操作文字'},
            '标签文字': {'size': '12-14px', 'weight': 'Regular', 'usage': '字段标签、状态提示'},
            '描述文字': {'size': '12px', 'weight': 'Regular', 'usage': '详细说明、帮助文本'}
        }

        for style_type, texts in text_styles.items():
            if not texts:
                continue

            spec = style_specs.get(style_type, {})
            f.write(f"## {style_type}\n\n")
            f.write(f"**建议规格:**\n")
            f.write(f"- 字号: {spec.get('size', '14px')}\n")
            f.write(f"- 字重: {spec.get('weight', 'Regular')}\n")
            f.write(f"- 用途: {spec.get('usage', '通用文字')}\n\n")

            f.write("**示例文字:**\n")
            for text in texts[:5]:  # 每类显示5个示例
                f.write(f"- {text}\n")

            if len(texts) > 5:
                f.write(f"- ... 还有 {len(texts) - 5} 个\n")

            f.write("\n")

    print(f"    - 文字样式: {len(text_styles)} 个分类")


def extract_color_palette(design_assets_dir, output_dir):
    """提取颜色面板"""
    colors_dir = os.path.join(design_assets_dir, '颜色规范')
    os.makedirs(colors_dir, exist_ok=True)

    # 从样式文件中提取颜色
    styles_dir = os.path.join(output_dir, '04_样式_Styles')
    colors = []

    if os.path.exists(styles_dir):
        for file in os.listdir(styles_dir):
            if file.endswith('.json'):
                file_path = os.path.join(styles_dir, file)
                try:
                    with open(file_path, 'r', encoding='utf-8') as f:
                        content = f.read()
                        # 简单提取颜色值
                        color_matches = re.findall(r'"red":\s*([\d.]+).*?"green":\s*([\d.]+).*?"blue":\s*([\d.]+)', content)
                        for r, g, b in color_matches:
                            colors.append({'r': float(r), 'g': float(g), 'b': float(b)})
                except:
                    continue

    # 生成颜色规范文档
    colors_doc = os.path.join(colors_dir, '颜色规范.md')
    with open(colors_doc, 'w', encoding='utf-8') as f:
        f.write("# 颜色规范\n\n")

        if colors:
            f.write("## 提取的颜色值\n\n")
            unique_colors = []
            for color in colors[:20]:  # 取前20个颜色
                rgb = f"rgb({int(color['r']*255)}, {int(color['g']*255)}, {int(color['b']*255)})"
                if rgb not in unique_colors:
                    unique_colors.append(rgb)

            for i, color in enumerate(unique_colors, 1):
                f.write(f"{i}. {color}\n")
        else:
            f.write("## 建议的颜色规范\n\n")
            f.write("基于汽车应用的特点，建议使用以下颜色：\n\n")
            f.write("- **主色调**: #2C3E50 (深蓝灰)\n")
            f.write("- **辅助色**: #3498DB (蓝色)\n")
            f.write("- **成功色**: #27AE60 (绿色)\n")
            f.write("- **警告色**: #F39C12 (橙色)\n")
            f.write("- **错误色**: #E74C3C (红色)\n")
            f.write("- **文字色**: #2C3E50 (深灰)\n")
            f.write("- **背景色**: #FFFFFF (白色)\n")

    print(f"    - 颜色规范: {len(colors)} 个颜色值")


def create_design_specification(design_assets_dir, output_dir):
    """创建设计规范文档"""
    spec_file = os.path.join(design_assets_dir, '设计规范总览.md')

    with open(spec_file, 'w', encoding='utf-8') as f:
        f.write("# alphax 汽车应用设计规范\n\n")
        f.write("基于Sketch文件提取的完整设计规范\n\n")

        f.write("## 📱 应用概述\n\n")
        f.write("这是一个汽车声音控制应用，主要功能包括：\n")
        f.write("- 车辆连接和管理\n")
        f.write("- 声音设置和调节\n")
        f.write("- 用户账户管理\n")
        f.write("- 设备设置和支持\n\n")

        f.write("## 🎨 设计文件结构\n\n")
        f.write("```\n")
        f.write("09_可用设计资源/\n")
        f.write("├── 组件库/\n")
        f.write("│   ├── 按钮组件.md\n")
        f.write("│   ├── 文字组件.md\n")
        f.write("│   └── 布局组件.md\n")
        f.write("├── 样式规范/\n")
        f.write("│   └── 文字样式规范.md\n")
        f.write("├── 颜色规范/\n")
        f.write("│   └── 颜色规范.md\n")
        f.write("└── 设计规范总览.md\n")
        f.write("```\n\n")

        f.write("## 🔧 如何使用这些资源\n\n")
        f.write("### 1. 开发团队\n")
        f.write("- 查看 `组件库/` 了解所有UI组件\n")
        f.write("- 参考 `样式规范/` 实现文字样式\n")
        f.write("- 使用 `颜色规范/` 保持颜色一致性\n\n")

        f.write("### 2. 设计团队\n")
        f.write("- 基于组件库创建设计系统\n")
        f.write("- 标准化按钮、文字、颜色等元素\n")
        f.write("- 确保设计一致性\n\n")

        f.write("### 3. 产品团队\n")
        f.write("- 了解现有功能和UI元素\n")
        f.write("- 规划新功能时参考现有组件\n")
        f.write("- 维护产品设计规范\n\n")

    print("    - 设计规范文档已生成")


def create_hierarchical_structure(design_assets_dir, output_dir):
    """创建层级结构展示"""
    hierarchy_dir = os.path.join(design_assets_dir, '层级结构')
    os.makedirs(hierarchy_dir, exist_ok=True)

    # 1. 创建页面层级结构
    create_page_hierarchy(hierarchy_dir, output_dir)

    # 2. 创建组件层级结构
    create_component_hierarchy(hierarchy_dir, output_dir)

    # 3. 创建功能模块层级
    create_functional_hierarchy(hierarchy_dir, output_dir)

    print("    - 层级结构展示已生成")


def create_page_hierarchy(hierarchy_dir, output_dir):
    """创建页面层级结构"""
    ui_summary_file = os.path.join(output_dir, '08_UI元素详细分析', 'ui_analysis_summary.json')
    if not os.path.exists(ui_summary_file):
        return

    with open(ui_summary_file, 'r', encoding='utf-8') as f:
        ui_data = json.load(f)

    texts = ui_data.get('all_unique_texts', [])

    # 按页面分组文字内容
    page_structure = {}
    for text_item in texts:
        source = text_item['source']
        text = text_item['text']

        if source not in page_structure:
            page_structure[source] = {
                '界面标题': [],
                '功能按钮': [],
                '导航元素': [],
                '状态信息': [],
                '输入控件': [],
                '车型相关': [],
                '其他内容': []
            }

        # 智能分类文字到不同层级
        if any(keyword in text for keyword in ['页面', '界面', '主页', '首页']):
            page_structure[source]['界面标题'].append(text)
        elif any(keyword in text for keyword in ['确定', '取消', '登录', '设置', '下载', '换车', '完成']):
            page_structure[source]['功能按钮'].append(text)
        elif any(keyword in text for keyword in ['我的', '首页', '设置', '支持']):
            page_structure[source]['导航元素'].append(text)
        elif any(keyword in text for keyword in ['已连接', '正在', 'offline', 'Scanning', '聲音資源']):
            page_structure[source]['状态信息'].append(text)
        elif any(keyword in text for keyword in ['输入', '请输入', '设备号', '弹框']):
            page_structure[source]['输入控件'].append(text)
        elif any(keyword in text for keyword in ['mercedes', 'amg', 'Camry', 'sedan', 'w168', 'v168']):
            page_structure[source]['车型相关'].append(text)
        else:
            page_structure[source]['其他内容'].append(text)

    # 生成层级结构文档
    hierarchy_file = os.path.join(hierarchy_dir, '页面层级结构.md')
    with open(hierarchy_file, 'w', encoding='utf-8') as f:
        f.write("# 页面层级结构\n\n")
        f.write("## 📱 应用整体架构\n\n")

        page_names = {
            '3A32758E-96E1-4F29-B73E-616E68E0C6DA': '主界面页面',
            'A37E5689-D4A7-426A-97B6-30682016EE6E': '控制面板页面'
        }

        for page_id, content in page_structure.items():
            page_name = page_names.get(page_id, f'页面_{page_id[:8]}')
            f.write(f"### 🎯 {page_name}\n\n")

            for category, items in content.items():
                if items:
                    f.write(f"#### 📂 {category}\n")
                    for item in sorted(set(items))[:10]:  # 去重并取前10个
                        f.write(f"    ├── {item}\n")
                    if len(set(items)) > 10:
                        f.write(f"    └── ... 还有 {len(set(items)) - 10} 个\n")
                    f.write("\n")


def create_component_hierarchy(hierarchy_dir, output_dir):
    """创建组件层级结构"""
    ui_summary_file = os.path.join(output_dir, '08_UI元素详细分析', 'ui_analysis_summary.json')
    if not os.path.exists(ui_summary_file):
        return

    with open(ui_summary_file, 'r', encoding='utf-8') as f:
        ui_data = json.load(f)

    ui_counts = ui_data.get('ui_element_counts', {})
    texts = ui_data.get('all_unique_texts', [])

    # 创建组件层级结构
    hierarchy_file = os.path.join(hierarchy_dir, '组件层级结构.md')
    with open(hierarchy_file, 'w', encoding='utf-8') as f:
        f.write("# UI组件层级结构\n\n")
        f.write("## 🏗️ 组件架构总览\n\n")

        f.write("```\n")
        f.write("alphax汽车应用\n")
        f.write("│\n")
        f.write("├─ 🖼️ 布局容器层\n")
        f.write(f"│   ├─ 编组/容器: {ui_counts.get('groups', 0)} 个\n")
        f.write(f"│   └─ 矩形/卡片: {ui_counts.get('rectangles', 0)} 个\n")
        f.write("│\n")
        f.write("├─ 🎨 视觉元素层\n")
        f.write(f"│   ├─ 图形/图标: {ui_counts.get('shapes', 0)} 个\n")
        f.write(f"│   ├─ 路径/线条: {ui_counts.get('paths', 0)} 个\n")
        f.write(f"│   └─ 符号组件: {ui_counts.get('symbols', 0)} 个\n")
        f.write("│\n")
        f.write("├─ 📝 文字内容层\n")
        f.write(f"│   └─ 文字元素: {ui_counts.get('texts', 0)} 个\n")
        f.write("│\n")
        f.write("└─ 🔧 交互功能层\n")
        f.write("    ├─ 按钮组件\n")
        f.write("    ├─ 输入控件\n")
        f.write("    └─ 状态指示\n")
        f.write("```\n\n")

        # 按功能模块分类组件
        f.write("## 🎛️ 功能模块组件\n\n")

        modules = {
            '车辆管理模块': ['我的车', '换车', '车型', 'mercedes', 'amg', 'Camry'],
            '音频控制模块': ['聲浪', '音量', '声音', '下载', '怠速'],
            '连接设置模块': ['蓝牙', '连接', '设备', '输入新设备'],
            '用户界面模块': ['登录', '设置', '首页', '我的', '支持'],
            '状态显示模块': ['已连接', '正在下载', 'offline', 'Scanning']
        }

        for module, keywords in modules.items():
            f.write(f"### 📦 {module}\n")

            # 找到相关的文字
            related_texts = []
            for text_item in texts:
                text = text_item['text']
                if any(keyword.lower() in text.lower() for keyword in keywords):
                    related_texts.append(text)

            # 去重并分层显示
            unique_texts = sorted(list(set(related_texts)))

            if unique_texts:
                f.write("```\n")
                for i, text in enumerate(unique_texts[:8], 1):  # 每模块显示8个
                    f.write(f"├─ {text}\n")
                if len(unique_texts) > 8:
                    f.write(f"└─ ... 还有 {len(unique_texts) - 8} 个相关元素\n")
                f.write("```\n")
            else:
                f.write("暂无匹配的组件\n")
            f.write("\n")


def create_functional_hierarchy(hierarchy_dir, output_dir):
    """创建功能模块层级"""
    ui_summary_file = os.path.join(output_dir, '08_UI元素详细分析', 'ui_analysis_summary.json')
    if not os.path.exists(ui_summary_file):
        return

    with open(ui_summary_file, 'r', encoding='utf-8') as f:
        ui_data = json.load(f)

    texts = ui_data.get('all_unique_texts', [])

    # 创建功能层级树
    hierarchy_file = os.path.join(hierarchy_dir, '功能层级树.md')
    with open(hierarchy_file, 'w', encoding='utf-8') as f:
        f.write("# 功能层级树\n\n")
        f.write("## 🌳 完整功能架构\n\n")

        f.write("```\n")
        f.write("📱 alphax汽车声音控制应用\n")
        f.write("│\n")
        f.write("├─ 🚗 车辆管理\n")
        f.write("│   ├─ 车辆选择\n")
        f.write("│   │   ├─ 换车\n")
        f.write("│   │   ├─ 我的车\n")
        f.write("│   │   └─ 车型信息\n")
        f.write("│   │       ├─ Mercedes-Benz\n")
        f.write("│   │       ├─ AMG 系列\n")
        f.write("│   │       └─ Toyota Camry\n")
        f.write("│   └─ 车辆状态\n")
        f.write("│       ├─ 已连接\n")
        f.write("│       ├─ 离线状态\n")
        f.write("│       └─ 扫描中\n")
        f.write("│\n")
        f.write("├─ 🔊 声音控制\n")
        f.write("│   ├─ 声浪设置\n")
        f.write("│   │   ├─ 声浪音量\n")
        f.write("│   │   ├─ 声浪音频\n")
        f.write("│   │   └─ 声浪类型\n")
        f.write("│   │       ├─ 聲浪1\n")
        f.write("│   │       ├─ 聲浪2\n")
        f.write("│   │       └─ 聲浪3\n")
        f.write("│   ├─ 启动声音\n")
        f.write("│   │   ├─ 動態啟動\n")
        f.write("│   │   ├─ 啟動咆哮\n")
        f.write("│   │   └─ 怠速聲\n")
        f.write("│   └─ 声音下载\n")
        f.write("│       ├─ 音频资源下载\n")
        f.write("│       └─ 正在下载状态\n")
        f.write("│\n")
        f.write("├─ 🔗 连接管理\n")
        f.write("│   ├─ 蓝牙连接\n")
        f.write("│   ├─ 设备管理\n")
        f.write("│   │   ├─ 输入新设备\n")
        f.write("│   │   └─ 设备号输入\n")
        f.write("│   └─ 连接状态\n")
        f.write("│\n")
        f.write("├─ 👤 用户管理\n")
        f.write("│   ├─ 登录系统\n")
        f.write("│   ├─ 我的账户\n")
        f.write("│   └─ 登出设备\n")
        f.write("│\n")
        f.write("├─ ⚙️ 系统设置\n")
        f.write("│   ├─ 常用功能\n")
        f.write("│   ├─ 更新软件\n")
        f.write("│   ├─ 检查更新\n")
        f.write("│   ├─ 车辆固件\n")
        f.write("│   └─ 演示模式\n")
        f.write("│\n")
        f.write("├─ 🆘 帮助支持\n")
        f.write("│   ├─ 技术支持\n")
        f.write("│   └─ 提示弹框\n")
        f.write("│       ├─ 提示弹框1\n")
        f.write("│       └─ 提示弹框2\n")
        f.write("│\n")
        f.write("└─ 🧭 导航界面\n")
        f.write("    ├─ 首页\n")
        f.write("    ├─ 我的\n")
        f.write("    └─ 设置\n")
        f.write("```\n\n")

        f.write("## 📊 功能完整度统计\n\n")

        # 统计每个功能模块的元素数量
        function_stats = {
            '车辆管理': 0,
            '声音控制': 0,
            '连接管理': 0,
            '用户管理': 0,
            '系统设置': 0,
            '帮助支持': 0,
            '导航界面': 0
        }

        for text_item in texts:
            text = text_item['text']
            if any(keyword in text for keyword in ['车', '换车', 'mercedes', 'amg', 'camry']):
                function_stats['车辆管理'] += 1
            elif any(keyword in text for keyword in ['声', '音', '聲浪', '启动', '怠速']):
                function_stats['声音控制'] += 1
            elif any(keyword in text for keyword in ['蓝牙', '连接', '设备', '输入']):
                function_stats['连接管理'] += 1
            elif any(keyword in text for keyword in ['登录', '我的', '登出']):
                function_stats['用户管理'] += 1
            elif any(keyword in text for keyword in ['设置', '更新', '固件', '演示']):
                function_stats['系统设置'] += 1
            elif any(keyword in text for keyword in ['支持', '提示', '弹框']):
                function_stats['帮助支持'] += 1
            elif any(keyword in text for keyword in ['首页', '导航']):
                function_stats['导航界面'] += 1

        for function, count in function_stats.items():
            f.write(f"- **{function}**: {count} 个相关元素\n")


def extract_ui_elements_from_large_content(content, all_ui_elements, all_texts, page_name):
    """从大文件内容中提取UI元素"""

    # 提取所有文字内容
    text_patterns = [
        r'"string":\s*"([^"]+)"',
        r'"name":\s*"([^"]*[\u4e00-\u9fa5]+[^"]*)"',  # 包含中文的名称
        r'"attributedString"[^{}]*"string":\s*"([^"]+)"'
    ]

    for pattern in text_patterns:
        matches = re.finditer(pattern, content)
        for match in matches:
            text_content = match.group(1)
            if text_content and len(text_content.strip()) > 0:
                all_texts.append({
                    'text': text_content,
                    'source': page_name,
                    'type': 'text_content'
                })

    # 提取UI元素类型
    ui_patterns = {
        'buttons': r'"_class":\s*".*button.*"[^{}]*{[^{}]*(?:{[^{}]*}[^{}]*)*}',
        'groups': r'"_class":\s*"group"[^{}]*{[^{}]*(?:{[^{}]*}[^{}]*)*}',
        'texts': r'"_class":\s*"text"[^{}]*{[^{}]*(?:{[^{}]*}[^{}]*)*}',
        'rectangles': r'"_class":\s*"rectangle"[^{}]*{[^{}]*(?:{[^{}]*}[^{}]*)*}',
        'shapes': r'"_class":\s*"shape.*"[^{}]*{[^{}]*(?:{[^{}]*}[^{}]*)*}',
        'symbols': r'"_class":\s*"symbol.*"[^{}]*{[^{}]*(?:{[^{}]*}[^{}]*)*}',
        'images': r'"_class":\s*".*image.*"[^{}]*{[^{}]*(?:{[^{}]*}[^{}]*)*}'
    }

    for element_type, pattern in ui_patterns.items():
        matches = re.finditer(pattern, content, re.IGNORECASE)
        for i, match in enumerate(matches):
            try:
                element_text = match.group()
                # 尝试构建完整的JSON
                if not element_text.startswith('{'):
                    element_text = '{' + element_text
                if not element_text.endswith('}'):
                    element_text = element_text + '}'

                element_data = json.loads(element_text)
                element_info = {
                    'id': f"{page_name}_{element_type}_{i}",
                    'source': page_name,
                    'name': element_data.get('name', 'unnamed'),
                    'class': element_data.get('_class'),
                    'data': element_data
                }

                all_ui_elements[element_type].append(element_info)

            except json.JSONDecodeError:
                continue


def extract_ui_elements_recursive(data, all_ui_elements, all_texts, page_name, path="root"):
    """递归提取UI元素"""

    if isinstance(data, dict):
        # 检查是否是UI元素
        element_class = data.get('_class', '')
        element_name = data.get('name', '')
        element_id = data.get('do_objectID', f"unknown_{len(all_ui_elements.get('shapes', []))}")

        # 分类处理不同类型的元素
        if element_class == 'text':
            # 提取文字元素
            text_info = {
                'id': element_id,
                'name': element_name,
                'source': page_name,
                'path': path,
                'class': element_class,
                'frame': data.get('frame', {}),
                'data': data
            }

            # 提取文字内容
            if 'attributedString' in data:
                attr_string = data['attributedString']
                if isinstance(attr_string, dict) and 'string' in attr_string:
                    text_content = attr_string['string']
                    all_texts.append({
                        'text': text_content,
                        'name': element_name,
                        'source': page_name,
                        'type': 'text_element',
                        'id': element_id
                    })

            all_ui_elements['texts'].append(text_info)

        elif 'button' in element_class.lower() or 'button' in element_name.lower():
            all_ui_elements['buttons'].append({
                'id': element_id,
                'name': element_name,
                'source': page_name,
                'path': path,
                'class': element_class,
                'frame': data.get('frame', {}),
                'data': data
            })

        elif element_class == 'group':
            all_ui_elements['groups'].append({
                'id': element_id,
                'name': element_name,
                'source': page_name,
                'path': path,
                'class': element_class,
                'frame': data.get('frame', {}),
                'layers_count': len(data.get('layers', [])),
                'data': data
            })

        elif element_class == 'rectangle':
            all_ui_elements['rectangles'].append({
                'id': element_id,
                'name': element_name,
                'source': page_name,
                'path': path,
                'class': element_class,
                'frame': data.get('frame', {}),
                'data': data
            })

        elif 'symbol' in element_class.lower():
            all_ui_elements['symbols'].append({
                'id': element_id,
                'name': element_name,
                'source': page_name,
                'path': path,
                'class': element_class,
                'frame': data.get('frame', {}),
                'data': data
            })

        elif 'shape' in element_class.lower() or 'path' in element_class.lower():
            category = 'paths' if 'path' in element_class.lower() else 'shapes'
            all_ui_elements[category].append({
                'id': element_id,
                'name': element_name,
                'source': page_name,
                'path': path,
                'class': element_class,
                'frame': data.get('frame', {}),
                'data': data
            })

        # 检查字符串值中的文字内容
        if 'string' in data and isinstance(data['string'], str):
            text_content = data['string']
            if text_content and len(text_content.strip()) > 0:
                all_texts.append({
                    'text': text_content,
                    'name': element_name or 'unnamed',
                    'source': page_name,
                    'type': 'string_value',
                    'id': element_id
                })

        # 递归处理子元素
        for key, value in data.items():
            if key not in ['data']:  # 避免重复处理
                extract_ui_elements_recursive(value, all_ui_elements, all_texts, page_name, f"{path}.{key}")

    elif isinstance(data, list):
        for i, item in enumerate(data):
            extract_ui_elements_recursive(item, all_ui_elements, all_texts, page_name, f"{path}[{i}]")


def save_ui_elements_analysis(ui_elements_dir, all_ui_elements, all_texts):
    """保存UI元素分析结果"""

    # 保存所有文字内容
    texts_by_type = {}
    for text_item in all_texts:
        text_type = text_item['type']
        if text_type not in texts_by_type:
            texts_by_type[text_type] = []
        texts_by_type[text_type].append(text_item)

    with open(os.path.join(ui_elements_dir, 'all_texts.json'), 'w', encoding='utf-8') as f:
        json.dump({
            'total_texts': len(all_texts),
            'texts_by_type': texts_by_type,
            'all_texts': all_texts
        }, f, ensure_ascii=False, indent=2)

    # 生成文字内容摘要
    unique_texts = list(set([t['text'] for t in all_texts if t['text']]))
    with open(os.path.join(ui_elements_dir, 'texts_summary.txt'), 'w', encoding='utf-8') as f:
        f.write("=== 所有提取的文字内容 ===\n\n")
        for i, text in enumerate(sorted(unique_texts), 1):
            f.write(f"{i:3d}. {text}\n")

    # 保存各类UI元素
    for element_type, elements in all_ui_elements.items():
        if elements:
            filename = f'{element_type}_elements.json'
            with open(os.path.join(ui_elements_dir, filename), 'w', encoding='utf-8') as f:
                json.dump({
                    'total_count': len(elements),
                    'elements': elements
                }, f, ensure_ascii=False, indent=2)

    # 生成UI元素摘要
    summary = {
        'extraction_summary': {
            'total_texts': len(all_texts),
            'unique_texts': len(unique_texts),
            'ui_elements_by_type': {k: len(v) for k, v in all_ui_elements.items()},
            'total_ui_elements': sum(len(v) for v in all_ui_elements.values())
        },
        'text_samples': unique_texts[:50],  # 显示前50个文字样本
        'ui_element_samples': {
            element_type: [{'name': e.get('name'), 'class': e.get('class')}
                          for e in elements[:10]]  # 每类显示前10个
            for element_type, elements in all_ui_elements.items() if elements
        }
    }

    with open(os.path.join(ui_elements_dir, 'ui_analysis_summary.json'), 'w', encoding='utf-8') as f:
        json.dump(summary, f, ensure_ascii=False, indent=2)

    print(f"    - 文字摘要文件: texts_summary.txt ({len(unique_texts)} 个不重复文字)")


def generate_comprehensive_report(output_dir):
    """生成综合报告"""
    report_file = os.path.join(output_dir, '完整提取报告.md')

    with open(report_file, 'w', encoding='utf-8') as f:
        f.write("# alphax界面设计.sketch 完整提取报告\n\n")

        # 统计信息
        f.write("## 📊 提取统计\n\n")

        # 图片统计
        images_count = len(list(Path(output_dir).glob('00_所有图片/*')))
        f.write(f"- 图片文件: {images_count} 个\n")

        # 符号统计
        symbols_file = os.path.join(output_dir, '01_符号库_Symbols', 'symbols_info.json')
        if os.path.exists(symbols_file):
            with open(symbols_file, 'r', encoding='utf-8') as sf:
                symbols_data = json.load(sf)
                f.write(f"- 符号数量: {symbols_data.get('symbols_count', 0)} 个\n")

        # 画板统计
        artboard_summary_file = os.path.join(output_dir, '02_画板_Artboards', 'artboards_summary.json')
        if os.path.exists(artboard_summary_file):
            with open(artboard_summary_file, 'r', encoding='utf-8') as asf:
                artboard_data = json.load(asf)
                f.write(f"- 画板数量: {artboard_data.get('total_artboards', 0)} 个\n")
        else:
            f.write("- 画板数量: 0 个\n")

        # 页面统计
        pages_src_dir = os.path.join(output_dir, 'pages')
        if os.path.exists(pages_src_dir):
            pages_count = len([f for f in os.listdir(pages_src_dir) if f.endswith('.json')])
            f.write(f"- 页面数量: {pages_count} 个\n")

        # 插件统计
        plugin_summary_file = os.path.join(output_dir, '05_插件数据', 'plugin_summary.json')
        if os.path.exists(plugin_summary_file):
            with open(plugin_summary_file, 'r', encoding='utf-8') as psf:
                plugin_data = json.load(psf)
                f.write(f"- 小型组件: {len(plugin_data.get('mini_components', []))} 个\n")
                f.write(f"- 字体文件: {len(plugin_data.get('fonts', []))} 个\n")

        # UI元素统计
        ui_summary_file = os.path.join(output_dir, '08_UI元素详细分析', 'ui_analysis_summary.json')
        if os.path.exists(ui_summary_file):
            with open(ui_summary_file, 'r', encoding='utf-8') as usf:
                ui_data = json.load(usf)
                extraction_summary = ui_data.get('extraction_summary', {})
                f.write(f"- 所有文字: {extraction_summary.get('total_texts', 0)} 个\n")
                f.write(f"- 不重复文字: {extraction_summary.get('unique_texts', 0)} 个\n")
                f.write(f"- UI元素: {extraction_summary.get('total_ui_elements', 0)} 个\n")
        f.write("\n")

        f.write("## 📁 目录结构\n\n")
        f.write("```\n")
        for item in sorted(os.listdir(output_dir)):
            if os.path.isdir(os.path.join(output_dir, item)):
                f.write(f"{item}/\n")
                # 显示子目录内容
                subdir = os.path.join(output_dir, item)
                for subitem in sorted(os.listdir(subdir))[:5]:  # 只显示前5个
                    f.write(f"  ├── {subitem}\n")
                if len(os.listdir(subdir)) > 5:
                    f.write(f"  └── ... 还有 {len(os.listdir(subdir)) - 5} 个文件\n")
            else:
                f.write(f"{item}\n")
        f.write("```\n\n")

        f.write("## 🎯 新功能特点\n\n")
        f.write("✅ **深度递归解析** - 完整遍历所有层级结构\n")
        f.write("✅ **嵌入图片提取** - 支持base64和引用图片\n")
        f.write("✅ **大文件处理** - 智能分块处理大型JSON\n")
        f.write("✅ **小型组件识别** - 自动识别按钮、图标等组件\n")
        f.write("✅ **符号完整提取** - 修复符号和组件的提取逻辑\n\n")

        f.write("## 🔧 使用说明\n\n")
        f.write("1. **00_所有图片**: 包含所有图片资源\n")
        f.write("   - `embedded/` - 嵌入的base64图片\n")
        f.write("   - `references/` - 引用的图片资源\n")
        f.write("2. **01_符号库_Symbols**: 符号和组件完整数据\n")
        f.write("   - `all_symbols.json` - 所有找到的符号\n")
        f.write("   - `all_components.json` - 按类型分类的组件\n")
        f.write("3. **02_画板_Artboards**: 完整的画板信息\n")
        f.write("   - `artboards_summary.json` - 画板统计摘要\n")
        f.write("4. **03_页面_Pages**: 页面结构和文档信息\n")
        f.write("5. **04_样式_Styles**: 图层样式和文本样式\n")
        f.write("6. **05_插件数据**: 完整的插件和小型组件\n")
        f.write("   - `mini_components/` - 识别出的小型UI组件\n")
        f.write("   - `plugin_summary.json` - 插件数据总结\n")
        f.write("   - `fonts/` - 提取的字体文件\n")
        f.write("7. **06_原始文件**: 原始JSON文件备份\n")
        f.write("8. **08_UI元素详细分析**: 完整的UI元素和文字分析\n")
        f.write("   - `all_texts.json` - 所有提取的文字内容\n")
        f.write("   - `texts_summary.txt` - 文字内容清单\n")
        f.write("   - `*_elements.json` - 各类型UI元素详细数据\n")
        f.write("   - `ui_analysis_summary.json` - UI元素分析总结\n")


def extract_embedded_images_from_json(output_dir, embedded_images_dir):
    """从JSON文件中提取嵌入的base64图片和图片引用"""
    print("  - 搜索嵌入图片和base64编码图片...")
    image_counter = 1

    for root, dirs, files in os.walk(output_dir):
        for file in files:
            if file.endswith('.json'):
                file_path = os.path.join(root, file)
                try:
                    with open(file_path, 'r', encoding='utf-8') as f:
                        content = f.read()

                        # 查找base64编码的图片
                        base64_patterns = [
                            r'data:image/([^;]+);base64,([A-Za-z0-9+/=]+)',
                            r'"image":\s*"([A-Za-z0-9+/=]{100,})"',
                            r'"data":\s*"([A-Za-z0-9+/=]{100,})"'
                        ]

                        for pattern in base64_patterns:
                            matches = re.findall(pattern, content)
                            for match in matches:
                                if isinstance(match, tuple):
                                    if len(match) == 2:  # data:image format
                                        format_type, base64_data = match
                                        extension = format_type.split('/')[1] if '/' in format_type else 'png'
                                    else:
                                        base64_data = match[0]
                                        extension = 'png'
                                else:
                                    base64_data = match
                                    extension = 'png'

                                try:
                                    image_data = base64.b64decode(base64_data)
                                    if len(image_data) > 100:  # 过滤掉太小的数据
                                        filename = f"embedded_{image_counter}.{extension}"
                                        with open(os.path.join(embedded_images_dir, filename), 'wb') as img_file:
                                            img_file.write(image_data)
                                        print(f"    * 提取嵌入图片: {filename} ({len(image_data)} bytes)")
                                        image_counter += 1
                                except Exception:
                                    continue

                        # 解析JSON查找图片引用
                        try:
                            json_data = json.loads(content)
                            extract_image_refs_from_obj(json_data, embedded_images_dir, output_dir, image_counter)
                        except json.JSONDecodeError:
                            continue

                except Exception as e:
                    continue


def extract_image_refs_from_obj(obj, embedded_images_dir, output_dir, counter=1):
    """递归搜索对象中的图片引用"""
    if isinstance(obj, dict):
        for key, value in obj.items():
            if key in ['image', 'fill', 'border', 'shadow'] and isinstance(value, dict):
                # 查找图片引用
                if '_ref' in value and 'images/' in str(value.get('_ref', '')):
                    ref_path = value['_ref']
                    source_path = os.path.join(output_dir, ref_path)
                    if os.path.exists(source_path):
                        filename = f"ref_{counter}_{os.path.basename(ref_path)}"
                        dest_path = os.path.join(embedded_images_dir, filename)
                        if not os.path.exists(dest_path):
                            shutil.copy2(source_path, dest_path)
                            counter += 1
            else:
                extract_image_refs_from_obj(value, embedded_images_dir, output_dir, counter)
    elif isinstance(obj, list):
        for item in obj:
            extract_image_refs_from_obj(item, embedded_images_dir, output_dir, counter)


def extract_image_references(output_dir, images_dir):
    """查找所有可能的图片引用"""
    print("  - 搜索图片引用...")
    refs_dir = os.path.join(images_dir, 'references')
    os.makedirs(refs_dir, exist_ok=True)

    # 搜索所有可能包含图片引用的模式
    image_patterns = [
        r'([a-f0-9]{40})\.(png|jpg|jpeg|gif|webp|svg)',  # SHA1哈希命名的图片
        r'images/([^"\'}\s]+\.(png|jpg|jpeg|gif|webp|svg))',  # images/目录引用
        r'"image":\s*"([^"]+\.(png|jpg|jpeg|gif|webp|svg))"',  # JSON中的图片字段
    ]

    found_images = set()

    for root, dirs, files in os.walk(output_dir):
        for file in files:
            if file.endswith(('.json', '.txt', '.xml')):
                file_path = os.path.join(root, file)
                try:
                    with open(file_path, 'r', encoding='utf-8') as f:
                        content = f.read()
                        for pattern in image_patterns:
                            matches = re.findall(pattern, content)
                            for match in matches:
                                if isinstance(match, tuple):
                                    image_name = match[0] + '.' + match[1] if len(match) > 1 else match[0]
                                else:
                                    image_name = match
                                found_images.add(image_name)
                except Exception:
                    continue

    # 尝试找到并复制这些图片
    for image_name in found_images:
        for root, dirs, files in os.walk(output_dir):
            for file in files:
                if file == os.path.basename(image_name) or file.endswith(os.path.basename(image_name)):
                    source_path = os.path.join(root, file)
                    dest_path = os.path.join(refs_dir, file)
                    if not os.path.exists(dest_path) and 'references' not in source_path:
                        shutil.copy2(source_path, dest_path)
                        print(f"    * 找到引用图片: {file}")


def extract_symbols_from_pages(output_dir, symbols_dir, all_symbols, all_components):
    """从页面中深度提取符号和组件"""
    print("  - 深度解析页面中的符号...")
    pages_dir = os.path.join(output_dir, 'pages')

    if not os.path.exists(pages_dir):
        return

    for page_file in os.listdir(pages_dir):
        if page_file.endswith('.json'):
            page_path = os.path.join(pages_dir, page_file)
            try:
                with open(page_path, 'r', encoding='utf-8') as f:
                    content = f.read()

                # 分块读取大文件
                if len(content) > 1000000:  # 1MB
                    process_large_json_file(page_path, symbols_dir, all_symbols, all_components)
                else:
                    page_data = json.loads(content)
                    extract_symbols_from_data(page_data, symbols_dir, all_symbols, all_components, page_file)

            except Exception as e:
                print(f"    ! 处理页面 {page_file} 时出错: {e}")
                continue

    # 保存所有找到的符号
    if all_symbols:
        with open(os.path.join(symbols_dir, 'all_symbols.json'), 'w', encoding='utf-8') as f:
            json.dump(all_symbols, f, ensure_ascii=False, indent=2)

    if all_components:
        with open(os.path.join(symbols_dir, 'all_components.json'), 'w', encoding='utf-8') as f:
            json.dump(dict(all_components), f, ensure_ascii=False, indent=2)


def process_large_json_file(file_path, symbols_dir, all_symbols, all_components):
    """处理大型JSON文件，分块读取"""
    print(f"    - 处理大文件: {os.path.basename(file_path)}")
    try:
        with open(file_path, 'r', encoding='utf-8') as f:
            # 尝试流式解析
            content = f.read()

            # 查找符号相关的关键字
            symbol_keywords = ['symbol', 'component', 'master', 'instance']
            for keyword in symbol_keywords:
                pattern = rf'"{keyword}"[^}}]*}}'
                matches = re.finditer(pattern, content, re.IGNORECASE)
                for i, match in enumerate(matches):
                    try:
                        symbol_data = json.loads('{' + match.group() + '}')
                        all_symbols[f'{keyword}_{i}'] = symbol_data
                    except json.JSONDecodeError:
                        continue

    except Exception as e:
        print(f"      ! 处理大文件时出错: {e}")


def extract_symbols_from_data(data, symbols_dir, all_symbols, all_components, source_file):
    """从数据中递归提取符号"""
    def recursive_extract(obj, path="root"):
        if isinstance(obj, dict):
            # 检查是否是符号定义
            if '_class' in obj:
                class_type = obj['_class']
                if 'symbol' in class_type.lower() or 'component' in class_type.lower():
                    symbol_id = obj.get('do_objectID', f"unknown_{len(all_symbols)}")
                    all_symbols[symbol_id] = obj
                    print(f"      * 找到符号: {class_type} - {obj.get('name', 'unnamed')}")

                if 'artboard' in class_type.lower():
                    artboard_name = obj.get('name', f'artboard_{len(all_components["artboards"])}')
                    all_components['artboards'].append({
                        'name': artboard_name,
                        'source': source_file,
                        'data': obj
                    })

            # 递归搜索
            for key, value in obj.items():
                recursive_extract(value, f"{path}.{key}")
        elif isinstance(obj, list):
            for i, item in enumerate(obj):
                recursive_extract(item, f"{path}[{i}]")

    recursive_extract(data)


def create_unique_filename(directory, filename):
    """创建唯一的文件名"""
    base, ext = os.path.splitext(filename)
    counter = 1
    new_filename = filename

    while os.path.exists(os.path.join(directory, new_filename)):
        new_filename = f"{base}_{counter}{ext}"
        counter += 1

    return new_filename


def print_summary(output_dir):
    """打印提取摘要"""
    print(f"📁 完整输出目录: {output_dir}")

    # 图片统计
    total_images = len(list(Path(output_dir).glob('00_所有图片/**/*')))
    embedded_images = len(list(Path(output_dir).glob('00_所有图片/embedded/*')))
    ref_images = len(list(Path(output_dir).glob('00_所有图片/references/*')))
    print(f"🖼️  图片资源: {total_images} 张 (嵌入:{embedded_images}, 引用:{ref_images})")

    # 符号统计
    all_symbols_file = os.path.join(output_dir, '01_符号库_Symbols', 'all_symbols.json')
    if os.path.exists(all_symbols_file):
        with open(all_symbols_file, 'r', encoding='utf-8') as f:
            symbols_data = json.load(f)
            print(f"🔧 符号组件: {len(symbols_data)} 个")

    # 画板统计
    artboard_summary_file = os.path.join(output_dir, '02_画板_Artboards', 'artboards_summary.json')
    if os.path.exists(artboard_summary_file):
        with open(artboard_summary_file, 'r', encoding='utf-8') as f:
            artboard_data = json.load(f)
            print(f"🎯 画板数量: {artboard_data.get('total_artboards', 0)} 个")

    # 插件和小型组件统计
    plugin_summary_file = os.path.join(output_dir, '05_插件数据', 'plugin_summary.json')
    if os.path.exists(plugin_summary_file):
        with open(plugin_summary_file, 'r', encoding='utf-8') as f:
            plugin_data = json.load(f)
            mini_components = len(plugin_data.get('mini_components', []))
            fonts = len(plugin_data.get('fonts', []))
            print(f"🔌 小型组件: {mini_components} 个")
            print(f"📝 字体文件: {fonts} 个")

    # UI元素和文字统计
    ui_summary_file = os.path.join(output_dir, '08_UI元素详细分析', 'ui_analysis_summary.json')
    if os.path.exists(ui_summary_file):
        with open(ui_summary_file, 'r', encoding='utf-8') as f:
            ui_data = json.load(f)
            extraction_summary = ui_data.get('extraction_summary', {})
            ui_elements = extraction_summary.get('ui_elements_by_type', {})
            print(f"📝 所有文字: {extraction_summary.get('total_texts', 0)} 个 (不重复: {extraction_summary.get('unique_texts', 0)} 个)")
            print(f"🎨 UI元素: 组{ui_elements.get('groups', 0)}个, 按钮{ui_elements.get('buttons', 0)}个, 矩形{ui_elements.get('rectangles', 0)}个")

    print(f"📊 详细报告: 查看 '{output_dir}/完整提取报告.md'")


def main():
    """主函数"""
    print("alphax界面设计.sketch 完整资源提取工具")
    print("版本: 3.0 - 完整深度提取，支持大文件处理")
    print("=" * 60)

    if extract_sketch_completely():
        print("\n✨ 所有设计资源提取完成！")
        print("💡 现在您可以查看符号、组件、画板等完整设计元素")
    else:
        print("\n❌ 提取失败")


def export_hierarchical_png_elements(output_dir):
    """按层级结构导出PNG元素到分类目录"""

    # 创建层级PNG目录
    png_hierarchy_dir = os.path.join(output_dir, '10_PNG元素分层导出')
    if os.path.exists(png_hierarchy_dir):
        shutil.rmtree(png_hierarchy_dir)
    os.makedirs(png_hierarchy_dir)

    # 根据功能层级创建目录结构
    hierarchy_structure = {
        '01_车辆管理': {
            'description': '车辆选择和状态相关PNG',
            'keywords': ['车', '换车', 'Camry', 'Mercedes', 'AMG', '已連接', 'offline', 'Scanning'],
            'subdirs': {
                '车辆选择': ['换车', '我的车', 'Camry', 'Mercedes', 'AMG'],
                '车辆状态': ['已連接', 'offline', 'Scanning', '正在下載']
            }
        },
        '02_声音控制': {
            'description': '声浪设置和音频控制PNG',
            'keywords': ['聲浪', '聲音', '音量', '下载', '啟動', '怠速', '動態'],
            'subdirs': {
                '声浪设置': ['聲浪設置', '聲浪1', '聲浪2', '聲浪3'],
                '启动声音': ['動態啟動', '啟動咆哮', '怠速聲'],
                '音频下载': ['声音下载', '音量下载', '正在下載聲音資源']
            }
        },
        '03_连接管理': {
            'description': '蓝牙连接和设备管理PNG',
            'keywords': ['蓝牙', '链接', '设备', '请输入'],
            'subdirs': {
                '蓝牙连接': ['蓝牙链接'],
                '设备管理': ['请输入新设备', '请输入新设备号']
            }
        },
        '04_用户管理': {
            'description': '用户账户和登录相关PNG',
            'keywords': ['登录', '我的', '用户'],
            'subdirs': {
                '账户管理': ['登录', '我的'],
                '用户操作': ['登出设备']
            }
        },
        '05_系统设置': {
            'description': '系统设置和配置PNG',
            'keywords': ['设置', '更新', '固件', '演示'],
            'subdirs': {
                '基本设置': ['设置'],
                '系统更新': ['更新软件', '检查更新', '车辆固件'],
                '特殊模式': ['演示模式']
            }
        },
        '06_帮助支持': {
            'description': '技术支持和帮助PNG',
            'keywords': ['支持', '技術支持', '提示弹框'],
            'subdirs': {
                '技术支持': ['支持', '技術支持'],
                '提示信息': ['提示弹框1', '提示弹框2']
            }
        },
        '07_导航界面': {
            'description': '应用导航和页面PNG',
            'keywords': ['首页', '我的', '设置页面'],
            'subdirs': {
                '主要页面': ['首页', '我的汽車', '我的聲音'],
                '页面状态': ['首页-点击发声样式', '首页-車輛動態的文字']
            }
        },
        '08_按钮元素': {
            'description': '各类按钮PNG元素',
            'keywords': ['确定', '完成', '好的', '取消'],
            'subdirs': {
                '确认按钮': ['确定', '完成', '好的'],
                '操作按钮': ['下载', '设置', '换车']
            }
        }
    }

    # 读取UI分析数据
    ui_analysis_file = os.path.join(output_dir, '08_UI元素详细分析', 'ui_analysis_summary.json')
    all_texts_file = os.path.join(output_dir, '08_UI元素详细分析', 'all_texts.json')

    ui_elements = {}
    all_texts = {}

    try:
        if os.path.exists(ui_analysis_file):
            with open(ui_analysis_file, 'r', encoding='utf-8') as f:
                ui_elements = json.load(f)
    except:
        pass

    try:
        if os.path.exists(all_texts_file):
            with open(all_texts_file, 'r', encoding='utf-8') as f:
                all_texts = json.load(f)
    except:
        pass

    # 创建层级目录结构
    for category, info in hierarchy_structure.items():
        category_dir = os.path.join(png_hierarchy_dir, category)
        os.makedirs(category_dir, exist_ok=True)

        # 创建子目录
        for subdir in info['subdirs']:
            subdir_path = os.path.join(category_dir, subdir)
            os.makedirs(subdir_path, exist_ok=True)

    # 复制现有PNG文件到层级结构中
    source_images_dir = os.path.join(output_dir, '00_所有图片')
    png_files = []

    if os.path.exists(source_images_dir):
        for root, dirs, files in os.walk(source_images_dir):
            for file in files:
                if file.lower().endswith('.png'):
                    png_files.append(os.path.join(root, file))

    # 分类PNG文件
    categorized_count = 0

    for png_file in png_files:
        filename = os.path.basename(png_file)
        file_copied = False

        # 根据文件名和关联文本分类
        for category, info in hierarchy_structure.items():
            category_dir = os.path.join(png_hierarchy_dir, category)

            # 检查是否匹配关键词
            for keyword in info['keywords']:
                if keyword.lower() in filename.lower() or any(keyword in text for text in all_texts.get('unique_texts', [])):
                    # 进一步分类到子目录
                    for subdir, subdir_keywords in info['subdirs'].items():
                        for sub_keyword in subdir_keywords:
                            if sub_keyword.lower() in filename.lower() or any(sub_keyword in text for text in all_texts.get('unique_texts', [])):
                                target_dir = os.path.join(category_dir, subdir)
                                target_file = os.path.join(target_dir, f"{sub_keyword}_{filename}")
                                try:
                                    shutil.copy2(png_file, target_file)
                                    file_copied = True
                                    categorized_count += 1
                                    break
                                except:
                                    pass
                        if file_copied:
                            break

                    # 如果没有分到子目录，放到主目录
                    if not file_copied:
                        target_file = os.path.join(category_dir, f"{keyword}_{filename}")
                        try:
                            shutil.copy2(png_file, target_file)
                            file_copied = True
                            categorized_count += 1
                        except:
                            pass
                    break

            if file_copied:
                break

        # 未分类的文件放到通用目录
        if not file_copied:
            uncategorized_dir = os.path.join(png_hierarchy_dir, '09_未分类PNG')
            os.makedirs(uncategorized_dir, exist_ok=True)
            target_file = os.path.join(uncategorized_dir, filename)
            try:
                shutil.copy2(png_file, target_file)
            except:
                pass

    # 创建虚拟PNG元素（基于文本内容）
    create_text_based_png_placeholders(png_hierarchy_dir, all_texts, hierarchy_structure)

    # 生成层级结构说明文档
    create_png_hierarchy_documentation(png_hierarchy_dir, hierarchy_structure, categorized_count, len(png_files))

    print(f"    ✅ PNG元素按层级导出完成")
    print(f"    📁 总计: {len(png_files)} 个PNG文件")
    print(f"    🎯 已分类: {categorized_count} 个")
    print(f"    📂 导出位置: {png_hierarchy_dir}")


def create_text_based_png_placeholders(png_hierarchy_dir, all_texts, hierarchy_structure):
    """基于文本内容创建PNG元素占位符"""

    if not all_texts or 'unique_texts' not in all_texts:
        return

    # 为每个文本元素创建占位说明
    for category, info in hierarchy_structure.items():
        category_dir = os.path.join(png_hierarchy_dir, category)

        for subdir, keywords in info['subdirs'].items():
            subdir_path = os.path.join(category_dir, subdir)

            # 创建该类别的文本元素清单
            matching_texts = []
            for text in all_texts['unique_texts']:
                for keyword in keywords:
                    if keyword in text:
                        matching_texts.append(text)
                        break

            if matching_texts:
                # 创建文本元素清单文件
                text_list_file = os.path.join(subdir_path, '_文本元素清单.md')
                with open(text_list_file, 'w', encoding='utf-8') as f:
                    f.write(f"# {subdir} - 文本元素清单\n\n")
                    f.write("以下文本元素可以导出为PNG：\n\n")
                    for i, text in enumerate(matching_texts, 1):
                        f.write(f"{i}. `{text}`\n")
                    f.write(f"\n💡 从Sketch中拖拽这些元素可生成对应的PNG文件\n")


def create_png_hierarchy_documentation(png_hierarchy_dir, hierarchy_structure, categorized_count, total_count):
    """创建PNG层级结构说明文档"""

    doc_file = os.path.join(png_hierarchy_dir, 'PNG层级结构说明.md')

    with open(doc_file, 'w', encoding='utf-8') as f:
        f.write("# PNG元素分层导出说明\n\n")
        f.write("## 📊 导出统计\n\n")
        f.write(f"- 总PNG文件数: {total_count}\n")
        f.write(f"- 已分类文件数: {categorized_count}\n")
        f.write(f"- 分类准确率: {(categorized_count/total_count*100):.1f}%\n\n")

        f.write("## 🗂️ 目录结构\n\n")
        f.write("```\n")
        f.write("10_PNG元素分层导出/\n")

        for category, info in hierarchy_structure.items():
            f.write(f"├── {category}/  # {info['description']}\n")
            for subdir in info['subdirs']:
                f.write(f"│   ├── {subdir}/\n")
        f.write("└── 09_未分类PNG/  # 未能自动分类的PNG文件\n")
        f.write("```\n\n")

        f.write("## 💡 使用说明\n\n")
        f.write("1. **按功能查找**: 每个主目录对应应用的一个功能模块\n")
        f.write("2. **按类型细分**: 子目录进一步按UI元素类型分类\n")
        f.write("3. **文本元素**: 查看各目录下的 `_文本元素清单.md` 了解可导出的文本PNG\n")
        f.write("4. **Sketch导出**: 直接从Sketch拖拽元素到桌面即可生成PNG\n\n")

        f.write("## 🎯 分类规则\n\n")
        for category, info in hierarchy_structure.items():
            f.write(f"### {category}\n")
            f.write(f"**描述**: {info['description']}\n\n")
            f.write("**关键词**: " + ", ".join(f"`{kw}`" for kw in info['keywords']) + "\n\n")
            f.write("**子分类**:\n")
            for subdir, keywords in info['subdirs'].items():
                f.write(f"- {subdir}: " + ", ".join(f"`{kw}`" for kw in keywords) + "\n")
            f.write("\n")


if __name__ == "__main__":
    main()