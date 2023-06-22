package com.example.multiplechoiceexam.Teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.multiplechoiceexam.Models.Account;
import com.example.multiplechoiceexam.R;

import com.example.multiplechoiceexam.SignIn.SignInActivity;
import com.example.multiplechoiceexam.Teacher.ExamClassManagement.ExamClassManagementFragment;
import com.example.multiplechoiceexam.Teacher.ExamManagement.ExamManagementFragment;
import com.example.multiplechoiceexam.Teacher.ExamsOffline.ExamsOfflineFragment;
import com.example.multiplechoiceexam.Utils.MemoryData;
import com.example.multiplechoiceexam.Utils.Utility;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class TeacherMainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private ExamManagementFragment examManagementFragment;
    private ExamClassManagementFragment examClassManagementFragment;
    private ExamsOfflineFragment examsOfflineFragment;
    private TextView menuTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);
        bottomNavigationView = findViewById(R.id.student_bottomNavigation);
        examManagementFragment = new ExamManagementFragment();
        examClassManagementFragment = new ExamClassManagementFragment();
        examsOfflineFragment = new ExamsOfflineFragment();
        menuTv = findViewById(R.id.topMenu);


        if (savedInstanceState == null) {
            // Tạo fragment mặc định
            ExamsOfflineFragment defaultFragment = new ExamsOfflineFragment();

            // Sử dụng replace() để thay thế fragment hiện tại bằng fragment mặc định
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.teacherMode_frame, defaultFragment)
                    .commit();
        }
        Utility.showToast(TeacherMainActivity.this,"Welcome Back!!");
        // điều hướng chat video hoặc chat
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.menuTeacher_Exam){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.teacherMode_frame,examManagementFragment).commit();
                }
                if(item.getItemId() == R.id.menuTeacher_ExamClass){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.teacherMode_frame,examClassManagementFragment).commit();
                }
                if(item.getItemId() == R.id.menuTeacher_ExamOffline){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.teacherMode_frame,examsOfflineFragment).commit();
                }
                return true;
            }
        });

    }
    public void updating(View view) {
        Utility.showToast(getApplicationContext(),"Tính năng đang được cập nhật!!");
    }
    public void topBarMenu(View view) {
        PopupMenu menu = new PopupMenu(TeacherMainActivity.this, menuTv);
        menu.getMenu().add("Sign Out");
        // show menu
        menu.show();
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getTitle() == "Sign Out"){
                    // làm trống bộ nhớ local
                    MemoryData.saveCurrentAccount(new Account(), TeacherMainActivity.this);
                    startActivity(new Intent(TeacherMainActivity.this, SignInActivity.class));
                    Utility.showToast(TeacherMainActivity.this,"Sign Out Successfully !");
                }
                return false;
            }
        });


    }
}