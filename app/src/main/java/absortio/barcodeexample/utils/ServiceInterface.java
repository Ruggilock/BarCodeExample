package absortio.barcodeexample.utils;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ServiceInterface {

    @POST("saveDefault")
    @FormUrlEncoded
    Call<String> saveBarcode(@Body String barcode);
}
