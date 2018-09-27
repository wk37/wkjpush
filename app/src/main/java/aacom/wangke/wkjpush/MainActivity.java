package aacom.wangke.wkjpush;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.wk.jpush.LogUtil;
import com.wk.jpush.ZgPushUtil;

public class MainActivity extends AppCompatActivity {


    private String[] project_uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: 2018/9/27   phone = ...
        ZgPushUtil.setAlias(this.getApplicationContext(), "phone");
        // TODO: 2018/9/27   project_uid 赋值
//        project_uid = data.getProject_uid();

    }


    // TODO: 2018/9/27  合适的时候设置 tag
    private void setJpushTags() {

        if (isJpushTagsChanged()) {
            ZgPushUtil.cleanTags(this.getApplicationContext());
            ZgPushUtil.setTags(this.getApplicationContext(), project_uid);
        } else {
            // A手机上设置了tag，然后长时间未登陆，导致房间全过期，
            // 这时在B 手机登录，服务器返回 project_uid为空，本地localUids为空，
            // 从而 isJpushTagsChanged()为false，但是极光那边还是有tag的
            // 所以在此增加 project_uid 为空判断，尝试删除tag操作
            if (project_uid == null || project_uid.length == 0) {
                ZgPushUtil.cleanTags(this.getApplicationContext());
            }
            ZgPushUtil.getInfo(this.getApplicationContext());
        }
    }

    private boolean isJpushTagsChanged() {
        boolean isChanged = false;
        // uids 在for循环里面拼接，保存到 sp
        String uids = "";
        // TODO: 2018/9/27    localUids 从本地sp 获取
//        String localUids = (String) LSSpUtil.get(SPConstants.SP_PROJECT_UIDS, "");
        String localUids = "";


        LogUtil.e("JIGUANG", "localUids: " + localUids);

        if (project_uid != null && project_uid.length > 0) {
            int length = project_uid.length;
            for (int i = 0; i < length; i++) {
                uids += project_uid[i];

                if (localUids.contains(project_uid[i])) {
                    // 每一次对比UID有，都清除掉 里面有 的 uid
                    localUids = localUids.replace(project_uid[i], "");
                } else {
                    // 某一个 UID 本地没有，表明新增了，需要 更新tag
                    isChanged = true;
                }
            }


        }
        // 1. 上面if 语句false, 接口返回 无tag，但是本地有，表明 房间都删光了，需要做删除操作
        // 2. 上面if 语句走完，因for循环会不断删除localUids中单个UID， 若最后还有值，表明tag 所代表房间被删除，需要把tag删除
        if (!TextUtils.isEmpty(localUids)) {
            isChanged = true;
        }

        // TODO: 2018/9/27   uids sp更新
//        LSSpUtil.put(SPConstants.SP_PROJECT_UIDS, uids);


        LogUtil.e("JIGUANG", "freshmain: " + uids);
        LogUtil.e("JIGUANG", "localUids2:  " + localUids);
        LogUtil.e("JIGUANG", "isChanged:  " + isChanged);
        return isChanged;
    }

    private void logout(){
        ZgPushUtil.deleteAlias(this.getApplicationContext());
        ZgPushUtil.cleanTags(this.getApplicationContext());
        ZgPushUtil.stopPush(this.getApplicationContext());
    }

}
