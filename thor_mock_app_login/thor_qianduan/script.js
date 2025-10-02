// AlphaX 汽车声音控制系统 - 页面切换功能

class PageManager {
    constructor() {
        this.currentPage = 'home';
        this.navButtons = document.querySelectorAll('.nav-btn');
        this.pages = document.querySelectorAll('.page');

        this.init();
    }

    init() {
        // 绑定导航按钮点击事件
        this.navButtons.forEach(button => {
            button.addEventListener('click', (e) => {
                const targetPage = e.target.getAttribute('data-page');
                this.switchPage(targetPage);
            });
        });

        // 设置初始页面
        this.switchPage('home');
    }

    switchPage(pageId) {
        // 隐藏所有页面
        this.pages.forEach(page => {
            page.classList.remove('active');
        });

        // 移除所有导航按钮的激活状态
        this.navButtons.forEach(button => {
            button.classList.remove('active');
        });

        // 显示目标页面
        const targetPage = document.getElementById(pageId);
        if (targetPage) {
            targetPage.classList.add('active');
            this.currentPage = pageId;
        }

        // 激活对应的导航按钮
        const targetButton = document.querySelector(`[data-page="${pageId}"]`);
        if (targetButton) {
            targetButton.classList.add('active');
        }

        // 页面切换完成后的回调
        this.onPageChanged(pageId);
    }

    onPageChanged(pageId) {
        console.log(`切换到页面: ${pageId}`);

        // 根据不同页面执行特定的初始化逻辑
        switch(pageId) {
            case 'home':
                this.initHomePage();
                break;
            case 'login':
                this.initLoginPage();
                break;
            case 'profile':
                this.initProfilePage();
                break;
            case 'settings':
                this.initSettingsPage();
                break;
            case 'download':
                this.initDownloadPage();
                break;
            case 'switch-car':
                this.initSwitchCarPage();
                break;
        }
    }

    initHomePage() {
        // 首页初始化逻辑
        console.log('初始化首页');
    }

    initLoginPage() {
        // 登录页面初始化逻辑
        console.log('初始化登录页面');
    }

    initProfilePage() {
        // 我的页面初始化逻辑
        console.log('初始化我的页面');
    }

    initSettingsPage() {
        // 设置页面初始化逻辑
        console.log('初始化设置页面');
    }

    initDownloadPage() {
        // 下载页面初始化逻辑
        console.log('初始化下载页面');
    }

    initSwitchCarPage() {
        // 换车页面初始化逻辑
        console.log('初始化换车页面');
    }

    getCurrentPage() {
        return this.currentPage;
    }
}

// 基础UI交互功能
class UIInteractions {
    constructor() {
        this.init();
    }

    init() {
        // 绑定各种UI交互事件
        this.bindSoundControls();
        this.bindSwitchControls();
        this.bindFormSubmissions();
        this.bindButtonClicks();
    }

    bindSoundControls() {
        // 声音控制按钮
        const soundBtns = document.querySelectorAll('.sound-btn');
        soundBtns.forEach(btn => {
            btn.addEventListener('click', (e) => {
                // 移除其他按钮的激活状态
                soundBtns.forEach(b => b.classList.remove('active'));
                // 激活当前按钮
                e.target.classList.add('active');
                console.log(`选择声音: ${e.target.textContent}`);
            });
        });

        // 功能按钮
        const functionBtns = document.querySelectorAll('.function-btn');
        functionBtns.forEach(btn => {
            btn.addEventListener('click', (e) => {
                btn.classList.toggle('active');
                console.log(`切换功能: ${e.target.textContent}`);
            });
        });
    }

