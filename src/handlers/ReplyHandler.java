package handlers;

import assistant.Constants;
import assistant.SharedHandler;
import assistant.pair.NullableExtendedParam;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import models.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
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
        return new String[]{
                Constants.Method.CREATE_REPLY,
                Constants.Method.MODIFY_REPLY,
                Constants.Method.DELETE_REPLY,
                Constants.Method.THUMB_UP,
                Constants.Method.CREATE_NOTE,
                Constants.Method.MODIFY_NOTE,
                Constants.Method.DELETE_NOTE
        };
    }

    @Override
    protected void process(Map<String, Object> jsonrpc2Params) throws Exception {
        if (method.equals(Constants.Method.CREATE_REPLY)) {
            createReply(jsonrpc2Params);
        } else if (method.equals(Constants.Method.MODIFY_REPLY)) {
            modifyReply(jsonrpc2Params);
        } else if (method.equals(Constants.Method.DELETE_REPLY)) {
            deleteReply(jsonrpc2Params);
        } else if (method.equals(Constants.Method.THUMB_UP)) {
            thumbUp(jsonrpc2Params);
        } else if (method.equals(Constants.Method.CREATE_NOTE)) {
            createNote(jsonrpc2Params);
        } else if (method.equals(Constants.Method.MODIFY_NOTE)) {
            modifyNote(jsonrpc2Params);
        } else if (method.equals(Constants.Method.DELETE_NOTE)) {
            deleteNote(jsonrpc2Params);
        } else {
            throwJSONRPC2Error(JSONRPC2Error.METHOD_NOT_FOUND, Constants.Errors.METHOD_NOT_FOUND, method);
        }
    }

    private void deleteNote(Map<String, Object> jsonrpc2Params) throws Exception {

        Map<String, Object> params = getParams(jsonrpc2Params,
                Constants.Param.Name.NOTE_ID);


        Note note =  em.find(Note.class, params.get(Constants.Param.Name.NOTE_ID));

        if(note == null){
            responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.OBJECT_NOT_FOUND);
            return;
        }

        if(note.getAccount().getId() != accountId){
            throwJSONRPC2Error(JSONRPC2Error.INVALID_PARAMS, Constants.Errors.ACTION_NOT_ALLOWED);
        }

        removeObjects(note);
        responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.SUCCESS);
    }

    private void modifyNote(Map<String, Object> jsonrpc2Params) throws Exception {
        Map<String, Object> params = getParams(jsonrpc2Params,
                new NullableExtendedParam(Constants.Param.Name.NOTE_ID, false),
                new NullableExtendedParam(Constants.Param.Name.TEXT, true),
                new NullableExtendedParam(Constants.Param.Name.HEADLINE, true));

        int noteId = (Integer)params.get(Constants.Param.Name.NOTE_ID);
        String text = (String)params.get(Constants.Param.Name.TEXT);
        String headline = (String)params.get(Constants.Param.Name.HEADLINE);
        Note note = em.find(Note.class, noteId);
        if(note == null){
            responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.OBJECT_NOT_FOUND);
            return;
        }
        if(note.getAccount().getId() != accountId){
            throwJSONRPC2Error(JSONRPC2Error.INVALID_PARAMS, Constants.Errors.ACTION_NOT_ALLOWED);
        }
        if(text != null){
            note.setText(text);
        }
        if(headline != null){
            note.setHeadLine(headline);
        }
        persistObjects(note);
        responseParams.put(Constants.Param.Name.NOTE, serialize(note));
        responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.SUCCESS);
    }

    private void createNote(Map<String, Object> jsonrpc2Params) throws Exception {
        Map<String, Object> params = getParams(jsonrpc2Params,
                new NullableExtendedParam(Constants.Param.Name.PRIVATE_CATEGORY_ID, false),
                new NullableExtendedParam(Constants.Param.Name.TEXT, false),
                new NullableExtendedParam(Constants.Param.Name.HEADLINE, true));

        int categoryId = (Integer)params.get(Constants.Param.Name.PRIVATE_CATEGORY_ID);
        String text = (String)params.get(Constants.Param.Name.TEXT);
        String headline = (String)params.get(Constants.Param.Name.HEADLINE);
        PrivateCategory privateCategory;
        try{
        privateCategory = em.getReference(PrivateCategory.class, categoryId);
        }catch(EntityNotFoundException e){
            responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.OBJECT_NOT_FOUND);
            return;
        }
        if(privateCategory.getAccount().getId() != accountId){
            throwJSONRPC2Error(JSONRPC2Error.INVALID_PARAMS, Constants.Errors.ACTION_NOT_ALLOWED);
        }
        Note note = new Note(text, headline, em.find(Account.class, accountId), privateCategory);
        persistObjects(note);
        responseParams.put(Constants.Param.Name.NOTE, serialize(note));
        responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.SUCCESS);
    }


    /**
     * Creates a reply by the given threadId.
     * Returns the created reply if successful.
     *
     * @param jsonrpc2Params
     * @throws Exception
     */
    private void createReply(Map<String, Object> jsonrpc2Params) throws Exception {

        Map<String, Object> params = getParams(jsonrpc2Params,
                Constants.Param.Name.THREAD_ID,
                Constants.Param.Name.TEXT);

        int parentThreadId = (Integer) params.get(Constants.Param.Name.THREAD_ID);
        String text = (String) params.get(Constants.Param.Name.TEXT);

        models.Thread parentThread = em.find(models.Thread.class, parentThreadId);
        Date currentDate = new Date();

        Reply reply = new Reply(parentThread, em.find(Account.class, accountId), text, getCurrentTime());

        persistObjects(reply);

        responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.SUCCESS);
        responseParams.put(Constants.Param.Name.REPLY, serialize(reply));
    }

    /**
     * Modify a reply by the given the replyId and returns the resulting object.
     *
     * @param jsonrpc2Params
     * @throws Exception
     */
    private void modifyReply(Map<String, Object> jsonrpc2Params) throws Exception {

        Map<String, Object> params = getParams(jsonrpc2Params,
                new NullableExtendedParam(Constants.Param.Name.REPLY_ID, false),
                new NullableExtendedParam(Constants.Param.Name.TEXT, true));

        int replyId = (Integer)params.get(Constants.Param.Name.REPLY_ID);
        String text = (String) params.get(Constants.Param.Name.TEXT);
        Reply reply = em.find(Reply.class, (Integer) params.get(Constants.Param.Name.REPLY_ID));

        if(reply.getAccount().getId() != accountId){
            throwJSONRPC2Error(JSONRPC2Error.INVALID_PARAMS, Constants.Errors.ACTION_NOT_ALLOWED);
        }

        if(reply == null){
            responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.OBJECT_NOT_FOUND);
            return;
        }
        if(text != null){
            reply.setText(text);
        }
        reply.setTime(getCurrentTime());
        persistObjects(reply);

        responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.SUCCESS);
        responseParams.put(Constants.Param.Name.REPLY, serialize(reply));
    }

    /**
     * Delete a reply by the given the replyId.
     *
     * @param jsonrpc2Params
     * @throws Exception
     */
    private void deleteReply(Map<String, Object> jsonrpc2Params) throws Exception {

        Map<String, Object> params = getParams(jsonrpc2Params,
                Constants.Param.Name.REPLY_ID);


       Reply reply =  em.find(Reply.class, params.get(Constants.Param.Name.REPLY_ID));

        if(reply == null){
            responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.OBJECT_NOT_FOUND);
            return;
        }

        if(reply.getAccount().getId() != accountId){
            throwJSONRPC2Error(JSONRPC2Error.INVALID_PARAMS, Constants.Errors.ACTION_NOT_ALLOWED);
        }


        removeObjects(reply);
        responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.SUCCESS);
    }

    /**
     *
     * @param jsonrpc2Params
     * @throws Exception
     */
    private void thumbUp(Map<String,Object> jsonrpc2Params) throws Exception {
        Map<String, Object> params = getParams(jsonrpc2Params,
                Constants.Param.Name.REPLY_ID,
                Constants.Param.Name.THUMBS_UP);

        int replyId = (Integer) params.get(Constants.Param.Name.REPLY_ID);
        boolean isThumbUp = (Boolean) params.get(Constants.Param.Name.THUMBS_UP);

        if(isThumbUp){
            ThumbsUp thumbsUpObject = new ThumbsUp(replyId, accountId);
            try{
                persistObjects(thumbsUpObject);
            } catch(EntityExistsException e){}


        }else{
            ThumbsUp thumbsUpObject = em.getReference(ThumbsUp.class, new ThumbsUpPK(replyId, accountId));
            removeObjects(thumbsUpObject);
        }

        Reply reply = em.find(Reply.class, replyId);
        responseParams.put(Constants.Param.Name.REPLY, serialize(reply));
        responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.SUCCESS);
    }
}

