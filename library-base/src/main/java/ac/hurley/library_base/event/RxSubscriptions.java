package ac.hurley.library_base.event;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/14/21 5:04 PM
 *      github  : https://github.com/HurleyJames
 *      desc    : 管理 CompositeSubscription
 * </pre>
 */
public class RxSubscriptions {

    private static CompositeDisposable mSubscriptions = new CompositeDisposable();

    public static boolean isDisposed() {
        return mSubscriptions.isDisposed();
    }

    public static void add(Disposable s) {
        if (s != null) {
            mSubscriptions.add(s);
        }
    }

    public static void remove(Disposable s) {
        if (s != null) {
            mSubscriptions.remove(s);
        }
    }

    public static void clear() {
        mSubscriptions.clear();
    }

    public static void dispose() {
        mSubscriptions.dispose();
    }
}
