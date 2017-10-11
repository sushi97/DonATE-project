package com.sdl.app.donate;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyProfile extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE =1;
    ImageButton button;
    Button save;
    EditText t1,t2,t3,t4,t5;
    ImageView imgView;
//    Apiinterface userclient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 2909);
            } else {
                Toast.makeText(MyProfile.this, "Cannot show your profile!", Toast.LENGTH_LONG).show();
                finish();
            }
        } else {
            // continue with your code
        }

        Retrofit.Builder builder=new Retrofit.Builder()
                .baseUrl("http://116.75.44.147:1307/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit=builder.build();
//        userclient=retrofit.create(Apiinterface.class);

//        getProfile();

        t1=(EditText)findViewById(R.id.email);
        t2=(EditText)findViewById(R.id.phone);
        t3=(EditText)findViewById(R.id.city);
        t4=(EditText)findViewById(R.id.location);
        t5=(EditText)findViewById(R.id.name);

        button=(ImageButton) findViewById(R.id.edit);
        save=(Button) findViewById(R.id.save);
        imgView = (ImageView) findViewById( R.id.imageView );


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t2.setEnabled(true);
                t3.setEnabled(true);
                t4.setEnabled(true);
                t5.setEnabled(true);
                save.setVisibility(View.VISIBLE);
                button.setVisibility(View.INVISIBLE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t2.setEnabled(false);
                t3.setEnabled(false);
                t4.setEnabled(false);
                t5.setEnabled(false);
                save.setVisibility(View.INVISIBLE);
                button.setVisibility(View.VISIBLE);
            }
        });
        imgView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        } );



        t4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                save.setVisibility(View.INVISIBLE);
                if(!b) {
                    save.setVisibility(View.VISIBLE);
                }


            }
        });


    }
    private static String utoken="yfjbcdsvkjhckjsdk jhDKJ h";

//    private void getProfile()
//    {
//
//        Call<List<com.sdl.app.donate.com.example.tanmay.profile_activity.User>> call= userclient.getProfile();
//
//        call.enqueue( new Callback<List<com.sdl.app.donate.com.example.tanmay.profile_activity.User>>() {
//            @Override
//            public void onResponse(Call<List<com.sdl.app.donate.com.example.tanmay.profile_activity.User>> call, Response<List<com.sdl.app.donate.com.example.tanmay.profile_activity.User>> response) {
//
//
//                List<com.sdl.app.donate.com.example.tanmay.profile_activity.User> pro= (List<com.sdl.app.donate.com.example.tanmay.profile_activity.User>) response.body();
//                t2.setEnabled(true);
//                t3.setEnabled(true);
//                t1.setEnabled( true );
//                t4.setEnabled(true);
//                t5.setEnabled(true);
//                t2.setText( pro.get(0).getPhone() );
//                t1.setText( pro.get(0).getEmail() );
//                t4.setText( pro.get(0).getLocation() );
//                t5.setText( pro.get(0).getUsername() );
//                t2.setEnabled(false);
//                t3.setEnabled(false);
//                t1.setEnabled( false );
//                t4.setEnabled(false);
//                t5.setEnabled(false);
//                //   Toast.makeText(MainActivity.this,response.body().string(),Toast.LENGTH_SHORT).show();
//
//
//
//
//
//
//            }
//
//            @Override
//            public void onFailure(Call<List<com.sdl.app.donate.com.example.tanmay.profile_activity.User>> call, Throwable t) {
//
//            }
//        } );
//
//    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == 1) {
            final Bundle extras = data.getExtras();
            if (extras != null) {
                //Get image
                Bitmap newProfilePic = extras.getParcelable("data");

                imgView.setImageBitmap(newProfilePic);
            }
        }

    }

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 1);
    }

}
