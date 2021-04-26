package ac.hurley.library_base.debug;


import ac.hurley.library_base.base.BaseApplication;
import ac.hurley.library_base.config.ModuleLifecycleConfig;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/12/21 3:42 PM
 *      github  : https://github.com/HurleyJames
 *      desc    : Debug 包下的代码不参与编译，仅作为独立模块运行时初始化数据
 * </pre>
 */
public class DebugApplication extends BaseApplication {

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
