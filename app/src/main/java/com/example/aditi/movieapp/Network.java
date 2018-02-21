package com.example.aditi.movieapp;


import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.example.aditi.movieapp.Adapter.Movie;
import com.example.aditi.movieapp.Adapter.MovieReview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Created by aditi on 10/1/18.
 */

public class Network {
    final static String MOVIE_DB_URL = "http://api.themoviedb.org/3/movie/";

    final static String API_KEY = "api_key";

    final static String api_key = "00bab64ed019eded1ab3d951af1bb2a0";

        public static List<Movie> fetchMovieData(URL url) {

        String jsonResponse = null;
        try {
            jsonResponse = getResponseFromHttpUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Movie> movies = extractFeatureFromJson(jsonResponse);
        return movies;


    }
    //Building URL used to query

    public static URL buildURl(String sort) {
        Uri builtUri = Uri.parse(MOVIE_DB_URL).buildUpon()
                .appendPath(sort)
                .appendQueryParameter(API_KEY, api_key)
                .build();
        Log.i("NewUrl", String.valueOf(builtUri));
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        return url;
    }

    private static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    private static List<Movie> extractFeatureFromJson(String movieJson) {
        if (TextUtils.isEmpty((movieJson))) {
            return null;
        }


        List<Movie> movie = new ArrayList<>();


        try {

            JSONObject baseJsonResponse = new JSONObject(movieJson);


            JSONArray movieArray = baseJsonResponse.getJSONArray("results");


            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject currentMovie = movieArray.getJSONObject(i);

                String img_path = currentMovie.getString("poster_path");

                String vote_average = currentMovie.getString("vote_average");

                String release_date = currentMovie.getString("release_date");

                String plot = currentMovie.getString("overview");

                String title = currentMovie.getString("title");


                Movie movie1 = new Movie(img_path, title, release_date, vote_average, plot);
                movie.add(movie1);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movie;
    }

    public static List<MovieReview> fetchMovieReviewData(URL url) {
        return null;
    }

    public static char[] buildYoutubeUrl(String first_trailer_link) {
            return new char[0];
    }
}
