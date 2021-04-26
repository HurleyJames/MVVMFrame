package ac.hurley.library_base.action;

import android.view.View;

import androidx.annotation.IdRes;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/15/21 2:40 PM
 *      github  : https://github.com/HurleyJames
 *      desc    : 点击事件意图
 * </pre>
 */
public interface ClickAction extends View.OnClickListener {

    <V extends View> V findViewById(@IdRes int id);

    default void setOnClickListener(@IdRes int... ids) {
        setOnClickListener(this, ids);
    }

    default void setOnClickListener(View.OnClickListener listener, @IdRes int... ids) {
        for (int id : ids) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    default void setClickListener(View... views) {
        setOnClickListener(this, views);
    }

    default void setOnClickListener(View.OnClickListener listener, View... views) {
        for (View view : views) {
            view.setOnClickListener(listener);
        }
    }

    @Override
    default void onClick(View v) {
        // 让子类来具体实现逻辑
    }
}
