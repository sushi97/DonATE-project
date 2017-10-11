package com.sdl.app.donate.receiverRecyclerView;


import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.sdl.app.donate.R;
import com.sdl.app.donate.food_donate_add;
import com.sdl.app.donate.ngolist;
import com.sdl.app.donate.user_info;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sdl.app.donate.food_donate_add.noofpeople;
import static com.sdl.app.donate.food_donate_add.user_latitude;
import static com.sdl.app.donate.food_donate_add.user_longitude;

public class ngo_receiver extends AppCompatActivity implements recyclerAdapter.ListItemClickListener{

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private recyclerAdapter adapter;
    private List<ngolist> info;
    private ApiInterface apiInterface;
    public Integer id = food_donate_add.getNumber();
    List<ngolist> finallist;
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_receiver);

        Log.e("impp!!", "onCreate: ");

        recyclerView = (RecyclerView) findViewById(R.id.ngo_receiver_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        apiInterface = ApiClient.getapiClient().create(ApiInterface.class);


        Log.e("latitude : ", user_latitude + "");
        Log.e("longitude : ", user_longitude + "");
        Log.e("hey there", "before location");
        getAddress(user_latitude, user_longitude);
        Log.e("hey there", "after location");

        location = "Pune";
        //Call<List<ngoInfo>> call = apiInterface.getuserinfo(id, loc);
        user_info.userdon infos = new user_info.userdon();
        infos.setUsername("gaurav");
        infos.setPassword("pass123");


        //Call<List<user_info>> call = apiInterface.getuserinfo(infos);
        Call<List<ngolist>> call2 = apiInterface.getorglist();

//        call.enqueue(new Callback<List<user_info>>() {
//            @Override
//            public void onResponse(Call<List<user_info>> call, Response<List<user_info>> response) {
//                List<user_info> obj = response.body();
//                //user_info.userdon obj1 = new user_info.userdon();
//                Log.e("jkshgktg", obj.get(0).getToken());
//                Log.e("TAG", "receiver" + obj.get(0).isReceiver()+"");
//                //obj1 = obj.get(0).getUser();
//                Log.e("TAG", "user name  :  " + obj.get(0).getUser().getName());
//            }
//
//            @Override
//            public void onFailure(Call<List<user_info>> call, Throwable t) {
//                Log.e("onFailure", t.toString() );
//            }
//        });

        call2.enqueue(new Callback<List<ngolist>>() {
            @Override
            public void onResponse(Call<List<ngolist>> call, Response<List<ngolist>> response) {
                Log.e("imp", "onResponse: ");
                finallist = response.body();
                adapter = new recyclerAdapter(finallist, ngo_receiver.this);
                recyclerView.setAdapter(adapter);



            }

            @Override
            public void onFailure(Call<List<ngolist>> call, Throwable t) {
                Log.e("oyeoye", "onFailure: ");
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error in fetching content!", Toast.LENGTH_LONG);
            }
        });

    }

    public void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        if(geocoder.isPresent() == false){
            Log.e("cunt!!", "you fucked up here");
            Toast.makeText(getApplicationContext(), "locale.default is null", Toast.LENGTH_LONG).show();
            return;
        }else {
            try {
                List<Address> addresses = geocoder.getFromLocation(18.458479, 73.851626, 1);
                if(addresses.isEmpty()){
                    Log.e("cunt!!", "you fucked up here, you know that");
                    Toast.makeText(getApplicationContext(), "addresses is empty", Toast.LENGTH_LONG).show();
                    return;
                }
                Address obj = addresses.get(0);
                String add = obj.getAddressLine(0);
                add = add + "\n" + obj.getCountryName();
                add = add + "\n" + obj.getCountryCode();
                add = add + "\n" + obj.getAdminArea();
                add = add + "\n" + obj.getPostalCode();
                add = add + "\n" + obj.getSubAdminArea();
                add = add + "\n" + obj.getLocality();
                add = add + "\n" + obj.getSubThoroughfare();
                Log.v("IGA", "Address : " + add);

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onListItemClick(int itemClickedIndex) {
        Toast.makeText(getApplicationContext(), "index : " + itemClickedIndex, Toast.LENGTH_LONG).show();
        ngolist sendinfo = new ngolist();
        sendinfo.userFrom = "Sushrut";
        sendinfo.userTo = finallist.get(itemClickedIndex).getName();
        sendinfo.address = location;
        sendinfo.quantity = noofpeople+"";

        Call<List<ngolist>> call = apiInterface.getrecv(sendinfo);

        call.enqueue(new Callback<List<ngolist>>() {
            @Override
            public void onResponse(Call<List<ngolist>> call, Response<List<ngolist>> response) {
                List<ngolist> res = response.body();
                if(res.get(0).isSuccess()) {
                    Toast.makeText(getApplicationContext(), "Successfully sent", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Successfully not sent", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ngolist>> call, Throwable t) {

            }
        });
    }

}

