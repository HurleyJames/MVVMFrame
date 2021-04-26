package ac.hurley.module_main.ui;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

import ac.hurley.library_base.base.view.BaseActivity;
import ac.hurley.library_base.base.view.BaseMvvmActivity;
import ac.hurley.library_base.base.viewmodel.BaseViewModel;
import ac.hurley.library_base.router.RouterActivityPath;
import ac.hurley.library_base.router.RouterFragmentPath;
import ac.hurley.module_main.BR;
import ac.hurley.module_main.R;
import ac.hurley.module_main.databinding.ActivityMainBinding;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/12/21 5:25 PM
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
@Route(path = RouterActivityPath.Main.PAGER_HOME)
public class MainActivity extends BaseMvvmActivity<ActivityMainBinding, BaseViewModel> {

    private List<Fragment> mFragments;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        // 初始化 Fragment
        initFragment();
        // 初始化 BottomTab
        initBottomTab();
    }

    private void initFragment() {
        Fragment homeFragment = (Fragment) ARouter.getInstance().build(RouterFragmentPath.Home.PAGER_HOME).navigation();
//        Fragment deliverFragment = (Fragment) ARouter.getInstance().build(RouterFragmentPath.Deliver.PAGER_DELIVER).navigation();
//        Fragment meFragment = (Fragment) ARouter.getInstance().build(RouterFragmentPath.User.PAGER_ME).navigation();
        mFragments = new ArrayList<>();
        mFragments.add(homeFragment);
//        mFragments.add(deliverFragment);
//        mFragments.add(meFragment);

        // 默认选中首页
        if (homeFragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fl_main, homeFragment);
            transaction.commitNowAllowingStateLoss();
        }
    }

    /**
     * 初始化底部 Button
     */
    private void initBottomTab() {
        NavigationController navigationController = binding.pagerBottomTab.material()
                .addItem(R.mipmap.home, getString(R.string.app_home))
                .addItem(R.mipmap.deliver, getString(R.string.app_deliver))
                .addItem(R.mipmap.me, getString(R.string.app_me))
                .setDefaultColor(ContextCompat.getColor(this, R.color.textColorVice))
                .build();

        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                Fragment currentFragment = mFragments.get(index);
                if (currentFragment != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fl_main, currentFragment);
                    transaction.commitNowAllowingStateLoss();
                }
            }

            @Override
            public void onRepeat(int index) {

            }
        });
    }
}
