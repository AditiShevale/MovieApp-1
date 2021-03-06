package com.example.aditi.movieapp.Service;

import com.example.aditi.movieapp.model.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("movie/{filter}")
    Call<Example> getMovies(@Path("filter") String filter, @Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<Example> getPopularMovies(@Query("api_key") String apiKey);


    @GET("movie/{filter}")
    Call<Example> fetchMoviesPaging(@Path("filter") String filter, @Query("api_key") String apiKey, @Query("page") long page, @Query("pageSize") int pageSize);


}
