package a201370518.sb.assignment_01;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_EDIT_PROFILE = 2;

    public static final String PHOTO_THUMBNAIL = "photo_thumbnail";

    ImageView imgPhoto;
    Button btnEditProfile;
    Bitmap photoThumbnail = null;
    TextView txtUserName;
    TextView txtUserID;
    TextView stateDeveloperStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        imgPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                dispatchTakePictureIntent();
            }
        });

        btnEditProfile = (Button) findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivityEditProfile();
            }
        });

        txtUserName = (TextView) findViewById(R.id.txtUserName);
        txtUserID = (TextView) findViewById(R.id.txtUserID);
        stateDeveloperStatus = (TextView) findViewById(R.id.stateDeveloperStatus);

        if (savedInstanceState != null){
            photoThumbnail = savedInstanceState.getParcelable(PHOTO_THUMBNAIL);
            imgPhoto.setImageBitmap(photoThumbnail);
        }
    }

    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } else{
            Toast.makeText(this, "No camera available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            photoThumbnail = (Bitmap) extras.get("data");
            imgPhoto.setImageBitmap(photoThumbnail);
        }
        if (requestCode == REQUEST_EDIT_PROFILE && resultCode == RESULT_OK){
            if (data != null){
                String name = data.getStringExtra("user_name");
                String id = data.getStringExtra("user_id");
                boolean developerStatus = data.getBooleanExtra("developer_status", false);
                if (developerStatus == true){
                    stateDeveloperStatus.setText(R.string.txtDeveloperStatusOn);
                } else{
                    stateDeveloperStatus.setText(R.string.txtDeveloperStatusOff);
                }
                txtUserName.setText(name);
                txtUserID.setText(id);

            }
        }
    }

    public void startActivityEditProfile(){
        Intent intentStartActivityEditProfile = new Intent(this, Edit_Profile.class);
        intentStartActivityEditProfile.putExtra("user_name", txtUserName.getText().toString());
        intentStartActivityEditProfile.putExtra("user_id", txtUserID.getText().toString());

        startActivityForResult(intentStartActivityEditProfile, REQUEST_EDIT_PROFILE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        if(photoThumbnail!=null){
            outState.putParcelable(PHOTO_THUMBNAIL, photoThumbnail);
        }

        final TextView name = (TextView) findViewById(R.id.txtUserName);
        CharSequence username = name.getText();
        outState.putCharSequence("user_name", username);

        final TextView id = (TextView) findViewById(R.id.txtUserID);
        CharSequence userid = id.getText();
        outState.putCharSequence("user_id", userid);

        final TextView status = (TextView) findViewById(R.id.stateDeveloperStatus);
        CharSequence devStatus = status.getText();
        outState.putCharSequence("status", devStatus);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedState) {

        final TextView name = (TextView) findViewById(R.id.txtUserName);
        final TextView id = (TextView) findViewById(R.id.txtUserID);
        final TextView status = (TextView) findViewById(R.id.stateDeveloperStatus);
        CharSequence username = savedState.getCharSequence("user_name");
        CharSequence userid = savedState.getCharSequence("user_id");
        CharSequence devStatus = savedState.getCharSequence("status");
        name.setText(username);
        id.setText(userid);
        status.setText(devStatus);
        super.onRestoreInstanceState(savedState);
    }

}
