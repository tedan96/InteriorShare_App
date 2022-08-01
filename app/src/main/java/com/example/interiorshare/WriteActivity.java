package com.example.interiorshare;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WriteActivity extends AppCompatActivity {
    private static final int CALL_LISTACTIVITY = 0;
    //intent 객체와 상수값을 전달
    EditText title, content, detail;
    ImageView photo;
    Button  but_edit, but_gallery, but_save;
    Uri uri;
    String imgName;
    Bitmap bitmap;// 이미지 이름
    final private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.write);
        content = findViewById(R.id.content);
        title = findViewById(R.id.title);
        detail=findViewById(R.id.detail);
        photo = findViewById(R.id.photo);
//        but_edit = findViewById(R.id.edit);
        but_gallery = findViewById(R.id.gallery);

        but_save = findViewById(R.id.save);
        but_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String FileTitle = title.getText().toString();
                String FileContent = content.getText().toString();
                FileOutputStream fos;
                imgName = FileTitle;
                File tempFile = new File(getCacheDir(), imgName);
                long now = System.currentTimeMillis();
                Date mDate = new Date(now);
                SimpleDateFormat simpleDate = new SimpleDateFormat("yy년 MM월 dd일");
                String time = simpleDate.format(mDate);
                try {
                    fos = openFileOutput(FileTitle, Context.MODE_PRIVATE | MODE_APPEND); //목록에 제목만 띄우기
                    ObjectOutputStream oos = new ObjectOutputStream(fos);

                    String[] saveData = {content.getText().toString(), title.getText().toString(), detail.getText().toString(), time};
                    oos.writeObject(saveData);

                    tempFile.createNewFile();
                    FileOutputStream out = new FileOutputStream(tempFile);
                    boolean compress = bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    fos.close();
                    out.close();
                    Toast.makeText(WriteActivity.this, FileTitle + " saved", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(WriteActivity.this, ListActivity.class);
                    startActivity(intent);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        //사진 작업
        but_gallery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent2.setType("image/*");
                intent2.putExtra("image", uri);
                startActivityResult.launch(intent2);
            }
        });
    }
    ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                        uri = result.getData().getData();

                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            bitmap = rotateBitmap(uri, bitmap);
                            photo.setImageBitmap(bitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
    );
    @RequiresApi(api = Build.VERSION_CODES.N)
    private Bitmap rotateBitmap(Uri uri, Bitmap bitmap) throws IOException {
        InputStream in = getContentResolver().openInputStream(uri);
        ExifInterface exif = new ExifInterface(in);
        in.close();

        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL);
        Matrix matrix = new Matrix();
        if(orientation == ExifInterface.ORIENTATION_ROTATE_90){
            matrix.postRotate(90);
        }
        else if(orientation == ExifInterface.ORIENTATION_ROTATE_180){
            matrix.postRotate(180);
        }
        else if(orientation == ExifInterface.ORIENTATION_ROTATE_270){
            matrix.postRotate(270);
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),bitmap.getHeight(),matrix,true);
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK && data != null) {
//            if (data == null) {   // 어떤 이미지도 선택하지 않은 경우
//                Toast.makeText(getApplicationContext(), "이미지를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
//            } else {   // 이미지를 하나라도 선택한 경우
//                if (data.getClipData() == null) {     // 이미지를 하나만 선택한 경우
//                    Log.e("single choice: ", String.valueOf(data.getData()));
//                    Uri selectedImage = data.getData();
//                    photo.setImageURI(selectedImage);
//                } else {      // 이미지를 여러장 선택한 경우
//                    ClipData clipData = data.getClipData();
//                    Log.e("clipData", String.valueOf(clipData.getItemCount()));
//                    if (clipData.getItemCount() > 10) {   // 선택한 이미지가 11장 이상인 경우
//                        Toast.makeText(getApplicationContext(), "사진은 10장까지 선택 가능합니다.", Toast.LENGTH_LONG).show();
//                    } else {   // 선택한 이미지가 1장 이상 10장 이하인 경우
//                        Log.e(TAG, "multiple choice");
//                        for (int i = 0; i < clipData.getItemCount(); i++) {
//                            Uri selectedImage = clipData.getItemAt(i).getUri();  // 선택한 이미지들의 uri를 가져온다.
//                            try {
//                                photo.setImageURI(selectedImage);
//                            } catch (Exception e) {
//                                Log.e(TAG, "File select error", e);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
}
//        {
//            if(requestCode == 1)
//            {
//                if(resultCode == RESULT_OK)
//                {
//                    try{
//                        InputStream in = getContentResolver().openInputStream(data.getData());
//                        Bitmap img = BitmapFactory.decodeStream(in);
//                        in.close();
//                        photo.setImageBitmap(img);
//                    }catch(Exception e)
//                    {
//
//                    }
//                }
//                else if(resultCode == RESULT_CANCELED)
//                {
//                    Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
//                }
//            }
//        }
        /*if (resultCode == RESULT_OK && data != null) {
            if (data == null) {   // 어떤 이미지도 선택하지 않은 경우
                Toast.makeText(getApplicationContext(), "이미지를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
            } else {   // 이미지를 하나라도 선택한 경우
                if (data.getClipData() == null) {     // 이미지를 하나만 선택한 경우
                    Log.e("single choice: ", String.valueOf(data.getData()));
                    Uri selectedImage = data.getData();
                    photo.setImageURI(selectedImage);
                } else {      // 이미지를 여러장 선택한 경우
                    ClipData clipData = data.getClipData();
                    Log.e("clipData", String.valueOf(clipData.getItemCount()));
                    if (clipData.getItemCount() > 10) {   // 선택한 이미지가 11장 이상인 경우
                        Toast.makeText(getApplicationContext(), "사진은 10장까지 선택 가능합니다.", Toast.LENGTH_LONG).show();
                    } else {   // 선택한 이미지가 1장 이상 10장 이하인 경우
                        Log.e(TAG, "multiple choice");
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            Uri selectedImage = clipData.getItemAt(i).getUri();  // 선택한 이미지들의 uri를 가져온다.
                            try {
                                photo.setImageURI(selectedImage);
                            } catch (Exception e) {
                                Log.e(TAG, "File select error", e);
                            }
                        }
                        Log.d(TAG, "AAAAAA");
                        *//*SpannableString ss = new SpannableString("abc\n");
                         *//**//*Drawable d= selectedImage.getDrawable();*//**//*
                Drawable d = getResources().getDrawable(R.drawable.ikea2);
                d.setBounds(0,0,d.getIntrinsicWidth(),d.getIntrinsicHeight());
                ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
                content.getText().setSpan(span,0 , 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                content.setText(ss);*//*
                    }
                }
            }
        }*/



























