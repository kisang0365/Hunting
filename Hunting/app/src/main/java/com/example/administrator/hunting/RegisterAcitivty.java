package com.example.administrator.hunting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;


/**
 * Created by Administrator on 2017-02-11.
 */

public class RegisterAcitivty extends AppCompatActivity{

    EditText et_id;
    EditText et_password;
    ImageView iv_pic;
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;

    private Uri mImageCaptureUri;
    private String absoultePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        iv_pic = (ImageView) findViewById(R.id.iv_register_pic);
        et_id = (EditText) findViewById(R.id.et_register_id);
        et_password = (EditText) findViewById(R.id.et_register_password);

        Button btn_regist = (Button) findViewById(R.id.btn_register_done);
        btn_regist.setOnClickListener(mRegisterListener);

        iv_pic.setOnClickListener(mRegisterPicture);

    }

    Button.OnClickListener mRegisterListener = new View.OnClickListener() {
        public void onClick(View v) {
            //입장하기 클릭시 DB에서 phoneNum잇나 확인
            Toast toast = Toast.makeText(getApplicationContext(), et_id.getText().toString() + "pass : " + et_password.getText().toString(), Toast.LENGTH_LONG);
            toast.show();
        }
    };


    ImageView.OnClickListener mRegisterPicture = new View.OnClickListener() {
        public void onClick(View v) {
            DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    doTakePhotoAction();
                }
            };
            DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doTakeAlbumAction();
                }
            };
            DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };
            new AlertDialog.Builder(v.getContext())
                    .setTitle("업로드할 이미지 선택")
                    .setPositiveButton("사진촬영", cameraListener)
                    .setNeutralButton("앨범선택", albumListener)
                    .setNegativeButton("취소", cancelListener)
                    .show();
        }
    };
    public void doTakePhotoAction(){
        Uri mImageCaptureUri;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    public void doTakeAlbumAction(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);

        if(resultCode != RESULT_OK)
            return;

        switch(requestCode){

            case PICK_FROM_ALBUM:
            {
                mImageCaptureUri = data.getData();
                Log.d("Kisang", mImageCaptureUri.getPath().toString());
            }

            case PICK_FROM_CAMERA:
            {
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                intent.putExtra("outputX", 200); // CROP한 이미지의 x축 크기
                intent.putExtra("outputY", 200); // CROP한 이미지의 y축 크기
                intent.putExtra("aspectX", 1); // CROP 박스의 X축 비율
                intent.putExtra("aspectY", 1); // CROP 박스의 Y축 비율
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_IMAGE); // CROP_FROM_CAMERA case문 이동
                break;
            }
            case CROP_FROM_IMAGE:
            {
                // 크롭이 된 이후의 이미지를 넘겨 받습니다.
                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
                // 임시 파일을 삭제합니다.
                if(resultCode != RESULT_OK) {
                    return;
                }

                final Bundle extras = data.getExtras();

                // CROP된 이미지를 저장하기 위한 FILE 경로
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+
                        "/hunting/"+System.currentTimeMillis()+".jpg";

                if(extras != null)
                {
                    Bitmap photo = extras.getParcelable("data"); // CROP된 BITMAP
                    iv_pic.setImageBitmap(photo); // 레이아웃의 이미지칸에 CROP된 BITMAP을 보여줌

                    storeCropImage(photo, filePath); // CROP된 이미지를 외부저장소, 앨범에 저장한다.
                    absoultePath = filePath;
                    break;

                }
                // 임시 파일 삭제
                File f = new File(mImageCaptureUri.getPath());
                if(f.exists())
                {
                    f.delete();
                }
            }
        }
    }
    /*
      * Bitmap을 저장하는 부분
      */
    private void storeCropImage(Bitmap bitmap, String filePath) {
        // hunting 폴더를 생성하여 이미지를 저장하는 방식이다.
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/hunting";
        File directory_hunting = new File(dirPath);

        if(!directory_hunting.exists()) // hunting 디렉터리에 폴더가 없다면 (새로 이미지를 저장할 경우에 속한다.)
            directory_hunting.mkdir();

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try {

            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            // sendBroadcast를 통해 Crop된 사진을 앨범에 보이도록 갱신한다.
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(copyFile)));

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
