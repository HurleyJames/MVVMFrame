package ac.hurley.library_base.base.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import com.gyf.immersionbar.ImmersionBar;

import java.util.List;
import java.util.Random;

import ac.hurley.library_base.R;
import ac.hurley.library_base.action.BundleAction;
import ac.hurley.library_base.action.ClickAction;
import ac.hurley.library_base.action.HandlerAction;
import ac.hurley.library_base.action.KeyboardAction;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/15/21 2:59 PM
 *      github  : https://github.com/HurleyJames
 *      desc    : Activity 基类
 * </pre>
 */
public abstract class BaseActivity extends AppCompatActivity
        implements ClickAction, HandlerAction, BundleAction, KeyboardAction {

    /**
     * 状态栏沉浸
     */
    private ImmersionBar mImmersionBar;

    /**
     * Activity 回调集合
     */
    private SparseArray<OnActivityCallback> mActivityCallbacks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeCallbacks();
    }

    @Override
    public void finish() {
        // 隐藏软键盘，避免内存泄漏
        hideKeyboard(getCurrentFocus());
        super.finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 设置为当前的 Intent，避免 Activity 被杀死后重启的 Intent 还是原先的那个
        setIntent(intent);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            // 这个 fragment 必须是 BaseFragment 的子类，并且处于可见状态
            if (!(fragment instanceof BaseFragment)
                    || fragment.getLifecycle().getCurrentState() != Lifecycle.State.RESUMED) {
                continue;
            }
            if (((BaseFragment<?>) fragment).dispatchKeyEvent(event)) {
                // 如果 Fragment 拦截了这个事件，那么就不交给 Activity 处理
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        // 隐藏软键盘，避免内存泄漏
        hideKeyboard(getCurrentFocus());
        super.startActivityForResult(intent, requestCode);
    }

    public void startActivityForResult(Class<? extends Activity> clazz, OnActivityCallback callback) {
        startActivityForResult(new Intent(this, clazz), null, callback);
    }

    public void startActivityForResult(Intent intent, OnActivityCallback callback) {
        startActivityForResult(intent, null, callback);
    }

    public void startActivityForResult(Intent intent, @Nullable Bundle options, OnActivityCallback callback) {
        if (mActivityCallbacks == null) {
            mActivityCallbacks = new SparseArray<>(1);
        }
        int requestCode = new Random().nextInt((int) Math.pow(2, 16));
        mActivityCallbacks.put(requestCode, callback);
        startActivityForResult(intent, requestCode, options);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        OnActivityCallback callback;
        if (mActivityCallbacks != null && (callback = mActivityCallbacks.get(requestCode)) != null) {
            callback.onActivityResult(resultCode, data);
            mActivityCallbacks.remove(requestCode);
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        super.onActionModeFinished(mode);
    }

    @Nullable
    @Override
    public Bundle getBundle() {
        return getIntent().getExtras();
    }

    /**
     * 获取 ContentView，和 setContentView 对应的方法
     *
     * @return
     */
    public ViewGroup getContentView() {
        return findViewById(Window.ID_ANDROID_CONTENT);
    }

    protected void initActivity() {
        initLayout();
        initView();
        initData();
    }

    /**
     * 获取布局 ID
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化布局
     */
    protected void initLayout() {
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
            initSoftKeyboard();
        }

        if (isStatusBarEnabled()) {
            getStatusBarConfig().init();
        }
    }

    /**
     * 初始化软键盘
     */
    protected void initSoftKeyboard() {
        // 点击外部就隐藏软键盘，提升用户体验
        getContentView().setOnClickListener(v -> {
            // 隐藏软键盘，避免内存泄漏
            hideKeyboard(getCurrentFocus());
        });
    }


    /**
     * 结果回调
     */
    public interface OnActivityCallback {
        // resultCode 结果码，data 数据
        void onActivityResult(int resultCode, @Nullable Intent intent);
    }

    /**
     * 是否沉浸式状态栏
     *
     * @return
     */
    protected boolean isStatusBarEnabled() {
        return true;
    }

    /**
     * 状态栏字体深色模式
     *
     * @return
     */
    protected boolean isStatusBarDarkFont() {
        return true;
    }

    /**
     * 获取状态栏沉浸的配置对象
     * @return
     */
    @NonNull
    public ImmersionBar getStatusBarConfig() {
        if (mImmersionBar == null) {
            mImmersionBar = createStatusBarConfig();
        }
        return mImmersionBar;
    }

    /**
     * 初始化沉浸式状态栏
     * @return
     */
    @NonNull
    protected ImmersionBar createStatusBarConfig() {
        return ImmersionBar.with(this)
                // 默认状态栏字体颜色为黑色
                .statusBarDarkFont(isStatusBarDarkFont())
                // 指定导航栏背景颜色
                .navigationBarColor(android.R.color.white)
                // 状态栏字体和导航栏内容自动变色，必须指定状态栏颜色和导航栏颜色才可以自动变色
                .autoDarkModeEnable(true, 0.2f);
    }
}
