package com.app.nationalpaints.services;


import com.app.nationalpaints.models.AreaModel;
import com.app.nationalpaints.models.GovernmentModel;
import com.app.nationalpaints.models.MyPointsDataModel;
import com.app.nationalpaints.models.PlaceGeocodeData;
import com.app.nationalpaints.models.PlaceMapDetailsData;
import com.app.nationalpaints.models.PrizeDataModel;
import com.app.nationalpaints.models.ShopGalleryDataModel;
import com.app.nationalpaints.models.ShopGalleryModel;
import com.app.nationalpaints.models.SliderDataModel;
import com.app.nationalpaints.models.StatusResponse;
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
    @GET("place/findplacefromtext/json")
    Call<PlaceMapDetailsData> searchOnMap(@Query(value = "inputtype") String inputtype,
                                          @Query(value = "input") String input,
                                          @Query(value = "fields") String fields,
                                          @Query(value = "language") String language,
                                          @Query(value = "key") String key
    );

    @GET("geocode/json")
    Call<PlaceGeocodeData> getGeoData(@Query(value = "latlng") String latlng,
                                      @Query(value = "language") String language,
                                      @Query(value = "key") String key);


    @FormUrlEncoded
    @POST("api/login")
    Call<UserModel> login(@Field("phone_code") String phone_code,
                          @Field("phone") String phone

    );

    @FormUrlEncoded
    @POST("api/register")
    Call<UserModel> signUpWithoutImage(@Field("first_name") String first_name,
                                       @Field("second_name") String second_name,
                                       @Field("last_name") String last_name,
                                       @Field("phone_code") String phone_code,
                                       @Field("phone") String phone,
                                       @Field("national_ID") String national_ID,
                                       @Field("address") String address,
                                       @Field("latitude") double latitude,
                                       @Field("longitude") double longitude,
                                       @Field("governorate_id") String governorate_id,
                                       @Field("city_id") String city_id,
                                       @Field("software_type") String software_type
    );

    @Multipart
    @POST("api/register")
    Call<UserModel> signUpWithImage(@Part("first_name") RequestBody first_name,
                                    @Part("second_name") RequestBody second_name,
                                    @Part("last_name") RequestBody last_name,
                                    @Part("phone_code") RequestBody phone_code,
                                    @Part("phone") RequestBody phone,
                                    @Part("national_ID") RequestBody national_ID,
                                    @Part("address") RequestBody address,
                                    @Part("latitude") RequestBody latitude,
                                    @Part("longitude") RequestBody longitude,
                                    @Part("governorate_id") RequestBody governorate_id,
                                    @Part("city_id") RequestBody city_id,
                                    @Part("software_type") RequestBody software_type,
                                    @Part MultipartBody.Part logo


    );

    @GET("api/allGovernorates")
    Call<GovernmentModel> getGovernate();

    @FormUrlEncoded
    @POST("api/citiesByGovernorateId")
    Call<AreaModel> getArea(@Field("governorate_id") int governorate_id);


    @GET("api/sliders")
    Call<SliderDataModel> getSlider();

    @GET("api/getMyPoints")
    Call<MyPointsDataModel> getMyPoints(@Header("Authorization") String user_token,
                                        @Query("from_date") String from_date,
                                        @Query("to_date") String to_date,
                                        @Query("order_by") String order_by
    );

    @GET("api/getAllPrizes")
    Call<PrizeDataModel> getPrize();


    @FormUrlEncoded
    @POST("api/changePointWithPrize")
    Call<StatusResponse> exchangePoints(@Header("Authorization") String user_token,
                                        @Field("prize_id") int prize_id
                                   );

    @GET("api/getCurrentUserData")
    Call<UserModel> getUserById(@Header("Authorization") String user_token);


    @GET("api/getAllColorShows")
    Call<ShopGalleryDataModel> getShopGallery(@Query("governorate_id") String governorate_id);
}

