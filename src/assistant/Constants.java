package assistant;

import sun.swing.plaf.synth.DefaultSynthStyle;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-03-13
 * Time: 14:07
 */
public abstract class Constants {

    public abstract static class General {

        public static final String PERSISTENCE_NAME ="hyperactivity_persistence";
        public static final String SERVER_OS = "Linux";
        public static final String OS_PROPERTY = "os.name";
        public static final boolean IS_DEVELOPMENT_SERVER = (System.getProperty(OS_PROPERTY).equals(SERVER_OS)) ? false : true;
        public static final String LOCAL_SERVER_IP = "192.168.1.101";
        public static final String EXTERNAL_SERVER_IP = "89.253.85.33";
        public static final String HIBERNATE_PERSISTENCE_CONNECTION_NAME = "hibernate.connection.url";
        public static final String HIBERNATE_PERSISTENCE_CONNECTION_DEVELOPMENT_VALUE = "jdbc:mysql://89.253.85.33/Hyperactivity?autoReconnect=true";
        public static final String HIBERNATE_PERSISTENCE_CONNECTION_VALUE = "jdbc:mysql://127.0.0.1/Hyperactivity?autoReconnect=true";
        public static final String APP_ID = "534043489970951";
    }

    public abstract class Method{
        public static final String LOGIN = "login";
        public static final String GET_ACCOUNT = "get_account";
        public static final String UPDATE_ACCOUNT = "update_account";
        public static final String GET_FORUM_CONTENT = "get_forum_content";
        public static final String GET_CATEGORY_CONTENT = "get_category_content";
        public static final String GET_THREAD_CONTENT = "get_thread_content";
        public static final String CREATE_THREAD = "create_thread";
        public static final String CREATE_REPLY = "create_reply";

        public static final String CREATE_CATEGORY = "create_category";
        public static final String MODIFY_CATEGORY = "modify_category";
        public static final String DELETE_CATEGORY = "delete_category";

        public static final String MODIFY_THREAD = "modify_thread";
        public static final String DELETE_THREAD = "delete_thread";
        public static final String DELETE_REPLY = "delete_reply";
        public static final String MODIFY_REPLY = "modify_reply";
        public static final String THUMB_UP = "thumb_up";
        public static final String GET_LATEST_THREADS = "get_latest_threads";
        public static final String CREATE_LINKED_THREAD = "create_linked_thread";
        public static final String MODIFY_LINKED_THREAD = "modify_linked_thread";
        public static final String DELETE_LINKED_THREAD = "delete_linked_thread";
        public static final String CREATE_NOTE = "create_note";
        public static final String MODIFY_NOTE = "modify_note";
        public static final String DELETE_NOTE = "delete_note";
        public static final String REGISTER = "register";
    }

    public abstract class Param{
        public abstract class Name{
            public static final String TOKEN = "token";
            public static final String ACCOUNT_ID = "account_id";
            public static final String ACCOUNT = "account";
            public static final String DESCRIPTION = "description";
            public static final String SHOW_BIRTH_DATE = "show_birth_date";
            public static final String AVATAR = "avatar";
            public static final String TYPE = "type";

            public static final String CATEGORIES = "categories";
            public static final String THREAD_ID = "thread_id";
            public static final String TEXT = "text";
            public static final String SORT_TYPE = "sort_type";
            public static final String HEADLINE = "headline";
            public static final String CATEGORY_ID = "category_id";
            public static final String CATEGORY = "category";
            public static final String REPLY = "reply";
            public static final String THREADS = "threads";
            public static final String REPLIES = "replies";
            public static final String REPLY_ID = "reply_id";
            public static final String COLOR_CODE = "color_code";
            public static final String THREAD = "thread";
            public static final String THUMBS_UP = "thumbs_up";
            public static final String LIMIT = "limit";
            public static final String PRIVATE_CATEGORY_ID = "private_category_id";
            public static final String LINKED_THREAD = "linked_thread";
            public static final String LINKED_THREAD_ID = "linked_thread_id";
            public static final String NOTE = "note";
            public static final String NOTE_ID = "note_id";
            public static final String USERNAME = "username";
        }
        public abstract class Status{
            public static final String STATUS = "status";
            public static final String REGISTER = "register";
            public static final String ACCOUNT_NOT_FOUND = "account_not_found";
            public static final String SUCCESS = "success";

            public static final String OBJECT_NOT_FOUND = "object_not_found";
            public static final String USERNAME_TAKEN = "username taken";
        }

        public abstract class Value {
            public static final String PUBLIC = "public";
            public static final String PRIVATE = "private";
        }
    }

    public abstract class Http {
        public static final int PORT = 12345;
        public static final String CONTENT_LENGTH = "Content-length";

    }
    public class Query {

        public static final String FACEBOOK_ID = "facebook_id";
        public static final String USERNAME = "username";
    }

    public class Database{
        public static final int NO_LIMIT_PER_DAY = 0;
        public static final boolean DEFAULT_SHOW_BIRTH_DATE = false;
        public static final boolean DEFAULT_USE_DEFAULT_COLORS = true;
        public static final int DEFAULT_COLOR_CODE = 0;
        public static final int DEFAULT_LATEST_THREADS_LIMIT = 10;
    }

    public class Errors {
        public static final String BAD_HEADERS = "Bad headers";
        public static final String BAD_ID = "ID could not be converted to int";
        public static final String METHOD_NOT_FOUND = "Method not found";
        public static final String PARAM_NOT_FOUND = "A needed param is not found";
        public static final String PARAM_NOT_ALLOWED = "Some given params are not allowed";
        public static final String PARAM_VALUE_NOT_ALLOWED = "The given param value is not allowed";
        public static final String ACTION_NOT_ALLOWED = "Action is not allowed";
        public static final String TOKEN_MISSING = "Facebook token is missing";
    }
}
