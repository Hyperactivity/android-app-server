package handlers;

import assistant.Constants;
import assistant.SharedHandler;
import assistant.pair.NullableExtendedParam;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import models.Account;

import javax.persistence.NoResultException;
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
                Constants.Method.REGISTER,
                Constants.Method.GET_ACCOUNT,
                Constants.Method.UPDATE_ACCOUNT,

        };
    }

    @Override
    public void process(Map<String, Object> jsonrpc2Params) throws Exception {
        if(method.equals(Constants.Method.LOGIN)){
            login(jsonrpc2Params);
        }
        else if(method.equals(Constants.Method.GET_ACCOUNT)){
            getAccount(jsonrpc2Params);
        }
        else if(method.equals(Constants.Method.UPDATE_ACCOUNT)){
            updateAccount(jsonrpc2Params);
        }
        else if(method.equals(Constants.Method.REGISTER)){
            register(jsonrpc2Params);
        }
        else{
            throwJSONRPC2Error(JSONRPC2Error.METHOD_NOT_FOUND, Constants.Errors.METHOD_NOT_FOUND, method);
        }
    }

    private void updateAccount(Map<String, Object> jsonrpc2Params) throws Exception {
        Map<String, Object> params = getParams(jsonrpc2Params,
                new NullableExtendedParam(Constants.Param.Name.DESCRIPTION, true),
                new NullableExtendedParam(Constants.Param.Name.SHOW_BIRTH_DATE, true),
                new NullableExtendedParam(Constants.Param.Name.AVATAR, true));

        String description = (String) params.get(Constants.Param.Name.DESCRIPTION);
        Boolean showBirthDate = (Boolean) params.get(Constants.Param.Name.SHOW_BIRTH_DATE);
        //TODO: handle Avatar

        if(description != null){
            clientAccount.setProfileDescription(description);
        }
        if(showBirthDate != null){
            clientAccount.setShowBirthDate(showBirthDate);
        }
        persistObjects(clientAccount);

        responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.SUCCESS);
        responseParams.put(Constants.Param.Name.ACCOUNT, serialize(clientAccount));
    }

    private void getAccount(Map<String, Object> jsonrpc2Params) throws Exception{
        Map<String, Object> params = getParams(jsonrpc2Params, Constants.Param.Name.ACCOUNT_ID);

        int accountId = (Integer) params.get(Constants.Param.Name.ACCOUNT_ID);
        if(clientAccount != null){
            // Account exist, send it back to the client
            responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.SUCCESS);
            responseParams.put(Constants.Param.Name.ACCOUNT, serialize(clientAccount));
        }else{
            // Account does not exist, send back "Account not found"
            responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.ACCOUNT_NOT_FOUND);
        }
    }

    /**
     * Only accepts login via facebook for now.
     */
    private void login(Map<String, Object> jsonrpc2Params) throws Exception {
        Map<String, Object> params = getParams(jsonrpc2Params, new String[0]); // No params
        //TODO: Should not accept token to be null. Also should use token to check the users credentials

        int facebookId = accountId;
        String query = "SELECT u FROM Account u WHERE u.facebookId = :" + Constants.Query.FACEBOOK_ID;
        try{
            clientAccount = em.createQuery( query, Account.class).setParameter(Constants.Query.FACEBOOK_ID, facebookId).getSingleResult();
            // User found by facebook Id
            validateUser(clientAccount, facebookToken);
            persistObjects(clientAccount);
            responseParams.put(Constants.Param.Name.ACCOUNT, serialize(clientAccount));
            responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.SUCCESS);
        }catch(NoResultException e){
            // User not found by facebook Id. tell user to register account
            responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.REGISTER);
        }
    }

    private void register(Map<String, Object> jsonrpc2Params) throws Exception{
        Map<String, Object> params = getParams(jsonrpc2Params,
                new NullableExtendedParam(Constants.Param.Name.USERNAME, false),
                new NullableExtendedParam(Constants.Param.Name.DESCRIPTION, true),
                new NullableExtendedParam(Constants.Param.Name.SHOW_BIRTH_DATE, true),
                new NullableExtendedParam(Constants.Param.Name.AVATAR, true));

        int facebookId = accountId;
        String username = (String) params.get(Constants.Param.Name.USERNAME);
        String description = (String) params.get(Constants.Param.Name.DESCRIPTION);
        Boolean showBirthDate = (Boolean) params.get(Constants.Param.Name.SHOW_BIRTH_DATE);
        if(showBirthDate == null){
            showBirthDate = false;
        }
        //TODO: Avatar
        String query = "SELECT u FROM Account u WHERE u.username = :" + Constants.Query.USERNAME;

        try{
            clientAccount = em.createQuery( query, Account.class).setParameter(Constants.Query.USERNAME, username).getSingleResult();
            responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.USERNAME_TAKEN);
            return;
        }catch(NoResultException e){
        // This is good!
//            new Date()userAndApp.me.getBirthday()
            clientAccount = new Account(username, facebookId, description, null , Constants.Database.NO_LIMIT_PER_DAY, Constants.Database.DEFAULT_USE_DEFAULT_COLORS, showBirthDate, facebookToken );
            persistObjects(clientAccount);
            responseParams.put(Constants.Param.Name.ACCOUNT, serialize(clientAccount));
            responseParams.put(Constants.Param.Status.STATUS, Constants.Param.Status.SUCCESS);
        }
    }
}