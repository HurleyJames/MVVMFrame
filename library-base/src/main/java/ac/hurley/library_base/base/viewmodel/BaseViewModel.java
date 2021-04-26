package ac.hurley.library_base.base.viewmodel;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.trello.rxlifecycle2.LifecycleProvider;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import ac.hurley.library_base.R;
import ac.hurley.library_base.base.model.BaseModel;
import ac.hurley.library_base.event.SingleLiveEvent;
import io.reactivex.disposables.Disposable;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/12/21 8:53 PM
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public class BaseViewModel<M extends BaseModel> extends AndroidViewModel implements IBaseViewModel {

    protected M model;

    private UIChangeLiveData uiChangeLiveData;

    // 弱引用持有
    private WeakReference<LifecycleProvider> lifecycle;

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public BaseViewModel(@NonNull Application application, M model) {
        super(application);
        this.model = model;
    }

    protected void addSubscribe(Disposable disposable) {
        model.addSubscribe(disposable);
    }

    /**
     * 进入 RxLifecycle 生命周期
     *
     * @param lifecycle
     */
    public void injectLifecycleProvider(LifecycleProvider lifecycle) {
        this.lifecycle = new WeakReference<>(lifecycle);
    }

    public LifecycleProvider getLifecycleProvider() {
        return lifecycle.get();
    }

    public UIChangeLiveData getUiChangeLiveData() {
        if (uiChangeLiveData == null) {
            uiChangeLiveData = new UIChangeLiveData();
        }
        return uiChangeLiveData;
    }

    public void showDialog() {
        showDialog(getApplication().getString(R.string.please_wait));
    }

    public void showDialog(String title) {
        uiChangeLiveData.showDialogEvent.postValue(title);
    }

    public void dismissDialog() {
        uiChangeLiveData.dismissDialogEvent.call();
    }

    /**
     * 跳转页面
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 跳转页面，携带信息
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.CLASS, clz);
        if (bundle != null) {
            params.put(ParameterField.BUNDLE, bundle);
        }
        uiChangeLiveData.startActivityEvent.postValue(params);
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
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.CANONICAL_NAME, canonicalName);
        if (bundle != null) {
            params.put(ParameterField.BUNDLE, bundle);
        }
        uiChangeLiveData.startContainerActivityEvent.postValue(params);
    }

    /**
     * 关闭当前界面
     */
    public void finish() {
        uiChangeLiveData.finishEvent.call();
    }

    /**
     * 返回上一层
     */
    public void onBackPressed() {
        uiChangeLiveData.onBackPressedEvent.call();
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void registerRxBus() {

    }

    @Override
    public void removeRxBus() {

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (model != null) {
            model.onCleared();
        }
    }

    public final class UIChangeLiveData extends SingleLiveEvent {
        private SingleLiveEvent<String> showDialogEvent;
        private SingleLiveEvent<Void> dismissDialogEvent;
        private SingleLiveEvent<Map<String, Object>> startActivityEvent;
        private SingleLiveEvent<Map<String, Object>> startContainerActivityEvent;
        private SingleLiveEvent<Void> finishEvent;
        private SingleLiveEvent<Void> onBackPressedEvent;

        public SingleLiveEvent<String> getShowDialogEvent() {
            return showDialogEvent = createLiveData(showDialogEvent);
        }

        public SingleLiveEvent<Void> getDismissDialogEvent() {
            return dismissDialogEvent = createLiveData(dismissDialogEvent);
        }

        public SingleLiveEvent<Map<String, Object>> getStartActivityEvent() {
            return startActivityEvent = createLiveData(startActivityEvent);
        }

        public SingleLiveEvent<Map<String, Object>> getStartContainerActivityEvent() {
            return startContainerActivityEvent = createLiveData(startContainerActivityEvent);
        }

        public SingleLiveEvent<Void> getFinishEvent() {
            return finishEvent = createLiveData(finishEvent);
        }

        public SingleLiveEvent<Void> getOnBackPressedEvent() {
            return onBackPressedEvent = createLiveData(onBackPressedEvent);
        }

        private SingleLiveEvent createLiveData(SingleLiveEvent liveEvent) {
            if (liveEvent == null) {
                liveEvent = new SingleLiveEvent();
            }
            return liveEvent;
        }

        @Override
        public void observe(LifecycleOwner owner, Observer observer) {
            super.observe(owner, observer);
        }
    }

    public static final class ParameterField {
        public static String CLASS = "CLASS";
        public static String CANONICAL_NAME = "CANONICAL_NAME";
        public static String BUNDLE = "BUNDLE";
    }
}
