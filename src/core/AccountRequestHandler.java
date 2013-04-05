package core;

import assistant.Constants;
import assistant.SharedHandler;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import javafx.util.Pair;
import models.Users;

import javax.persistence.NoResultException;
import java.awt.*;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: OMMatte
 * Date: 2013-03-13
 * Time: 11:57
 */
public class AccountRequestHandler extends SharedHandler {

    public AccountRequestHandler() {
        super();
    }

    @Override
    public String[] handledRequests() {
        return new String[]{
                Constants.Method.LOGIN,
                Constants.Method.GET_PROFILE,
                Constants.Method.UPDATE_PROFILE,
        };
    }

    @Override
    public void process(Map<String, Object> jsonrpc2Params) throws Exception {
        if(method.equals(Constants.Method.LOGIN)){
            login(jsonrpc2Params);
        }
        else if(method.equals(Constants.Method.GET_PROFILE)){
            getProfile(jsonrpc2Params);
        }
        else if(method.equals(Constants.Method.UPDATE_PROFILE)){
            updateProfile(jsonrpc2Params);
        }
        else{
        throwJSONRPC2Error(JSONRPC2Error.METHOD_NOT_FOUND, Constants.Errors.METHOD_NOT_FOUND, method);
        }
    }

    private void updateProfile(Map<String, Object> jsonrpc2Params) throws Exception {
        Map<String, Object> updateProfileParams = getParams(jsonrpc2Params,
                new Pair(Constants.Param.DESCRIPTION, true),
                new Pair(Constants.Param.SHOW_BIRTH_DATE, true),
                new Pair(Constants.Param.AVATAR, true));

        String description = (String) updateProfileParams.get(Constants.Param.DESCRIPTION);
        Boolean showBirthDate = (Boolean) updateProfileParams.get(Constants.Param.SHOW_BIRTH_DATE);
        //TODO: handle Avatar

        Users profile = em.find(Users.class, userId);
        if(description != null){
            profile.setProfileDescription(description);
        }
        if(showBirthDate != null){
            profile.setShowBirthDate(showBirthDate);
        }

        responseParams.put(Constants.Param.VALUE, Constants.Param.SUCCESS);
        responseParams.put(Constants.Param.PROFILE, profile);
    }

    private void getProfile(Map<String, Object> jsonrpc2Params) throws Exception{
        Map<String, Object> getProfileParams = getParams(jsonrpc2Params, Constants.Param.ACCOUNT_ID);

        int profileId = (Integer) getProfileParams.get(Constants.Param.ACCOUNT_ID);
        Users profile = em.find(Users.class, profileId);
        if(profile != null){
            // Profile exist, send it back to the client
            responseParams.put(Constants.Param.VALUE, Constants.Param.SUCCESS);
            responseParams.put(Constants.Param.PROFILE, profile);
        }else{
            // Profile does not exist, send back "Profile not found"
            responseParams.put(Constants.Param.VALUE, Constants.Param.PROFILE_NOT_FOUND);
        }
    }

    /**
     * Only accepts login via facebook for now.
     */
    private void login(Map<String, Object> jsonrpc2Params) throws Exception {
        Map<String, Object> loginParams = getParams(jsonrpc2Params, new Pair(Constants.Param.TOKEN, true));
        //TODO: Should not accept token to be null. Also should use token to check the users credentials

        int facebookId = userId;
        String query = "SELECT u FROM Users u WHERE u.facebookId = :" + Constants.Query.FACEBOOK_ID;
        Users user;
        try{
            user = em.createQuery( query, Users.class).setParameter(Constants.Query.FACEBOOK_ID, facebookId).getSingleResult();
            // User found by facebook Id
            responseParams.put(Constants.Param.VALUE, Constants.Param.SUCCESS);
        }catch(NoResultException e){
            // User not found by facebook Id. Create user.
            user = new Users(null, facebookId, null, null, Constants.Database.NO_LIMIT_PER_DAY, true, true);
            persistObjects(user);
            responseParams.put(Constants.Param.VALUE, Constants.Param.FIRST_LOGIN);
        }
        responseParams.put(Constants.Param.ACCOUNT, serialize(user));
    }
}
