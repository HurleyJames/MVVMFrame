package ac.hurley.module_home.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.blankj.utilcode.util.ToastUtils;

import ac.hurley.library_base.action.BindingCommandAction;
import ac.hurley.library_base.action.BindingConsumerAction;
import ac.hurley.library_base.base.viewmodel.BaseViewModel;
import ac.hurley.library_base.event.SingleLiveEvent;
import ac.hurley.module_home.BR;
import ac.hurley.module_home.R;
import ac.hurley.module_home.ui.adapter.ViewPagerBindingAdapter;
import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/16/21 5:06 PM
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public class HomeViewModel extends BaseViewModel {

    public SingleLiveEvent<String> itemClickEvent = new SingleLiveEvent<>();

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public void addPage() {
        items.clear();
        // 模拟了 3 个 ViewPager 的页面
        for (int i = 1; i <= 3; i++) {
            ViewPagerItemViewModel itemViewModel = new ViewPagerItemViewModel(this, "第" + i + "个页面");
            items.add(itemViewModel);
        }
    }

    /**
     * 给 ViewPager 添加 ObservableList
     */
    public ObservableList<ViewPagerItemViewModel> items = new ObservableArrayList<>();

    /**
     * 给 ViewPager 添加 ItemBinding
     */
    public ItemBinding<ViewPagerItemViewModel> itemBinding = ItemBinding.of(BR.viewModel,
            R.layout.item_viewpager);

    /**
     * 给 ViewPager 添加 PageTitle
     */
    public final BindingViewPagerAdapter.PageTitles<ViewPagerItemViewModel> pageTitles = ((position, item) -> {
        return "条目" + position;
    });

    /**
     * 给 ViewPager 添加 Adapter，用自定义的 Adapter 继承 BindingViewPagerAdapter，重写 onBinding 方法
     */
    public final ViewPagerBindingAdapter adapter = new ViewPagerBindingAdapter();

    /**
     * ViewPager 切换监听
     */
    public BindingCommandAction<Integer> onPageSelectedCommand = new BindingCommandAction<>(new BindingConsumerAction<Integer>() {
        @Override
        public void call(Integer integer) {
            ToastUtils.showShort("ViewPager 切换：" + integer);
        }

        @Override
        public Integer call() {
            return null;
        }
    });
}
