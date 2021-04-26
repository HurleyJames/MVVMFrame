package ac.hurley.library_base.base.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.lang.ref.WeakReference;

import ac.hurley.library_base.R;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/13/21 3:16 PM
 *      github  : https://github.com/HurleyJames
 *      desc    : 一个容器型的 Activity，里面用来装配 Fragment
 * </pre>
 */
public class ContainerActivity extends RxAppCompatActivity {

    private static final String FRAGMENT_TAG = "content_fragment_tag";

    public static final String FRAGMENT = "fragment";

    public static final String BUNDLE = "bundle";

    /**
     * Fragment 的弱引用
     */
    protected WeakReference<Fragment> mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        // 默认的盛装 Fragment 的 Activity 的界面
        setContentView(R.layout.activity_container);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = null;
        if (savedInstanceState != null) {
            fragment = fm.getFragment(savedInstanceState, FRAGMENT_TAG);
        }

        if (fragment == null) {
            fragment = initFromIntent(getIntent());
        }
        // 负责切换 Fragment 的方法
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.commitAllowingStateLoss();
        mFragment = new WeakReference<>(fragment);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, FRAGMENT_TAG, mFragment.get());
    }

    /**
     * 根据传递的 Intent 来初始化 Fragment
     * @param intent
     * @return
     */
    protected Fragment initFromIntent(Intent intent) {
        if (intent == null) {
            throw new RuntimeException("you must provide a page info to display");
        }
        try {
            String fragmentName = intent.getStringExtra(FRAGMENT);
            if (fragmentName == null || "".equals(fragmentName)) {
                throw new IllegalArgumentException("can not find page fragmentName");
            }
            Class<?> fragmentClass = Class.forName(fragmentName);
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            Bundle args = intent.getBundleExtra(BUNDLE);
            if (args != null) {
                fragment.setArguments(args);
            }
            return fragment;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("fragment initialization failed!");
    }

}
