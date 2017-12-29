package com.dms.hanumanchalisa.adapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import com.dms.views.TaskToggleButton;
import com.example.calendarsample.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CalendarDataAdapter extends BaseAdapter {
    private final Context _context;

    private final List<String> list;
    private static final int DAY_OFFSET = 1;

    /*
     * private final String[] months = { "January", "February", "March",
     * "April", "May", "June", "July", "August", "September", "October",
     * "November", "December" };
     */
    private final String[] months = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP",
            "OCT", "NOV", "DEC" };
    private final int[] daysOfMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    private int daysInMonth;
    private int currentDayOfMonth;
    private int currentWeekDay;
    private TaskToggleButton gridcell;
    private TextView num_events_per_day;
    private final HashMap<String, Integer> eventsPerMonthMap;
    String flag = "abc";
    String date_month_year;
    private Calendar startCal;
    private Calendar endCal;

    // Days in Current Month
    public CalendarDataAdapter(Context context, int textViewResourceId, int month, int year) {
        super();
        this._context = context;
        this.list = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
        setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));

        startCal = Calendar.getInstance();
        endCal = Calendar.getInstance();

        startCal.set(Calendar.DAY_OF_MONTH, 30);
        startCal.set(Calendar.MONTH, 9);
        startCal.set(Calendar.YEAR, 2017);

        endCal.set(Calendar.DAY_OF_MONTH, 9);
        endCal.set(Calendar.MONTH, 11);
        endCal.set(Calendar.YEAR, 2017);

        Date startDate = new Date(startCal.getTimeInMillis());
        Date endDate = new Date(endCal.getTimeInMillis());

        // Print Month
        // printMonth(month, year);

        // Initializes list with prayer dates
        getDaysBetweenDates(startDate, endDate);

        // Find Number of Events
        eventsPerMonthMap = findNumberOfEventsPerMonth(year, month);
    }

    private String getMonthAsString(int i) {
        return months[i];
    }

    private int getNumberOfDaysOfMonth(int i) {
        return daysOfMonth[i];
    }

    public String getItem(int position) {
        return dates_list.get(position);
    }

    @Override
    public int getCount() {
        return dates_list.size();
    }

    private void printMonth(int mm, int yy) {
        int trailingSpaces = 0;
        int daysInPrevMonth = 0;
        int prevMonth = 0;
        int prevYear = 0;
        int nextMonth = 0;
        int nextYear = 0;

        int currentMonth = mm - 1;
        daysInMonth = getNumberOfDaysOfMonth(currentMonth);

        // Gregorian Calendar : MINUS 1, set to FIRST OF MONTH
        GregorianCalendar cal = new GregorianCalendar(yy, 3, 20);

        if (currentMonth == 11) {
            prevMonth = currentMonth - 1;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            nextMonth = 0;
            prevYear = yy;
            nextYear = yy + 1;
        } else if (currentMonth == 0) {
            prevMonth = 11;
            prevYear = yy - 1;
            nextYear = yy;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            nextMonth = 1;
        } else {
            prevMonth = currentMonth - 1;
            nextMonth = currentMonth + 1;
            nextYear = yy;
            prevYear = yy;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
        }

        int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
        trailingSpaces = currentWeekDay;

        if (cal.isLeapYear(cal.get(Calendar.YEAR)) && mm == 1) {
            ++daysInMonth;
        }

        // Trailing Month days
        for (int i = 0; i < trailingSpaces; i++) {
            list.add(String.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i) + "-GREY"
                    + "-" + getMonthAsString(prevMonth) + "-" + prevYear);
        }
        // 21-BLUE-SEPTEMBER-17;

        // Current Month Days
        for (int i = 1; i <= daysInMonth; i++) {
            if (i == getCurrentDayOfMonth())
                list.add(String.valueOf(i) + "-BLUE" + "-" + getMonthAsString(currentMonth) + "-"
                        + yy);
            else
                list.add(String.valueOf(i) + "-WHITE" + "-" + getMonthAsString(currentMonth) + "-"
                        + yy);
        }
        int size = list.size() % 7;
        int nextmonth_days = size % 7;
        // Leading Month days
        for (int i = 0; i < list.size() % 7; i++) {
            list.add(String.valueOf(i + 1) + "-GREY" + "-" + getMonthAsString(nextMonth) + "-"
                    + nextYear);
        }
    }

    private HashMap<String, Integer> findNumberOfEventsPerMonth(int year, int month) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        return map;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) _context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.calendar_day_gridcell, parent, false);
        }

        // Get a reference to the Day gridcell
        gridcell = (TaskToggleButton) row.findViewById(R.id.calendar_day_gridcell);
        // gridcell.setOnClickListener(this);

        // ACCOUNT FOR SPACING

        String[] day_color = dates_list.get(position).split("-");
        String theday = day_color[0];
        String themonth = day_color[2];
        String theyear = day_color[3];
        if ((!eventsPerMonthMap.isEmpty()) && (eventsPerMonthMap != null)) {
            if (eventsPerMonthMap.containsKey(theday)) {
                num_events_per_day = (TextView) row.findViewById(R.id.num_events_per_day);
                Integer numEvents = (Integer) eventsPerMonthMap.get(theday);
                num_events_per_day.setText(numEvents.toString());
            }
        }
        num_events_per_day = (TextView) row.findViewById(R.id.num_events_per_day);
        num_events_per_day.setText(themonth);

        // Set the Day GridCell
        gridcell.setText(theday);
        gridcell.setTag(theday + "-" + themonth + "-" + theyear);

        if (day_color[1].equals("GREY"))
            gridcell.setTextColor(Color.LTGRAY);

        if (day_color[1].equals("WHITE"))
            gridcell.setTextColor(Color.WHITE);

        if (day_color[1].equals("BLUE"))
            gridcell.setTextColor(_context.getResources().getColor(R.color.static_text_color));

        return row;
    }

    /*
     * @Override public void onClick(View view) { date_month_year = (String)
     * view.getTag(); flag = "Date selected ..."; //
     * selectedDayMonthYearButton.setText("Selected: " + date_month_year); }
     */

    public int getCurrentDayOfMonth() {
        return currentDayOfMonth;
    }

    private void setCurrentDayOfMonth(int currentDayOfMonth) {
        this.currentDayOfMonth = currentDayOfMonth;
    }

    public void setCurrentWeekDay(int currentWeekDay) {
        this.currentWeekDay = currentWeekDay;
    }

    public int getCurrentWeekDay() {
        return currentWeekDay;
    }

    // 21-BLUE-SEPTEMBER-17;
    List<String> dates_list = new ArrayList<String>();

    public List<Date> getDaysBetweenDates(Date startdate, Date enddate) {
        List<Date> dates = new ArrayList<Date>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);

        while (calendar.getTime().before(enddate)) {
            Date result = calendar.getTime();
            dates.add(result);
            dates_list.add(calendar.get(Calendar.DATE) + "-BLUE-"
                    + getMonthAsString(calendar.get(Calendar.MONTH)) + "-"
                    + calendar.get(Calendar.YEAR));
            calendar.add(Calendar.DATE, 1);
        }
        // adding last date as loop will work like < not <=
        dates_list.add(calendar.get(Calendar.DATE) + "-BLUE-"
                + getMonthAsString(calendar.get(Calendar.MONTH)) + "-"
                + calendar.get(Calendar.YEAR));
        return dates;
    }

}
