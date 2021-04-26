package ac.hurley.library_base.action;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/16/21 2:00 PM
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public interface BindingConsumerAction<T> {

    void call(T t);

    T call();
}
