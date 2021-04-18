package com.globalaccelerex.openinghours.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.globalaccelerex.openinghours.utils.Constants;
import com.globalaccelerex.openinghours.vo.response.ServiceResponse;
import com.globalaccelerex.openinghours.utils.Utils;
import com.globalaccelerex.openinghours.vo.Enums.Type;
import com.globalaccelerex.openinghours.vo.OpenHoursDto;
import com.globalaccelerex.openinghours.vo.response.ErrorResponse;
import com.globalaccelerex.openinghours.vo.response.SuccessResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OpenHourService {

    @Autowired
    ObjectMapper mapper;

    public ServiceResponse getReadableOpenHours(String jsonOpenHour) {
        try {
            JSONObject jsonOpenHourObj = null;
            LinkedHashMap<String, String> response = new LinkedHashMap<>();
            try {
                jsonOpenHourObj = new JSONObject(jsonOpenHour);
            } catch (JSONException ex) {
                return new ErrorResponse(Constants.INVALID_JSON_STRING);
            }

            if (jsonOpenHourObj.has("monday")) {
                response.put("Monday", processOpenHours(jsonOpenHourObj, "monday", response));
            }
            if (jsonOpenHourObj.has("tuesday")) {
                response.put("Tuesday", processOpenHours(jsonOpenHourObj, "tuesday", response));
            }
            if (jsonOpenHourObj.has("wednesday")) {
                response.put("Wednesday", processOpenHours(jsonOpenHourObj, "wednesday", response));
            }
            if (jsonOpenHourObj.has("thursday")) {
                response.put("Thursday", processOpenHours(jsonOpenHourObj, "thursday", response));
            }
            if (jsonOpenHourObj.has("friday")) {
                response.put("Friday", processOpenHours(jsonOpenHourObj, "friday", response));
            }
            if (jsonOpenHourObj.has("saturday")) {
                response.put("Saturday", processOpenHours(jsonOpenHourObj, "saturday", response));
            }
            if (jsonOpenHourObj.has("sunday")) {
                response.put("Sunday", processOpenHours(jsonOpenHourObj, "sunday", response));
            }

            return new SuccessResponse(response);
        } catch (Exception ex) {
            return new ErrorResponse(Constants.ERROR_PROCESSING);
        }
    }

    public String processOpenHours(JSONObject jsonOpenHourObj, String day, LinkedHashMap<String, String> response) throws JsonProcessingException {
        StringBuilder builder = new StringBuilder();
        List<OpenHoursDto> openHoursList = Arrays.asList(mapper.readValue(jsonOpenHourObj.getJSONArray(day).toString(), OpenHoursDto[].class));
        if (openHoursList.size() > 0) {
            for (int i = 0; i < openHoursList.size(); i++) {
                if (i == 0 && openHoursList.get(i).getType().equalsIgnoreCase(Type.CLOSE.name())) {
                    String previousDay = Utils.lookupPreviousDay(day);
                    String value = response.get(previousDay);
                    value = value.concat(" - " + Utils.convertUnixTimeToDate(openHoursList.get(i).getValue()));

                    response.replace(previousDay, value);
                } else {
                    if (openHoursList.get(i).getType().equalsIgnoreCase(Type.OPEN.name())) {
                        builder.append(Utils.convertUnixTimeToDate(openHoursList.get(i).getValue()));
                    } else if (openHoursList.get(i).getType().equalsIgnoreCase(Type.CLOSE.name())) {
                        builder.append(" - " + Utils.convertUnixTimeToDate(openHoursList.get(i).getValue()) + ", ");
                    }
                }
            }
        } else {
            builder.append("Closed");
        }
        return builder.toString().trim();
    }
}
