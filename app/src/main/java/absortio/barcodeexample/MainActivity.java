package absortio.barcodeexample;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.CAMERA},
                1);

    }
    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode ==0){
            if(resultCode== CommonStatusCodes.SUCCESS){
                if(data!=null){
                    Barcode barcode = data.getParcelableExtra("barcode");

                    barcode_result.setText(R.string.barcodevalues + barcode.displayValue);
                }else{
                    barcode_result.setText(R.string.barcodenofound);
                }
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    public void saveBarcode(View view) {
        serviceInterface.saveBarcode(barcode_result.getText().toString()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Context context = getApplicationContext();
                CharSequence text = "Congrats";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Context context = getApplicationContext();
                CharSequence text = "Error No servidor !";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(this, ScanBarcodeActivity.class);
                    startActivityForResult(intent,0);
                } else {
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }
}
