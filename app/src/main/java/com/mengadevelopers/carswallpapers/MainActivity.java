package com.mengadevelopers.carswallpapers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.mengadevelopers.carswallpapers.databinding.ActivityMainBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private AdView mAdView;
List<model> list=new ArrayList<>();
    int pagenumber;
    Boolean isscroling=false;
    int currentitems,totalitems,scroloutitems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView = findViewById(R.id.adView);
getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {

                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                super.onAdFailedToLoad(adError);
                mAdView.loadAd(adRequest);
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
                // Code to be executed when an impression is recorded
                // for an ad.
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
        });
        GridLayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),2);
        binding.rv.setLayoutManager(layoutManager);
        binding.rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


                if (newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isscroling=true;
                }


            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentitems=layoutManager.getChildCount();
                totalitems=layoutManager.getItemCount();
                scroloutitems=layoutManager.findFirstVisibleItemPosition();
                if (isscroling&&(currentitems+scroloutitems==totalitems)){
                    isscroling=false;
                    fetch();
                }

            }
        });

        fetch();
    }

    private void fetch() {
        StringRequest request = new StringRequest(Request.Method.GET, "https://api.pexels.com/v1/search?query=luxury cars/?page="+pagenumber+"&per_page=80", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("photos");

                    Adapter adapter=new Adapter(list,getApplicationContext());

                    for (int i = 1; i <= jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        JSONObject imgsrc = object.getJSONObject("src");

                        String image = imgsrc.getString("portrait");
                        model models = new model();
                        models.setMedium(image);
                        list.add(models);
                        adapter.notifyDataSetChanged();
                        binding.rv.setAdapter(adapter);

                    }
pagenumber++;

                } catch (JSONException e) {

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> parapm = new HashMap<>();
                parapm.put("Authorization", "563492ad6f917000010000018a057bbfc50e4224b99b2282463e862f");
                return parapm;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }
}