package com.example.pdfupload;

import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api
{


    @FormUrlEncoded
    @POST("upload_document.php")
    //Now Create a class named as ResponsePOJO
    Call<ResponsePOJO> UploadDocument(
            @Field("PDF") String encodedPDF
    );


}
