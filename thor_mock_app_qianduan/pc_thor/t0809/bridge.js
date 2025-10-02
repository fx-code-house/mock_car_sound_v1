Java.perform(function () {
    let CryptoManager = Java.use("com.thor.businessmodule.crypto.CryptoManager");
    CryptoManager["coreAesEncrypt"].implementation = function (data) {
        console.log(`CryptoManager.coreAesEncrypt is called: data=${data}`);
        let result = this["coreAesEncrypt"](data);
        console.log(`CryptoManager.coreAesEncrypt result=${result}`);
        return result;
    };

    CryptoManager["coreAesInit"].implementation = function (iv, hardware_version, firmware_version, serial_number) {
        console.log(`CryptoManager.coreAesInit is called: iv=${iv}, hardware_version=${hardware_version}, firmware_version=${firmware_version}, serial_number=${serial_number}`);
        this["coreAesInit"](iv, hardware_version, firmware_version, serial_number);
    };
})