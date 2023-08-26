package com.github.janisslsm.instagramxposed

import de.robv.android.xposed.*
import de.robv.android.xposed.callbacks.XC_LoadPackage

class MainHook : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName == "com.instagram.android" ||
                lpparam.packageName == "com.instander.android") {
            try {
                XposedHelpers.findAndHookMethod("android.app.SharedPreferencesImpl", lpparam.classLoader, "getBoolean",
                    String::class.java,
                    Boolean::class.java, object : XC_MethodHook() {
                    override fun afterHookedMethod(param: MethodHookParam) {
                        if(param.args[0] == "has_interop_enable")
                            param.result = true;
                    }
                });
            } catch (e: Throwable) {
                XposedBridge.log(e)
            }
        }
    }
}