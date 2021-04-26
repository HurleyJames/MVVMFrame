package ac.hurley.module_main.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;

import com.airbnb.lottie.LottieAnimationView;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;

import ac.hurley.library_base.base.view.BaseActivity;
import ac.hurley.module_main.R;


/**
 * <pre>
 *      @author hurley
 *      date    : 4/12/21 5:24 PM
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public class SplashActivity extends BaseActivity {

    private LottieAnimationView mLottieView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        mLottieView = findViewById(R.id.lav_splash_lottie);
        mLottieView.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mLottieView.removeAnimatorListener(this);
                jumpToMain();
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    @NonNull
    @Override
    protected ImmersionBar createStatusBarConfig() {
        return super.createStatusBarConfig().hideBar(BarHide.FLAG_HIDE_BAR);
    }

    @Override
    protected void initActivity() {
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            if (intent != null && intent.hasCategory(Intent.CATEGORY_LAUNCHER)
                    && Intent.ACTION_MAIN.equals(intent.getAction())) {
                finish();
                return;
            }
        }
        super.initActivity();
    }

    private void jumpToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
