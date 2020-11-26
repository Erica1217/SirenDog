package com.teamteam.sirendog;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.teamteam.sirendog.list.PhoneArrayList;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class EmergencyCallActivity extends AppCompatActivity implements PhoneAddDialog.DialogListener {

    private static final long serialVersionUID = 1L;
    PhoneArrayList<Phone> data = new PhoneArrayList<>();
    AesAlgorithm aesAlgorithm = new AesAlgorithm(this);
    PhoneAdapter mAdapter = new PhoneAdapter(this,data);

    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.CALL_PHONE};

    private static final int PERMISSION_READ = 1;
    private static final int PERMISSION_WRITE = 2;
    private static final int PERMISSION_PHONE_CALL = 3;

    private boolean hasReadPermission = false;
    private boolean hasWritePermission = false;
    private boolean  hasCallPermission= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_call);

        FloatingActionButton btnPlus = findViewById(R.id.btn_plus);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        getPermissions();
        try {
            //String cipherText =  asAlgorithm.decryption();
            //byte[] serializedMember = Base64.getDecoder().decode(cipherText);
            byte[] serializedMember = aesAlgorithm.decryption();
            try (ByteArrayInputStream bais = new ByteArrayInputStream(serializedMember)) {
                try (ObjectInputStream ois = new ObjectInputStream(bais)) {
                    // 역직렬화된 Member 객체를 읽어온다.
                    Object objectMember = ois.readObject();
                    data.append((PhoneArrayList<Phone>) objectMember);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(data.length()==0)
        {
            data.append(new Phone("경찰서", "112"));
        }

        recyclerView.setAdapter(mAdapter);

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] serializedMember;
            try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(data);
                // serializedMember -> 직렬화된 member 객체
                serializedMember = baos.toByteArray();
            }
            aesAlgorithm.encryption(serializedMember);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestPermissions(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED  || (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    PERMISSION_READ);
        }
    }


    public void getPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            requestPermissions();
        }
        else {
            hasReadPermission=true;
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            requestPermissions();
        }
        else {
            hasWritePermission = true;
        }
    }

    public void openDialog() {
        PhoneAddDialog exampleDialog = new PhoneAddDialog();
        exampleDialog.show(getSupportFragmentManager(), "연락처 추가");
    }

    @Override
    public void applyTexts(String name, String password) {

        data.append(new Phone(name, password));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_READ: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    hasReadPermission=true;
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case PERMISSION_WRITE:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    hasWritePermission = true;
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

}