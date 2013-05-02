package handlers;

import assistant.Constants;
import assistant.SharedHandler;
import assistant.pair.NullableExtendedParam;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.hibernate.converter.*;
import com.thoughtworks.xstream.hibernate.mapper.HibernateMapper;
import com.thoughtworks.xstream.mapper.MapperWrapper;
import models.*;
import models.Thread;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-04-22
 * Time: 14:13
 */
public class ThreadHandler extends SharedHandler {
    @Override
    public String[] handledRequests() {
        return new String[]{
                Constants.Method.GET_THREAD_CONTENT,
                Constants.Method.CREATE_THREAD,
                Constants.Method.MODIFY_THREAD,
                Constants.Method.DELETE_THREAD,
                Constants.Method.GET_LATEST_THREADS
        };
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
        else if(method.equals(Constants.Method.MODIFY_THREAD)){
            modifyThread(jsonrpc2Params);
        }
        else if(method.equals(Constants.Method.DELETE_THREAD)){
            deleteThread(jsonrpc2Params);
        }
        else if(method.equals(Constants.Method.GET_LATEST_THREADS)){
            getLatestThreads(jsonrpc2Params);
        }
        else {
            throwJSONRPC2Error(JSONRPC2Error.METHOD_NOT_FOUND, Constants.Errors.METHOD_NOT_FOUND, method);
        }
    }

    /**
     *
     * @param jsonrpc2Params
     */
    private void getLatestThreads(Map<String, Object> jsonrpc2Params) throws Exception {
        Map<String, Object> params = getParams(jsonrpc2Params,
                new NullableExtendedParam(Constants.Param.Name.LIMIT, true));

        Integer limit = (Integer) params.get(Constants.Param.Name.LIMIT);
        if(limit == null){
            limit = Constants.Database.DEFAULT_LATEST_THREADS_LIMIT;
        }

        List<models.Thread> threads = getAllColumns(models.Thread.class);
        Collections.sort(threads, timeSort());

        List<models.Thread> returnThreads = new LinkedList<models.Thread>();
        for(int i= 0; i<limit && i<threads.size(); i++){
            returnThreads.add(threads.get(i));
        }

        threads = threads.subList(0, limit);
        responseParams.put(Constants.Param.Name.THREADS, serialize(returnThreads));
        responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.SUCCESS);
    }

    /**
     * Returns a list of replies given a threadId.
     * @param jsonrpc2Params
     * @throws Exception
     */
    private void getThreadContent(Map<String, Object> jsonrpc2Params) throws Exception {

        Map<String, Object> params = getParams(jsonrpc2Params,
                new NullableExtendedParam(Constants.Param.Name.THREAD_ID, false),
                new NullableExtendedParam(Constants.Param.Name.SORT_TYPE, true));

        int threadId = (Integer) params.get(Constants.Param.Name.THREAD_ID);
        Integer sortType = (Integer) params.get(Constants.Param.Name.SORT_TYPE);

        models.Thread thread = em.find(models.Thread.class, threadId);
//        Hibernate.initialize(thread);
        if(thread == null){
            responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.OBJECT_NOT_FOUND);
            return;
        }

        List replies = new ArrayList(thread.getReplies());

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
        responseParams.put(Constants.Param.Name.REPLIES, serialize(replies));
    }

    /**
     * Creates a Thread by the given categoryId.
     * Returns the created Thread.
     * @param jsonrpc2Params
     * @throws Exception
     */
    private void createThread(Map<String, Object> jsonrpc2Params) throws Exception {

        Map<String, Object> params = getParams(jsonrpc2Params,
                Constants.Param.Name.CATEGORY_ID,
                Constants.Param.Name.HEADLINE,
                Constants.Param.Name.TEXT);


        models.Thread thread = new models.Thread(
                em.getReference(Category.class, params.get(Constants.Param.Name.CATEGORY_ID)),
                em.getReference(Account.class, accountId),
                (String)        params.get(Constants.Param.Name.HEADLINE),
                (String)        params.get(Constants.Param.Name.TEXT),
                getCurrentTime()
        );


        persistObjects(thread);
        responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.SUCCESS);
        responseParams.put(Constants.Param.Name.THREAD, serialize(thread));
    }

    /**
     * Modifies a Threads headline and text by the given threadId .
     * Returns the created Thread.
     * @param jsonrpc2Params
     * @throws Exception
     */
    private void modifyThread(Map<String, Object> jsonrpc2Params) throws Exception {

        Map<String, Object> params = getParams(jsonrpc2Params,
                new NullableExtendedParam(Constants.Param.Name.THREAD_ID, false),
                new NullableExtendedParam(Constants.Param.Name.HEADLINE, false),
                new NullableExtendedParam(Constants.Param.Name.TEXT, false));

        models.Thread thread = em.find(models.Thread.class, params.get(Constants.Param.Name.THREAD_ID));
        String headline = (String) params.get(Constants.Param.Name.HEADLINE);
        String text = (String) params.get(Constants.Param.Name.TEXT);

        if(thread.getAccount().getId() != accountId){
            throwJSONRPC2Error(JSONRPC2Error.INVALID_PARAMS, Constants.Errors.ACTION_NOT_ALLOWED);
        }

        if(thread == null){
            responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.OBJECT_NOT_FOUND);
            return;
        }

        if(headline != null){
            thread.setHeadLine(headline);
        }

        if(text != null){
            thread.setText(text);
        }

        persistObjects(thread);
        responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.SUCCESS);
        responseParams.put(Constants.Param.Name.THREAD, serialize(thread));
    }


    /**
     * Deletes a thread by the given threadId.
     * Returns the created Thread.
     * @param jsonrpc2Params
     * @throws Exception
     */
    private void deleteThread(Map<String, Object> jsonrpc2Params) throws Exception {

        Map<String, Object> params = getParams(jsonrpc2Params, Constants.Param.Name.THREAD_ID);

        models.Thread thread = em.find(models.Thread.class, params.get(Constants.Param.Name.THREAD_ID));

        if(thread.getAccount().getId() != accountId){
            throwJSONRPC2Error(JSONRPC2Error.INVALID_PARAMS, Constants.Errors.ACTION_NOT_ALLOWED);
        }

        if(thread == null){
            responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.OBJECT_NOT_FOUND);
            return;
        }
        removeObjects(thread);
        responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.SUCCESS);
    }
}
