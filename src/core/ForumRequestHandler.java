package core;

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
public class ForumRequestHandler extends SharedHandler {

    public ForumRequestHandler() {
        super();
    }

    @Override
    public String[] handledRequests() {
        return new String[]{
            Constants.Method.GET_FORUM,
            Constants.Method.GET_CATEGORY,
            Constants.Method.GET_THREAD,
            Constants.Method.CREATE_THREAD,
            Constants.Method.CREATE_REPLY,
        };
    }

    @Override
    public void process(Map<String, Object> jsonrpc2Params) throws Exception {
        if(method.equals(Constants.Method.GET_FORUM)){
            getForum(jsonrpc2Params);
        }
        else if(method.equals(Constants.Method.GET_CATEGORY)){
            getCategory(jsonrpc2Params);
        }
        else if(method.equals(Constants.Method.GET_THREAD)){
            getThread(jsonrpc2Params);
        }
        else if(method.equals(Constants.Method.CREATE_THREAD)){
            createThread(jsonrpc2Params);
        }
        else if(method.equals(Constants.Method.CREATE_REPLY)){
            createReply(jsonrpc2Params);
        }
        throwJSONRPC2Error(JSONRPC2Error.METHOD_NOT_FOUND, Constants.Errors.METHOD_NOT_FOUND, method);
    }

    private void getForum(Map<String, Object> jsonrpc2Params) throws Exception {
        Map<String, Object> getForumParams = getParams(jsonrpc2Params, Constants.Param.Name.TYPE);

        String type = (String) getForumParams.get(Constants.Param.Name.TYPE);
        String query;
        List<Category> categories = null;
        if(type.equals(Constants.Param.Value.PUBLIC)){
            query = "SELECT f FROM Category f WHERE f.parentCategory = :" + Constants.Query.PARENT_CATEGORY_ID;
            categories = em.createQuery( query, Category.class).setParameter(Constants.Query.PARENT_CATEGORY_ID, null).getResultList();
        }
        else if(type.equals(Constants.Param.Value.PRIVATE)){
            query = "SELECT f FROM PrivateCategory f WHERE f.parentPrivateCategory = :" + Constants.Query.PARENT_CATEGORY_ID;
            categories = em.createQuery( query, Category.class).setParameter(Constants.Query.PARENT_CATEGORY_ID, null).getResultList();

        }else{
            throwJSONRPC2Error(JSONRPC2Error.INVALID_PARAMS, Constants.Errors.PARAM_VALUE_NOT_ALLOWED, "Type: " + type);
        }
        responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.SUCCESS);
        responseParams.put(Constants.Param.Name.CATEGORIES, serialize((Serializable) categories));
    }

    private void createReply(Map<String, Object> jsonrpc2Params) throws Exception {

        Map<String, Object> createReplyParams = getParams(jsonrpc2Params,
                                                new NullableExtendedParam(Constants.Param.Name.THREAD_ID, false ),
                                                new NullableExtendedParam(Constants.Param.Name.TEXT, false),
                                                new NullableExtendedParam(Constants.Param.Name.SORT_TYPE, true));

        int parentThreadId = (Integer)createReplyParams.get(Constants.Param.Name.THREAD_ID);
        String text = (String) createReplyParams.get(Constants.Param.Name.TEXT);
        Integer sortType = (Integer) createReplyParams.get(Constants.Param.Name.SORT_TYPE);


        models.Thread parentThread = em.find(models.Thread.class, parentThreadId);
        Date currentDate = new Date();

        Reply reply = new Reply(parentThread, em.find(Account.class, accountId), text, new Timestamp(currentDate.getTime()));

        //TODO: Fix so that this works
//        Reply reply = new Reply(parentThread.getId(), accountId, text, new Timestamp(currentDate.getTime()));

        persistObjects(reply);
        refreshObjects(parentThread);

        if(sortType == null || sortType == 0){
            // Sort using standard sorting
        Collections.sort(parentThread.getReplies(), ReplyComparator.getComparator(ReplyComparator.TIME_SORT));
            // Sort
//            parentThread.getReplies();

        }else if(sortType == 1){
            //Sort after time
            Collections.sort(parentThread.getReplies(), ReplyComparator.getComparator(ReplyComparator.TIME_SORT));
        }else if(sortType == 2){
            //Sort after date, and secondly time
            Collections.sort(parentThread.getReplies(), ReplyComparator.getComparator( ReplyComparator.VOTE_SORT, ReplyComparator.TIME_SORT));

        }else{
            throwJSONRPC2Error(JSONRPC2Error.INVALID_PARAMS, "sortType is not allowed!" + sortType);
        }
        responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.SUCCESS);
//        responseParams.put(Constants.Param.)

    }


    private void createThread(Map<String, Object> jsonrpc2Params) throws Exception {

        Map<String, Object> createReplyParams = getParams(jsonrpc2Params,
                new NullableExtendedParam(Constants.Param.Name.CATEGORY_ID, false ),
                new NullableExtendedParam(Constants.Param.Name.HEADLINE, false),
                new NullableExtendedParam(Constants.Param.Name.TEXT, false));


        models.Thread thread = new models.Thread(
                                em.find(Category.class, createReplyParams.get(Constants.Param.Name.CATEGORY_ID)),
                                 em.find(Account.class, accountId),
                (String)        createReplyParams.get(Constants.Param.Name.HEADLINE),
                (String)        createReplyParams.get(Constants.Param.Name.TEXT)
                );

        try {
            persistObjects(thread);
            responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.SUCCESS);
            responseParams.put(Constants.Param.Name.CATEGORY, serialize((Serializable) thread.getParentCategoryId()));
        }
        catch (Exception e)
        {

        }
    }

    private void getThread(Map<String, Object> jsonrpc2Params) throws Exception {

        Map<String, Object> getForumParams = getParams(jsonrpc2Params, Constants.Param.Name.THREAD_ID, Constants.Param.Name.TYPE, Constants.Param.Name.SORT_TYPE);

        String threadId = (String) getForumParams.get(Constants.Param.Name.THREAD_ID);
        String type = (String) getForumParams.get(Constants.Param.Name.TYPE);
        String sortType = (String) getForumParams.get(Constants.Param.Name.SORT_TYPE);

        models.Thread thread = em.find(models.Thread.class, threadId);

        responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.SUCCESS);
        responseParams.put(Constants.Param.Name.CATEGORIES, serialize((Serializable) thread.getReplies()));


    }

    private void getCategory(Map<String, Object> jsonrpc2Params) throws Exception {

        Map<String, Object> createReplyParams = getParams(jsonrpc2Params, new NullableExtendedParam(Constants.Param.Name.CATEGORY_ID, false));
        String categoryId = (String) createReplyParams.get(Constants.Param.Name.CATEGORY_ID);

        //Category category = new Category(createReplyParams.get);
        Category category = (Category) em.find(Category.class, categoryId);
        //Category catego = new Category(123, "abc", 456, null);
        List<models.Thread> threadList = category.getThreads();


        responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.SUCCESS);
        responseParams.put(Constants.Param.Name.CATEGORIES, serialize((Serializable) threadList));
    }
}
