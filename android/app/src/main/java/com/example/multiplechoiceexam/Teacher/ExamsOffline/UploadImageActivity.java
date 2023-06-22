package com.example.multiplechoiceexam.Teacher.ExamsOffline;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.multiplechoiceexam.Api.ApiService;
import com.example.multiplechoiceexam.R;
import com.example.multiplechoiceexam.Api.RetrofitClient;
import com.example.multiplechoiceexam.Utils.Utility;
import com.example.multiplechoiceexam.dto.upload.TestImageResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadImageActivity extends AppCompatActivity {
    private RecyclerView imgRecyclerview;
    private Button pickImgBtn;
    private TextView imgCount;
    private ArrayList<Uri> list;
    private ImageAdapter imageAdapter;
    private FloatingActionButton done_img;
    private String currentPhotoPath;
    private final int REQUESTCODE_GALLEY =  123;
    private final int REQUEST_IMAGE_CAPTURE =  115;
    private EditText classCode_edt;
    private String permissions[] = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_MEDIA_IMAGES
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        list = new ArrayList<>();
        pickImgBtn =findViewById(R.id.pickImage);
        imgCount = findViewById(R.id.imageCount_tv);
        imageAdapter = new ImageAdapter(list);
        imgRecyclerview = findViewById(R.id.recycler_image);
        done_img = findViewById(R.id.upload_img);
        classCode_edt = findViewById(R.id.classCode_edt);
        // set layout
        /*imgRecyclerview.setLayoutManager(new GridLayoutManager(this,2));*/
        imgRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        imgRecyclerview.setAdapter(imageAdapter);
        ProgressDialog progressDialog = new ProgressDialog(UploadImageActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Uploading...");


        pickImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(UploadImageActivity.this);
                dialog.setContentView(R.layout.dialog_option_select_img);
                ImageView camera = (ImageView) dialog.findViewById(R.id.camera_img);
                ImageView galley = (ImageView) dialog.findViewById(R.id.galley_img);
                Button cancel = (Button) dialog.findViewById(R.id.cancel_button);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                galley.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // check permission for galley
                        if (ActivityCompat.checkSelfPermission(
                                UploadImageActivity.this, permissions[0]) != PackageManager.PERMISSION_GRANTED &&
                                ActivityCompat.checkSelfPermission(
                                        UploadImageActivity.this, permissions[1]) != PackageManager.PERMISSION_GRANTED &&
                                ActivityCompat.checkSelfPermission(
                                        UploadImageActivity.this, permissions[2]) != PackageManager.PERMISSION_GRANTED &&
                                ActivityCompat.checkSelfPermission(
                                        UploadImageActivity.this, permissions[3]) != PackageManager.PERMISSION_GRANTED) {

                            // if permission not granted then request
                            ActivityCompat.requestPermissions(UploadImageActivity.this,permissions, REQUESTCODE_GALLEY);
                        }
                        else {
                            openGalley();
                            dialog.dismiss();
                        }

                    }
                });
                // click camera
                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // check permission for camera
                        if (ActivityCompat.checkSelfPermission(
                                UploadImageActivity.this, permissions[0]) != PackageManager.PERMISSION_GRANTED &&
                                ActivityCompat.checkSelfPermission(
                                        UploadImageActivity.this, permissions[1]) != PackageManager.PERMISSION_GRANTED &&
                                ActivityCompat.checkSelfPermission(
                                        UploadImageActivity.this, permissions[2]) != PackageManager.PERMISSION_GRANTED) {

                            // if permission not granted then request
                            requestPermissions(permissions, REQUESTCODE_GALLEY);
                        }
                        else {
                           String fileName = "photo";
                           File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                           try {
                               File imageFile= File.createTempFile(fileName,".jpg",storageDir);
                               currentPhotoPath = imageFile.getAbsolutePath();
                              Uri imageUri =  FileProvider.getUriForFile(UploadImageActivity.this,
                                       "com.example.multiplechoiceexam.fileprovider",imageFile);
                               Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                               intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                               startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
                                dialog.dismiss();
                           } catch (IOException e) {
                               throw new RuntimeException(e);
                           }
                        }


                    }
                });
                dialog.show();
            }
        });



        // click upload
        done_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String classCode = classCode_edt.getText().toString().trim();
                 if(classCode.isEmpty()){
                    classCode_edt.setError("Enter classCode");
                    return;
                }
                progressDialog.show();
                List<MultipartBody.Part> partList = new ArrayList<>();
                // Thực hiện chuyển đổi cho mỗi URI
                for (Uri imageUri : list) {
                    // Chuyển đổi Uri thành File
                    File file = new File(getRealPathFromURI(imageUri));
                    // Tạo RequestBody từ File
                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                    MultipartBody.Part filePart = MultipartBody.Part.createFormData("files", file.getName(), requestFile);
                    partList.add(filePart);
                }

                // get api service
                ApiService apiService = RetrofitClient.getApiService();
                // gửi yêu cầu tải ảnh lên
                RequestBody classCodeBody = RequestBody.create(MediaType.parse("text/plain"), classCode);

                Call<TestImageResponse> call = apiService.uploadStudentTestImages(classCodeBody,partList);
                call.enqueue(new Callback<TestImageResponse>() {
                    @Override
                    public void onResponse(Call<TestImageResponse> call, Response<TestImageResponse> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            // Xử lý phản hồi thành công
                            Utility.showToast(UploadImageActivity.this,"Upload Successfully !");
                            finish();
                            // ...
                        } else {
                            Utility.showToast(UploadImageActivity.this,"Error: " + response.code() + response.message() );
                        }
                    }

                    @Override
                    public void onFailure(Call<TestImageResponse> call, Throwable t) {
                        Utility.showToast(UploadImageActivity.this, t.getMessage());
                        Log.e("LOG_BUG", "onFailure: " + t.getMessage() );
                        progressDialog.dismiss();

                    }
                });



            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUESTCODE_GALLEY) { // Kiểm tra mã yêu cầu quyền
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Utility.showToast(UploadImageActivity.this,"Permission granted !!");
            } else {
                Utility.showToast(UploadImageActivity.this,"The app needs permission to access gallery");
            }
        }
    }


    private void openGalley() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUESTCODE_GALLEY && resultCode == RESULT_OK) {
            assert data != null;
            if (data.getClipData() != null) {
                 int numberImage = data.getClipData().getItemCount();
                for (int i = 0; i < numberImage; i++) {
                    list.add(data.getClipData().getItemAt(i).getUri());
                }

            } else if (data.getData() != null) {
                String imgurl = data.getData().getPath();
                list.add(Uri.parse(imgurl));
            }
            // Thông báo cho Adapter cập nhật lại dữ liệu
            imageAdapter.notifyDataSetChanged();
            imgCount.setText("Image(" + list.size() + ")");
            done_img.setVisibility(View.VISIBLE);
            imgCount.setVisibility(View.VISIBLE);
            classCode_edt.setVisibility(View.VISIBLE);
            classCode_edt.requestFocus();

        }

        // result capture
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
            int rotationDegrees = 90;
            Matrix matrix = new Matrix();
            matrix.postRotate(rotationDegrees);
            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            String path = MediaStore.Images.Media.insertImage(UploadImageActivity.this.getContentResolver(), rotatedBitmap, "Image Title", null);
            Uri imageUri = Uri.parse(path);
            list.add(imageUri);
            imageAdapter.notifyDataSetChanged();
            imgCount.setText("Image(" + list.size() + ")");
            done_img.setVisibility(View.VISIBLE);
            imgCount.setVisibility(View.VISIBLE);
            classCode_edt.setVisibility(View.VISIBLE);
            classCode_edt.requestFocus();

        }
    }


    private String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        cursor.close();
        return path;
    }

}