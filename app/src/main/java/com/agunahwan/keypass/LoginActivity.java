package com.agunahwan.keypass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.agunahwan.keypass.database.MasterPasswordDB;
import com.agunahwan.keypass.entity.MasterPassword;
import com.agunahwan.keypass.sessions.LoginSessionManager;

public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener {

    MasterPasswordDB masterPasswordDB;
    MasterPassword masterPassword;

    LoginSessionManager loginSessionManager;

    private EditText txt_password;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Set Object View
        masterPasswordDB = MasterPasswordDB.getInstance(this);
        txt_password = (EditText) findViewById(R.id.txtPassword);
        btn_login = (Button) findViewById(R.id.btnLogin);

        btn_login.setOnClickListener(this);

        //Session Manager Class
        loginSessionManager = new LoginSessionManager(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (!TextUtils.isEmpty(txt_password.getText().toString())) {
            masterPassword = masterPasswordDB.checkPasswordLogin(txt_password.getText().toString());

            if (masterPassword != null) {
                loginSessionManager.createLoginSession(masterPassword.getNama());

                //Pindah ke halaman utama
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Password yang anda masukkan salah", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Isikan password", Toast.LENGTH_SHORT).show();
        }
    }
}
