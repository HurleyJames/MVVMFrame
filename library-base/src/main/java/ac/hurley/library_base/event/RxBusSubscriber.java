package ac.hurley.library_base.event;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/14/21 4:46 PM
 *      github  : https://github.com/HurleyJames
 *      desc    : 为 RxBus 使用的 Subscriber，主要提供 next 事件的 try | catch
 * </pre>
 */
public abstract class RxBusSubscriber<T> extends DisposableObserver<T> {

    @Override
    public void onNext(@NonNull T t) {
        try {
            onEvent(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onError(@NonNull Throwable e) {
        e.printStackTrace();
    }

    protected abstract void onEvent(T t);
}
