package fr.moralesmarie.spendmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.concurrent.ExecutionException;

import fr.moralesmarie.spendmanager.Class.Frais;
import fr.moralesmarie.spendmanager.Class.Justificatif;
import fr.moralesmarie.spendmanager.Class.Trajet;
import fr.moralesmarie.spendmanager.Fragment.DetailFraisFragment;
import fr.moralesmarie.spendmanager.Fragment.DetailTrajetFragment;
import fr.moralesmarie.spendmanager.HttpRequest.HttpGetRequest;

public class DetailDepenseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private int idDepense;
    private String datePaiement;
    private String libelle;
    private String commentaire;
    private float montant;
    private int idNotefrais;

    private TextView titreDepense;
    private ImageView imageViewType;
    private TextView textDatePaiement;
    private TextView textLibelle;
    private TextView textCommentaire;
    private TextView textMontant;
    private TextView textIdNotefrais;
    private ImageView imageJustificatif;
    private TextView titreDateFrais;
    private TextView textDateFrais;
    private TextView titreDuree;
    private TextView textDuree;
    private TextView titreVilleDepard;
    private TextView textVilleDepard;
    private TextView titreVilleArrivee;
    private TextView textVilleArrivee;
    private TextView titreDateAller;
    private TextView textDateAller;
    private TextView titreDateRetour;
    private TextView textDateRetour;
    private TextView titreKilometre;
    private TextView textKilometre;

    private ImageButton btnRetour;

	final String LOGIN_USER = "user_profile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail_depense);

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

        titreDepense = (TextView) findViewById(R.id.textTitreDetailDepense);
        imageViewType = (ImageView) findViewById(R.id.imageViewType);
        textDatePaiement = (TextView) findViewById(R.id.textDatePaiement);
        textLibelle = (TextView) findViewById(R.id.textLibelle);
        textCommentaire = (TextView) findViewById(R.id.textCommentaire);
        textMontant = (TextView) findViewById(R.id.textMontant);
        textIdNotefrais = (TextView) findViewById(R.id.textIdNotefrais);
        imageJustificatif = (ImageView) findViewById(R.id.imageJustificatif);
//        titreDateFrais = (TextView) findViewById(R.id.titreDateFrais2);
//        textDateFrais = (TextView) findViewById(R.id.textDatefrais2);
//        titreDuree = (TextView) findViewById(R.id.titreDuree);
//        textDuree = (TextView) findViewById(R.id.textDuree);
//        titreVilleDepard = (TextView) findViewById(R.id.titreVilleDepard);
//        textVilleDepard = (TextView) findViewById(R.id.textVilleDepard);
//        titreVilleArrivee = (TextView) findViewById(R.id.titreVilleArrivee);
//        textVilleArrivee = (TextView) findViewById(R.id.textVilleArrivee);
//        titreDateAller = (TextView) findViewById(R.id.titreDateAller);
//        textDateAller = (TextView) findViewById(R.id.textDateAller);
//        titreDateRetour = (TextView) findViewById(R.id.titreDateRetour);
//        textDateRetour = (TextView) findViewById(R.id.textDateRetour);
//        titreKilometre = (TextView) findViewById(R.id.titreKilometre);
//        textKilometre = (TextView) findViewById(R.id.textKilometre);

        Intent intentDepense = getIntent();
        idDepense = Integer.parseInt(intentDepense.getStringExtra("id_depense"));
        datePaiement = intentDepense.getStringExtra("date_paiement");
        libelle = intentDepense.getStringExtra("libelle");
        commentaire = intentDepense.getStringExtra("commentaire");
        montant = Float.parseFloat(intentDepense.getStringExtra("montant"));
        idNotefrais = Integer.parseInt(intentDepense.getStringExtra("id_notefrais"));

        switch (libelle) {
            case "Essence":
                imageViewType.setImageResource(R.drawable.ic_local_gas_station_black_24dp);
                break;
            case "Train":
                imageViewType.setImageResource(R.drawable.ic_train_black_24dp);
                break;
            case "Hotel":
                imageViewType.setImageResource(R.drawable.ic_hotel_black_24dp);
                break;
            case "Taxi":
                imageViewType.setImageResource(R.drawable.ic_local_taxi_black_24dp);
                break;
            case "Parking":
                imageViewType.setImageResource(R.drawable.ic_local_parking_black_24dp);
                break;
            case "Bus":
                imageViewType.setImageResource(R.drawable.ic_directions_bus_black_24dp);
                break;
            case "Autoroute":
                imageViewType.setImageResource(R.drawable.ic_directions_car_black_24dp);
                break;
            case "Restaurant":
                imageViewType.setImageResource(R.drawable.ic_restaurant_black_24dp);
                break;
            case "Avion":
                imageViewType.setImageResource(R.drawable.ic_airplanemode_active_black_24dp);
                break;
        }
        textDatePaiement.setText(datePaiement);
        textLibelle.setText(libelle);
        textCommentaire.setText(commentaire);
        textMontant.setText(montant + " €");
        textIdNotefrais.setText("Note de frais N° " + idNotefrais);

