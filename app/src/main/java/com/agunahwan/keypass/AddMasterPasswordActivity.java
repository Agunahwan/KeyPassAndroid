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

public class AddMasterPasswordActivity extends AppCompatActivity implements
        View.OnClickListener {

    private MasterPasswordDB db;

    private EditText txt_nama, txt_password, txt_confirm_password;
    private Button btn_simpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_master_password);

        //Define Object
        db = MasterPasswordDB.getInstance(this);
        txt_nama = (EditText) findViewById(R.id.txtNama);
        txt_password = (EditText) findViewById(R.id.txtPassword);
        txt_confirm_password = (EditText) findViewById(R.id.txtConfirmPassword);

        btn_simpan = (Button) findViewById(R.id.btnSimpan);

        btn_simpan.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_master_password, menu);

//        // Inflate your custom layout
//        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
//                R.layout.activity_add_master_password,
//                null);
//
//        // Set up your ActionBar
//        final ActionBar actionBar = getActionBar();
//        actionBar.setDisplayShowHomeEnabled(false);
//        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setDisplayShowCustomEnabled(true);
//        actionBar.setCustomView(actionBarLayout);
//
//        // You customization
//        final Button actionBarTitle = (Button) findViewById(R.id.action_bar_title);
//        actionBarTitle.setText("Master Password");
//
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
        if (!TextUtils.isEmpty(txt_nama.getText().toString())
                && !TextUtils.isEmpty(txt_password.getText().toString())
                && !TextUtils.isEmpty(txt_confirm_password.getText().toString())) {
            if (txt_password.getText().toString().equals(txt_confirm_password.getText().toString())) {
                //Isikan text ke dalam entity
                MasterPassword masterPassword = new MasterPassword();
                masterPassword.setNama(txt_nama.getText().toString());
                masterPassword.setPassword(txt_password.getText().toString());

                //Add Master Password
                boolean boolAdd = db.addMasterPassword(masterPassword);

                if (boolAdd) {
                    //Pindah ke halaman utama
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Data gagal disimpan", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Password dan Konfimasi Password harus sama.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Nama, Password, dan Konfimasi Password tidak boleh kosong.", Toast.LENGTH_SHORT).show();
        }
    }
}
