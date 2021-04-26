package ac.hurley.library_base.config;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/12/21 3:26 PM
 *      github  : https://github.com/HurleyJames
 *      desc    : 组件生命周期反射类名管理，在这里注册需要初始化的组件，通过反射动态调用各个组件的初始化方法
 * </pre>
 */
public class ModuleLifecycleReflexs {

    private static final String BaseInit = "ac.hurley.library_base.init.BaseModuleInit";
    // 主业务模块
    private static final String MainInit = "ac.hurley.module_main.MainModuleInit";

    private static final String HomeInit = "ac.hurley.module_home.HomeModuleInit";

    public static String[] initModuleNames = {BaseInit, MainInit, HomeInit};
}
