package ac.hurley.library_base.base.model;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/12/21 8:44 PM
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public class BaseModel implements IModel {

    /**
     * 管理 RxJava，主要针对 R小Java 异步操作造成的内存泄漏
     */
    private CompositeDisposable mCompositeDisposable;

    public BaseModel() {
        mCompositeDisposable = new CompositeDisposable();
    }

    public void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }


    @Override
    public void onCleared() {
        // ViewModel 销毁时会执行，同时取消所有的异步任务
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }
}
