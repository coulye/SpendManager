package fr.moralesmarie.spendmanager.Fragment;

import android.app.DatePickerDialog;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import java.util.Calendar;
import fr.moralesmarie.spendmanager.R;

public class FraisFragment extends Fragment {

    private Calendar mCurrentDate;
    private String mDate;
    private String months;
    private String days;
    private String years;
    private EditText mDateExpense;
    private String SQLDateExpense;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frais_fragment, container, false);
        //Instancier vos composants graphique ici (faîtes vos findViewById)
        return view;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //POP-UP CALENDRIER
        mCurrentDate = Calendar.getInstance();
        final int year = mCurrentDate.get(Calendar.YEAR);
        final int month = mCurrentDate.get(Calendar.MONTH);
        final int day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        mDateExpense = (EditText) view.findViewById(R.id.dateExpense);
        if(month >= 1 && month <= 9 ){
            months = "0" + (month+1);
        } else {
            months = "" + (month+1);
        }
        if(day >= 1 && day <= 9){
            days = "0" + day;
        } else {
            days = "" + day;
        }
        years = String.valueOf(year);
        mDateExpense.setText(days + " - " + months + " - " + year);
        mDate = year + "-" + months + "-" + days;
        SQLDateExpense = year+"-"+months+"-"+days;
        mDateExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        if(month >= 1 && month <= 9 ){
                            months = "0" + (month+1);
                        } else {
                            months = "" + (month+1);
                        }
                        if(day >= 1 && day <= 9){
                            days = "0" + day;
                        } else {
                            days = "" + day;
                        }
                        mDateExpense.setText(days + " - " + months + " - " + year);
                        SQLDateExpense = year+"-"+months+"-"+days;
                    }
                }, year, month, day);
                mDatePicker.show();
            }
        });
    }

    public String getSQLDateExpense(){
        return SQLDateExpense;
    }

}
