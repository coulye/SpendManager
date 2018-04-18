package fr.moralesmarie.spendmanager;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddFraisActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Button btnAddClient;
    private ImageButton imgbtnRest;
    private ImageButton imgBtnPlane;
    private ImageButton imgBtnCar;
    private ImageButton imgBtnTrain;
    private ImageButton imgBtnHotel;
    private ImageButton imgBtnGas;
    private ImageButton imgBtnParking;
    private ImageButton imgBtnTaxi;
    private ImageButton imgBtnBus;
    private ArrayList<ImageButton> tblImgBtn;

    Spinner spinner;

    private Calendar mCurrentDate;
    private EditText mDateExpense;
    private String mont;
    private String days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_add_frais);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        btnAddClient = (Button) findViewById(R.id.btnAddClient);
        btnAddClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddFraisActivity.this, AddClientActivity.class);
                startActivity(i);
            }
        });

        //Récupération du Spinner déclaré dans le fichier main.xml de res/layout
        spinner = (Spinner) findViewById(R.id.spinnerClient);
        //Création d'une liste d'élément à mettre dans le Spinner(pour l'exemple)
        List exempleList = new ArrayList();
        exempleList.add("Choisissez un client");
        exempleList.add("Assinie");
        exempleList.add("Bassam");
        exempleList.add("Abidjan");
		/*Le Spinner a besoin d'un adapter pour sa presentation alors on lui passe le context(this) et
                un fichier de presentation par défaut( android.R.layout.simple_spinner_item)
		Avec la liste des elements (exemple) */
        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                exempleList
        );
               /* On definit une présentation du spinner quand il est déroulé         (android.R.layout.simple_spinner_dropdown_item) */
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Enfin on passe l'adapter au Spinner et c'est tout
        spinner.setAdapter(adapter);

        //POP-UP CALENDRIER
        mCurrentDate = Calendar.getInstance();
        final int year = mCurrentDate.get(Calendar.YEAR);
        final int month = mCurrentDate.get(Calendar.MONTH);
        final int day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        mDateExpense = (EditText) findViewById(R.id.dateExpense);
        if(month >= 1 && month <= 9 ){
            mont = "0" + (month+1);
        } else {
            mont = "" + (month+1);
        }
        if(day >= 1 && day <= 9){
            days = "0" + day;
        } else {
            days = "" + day;
        }
        mDateExpense.setText(days + " - " + mont + " - " + year);
        mDateExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog mDatePicker = new DatePickerDialog(AddFraisActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        if(month >= 1 && month <= 9 ){
                            mont = "0" + (month+1);
                        } else {
                            mont = "" + (month+1);
                        }
                        if(day >= 1 && day <= 9){
                            days = "0" + day;
                        } else {
                            days = "" + day;
                        }
                        mDateExpense.setText(days + " - " + mont + " - " + year);
                    }
                }, year, month, day);
                mDatePicker.show();
            }
        });

        imgBtnBus = (ImageButton) findViewById(R.id.imageButtonBus);
        imgBtnCar = (ImageButton) findViewById(R.id.imageButtonCar);
        imgBtnGas = (ImageButton) findViewById(R.id.imageButtonGas);
        imgBtnHotel = (ImageButton) findViewById(R.id.imageButtonHotel);
        imgBtnParking = (ImageButton) findViewById(R.id.imageButtonParking);
        imgBtnPlane = (ImageButton) findViewById(R.id.imageButtonPlane);
        imgbtnRest = (ImageButton) findViewById(R.id.imageButtonRestaurant);
        imgBtnTaxi = (ImageButton) findViewById(R.id.imageButtonTaxi);
        imgBtnTrain = (ImageButton) findViewById(R.id.imageButtonTrain);

        tblImgBtn = new ArrayList<ImageButton>();
        tblImgBtn.add(imgBtnBus);
        tblImgBtn.add(imgBtnCar);
        tblImgBtn.add(imgBtnGas);
        tblImgBtn.add(imgBtnHotel);
        tblImgBtn.add(imgBtnParking);
        tblImgBtn.add(imgBtnPlane);
        tblImgBtn.add(imgbtnRest);
        tblImgBtn.add(imgBtnTaxi);
        tblImgBtn.add(imgBtnTrain);

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
            Intent addNoteIntent = new Intent(AddFraisActivity.this, AddFraisActivity.class);
            startActivity(addNoteIntent);

        } else if (id == R.id.list_note) {
            Intent listNoteIntent = new Intent(AddFraisActivity.this, MenuActivity.class);

        } else if (id == R.id.add_client) {

            Intent addClientIntent = new Intent(AddFraisActivity.this, AddClientActivity.class);
            startActivity(addClientIntent);

        } else if (id == R.id.view_stats) {

        } else if (id == R.id.user_account) {

        } else if (id == R.id.user_deco) {
            Intent DecoIntent = new Intent(AddFraisActivity.this, LoginActivity.class);
            startActivity(DecoIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
