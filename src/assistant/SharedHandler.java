package assistant;

import assistant.pair.NullableExtendedParam;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.server.MessageContext;
import com.thetransactioncompany.jsonrpc2.server.RequestHandler;
import core.Engine;
import net.minidev.json.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.persistence.EntityManager;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-03-25
 * Time: 14:26
 */
public abstract class SharedHandler implements RequestHandler{
    public EntityManager em;

    public int accountId;
    public String method;
    public JSONObject responseParams;
    public JSONRPC2Response response;

    /**
     * Creates the entity factory that produces the entity managers
     */
    public SharedHandler(){
    }

    /**
     *
     * @param serializedObject
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    //TODO Check if classType is not needed to be used in some way
    protected static final <T>  T deSerialize(java.lang.Class<T> classType, String serializedObject) throws IOException,
            ClassNotFoundException {
        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte [] data = base64Decoder.decodeBuffer(serializedObject);
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream( data ) );
        Object o = ois.readObject();
        ois.close();

        return (T)o;
    }

    /**
     *
     * @param object
     * @return
     * @throws IOException
     */
    protected static final String serialize(Serializable object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( object );
        oos.close();
        BASE64Encoder base64Encoder =  new BASE64Encoder();
        return new String( base64Encoder.encode(baos.toByteArray()) );
    }


    @Override
    public abstract String[] handledRequests();

    @Override
    public final JSONRPC2Response process(JSONRPC2Request jsonrpc2Request, MessageContext messageContext) { //TODO find out what MessageContext is exactly and how we can use it

        try{
            initializeData(jsonrpc2Request);
            process(jsonrpc2Request.getNamedParams());
            validateResponse();
            return response;
        }catch (JSONRPC2Error e){
            return new JSONRPC2Response(e, accountId);
        }catch (Exception e){
            return new JSONRPC2Response(JSONRPC2Error.INTERNAL_ERROR.appendMessage(" || Exception: " + e.toString()), accountId);
        }

    }

    private static void validateResponse() {
        //TODO: check that value and accountId is present in the response. And that value is in "result"
    }

    private void initializeData(JSONRPC2Request jsonrpc2Request) throws Exception {
        em = Engine.entityManagerFactory.createEntityManager();
        responseParams = new JSONObject();
        method = jsonrpc2Request.getMethod();

        try{
            accountId = Integer.parseInt(jsonrpc2Request.getID().toString());
        }catch(NumberFormatException e){
            throwJSONRPC2Error(JSONRPC2Error.INVALID_REQUEST, Constants.Errors.BAD_ID);
        }
        response = new JSONRPC2Response(responseParams, accountId);
    }

    protected abstract void process(Map<String, Object> jsonrpc2Params) throws Exception;

    /**
     * Throws and JSONRPC2Error with messages to append
     * @param e             The error to throw
     * @param msgToAppend   The messages to append to the error
     * @throws Exception
     */
    public void throwJSONRPC2Error(JSONRPC2Error e, String... msgToAppend) throws Exception {
        for(String msg: msgToAppend){
            e.appendMessage( " || " + msg);
        }
        throw e;
    }

    /**
     * Makes sure that certain parameters are present in the request, no other parameters are allowed.
     * @param jsonrpc2Params    The params in the request
     * @param params            The params that must be present
     * @return                  c
     * @throws Exception        If the map does not contain a key or the key is null. Also if there are more params then the allowed
     */
    public Map<String, Object> getParams(Map<String, Object> jsonrpc2Params, String... params) throws Exception {
        NullableExtendedParam[] np = new NullableExtendedParam[params.length];
        for(int i = 0; i < params.length; i ++){
            String param = params[i];
            np[i] = new NullableExtendedParam(param, false);
        }
        return getParams(jsonrpc2Params, np);
    }


