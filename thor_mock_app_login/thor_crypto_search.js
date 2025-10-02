/**
 * Thor CryptoManager 类搜索脚本
 * 寻找包含AES加密方法的类
 */

Java.perform(function () {
    console.log("🔍 [CRYPTO SEARCH] 搜索加密相关类");
    console.log("=======================================\n");

    // 1. 搜索所有包含 "crypto" 或 "aes" 的类
    Java.enumerateLoadedClasses({
        onMatch: function(className) {
            const lowerClassName = className.toLowerCase();
            if (lowerClassName.includes('crypto') || 
                lowerClassName.includes('aes') || 
                lowerClassName.includes('encrypt') ||
                lowerClassName.includes('cipher')) {
                console.log("🔐 找到加密相关类: " + className);
                
                try {
                    const clazz = Java.use(className);
                    const methods = clazz.class.getDeclaredMethods();
                    
                    for (let i = 0; i < methods.length; i++) {
                        const methodName = methods[i].getName();
                        const lowerMethodName = methodName.toLowerCase();
                        
                        if (lowerMethodName.includes('encrypt') || 
                            lowerMethodName.includes('decrypt') ||
                            lowerMethodName.includes('aes') ||
                            lowerMethodName.includes('init')) {
                            console.log("   📝 加密方法: " + methodName);
                        }
                    }
                } catch (e) {
                    console.log("   ❌ 无法访问类方法: " + e.message);
                }
                console.log("   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            }
        },
        onComplete: function() {
            console.log("\n✅ 类搜索完成");
        }
    });

    // 2. 搜索包含特定方法名的类
    const targetMethods = ["coreAesEncrypt", "coreAesDecrypt", "coreAesInit"];
    
    setTimeout(function() {
        console.log("\n🎯 搜索包含目标方法的类...");
        
        Java.enumerateLoadedClasses({
            onMatch: function(className) {
                try {
                    const clazz = Java.use(className);
                    const methods = clazz.class.getDeclaredMethods();
                    
                    for (let i = 0; i < methods.length; i++) {
                        const methodName = methods[i].getName();
                        
                        if (targetMethods.includes(methodName)) {
                            console.log("🎉 找到目标方法: " + className + "." + methodName);
                            
                            // 尝试Hook这个方法
                            try {
                                if (methodName === "coreAesEncrypt") {
                                    clazz[methodName].implementation = function(data) {
                                        console.log(`🔐 [${className}] ${methodName} 被调用`);
                                        console.log(`   📤 数据: ${formatByteArray(data)}`);
                                        console.log(`   🔍 十六进制: ${formatHex(data)}`);
                                        
                                        const result = this[methodName](data);
                                        
                                        console.log(`   📥 结果: ${formatByteArray(result)}`);
                                        console.log(`   🔍 结果十六进制: ${formatHex(result)}`);
                                        console.log("   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
                                        
                                        return result;
                                    };
                                    console.log("✅ Hook成功: " + className + "." + methodName);
                                }
                                
                                if (methodName === "coreAesInit") {
                                    clazz[methodName].implementation = function() {
                                        console.log(`🔑 [${className}] ${methodName} 被调用`);
                                        console.log(`   📝 参数数量: ${arguments.length}`);
                                        
                                        for (let j = 0; j < arguments.length; j++) {
                                            const arg = arguments[j];
                                            if (arg !== null && arg !== undefined) {
                                                if (typeof arg === 'object' && arg.length !== undefined) {
                                                    console.log(`   📋 参数[${j}]: ${formatByteArray(arg)}`);
                                                } else {
                                                    console.log(`   📋 参数[${j}]: ${arg}`);
                                                }
                                            }
                                        }
                                        console.log("   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
                                        
                                        return this[methodName].apply(this, arguments);
                                    };
                                    console.log("✅ Hook成功: " + className + "." + methodName);
                                }
                                
                            } catch (e) {
                                console.log("❌ Hook失败: " + e.message);
                            }
                        }
                    }
                } catch (e) {
                    // 忽略无法访问的类
                }
            },
            onComplete: function() {
                console.log("\n✅ 方法搜索完成");
            }
        });
    }, 2000);

    // 工具函数
    function formatByteArray(bytes) {
        if (!bytes) return "null";
        return Array.from(bytes).join(",");
    }

    function formatHex(bytes) {
        if (!bytes) return "null";
        return Array.from(bytes).map(b => (b & 0xFF).toString(16).padStart(2, '0').toUpperCase()).join(" ");
    }

    console.log("\n🎯 [就绪] 搜索脚本启动完成！");
});