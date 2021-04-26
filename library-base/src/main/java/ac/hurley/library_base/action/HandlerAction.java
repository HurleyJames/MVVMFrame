package ac.hurley.library_base.action;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/15/21 1:41 PM
 *      github  : https://github.com/HurleyJames
 *      desc    : Handler 意图处理
 * </pre>
 */
public interface HandlerAction {

    Handler handler = new Handler(Looper.getMainLooper());

    /**
     * 获取 Handler
     *
     * @return
     */
    default Handler getHandler() {
        return handler;
    }

    /**
     * 延迟执行
     *
     * @param runnable
     * @return
     */
    default boolean post(Runnable runnable) {
        return postDelayed(runnable, 0);
    }

    /**
     * 延迟一段时间执行
     *
     * @param runnable
     * @param delayMillis
     * @return
     */
    default boolean postDelayed(Runnable runnable, long delayMillis) {
        if (delayMillis < 0) {
            delayMillis = 0;
        }
        return postAtTime(runnable, SystemClock.uptimeMillis() + delayMillis);
    }

    /**
     * 在指定的时间内执行
     *
     * @param runnable
     * @param uptimeMillis
     * @return
     */
    default boolean postAtTime(Runnable runnable, long uptimeMillis) {
        return handler.postAtTime(runnable, this, uptimeMillis);
    }

    /**
     * 移除单个消息回调
     *
     * @param runnable
     */
    default void removeCallback(Runnable runnable) {
        handler.removeCallbacks(runnable);
    }

    /**
     * 移除全部消息回调
     */
    default void removeCallbacks() {
        // 移除和当前对象相关的消息回调
        handler.removeCallbacksAndMessages(this);
    }
}
