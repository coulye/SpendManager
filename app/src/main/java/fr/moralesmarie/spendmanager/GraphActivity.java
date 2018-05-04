package fr.moralesmarie.spendmanager;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import fr.moralesmarie.spendmanager.Class.Depense;
import fr.moralesmarie.spendmanager.HttpRequest.HttpGetRequest;

public class GraphActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    float countEssence = 0;
    float countTrain = 0;
    float countHotel = 0;
    float countTaxi = 0;
    float countParking = 0;
    float countBus = 0;
    float countAutoroute = 0;
    float countRestaurant = 0;
    float countAvion = 0;
    float sumEssence = 0;
    float sumTrain = 0;
    float sumHotel = 0;
    float sumTaxi = 0;
    float sumParking = 0;
    float sumBus = 0;
    float sumAutoroute = 0;
    float sumRestaurant = 0;
    float sumAvion = 0;
    float sumTotal = 0;
    float percentEssence;
    float percentTrain;
    float percentHotel;
    float percentTaxi;
    float percentParking;
    float percentBus;
    float percentAutoroute;
    float percentRestaurant;
    float percentAvion;

    private WebView webViewGraphType;
    private WebView webViewGraphMontant;
    private int idUtilisateur = 1;
    private ArrayList<Depense> listDepenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_graph);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listDepenses = listDepensesUtilisateur(idUtilisateur);
        for (Depense d : listDepenses) {
            switch(d.getLibelle_Depense()){
                case "Essence":
                    countEssence++;
                    sumEssence += d.getMontantRemboursement_Depense();
                    sumTotal += d.getMontantRemboursement_Depense();
                    break;
                case "Train":
                    countTrain++;
                    sumTrain += d.getMontantRemboursement_Depense();
                    sumTotal += d.getMontantRemboursement_Depense();
                    break;
                case "Hotel":
                    countHotel++;
                    sumHotel += d.getMontantRemboursement_Depense();
                    sumTotal += d.getMontantRemboursement_Depense();
                    break;
                case "Taxi":
                    countTaxi++;
                    sumTaxi += d.getMontantRemboursement_Depense();
                    sumTotal += d.getMontantRemboursement_Depense();
                    break;
                case "Parking":
                    countParking++;
                    sumParking += d.getMontantRemboursement_Depense();
                    sumTotal += d.getMontantRemboursement_Depense();
                    break;
                case "Bus":
                    countBus++;
                    sumBus += d.getMontantRemboursement_Depense();
                    sumTotal += d.getMontantRemboursement_Depense();
                    break;
                case "Autoroute":
                    countAutoroute++;
                    sumAutoroute += d.getMontantRemboursement_Depense();
                    sumTotal += d.getMontantRemboursement_Depense();
                    break;
                case "Restaurant":
                    countRestaurant++;
                    sumRestaurant += d.getMontantRemboursement_Depense();
                    sumTotal += d.getMontantRemboursement_Depense();
                    break;
                case "Avion":
                    countAvion++;
                    sumAvion += d.getMontantRemboursement_Depense();
                    sumTotal += d.getMontantRemboursement_Depense();
                    break;
            }
        }
        float nbreDepense = listDepenses.size();
        percentAvion = (countAvion/nbreDepense)*100;
        percentRestaurant = (countRestaurant/nbreDepense)*100;
        percentAutoroute = (countAutoroute/nbreDepense)*100;
        percentBus = (countBus/nbreDepense)*100;
        percentParking = (countParking/nbreDepense)*100;
        percentTaxi = (countTaxi/nbreDepense)*100;
        percentHotel = (countHotel/nbreDepense)*100;
        percentTrain = (countTrain/nbreDepense)*100;
        percentEssence = (countEssence/nbreDepense)*100;
        webViewGraphType = (WebView) findViewById(R.id.webViewGraphType);
        webViewGraphType.loadUrl("http://chart.apis.google.com/chart?cht=p&chs=350x250&chd=t:"+countAvion+","
                +countRestaurant+","+countAutoroute+","+countBus+","+countParking+","+countTaxi+","
                +countHotel+","+countTrain+","+countEssence+"" +
                "&chco=800000|A0522D|D2691E|DAA520|BC8F8F|DEB887|FFDEAD|FFEBCD|F0FFFF" +
                "&chl="+percentAvion+"%|"+percentRestaurant+"%|"+percentAutoroute+"%|"+percentBus+"%|"
                +percentParking+"%|"+percentTaxi+"%|"+percentHotel+"%|"+percentTrain+"%|"+percentEssence+"%" +
                "&chdl=Avion|Restaurant|Autoroute|Bus|Parking|Taxi|Hotel|Train|Essence" +
                "&chdlp=b");

        webViewGraphMontant = (WebView) findViewById(R.id.webViewGraphMontant);
        webViewGraphMontant.loadUrl("http://chart.apis.google.com/chart?cht=bvs&chs=350x250&chd=t:"+(sumAvion/sumTotal)*100+","
                +(sumRestaurant/sumTotal)*100+","+(sumAutoroute/sumTotal)*100+","+(sumBus/sumTotal)*100+","+(sumParking/sumTotal)*100+","
                +(sumTaxi/sumTotal)*100+","+(sumHotel/sumTotal)*100+","+(sumTrain/sumTotal)*100+","+(sumEssence/sumTotal)*100+
                "&chco=800000|A0522D|D2691E|DAA520|BC8F8F|DEB887|FFDEAD|FFEBCD|F0FFFF" +
                "&chxt=x,y" +
                "&chdl=Avion|Restaurant|Autoroute|Bus|Parking|Taxi|Hotel|Train|Essence");
    }

    public ArrayList<Depense> listDepensesUtilisateur(int idUtilisateur){
        ArrayList<Depense> lesDepenses = new ArrayList<Depense>();
        String result = "";

        String myUrl = "http://moralesmarie.alwaysdata.net/public/utilisateur/"+idUtilisateur+"/depense";

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
            Intent addNoteIntent = new Intent(GraphActivity.this, AddFraisActivity.class);
            startActivity(addNoteIntent);

        } else if (id == R.id.list_note) {
            Intent listNoteIntent = new Intent(GraphActivity.this, MenuActivity.class);
            startActivity(listNoteIntent);

        } else if (id == R.id.add_client) {

            Intent addClientIntent = new Intent(GraphActivity.this, AddClientActivity.class);
            startActivity(addClientIntent);

        } else if (id == R.id.view_stats) {
            Intent graphIntent = new Intent(GraphActivity.this, GraphActivity.class);
            startActivity(graphIntent);

        } else if (id == R.id.user_account) {

        } else if (id == R.id.user_deco) {
            Intent DecoIntent = new Intent(GraphActivity.this, LoginActivity.class);
            startActivity(DecoIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}