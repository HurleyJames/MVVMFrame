package ac.hurley.module_home.ui.adapter;

import android.view.ViewGroup;

import androidx.databinding.ViewDataBinding;

import ac.hurley.module_home.databinding.ItemViewpagerBinding;
import ac.hurley.module_home.ui.viewmodel.ViewPagerItemViewModel;
import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/16/21 8:17 PM
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public class ViewPagerBindingAdapter extends BindingViewPagerAdapter<ViewPagerItemViewModel> {

    @Override
    public void onBindBinding(ViewDataBinding binding, int variableId, int layoutRes, int position, ViewPagerItemViewModel item) {
        super.onBindBinding(binding, variableId, layoutRes, position, item);
        ItemViewpagerBinding viewpagerBinding = (ItemViewpagerBinding) binding;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
