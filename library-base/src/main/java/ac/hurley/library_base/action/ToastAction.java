package ac.hurley.library_base.action;

import androidx.annotation.StringRes;

import com.blankj.utilcode.util.ToastUtils;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/16/21 3:03 PM
 *      github  : https://github.com/HurleyJames
 *      desc    : 吐司意图
 * </pre>
 */
public interface ToastAction {

    default void toast(CharSequence text) {
        ToastUtils.showShort(text);
    }

    default void toast(@StringRes int id) {
        ToastUtils.showShort(id);
    }

    default void toast(@StringRes int id, Object object) {
        ToastUtils.showShort(id, object);
    }
}
