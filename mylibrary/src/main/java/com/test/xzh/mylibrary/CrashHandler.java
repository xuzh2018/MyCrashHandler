package com.test.xzh.mylibrary;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import com.test.xzh.mylibrary.utils.PropertiesUtil;
import com.test.xzh.mylibrary.utils.TimeUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;

import static android.content.ContentValues.TAG;

/**
 * Created by xzh on 2017/5/15
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static CrashHandler INSTANCE;
    //是否开启日志输出，在Debug状态下开启
    public static final boolean DEBUG = true;
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private PropertiesUtil mProp;

    private static final String VERSION_NAME = "versionName";
    private static final String VERSION_CODE = "versionCode";
    private static final String STACK_TRACE = "STACK_TRACE";

    public static CrashHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CrashHandler();
        }
        return INSTANCE;
    }

    //获取系统默认的UncaughtException处理器，设置该CrashHandler为程序的默认处理器
    public void setCrashHandler(Context con) {
        mContext = con;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mProp = PropertiesUtil.getInstance(con);
    }

    //当UncaughtException发生时会转入该函数来处理
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Log.d(TAG, "CrashHandler uncaughtException");
        if (DEBUG) {
            e.printStackTrace();
        }
        if (!handleException(e) && mDefaultHandler != null) {
            Log.d(TAG, "mDefaultHandler.uncaughtException");
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(t, e);
        } else {
            Log.d(TAG, "sleep and killProcess");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Log.e(TAG, "Error : ", ex);
            }
            Process.killProcess(Process.myPid());
            System.exit(10);
        }
    }

    private boolean handleException(Throwable e) {
        if (e == null) {
            Log.d(TAG, "handleException --- ex==null");
            return true;
        }
        final String msg = getMsg(e);
        if (msg == null) {
            Log.d(TAG, "getMessage is null");
            return false;
        }
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "程序出错，即将退出:\n" + msg, Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        String file_name = String.format("crash-%s.log", TimeUtil.getNowDateTime());
        mProp.setFile(file_name).init();
        //收集设备信息
        collectCrashDeviceInfo(mContext);
        //保存错误报告文件
        saveCrashInfoToFile(e);
        //保存错误信息
        mProp.commit();
        //发送错误报告到服务器，若后台需要获取错误报告则打开
        //sendCrashReportsToServer(mContext);
        return true;
    }

    private void collectCrashDeviceInfo(Context ctx) {
        Log.d(TAG, "collectCrashDeviceInfo");
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                mProp.writeString(VERSION_NAME, (pi.versionName==null)?"not set":pi.versionName);
                mProp.writeInt(VERSION_CODE, pi.versionCode);
            }
        }catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Error while collect package info", e);
        }
        //使用反射来收集设备信息，例如：系统版本号、设备生产商等环境信息
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                mProp.writeString(field.getName(), ""+field.get(null));
                if (DEBUG) {
                    Log.d(TAG, field.getName() + " : " + field.get(null));
                }
            }catch (Exception e) {
                Log.e(TAG, "Error while collect crash info", e);
            }
        }
    }

    private String getMsg(Throwable e) {
        //若是空指针异常，getLocalizedMessage返回的是null
        String msg = e.getLocalizedMessage();
        if (msg == null) {
//        	PrintStream err_msg = System.err.append(toString());
//        	msg = err_msg.toString();
            StackTraceElement[] stackArray = e.getStackTrace();
            StackTraceElement element = stackArray[0];
            msg = element.toString();
        }
        return msg;
    }

    //保存错误信息到文件中
    private void saveCrashInfoToFile(Throwable ex) {
        Log.d(TAG, "saveCrashInfoToFile");
        Writer info = new StringWriter();
        PrintWriter printWriter = new PrintWriter(info);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        String result = info.toString();
        printWriter.close();
        mProp.writeString("EXEPTION", getMsg(ex));
        mProp.writeString(STACK_TRACE, result);
    }
}
