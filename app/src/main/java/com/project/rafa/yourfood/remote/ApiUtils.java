package com.project.rafa.yourfood.remote;

public class ApiUtils {
    public static String BASE_URL = "http://proyeto-81.appspot.com/";

    public static ApiService getApiServices(){
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }

    public static void changeApiBaseUrl(String newApiBaseUrl) {
        BASE_URL = newApiBaseUrl;
    }

}
