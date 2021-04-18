package com.globalaccelerex.openinghours.vo.response;


import com.globalaccelerex.openinghours.utils.Constants;

public class ErrorResponse extends ServiceResponse {

    public ErrorResponse(String message){
        super(false, message, null);
    }

    public ErrorResponse(){
        super(false, Constants.ERROR_PROCESSING, null);
    }
}
