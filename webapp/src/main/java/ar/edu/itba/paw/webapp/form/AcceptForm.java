package ar.edu.itba.paw.webapp.form;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AcceptForm {

    private  String description;

    @Min(1)
    @NotNull
    private Integer price;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
