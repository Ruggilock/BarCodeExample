package absortio.barcodeexample.utils;

import absortio.barcodeexample.model.ModelCodebar;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ServiceInterface {

    @POST("saveDefault")
    @FormUrlEncoded
    Call<ModelCodebar> saveBarcode(@Field("String") String barcode);
}
