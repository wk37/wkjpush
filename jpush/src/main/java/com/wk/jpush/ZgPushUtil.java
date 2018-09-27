package com.wk.jpush;

import android.content.Context;
import android.text.TextUtils;


import java.util.LinkedHashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

public class ZgPushUtil {

    public static void init(Context context) {
        JPushInterface.init(context);            // 初始化 JPush
    }

    public static void setDebugMode(boolean isDebug) {
        JPushInterface.setDebugMode(isDebug);    // 设置开启日志,发布时请关闭日志
    }

    public static void stopPush(Context context) {
        JPushInterface.stopPush(context);
    }

    public static void resumePush(Context context) {
        if (JPushInterface.isPushStopped(context)) {
            JPushInterface.resumePush(context);
        }
    }

    public static void setAlias(Context context, String alias) {
        if (!TextUtils.isEmpty(alias)) {
            JPushInterface.setAlias(context, 1, alias);
        }
    }

    public static void setTags(Context context, Set<String> tags) {
        if (!TextUtils.isEmpty(tags.toString())) {
            JPushInterface.setTags(context, 1, tags);
        }
    }
    public static void setTags(Context context, String[] tags) {

        if (tags != null && tags.length >0) {
            Set<String> tagSet = new LinkedHashSet<String>();

            int length = tags.length;
            for (int i = 0; i < length; i++) {
                tagSet.add("notice_"+tags[i]);
            }
            setTags(context, tagSet);
            LogUtil.e("JIGUANG", "setTags: "+tagSet.toString());

        }

    }

    public static void cleanTags(Context context) {
        JPushInterface.cleanTags(context, 1);
    }

    public static void deleteAlias(Context context) {
        JPushInterface.deleteAlias(context, 1);
    }


    public static void getInfo(Context context) {
        if (BuildConfig.DEBUG) {

        String registrationID = JPushInterface.getRegistrationID(context.getApplicationContext());
        LogUtil.e("JIGUANG", registrationID);
        JPushInterface.getAllTags(context.getApplicationContext(),1);
        JPushInterface.getAlias(context, 1);
        }
    }




}
