package assistant;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-03-13
 * Time: 14:07
 */
public abstract class Constants {

    public class General {

        public static final String PERSISTENCE_NAME ="hyperactivity_persistence";


    }

    public class Method{
        public static final String LOGIN = "login";
        public static final String REGISTER = "register";
        public static final String GET_PROFILE = "get_profile";
        public static final String UPDATE_PROFILE = "update_profile";
    }

    public class Param{

        public static final String TOKEN = "token";
        public static final String SUCCESS = "success";
        public static final String VALUE = "value";
        public static final String ACCOUNT = "account";
        public static final String FIRST_LOGIN = "first_login";
    }

    public class Json {

    }

    public class Http {
        public static final int PORT = 12345;
        public static final String CONTENT_LENGTH = "Content-length";

    }
    public class Query {

        public static final String FACEBOOK_ID = "facebook_id";
    }

    public class Database{
        public static final int NO_LIMIT_PER_DAY = 0;
    }

    public class Errors {
        public static final String BAD_HEADERS = "Bad headers";
        public static final String BAD_ID = "ID could not be converted to int";
        public static final String METHOD_NOT_FOUND = "Method not found";
        public static final String PARAM_NOT_FOUND = "Param not found";
    }
}
