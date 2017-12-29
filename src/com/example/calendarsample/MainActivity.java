package com.example.calendarsample;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.dms.hanumanchalisa.adapter.CalendarDataAdapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnClickListener {

    private long startDate;
    private long endDate;
    private GridView calendarView;
    private Calendar _calendar;
    private int month, year;
    private CalendarDataAdapter adapter;
    private Button selectedDayMonthYearButton;
    private Button currentMonth;
    private ImageView prevMonth;
    private ImageView nextMonth;
    private static final String dateTemplate = "MMMM yyyy";
    private int MAX_DAYS_IN_WEEK = 7;
    private List<Integer> daysinWeekHeader = null;
    private GridView weekLayout;
    private Iterator<Integer> iterator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_calendar_view);

        _calendar = Calendar.getInstance(Locale.getDefault());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Now use today date.
        c.add(Calendar.DATE, 40); // Adding 5 days
        String output = sdf.format(c.getTime());
        System.out.println(output);
        _calendar.set(2017, 8, 23);

        selectedDayMonthYearButton = (Button) this.findViewById(R.id.selectedDayMonthYear);
        selectedDayMonthYearButton.setText("Select Date");

        prevMonth = (ImageView) this.findViewById(R.id.prevMonth);
        prevMonth.setOnClickListener(this);

        currentMonth = (Button) this.findViewById(R.id.currentMonth);
        currentMonth.setText(DateFormat.format(dateTemplate, _calendar.getTime()));

        nextMonth = (ImageView) this.findViewById(R.id.nextMonth);
        nextMonth.setOnClickListener(this);

        calendarView = (GridView) findViewById(R.id.calendar);

        weekLayout = (GridView) findViewById(R.id.calendarheader);

        month = _calendar.get(Calendar.MONTH) + 1;
        year = _calendar.get(Calendar.YEAR);
        constructWeekHeader(2);
        weekLayout.setAdapter(new WeekDayAdapter());
        adapter = new CalendarDataAdapter(getApplicationContext(), R.id.calendar_day_gridcell,
                month, year);
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);

        /*
         * calendarView.setMinDate(startDate);
         * 
         * calendarView.setMaxDate(endDate);
         */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setGridCellAdapterToDate(int month, int year) {
        adapter = new CalendarDataAdapter(getApplicationContext(), R.id.calendar_day_gridcell,
                month, year);
        _calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
        currentMonth.setText(DateFormat.format(dateTemplate, _calendar.getTime()));
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        /*
         * if (year == 2017 && (month >= 9 && month <= 11)) { if (v == prevMonth
         * && month > 9) { if (month <= 1) { month = 12; year--; } else month--;
         * setGridCellAdapterToDate(month, year); } if (v == nextMonth && month
         * < 11) { if (month > 11) { month = 1; year++; } else month++;
         * setGridCellAdapterToDate(month, year); } }
         */
    }

    private String returnDay(int day) {
        String dayString = "";
        switch (day) {
        case 1:
            dayString = "Sun";
            break;
        case 2:
            dayString = "Mon";
            break;
        case 3:
            dayString = "Tue";
            break;
        case 4:
            dayString = "Wed";
            break;
        case 5:
            dayString = "Thu";
            break;
        case 6:
            dayString = "Fri";
            break;
        case 7:
            dayString = "Sat";
            break;
        default:
            break;
        }
        return dayString;
    }

    private class WeekDayAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 7;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position + 1;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) MainActivity.this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.calendar_day_gridcell, parent, false);

            }
            // TextView num_events_per_day = (TextView)
            // row.findViewById(R.id.num_events_per_day);
            // num_events_per_day.setVisibility(View.GONE);
            Button gridcell = (Button) row.findViewById(R.id.calendar_day_gridcell);
            // gridcell.setBackground(getDrawable(R.drawable.calendar_button_selector));
            gridcell.setText(returnDay(daysinWeekHeader.get(position)));
            // textView.setBackground(getResources().getDrawable(R.drawable.calendar_tile_small));
            return row;

        }

    }

    public void constructWeekHeader(int startDay) {
        daysinWeekHeader = new ArrayList<Integer>();
        // indicates days added to daysinWeekHeader
        int day_counter = 0;
        for (int temp = startDay; day_counter != MAX_DAYS_IN_WEEK; temp++, day_counter++) {
            if (temp == 8) {
                temp = 1;
            }
            daysinWeekHeader.add(temp);

        }
    }

}
