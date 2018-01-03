package absortio.barcodeexample.utils;


public class Service {
    public static final String BASE_URL = "https://demobarcode.herokuapp.com/";

    public static ServiceInterface userService() {
        return RetrofitClient.getClient(BASE_URL).create(ServiceInterface.class);
    }

}
