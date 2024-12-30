package com.example.pawsconnectapp;

import android.content.Intent;
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

        // Initialize the RequestQueue and TextViews
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        pets = findViewById(R.id.pets);
        favorites = findViewById(R.id.favorites);
        adoptionRequests = findViewById(R.id.adoptionRequests);
        messages = findViewById(R.id.messages);
    }

    // Show available pets
    public void showPets(View view) {
        if (view != null) {
            JsonArrayRequest request = new JsonArrayRequest(petsUrl, petsResponseListener, errorListener);
            requestQueue.add(request);
        }
    }

    // Show favorite animals
    public void showFavorites(View view) {
        if (view != null) {
            JsonArrayRequest request = new JsonArrayRequest(favoritesUrl, favoritesResponseListener, errorListener);
            requestQueue.add(request);
        }
    }

    // Show adoption requests
    public void showAdoptionRequests(View view) {
        if (view != null) {
            JsonArrayRequest request = new JsonArrayRequest(adoptionRequestsUrl, adoptionRequestsResponseListener, errorListener);
            requestQueue.add(request);
        }
    }

    // Show messages
    public void showMessages(View view) {
        if (view != null) {
            JsonArrayRequest request = new JsonArrayRequest(messagesUrl, messagesResponseListener, errorListener);
            requestQueue.add(request);
        }
    }

    // Navigate to AddPetActivity
    public void addPetActivity(View view) {
        Intent intent = new Intent(this, AddPetActivity.class);
        startActivity(intent);
    }

    // Listener for Pets API response
    private final Response.Listener<JSONArray> petsResponseListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            ArrayList<String> data = new ArrayList<>();
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject object = response.getJSONObject(i);
                    String name = object.getString("name");
                    String age = object.getString("age");

                    data.add(name + " " + age);
                } catch (JSONException e) {
                    e.printStackTrace();
                    pets.setText("Error parsing data.");
                    return;
                }
            }
            pets.setText("");
            for (String row : data) {
                String currentText = pets.getText().toString();
                pets.setText(currentText + "\n\n" + row);
            }
        }
    };

    // Listener for Favorites API response
    private final Response.Listener<JSONArray> favoritesResponseListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            ArrayList<String> data = new ArrayList<>();
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject object = response.getJSONObject(i);
                    String name = object.getString("name");
                    String reason = object.getString("reason");

                    data.add(name + " " + reason);
                } catch (JSONException e) {
                    e.printStackTrace();
                    favorites.setText("Error parsing data.");
                    return;
                }
            }
            favorites.setText("");
            for (String row : data) {
                String currentText = favorites.getText().toString();
                favorites.setText(currentText + "\n\n" + row);
            }
        }
    };

    // Listener for Adoption Requests API response
    private final Response.Listener<JSONArray> adoptionRequestsResponseListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            ArrayList<String> data = new ArrayList<>();
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject object = response.getJSONObject(i);
                    String adopterName = object.getString("adopterName");
                    String petName = object.getString("petName");
                    String status = object.getString("status");

                    data.add(adopterName + " requested " + petName + " - " + status);
                } catch (JSONException e) {
                    e.printStackTrace();
                    adoptionRequests.setText("Error parsing data.");
                    return;
                }
            }
            adoptionRequests.setText("");
            for (String row : data) {
                String currentText = adoptionRequests.getText().toString();
                adoptionRequests.setText(currentText + "\n\n" + row);
            }
        }
    };

    // Listener for Messages API response
    private final Response.Listener<JSONArray> messagesResponseListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            ArrayList<String> data = new ArrayList<>();
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject object = response.getJSONObject(i);
                    String sender = object.getString("sender");
                    String content = object.getString("content");

                    data.add(sender + ": " + content);
                } catch (JSONException e) {
                    e.printStackTrace();
                    messages.setText("Error parsing data.");
                    return;
                }
            }
            messages.setText("");
            for (String row : data) {
                String currentText = messages.getText().toString();
                messages.setText(currentText + "\n\n" + row);
            }
        }
    };

    // Error listener for all requests
    private final Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("REST error", error.getMessage());
        }
    };
}
