1.
OnMaliceClick：使用防止恶意点击的类，或者按钮上锁的类继承这个接口；
实现这两个方法：
 @Override
    public void onMaliceClick(View v) {

    }

    @Override
    public void onGoodClick(View v) {

    }
2.
MaliceClickLock maliceClickLock= new MaliceClickLock(this)初始化这个类，获取监听对象;
在需要锁住按钮的地方使用：maliceClickLock.setLock(v);
在需要释放按钮的地方使用：maliceClickLock.unLock(v);
锁住按钮同时传导点击事件.



