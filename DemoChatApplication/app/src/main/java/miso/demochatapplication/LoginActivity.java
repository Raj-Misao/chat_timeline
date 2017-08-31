package miso.demochatapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText l_email,l_password;
    Button loginBtn;
    TextView regLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        l_email = (EditText) findViewById(R.id.l_email);
        l_password = (EditText) findViewById(R.id.l_password);
        regLink = (TextView) findViewById(R.id.regLink);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        regLink.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.regLink:{
                startActivity(new Intent(this,RegisterActivity.class));
            }; break;
            case R.id.loginBtn:{
                String email = l_email.getText().toString();
                String password = l_password.getText().toString();
                String method = "Login";
                DatabaseHelper db = new DatabaseHelper(this);
                db.execute(method,email,password);
            }; break;
        }
    }
}
