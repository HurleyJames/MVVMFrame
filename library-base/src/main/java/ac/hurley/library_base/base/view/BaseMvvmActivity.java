package ac.hurley.library_base.base.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.afollestad.materialdialogs.MaterialDialog;
import com.gyf.immersionbar.ImmersionBar;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import ac.hurley.library_base.base.viewmodel.BaseViewModel;
import ac.hurley.library_base.event.Messenger;
import ac.hurley.library_base.util.DialogUtils;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/12/21 11:03 PM
 *      github  : https://github.com/HurleyJames
 *      desc    : MVVM 类型的 Activity 基类
 * </pre>
 */
public abstract class BaseMvvmActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends RxAppCompatActivity implements IBaseView {

    protected V binding;
    protected VM viewModel;
    private int viewModelId;
    private MaterialDialog dialog;
    private ImmersionBar immersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 页面接收的参数
        initParam();
        // 私有的初始化 DataBinding 和 ViewModel 方法
        initViewDataBinding(savedInstanceState);
        // 私有的 ViewModel 与 View 的契约事件的回调逻辑
        registerUIChangeLiveDataCallBack();
        // 页面初始化数据
        initData();
        // 页面事件监听方法
        initViewObservable();
        // 注册 RxBus
        viewModel.registerRxBus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 解除 Messenger 注册
        Messenger.getDefault().unregister(viewModel);
        // 解除 ViewModel 生命周期感应
        getLifecycle().removeObserver(viewModel);

        if (viewModel != null) {
            viewModel.removeRxBus();
        }
        if (binding != null) {
            binding.unbind();
        }
    }

    /**
     * 注入绑定
     *
     * @param savedInstanceState
     */
    private void initViewDataBinding(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, initContentView(savedInstanceState));
        viewModelId = initVariableId();
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                modelClass = BaseViewModel.class;
            }
            viewModel = (VM) createViewModel(this, modelClass);
        }
        // 关联 bindingModel
        binding.setVariable(viewModelId, viewModel);
        // 让 ViewModel 拥有 View 的生命周期
        getLifecycle().addObserver(viewModel);
        // 注入 RxLifecycle 的生命周期
        viewModel.injectLifecycleProvider(this);
    }

    /**
     * 刷新布局
     */
    public void refreshLayout() {
        if (viewModel != null) {
            binding.setVariable(viewModelId, viewModel);
        }
    }

    /**
     * 注册 ViewModel 与 View 的契约 UI 回调事件
     */
    private void registerUIChangeLiveDataCallBack() {
        // 显示加载的对话框
        viewModel.getUiChangeLiveData().getShowDialogEvent().observe(this, (Observer<String>) this::showDialog);

        // 隐藏加载的对话框
        viewModel.getUiChangeLiveData().getDismissDialogEvent().observe(this, (Observer<Void>) v -> dismissDialog());

        // 进入一个新页面
        viewModel.getUiChangeLiveData().getStartActivityEvent().observe(this, (Observer<Map<String, Object>>) params -> {
            Class<?> clazz = (Class<?>) params.get(BaseViewModel.ParameterField.CLASS);
            Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
            startActivity(clazz, bundle);
        });

        // 跳入 ContainerActivity
        viewModel.getUiChangeLiveData().getStartContainerActivityEvent().observe(this, (Observer<Map<String, Object>>) params -> {
            String canonicalName = (String) params.get(BaseViewModel.ParameterField.CANONICAL_NAME);
            Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
            startContainerActivity(canonicalName, bundle);
        });

        // 关闭界面
        viewModel.getUiChangeLiveData().getFinishEvent().observe(this, v -> finish());

        // 关闭上一层界面
        viewModel.getUiChangeLiveData().getOnBackPressedEvent().observe(this, v -> onBackPressed());
    }

    /**
     * 跳转到另外一个 Activity
     *
     * @param clazz
     */
    public void startActivity(Class<?> clazz) {
        startActivity(new Intent(this, clazz));
    }

    /**
     * 跳转到另外一个 Activity，携带信息
     *
     * @param clazz
     * @param bundle
     */
    public void startActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转到容器页面
     *
     * @param canonicalName
     */
    public void startContainerActivity(String canonicalName) {
        startContainerActivity(canonicalName, null);
    }

    /**
     * 跳转到容器页面，携带信息
     *
     * @param canonicalName
     * @param bundle
     */
    public void startContainerActivity(String canonicalName, Bundle bundle) {
        Intent intent = new Intent(this, ContainerActivity.class);
        intent.putExtra(ContainerActivity.FRAGMENT, canonicalName);
        if (bundle != null) {
            intent.putExtra(ContainerActivity.BUNDLE, bundle);
        }
        startActivity(intent);
    }

    /**
     * 显示 Dialog
     *
     * @param title
     */
    public void showDialog(String title) {
        if (dialog != null) {
            dialog.show();
        } else {
            MaterialDialog.Builder builder = DialogUtils.showIndeterminateProgressDialog(this, title, true);
            dialog = builder.show();
        }
    }

    /**
     * 隐藏 Dialog
     */
    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 分割线
     * =====================================================================
     */

    @Override
    public void initParam() {
        if (isStatusBarEnabled()) {
            getStatusBarConfig().init();
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void initViewObservable() {

    }

    /**
     * 初始化根布局
     *
     * @param savedInstanceState
     * @return
     */
    public abstract int initContentView(Bundle savedInstanceState);

    /**
     * 初始化 ViewModel 的 Id
     *
     * @return
     */
    public abstract int initVariableId();

    /**
     * 初始化 ViewModel
     *
     * @return
     */
    public VM initViewModel() {
        return null;
    }

    /**
     * 创建 ViewModel
     *
     * @param activity
     * @param clazz
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(FragmentActivity activity, Class<T> clazz) {
        return ViewModelProviders.of(activity).get(clazz);
    }

    /**
     * 是否使用沉浸式状态栏
     * @return
     */
    protected boolean isStatusBarEnabled() {
        return true;
    }

    /**
     * 状态栏字体深色模式
     * @return
     */
    protected boolean isStatusBarDarkFont() {
        return true;
    }

    /**
     * 获取状态栏沉浸的配置对象
     */
    @NonNull
    public ImmersionBar getStatusBarConfig() {
        if (immersionBar == null) {
            immersionBar = createStatusBarConfig();
        }
        return immersionBar;
    }

    /**
     * 初始化沉浸式状态栏
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
