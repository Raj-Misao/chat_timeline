package miso.demochatapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText r_uname,r_email,r_password,r_birthday,r_gender;
    Button regBtn;
    String name,email,password,birthday,gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        r_uname = (EditText) findViewById(R.id.r_uname);
        r_email = (EditText) findViewById(R.id.r_email);
        r_password = (EditText) findViewById(R.id.r_password);
        r_birthday = (EditText) findViewById(R.id.r_birthday);
        r_gender = (EditText) findViewById(R.id.r_gender);
        regBtn = (Button) findViewById(R.id.regBtn);
        regBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        name = r_uname.getText().toString();
        email = r_email.getText().toString();
        password = r_password.getText().toString();
        birthday = r_birthday.getText().toString();
        gender = r_gender.getText().toString();
        String method = "Register";
        DatabaseHelper db = new DatabaseHelper(this);
        db.execute(method,name,email,password,birthday,gender);
        finish();
    }
}
