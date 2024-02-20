package com.momground.android.response;

import java.util.Locale;


public enum ErrorCode {

    ERROR_NONE(1, "ERROR_NONE"),

    //Todo Login Error 1000~
    //spring security 자체 로그인 에러
    NOT_FOUND_USER(1000, "Not found User"), //일반 로그인 시
    BAD_CREDENTIALS(1001, "Bad credentials"),
    NOT_FOUND_PERSONAL_USER(1010, "NOT_FOUND_PERSONAL_USER"), //이중 로그인 시

    //Todo Point Error 2000~
    NOT_ENOUGH_POINT(2000, "NOT_ENOUGH_POINT"),
    NOT_ADD_POINT_USE_LOG(2001, "NOT_ADD_POINT_USE_LOG"),
    NOT_ADD_POINT_BUY_LOG(2002, "NOT_ADD_POINT_BUY_LOG"),
    FAIL_USE_POINT(2003, "FAIL_USE_POINT"),
    FAIL_CHARGE_POINT(2004, "FAIL_CHARGE_POINT"),
    POINT_ALREADY_USED(2005),

    //Training Error - 3000~4000
    // 000 code
    // 100 simple
    // 200 music
    // 300 face
    // 400 block
    // 500 tracing
    // 600 chosung word
    // 700 chosung hint
    // 800 coin
    // 900 stimulus
    // 1000 idiom
    CHOSUNG_WORD_NOT_FOUND(4600),
    FAIL_GET_SAMPLE_ANS(4601,"FAIL_GET_SAMPLE_ANS"),
    FAIL_LOAD_CATEGORY(4701,"FAIL_LOAD_CATEGORY"),

    //ADMIN Error - 5000
    NOT_ALLOWED_USER(5000, "NOT_ALLOWED_USER"),
    FAIL_LOAD_INST_QUEST_STAT(5001, "FAIL_LOAD_INST_QUEST_STAT"),
    NO_INST_FOUND(5002, "NO_INST_FOUND"),
    INVALID_PARAMETER(5003, "INVALID_PARAMETER"),
    FAIL_LOAD_PUSH_INFO(5004, "FAIL_LOAD_PUSH_INFO"),

    //GROUP Error - 6000
    GROUP_NOT_FOUND(6000, "GROUP_NOT_FOUND"),
    FAIL_CREATE_GROUP(6001, "FAIL_CREATE_GRUOP"),
    FAIL_UPDATE_GROUP(6002, "FAIL_UPDATE_GRUOP"),
    FAIL_DELETE_GROUP(6003, "FAIL_DELETE_GROUP"),
    FAIL_DELETE_USER_FROM_GROUP(6004, "FAIL_DELETE_USER_FROM_GROUP"),

    // Purchase - 7000
    PURCHASE_INVALID_TOKEN(7000, "PURCHASE_INVALID_TOKEN"),
    PURCHASE_INITIALIZATION_FAILED(7001, "PURCHASE_INITIALIZATION_FAILED"),
    PURCHASE_NOT_SUPPORTED(7002, "PURCHASE_NOT_SUPPORTED"),

    // User Error - 8000
    CANCELED(8000, "USER_CANCELED"),
    FAIL_USER_UPDATE(8001, "FAIL_USER_UPDATE"),
    NOT_FOUND_USERNAME(8002,"NOT_FOUND_USERNAME"),
    NOT_FOUND_PASSWORD(8003,"NOT_FOUND_PASSWORD"),
    NOT_VOID_EMAIL(8004,"NOT_VOID_EMAIL"),
    NOT_FOUND_CORRECT_DATA(8005,"NOT_FOUND_USERNAME"),

    //Todo cog common
    NOT_CREATE_COG_USER(10001,"NOT_CREATE_COG_USER"),
    FAIL_ADD_FILE(10002,"FAIL_ADD_FILE"),
    //Todo CST
    NOT_INSERT_CST_DATA(11001,"NOT_INSERT_CST_DATA"),
    NOT_CREATE_COG_CST(11002,"NOT_CREATE_COG_CST"),
    NOT_FOUND_CST_DATA(11003,"NOT_FOUND_CST_DATA"),
    NOT_FOUND_CST_PREVIEW_DATA(11004,"NOT_FOUND_CST_PREVIEW_DATA"),
    //Todo NeST
    NOT_INSERT_NEST_DATA(12001,"NOT_INSERT_NEST_DATA"),
    NOT_CREATE_COG_NEST(12002,"NOT_CREATE_COG_NEST"),
    NOT_FOUND_NEST_DATA(12003,"NOT_FOUND_NEST_DATA"),
    NOT_FOUND_NEST_PREVIEW_DATA(12004,"NOT_FOUND_NEST_PREVIEW_DATA"),

    //Todo Sast
    NOT_INSERT_SAST_DATA(13001,"NOT_INSERT_SAST_DATA"),
    NOT_CREATE_COG_SAST(13002,"NOT_CREATE_COG_SAST"),
    NOT_FOUND_SAST_DATA(13003, "NOT_FOUND_SAST_DATA"),
    NOT_FOUND_SAST_PREVIEW_DATA(13004,"NOT_FOUND_SAST_PREVIEW_DATA"),

    //Todo Vast
    NOT_INSERT_VAST_DATA(14001,"NOT_INSERT_VAST_DATA"),
    NOT_CREATE_COG_VAST(14002,"NOT_CREATE_COG_VAST"),
    NOT_FOUND_VAST_DATA(14003, "NOT_FOUND_VAST_DATA"),
    NOT_FOUND_VAST_PREVIEW_DATA(14004,"NOT_FOUND_SAST_PREVIEW_DATA"),


    REQUEST_IS_CANCELED(9996, "REQUEST_IS_CANCELED"),
    DATA_IS_NULL(9997, "DATA_IS_NULL"),
    UNKNOWN_HOST_ERROR(9998, "UNKNOWN_HOST_ERROR"),
    UNKNOWN_ERROR(9999, "UNKNOWN_ERROR");

    public final int code;
    public final String name;

    ErrorCode(int code, String name) {
        this.code = code;
        this.name = name;
    }

    ErrorCode(int code) {
        this.code = code;
        this.name = name();
    }

    public static ErrorCode getErrorCode(String errorName) {
        if (errorName == null)
            return UNKNOWN_ERROR;

        ErrorCode[] codes = values();

        for (int i = 0; i < codes.length; i++) {
            if (errorName.trim().equals(codes[i].name))
                return codes[i];
        }

        return UNKNOWN_ERROR;
    }
    
    public String describe() {
        return String.format(Locale.getDefault(), "%s(%d)", name, code);
    }
}
