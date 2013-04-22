package handlers;

import assistant.Constants;
import assistant.SharedHandler;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import models.Account;
import models.Reply;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: marcus
 * Date: 4/22/13
 * Time: 2:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReplyHandler extends SharedHandler {
    @Override
    public String[] handledRequests() {
        return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void process(Map<String, Object> jsonrpc2Params) throws Exception {
        if (method.equals(Constants.Method.CREATE_REPLY)) {
            createReply(jsonrpc2Params);
        } else {
            throwJSONRPC2Error(JSONRPC2Error.METHOD_NOT_FOUND, Constants.Errors.METHOD_NOT_FOUND, method);
        }
    }

    /**
     * Creates a reply by the given threadId.
     * Returns the created reply if successful.
     *
     * @param jsonrpc2Params
     * @throws Exception
     */
    private void createReply(Map<String, Object> jsonrpc2Params) throws Exception {

        Map<String, Object> createReplyParams = getParams(jsonrpc2Params,
                Constants.Param.Name.THREAD_ID,
                Constants.Param.Name.TEXT);

        int parentThreadId = (Integer) createReplyParams.get(Constants.Param.Name.THREAD_ID);
        String text = (String) createReplyParams.get(Constants.Param.Name.TEXT);

        models.Thread parentThread = em.find(models.Thread.class, parentThreadId);
        Date currentDate = new Date();

        Reply reply = new Reply(parentThread, em.find(Account.class, accountId), text, new Timestamp(currentDate.getTime()));

        persistObjects(reply);

        responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.SUCCESS);
        responseParams.put(Constants.Param.Name.REPLY, serialize(reply));
    }

}

