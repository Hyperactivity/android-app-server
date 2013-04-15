package assistant.pair;

import org.hibernate.loader.custom.Return;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-15
 * Time: 14:26
 */
public class NullableExtendedParam extends Pair{

    public NullableExtendedParam(String param, boolean nullable){
        left = param;
        right = nullable;
    }

    public String getParam(){
        return (String) left;
    }

    public boolean isNullable(){
        return (Boolean) right;
    }
}
