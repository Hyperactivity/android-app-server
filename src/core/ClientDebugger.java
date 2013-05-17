package core;

import assistant.Constants;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2Session;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionOptions;
import com.thoughtworks.xstream.XStream;
import javafx.util.Pair;
import net.minidev.json.JSONObject;

import sun.misc.BASE64Decoder;

import java.io.*;
import java.net.InetAddress;
import java.net.URL;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-03-27
 * Time: 10:44
 */
public class ClientDebugger {
    /**
     * Remember to change the boolean depending on if you are testing in development or live
     */
    private static final boolean isDevelopment = true;
    public static void main(String[] args) throws IOException {
        new ClientDebugger();

    }

    /**
     * Write what you want to test here
     */
    public ClientDebugger() {
//        testStandardJson(9876, Constants.Method.LOGIN, Constants.Param.Name.ACCOUNT, new Pair(Constants.Param.Name.TOKEN, "heasfda3"));
//        testStandardJson(1, Constants.Method.GET_ACCOUNT, Constants.Param.Name.ACCOUNT, new Pair(Constants.Param.Name.ACCOUNT_ID, 1));
//        testStandardJson(1, Constants.Method.UPDATE_ACCOUNT, Constants.Param.Name.ACCOUNT, new Pair(Constants.Param.Name.SHOW_BIRTH_DATE, true), new Pair(Constants.Param.Name.DESCRIPTION, "Jag är bäst"));

//        testStandardJson(444, Constants.Method.GET_FORUM_CONTENT, Constants.Param.Name.CATEGORIES, new Pair(Constants.Param.Name.TYPE, "public"));
//        testStandardJson(444, Constants.Method.GET_CATEGORY_CONTENT, Constants.Param.Name.THREADS, new Pair(Constants.Param.Name.CATEGORY_ID, 1));
//        testStandardJson(444, Constants.Method.CREATE_CATEGORY, Constants.Param.Name.CATEGORY, new Pair(Constants.Param.Name.TYPE, "public"), new Pair(Constants.Param.Name.HEADLINE, "här är headline"), new Pair(Constants.Param.Name.COLOR_CODE, 433));
//        testStandardJson(444, Constants.Method.MODIFY_CATEGORY, Constants.Param.Name.CATEGORY, new Pair(Constants.Param.Name.TYPE, "public"), new Pair(Constants.Param.Name.CATEGORY_ID, 1), new Pair(Constants.Param.Name.HEADLINE, "Ny headline"), new Pair(Constants.Param.Name.COLOR_CODE, 9999));
//        testStandardJson(444, Constants.Method.DELETE_CATEGORY, null, new Pair(Constants.Param.Name.TYPE, "public"), new Pair(Constants.Param.Name.CATEGORY_ID, 5));

        testStandardJson(1, Constants.Method.GET_THREAD_CONTENT, Constants.Param.Name.REPLIES, new Pair(Constants.Param.Name.TOKEN, "test"), new Pair(Constants.Param.Name.THREAD_ID, 123));
//        testStandardJson(123456, Constants.Method.CREATE_THREAD, Constants.Param.Name.THREAD, new Pair(Constants.Param.Name.CATEGORY_ID, 2), new Pair(Constants.Param.Name.HEADLINE, "Test-headline"), new Pair(Constants.Param.Name.TEXT, "Lite text här"));
//        testStandardJson(444, Constants.Method.MODIFY_THREAD, Constants.Param.Name.THREAD, new Pair(Constants.Param.Name.THREAD_ID, 126), new Pair(Constants.Param.Name.HEADLINE, "Ny headline"), new Pair(Constants.Param.Name.TEXT, "Jag har ändrat texten!!!!"));
//        testStandardJson(444, Constants.Method.DELETE_THREAD, null, new Pair(Constants.Param.Name.THREAD_ID, 131));
//        testStandardJson(444, Constants.Method.GET_LATEST_THREADS, Constants.Param.Name.THREADS);


//        testStandardJson(123462, Constants.Method.CREATE_REPLY, Constants.Param.Name.REPLY, new Pair(Constants.Param.Name.THREAD_ID, 122+i), new Pair(Constants.Param.Name.TEXT, "Nu skriver jag: "+ i));
//        testStandardJson(444, Constants.Method.MODIFY_REPLY, Constants.Param.Name.REPLY, new Pair(Constants.Param.Name.REPLY_ID, 50), new Pair(Constants.Param.Name.TEXT, "Jag skriver den här nya texten nu! Den är mycket bättre"));
//        testStandardJson(444, Constants.Method.DELETE_REPLY, null, new Pair(Constants.Param.Name.REPLY_ID, 50));
//        testStandardJson(444, Constants.Method.THUMB_UP, Constants.Param.Name.REPLY, new Pair(Constants.Param.Name.REPLY_ID, 49), new Pair(Constants.Param.Name.THUMBS_UP, true));



//        testStandardJson(123456, "get_category_content", new Pair("category_id", 1));
//        testStandardJson(123456, "get_thread_content", new Pair("thread_id", 123));
//        testStandardJson(123456, "get_forum_content", new Pair("type", "public"));
    }


