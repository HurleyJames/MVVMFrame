package ac.hurley.library_base.base.view;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/12/21 5:31 PM
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public interface IBaseView {

    /**
     * 初始化界面传递参数
     */
    void initParam();

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 初始化界面观察者的监听
     */
    void initViewObservable();
}
