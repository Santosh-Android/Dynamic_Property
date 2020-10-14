package in.santosh.property;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//saveBitmapInFile();
        try {

           String name= Util.getProperty("data.lastName",getApplicationContext());
            String age = Util.getProperty("data.firstName",getApplicationContext());
            String ok= Util.getProperty("data.cars[1]",getApplicationContext());
            Log.d("propery file content",name+age+ok);
//            txtname.setText(Util.getProperty("name",getApplicationContext()));
//            txtage.setText(Util.getProperty("age",getApplicationContext()));
//            btnok.setText(Util.getProperty("ok",getApplicationContext()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }
