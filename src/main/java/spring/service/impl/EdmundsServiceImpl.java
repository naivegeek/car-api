package spring.service.impl;

import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import spring.service.EdmundsService;

/**
 * Created by @author srinath medala on 3/31/16.
 */
@Service("edmundsService")
public class EdmundsServiceImpl implements EdmundsService {

    public String getCarInfo(String vin) {
        String url = "https://api.edmunds.com/api/vehicle/v2/vins/" + vin+ "?fmt=json&api_key=ymg7mstwnse27aar9259tn7q";
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            String json = response.body().string();
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
            if(jsonObject.get("make")!=null){
                Map make = (Map)jsonObject.getOrDefault("make", null);
                System.out.println("make: "+make);
            }
            return json;
        } catch (Exception e) {

        }
        return null;
    }

}
