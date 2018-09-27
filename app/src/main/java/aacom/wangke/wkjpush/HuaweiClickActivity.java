package aacom.wangke.wkjpush;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.wk.jpush.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by wk on 2017/11/1.
 * 华为推送 点击通知栏 跳转界面，仅做逻辑处理
 */

public class HuaweiClickActivity extends AppCompatActivity {

    private static final String TAG = "HuaweiClickActivity";
    /**
     * 消息 Id
     **/
    private static final String KEY_MSGID = "msg_id";
    /**
     * 该通知的下发通道
     **/
    private static final String KEY_WHICH_PUSH_SDK = "rom_type";
    /**
     * 通知标题
     **/
    private static final String KEY_TITLE = "n_title";
    /**
     * 通知内容
     **/
    private static final String KEY_CONTENT = "n_content";
    /**
     * 通知附加字段
     **/
    private static final String KEY_EXTRAS = "n_extras";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        handleOpenClick();

    }

    /**
     * 处理点击事件，当前启动配置的 Activity 都是使用
     * Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
     * 方式启动，只需要在 onCreat 中调用此方法进行处理
     */
    private void handleOpenClick() {
        LogUtil.d(TAG, "用户点击打开了通知");
        if (getIntent().getData() == null) return;
        String data = getIntent().getData().toString();
        LogUtil.e(TAG, "msg content is " + String.valueOf(data));
        if (TextUtils.isEmpty(data)) return;
        try {
            JSONObject jsonObject = new JSONObject(data);
            byte whichPushSDK = (byte) jsonObject.optInt(KEY_WHICH_PUSH_SDK);
            String msgId = jsonObject.optString(KEY_MSGID);
            String extras = jsonObject.optString(KEY_EXTRAS);
            if (BuildConfig.DEBUG) {
                String title = jsonObject.optString(KEY_TITLE);
                String content = jsonObject.optString(KEY_CONTENT);
                StringBuilder sb = new StringBuilder();
                sb.append("msgId:");
                sb.append(String.valueOf(msgId));
                sb.append("\n");
                sb.append("title:");
                sb.append(String.valueOf(title));
                sb.append("\n");
                sb.append("content:");
                sb.append(String.valueOf(content));
                sb.append("\n");
                sb.append("extras:");
                sb.append(String.valueOf(extras));
                sb.append("\n");
                sb.append("platform:");
                sb.append(getPushSDKName(whichPushSDK));
                LogUtil.e(TAG, sb.toString());
            }

            //上报点击事件
            JPushInterface.reportNotificationOpened(this, msgId, whichPushSDK);

            checkMsg2Jump(String.valueOf(extras));
            finish();
        } catch (JSONException e) {
            LogUtil.e(TAG, "parse notification error");
        }
    }

    private void checkMsg2Jump(String msgExtra) {
        if (TextUtils.isEmpty(msgExtra)) {
            return;
        }
        // TODO: 2018/9/27   checkMsg2Jump
    }

    private String getPushSDKName(byte whichPushSDK) {
        String name;
        switch (whichPushSDK) {
            case 0:
                name = "jpush";
                break;
            case 1:
                name = "xiaomi";
                break;
            case 2:
                name = "huawei";
                break;
            case 3:
                name = "meizu";
                break;
            case 8:
                name = "fcm";
                break;
            default:
                name = "jpush";
        }
        return name;
    }

}