    bindSwitchControls() {
        // 开关控制
        const switches = document.querySelectorAll('.switch input[type="checkbox"]');
        switches.forEach(switchEl => {
            switchEl.addEventListener('change', (e) => {
                const label = e.target.closest('.switch').querySelector('.slider');
                console.log(`开关状态: ${label.textContent} - ${e.target.checked ? '开' : '关'}`);
            });
        });

        // 切换控制
        const toggles = document.querySelectorAll('.toggle input[type="checkbox"]');
        toggles.forEach(toggle => {
            toggle.addEventListener('change', (e) => {
                console.log(`切换设置: ${e.target.checked ? '开启' : '关闭'}`);
            });
        });
    }

    bindFormSubmissions() {
        // 登录表单
        const loginForm = document.querySelector('.login-form');
        if (loginForm) {
            loginForm.addEventListener('submit', (e) => {
                e.preventDefault();
                console.log('提交登录表单');
                // 这里可以添加登录逻辑
            });
        }
    }

    bindButtonClicks() {
        // 品牌选择
        const brandBtns = document.querySelectorAll('.brand-btn');
        brandBtns.forEach(btn => {
            btn.addEventListener('click', (e) => {
                brandBtns.forEach(b => b.classList.remove('active'));
                e.target.classList.add('active');
                console.log(`选择品牌: ${e.target.textContent}`);
            });
        });

        // 系列选择
        const seriesBtns = document.querySelectorAll('.series-btn');
        seriesBtns.forEach(btn => {
            btn.addEventListener('click', (e) => {
                seriesBtns.forEach(b => b.classList.remove('active'));
                e.target.classList.add('active');
                console.log(`选择系列: ${e.target.textContent}`);
            });
        });

        // 型号选择
        const modelItems = document.querySelectorAll('.model-item');
        modelItems.forEach(item => {
            item.addEventListener('click', (e) => {
                modelItems.forEach(i => i.classList.remove('active'));
                e.currentTarget.classList.add('active');
                const modelName = e.currentTarget.querySelector('.model-name').textContent;
                console.log(`选择型号: ${modelName}`);
            });
        });

        // 设置按钮
        const settingBtns = document.querySelectorAll('.setting-btn');
        settingBtns.forEach(btn => {
            btn.addEventListener('click', (e) => {
                console.log(`点击设置: ${e.target.textContent}`);
            });
        });

        // 连接设备按钮
        const connectBtn = document.querySelector('.connect-btn');
        if (connectBtn) {
            connectBtn.addEventListener('click', () => {
                const deviceInput = document.querySelector('.device-input');
                const deviceId = deviceInput.value.trim();
                if (deviceId) {
                    console.log(`连接设备: ${deviceId}`);
                    // 这里可以添加设备连接逻辑
                } else {
                    alert('请输入设备号');
                }
            });
        }

        // 确定和完成按钮
        const confirmBtn = document.querySelector('.confirm-btn');
        const completeBtn = document.querySelector('.complete-btn');

        if (confirmBtn) {
            confirmBtn.addEventListener('click', () => {
                console.log('确认操作');
            });
        }

        if (completeBtn) {
            completeBtn.addEventListener('click', () => {
                console.log('完成操作');
            });
        }
    }
}

// 应用启动
document.addEventListener('DOMContentLoaded', () => {
    // 初始化页面管理器
    const pageManager = new PageManager();

    // 初始化UI交互
    const uiInteractions = new UIInteractions();

    // 全局对象，方便调试
    window.pageManager = pageManager;
    window.uiInteractions = uiInteractions;

    console.log('AlphaX 汽车声音控制系统已启动');
});

// 工具函数
const utils = {
    // 显示提示消息
    showMessage(message, type = 'info') {
        console.log(`[${type.toUpperCase()}] ${message}`);
        // 这里可以添加实际的消息显示逻辑
    },

    // 模拟加载状态
    showLoading(show = true) {
        console.log(show ? '显示加载状态' : '隐藏加载状态');
        // 这里可以添加实际的加载状态显示逻辑
    },

    // 格式化时间
    formatTime(timestamp) {
        return new Date(timestamp).toLocaleString('zh-CN');
    }
};

// 导出到全局作用域
window.utils = utils;