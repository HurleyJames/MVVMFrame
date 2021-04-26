package ac.hurley.library_base.init;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;

import ac.hurley.library_base.config.BuildConfig;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/12/21 2:35 PM
 *      github  : https://github.com/HurleyJames
 *      desc    : 基础库自身初始化操作
 * </pre>
 */
public class BaseModuleInit implements IModuleInit {

    @Override
    public boolean onInitAhead(Application application) {
        // 初始化路由框架
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(application);
        LogUtils.e("基础层初始化 -- OnInitAhead");
        return false;
    }

    @Override
    public boolean onInitLow(Application application) {
        LogUtils.e("基础层初始化 -- OnInitLow");
        return false;
    }
}
