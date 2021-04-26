package ac.hurley.library_base.base.viewmodel;

import androidx.annotation.NonNull;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/12/21 9:49 PM
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public class ItemViewModel<VM extends BaseViewModel> {

    protected VM viewModel;

    public ItemViewModel(@NonNull VM viewModel) {
        this.viewModel = viewModel;
    }
}
