package ru.korobeynikov.myweatherapplication.json;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebApi {
    @GET("weather?units=metric")
    Observable<Root> root(@Query("q") String city, @Query("appid") String apiKey);
}