package indi.ayun.mingwork_all.weight;


import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;

import java.util.Calendar;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * A simple dialog containing an {@link DatePicker}.
 * <p>
 * See the <a href="{@docRoot}guide/topics/ui/controls/pickers.html">Pickers</a>
 * guide.
 */
public class DatePickerFragment extends AlertDialog {
    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String DAY = "day";
    private int year = 2000;
    private int monthOfYear =12;
    private int dayOfMonth =12;
    public static int TYPE_BIRTHDAY=1;

    private final DatePicker mDatePicker;

    private OnDateChangedListener listener;
    private Context context;

    /**
     * 构造方法
     */
    public DatePickerFragment(Context context) {
        this(context, 0, Calendar.getInstance(), -1, -1, -1,null);
    }
    public DatePickerFragment(Context context, @StyleRes int themeResId) {
        this(context, themeResId, Calendar.getInstance(), -1, -1, -1,null);
    }
    public DatePickerFragment(@NonNull Context context, int year, int month, int dayOfMonth) {
        this(context, 0, null, year, month, dayOfMonth,null);
    }
    public DatePickerFragment(@NonNull Context context, @StyleRes int themeResId, int year, int monthOfYear, int dayOfMonth) {
        this(context, themeResId, null, year, monthOfYear, dayOfMonth,null);
    }
    public DatePickerFragment(@NonNull Context context, int year, int month, int dayOfMonth, RelativeLayout relativeLayout) {
        this(context, 0, null, year, month, dayOfMonth,relativeLayout);
    }
    public DatePickerFragment(@NonNull Context context, @StyleRes int themeResId, int year, int monthOfYear, int dayOfMonth, RelativeLayout relativeLayout) {
        this(context, themeResId, null, year, monthOfYear, dayOfMonth,relativeLayout);
    }


    /**
     * 初始化方法
     * @param context 批次1：每个构造方法都有，不用处理初始化
     * @param themeResId 批次3：样式，是否使用默认样式
     * @param calendar 批次2：是否使用默认的时间
     * @param year 批次2：是否使用默认的时间
     * @param monthOfYear 批次2：是否使用默认的时间
     * @param dayOfMonth 批次2：是否使用默认的时间
     * @param relativeLayout 批次5：判断是不是弹窗
     */
    private DatePickerFragment(@NonNull Context context, @StyleRes int themeResId, @Nullable Calendar calendar, int year, int monthOfYear, int dayOfMonth, RelativeLayout relativeLayout) {
        super(context, resolveDialogTheme(context, themeResId));
        if (calendar != null) {
            year = calendar.get(Calendar.YEAR);
            monthOfYear = calendar.get(Calendar.MONTH);
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        }
        this.year=year;
        this.monthOfYear=monthOfYear;
        this.dayOfMonth=dayOfMonth;
        this.context=context;

        final Context themeContext = getContext();
        final LayoutInflater inflater = LayoutInflater.from(themeContext);
        final View view = inflater.inflate(Resources.getSystem().getIdentifier("date_picker_dialog","layout","android"), null);

        if (relativeLayout!=null) {
            //relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            //RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            //layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            relativeLayout.addView(view);
        }else {
            setView(view);
        }
        mDatePicker =  view.findViewById(Resources.getSystem().getIdentifier("datePicker","id","android"));
        mDatePicker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        //mDatePicker.setValidationCallback(mValidationCallback);
    }

    public void setOnDateListener(@Nullable OnDateChangedListener listener) {
        this.listener = listener;
    }

    public void showPicker(int type){

        mDatePicker.init(year, monthOfYear, dayOfMonth, listener);
        if (type==TYPE_BIRTHDAY) {
            Calendar calendar = Calendar.getInstance();
            mDatePicker.setMinDate(calendar.get(Calendar.YEAR) - 70);
            mDatePicker.setMaxDate(System.currentTimeMillis());
        }
    }

    /**
     * 得到对象
     * @return
     */
    @NonNull
    public DatePicker getDatePicker() {
        return mDatePicker;
    }

    /**
     * 处理theme的初始化
     * @param context
     * @param themeResId
     * @return
     */
    static @StyleRes int resolveDialogTheme(@NonNull Context context, @StyleRes int themeResId) {
        if (themeResId == 0) {
            final TypedValue outValue = new TypedValue();
            context.getTheme().resolveAttribute(Resources.getSystem().getIdentifier("datePickerDialogTheme","attr","android"), outValue, true);
            return outValue.resourceId;
        } else {
            return themeResId;
        }
    }


    /**
     * 数据更新
     * @param year
     * @param month
     * @param dayOfMonth
     */
    public void updateDate(int year, int month, int dayOfMonth) {
        mDatePicker.updateDate(year, month, dayOfMonth);
    }

    @Override
    public Bundle onSaveInstanceState() {
        final Bundle state = super.onSaveInstanceState();
        state.putInt(YEAR, mDatePicker.getYear());
        state.putInt(MONTH, mDatePicker.getMonth());
        state.putInt(DAY, mDatePicker.getDayOfMonth());
        return state;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final int year = savedInstanceState.getInt(YEAR);
        final int month = savedInstanceState.getInt(MONTH);
        final int day = savedInstanceState.getInt(DAY);
        mDatePicker.init(year, month, day, listener);
    }

//    private final ValidationCallback mValidationCallback = new ValidationCallback() {
//        @Override
//        public void onValidationChanged(boolean valid) {
//            final Button positive = getButton(BUTTON_POSITIVE);
//            if (positive != null) {
//                positive.setEnabled(valid);
//            }
//        }
//    };

    /**
     * The listener used to indicate the user has finished selecting a date.
     */
    public interface OnDateSetListener {
        /**
         * @param view the picker associated with the dialog
         * @param year the selected year
         * @param month the selected month (0-11 for compatibility with
         *              {@link Calendar#MONTH})
         * @param dayOfMonth th selected day of the month (1-31, depending on
         *                   month)
         */
        void onDateSet(DatePicker view, int year, int month, int dayOfMonth);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction()== MotionEvent.ACTION_DOWN){
            hideInput();//隐藏输入框
        }
        return super.onTouchEvent(event);
    }

    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager)context. getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}

