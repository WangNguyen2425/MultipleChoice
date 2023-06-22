package com.example.multiplechoiceexam.Teacher.ExamsOffline;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.multiplechoiceexam.Models.Account;
import com.example.multiplechoiceexam.R;
import com.example.multiplechoiceexam.SignIn.SignInActivity;
import com.example.multiplechoiceexam.Teacher.TeacherMainActivity;
import com.example.multiplechoiceexam.Utils.MemoryData;
import com.example.multiplechoiceexam.Utils.Utility;

public class ExamsOfflineFragment extends Fragment {
    private ImageView export;
    private ImageView mark;
    private ImageView uploadImg;
    private ImageView previewExam;

    public ExamsOfflineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_exams_offline, container, false);
        export = root.findViewById(R.id.offline_export);
        mark = root.findViewById(R.id.offline_mark);
        uploadImg = root.findViewById(R.id.offline_upload);
        previewExam = root.findViewById(R.id.offline_preview);

        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),UploadImageActivity.class));
            }
        });

        return root;
    }



}