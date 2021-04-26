package ac.hurley.library_base.init;

import android.app.Application;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/12/21 2:33 PM
 *      github  : https://github.com/HurleyJames
 *      desc    : 动态配置 Application，有需要初始化的组件实现该接口，统一在主 app 的 Application 中初始化
 * </pre>
 */
public interface IModuleInit {

    /**
     * 高优先级初始化
     *
     * @param application
     * @return
     */
    boolean onInitAhead(Application application);

    /**
     * 低优先级初始化
     *
     * @param application
     * @return
     */
    boolean onInitLow(Application application);
}
