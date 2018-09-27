package aacom.wangke.wkjpush;

import android.app.Application;
import android.content.Context;

import com.wk.jpush.ZgPushUtil;

/**
 * Created by wk on 2017/11/1.
 */

public class PushApplication extends Application {

    public static Context context;
    private static Application application;

    public static synchronized final Application getInstance() {
        if (application == null) {
            application = new Application();
        }
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        application = this;
        ZgPushUtil.init(this);
        ZgPushUtil.setDebugMode(BuildConfig.DEBUG);

    }

}
