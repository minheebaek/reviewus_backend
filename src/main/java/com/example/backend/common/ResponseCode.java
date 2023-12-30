package com.example.backend.common;

public interface ResponseCode {
    //HTTP Status 200
    String SUCCESS = "SU";
    //HTTP Status 400
    String VALIDATION_FAILED = "VF";
    String DUPLICATE_EMAIL = "DE";
    String NOT_EXISTED_BOARD = "NB";
    String WRONG_TOKEN = "WT";

    //HTTP Status 401
    String SIGN_IN_FAIL = "SF";
    String AUTHORIZATION_FAIL = "AF";
    String NOT_EXISTED_USER = "NU";
    String INVALID_TOKEN = "IT";
    String EXPIRED_TOKEN = "ET";
    String UNSUPPORTED_TOKEN = "UT";
    String NOT_FOUND_TOKEN = "NT";
    String PASSWD_FAIL = "PF";
    String NEW_PASSWD_FAIL = "NPF";

    //HTTP Status 403
    String NO_PERMISSION = "NP";

    //HTTP Status 500
    String DATABASE_ERROR = "DBE";
}
