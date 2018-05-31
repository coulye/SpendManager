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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import fr.moralesmarie.spendmanager.Class.Depense;
import fr.moralesmarie.spendmanager.HttpRequest.HttpGetRequest;

public class ListDepenseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ArrayList<Depense> lesDepenses;
    private int idNotefrais;
    private TableLayout table;
    private TextView textTitreDepense;
    private ImageButton btnRetour;
	
	final String LOGIN_USER = "user_profile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list_depense);

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
            mailuser =  myPref.getString("loginSend", mailuser);
            System.out.println("mailuser : "+mailuser);
            mail_user = (TextView)headerLayout.findViewById(R.id.mail_user);
            mail_user.setText(mailuser);

            coorduser = myPref.getString("prenom_extra", coorduser)+ " " + myPref.getString("nom_extra", coorduser);
            user_detail = (TextView)headerLayout.findViewById(R.id.user_detail);
            user_detail.setText(coorduser);
        }

        Intent intentNotefrais = getIntent();
        idNotefrais = Integer.parseInt(intentNotefrais.getStringExtra("id_notefrais"));

        textTitreDepense = (TextView) findViewById(R.id.textTitreDepense);
        textTitreDepense.setText("Liste des dépenses de la note de frais n° " + idNotefrais);

        lesDepenses = listDepenses(idNotefrais);
        LayoutInflater inflater = getLayoutInflater();
        table = (TableLayout) findViewById(R.id.tableLayoutDepense);
        for (Depense d : lesDepenses) {
            final String idDepense = String.valueOf(d.getId_Depense());
            final String date_paiement = d.getDatePaiement_Depense();
            final String libelle = d.getLibelle_Depense();
            final String commentaire = d.getCommentaire_Depense();
            final String montant = String.valueOf(d.getMontantRemboursement_Depense());
            final String idNotefrais = String.valueOf(d.getId_Notefrais());
            final String etatRemboursement = etatRemboursement(d.getId_Depense());
            TableRow tr = (TableRow) inflater.inflate(R.layout.tablerow_depense, null);
            switch (libelle) {
                case "Essence":
                    ((ImageView) tr.findViewById(R.id.imageViewDepense)).setImageResource(R.drawable.ic_local_gas_station_black_24dp);
                    break;
                case "Train":
                    ((ImageView) tr.findViewById(R.id.imageViewDepense)).setImageResource(R.drawable.ic_train_black_24dp);
                    break;
                case "Hotel":
                    ((ImageView) tr.findViewById(R.id.imageViewDepense)).setImageResource(R.drawable.ic_hotel_black_24dp);
                    break;
                case "Taxi":
                    ((ImageView) tr.findViewById(R.id.imageViewDepense)).setImageResource(R.drawable.ic_local_taxi_black_24dp);
                    break;
                case "Parking":
                    ((ImageView) tr.findViewById(R.id.imageViewDepense)).setImageResource(R.drawable.ic_local_parking_black_24dp);
                    break;
                case "Bus":
                    ((ImageView) tr.findViewById(R.id.imageViewDepense)).setImageResource(R.drawable.ic_directions_bus_black_24dp);
                    break;
                case "Autoroute":
                    ((ImageView) tr.findViewById(R.id.imageViewDepense)).setImageResource(R.drawable.ic_directions_car_black_24dp);
                    break;
                case "Restaurant":
                    ((ImageView) tr.findViewById(R.id.imageViewDepense)).setImageResource(R.drawable.ic_restaurant_black_24dp);
                    break;
                case "Avion":
                    ((ImageView) tr.findViewById(R.id.imageViewDepense)).setImageResource(R.drawable.ic_airplanemode_active_black_24dp);
                    break;
            }
            switch (etatRemboursement){
                case "false":
                    ((ImageView) tr.findViewById(R.id.imageRefund)).setImageResource(R.drawable.if_circle_red_10282);
                    break;
                case "Attente":
                    ((ImageView) tr.findViewById(R.id.imageRefund)).setImageResource(R.drawable.if_loading_1055035);
                    break;
                case "Rembourse":
                    ((ImageView) tr.findViewById(R.id.imageRefund)).setImageResource(R.drawable.if_money_299107);
            }
            ((TextView) tr.findViewById(R.id.textIdDepense)).setText("N° : "+idDepense);
            ((TextView) tr.findViewById(R.id.textDatePaiement)).setText("Date paiement : "+date_paiement);
            ((TextView) tr.findViewById(R.id.textCommentaires)).setText("Commentaire : "+commentaire);
            ((TextView) tr.findViewById(R.id.textMontantDepense)).setText("Montant : "+montant+" €");
            tr.findViewById(R.id.btnVoirDepense).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentListDepense = new Intent(ListDepenseActivity.this, DetailDepenseActivity.class);
                    intentListDepense.putExtra("id_depense", idDepense);
                    intentListDepense.putExtra("date_paiement", date_paiement);
                    intentListDepense.putExtra("libelle", libelle);
                    intentListDepense.putExtra("commentaire", commentaire);
                    intentListDepense.putExtra("montant", montant);
                    intentListDepense.putExtra("id_notefrais", idNotefrais);
                    startActivity(intentListDepense);
                }
            });
            table.addView(tr);
        }

        btnRetour = (ImageButton) findViewById(R.id.imageButtonBack);
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listNote = new Intent(ListDepenseActivity.this, MenuActivity.class);
                startActivity(listNote);
            }
        });
    }

    public String etatRemboursement(int idDepense){
        String result = "";

        String myUrl = "http://moralesmarie.alwaysdata.net/public/depense/"+idDepense+"/validation";

        HttpGetRequest getRequest = new HttpGetRequest();
        try{
            result = getRequest.execute(myUrl).get();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
//        if (!result.equals("false")){
            try {
                JSONObject jsonObject = new JSONObject(result);
                result = jsonObject.getString("Etat_Validation");
            } catch (JSONException e) {
                e.printStackTrace();
            }
//        }
        return result;
    }

    public ArrayList<Depense> listDepenses(int idNotefrais){
        ArrayList<Depense> lesDepenses = new ArrayList<Depense>();
        String result = "";

        String myUrl = "http://moralesmarie.alwaysdata.net/public/notefrais/"+idNotefrais+"/depense";

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
            JSONArray tblJSON = new JSONArray(result);
            for (int i = 0 ; i < tblJSON.length() ; i++) {
                JSONObject jsonObject = tblJSON.getJSONObject(i);
                Depense laDepense = new Depense(
                        jsonObject.getInt("Id_Depense"),
                        jsonObject.getString("DatePaiement_Depense"),
                        jsonObject.getString("Libelle_Depense"),
                        jsonObject.getString("Commentaire_Depense"),
                        Float.valueOf(jsonObject.getString("MontantRemboursement_Depense")),
                        jsonObject.getInt("Id_Notefrais")
                );
                lesDepenses.add(laDepense);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lesDepenses;
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
            Intent addNoteIntent = new Intent(ListDepenseActivity.this, AddFraisActivity.class);
            startActivity(addNoteIntent);

        } else if (id == R.id.list_note) {
            Intent listNoteIntent = new Intent(ListDepenseActivity.this, MenuActivity.class);
            startActivity(listNoteIntent);

        } else if (id == R.id.add_client) {
            Intent addClientIntent = new Intent(ListDepenseActivity.this, AddClientActivity.class);
            startActivity(addClientIntent);

        } else if (id == R.id.view_stats) {
            Intent graphIntent = new Intent(ListDepenseActivity.this, GraphActivity.class);
            startActivity(graphIntent);

        } else if (id == R.id.user_account) {
            Intent accountIntent = new Intent(ListDepenseActivity.this, AccountActivity.class);
            startActivity(accountIntent);

        } else if (id == R.id.user_deco) {
            Intent DecoIntent = new Intent(ListDepenseActivity.this, LoginActivity.class);
            startActivity(DecoIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
