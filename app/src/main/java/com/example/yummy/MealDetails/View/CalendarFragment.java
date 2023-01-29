package com.example.yummy.MealDetails.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yummy.MainActivity.View.MainActivity;
import com.example.yummy.R;
import com.example.yummy.Utility.NetworkChecker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarFragment extends Fragment {

    CalendarView calendar;
    TextView date_view;
    String startTime;
    String endTime;
    Date mStartTime;
    Date mEndTime;
    NetworkChecker networkChecker = NetworkChecker.getInstance();


    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendar = (CalendarView)
                view.findViewById(R.id.calendar);
        date_view = (TextView)
                view.findViewById(R.id.date_view);

        calendar.setOnDateChangeListener(
                new CalendarView
                        .OnDateChangeListener() {


                    @Override
                    public void onSelectedDayChange(
                            @NonNull CalendarView view,
                            int year,
                            int month,
                            int dayOfMonth) {

                        if (MainActivity.isLoginAsGuest == false) {
                            if (!networkChecker.checkIfInternetIsConnected()) {
                                MainActivity.mainActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.mainActivity, "Turn internet on to be able to save meals to your favorites.", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            } else if (networkChecker.checkIfInternetIsConnected()) {
                                String date
                                        = dayOfMonth + "-"
                                        + (month + 1) + "-" + year;

                                startTime = Integer.toString(year) + "-" + Integer.toString(month + 1) + "-" + Integer.toString(dayOfMonth) + "T09:00:00";
                                endTime = Integer.toString(year) + "-" + Integer.toString(month + 1) + "-" + Integer.toString(dayOfMonth) + "T12:00:00";


                                SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                try {
                                    mStartTime = mSimpleDateFormat.parse(startTime);
                                    mEndTime = mSimpleDateFormat.parse(endTime);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                                Intent mIntent = new Intent(Intent.ACTION_EDIT);
                                mIntent.setType("vnd.android.cursor.item/event");
                                mIntent.putExtra("beginTime", mStartTime.getTime());
                                mIntent.putExtra("time", true);
                                mIntent.putExtra("endTime", mEndTime.getTime());
                                mIntent.putExtra("title", CalendarFragmentArgs.fromBundle(getArguments()).getMealName());
                                startActivity(mIntent);


                                MainActivity.navController.popBackStack();
                            }

                        } else {
                            Toast.makeText(requireContext(), "You need to log in to be able to save meals to your favorites.", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }
}