package ac.hurley.module_main;

import android.app.Application;

import com.blankj.utilcode.util.LogUtils;

import ac.hurley.library_base.init.IModuleInit;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/12/21 3:30 PM
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public class MainModuleInit implements IModuleInit {


    @Override
    public boolean onInitAhead(Application application) {
        LogUtils.e("主业务模块初始化 -- onInitAhead");
        return false;
    }

    @Override
    public boolean onInitLow(Application application) {
        LogUtils.e("主业务模块初始化 -- onInitLow");
        return false;
    }
}