//        String encodedImageData = catchJustificatif(idDepense, idNotefrais);
//        System.out.println("toto "+encodedImageData);
//        byte[] imageBytes = Base64.decode(encodedImageData, Base64.DEFAULT);
//        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
//        System.out.println("toto "+decodedImage);
//        imageJustificatif.setImageBitmap(decodedImage);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (libelle.equals("Essence") || libelle.equals("Hotel") || libelle.equals("Parking") || libelle.equals("Restaurant")) {
            titreDepense.setText("Détails du Frais N° " + idDepense);

            Frais leFrais = catchFrais(idDepense);
            DetailFraisFragment detailFraisFragment = new DetailFraisFragment();
            detailFraisFragment.setStringDateFrais(leFrais.getDate_Frais());
            transaction.replace(R.id.fragmentLayout, detailFraisFragment);


//            titreDuree.setVisibility(View.INVISIBLE);
//            textDuree.setVisibility(View.INVISIBLE);
//            titreVilleDepard.setVisibility(View.INVISIBLE);
//            textVilleDepard.setVisibility(View.INVISIBLE);
//            titreVilleArrivee.setVisibility(View.INVISIBLE);
//            textVilleArrivee.setVisibility(View.INVISIBLE);
//            titreDateAller.setVisibility(View.INVISIBLE);
//            textDateAller.setVisibility(View.INVISIBLE);
//            titreDateRetour.setVisibility(View.INVISIBLE);
//            textDateRetour.setVisibility(View.INVISIBLE);
//            titreKilometre.setVisibility(View.INVISIBLE);
//            textKilometre.setVisibility(View.INVISIBLE);

