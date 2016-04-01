package spring.service.impl;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import spring.dto.CarDTO;
import spring.service.EdmundsService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by @author srinath medala on 3/31/16.
 */
@Service("edmundsService")
public class EdmundsServiceImpl implements EdmundsService {

    static Logger log = LoggerFactory.getLogger(EdmundsServiceImpl.class);

    public String getCarInfo(String vin) {
        String url = "https://api.edmunds.com/api/vehicle/v2/vins/" + vin
                + "?fmt=json&api_key=ymg7mstwnse27aar9259tn7q";
        String json = null;
        try {
            json = makeHttpRequest(url);
            if (json != null) {
                CarDTO car = new CarDTO();
                car = tranformJSONRespone(json);
                if (car != null && StringUtils.hasText(car.getMake()) && StringUtils.hasText(car.getModel())
                        && (car.getYear() != null)) {
                    car = getEditorialReviews(car);
                    car = getAverageCustomerRating(car);
                }
                if (car != null) {
                    json = convertToJSON(car);
                }
            }
        } catch (Exception e) {
            log.error(" Error in making a call to Edmunds REST API: {} ", url, e.getMessage());
        }
        return json;
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

    private CarDTO getEditorialReviews(CarDTO dto) {
        String editorialUrl = "https://api.edmunds.com/v1/content/editorreviews?make=" + dto.getMake() + "&model="
                + dto.getModel() + "&year=" + dto.getYear() + "&fmt=json&api_key=ymg7mstwnse27aar9259tn7q";
        try {
            String json = makeHttpRequest(editorialUrl);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
            if (jsonObject != null) {
                JSONObject editorial = (JSONObject) jsonObject.get("editorial");
                if (editorial != null) {
                    dto.setEditorReview((String) editorial.get("edmundsSays"));
                }
            }
        } catch (Exception e) {
            log.error(" Error in making a call to Edmunds REST API: {} ", editorialUrl, e.getMessage());
        }
        return dto;

    }

    private CarDTO getAverageCustomerRating(CarDTO dto) {
        String customerReviewUrl = "https://api.edmunds.com/api/vehiclereviews/v2/" + dto.getMake().toLowerCase() + "/"
                + dto.getModel().toLowerCase() + "/" + dto.getYear() + "?fmt=json&api_key=ymg7mstwnse27aar9259tn7q";
        try {
            String json = makeHttpRequest(customerReviewUrl);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
            if (jsonObject != null) {
                String averageRating = (String) jsonObject.get("averageRating");
                dto.setAverageConsumerRating(averageRating);
            }
        } catch (Exception e) {
            log.error(" Error in making a call to Edmunds REST API: {} ", customerReviewUrl, e.getMessage());
        }
        return dto;
    }

    private String makeHttpRequest(String url) {
        String json = null;
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            if (response != null) {
                json = response.body().string();
            }
        } catch (Exception e) {
            log.error(" Error in making a call to Edmunds REST API: {} ", url, e.getMessage());
        }
        return json;
    }
}
