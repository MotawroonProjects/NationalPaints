package com.app.nationalpaints.services;


import com.app.nationalpaints.models.UserModel;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface Service {



    @FormUrlEncoded
    @POST("api/login")
    Call<UserModel> login(@Field("phone_code") String phone_code,
                          @Field("phone") String phone

    );

    @FormUrlEncoded
    @POST("api/register")
    Call<UserModel> signUpWithoutImage(@Field("name") String name,
                                       @Field("phone_code") String phone_code,
                                       @Field("phone") String phone,
                                       @Field("email") String email,

                                       @Field("address") String address,
                                       @Field("latitude") double latitude,
                                       @Field("longitude") double longitude,
                                       @Field("software_type") String software_type
    );

    @Multipart
    @POST("api/register")
    Call<UserModel> signUpWithImage(@Part("name") RequestBody name,
                                    @Part("phone_code") RequestBody phone_code,
                                    @Part("phone") RequestBody phone,
                                    @Part("email") RequestBody email,
                                    @Part("address") RequestBody address,
                                    @Part("latitude") RequestBody latitude,
                                    @Part("longitude") RequestBody longitude,
                                    @Part("software_type") RequestBody software_type,
                                    @Part MultipartBody.Part logo


    );





}

