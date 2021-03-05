1. FlowLayout:流布局，比如标签
<indi.ayun.mingwork_all.weight.FlowLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"/>
。。。。。。
flowLayout.setData(array2List(label));


2.DatePickerFragment日期选择器
DatePickerFragment datePickerFragment = new DatePickerFragment(context, DatePickerDialog.THEME_HOLO_LIGHT, year, month, day, layout);
        datePickerFragment.setOnDateListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            }
        });
        datePickerFragment.showPicker(DatePickerFragment.TYPE_BIRTHDAY);//如果先择生日，限定70年

3. NoScrollViewPager可控制滑动的viewPager，适用于首页布局.

