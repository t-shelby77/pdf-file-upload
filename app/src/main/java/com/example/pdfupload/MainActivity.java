package com.example.pdfupload;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;




public class MainActivity extends AppCompatActivity {

    private Button btn_upload,btn_select;
    private TextView text_view;

    private int REQ_PDF = 21;
    private String encodedPDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_view = findViewById(R.id.text_view);
        btn_select = findViewById(R.id.btn_select);
        btn_upload = findViewById(R.id.btn_upload);

        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("application/pdf");
                chooseFile = Intent.createChooser(chooseFile, "Choose a PDF File");

                startActivityForResult(chooseFile,REQ_PDF);

            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadDocument();
            }
        });
    }

    private void uploadDocument() {

        //UploadDocument in available in the Api.java
        Call<ResponsePOJO> call = RetroFitClient.getInstance().getAPI().UploadDocument(encodedPDF);

        call.enqueue(new Callback<ResponsePOJO>() {

            @Override
            public void onResponse(Call<ResponsePOJO> call, Response<ResponsePOJO> response) {


                Toast.makeText(MainActivity.this, response.body().getRemarks(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ResponsePOJO> call, Throwable t) {
                t.printStackTrace();

                Toast.makeText(MainActivity.this, "Network Failed", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_PDF && resultCode == RESULT_OK && data!=null){
            Uri path = data.getData();


            try {
                //mycode
                InputStream inputStream = MainActivity.this.getContentResolver().openInputStream(path);
                byte[] pdfInBytes = new byte[inputStream.available()];
                inputStream.read();
                encodedPDF = Base64.encodeToString(pdfInBytes,Base64.DEFAULT);

                Toast.makeText(this,"Document is selected",Toast.LENGTH_SHORT).show();




            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
