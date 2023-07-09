package com.u2020.sdk.env;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import com.u2020.sdk.env.device.AndroidDeviceUtil;
import com.u2020.sdk.env.device.ShellAdbUtils;
import com.u2020.sdk.env.device.emulator.EmuCheckUtil;
import com.u2020.sdk.env.device.macaddress.MacAddressUtils;
import com.u2020.sdk.env.device.manufacturer.Identifier;
import com.u2020.sdk.env.device.manufacturer.Rom;
import com.u2020.sdk.env.internal.IACheck;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * 入口类
 */
public class Tattoo {
    @Keep
    public static final String VERSION = "1.2.7.0";
    @Keep
    public static boolean DEBUG = true;
    //key of json object element
    private static final String a  = "a";//virtual
    private static final String b = "b";//root
    private static final String c = "c";//cpu

    @Keep
    public static JSONObject env(Context context) {
        return abc(context);
    }

    /**
     * a: 0-不是模拟器，1-是模拟器，2-不能确定
     * b: root stat if true
     * c:cpu
     */
    @Keep
    public static void env(final Context context, final CallBack callBack) {
        if (callBack == null)
            return;
        final JSONObject env = Tattoo.env(context);

        if (env.optString(c).contains("x86")) {
            callBack.onComplete(env);
            return;
        }
        context.bindService(new Intent(context, TattooService.class), new ServiceConnection() {
            @Keep
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                IACheck aCheck = com.u2020.sdk.env.internal.IACheck.Stub.asInterface(service);

                if (aCheck != null) {
                    try {
                        boolean isEmulator = aCheck.a();
                        env.put(a, isEmulator ? 1 : 0);
                        if (isEmulator)
                            env.put(c, "x86");

                    } catch (RemoteException e) {
                        try {
                            env.put(a, 2);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                callBack.onComplete(env);
                context.unbindService(this);
            }

            @Keep
            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        }, Context.BIND_AUTO_CREATE);
    }

    /**
     * 判断是否root
     * return true root设备
     */
    @Keep
    public static boolean b() {
        //命令查找是否存在su文件，在没有权限或未root的手机结果会为空
        boolean isRoot = ShellAdbUtils.execCommand("which su", false).result == 0;
        if (!isRoot) {
            //遍历可能存在root文件的文件夹
            isRoot = getAllFiles(Environment.getRootDirectory() + "/bin/", "su").length() > 0
                    || getAllFiles(Environment.getRootDirectory() + "/xbin/", "su").length() > 0;
            if (!isRoot) {
                //如果上面的条件没有获取到，尝试直接执行su文件，如果存在，授权与不授权都可以确实是具有root权限
                ShellAdbUtils.CommandResult commandResult = ShellAdbUtils.execCommand("su", false);
                isRoot = commandResult.result == 0 || commandResult.errorMsg.toLowerCase().trim().equals("permissiondenied");
            }
        }
        return isRoot;
    }

    @NonNull
    private static JSONObject abc(Context context) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(a, rta(context) ? 1 : 0);
            jsonObject.put(b, b());
            jsonObject.put(c, AndroidDeviceUtil.getCpuAbi());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * relative, 相对准确获取{@link Tattoo#env}
     * @return true 是模拟器
     */
    @Keep
    public static boolean rta(Context context) {
        boolean a = false;
        try {
            a = EmuCheckUtil.mayOnEmulator(context);
        }catch (Throwable ignored){}
        return a;
    }

    /**
     * relative and safe, 常规相对安全获取{@link Tattoo#env}
     * @return true 是模拟器
     */
    @Keep
    public static boolean sva(Context context) {
        boolean a = false;
        try {
            a = EmuCheckUtil.mayOnEmulator2(context);
        }catch (Throwable ignored){}
        return a;
    }

    /**
     * 获取mac
     * return mac address
     */
    @Keep
    public static String d(Context context) {
        return MacAddressUtils.getMacAddress(context);
    }

    /**
     * 获取硬件序列号
     * return Serial
     */
    @Keep
    public static String e(Context context) {
        return AndroidDeviceUtil.getSerial(context);
    }

    @Keep
    public static JSONObject f(JSONObject var) {
        try {
            var = Rom.get(var);
        } catch (Exception ignored) {
        }
        return var;
    }

    @Keep
    public static void o(Context context, O oo) {
        Identifier.register(context, oo);
    }

    /**
     * 获取指定目录内所有文件路径
     *
     * @param dirPath 需要查询的文件目录
     */
    private static JSONArray getAllFiles(String dirPath, String name) {
        File f = new File(dirPath);
        f.setReadable(true);
        JSONArray fileList = new JSONArray();
        if (!f.exists()) {//判断路径是否存在
            return fileList;
        }
        File[] files = f.listFiles();
        if (files == null) {//判断权限
            return fileList;
        }
        for (File _file : files) {//遍历目录
            if (_file.isFile()) {
                String _name = _file.getName();
                String filePath = _file.getAbsolutePath();//获取文件路径
                String fileName = _file.getName();
//                Log.d("LOGCAT","fileName:"+fileName);
//                Log.d("LOGCAT","filePath:"+filePath);
                try {
                    if (fileName.equals(name)) {
                        JSONObject _fInfo = new JSONObject();
                        _fInfo.put("name", fileName);
                        _fInfo.put("path", filePath);
                        fileList.put(_fInfo);
                    }
                } catch (Exception e) {
                    return fileList;
                }
            } else if (_file.isDirectory()) {//查询子目录
                getAllFiles(_file.getAbsolutePath(), name);
            } else {
                return fileList;
            }
        }
        return fileList;
    }

    @Keep
    public interface CallBack {
        @Keep
        void onComplete(JSONObject jsonObject);
    }

    @Keep
    public interface O {
        @Keep
        @WorkerThread
        void valid(String o);
    }
}
