package com.example.multiplechoiceexam.Api;

import com.example.multiplechoiceexam.dto.auth.AuthenticationRequest;
import com.example.multiplechoiceexam.dto.auth.RegisterRequest;
import com.example.multiplechoiceexam.dto.auth.AuthenticationResponse;
import com.example.multiplechoiceexam.dto.upload.TestImageResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {
    @POST("/api/v1/auth/signin")
    Call<AuthenticationResponse> authenticateUser(@Body AuthenticationRequest request);

    @POST("/api/v1/auth/signup")
    Call<AuthenticationResponse> registerUser(@Body RegisterRequest registerRequest);




    @Multipart
    @POST("/api/v1/student-test/uploads")
    Call<TestImageResponse> uploadStudentTestImages(
            @Part("exam-class") RequestBody classCode,
            @Part List<MultipartBody.Part> files
    );
}
