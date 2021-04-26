package ac.hurley.module_home.ui.viewmodel;

import androidx.annotation.NonNull;

import ac.hurley.library_base.action.BindingAction;
import ac.hurley.library_base.action.BindingCommandAction;
import ac.hurley.library_base.base.viewmodel.ItemViewModel;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/16/21 5:08 PM
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public class ViewPagerItemViewModel extends ItemViewModel<HomeViewModel> {

    public String text;

    public ViewPagerItemViewModel(@NonNull HomeViewModel viewModel, String text) {
        super(viewModel);
        this.text = text;
    }

    public BindingCommandAction onClick = new BindingCommandAction(() -> {
        // 点击之后逻辑转到 adapter 中进行处理
        viewModel.itemClickEvent.setValue(text);
    });
}
