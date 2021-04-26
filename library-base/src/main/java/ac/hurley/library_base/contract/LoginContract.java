package ac.hurley.library_base.contract;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/12/21 2:29 PM
 *      github  : https://github.com/HurleyJames
 *      desc    : 登录完成后，组件间的通信的契约类
 * </pre>
 */
public class LoginContract {

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