    /**
     * Tests a request with paired json objects as params
     * @param id
     * @param method
     * @param params
     */
    public void testStandardJson(Object id, String method, String resultObject, Pair<String, Object>... params){
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        for(Pair<String, Object> pair: params){
            jsonMap.put(pair.getKey(), pair.getValue());
        }
        jsonMap.put(Constants.Param.Name.TOKEN, "a");

        JSONRPC2Request jsonrpc2Request = new JSONRPC2Request(method, jsonMap , id);
        sendRequest(resultObject, jsonrpc2Request);

    }


    /**
     * Send the request you wish to test
     * @param jsonrpc2Request
     */
    public void sendRequest(String resultObject, JSONRPC2Request jsonrpc2Request){
        System.out.println();
        System.out.println("Request: " +jsonrpc2Request.toJSONString());
        String id = jsonrpc2Request.getID().toString();
        URL serverURL = null;

        try {
            if(isDevelopment){
                serverURL = new URL("http://" + InetAddress.getLocalHost().getHostAddress() + ":" + Constants.Http.PORT + "/");
                System.out.println("Client is running in development");
            }else{
                serverURL = new URL("http://" + Constants.General.EXTERNAL_SERVER_IP + ":" + Constants.Http.PORT + "/");
                System.out.println("Client is running in live");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ServerURL: " + serverURL.toString());
        JSONRPC2Session mySession = new JSONRPC2Session(serverURL);
        JSONRPC2SessionOptions options = new JSONRPC2SessionOptions();
        options.setConnectTimeout(10000);
        mySession.setOptions(options);

        JSONRPC2Response response = null;
        // Send request
        try {
            response = mySession.send(jsonrpc2Request);
        } catch (JSONRPC2SessionException e) {
            response = handleJSONRPC2Exception(e);
        }


        XStream xstream = new XStream();
        xstream.aliasPackage("models", "core.test.models");
        printResponse(response);
        String xmlString = null;
        Object returnedObject = null;
        xstream.alias("list", LinkedList.class);
        if(response.indicatesSuccess() && ((JSONObject)response.getResult()).get(Constants.Param.Status.STATUS).equals(Constants.Param.Status.SUCCESS)){
            String test;

            if(resultObject != null){
                test = (String) ((JSONObject)response.getResult()).get(resultObject);
                try {
                    xmlString = deSerializeObject(String.class, test);

                    returnedObject =  xstream.fromXML(xmlString);
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
    }


    /**
     * Print out the response for debugging purposes
     * @param response
     */
    private void printResponse(JSONRPC2Response response) {
        System.out.println("Response: " + response.toJSONString());

    }

    /**
     * Handle the different possible json errors
     * @param e
     * @return
     */
    private JSONRPC2Response handleJSONRPC2Exception(JSONRPC2SessionException e) {
        JSONRPC2Response errorResponse;
        String errorType = null;
        if (e.getCauseType() == JSONRPC2SessionException.JSONRPC2_ERROR) {
            errorType = "JSONRPC2 Error: " + e.getMessage();
        }
        if (e.getCauseType() == JSONRPC2SessionException.BAD_RESPONSE) {
            errorType = "Bad response Error: " + e.getMessage();
        }
        if (e.getCauseType() == JSONRPC2SessionException.NETWORK_EXCEPTION) {
            errorType = "Network Error: " + e.getMessage();
        }
        if (e.getCauseType() == JSONRPC2SessionException.UNEXPECTED_CONTENT_TYPE) {
            errorType = "Wrong content Error: " + e.getMessage();
        }
        if (e.getCauseType() == JSONRPC2SessionException.UNEXPECTED_RESULT) {
            errorType = "Strange result Error: " + e.getMessage();
        }
        if (e.getCauseType() == JSONRPC2SessionException.UNSPECIFIED) {
            errorType = "Unknown Error: " + e.getMessage();
        }
        errorResponse = new JSONRPC2Response(new JSONRPC2Error(-32099, errorType), null);
        return errorResponse;
    }

    //TODO Check if classType is not needed to be used in some way
    public static final <T>  T deSerializeObject(java.lang.Class<T> classType, String serializedObject) throws IOException,
            ClassNotFoundException {
        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte [] data = base64Decoder.decodeBuffer(serializedObject);
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream( data ) );

        Object o = ois.readObject();

        ois.close();

        return (T)o;
    }
}
