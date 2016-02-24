package com.agunahwan.keypass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.agunahwan.keypass.database.PasswordSavedDB;
import com.agunahwan.keypass.entity.PasswordSaved;

public class EditPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    //Declare object View
    private EditText txt_nama, txt_username, txt_password;
    private Button btn_simpan, btn_batal;

    //Define Class
    PasswordSaved passwordSaved;
    PasswordSavedDB passwordSavedDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        passwordSavedDB = PasswordSavedDB.getDbInstance(this);

        //Define object
        txt_nama = (EditText) findViewById(R.id.txtNama);
        txt_username = (EditText) findViewById(R.id.txtUsername);
        txt_password = (EditText) findViewById(R.id.txtPassword);
        btn_simpan = (Button) findViewById(R.id.btnSimpan);
        btn_batal = (Button) findViewById(R.id.btnBatal);

        btn_simpan.setOnClickListener(this);
        btn_batal.setOnClickListener(this);

        //Get Data Parameter
        Bundle bundle = getIntent().getExtras();
        passwordSaved = new PasswordSaved();
        if (bundle != null) {
            txt_nama.setText(bundle.getString("nama"));
            txt_username.setText(bundle.getString("username"));
            txt_password.setText(bundle.getString("password"));

            passwordSaved.setId(bundle.getInt("id"));
            passwordSaved.setNama(bundle.getString("nama"));
            passwordSaved.setUsername(bundle.getString("username"));
            passwordSaved.setPassword(bundle.getString("password"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_password, menu);
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
        Intent intent;
        switch (view.getId()) {
            case R.id.btnSimpan:
                //Set New Value
                passwordSaved.setNama(txt_nama.getText().toString());
                passwordSaved.setUsername(txt_username.getText().toString());
                passwordSaved.setPassword(txt_password.getText().toString());

                boolean boolEdit = passwordSavedDB.editPasswordSaved(passwordSaved);

                if (boolEdit) {
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Data gagal disimpan", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnBatal:
                Bundle b = new Bundle();
                b.putInt("id", passwordSaved.getId());
                b.putString("nama", passwordSaved.getNama());
                b.putString("username", passwordSaved.getUsername());
                b.putString("password", passwordSaved.getPassword());

                intent = new Intent(getApplicationContext(), DetailPasswordActivity.class);
                intent.putExtras(b);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), DetailPasswordActivity.class);
        startActivity(intent);
        finish();
    }
}
