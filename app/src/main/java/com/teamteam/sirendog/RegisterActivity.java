package com.teamteam.sirendog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class RegisterActivity extends AppCompatActivity {
    Button btn_save, btn_cancel;
    EditText edt_phone_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_save = (Button) findViewById(R.id.btn_save);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        edt_phone_number = (EditText)findViewById(R.id.edt_phone_number);

        SharedPreferences sp = getSharedPreferences("EmergencyCall", MODE_PRIVATE);
        String PhoneNumber = sp.getString("PhoneNumber", "");

        edt_phone_number.setText(PhoneNumber);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplication(),EmergencyCallActivity.class);
                startActivity(it);
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplication(),EmergencyCallActivity.class);
                SharedPreferences sp2 = getSharedPreferences("EmergencyCall", MODE_PRIVATE);

                SharedPreferences.Editor speditor = sp2.edit();
                String PhoneNumber2 = edt_phone_number.getText().toString();
                speditor.putString("PhoneNumber", PhoneNumber2);
                speditor.commit();

                startActivity(it);
            }
        });

    }

}
