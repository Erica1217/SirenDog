package com.teamteam.sirendog;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ExampleBottomSheetDialog extends BottomSheetDialogFragment {

    private static final int PHONE_CALL_PERMISSION = 1;
    private boolean  mCallPermissionGranted= false;
    private BottomSheetListener mListener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_bottom_sheet, container, false);
        TextView nameTextview = v.findViewById(R.id.text_name);
        TextView phoneTextview = v.findViewById(R.id.text_phone);

        Button callBtn = v.findViewById(R.id.btn_call);

        Bundle args = getArguments();
        String phone = args.getString("phone");
        String name = args.getString("name");

        nameTextview.setText(name);
        phoneTextview.setText(phone);

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCallPermission();
                if(!mCallPermissionGranted)
                {
                    getCallPermission();
                    return;
                }
                if(phone == null || phone.length() <= 3) {
                    Toast.makeText(getContext(), "전화번호 정보가 없습니다. ",Toast.LENGTH_SHORT).show();
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle("전화")
                        .setMessage("정말로 전화를 거시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent policecall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phone.replace("-","")));
                                startActivity(policecall);
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dismiss();
                            }
                        });

                AlertDialog alertDialog = builder.create();

                alertDialog.show();
            }
        });
        return v;
    }

    private void getCallPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            mCallPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE},
                    PHONE_CALL_PERMISSION);
        }
    }

    public interface BottomSheetListener {
        void onButtonClicked(String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        try {
//            mListener = (BottomSheetListener) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString()
//                    + " must implement BottomSheetListener");
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PHONE_CALL_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mCallPermissionGranted = true;
                }
            }
        }
    }
}