package handlers;

import assistant.Constants;
import assistant.SharedHandler;
import assistant.pair.NullableExtendedParam;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import models.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-03-13
 * Time: 11:57
 */
public class CategoryRequestHandler extends SharedHandler {

    public CategoryRequestHandler() {
        super();
    }

    @Override
    public String[] handledRequests() {
        return new String[]{
            Constants.Method.GET_FORUM_CONTENT,
            Constants.Method.GET_CATEGORY_CONTENT,
            Constants.Method.GET_THREAD_CONTENT,
            Constants.Method.CREATE_THREAD,
            Constants.Method.CREATE_REPLY,
        };
    }

    @Override
    public void process(Map<String, Object> jsonrpc2Params) throws Exception {
        if(method.equals(Constants.Method.GET_FORUM_CONTENT)){
            getForumContent(jsonrpc2Params);
        }
        else if(method.equals(Constants.Method.GET_CATEGORY_CONTENT)){
            getCategoryContent(jsonrpc2Params);
        }
        else {
            throwJSONRPC2Error(JSONRPC2Error.METHOD_NOT_FOUND, Constants.Errors.METHOD_NOT_FOUND, method);
        }
    }

    /**
     * Returns the categories by the given forum type.
     * @param jsonrpc2Params
     * @throws Exception
     */
    private void getForumContent(Map<String, Object> jsonrpc2Params) throws Exception {
        Map<String, Object> getForumParams = getParams(jsonrpc2Params,
                Constants.Param.Name.TYPE);

        String type = (String) getForumParams.get(Constants.Param.Name.TYPE);
        List<Category> categories = null;

        if(type.equals(Constants.Param.Value.PUBLIC)){
            categories = getAllColumns(Category.class);
        }
        else if(type.equals(Constants.Param.Value.PRIVATE)){

            categories = getAllColumns(Category.class);

        }else{
            throwJSONRPC2Error(JSONRPC2Error.INVALID_PARAMS, Constants.Errors.PARAM_VALUE_NOT_ALLOWED, "Type: " + type);
        }

        responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.SUCCESS);
        responseParams.put(Constants.Param.Name.CATEGORIES, serialize((Serializable) categories));
    }

    /**
     * Creates a reply by the given threadId.
     * Returns the created reply if successful.
     * @param jsonrpc2Params
     * @throws Exception
     */
    private void createReply(Map<String, Object> jsonrpc2Params) throws Exception {

        Map<String, Object> createReplyParams = getParams(jsonrpc2Params,
                Constants.Param.Name.THREAD_ID,
                Constants.Param.Name.TEXT);

        int parentThreadId = (Integer)createReplyParams.get(Constants.Param.Name.THREAD_ID);
        String text = (String) createReplyParams.get(Constants.Param.Name.TEXT);

        models.Thread parentThread = em.find(models.Thread.class, parentThreadId);
        Date currentDate = new Date();

        Reply reply = new Reply(parentThread, em.find(Account.class, accountId), text, new Timestamp(currentDate.getTime()));

        persistObjects(reply);

        responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.SUCCESS);
        responseParams.put(Constants.Param.Name.REPLY, serialize(reply));
    }




    /**
     * Returns a list of threads by the given categoryId.
     * @param jsonrpc2Params
     * @throws Exception
     */
    private void getCategoryContent(Map<String, Object> jsonrpc2Params) throws Exception {

        Map<String, Object> createReplyParams = getParams(jsonrpc2Params,
                new NullableExtendedParam(Constants.Param.Name.CATEGORY_ID, false));

        int categoryId = (Integer) createReplyParams.get(Constants.Param.Name.CATEGORY_ID);


        Category category = em.find(Category.class, categoryId);
        if(category == null){
            responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.CATEGORY_NOT_FOUND);
            return;
        }

        List<models.Thread> threadList = category.getThreads();

        responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.SUCCESS);
        responseParams.put(Constants.Param.Name.THREADS, serialize((Serializable) threadList));
    }
}
