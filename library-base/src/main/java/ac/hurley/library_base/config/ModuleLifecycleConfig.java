package ac.hurley.library_base.config;

import android.app.Application;

import ac.hurley.library_base.init.IModuleInit;
import io.reactivex.annotations.Nullable;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/12/21 3:22 PM
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public class ModuleLifecycleConfig {

    private static class SingletonHolder {
        public static ModuleLifecycleConfig instance = new ModuleLifecycleConfig();
    }

    public static ModuleLifecycleConfig getInstance() {
        return SingletonHolder.instance;
    }

    public ModuleLifecycleConfig() {
    }

    // 初始化组件-靠前
    public void initModuleAhead(@Nullable Application application) {
        for (String moduleInitName : ModuleLifecycleReflexs.initModuleNames) {
            try {
                Class<?> clazz = Class.forName(moduleInitName);
                IModuleInit init = (IModuleInit) clazz.newInstance();
                // 调用初始化方法
                init.onInitAhead(application);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    // 初始化组件-靠后
    public void initModuleLow(@Nullable Application application) {
        for (String moduleInitName : ModuleLifecycleReflexs.initModuleNames) {
            try {
                Class<?> clazz = Class.forName(moduleInitName);
                IModuleInit init = (IModuleInit) clazz.newInstance();
                // 调用初始化方法
                init.onInitLow(application);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
