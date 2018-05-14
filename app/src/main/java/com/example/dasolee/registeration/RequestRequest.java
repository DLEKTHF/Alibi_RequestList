package com.example.dasolee.registeration;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestRequest extends  StringRequest{

        final static private String URL = "http://dleekthf.cafe24.com/RequestList.php";
        private Map<String, String> parameters;

    public RequestRequest(String requestTitle, String requestContent, Response.Listener<String>listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("requestTitle",requestTitle);
        parameters.put("requestContent",requestContent);
    }

        @Override
        public Map<String, String> getParams() {
        return  parameters;
    }
}
