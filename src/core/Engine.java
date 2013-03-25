package core; /**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-03-11
 * Time: 15:49
 */

import assistant.Constants;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.thetransactioncompany.jsonrpc2.*;
import com.thetransactioncompany.jsonrpc2.server.Dispatcher;
import models.Categories;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Map;

public class Engine{

    private HttpServer httpServer;
    private Dispatcher dispatcher;


    public static void main(String[] args) throws IOException {
        try {
            new Engine();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private Engine() throws IOException, ClassNotFoundException {
//        initiateDispatchers();
//        initiateServer();
        Categories categories = new Categories();
        categories.setCategoryColorCode(1);
        categories.setCategoryId(1);
        categories.setCategoryName("test");
        categories.setParentCategoryId(2);
        String testString;
        testString =  toString(categories);
        Categories test = (Categories) fromString(testString);
    }

    /**
     * Initialize dispatchers for handling requests and notifications from clients.
     * Multiple request and notification handlers give a better overview.
     */
    private void initiateDispatchers() {
        dispatcher = new Dispatcher();
        dispatcher.register(new ServerRequestHandler());
    }

    /**
     * Create the server and start listening for http requests.
     */
    private void initiateServer() {
        try {
            InetSocketAddress isa = new InetSocketAddress(InetAddress.getLocalHost(), Constants.Settings.PORT);
            System.out.println(isa);
            httpServer = HttpServer.create(isa, 0); //TODO check what the zero does exactly
            httpServer.createContext("/", new HTTPRequestHandler());
        } catch (IOException e) {
            e.printStackTrace();  //TODO Better handling of this exception
        }
        httpServer.start();
        System.out.println("Server is listening");
        System.out.println();
    }

    private class HTTPRequestHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            System.out.println();
            System.out.println("Connection Made");
            String requestMethod = exchange.getRequestMethod();
                               if (requestMethod.equalsIgnoreCase("POST")) {

                        // Prepare the response
                        JSONRPC2Response jsonrpc2Response;

                        Headers requestHeaders = exchange.getRequestHeaders();
                        if (examineHeaders(requestHeaders)) {
                            // Headers seems ok. Continue

                            int bodyLength = Integer.parseInt(requestHeaders.getFirst(Constants.Settings.CONTENT_LENGTH));
                            byte[] jsonBytes = new byte[bodyLength];
                            // Start reading the body
                            InputStream requestBody = exchange.getRequestBody();
                            requestBody.read(jsonBytes, 0, bodyLength);
                            String jsonString = new String(jsonBytes);

                            System.out.println("Request: " + jsonString);

                    // Parse the jsonString and get the response to send back
                    jsonrpc2Response = parseReceivedString(jsonString);
                    requestBody.close();

                } else {
                    // We have some error with the header, send back a error and skip the actual message
                    jsonrpc2Response = new JSONRPC2Response(JSONRPC2Error.INVALID_REQUEST.appendMessage(Constants.Errors.BAD_HEADERS), null);
                }

                if (jsonrpc2Response != null) {
                    // This means it was a request and not a response/notifcation
                    //TODO: Should maybe be decided in the header instead of the body...
                    System.out.println("Response: " + jsonrpc2Response.toString());
                    // Set the content-type, important!
                    Headers responseHeaders = exchange.getResponseHeaders();
                    responseHeaders.set("Content-Type", "text/plain");

                    // Write the body and send it
                    OutputStream responseBody = exchange.getResponseBody();
                    exchange.sendResponseHeaders(200, jsonrpc2Response.toJSONString().length());
                    responseBody.write(jsonrpc2Response.toJSONString().getBytes());
                    responseBody.close();
                    System.out.println("Response sent");
                }
            }
        }
    }

    public boolean examineHeaders(Headers requestHeaders) {
        String headersString = "";
        for (Map.Entry headersSet : requestHeaders.entrySet()) {
            headersString += headersSet.getKey() + ": " + headersSet.getValue() + " | ";
        }
        System.out.println("Header || " + headersString);
        try {
            Integer.parseInt(requestHeaders.getFirst(Constants.Settings.CONTENT_LENGTH));
        } catch (NumberFormatException e) {
            return false;
        }

        //Checkapplication/json-rpc
        return true;
    }

    /**
     *
     * @param jsonString The json string recieved from a client
     * @return The response generated
     */
    private JSONRPC2Response parseReceivedString(String jsonString) {
        JSONRPC2Message reqIn;

        try {
            reqIn = JSONRPC2Message.parse(jsonString);

        } catch (JSONRPC2ParseException e) {
            //TODO: Seems not to work for some reason...
            e.printStackTrace();
            return new JSONRPC2Response(JSONRPC2Error.PARSE_ERROR.appendMessage(jsonString), null);
        }

        if (reqIn instanceof JSONRPC2Request) {
            System.out.println("The message is a Request");
            JSONRPC2Response reqOut = dispatcher.process((JSONRPC2Request) reqIn, null);
            return reqOut;

        } else if (reqIn instanceof JSONRPC2Notification) {
            System.out.println("The message is a Notification");
            dispatcher.process((JSONRPC2Notification) reqIn, null);
            return null;

        } else if (reqIn instanceof JSONRPC2Response) {
            System.out.println("The message is a Response");
            return null;
        }

        return new JSONRPC2Response(JSONRPC2Error.INVALID_REQUEST.appendMessage("Message is of unknown type"), null);
    }



    /** Read the object from Base64 string. */
    private static Object fromString( String s ) throws IOException ,
            ClassNotFoundException {
        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte [] data = base64Decoder.decodeBuffer(s);
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream( data ) );
        Object o = ois.readObject();
        ois.close();
        return o;
    }
    /** Write the object to a Base64 string. */
    private static String toString( Serializable o ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( o );
        oos.close();
        BASE64Encoder base64Encoder =  new BASE64Encoder();
        return new String( base64Encoder.encode(baos.toByteArray()) );
    }
}
