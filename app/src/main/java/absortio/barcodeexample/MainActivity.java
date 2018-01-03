package absortio.barcodeexample;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import absortio.barcodeexample.utils.Service;
import absortio.barcodeexample.utils.ServiceInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView barcode_result;
    ServiceInterface serviceInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceInterface = Service.userService();

        setContentView(R.layout.activity_main);
        barcode_result= findViewById(R.id.barcode_result);
    }

    public void scanBarcode(View v) {
        Intent intent = new Intent(this, ScanBarcodeActivity.class);
        startActivityForResult(intent,0);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode ==0){
            if(resultCode== CommonStatusCodes.SUCCESS){
                if(data!=null){
                    Barcode barcode = data.getParcelableExtra("barcode");
                    saveBarcode(barcode.toString());
                    barcode_result.setText(R.string.barcodevalues + barcode.displayValue);
                }else{
                    barcode_result.setText(R.string.barcodenofound);
                }
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void saveBarcode(final String barcode){
        serviceInterface.saveBarcode(barcode).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                System.out.println(barcode);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
