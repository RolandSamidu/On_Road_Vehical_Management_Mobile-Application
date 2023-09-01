package com.example.onroadvehiclemanagement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("viewMaintain.php")
    Call<Maintain> getMaintain(
            @Query("item_type") String item_type,
            @Query("key") String keyword
    );
}
