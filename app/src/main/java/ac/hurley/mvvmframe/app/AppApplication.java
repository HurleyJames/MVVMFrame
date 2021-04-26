package ac.hurley.mvvmframe.app;

import ac.hurley.library_base.base.BaseApplication;
import ac.hurley.library_base.config.ModuleLifecycleConfig;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/12/21 5:19 PM
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public class AppApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化组件(靠前)
        ModuleLifecycleConfig.getInstance().initModuleAhead(this);
        //....
        // 初始化组件(靠后)
        ModuleLifecycleConfig.getInstance().initModuleLow(this);
    }
}
