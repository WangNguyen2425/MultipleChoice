package com.example.multiplechoiceexam;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.multiplechoiceexam.Models.Account;
import com.example.multiplechoiceexam.SignIn.SignInActivity;
import com.example.multiplechoiceexam.Teacher.TeacherMainActivity;
import com.example.multiplechoiceexam.Utils.MemoryData;

import java.util.Objects;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = findViewById(R.id.progress_bar_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                 * lấy dữ liệu từ bộ nhớ và kiểm tra xem nếu trong bộ nhớ đã có tài khoản
//                 * kết hợp với quyền của user chuyển màn hình tới các màn hình tương ứng
//                 * hoặc chuyển tới màn hình login khi chưa đăng nhập hoặc đã đăng xuất
//
                Account currentAccount = MemoryData.getCurrentAccount(SplashActivity.this);
                if(! isAccountEmpty(currentAccount) && !Objects.equals(Objects.requireNonNull(currentAccount).getEmail(), "")){
                   /* if(currentAccount.isTeacher()){  // nếu là giáo viên thì chuyển tới màn hình giáo viên và ngược lại
                        startActivity(new Intent(SplashActivity.this, TeacherModeMain.class));
                        finish();
                    }
                    else {
                        startActivity(new Intent(SplashActivity.this, StudentModeMain.class));
                        finish();
                    }*/

                    startActivity(new Intent(SplashActivity.this, TeacherMainActivity.class));
                    finish();
                }
                else {  // chuyển tới màn login vì chưa đăng nhập
                    startActivity(new Intent(SplashActivity.this, SignInActivity.class));
                    finish();
                }


            }
        },1000);   // progressbar quay 2 giây
    }
    // hàm kiểm tra xem đã có account nào được lưu chưa
    public boolean isAccountEmpty(Account account) {
        return account == null || account.getEmail() == "";
    }
}