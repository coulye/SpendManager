package fr.moralesmarie.spendmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.Serializable;

import fr.moralesmarie.spendmanager.Class.Utilisateur;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Spinner spinner;
    private static final String[]paths = {"tri par date", "tri par client"};

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;

    private ImageButton btnAddFrais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        String mailuser = null;
        String coorduser = null;
        TextView mail_user;
        TextView user_detail;
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        final View headerLayout = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        //recuperation du bundle de l intent dans LoginActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //extraction de la valeur par la clé
            mailuser = extras.getString("mail_extra");
            //recuperation du texteview mail user
            mail_user = (TextView)headerLayout.findViewById(R.id.mail_user);
            mail_user.setText(mailuser);

            //recuperation du textView du nom et prenom
            coorduser = extras.getString("prenom_extra")+ " " +extras.getString("nom_extra");
            //recuperation du texteview mail user
            user_detail = (TextView)headerLayout.findViewById(R.id.user_detail);
            user_detail.setText(coorduser);
        }

        spinner = (Spinner)findViewById(R.id.spinner_liste_note);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MenuActivity.this,
                android.R.layout.simple_spinner_item,paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        btnAddFrais = (ImageButton) findViewById(R.id.btnAddFrais);
        btnAddFrais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddFrais = new Intent(MenuActivity.this, AddFraisActivity.class);
                startActivity(intentAddFrais);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the MenuActivity; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.add_note) {
//            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//                startActivityForResult(takePictureIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
//            }
            Intent addNoteIntent = new Intent(MenuActivity.this, AddFraisActivity.class);
            startActivity(addNoteIntent);

        } else if (id == R.id.list_note) {
            Intent listNoteIntent = new Intent(MenuActivity.this, MenuActivity.class);
            startActivity(listNoteIntent);

        } else if (id == R.id.add_client) {

            Intent addClientIntent = new Intent(MenuActivity.this, AddClientActivity.class);
            startActivity(addClientIntent);

        } else if (id == R.id.view_stats) {

        } else if (id == R.id.user_account) {

        } else if (id == R.id.user_deco) {
            Intent DecoIntent = new Intent(MenuActivity.this, LoginActivity.class);
            startActivity(DecoIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
