package ac.hurley.library_base.base.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import java.util.List;

import ac.hurley.library_base.action.BundleAction;
import ac.hurley.library_base.action.ClickAction;
import ac.hurley.library_base.action.HandlerAction;
import ac.hurley.library_base.action.KeyboardAction;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/15/21 3:33 PM
 *      github  : https://github.com/HurleyJames
 *      desc    : Fragment 基类
 * </pre>
 */
public abstract class BaseFragment<A extends BaseActivity> extends Fragment
        implements HandlerAction, ClickAction, BundleAction, KeyboardAction {

    /**
     * Activity 对象
     */
    private A mActivity;
    /**
     * 根布局
     */
    private View mRootView;
    /**
     * 当前是否加载过
     */
    private boolean mLoading;

    @SuppressWarnings("unchecked")
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // 获得全局的 Activity
        mActivity = (A) requireActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutId() > 0) {
            return null;
        }
        mLoading = false;
        mRootView = inflater.inflate(getLayoutId(), container, false);
        initView();
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mLoading) {
            mLoading = true;
            initData();
            onFragmentResume(true);
            return;
        }

        if (mActivity != null && mActivity.getLifecycle().getCurrentState() == Lifecycle.State.STARTED) {
            onActivityResume();
        } else {
            onFragmentResume(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRootView = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLoading = false;
        removeCallbacks();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Nullable
    @Override
    public View getView() {
        return mRootView;
    }

    /**
     * 根据资源 id 获取一个 View
     *
     * @param id
     * @param <V>
     * @return
     */
    @Override
    public <V extends View> V findViewById(@IdRes int id) {
        return mRootView.findViewById(id);
    }

    @Nullable
    @Override
    public Bundle getBundle() {
        return getArguments();
    }

    public void startActivityForResult(Class<? extends Activity> clazz, BaseActivity.OnActivityCallback callback) {
        getAttachActivity().startActivityForResult(clazz, callback);
    }

    public void startActivityForResult(Intent intent, BaseActivity.OnActivityCallback callback) {
        getAttachActivity().startActivityForResult(intent, null, callback);
    }

    public void startActivityForResult(Intent intent, Bundle options, BaseActivity.OnActivityCallback callback) {
        getAttachActivity().startActivityForResult(intent, options, callback);
    }

    /**
     * 销毁当前 Fragment 所在的 Activity
     */
    public void finish() {
        if (mActivity == null || mActivity.isFinishing() || mActivity.isDestroyed()) {
            return;
        }
        mActivity.finish();
    }

    /**
     * Fragment 按键事件分发
     *
     * @param event
     * @return
     */
    public boolean dispatchKeyEvent(KeyEvent event) {
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            // 这个子 Fragment 必须是 BaseFragment 的子类，并且处于可见状态
            if (!(fragment instanceof BaseFragment) ||
                    fragment.getLifecycle().getCurrentState() != Lifecycle.State.RESUMED) {
                continue;
            }
            // 将按键事件派发给子 Fragment 进行处理
            if (((BaseFragment<?>) fragment).dispatchKeyEvent(event)) {
                // 如果子 Fragment 拦截了这个事件，那么就不交给父 Fragment 处理
                return true;
            }
        }
        switch (event.getAction()) {
            case KeyEvent.ACTION_DOWN:
                return onKeyDown(event.getKeyCode(), event);
            case KeyEvent.ACTION_UP:
                return onKeyUp(event.getKeyCode(), event);
            default:
                return false;
        }
    }

    /**
     * 这个 Fragment 是否已经加载过了
     *
     * @return
     */
    public boolean isLoading() {
        return mLoading;
    }

    /**
     * 获取绑定的 Activity，防止出现 Activity 为空的情况
     *
     * @return
     */
    public A getAttachActivity() {
        return mActivity;
    }

    /**
     * Fragment 可见回调，是否首次调用
     *
     * @param first
     */
    protected void onFragmentResume(boolean first) {
    }

    /**
     * Activity 回调可见
     */
    protected void onActivityResume() {
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
     * 按键按下
     *
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 默认不拦截事件
        return false;
    }

    /**
     * 按钮抬起
     *
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // 默认不拦截事件
        return false;
    }
}
