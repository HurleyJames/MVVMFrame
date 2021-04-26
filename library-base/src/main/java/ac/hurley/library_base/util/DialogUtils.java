package ac.hurley.library_base.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.KeyEvent;

import com.afollestad.materialdialogs.MaterialDialog;

import ac.hurley.library_base.R;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/13/21 2:22 PM
 *      github  : https://github.com/HurleyJames
 *      desc    : Dialog 工具类
 * </pre>
 */
public class DialogUtils {

    public static MaterialDialog.Builder showIndeterminateProgressDialog(Context context, String content, boolean horizontal) {
        @SuppressLint("ResourceAsColor") MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title(content)
                .progress(true, 0)
                .progressIndeterminateStyle(horizontal)
                .canceledOnTouchOutside(false)
                .backgroundColor(R.color.white)
                .keyListener(((dialog, keyCode, event) -> {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {

                        }
                    }
                    return false;
                }));
        return builder;
    }
}
