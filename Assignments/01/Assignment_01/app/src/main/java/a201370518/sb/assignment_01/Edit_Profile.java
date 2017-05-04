package a201370518.sb.assignment_01;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;

public class Edit_Profile extends AppCompatActivity {

    Button btnCancel;
    Button btnSubmit;
    EditText edtUserName;
    EditText edtID;
    Switch switch1;
    boolean devStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile);

        Intent fromMainActivity = getIntent();
        String name = fromMainActivity.getStringExtra("user_name");
        String id = fromMainActivity.getStringExtra("user_id");

        edtUserName = (EditText) findViewById(R.id.edtUserName);
        if (name!=null){
            edtUserName.setText(name);
        }

        edtID = (EditText) findViewById(R.id.edtID);
        if (id!=null){
            edtID.setText(id);
        }

        switch1 = (Switch) findViewById(R.id.switch1);
        devStatus = fromMainActivity.getBooleanExtra("developer_status", false);
        if (devStatus == true){
            switch1.setChecked(true);
        }

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canceled();
            }
        });
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    public void canceled() {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void submit() {
        String name = edtUserName.getText().toString();
        String id = edtID.getText().toString();
        Intent data = new Intent();
        data.putExtra("user_name", name);
        data.putExtra("user_id", id);

        if (switch1.isChecked()){
            data.putExtra("developer_status", true);
        }

        setResult(RESULT_OK, data);
        finish();
    }
}
