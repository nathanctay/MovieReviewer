package com.example.finalproject.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalproject.models.SearchItem;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class tmdbAPI {
    public interface OnRequestCompleteListener<T> {
        void onComplete(T[] results, String errorMessage);
    }


    private RequestQueue queue;
    private static tmdbAPI instance;


    public static tmdbAPI getInstance(Context context) {
        if (instance == null) {
            instance = new tmdbAPI(context);
        }
        return instance;
    }

    private tmdbAPI(Context context) {
        this.queue = Volley.newRequestQueue(context);
    }


    public void getMovieTitle(OnRequestCompleteListener<SearchItem> onRequestCompleteListener, String search) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                "https://api.themoviedb.org/3/search/movie?api_key=2ac2456d381bbe8ba69956a4760fa2ae&query=" + search,
                null,
                response -> {
                    JSONArray results = new JSONArray();
                    try {
                        results = response.getJSONArray("results");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    SearchItem[] titles = new SearchItem[results.length()];

                    for (int i = 0; i < results.length(); i++) {
                        try {
                            JSONObject object = results.getJSONObject(i);
                            SearchItem searchItem = new SearchItem();
                            searchItem.title = object.getString("title");
                            searchItem.posterURL = "https://image.tmdb.org/t/p/original" + object.getString("poster_path");
                            if (object.getString("release_date").length() >= 4) {
                                searchItem.year = Integer.parseInt(object.getString("release_date").substring(0, 4));
                            } else {
                                searchItem.year = 0;
                            }
                            titles[i] = searchItem;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    onRequestCompleteListener.onComplete(titles, null);
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onRequestCompleteListener.onComplete(null, error.toString());
                error.printStackTrace();
            }
        }
        );
        queue.add(jsonObjectRequest);
    }






}
