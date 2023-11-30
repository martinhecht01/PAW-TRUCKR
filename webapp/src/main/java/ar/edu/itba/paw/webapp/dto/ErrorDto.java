package ar.edu.itba.paw.webapp.dto;

public class ErrorDto {

    private String message;

    public static ErrorDto fromErrorMessage(String message){
        ErrorDto dto = new ErrorDto();
        dto.message = message;
        return dto;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
