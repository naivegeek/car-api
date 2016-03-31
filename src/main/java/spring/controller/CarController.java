package spring.controller;

/**
 * Created by @author srinath medala on 3/31/16.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

import spring.service.EdmundsService;

@Api(value = "cars", description = "Api related to Car operations")
@RestController("carController")
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private EdmundsService edmundsService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ApiOperation(value = "Get List of all Cars", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 500, message = "SERVER ERROR"),
            @ApiResponse(code = 400, message = "Invalid request params")})
    public
    @ResponseBody
    String getAllCars() {
        return "OK";
    }

    @RequestMapping(value = "/{vin}", method = RequestMethod.GET)
    @ApiOperation(value = "Get All available information related to the VIN number", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 500, message = "SERVER ERROR"),
            @ApiResponse(code = 400, message = "Invalid request params")})
    public
    @ResponseBody
    String getCarInfo(@PathVariable("vin")String vin) {
        return edmundsService.getCarInfo(vin);
    }


}