/*        but_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Title = title.getText().toString();
                String Content = content.getText().toString();

                Intent intent = new Intent(WriteActivity.this, ListActivity.class);
                startActivityForResult(intent, CALL_LISTACTIVITY);
                //Activity A에서 B를 호출하고 결과값을 반환해서 다시 A로 돌아가는 방식
                //CALL_LISTACTIVITY는 요청 코드
            }
        });
    }
    public void onActivityResult (int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CALL_LISTACTIVITY:
                if (resultCode == RESULT_OK) { // 잘 넘어왔으면 ok값을 가져옴
                    String Title = data.getStringExtra("title");
                    String Content = data.getStringExtra("content");
                    String Date=data.getStringExtra("date");
                    title.setText(Title); // 인텐트에서 읽어온 값 다시 설정
                    content.setText(Content);
                }
                break;
        }
    }*/









       /* Drawable d = getResources().getDrawable(R.drawable.ic_launcher_background);
        EditText editText = findViewById(R.id.content);
        ImageSpan imageSpan = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(editText.getText());
        String imgId = "[img=1]";
        int selStart = editText.getSelectionStart();
        builder.replace(editText.getSelectionStart(), editText.getSelectionEnd(), imgId);
        builder.setSpan(imageSpan, selStart, selStart + imgId.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setText(builder);*/