package com.sdl.app.donate;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProfile extends AppCompatActivity {
    private int STORAGE_PERMISSION_CODE = 23;
    ImageButton button;
    Button save;
    EditText t1,t2,t3,t5;
    ImageView imgView;
    private ProgressDialog pDialog;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        if(isReadStorageAllowed()){
            t1=(EditText)findViewById(R.id.email);
            t2=(EditText)findViewById(R.id.phone);
            t3=(EditText)findViewById(R.id.city);
            t5=(EditText)findViewById(R.id.name);

            button=(ImageButton) findViewById(R.id.edit);
            save=(Button) findViewById(R.id.save);
            imgView = (ImageView) findViewById(R.id.imageView);

            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("LOGIN_PREF", Context.MODE_PRIVATE);
            token = sharedPreferences.getString("token", "");
            if(token==null || token=="") {
                Toast.makeText(getApplicationContext(), "Unauthorized login", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }

            APIService service = APIClient.getClient().create(APIService.class);

            Call<List<Profile>> profileCall = service.userProfile(token);

            profileCall.enqueue(new Callback<List<Profile>>() {
                @Override
                public void onResponse(Call<List<Profile>> call, Response<List<Profile>> response) {
                    Toast.makeText(getApplicationContext(), "Profile received successfully!", Toast.LENGTH_SHORT).show();
                    t5.setText(response.body().get(0).getName());
                    t1.setText(response.body().get(0).getEmail());
                    t2.setText(response.body().get(0).getPhoneNo());
                    t3.setText(response.body().get(0).getCity());
                }

                @Override
                public void onFailure(Call<List<Profile>> call, Throwable t) {
                    Log.d("onFailure", t.toString());
                    Toast.makeText(MyProfile.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                }
            });

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    t2.setEnabled(true);
                    t3.setEnabled(true);
                    t5.setEnabled(true);
                    save.setVisibility(View.VISIBLE);
                    button.setVisibility(View.INVISIBLE);
                }
            });

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(!validate()) {
                        Toast.makeText(getApplicationContext(), "Invalid data entered", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    saveToServer();

                    t2.setEnabled(false);
                    t3.setEnabled(false);
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

            return;
        }

        requestStoragePermission();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
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

    private boolean isReadStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    private void requestStoragePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
    }


    private void saveToServer() {
        save.setEnabled(false);
        pDialog = new ProgressDialog(MyProfile.this,
                R.style.AppTheme);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.setMessage("Updating Data on the Server...");

        if(!pDialog.isShowing())
            pDialog.show();

        String name = t5.getText().toString();
        String phone = t2.getText().toString();
        String city = t3.getText().toString();

        APIService service = APIClient.getClient().create(APIService.class);

        User user = new User(name,null,null,phone,city);
        Call<List<MSG>> updateCall = service.userUpdate(user,token);

        updateCall.enqueue(new Callback<List<MSG>>() {
            @Override
            public void onResponse(Call<List<MSG>> call, Response<List<MSG>> response) {
                if(pDialog.isShowing())
                    pDialog.dismiss();

                if(response.body().get(0).getSuccess()) {
                    Toast.makeText(getApplicationContext(), "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Toast.makeText(getApplicationContext(), "Profile could not be found and updated", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<List<MSG>> call, Throwable t) {
                if(pDialog.isShowing())
                    pDialog.dismiss();

                Log.d("onFailure", t.toString());
                Toast.makeText(MyProfile.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                return;
            }
        });


    }

    private boolean validate() {
        boolean valid = true;

        String name = t5.getText().toString();
        String phone = t2.getText().toString();
        String city = t3.getText().toString();

        if(name.length() < 2) {
            t5.setError("Atleast 2 characters must be present");
            t5.requestFocus();
            valid = false;
        } else {
            t5.setError(null);
        }

        if(!isValidMobile(phone)) {
            t2.setError("Not a valid phone number");
            t2.requestFocus();
            valid = false;
        } else {
            t2.setError(null);
        }

        if(city.length() < 2) {
            t3.setError("Not a valid city");
            t3.requestFocus();
            valid = false;
        } else {
            t3.setError(null);
        }
        return valid;
    }

    private boolean isValidMobile(String mobile) {
        boolean valid;
        if(!Pattern.matches("[a-zA-Z]+", mobile)) {
            if(mobile.length() != 10)
                valid = false;
            else
                valid = true;
        }
        else
            valid = false;
        return valid;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //Checking the request code of our request
        if(requestCode == STORAGE_PERMISSION_CODE){

            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                t1=(EditText)findViewById(R.id.email);
                t2=(EditText)findViewById(R.id.phone);
                t3=(EditText)findViewById(R.id.city);
                t5=(EditText)findViewById(R.id.name);

                button=(ImageButton) findViewById(R.id.edit);
                save=(Button) findViewById(R.id.save);
                imgView = (ImageView) findViewById(R.id.imageView);

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("LOGIN_PREF", Context.MODE_PRIVATE);
                token = sharedPreferences.getString("token", "");
                if(token==null || token=="") {
                    Toast.makeText(getApplicationContext(), "Unauthorized login", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

                APIService service = APIClient.getClient().create(APIService.class);

                Call<List<Profile>> profileCall = service.userProfile(token);

                profileCall.enqueue(new Callback<List<Profile>>() {
                    @Override
                    public void onResponse(Call<List<Profile>> call, Response<List<Profile>> response) {
                        Toast.makeText(getApplicationContext(), "Profile received successfully!", Toast.LENGTH_SHORT).show();
                        t5.setText(response.body().get(0).getName());
                        t1.setText(response.body().get(0).getEmail());
                        t2.setText(response.body().get(0).getPhoneNo());
                        t3.setText(response.body().get(0).getCity());
                    }

                    @Override
                    public void onFailure(Call<List<Profile>> call, Throwable t) {
                        Log.d("onFailure", t.toString());
                        Toast.makeText(MyProfile.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                });

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        t2.setEnabled(true);
                        t3.setEnabled(true);
                        t5.setEnabled(true);
                        save.setVisibility(View.VISIBLE);
                        button.setVisibility(View.INVISIBLE);
                    }
                });

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(!validate()) {
                            Toast.makeText(getApplicationContext(), "Invalid data entered", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        saveToServer();

                        t2.setEnabled(false);
                        t3.setEnabled(false);
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
            }else{
                //Displaying another toast if permission is not granted
                Toast.makeText(this,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
            }
        }
    }
}
