package com.gingold.basisdependency.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.gingold.basisdependency.Base.BaseActivity;
import com.gingold.basisdependency.R;
import com.gingold.basislibrary.utils.BasisLogUtils;
import com.gingold.basislibrary.utils.BasisTimesUtils;

public class DateTimePickerActivity extends BaseActivity {
    private DatePicker dp_datetimepicker_date;
    private TimePicker tp_datetimepicker_time;

    @Override
    public void setupViewLayout() {
        setContentView(R.layout.activity_datetimepicker);
        initTitle("日期时间选择", "");
    }

    @Override
    public void initView() {
        dp_datetimepicker_date = getViewNoClickable(R.id.dp_datetimepicker_date);
        tp_datetimepicker_time = getViewNoClickable(R.id.tp_datetimepicker_time);
    }

    @Override
    public void listener() {
        setOnClickListener(R.id.tv_datetimepicker_date, R.id.tv_datetimepicker_time, R.id.tv_datetimepicker_yearmonth, R.id.tv_datetimepicker_monthday);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void logicDispose() {
        //日期选择器初始化和变化监听
        dp_datetimepicker_date.init(2016, 1, 1, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                BasisLogUtils.e("onDateChanged: " + year + "-" + monthOfYear + "-" + dayOfMonth);
            }
        });

        //时间选择器初始化和变化监听
        tp_datetimepicker_time.setIs24HourView(true);
        tp_datetimepicker_time.setHour(16);
        tp_datetimepicker_time.setMinute(36);
        tp_datetimepicker_time.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                BasisLogUtils.e(hourOfDay + ":" + minute);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_datetimepicker_date://年月日选择
                showYearMonthDayPicker();
                break;
            case R.id.tv_datetimepicker_yearmonth://年月选择
                showYearMonthPicker();
                break;
            case R.id.tv_datetimepicker_monthday://月日选择
                showMonthDayPicker();
                break;
            case R.id.tv_datetimepicker_time://时间选择
                showTimerPicker();
                break;

        }
    }

    /**
     * 时间选择
     */
    private void showTimerPicker() {
        BasisTimesUtils.showTimerPickerDialog(mActivity, true, "请选择时间", 21, 33, true, new BasisTimesUtils.OnTimerPickerListener() {
            @Override
            public void onConfirm(int hourOfDay, int minute) {
                toast(hourOfDay + ":" + minute);
            }

            @Override
            public void onCancel() {
                toast("cancle");
            }
        });
    }

    /**
     * 月日选择
     */
    private void showMonthDayPicker() {
        BasisTimesUtils.showDatePickerDialog(context, false, "请选择月日", 2015, 8, 28, new BasisTimesUtils.OnDatePickerListener() {

            @Override
            public void onConfirm(int year, int month, int dayOfMonth) {
                toast(year + "-" + month + "-" + dayOfMonth);
            }

            @Override
            public void onCancel() {
                toast("cancle");
            }
        }).setYearGone();
    }

    /**
     * 年月选择
     */
    private void showYearMonthPicker() {
        BasisTimesUtils.showDatePickerDialog(context, true, "", 2015, 12, 22,
                new BasisTimesUtils.OnDatePickerListener() {

                    @Override
                    public void onConfirm(int year, int month, int dayOfMonth) {
                        toast(year + "-" + month + "-" + dayOfMonth);
                    }

                    @Override
                    public void onCancel() {
                        toast("cancle");
                    }
                }).setDayGone();
    }

    /**
     * 年月日选择
     */
    private void showYearMonthDayPicker() {
        BasisTimesUtils.showDatePickerDialog(context, BasisTimesUtils.THEME_HOLO_DARK, "请选择年月日", 2015, 1, 1, new BasisTimesUtils.OnDatePickerListener() {

            @Override
            public void onConfirm(int year, int month, int dayOfMonth) {
                toast(year + "-" + month + "-" + dayOfMonth);
            }

            @Override
            public void onCancel() {
                toast("cancle");
            }
        });
    }

}
