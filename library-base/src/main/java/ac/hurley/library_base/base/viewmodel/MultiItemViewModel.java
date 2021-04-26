package ac.hurley.library_base.base.viewmodel;

import androidx.annotation.NonNull;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/12/21 10:14 PM
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public class MultiItemViewModel<VM extends BaseViewModel> extends ItemViewModel<VM> {

    protected Object multiType;

    public Object getItemType() {
        return multiType;
    }

    public void multiItemType(@NonNull Object multiType) {
        this.multiType = multiType;
    }

    public MultiItemViewModel(@NonNull VM viewModel) {
        super(viewModel);
    }
}
