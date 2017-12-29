package com.dms.views;

import com.example.calendarsample.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;

public class TaskToggleButton extends CompoundButton {

    static private final int UNKNOW = -1;
    static private final int TASK_NOT_COMPLETED = 0;
    static private final int TASK_COMPLETED = 1;
    private int state;

    public TaskToggleButton(Context context) {
        super(context);
        init();
    }

    public TaskToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TaskToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        state = UNKNOW;
        updateBtn();

        setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            // checkbox status is changed from uncheck to checked.
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch (state) {
                case UNKNOW:
                    state = TASK_NOT_COMPLETED;
                    break;
                case TASK_NOT_COMPLETED:
                    state = TASK_COMPLETED;
                    break;
                case TASK_COMPLETED:
                    state = UNKNOW;
                    break;
                }
                updateBtn();
            }
        });

    }

    private void updateBtn() {
        int btnDrawable = android.R.color.transparent;
        switch (state) {
        case UNKNOW:
            btnDrawable = android.R.color.transparent;
            break;
        case TASK_NOT_COMPLETED:
            btnDrawable = R.drawable.cross_mark;
            break;
        case TASK_COMPLETED:
            btnDrawable = R.drawable.correct_symbol;
            break;
        }
        setButtonDrawable(btnDrawable);

    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        updateBtn();
    }

}
