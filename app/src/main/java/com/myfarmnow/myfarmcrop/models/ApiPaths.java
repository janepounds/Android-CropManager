package com.myfarmnow.myfarmcrop.models;

public class ApiPaths {

    public final static String LOCAL_URL = "http://10.0.2.2:8000";
    public final static String LIVE_SERVER_URL = "http://cattle.myfarmnow.com";
    public final static String SERVER_URL = LIVE_SERVER_URL;
    //public final static String SERVER_URL = LOCAL_URL;
    public static final String CROP_LOGIN_GET_ALL =  SERVER_URL + "/api/crop/user/authenticate";;
    public static final String CROP_CREATE_USER =  SERVER_URL + "/api/crop/user/create";;
    public static final String CROP_USER_UPDATE =  SERVER_URL + "/api/crop/user/update";;
    public static final String CROP_USER_VERIFY_PHONE =  SERVER_URL + "/api/crop/user/verify/code";
    public static final String CROP_USER_RESEND_CODE =  SERVER_URL + "/api/crop/user/resend/code";
    public static final String CROP_FORGOT_PASSWORD_REQUEST_CODE =  SERVER_URL + "/api/crop/user/forgot/password/request/code";
    public static final String CROP_FORGOT_PASSWORD_CHANGE =  SERVER_URL + "/api/crop/user/forgot/password/change";

}
