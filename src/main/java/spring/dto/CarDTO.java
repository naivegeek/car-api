package spring.dto;

import java.io.Serializable;

/*
 * @author srinath medala
 */
public class CarDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long year;
    private String make;
    private String model;

    private String editorReview;
    private String averageConsumerRating;

    private String cityMpg;
    private String highwayMpg;

    private String engineFuelType;

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getEditorReview() {
        return editorReview;
    }

    public void setEditorReview(String editorReview) {
        this.editorReview = editorReview;
    }

    public String getAverageConsumerRating() {
        return averageConsumerRating;
    }

    public void setAverageConsumerRating(String averageConsumerRating) {
        this.averageConsumerRating = averageConsumerRating;
    }

    public String getCityMpg() {
        return cityMpg;
    }

    public void setCityMpg(String cityMpg) {
        this.cityMpg = cityMpg;
    }

    public String getHighwayMpg() {
        return highwayMpg;
    }

    public void setHighwayMpg(String highwayMpg) {
        this.highwayMpg = highwayMpg;
    }

    public String getEngineFuelType() {
        return engineFuelType;
    }

    public void setEngineFuelType(String engineFuelType) {
        this.engineFuelType = engineFuelType;
    }
    
    
}
