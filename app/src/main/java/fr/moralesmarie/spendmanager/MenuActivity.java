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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;

import fr.moralesmarie.spendmanager.Class.Client;
import fr.moralesmarie.spendmanager.Class.Notefrais;
import fr.moralesmarie.spendmanager.HttpRequest.HttpGetRequest;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Spinner spinner;
    private static final String[]paths = {"Tri par date", "Tri par client", "Uniquement validées", "Uniquement partiellement validées", "Uniquement non validées"};

    private TableLayout table;
    private ArrayList<Notefrais> lesNotefrais;
    private ArrayList<Client> lesClients;
    private int idUtilisateur;
    private ImageButton btnAddFrais;
	
	final String LOGIN_USER = "user_profile";

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
        SharedPreferences myPref = getSharedPreferences(LOGIN_USER, Context.MODE_PRIVATE);
        Intent i = getIntent();
        if (i != null) {
            mailuser =  myPref.getString("loginSend", mailuser);
            System.out.println("mailuser : "+mailuser);
            mail_user = (TextView)headerLayout.findViewById(R.id.mail_user);
            mail_user.setText(mailuser);

            //recuperation du textView du nom et prenom
            coorduser = myPref.getString("prenom_extra", coorduser)+ " " + myPref.getString("nom_extra", coorduser);
            //recuperation du texteview mail user
            user_detail = (TextView)headerLayout.findViewById(R.id.user_detail);
            user_detail.setText(coorduser);

            idUtilisateur = myPref.getInt("id_user",idUtilisateur);
        }

        spinner = (Spinner)findViewById(R.id.spinner_liste_note);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MenuActivity.this,
                android.R.layout.simple_spinner_item,paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final LayoutInflater inflater = getLayoutInflater();
        table = (TableLayout) findViewById(R.id.tableLayout);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tri = spinner.getSelectedItem().toString();
                if (tri.equals("Tri par date")){
                    table.removeAllViewsInLayout();
                    lesNotefrais = listNotefraisByDate(idUtilisateur);
                    lesClients = listClients();
                    for (Notefrais n : lesNotefrais) {
                        final String idNotefrais = String.valueOf(n.getId_Notefrais());
                        int validation[] = etatValidation(n.getId_Notefrais());
                        int diff = validation[0]-validation[1];
                        String leClient = "";
                        for (Client c : lesClients) {
                            if (c.getId_Client() == n.getId_Client()){
                                leClient = c.getNom_Client()+" "+c.getPrenom_Client();
                                break;
                            }
                        }
                        TableRow tr = (TableRow) inflater.inflate(R.layout.tablerow_notefrais, null);
                        if (diff == 0 ){
                            ((ImageView) tr.findViewById(R.id.imageEtat)).setImageResource(R.drawable.if_circle_green_10280);
                        } else if (validation[1] == 0){
                            ((ImageView) tr.findViewById(R.id.imageEtat)).setImageResource(R.drawable.if_circle_red_10282);
                        } else {
                            ((ImageView) tr.findViewById(R.id.imageEtat)).setImageResource(R.drawable.if_circle_orange_10281_version2);
                        }
                        ((TextView) tr.findViewById(R.id.textIdNotefrais)).setText("N° : "+idNotefrais);
                        ((TextView) tr.findViewById(R.id.textDateNotefrais)).setText("Date : "+n.getDate_Notefrais());
                        ((TextView) tr.findViewById(R.id.textNomClient)).setText("Client : "+leClient);
                        tr.findViewById(R.id.btnVoirDepense).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intentListDepense = new Intent(MenuActivity.this, ListDepenseActivity.class);
                                intentListDepense.putExtra("id_notefrais", idNotefrais);
                                startActivity(intentListDepense);
                            }
                        });
                        table.addView(tr);
                    }
                } else if (tri.equals("Tri par client")){
                    table.removeAllViewsInLayout();
                    lesNotefrais = listNotefraisByClient(idUtilisateur);
                    lesClients = listClients();
                    for (Notefrais n : lesNotefrais) {
                        final String idNotefrais = String.valueOf(n.getId_Notefrais());
                        int validation[] = etatValidation(n.getId_Notefrais());
                        int diff = validation[0]-validation[1];
                        String leClient = "";
                        for (Client c : lesClients) {
                            if (c.getId_Client() == n.getId_Client()){
                                leClient = c.getNom_Client()+" "+c.getPrenom_Client();
                                break;
                            }
                        }
                        TableRow tr = (TableRow) inflater.inflate(R.layout.tablerow_notefrais, null);
                        if (diff == 0 ){
                            ((ImageView) tr.findViewById(R.id.imageEtat)).setImageResource(R.drawable.if_circle_green_10280);
                        } else if (validation[1] == 0){
                            ((ImageView) tr.findViewById(R.id.imageEtat)).setImageResource(R.drawable.if_circle_red_10282);
                        } else {
                            ((ImageView) tr.findViewById(R.id.imageEtat)).setImageResource(R.drawable.if_circle_orange_10281_version2);
                        }
                        ((TextView) tr.findViewById(R.id.textIdNotefrais)).setText("N° : "+idNotefrais);
                        ((TextView) tr.findViewById(R.id.textDateNotefrais)).setText("Date : "+n.getDate_Notefrais());
                        ((TextView) tr.findViewById(R.id.textNomClient)).setText("Client : "+leClient);
                        tr.findViewById(R.id.btnVoirDepense).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intentListDepense = new Intent(MenuActivity.this, ListDepenseActivity.class);
                                intentListDepense.putExtra("id_notefrais", idNotefrais);
                                startActivity(intentListDepense);
                            }
                        });
                        table.addView(tr);
                    }
                } else if (tri.equals("Uniquement validées")){
                    table.removeAllViewsInLayout();
                    lesNotefrais = listNotefraisByDate(idUtilisateur);
                    lesClients = listClients();
                    for (Notefrais n : lesNotefrais) {
                        final String idNotefrais = String.valueOf(n.getId_Notefrais());
                        int validation[] = etatValidation(n.getId_Notefrais());
                        int diff = validation[0]-validation[1];
                        if (diff == 0){
                            String leClient = "";
                            for (Client c : lesClients) {
                                if (c.getId_Client() == n.getId_Client()){
                                    leClient = c.getNom_Client()+" "+c.getPrenom_Client();
                                    break;
                                }
                            }
                            TableRow tr = (TableRow) inflater.inflate(R.layout.tablerow_notefrais, null);
                            if (diff == 0 ){
                                ((ImageView) tr.findViewById(R.id.imageEtat)).setImageResource(R.drawable.if_circle_green_10280);
                            } else if (validation[1] == 0){
                                ((ImageView) tr.findViewById(R.id.imageEtat)).setImageResource(R.drawable.if_circle_red_10282);
                            } else {
                                ((ImageView) tr.findViewById(R.id.imageEtat)).setImageResource(R.drawable.if_circle_orange_10281_version2);
                            }
                            ((TextView) tr.findViewById(R.id.textIdNotefrais)).setText("N° : "+idNotefrais);
                            ((TextView) tr.findViewById(R.id.textDateNotefrais)).setText("Date : "+n.getDate_Notefrais());
                            ((TextView) tr.findViewById(R.id.textNomClient)).setText("Client : "+leClient);
                            tr.findViewById(R.id.btnVoirDepense).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intentListDepense = new Intent(MenuActivity.this, ListDepenseActivity.class);
                                    intentListDepense.putExtra("id_notefrais", idNotefrais);
                                    startActivity(intentListDepense);
                                }
                            });
                            table.addView(tr);
                        }
                    }
                } else if (tri.equals("Uniquement partiellement validées")){
                    table.removeAllViewsInLayout();
                    lesNotefrais = listNotefraisByDate(idUtilisateur);
                    lesClients = listClients();
                    for (Notefrais n : lesNotefrais) {
                        final String idNotefrais = String.valueOf(n.getId_Notefrais());
                        int validation[] = etatValidation(n.getId_Notefrais());
                        int diff = validation[0] - validation[1];
                        if (diff > 0 && validation[1] != 0) {
                            String leClient = "";
                            for (Client c : lesClients) {
                                if (c.getId_Client() == n.getId_Client()) {
                                    leClient = c.getNom_Client() + " " + c.getPrenom_Client();
                                    break;
                                }
                            }
                            TableRow tr = (TableRow) inflater.inflate(R.layout.tablerow_notefrais, null);
                            if (diff == 0) {
                                ((ImageView) tr.findViewById(R.id.imageEtat)).setImageResource(R.drawable.if_circle_green_10280);
                            } else if (validation[1] == 0) {
                                ((ImageView) tr.findViewById(R.id.imageEtat)).setImageResource(R.drawable.if_circle_red_10282);
                            } else {
                                ((ImageView) tr.findViewById(R.id.imageEtat)).setImageResource(R.drawable.if_circle_orange_10281_version2);
                            }
                            ((TextView) tr.findViewById(R.id.textIdNotefrais)).setText("N° : " + idNotefrais);
                            ((TextView) tr.findViewById(R.id.textDateNotefrais)).setText("Date : " + n.getDate_Notefrais());
                            ((TextView) tr.findViewById(R.id.textNomClient)).setText("Client : " + leClient);
                            tr.findViewById(R.id.btnVoirDepense).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intentListDepense = new Intent(MenuActivity.this, ListDepenseActivity.class);
                                    intentListDepense.putExtra("id_notefrais", idNotefrais);
                                    startActivity(intentListDepense);
                                }
                            });
                            table.addView(tr);
                        }
                    }
                } else if (tri.equals("Uniquement non validées")){
                    table.removeAllViewsInLayout();
                    lesNotefrais = listNotefraisByDate(idUtilisateur);
                    lesClients = listClients();
                    for (Notefrais n : lesNotefrais) {
                        final String idNotefrais = String.valueOf(n.getId_Notefrais());
                        int validation[] = etatValidation(n.getId_Notefrais());
                        int diff = validation[0] - validation[1];
                        if (validation[1] == 0) {
                            String leClient = "";
                            for (Client c : lesClients) {
                                if (c.getId_Client() == n.getId_Client()) {
                                    leClient = c.getNom_Client() + " " + c.getPrenom_Client();
                                    break;
                                }
                            }
                            TableRow tr = (TableRow) inflater.inflate(R.layout.tablerow_notefrais, null);
                            if (diff == 0) {
                                ((ImageView) tr.findViewById(R.id.imageEtat)).setImageResource(R.drawable.if_circle_green_10280);
                            } else if (validation[1] == 0) {
                                ((ImageView) tr.findViewById(R.id.imageEtat)).setImageResource(R.drawable.if_circle_red_10282);
                            } else {
                                ((ImageView) tr.findViewById(R.id.imageEtat)).setImageResource(R.drawable.if_circle_orange_10281_version2);
                            }
                            ((TextView) tr.findViewById(R.id.textIdNotefrais)).setText("N° : " + idNotefrais);
                            ((TextView) tr.findViewById(R.id.textDateNotefrais)).setText("Date : " + n.getDate_Notefrais());
                            ((TextView) tr.findViewById(R.id.textNomClient)).setText("Client : " + leClient);
                            tr.findViewById(R.id.btnVoirDepense).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intentListDepense = new Intent(MenuActivity.this, ListDepenseActivity.class);
                                    intentListDepense.putExtra("id_notefrais", idNotefrais);
                                    startActivity(intentListDepense);
                                }
                            });
                            table.addView(tr);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAddFrais = (ImageButton) findViewById(R.id.btnAddFrais);
        btnAddFrais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddFrais = new Intent(MenuActivity.this, AddFraisActivity.class);
                startActivity(intentAddFrais);
            }
        });
    }

    public int[] etatValidation(int idNotefrais){
        String result = "";
        int nbreDepense[] = new int[2];

        String myUrl = "http://moralesmarie.alwaysdata.net/public/notefrais/"+idNotefrais+"/validation";

        HttpGetRequest getRequest = new HttpGetRequest();
        try{
            result = getRequest.execute(myUrl).get();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        try {
            JSONArray jsonArray = new JSONArray(result);
            nbreDepense[0] = jsonArray.getJSONObject(0).getInt("TOTAL_DEPENSE");
            nbreDepense[1] = jsonArray.getJSONObject(1).getInt("TOTAL_DEPENSE_VALIDER");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return nbreDepense;
    }

    public ArrayList<Notefrais> listNotefraisByDate(int idUtilisateur){
        ArrayList<Notefrais> lesNotefrais = new ArrayList<Notefrais>();
        String result = "";

        String myUrl = "http://moralesmarie.alwaysdata.net/public/utilisateur/"+idUtilisateur+"/notefrais/tri/date";

        HttpGetRequest getRequest = new HttpGetRequest();
        try{
            result = getRequest.execute(myUrl).get(); // exécution de la connexion
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        try {
            JSONArray tblJSON = new JSONArray(result);
            for (int i = 0 ; i < tblJSON.length() ; i++) {
                JSONObject jsonObject = tblJSON.getJSONObject(i);
                Notefrais laNotefrais = new Notefrais(
                        jsonObject.getInt("Id_Notefrais"),
                        jsonObject.getString("Date_Notefrais"),
                        jsonObject.getString("DateSoumission_Notefrais"),
                        jsonObject.getInt("Id_Utilisateur"),
                        jsonObject.getInt("Id_Client")
                );
                lesNotefrais.add(laNotefrais);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lesNotefrais;
    }

    public ArrayList<Notefrais> listNotefraisByClient(int idUtilisateur){
        ArrayList<Notefrais> lesNotefrais = new ArrayList<Notefrais>();
        String result = "";

        String myUrl = "http://moralesmarie.alwaysdata.net/public/utilisateur/"+idUtilisateur+"/notefrais/tri/client";

        HttpGetRequest getRequest = new HttpGetRequest();
        try{
            result = getRequest.execute(myUrl).get(); // exécution de la connexion
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        try {
            JSONArray tblJSON = new JSONArray(result);
            for (int i = 0 ; i < tblJSON.length() ; i++) {
                JSONObject jsonObject = tblJSON.getJSONObject(i);
                Notefrais laNotefrais = new Notefrais(
                        jsonObject.getInt("Id_Notefrais"),
                        jsonObject.getString("Date_Notefrais"),
                        jsonObject.getString("DateSoumission_Notefrais"),
                        jsonObject.getInt("Id_Utilisateur"),
                        jsonObject.getInt("Id_Client")
                );
                lesNotefrais.add(laNotefrais);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lesNotefrais;
    }

    public ArrayList<Client> listClients(){
        ArrayList<Client> listClient = new ArrayList<Client>();
        String result = "";

//        String myUrl = "http://127.0.0.1:8080/REST-API-SY4/public/client";
        String myUrl = "http://moralesmarie.alwaysdata.net/public/client";

        HttpGetRequest getRequest = new HttpGetRequest();
        try{
            result = getRequest.execute(myUrl).get(); // exécution de la connexion
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        try {
            JSONArray tblJSON = new JSONArray(result);
            for (int i = 0 ; i < tblJSON.length() ; i++) {
                JSONObject jsonObject = tblJSON.getJSONObject(i);
                Client leClient = new Client(
                        jsonObject.getInt("Id_Client"),
                        jsonObject.getString("Titre_Client"),
                        jsonObject.getString("Nom_Client"),
                        jsonObject.getString("Prenom_Client"),
                        jsonObject.getString("Adresse_Client"),
                        jsonObject.getString("Cp_Client"),
                        jsonObject.getString("Ville_Client"),
                        jsonObject.getString("Telephone_Client"),
                        jsonObject.getString("Mail_Client"),
                        jsonObject.getString("Rs_Client")
                );
                listClient.add(leClient);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listClient;
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
            Intent addNoteIntent = new Intent(MenuActivity.this, AddFraisActivity.class);
            startActivity(addNoteIntent);

        } else if (id == R.id.list_note) {
            Intent listNoteIntent = new Intent(MenuActivity.this, MenuActivity.class);
            startActivity(listNoteIntent);

        } else if (id == R.id.add_client) {
            Intent addClientIntent = new Intent(MenuActivity.this, AddClientActivity.class);
            startActivity(addClientIntent);

        } else if (id == R.id.view_stats) {
            Intent graphIntent = new Intent(MenuActivity.this, GraphActivity.class);
            startActivity(graphIntent);

        } else if (id == R.id.user_account) {
            Intent accountIntent = new Intent(MenuActivity.this, AccountActivity.class);
            startActivity(accountIntent);

        } else if (id == R.id.user_deco) {
            Intent DecoIntent = new Intent(MenuActivity.this, LoginActivity.class);
            startActivity(DecoIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
