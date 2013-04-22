package handlers;

import assistant.Constants;
import assistant.SharedHandler;
import assistant.pair.NullableExtendedParam;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import models.Account;
import models.Category;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-22
 * Time: 14:13
 */
public class ThreadHandler extends SharedHandler {
    @Override
    public String[] handledRequests() {
        return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void process(Map<String, Object> jsonrpc2Params) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.

        if(method.equals(Constants.Method.GET_THREAD_CONTENT)){
            getThreadContent(jsonrpc2Params);
        }
        else if(method.equals(Constants.Method.CREATE_THREAD)){
            createThread(jsonrpc2Params);
        }
        else {
            throwJSONRPC2Error(JSONRPC2Error.METHOD_NOT_FOUND, Constants.Errors.METHOD_NOT_FOUND, method);
        }
    }

    /**
     * Creates a Thread by the given categoryId.
     * Returns the created Thread.
     * @param jsonrpc2Params
     * @throws Exception
     */
    private void createThread(Map<String, Object> jsonrpc2Params) throws Exception {

        Map<String, Object> createThreadParams = getParams(jsonrpc2Params,
                Constants.Param.Name.CATEGORY_ID,
                Constants.Param.Name.HEADLINE,
                Constants.Param.Name.TEXT);


        models.Thread thread = new models.Thread(
                em.find(Category.class, createThreadParams.get(Constants.Param.Name.CATEGORY_ID)),
                em.find(Account.class, accountId),
                (String)        createThreadParams.get(Constants.Param.Name.HEADLINE),
                (String)        createThreadParams.get(Constants.Param.Name.TEXT)
        );

        persistObjects(thread);
        responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.SUCCESS);
        responseParams.put(Constants.Param.Name.CATEGORY, serialize(thread));

    }

    /**
     * Returns a list of replies given a threadId.
     * @param jsonrpc2Params
     * @throws Exception
     */
    private void getThreadContent(Map<String, Object> jsonrpc2Params) throws Exception {

        Map<String, Object> getForumParams = getParams(jsonrpc2Params,
                new NullableExtendedParam(Constants.Param.Name.THREAD_ID, false),
                new NullableExtendedParam(Constants.Param.Name.SORT_TYPE, true));

        String threadId = (String) getForumParams.get(Constants.Param.Name.THREAD_ID);
        Integer sortType = (Integer) getForumParams.get(Constants.Param.Name.SORT_TYPE);

        models.Thread thread = em.find(models.Thread.class, threadId);
        if(thread == null){
            responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.THREAD_NOT_FOUND);
            return;
        }

        List replies = thread.getReplies();

        if(sortType == null || sortType == 0){
            // Sort using standard sorting (aka time)
            Collections.sort(replies, ReplyComparator.getComparator(ReplyComparator.TIME_SORT));

        }else if(sortType == 1){
            //Sort after time
            Collections.sort(replies, ReplyComparator.getComparator(ReplyComparator.TIME_SORT));

        }else if(sortType == 2){
            //Sort after date, and secondly time
            Collections.sort(replies, ReplyComparator.getComparator( ReplyComparator.VOTE_SORT, ReplyComparator.TIME_SORT));

        }

        responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.SUCCESS);
        responseParams.put(Constants.Param.Name.REPLIES, serialize((Serializable) replies));
    }
}
