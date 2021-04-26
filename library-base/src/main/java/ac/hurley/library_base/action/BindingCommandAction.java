package ac.hurley.library_base.action;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/16/21 7:55 PM
 *      github  : https://github.com/HurleyJames
 *      desc    : 执行的命令回调, 用于 ViewModel与xml之间的数据绑定
 * </pre>
 */
public class BindingCommandAction<T> {

    private BindingAction execute;
    private BindingConsumerAction<T> consumer;
    private BindingConsumerAction<Boolean> canExecute0;

    public BindingCommandAction(BindingAction execute) {
        this.execute = execute;
    }

    /**
     * @param execute 带泛型参数的命令绑定
     */
    public BindingCommandAction(BindingConsumerAction<T> execute) {
        this.consumer = execute;
    }

    /**
     * @param execute     触发命令
     * @param canExecute0 true则执行,反之不执行
     */
    public BindingCommandAction(BindingAction execute, BindingConsumerAction<Boolean> canExecute0) {
        this.execute = execute;
        this.canExecute0 = canExecute0;
    }

    /**
     * @param execute     带泛型参数触发命令
     * @param canExecute0 true则执行,反之不执行
     */
    public BindingCommandAction(BindingConsumerAction<T> execute, BindingConsumerAction<Boolean> canExecute0) {
        this.consumer = execute;
        this.canExecute0 = canExecute0;
    }

    /**
     * 执行BindingAction命令
     */
    public void execute() {
        if (execute != null && canExecute0()) {
            execute.call();
        }
    }

    /**
     * 执行带泛型参数的命令
     *
     * @param parameter 泛型参数
     */
    public void execute(T parameter) {
        if (consumer != null && canExecute0()) {
            consumer.call(parameter);
        }
    }

    /**
     * 是否需要执行
     *
     * @return true则执行, 反之不执行
     */
    private boolean canExecute0() {
        if (canExecute0 == null) {
            return true;
        }
        return canExecute0.call();
    }
}
