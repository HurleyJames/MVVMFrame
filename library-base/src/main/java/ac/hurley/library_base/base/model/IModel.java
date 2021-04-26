package ac.hurley.library_base.base.model;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/12/21 8:37 PM
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public interface IModel {

    /**
     * ViewModel 销毁时清除 Model，所以 Model 与 ViewModel 共存亡
     */
    void onCleared();
}
