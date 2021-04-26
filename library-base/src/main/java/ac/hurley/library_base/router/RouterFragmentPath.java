package ac.hurley.library_base.router;

/**
 * <pre>
 *      @author hurley
 *      date    : 4/15/21 10:31 PM
 *      github  : https://github.com/HurleyJames
 *      desc    : 用于组件开发中，ARouter 多 Fragment 跳转的统一路径注册
 * </pre>
 */
public class RouterFragmentPath {

    public static class Home {
        private static final String HOME = "/home";
        public static final String PAGER_HOME = HOME + "/home";
    }

    public static class Deliver {
        private static final String DELIVER = "/deliver";
        public static final String PAGER_DELIVER = DELIVER + "/deliver";
    }

    public static class User {
        private static final String USER = "/user";
        public static final String PAGER_ME = USER + "/me";
    }
}
