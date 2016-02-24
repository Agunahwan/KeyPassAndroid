package com.agunahwan.keypass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.agunahwan.keypass.adapter.PasswordSavedAdapter;
import com.agunahwan.keypass.database.MasterPasswordDB;
import com.agunahwan.keypass.database.PasswordSavedDB;
import com.agunahwan.keypass.entity.PasswordSaved;
import com.agunahwan.keypass.sessions.LoginSessionManager;

public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener {

    //Declare object view
    private ListView lv_password;

    //Set DB Class
    private MasterPasswordDB masterPasswordDB;
    private PasswordSavedDB passwordSavedDB;

    private PasswordSavedAdapter passwordSavedAdapter;

    // Session Manager Class
    LoginSessionManager loginSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Session class instance
        loginSessionManager = new LoginSessionManager(getApplicationContext());
        boolean isLoggedIn = loginSessionManager.isLoggedIn();

        //Declare DB
        masterPasswordDB = MasterPasswordDB.getInstance(this);
        passwordSavedDB = PasswordSavedDB.getDbInstance(this);

        boolean isExistMasterPassword = masterPasswordDB.isExistMasterPassword();

        //Get Object view
        lv_password = (ListView) findViewById(R.id.lvPassword);

        if (!isExistMasterPassword) { //Jika Aplikasi baru pertama dijalankan
            this.loadAddMasterPassword();
        } else {
            if (!isLoggedIn) { //Jika belum melakukan login
                this.loadLogin();
            } else { //Jika sudah login
                lv_password.setEmptyView(findViewById(R.id.empty));

                if (passwordSavedDB.isPasswordHasData("", "")) {
                    passwordSavedAdapter = new PasswordSavedAdapter(this, passwordSavedDB.getAllPassword());
                    lv_password.setAdapter(passwordSavedAdapter);
                }
                lv_password.setOnItemClickListener(this);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //loginSessionManager.logoutUser();
    }

    @Override
    public void onStop() {
        super.onStop();
        //loginSessionManager.logoutUser();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //loginSessionManager.logoutUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

// Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            public boolean onQueryTextSubmit(String query) {
                loadSearchResult(query);
//                handler.removeCallbacks(autoSearchRunnable);
//                Log.i("HELP", "ME");
//                submitSearch(query);
                return true;
            }

            public boolean onQueryTextChange(String newText) {
                loadSearchResult(newText);
//                handler.removeCallbacks(autoSearchRunnable);
//                currentText = newText;
//                if (newText == "") {
//                    clearSearch();
//                } else {
//                    // handler.postDelayed(autoSearchRunnable,
//                    // AUTO_SEARCH_DELAY);
//                }
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent;

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_add:
                intent = new Intent(getApplicationContext(), AddPasswordActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_logout:
                loginSessionManager.logoutUser();

//                intent = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Bundle b = new Bundle();
        b.putInt("id", ((PasswordSaved) passwordSavedAdapter.getItem(position)).getId());
        b.putString("nama", ((PasswordSaved) passwordSavedAdapter.getItem(position)).getNama());
        b.putString("username", ((PasswordSaved) passwordSavedAdapter.getItem(position)).getUsername());
        b.putString("password", ((PasswordSaved) passwordSavedAdapter.getItem(position)).getPassword());

        Intent intent = new Intent(this, DetailPasswordActivity.class);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

    public void loadAddMasterPassword() {
        Intent i = new Intent(getApplicationContext(), AddMasterPasswordActivity.class);//target = nama class
        startActivity(i);
        finish();
    }

    public void loadLogin() {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);//target = nama class
        startActivity(i);
        finish();
    }

    public void loadSearchResult(String query) {
        lv_password.setEmptyView(findViewById(R.id.empty));

        if (passwordSavedDB.isPasswordHasData(query, query)) {
            passwordSavedAdapter = new PasswordSavedAdapter(this, passwordSavedDB.getPassword(query, query));
            lv_password.setAdapter(passwordSavedAdapter);
        }
//        lv_password.setOnItemClickListener(this);
    }
}
