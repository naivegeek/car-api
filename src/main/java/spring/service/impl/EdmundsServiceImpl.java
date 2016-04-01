package spring.service.impl;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import spring.dto.CarDTO;
import spring.service.EdmundsService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by @author srinath medala on 3/31/16.
 */
@Service("edmundsService")
public class EdmundsServiceImpl implements EdmundsService {

    public String getCarInfo(String vin) {
        String url = "https://api.edmunds.com/api/vehicle/v2/vins/" + vin
                + "?fmt=json&api_key=ymg7mstwnse27aar9259tn7q";
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            String json = response.body().string();
            if (json != null) {
                CarDTO car = new CarDTO();
                car = tranformJSONRespone(json);
                if (car != null) {
                    json = convertToJSON(car);
                }
            }
            return json;
        } catch (Exception e) {

        }
        return null;
    }

    private CarDTO tranformJSONRespone(String json) throws Exception {
        CarDTO car = new CarDTO();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
        if (jsonObject != null) {
            JSONObject makes = (JSONObject) jsonObject.get("make");
            if (makes != null) {
                car.setMake((String) makes.get("name"));
            }
            JSONObject models = (JSONObject) jsonObject.get("model");
            if (models != null) {
                car.setModel((String) models.get("name"));
            }
            JSONArray years = (JSONArray) jsonObject.get("years");
            if (years != null && years.get(0) != null) {
                JSONObject year = (JSONObject) years.get(0);
                if (year != null) {
                    car.setYear((Long) year.get("year"));
                }
            }
            JSONObject mpg = (JSONObject) jsonObject.get("MPG");
            if (mpg != null) {
                car.setCityMpg((String) mpg.get("city"));
                car.setHighwayMpg((String) mpg.get("highway"));
            }
            JSONObject engine = (JSONObject) jsonObject.get("engine");
            if (engine != null) {
                car.setEngineFuelType((String) engine.get("type"));
            }
        }
        return car;
    }

    private String convertToJSON(CarDTO dto) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(dto);
        return jsonInString;
    }
}
