package com.sdl.app.donate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";
    private ProgressDialog pDialog;

    @Bind(R.id.input_name) EditText nameText;
    @Bind(R.id.input_email) EditText emailText;
    @Bind(R.id.input_password) EditText passwordText;
    @Bind(R.id.input_reEnterPassword) EditText reEnterPasswordText;
    @Bind(R.id.input_mob) EditText mobileText;
    @Bind(R.id.btn_signup) Button signupButton;
    @Bind(R.id.link_login) TextView loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void signup() {
        Log.d(TAG, "SignUp");

        if(!validate()) {
            onSignupFailed();
            return;
        }

        savetoServerDB();
    }

    private boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String reEnterPassword = reEnterPasswordText.getText().toString();
        String mobile = mobileText.getText().toString();

        if (name.length() < 2) {
            nameText.setError("At least 2 characters must be present");
            nameText.requestFocus();
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Enter a valid email address");
            emailText.requestFocus();
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty()) {
            passwordText.setError("Cannot be empty");
            passwordText.requestFocus();
            valid = false;
        } else {
            passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || !(reEnterPassword.equals(password))) {
            reEnterPasswordText.setError("Passwords Do not match");
            reEnterPasswordText.requestFocus();
            valid = false;
        } else {
            reEnterPasswordText.setError(null);
        }

        if(!(isValidMobile(mobile))) {
            mobileText.setError("Not a valid mobile number");
            mobileText.requestFocus();
             valid = false;
        } else {
            mobileText.setError(null);
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

    private void savetoServerDB() {
        signupButton.setEnabled(false);
        pDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.setMessage("Creating Account...");

        showpDialog();

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String mobile = mobileText.getText().toString();

        APIService service = APIClient.getClient().create(APIService.class);

        User user = new User(name,email,password,mobile);
        Call<MSG> signupCall =  service.userSignUp(user);

        signupCall.enqueue(new Callback<MSG>() {
            @Override
            public void onResponse(Call<MSG> call, Response<MSG> response) {
                hidepDialog();

                if(response.body().getSuccess()) {
                    Toast.makeText(SignupActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                    finish();
                }
                else
                    Toast.makeText(SignupActivity.this, "Account already exists!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MSG> call, Throwable t) {
                hidepDialog();

                onSignupFailed();

                Log.d("onFailure", t.toString());
                Toast.makeText(SignupActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void onSignupFailed() {
        signupButton.setEnabled(true);
    }


    private void showpDialog() {
        if(!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if(pDialog.isShowing())
            pDialog.dismiss();
    }


}
