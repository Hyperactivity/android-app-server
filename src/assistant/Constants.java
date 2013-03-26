package assistant;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-03-13
 * Time: 14:07
 */
public abstract class Constants {

    public class Settings {
        public static final String CONTENT_LENGTH = "Content-length";
        public static final String PERSISTENCE_NAME ="hyperactivity_persistence";

        public static final int PORT = 12345;
    }

    public class Method{
        public static final String LOGIN = "login";
        public static final String REGISTER = "register";
    }

    public class Json {

    }

    public class Http {

    }

    public class Errors {
        public static final String BAD_HEADERS = "Bad headers";
        public static final String BAD_ID = "ID could not be converted to int";
    }
}
