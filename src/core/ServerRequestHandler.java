package core;

import assistant.Constants;
import assistant.SharedHandler;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2ParamsType;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.server.MessageContext;
import com.thetransactioncompany.jsonrpc2.server.RequestHandler;
import models.*;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.hibernate.ejb.HibernatePersistence;

import javax.persistence.*;
import javax.persistence.spi.PersistenceProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-03-13
 * Time: 11:57
 */
public class ServerRequestHandler extends SharedHandler implements RequestHandler {
    // Reports the method names of the handled requests

    private EntityManagerFactory entityManagerFactory;
    private EntityManager em;

    public ServerRequestHandler() {
        PersistenceProvider persistenceProvider = new HibernatePersistence();
        entityManagerFactory = persistenceProvider.createEntityManagerFactory(Constants.Settings.PERSISTENCE_NAME, new HashMap());

    }

    @Override
    public String[] handledRequests() {

        return new String[]{
                };
    }

    // Processes the requests
    @Override
    public JSONRPC2Response process(JSONRPC2Request req, MessageContext ctx) {


        return null;
    }
}