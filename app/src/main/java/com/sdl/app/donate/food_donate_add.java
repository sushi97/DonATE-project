package com.sdl.app.donate;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.sdl.app.donate.receiverRecyclerView.ApiClient;
import com.sdl.app.donate.receiverRecyclerView.ApiInterface;
import com.sdl.app.donate.receiverRecyclerView.ngo_receiver;

import java.util.ArrayList;

public class food_donate_add extends AppCompatActivity {

    EditText fooditem, number;
    ArrayList<String> food_list;
    ArrayAdapter<String> adapter;
    ListView listView;
    Button add, location;
    int cnt = 0;
    public static int noofpeople;
    public static double user_latitude, user_longitude;
    private LocationManager locationManager;
    private LocationListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_donate_add);

        number = (EditText) findViewById(R.id.no_people_feed);
        fooditem = (EditText) findViewById(R.id.fooditem_user);
        add = (Button) findViewById(R.id.add_food_in_list);
        location = (Button) findViewById(R.id.button_add_location);
        food_list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.fooditem_listview, R.id.txtview_for_foodlist, food_list);
        //done = (Button) findViewById(R.id.button_done_fooditem);
        listView = (ListView) findViewById(R.id.donate_fooditem_list);
        listView.setAdapter(adapter);
        final ArrayList<String> listfood = new ArrayList<>();

        noofpeople = Integer.parseInt(number.getText().toString());

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cnt++;
                String item = " " + cnt + ". " + fooditem.getText().toString();
                listfood.add(fooditem.getText().toString());
                food_list.add(item);
                fooditem.setText("");
                adapter.notifyDataSetChanged();
            }
        });

//        done.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), ngo_receiver.class);
//                startActivity(intent);
//            }
//        });


        //getting user location
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                user_latitude = location.getLongitude();
                user_longitude = location.getLatitude();

                Log.e("latitude : ", user_latitude + "");
                Log.e("longitude : ", user_longitude + "");

                String donerfood = getfoodlist(listfood);
                Log.e("TAG", "doner food : " + donerfood );

                Intent intent = new Intent(getApplicationContext(), ngo_receiver.class);
                startActivity(intent);

                ApiInterface apiInterface;
                apiInterface = ApiClient.getapiClient().create(ApiInterface.class);

//                Call<List<user_info>> call = apiInterface.sendfoodlist(donerfood);
//
//                call.enqueue(new Callback<List<user_info>>() {
//                    @Override
//                    public void onResponse(Call<List<user_info>> call, Response<List<user_info>> response) {
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<user_info>> call, Throwable t) {
//
//                    }
//                });

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        configure_button();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    public String getfoodlist(ArrayList<String> list) {
        String food = "";
        for(int i = 0; i < list.size(); ++i) {
            food += ", " + list.get(i);
        }
        return food;
    }

    void configure_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }

        final Criteria criteria;
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        final Looper looper = null;

        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //noinspection MissingPermission
                locationManager.requestSingleUpdate(criteria, listener, looper);
            }
        });
    }


    public static int getNumber() {
        return noofpeople;
    }
}


