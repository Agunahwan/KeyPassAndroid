package com.agunahwan.keypass;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.agunahwan.keypass.database.PasswordSavedDB;
import com.agunahwan.keypass.entity.PasswordSaved;

public class DetailPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    //Declare object View
    private TextView txt_username, txt_password;
    private Button btn_tutup;

    PasswordSaved passwordSaved;
    PasswordSavedDB passwordSavedDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_password);

        passwordSavedDB = PasswordSavedDB.getDbInstance(this);

        //Define object
        txt_username = (TextView) findViewById(R.id.txtUsername);
        txt_password = (TextView) findViewById(R.id.txtPassword);
        btn_tutup = (Button) findViewById(R.id.btnTutup);

        btn_tutup.setOnClickListener(this);

        //Get Data Parameter
        Bundle bundle = getIntent().getExtras();
        passwordSaved = new PasswordSaved();
        if (bundle != null) {
            setTitle(bundle.getString("nama"));
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
        getMenuInflater().inflate(R.menu.menu_detail_password, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_edit:
                Bundle bundle = new Bundle();

                bundle.putInt("id", passwordSaved.getId());
                bundle.putString("nama", passwordSaved.getNama());
                bundle.putString("username", passwordSaved.getUsername());
                bundle.putString("password", passwordSaved.getPassword());

                Intent intent = new Intent(getApplicationContext(), EditPasswordActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();

                return true;
            case R.id.action_delete:
                new AlertDialog.Builder(this)
                        .setTitle("Delete")
                        .setMessage("Apakah yakin ingin menghapus data ini?")
                        .setNegativeButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                passwordSavedDB.deletePasswordSaved(passwordSaved.getId());

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setPositiveButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                startActivity(intent);
//                                finish();
                            }
                        })
                        .show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnTutup) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
