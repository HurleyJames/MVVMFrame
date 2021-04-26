package ac.hurley.library_base.base.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.afollestad.materialdialogs.MaterialDialog;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import ac.hurley.library_base.base.viewmodel.BaseViewModel;
import ac.hurley.library_base.util.DialogUtils;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/13/21 9:25 PM
 *      github  : https://github.com/HurleyJames
 *      desc    : MVVM 类型的 Fragment 基类
 * </pre>
 */
public abstract class BaseMvvmFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends RxFragment implements IBaseView {

    protected V binding;
    protected VM viewModel;
    private int viewModelId;
    private MaterialDialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParam();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, initContentView(inflater, container, savedInstanceState), container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // TODO

        if (viewModel != null) {
            viewModel.removeRxBus();
        }
        if (binding != null) {
            binding.unbind();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewDataBinding();
        registerUIChangeLiveDataCallBack();
        initData();
        // 页面事件监听方法
        initViewObservable();
        // 注册 RxBus
        viewModel.registerRxBus();
    }

    /**
     * 注入绑定
     */
    private void initViewDataBinding() {
        viewModelId = initVariableId();
        viewModel = initViewModel();
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                // 如果没有指定泛型参数，则默认使用 BaseViewModel
                modelClass = BaseViewModel.class;
            }
            viewModel = (VM) createViewModel(this, modelClass);
        }
        binding.setVariable(viewModelId, viewModel);
        // 让 ViewModel 拥有 View 的生命周期感应
        getLifecycle().addObserver(viewModel);
        // 注入 RxLifecycle 生命周期
        viewModel.injectLifecycleProvider(this);
    }

    /**
     * 注册 ViewModel 与 View 的契约 UI 回调事件
     */
    protected void registerUIChangeLiveDataCallBack() {
        // 显示加载的对话框
        viewModel.getUiChangeLiveData().getShowDialogEvent().observe(this, (Observer<String>) title -> showDialog(title));

        // 隐藏加载的对话框
        viewModel.getUiChangeLiveData().getDismissDialogEvent().observe(this, (Observer<Void>) v -> dismissDialog());

        // 跳转到新页面
        viewModel.getUiChangeLiveData().getStartActivityEvent().observe(this, (Observer<Map<String, Object>>) params -> {
            Class<?> clazz = (Class<?>) params.get(BaseViewModel.ParameterField.CLASS);
            Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
            startActivity(clazz, bundle);
        });

        // 跳转到容器页面
        viewModel.getUiChangeLiveData().getStartContainerActivityEvent().observe(this, (Observer<Map<String, Object>>) params -> {
            String canonicalName = (String) params.get(BaseViewModel.ParameterField.CANONICAL_NAME);
            Bundle bundle = (Bundle) params.get(BaseViewModel.ParameterField.BUNDLE);
            startContainerActivity(canonicalName, bundle);
        });

        // 关闭上一层页面
        viewModel.getUiChangeLiveData().getOnBackPressedEvent().observe(this, (Observer<Void>) v -> getActivity().onBackPressed());
    }

    /**
     * 显示 Dialog
     *
     * @param title
     */
    public void showDialog(String title) {
        if (dialog != null) {
            dialog = dialog.getBuilder().title(title).build();
            dialog.show();
        } else {
            MaterialDialog.Builder builder = DialogUtils.showIndeterminateProgressDialog(getActivity(), title, true);
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
     * 跳转页面
     *
     * @param clazz
     */
    public void startActivity(Class<?> clazz) {
        startActivity(new Intent(getContext(), clazz));
    }

    /**
     * 跳转页面，携带信息
     *
     * @param clazz
     * @param bundle
     */
    public void startActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clazz);
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
     * 跳转到容器页面
     *
     * @param canonicalName
     * @param bundle
     */
    public void startContainerActivity(String canonicalName, Bundle bundle) {
        Intent intent = new Intent(getContext(), ContainerActivity.class);
        intent.putExtra(ContainerActivity.FRAGMENT, canonicalName);
        if (bundle != null) {
            intent.putExtra(ContainerActivity.BUNDLE, bundle);
        }
        startActivity(intent);
    }

    /**
     * 分割线
     * =====================================================================
     */

    /**
     * 刷新布局
     */
    public void refreshLayout() {
        if (viewModel != null) {
            binding.setVariable(viewModelId, viewModel);
        }
    }

    @Override
    public void initParam() {

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
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public abstract int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

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
     * 是否回退到上一页面
     *
     * @return
     */
    public boolean isBackPressed() {
        return false;
    }

    /**
     * 创建 ViewModel
     *
     * @param fragment
     * @param clazz
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(Fragment fragment, Class<T> clazz) {
        return ViewModelProviders.of(fragment).get(clazz);
    }

}
