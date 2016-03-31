package spring.service;

/**
 * Created by @author srinath medala on 3/31/16.
 */
public interface EdmundsService {

    /*
    http://edmunds.mashery.com/io-docs

    https://api.edmunds.com/v1/content/editorreviews?make=acura&model=tsx&year=2009&fmt=json&api_key=ymg7mstwnse27aar9259tn7q

    https://api.edmunds.com/api/vehiclereviews/v2/acura/tsx/2009?fmt=json&api_key=ymg7mstwnse27aar9259tn7q

    https://api.edmunds.com/api/vehicle/v2/acura/models?state=new&fmt=json&api_key=ymg7mstwnse27aar9259tn7q

    https://api.edmunds.com/api/vehicle/v2/vins/JH4CU26679C029109?fmt=json&api_key=ymg7mstwnse27aar9259tn7q
     */


    String getCarInfo(String vin);

    //String getCarInfo(String )


}
