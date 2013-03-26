package assistant;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.server.MessageContext;
import com.thetransactioncompany.jsonrpc2.server.RequestHandler;
import net.minidev.json.JSONObject;
import org.hibernate.ejb.HibernatePersistence;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import java.io.*;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-03-25
 * Time: 14:26
 */
public abstract class SharedHandler implements RequestHandler{
    private EntityManagerFactory entityManagerFactory;
    public EntityManager em;

    public int userId;
    public String method;
    public JSONObject responseJson;

    /**
     * Creates the entity factory that produces the entity managers
     */
    public SharedHandler(){
        PersistenceProvider persistenceProvider = new HibernatePersistence();
        entityManagerFactory = persistenceProvider.createEntityManagerFactory(Constants.Settings.PERSISTENCE_NAME, new HashMap());
    }

    /** Read the object from Base64 string. */
    private static Object fromString( String s ) throws IOException,
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


    @Override
    public abstract String[] handledRequests();

    @Override
    public final JSONRPC2Response process(JSONRPC2Request jsonrpc2Request, MessageContext messageContext) { //TODO find out what MessageContext is exactly and how we can use it

        try{
            initializeData(jsonrpc2Request);
            return process(jsonrpc2Request);
        }catch (JSONRPC2Error e){
            return new JSONRPC2Response(e, userId);
        }catch (Exception e){

        }
        return null;
    }

    private void initializeData(JSONRPC2Request jsonrpc2Request) throws Exception {
        em = entityManagerFactory.createEntityManager();
        responseJson = new JSONObject();
        method = jsonrpc2Request.getMethod();
        try{
            userId = Integer.parseInt(jsonrpc2Request.getID().toString());
        }catch(NumberFormatException e){
            throwJSONRPC2Error(JSONRPC2Error.INVALID_REQUEST, Constants.Errors.BAD_ID);
        }
    }

    protected abstract JSONRPC2Response process(JSONRPC2Request jsonrpc2Request);

    public void throwJSONRPC2Error(JSONRPC2Error e, String msgToAppend) throws Exception {
        e.appendMessage( " || " + msgToAppend);
        throw e;
    }
}


