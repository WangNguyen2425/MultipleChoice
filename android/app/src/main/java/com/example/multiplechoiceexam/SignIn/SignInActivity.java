package com.example.multiplechoiceexam.SignIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.multiplechoiceexam.Api.ApiService;
import com.example.multiplechoiceexam.Models.Account;
import com.example.multiplechoiceexam.R;
import com.example.multiplechoiceexam.Api.RetrofitClient;
import com.example.multiplechoiceexam.Teacher.TeacherMainActivity;
import com.example.multiplechoiceexam.dto.auth.AuthenticationRequest;
import com.example.multiplechoiceexam.dto.auth.AuthenticationResponse;
import com.example.multiplechoiceexam.SignUp.SignUpActivity;
import com.example.multiplechoiceexam.Utils.MemoryData;
import com.example.multiplechoiceexam.Utils.Utility;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {
    private EditText usernamEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ProgressBar progressBar_login;
    private TextInputLayout passLayout,usernameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_activty);

        usernamEditText = findViewById(R.id.signIn_userName_edt);
        passwordEditText = findViewById(R.id.signIn_password_edt);
        progressBar_login = findViewById(R.id.signIn_progressBar);
        loginButton = findViewById(R.id.signIn_button);

        passLayout = findViewById(R.id.signIn_passLayout);

        passLayout.setHelperText("Password length must be >6");

        // get user name and pass word
        usernamEditText.setText(getIntent().getStringExtra("username"));
        passwordEditText.setText(getIntent().getStringExtra("password"));

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changInProgress(true);
                String username = usernamEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                // kiểm tra tên đăng nhập mật khẩu
                if(!validateData(username, password)){
                    changInProgress(false);
                    return;
                }
                AuthenticationRequest authenticationRequest = new AuthenticationRequest(username,password);
                // call api register
                ApiService apiService = RetrofitClient.getApiService();

                Call<AuthenticationResponse> call = apiService.authenticateUser(authenticationRequest);
                call.enqueue(new Callback<AuthenticationResponse>() {
                    @Override
                    public void onResponse(Call<AuthenticationResponse> call, Response<AuthenticationResponse> response) {
                        changInProgress(false);
                        if(response.isSuccessful()){
                            AuthenticationResponse authenticationResponse = response.body();
                            Account account = new Account();
                            assert authenticationResponse != null;
                            account.setEmail(authenticationResponse.getEmail());
                            account.setUsername(authenticationResponse.getUsername());
                            account.setPassword(password);
                            MemoryData.saveCurrentAccount(account,SignInActivity.this);
                            startActivity(new Intent(SignInActivity.this, TeacherMainActivity.class));
                            Utility.showToast(SignInActivity.this,"Sign In Successfully !");
                            finish();

                        }
                        else {
                            if(response.code() == 401){
                                Utility.showToast(SignInActivity.this,"Incorrect username or password !");
                            }
                            if(response.code() == 400){
                                Utility.showToast(SignInActivity.this,"Check your email to verify your account !");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthenticationResponse> call, Throwable t) {
                        Utility.showToast(SignInActivity.this,"Error: " + t.getMessage());
                        changInProgress(false);
                    }
                });
            }
        });


    }
    private void changInProgress(boolean inProgress){
        if(inProgress){
            loginButton.setVisibility(View.GONE);  // đang trong tiến trình tạo account thì button biến mất
            progressBar_login.setVisibility(View.VISIBLE);
        }else {
            progressBar_login.setVisibility(View.GONE);
            loginButton.setVisibility(View.VISIBLE);
        }
    }
    // phương thức kiểm tra dữ liệu đầu vào
    private boolean validateData(String username, String password){
        if(username.length() <6 || isValidInput(username)){
            usernamEditText.setError("Username is incorrect!");
            return false;
        }
        if(password.length() <6){
            passLayout.setHelperText("Password length must be >6");
            passLayout.setHelperTextColor(ColorStateList.valueOf(Color.RED));
            return false;
        }
        return true;
    }

    public boolean isValidInput(String input) {
        // Biểu thức chính quy để kiểm tra chữ cái tiếng Việt có dấu và không chứa khoảng trắng ở giữa
        String regex = "^[^\\s]*[áàảãạăắằẳẵặâấầẩẫậđéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵ][^\\s]*$";

        // Kiểm tra so khớp biểu thức chính quy với chuỗi đầu vào
        return input.trim().matches(regex);
    }
    public void startSignUpActivity(View view) {
        startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
    }
}