    /**
     * Same as getParams(Map<>, String...) but allows certain params to be null or not present.
     * @param jsonrpc2Params    The params in the request
     * @param params            Contains the param and a boolean stating if the param can be null or not
     * @return                  The params in the request
     * @throws Exception        If the map does not contain a key or the key is null, If the key can´t be null that is. Also if there are more params then the allowed
     */
    //TODO: Send what class each object should so we do not need to cast the objects retrieved
    public Map<String, Object> getParams(Map<String, Object> jsonrpc2Params, NullableExtendedParam... params) throws Exception {
        Map<String, Object> returnParams= new HashMap<String, Object>();
        Object value;
        String param;
        boolean allowedToBeNull;
        for(NullableExtendedParam extendedParam: params){
            param = extendedParam.getParam();
            value = jsonrpc2Params.get(param);
            allowedToBeNull = extendedParam.isNullable();
            if(value == null && !allowedToBeNull){
                throwJSONRPC2Error(JSONRPC2Error.INVALID_PARAMS, Constants.Errors.PARAM_NOT_FOUND, param);
            }
            if(Long.class.isInstance(value)){
                value = (int)(long)(Long)value; //Needed to avoid using Long
            }
            returnParams.put(param, value);
            jsonrpc2Params.remove(param);
        }
        if(!jsonrpc2Params.isEmpty()){
            String debugInfo = "Bad params >> ";
            for(Map.Entry entry: jsonrpc2Params.entrySet()){
                debugInfo += "Key: " + entry.getKey() + "  Value: " + entry.getValue() + " | ";
            }
            throwJSONRPC2Error(JSONRPC2Error.INVALID_PARAMS, Constants.Errors.PARAM_NOT_ALLOWED, debugInfo);
        }
        return returnParams;
    }


    protected void persistObjects(Object... objects){
        em.getTransaction().begin();
        for(Object o: objects){
            em.persist(o);
        }
        em.getTransaction().commit(); //TODO: Check if it is important to close EntityManager at some point
    }

    protected void refreshObjects(Object... objects) {
        em.getTransaction().begin();
        for(Object o: objects){
            em.refresh(o);
        }

        em.getTransaction().commit(); //TODO: Check if it is important to close EntityManager at some point
    }

//    enum ReplyComparator implements Comparator<Reply> {
//
//        TIME_SORT {
//            public int compare(Reply r1, Reply r2) {
//                return r1.getTime().compareTo(r2.getTime());
//            }},
//
//        VOTE_SORT {
//            public int compare(Reply r1, Reply r2) {
//                return r1().compareTo(r2.getFullName());
//            }};
//
//        public static Comparator<Reply> decending(final Comparator<Reply> other) {
//            return new Comparator<Reply>() {
//                public int compare(Reply r1, Reply r2) {
//                    return -1 * other.compare(r1, r2);
//                }
//            };
//        }
//
//        public static Comparator<Reply> getComparator(final ReplyComparator... multipleOptions) {
//            return new Comparator<Reply>() {
//                public int compare(Reply r1, Reply r2) {
//                    for (ReplyComparator option : multipleOptions) {
//                        int result = option.compare(r1, r2);
//                        if (result != 0) {
//                            return result;
//                        }
//                    }
//                    return 0;
//                }
//            };
//        }
//    }
//
//
//
//    protected class DescendingComparator implements Comparator<Reply> {
//
//        @Override
//        public int compare(Reply reply1, Reply reply2) {
//            String p1Name;
//            String p2Name;
//            if(reply1.getTeam() != 0){
//                p1Score = engine.getPlayerManager().getTeamScore(reply1.getTeam());
//                p1Name = Config.Colors.MAINCOLORSNAME[reply1.getTeam()-1];
//            } else{
//                p1Score = reply1.getScore();
//                p1Name = reply1.getName();
//            }
//            if(reply2.getTeam() != 0){
//                p2Score = engine.getPlayerManager().getTeamScore(reply2.getTeam());
//                p2Name = Config.Colors.MAINCOLORSNAME[reply2.getTeam()-1];
//            } else{
//                p2Score = reply2.getScore();
//                p2Name = reply2.getName();
//            }
//
//            if (p1Score < p2Score) {
//                return 1;
//            } else if (p1Score > p2Score){
//                return -1;
//            }
//            if(p1Name.compareTo(p2Name) < 0){
//                return -1;
//            }
//            return 1;
//        }
//
//    }

}


