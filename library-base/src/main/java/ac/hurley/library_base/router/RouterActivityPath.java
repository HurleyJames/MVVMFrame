package ac.hurley.library_base.router;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/12/21 2:22 PM
 *      github  : https://github.com/HurleyJames
 *      desc    : 用于组件开发中，ARouter 单 Activity 跳转的统一路径注册
 * </pre>
 */
public class RouterActivityPath {

    public static class Main {
        public static final String MAIN = "/main";

        public static final String PAGER_HOME = MAIN + "/home";
    }

    public static class Login {
        private static final String LOGIN = "/login";
        public static final String PAGER_LOGIN = LOGIN + "/login";
    }

    public static class User {
        private static final String USER = "/user";
        public static final String PAGER_USER_DETAIL = USER + "/userDetail";
    }
}
