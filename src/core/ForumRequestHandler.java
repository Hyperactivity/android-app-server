package core;

import assistant.Constants;
import assistant.SharedHandler;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import javafx.util.Pair;
import models.Categories;
import models.Users;

import javax.persistence.NoResultException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
                Constants.Method.GET_CATEGORY_CONTENT,
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
        else if(method.equals(Constants.Method.GET_CATEGORY_CONTENT)){
            getCategoryContent(jsonrpc2Params);
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
        List<Categories> categories = null;
        if(type.equals(Constants.Param.Value.PUBLIC)){
            query = "SELECT f FROM Categories f WHERE f.parentCategoryId = :" + Constants.Query.PARENT_CATEGORY_ID;
            categories = em.createQuery( query, Categories.class).setParameter(Constants.Query.PARENT_CATEGORY_ID, null).getResultList();
        }
        else if(type.equals(Constants.Param.Value.PRIVATE)){
            query = "SELECT f FROM PrivateCategories f WHERE f.privateCategoryId = :" + Constants.Query.PARENT_CATEGORY_ID;
            categories = em.createQuery( query, Categories.class).setParameter(Constants.Query.PARENT_CATEGORY_ID, null).getResultList();

        }else{
            throwJSONRPC2Error(JSONRPC2Error.INVALID_PARAMS, Constants.Errors.PARAM_VALUE_NOT_ALLOWED, "Type: " + type);
        }
        responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.SUCCESS);
        responseParams.put(Constants.Param.Name.CATEGORIES, serialize((Serializable) categories));
    }

    private void createReply(Map<String, Object> jsonrpc2Params) {
//        Create Reply
//        Request
//        method	create_reply
//        id	Account.id
//        params-name	thread_id	text
//        params-value	Thread.id	String
//        Response
//        value	success
//        result-name	parent_thread
//        result-value	Thread
    }

    private void createThread(Map<String, Object> jsonrpc2Params) {

//        Create Thread
//        Request
//        method	create_thread
//        id	Account.id
//        params-name	category_id	headline	text
//        params-value	Category.id	String	String
//                Response
//        value	success
//        result-name	parent_category
//        result-value	Category
    }

    private void getThread(Map<String, Object> jsonrpc2Params) {
//        Get Thread
//        Request
//        method	get_thread
//        id	Account.id
//        params-name	thread_id
//        params-value	thread.id
//                Response
//        value	success
//        result-name	thread
//        result-value	Thread
    }

    private void getCategoryContent(Map<String, Object> jsonrpc2Params) {
//        Get Category Content
//                Request
//        method	get_category_content
//        id	Account.id
//        params-name	category_id
//        params-value	Category.id
//                Response
//        value	success
//        result-name	categories	threads
//        result-value	Category[]	Thread[]
    }
}
