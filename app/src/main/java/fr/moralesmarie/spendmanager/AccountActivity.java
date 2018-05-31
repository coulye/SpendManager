package fr.moralesmarie.spendmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;



public class AccountActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private TextView user_nom;
    private String nom;
    private TextView user_prenom;
    private String prenom;
    private TextView user_adresse;
    private String adresse;
    private TextView user_tel;
    private String tel;
    private TextView user_mail;
    private String mail;
    private TextView user_mdp;
    private String mdp;
    private TextView user_statut;
    private String statut;

    private ImageButton btnRetour;

    final String LOGIN_USER = "user_profile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_account);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //menu nav header - info user
        String mailuser = null;
        String coorduser = null;
        TextView mail_user;
        TextView user_detail;
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        final View headerLayout = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        SharedPreferences myPref = getSharedPreferences(LOGIN_USER, Context.MODE_PRIVATE);
        Intent i = getIntent();
        if (i != null) {
            mailuser = myPref.getString("loginSend", mailuser);
            System.out.println("mailuser : " + mailuser);
            mail_user = (TextView) headerLayout.findViewById(R.id.mail_user);
            mail_user.setText(mailuser);

            coorduser = myPref.getString("prenom_extra", coorduser) + " " + myPref.getString("nom_extra", coorduser);
            user_detail = (TextView) headerLayout.findViewById(R.id.user_detail);
            user_detail.setText(coorduser);
        }

        user_nom = (TextView) findViewById(R.id.user_nom);
        user_prenom = (TextView) findViewById(R.id.user_prenom);
        user_adresse = (TextView) findViewById(R.id.user_adresse);
        user_tel = (TextView) findViewById(R.id.user_tel);
        user_mail = (TextView) findViewById(R.id.user_mail);
        user_mdp = (TextView) findViewById(R.id.user_mdp);
        user_statut = (TextView) findViewById(R.id.user_statut);

        Intent intentAccount = getIntent();
        nom = myPref.getString("nom_extra", nom);
        user_nom.setText(nom);
        prenom = myPref.getString("prenom_extra", nom);
        user_prenom.setText(prenom);
        adresse = myPref.getString("adresse_extra", nom);
        user_adresse.setText(adresse);
        tel = myPref.getString("tel_extra", tel);
        user_tel.setText(tel);
        mail = myPref.getString("mail_extra", mail);
        user_mail.setText(mail);
        mdp = myPref.getString("mdp_extra", mdp);
        user_mdp.setText(mdp);
        statut = myPref.getString("statut_extra", statut);
        user_statut.setText(statut);

        btnRetour = (ImageButton) findViewById(R.id.imageButtonBack);
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(AccountActivity.this, MenuActivity.class);
                startActivity(back);
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


    @Override
        public boolean onNavigationItemSelected (MenuItem item){

            int id = item.getItemId();

            if (id == R.id.add_note) {
                Intent addNoteIntent = new Intent(AccountActivity.this, AddFraisActivity.class);
                startActivity(addNoteIntent);

            } else if (id == R.id.list_note) {
                Intent listNoteIntent = new Intent(AccountActivity.this, MenuActivity.class);
                startActivity(listNoteIntent);

            } else if (id == R.id.add_client) {

                Intent addClientIntent = new Intent(AccountActivity.this, AddClientActivity.class);
                startActivity(addClientIntent);

            } else if (id == R.id.view_stats) {
                Intent graphIntent = new Intent(AccountActivity.this, GraphActivity.class);
                startActivity(graphIntent);

            } else if (id == R.id.user_account) {
                Intent accountIntent = new Intent(AccountActivity.this, AccountActivity.class);
                startActivity(accountIntent);

            } else if (id == R.id.user_deco) {
                Intent DecoIntent = new Intent(AccountActivity.this, LoginActivity.class);
                startActivity(DecoIntent);
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

}
