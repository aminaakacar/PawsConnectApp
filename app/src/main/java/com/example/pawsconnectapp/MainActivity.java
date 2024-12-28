package com.example.pawsconnectapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private TextView pets, favorites, adoptionRequests, messages;

    private String petsUrl = "http://localhost:5263/api/v1/PetsApi";
    private String favoritesUrl = "http://localhost:5263/api/v1/FavoritesApi";
    private String adoptionRequestsUrl = "http://localhost:5263/api/v1/AdoptionRequests";
    private String messagesUrl = "http://localhost:5263/api/v1/MessagesApi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        pets = findViewById(R.id.pets);
        favorites = findViewById(R.id.favorites);
        adoptionRequests = findViewById(R.id.adoptionRequests);
        messages = findViewById(R.id.messages);
    }

    public void showPets(View view) {
        fetchData(petsUrl, pets);
    }

    public void showFavorites(View view) {
        fetchData(favoritesUrl, favorites);
    }

    public void showAdoptionRequests(View view) {
        fetchData(adoptionRequestsUrl, adoptionRequests);
    }

    public void showMessages(View view) {
        fetchData(messagesUrl, messages);
    }

    private void fetchData(String url, TextView textView) {
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<String> data = new ArrayList<>();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        data.add(object.toString());
                    }
                    textView.setText(""); // Clear the TextView
                    for (String row : data) {
                        String currentText = textView.getText().toString();
                        textView.setText(currentText + "\n\n" + row);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    textView.setText("Error parsing data.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("REST error", error.getMessage());
                textView.setText("Error fetching data.");
            }
        });

        requestQueue.add(request);
    }
}
