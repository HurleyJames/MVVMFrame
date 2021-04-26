package ac.hurley.module_home.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.tabs.TabLayout;

import ac.hurley.library_base.base.view.BaseMvvmFragment;
import ac.hurley.library_base.router.RouterFragmentPath;
import ac.hurley.module_home.BR;
import ac.hurley.module_home.R;
import ac.hurley.module_home.databinding.FragmentHomeBinding;
import ac.hurley.module_home.ui.viewmodel.HomeViewModel;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/16/21 8:23 PM
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
@Route(path = RouterFragmentPath.Home.PAGER_HOME)
public class HomeFragment extends BaseMvvmFragment<FragmentHomeBinding, HomeViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_home;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        // 使用 TabLayout 和 ViewPager 相关联
        binding.tlHome.setupWithViewPager(binding.vpHome);
        binding.vpHome.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tlHome));
        viewModel.addPage();
    }

    @Override
    public void initViewObservable() {
        viewModel.itemClickEvent.observe(this, ToastUtils::showShort);
    }
}
