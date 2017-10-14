package com.sdl.app.donate.mydonations;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.sdl.app.donate.Home_Screeen;
import com.sdl.app.donate.R;
import com.sdl.app.donate.ngolist;
import com.sdl.app.donate.receiverRecyclerView.ApiClient;
import com.sdl.app.donate.receiverRecyclerView.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyDonations extends AppCompatActivity {

    ApiInterface apiInterface;
    RecAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_donations);

        recyclerView = (RecyclerView) findViewById(R.id.mydonatefood);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        String id = "Sushrut";
        apiInterface = ApiClient.getapiClient().create(ApiInterface.class);
        Call<List<ngolist>> call = apiInterface.getuserdonation(id);

        call.enqueue(new Callback<List<ngolist>>() {
            @Override
            public void onResponse(Call<List<ngolist>> call, Response<List<ngolist>> response) {
                Log.e("TAG", "onResponse: " );
                List<ngolist> mydon = response.body();
                adapter = new RecAdapter(mydon);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<ngolist>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No internet connection found", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Home_Screeen.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