//            textDateFrais.setText(leFrais.getDate_Frais());


        } else if (libelle.equals("Train") || libelle.equals("Taxi") || libelle.equals("Bus") ||
                libelle.equals("Autoroute") || libelle.equals("Avion")) {
            titreDepense.setText("Détails du Trajet N° " + idDepense);

            Trajet leTrajet = catchTrajet(idDepense);
            DetailTrajetFragment detailTrajetFragment = new DetailTrajetFragment();
            detailTrajetFragment.setStringDuree(leTrajet.getDuree_Trajet());
            detailTrajetFragment.setStringDateAller(leTrajet.getDateAller_Trajet());
            detailTrajetFragment.setStringDateRetour(leTrajet.getDateRetour_Trajet());
            detailTrajetFragment.setStringVilleDepard(leTrajet.getVilleDepart_Trajet());
            detailTrajetFragment.setStringVilleArrivee(leTrajet.getVilleArrivee_Trajet());
            detailTrajetFragment.setStringKilometre(String.valueOf(leTrajet.getKilometre_Trajet()));
            transaction.replace(R.id.fragmentLayout, detailTrajetFragment);



//            titreDateFrais.setVisibility(View.INVISIBLE);
//            textDateFrais.setVisibility(View.INVISIBLE);

//            textDuree.setText(leTrajet.getDuree_Trajet());
//            textVilleDepard.setText(leTrajet.getVilleDepart_Trajet());
//            textVilleArrivee.setText(leTrajet.getVilleArrivee_Trajet());
//            textDateAller.setText(leTrajet.getDateAller_Trajet());
//            textDateRetour.setText(leTrajet.getDateRetour_Trajet());
//            textKilometre.setText(String.valueOf(leTrajet.getKilometre_Trajet()));
        }
        transaction.commit();

        btnRetour = (ImageButton) findViewById(R.id.imageButtonBack);
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(DetailDepenseActivity.this, MenuActivity.class);
                startActivity(back);
            }
        });
    }

    public String catchJustificatif (int idDepense, int idNotefrais){
        Justificatif leJustificatif = null;
        String encodedImageData = "";

        String result = "";
        String myUrl = "http://moralesmarie.alwaysdata.net/public/notefrais/"+idNotefrais+"/depense/"+idDepense+"/justificatif";

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
            JSONObject jsonObject = new JSONObject(result);
            leJustificatif = new Justificatif(
                    jsonObject.getInt("Id_Justificatif"),
                    jsonObject.getString("Titre_Justificatif"),
                    jsonObject.getString("Url_Justificatif"),
                    idDepense,
                    idNotefrais
            );
            encodedImageData = leJustificatif.getUrl_Justificatif();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return encodedImageData;
    }

    public Frais catchFrais(int idDepense){
        Frais leFrais = new Frais();

        String result = "";
        String myUrl = "http://moralesmarie.alwaysdata.net/public/depense/"+idDepense+"/frais";

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
            JSONObject jsonObject = new JSONObject(result);
            leFrais = new Frais(
                    jsonObject.getString("Date_Frais"),
                    idDepense,
                    datePaiement,
                    libelle,
                    commentaire,
                    montant,
                    idNotefrais
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return leFrais;
    }

    public Trajet catchTrajet(int idDepense){
        Trajet leTrajet = new Trajet();

        String result = "";
        String myUrl = "http://moralesmarie.alwaysdata.net/public/depense/"+idDepense+"/trajet";

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
            JSONObject jsonObject = new JSONObject(result);
            leTrajet = new Trajet(
                    jsonObject.getString("Duree_Trajet"),
                    jsonObject.getString("VilleDepart_Trajet"),
                    jsonObject.getString("VilleArrivee_Trajet"),
                    jsonObject.getString("DateAller_Trajet"),
                    jsonObject.getString("DateRetour_Trajet"),
                    Float.parseFloat(jsonObject.getString("Kilometre_Trajet")),
                    idDepense,
                    datePaiement,
                    libelle,
                    commentaire,
                    montant,
                    idNotefrais
            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return leTrajet;
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
            Intent addNoteIntent = new Intent(DetailDepenseActivity.this, AddFraisActivity.class);
            startActivity(addNoteIntent);

        } else if (id == R.id.list_note) {
            Intent listNoteIntent = new Intent(DetailDepenseActivity.this, MenuActivity.class);
            startActivity(listNoteIntent);

        } else if (id == R.id.add_client) {

            Intent addClientIntent = new Intent(DetailDepenseActivity.this, AddClientActivity.class);
            startActivity(addClientIntent);

        } else if (id == R.id.view_stats) {
            Intent graphIntent = new Intent(DetailDepenseActivity.this, GraphActivity.class);
            startActivity(graphIntent);

        } else if (id == R.id.user_account) {
            Intent accountIntent = new Intent(DetailDepenseActivity.this, AccountActivity.class);
            startActivity(accountIntent);

        } else if (id == R.id.user_deco) {
            Intent DecoIntent = new Intent(DetailDepenseActivity.this, LoginActivity.class);
            startActivity(DecoIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
