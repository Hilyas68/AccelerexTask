package com.globalaccelerex.openinghours.vo.response;


import com.globalaccelerex.openinghours.utils.Constants;

public class SuccessResponse extends ServiceResponse {
    public SuccessResponse(String message, Object data) {
        super(true, message, data);
    }

    public SuccessResponse(Object data) {
        super(true, Constants.OPERATION_SUCCESS, data);
    }
}
