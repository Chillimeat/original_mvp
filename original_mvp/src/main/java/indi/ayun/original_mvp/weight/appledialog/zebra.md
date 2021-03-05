1. 设置对象
AppleDialog appleDialog;
2. 显示
appleDialog = new AppleDialog(context);
appleDialog.addItem(new AppleDialogItem(id, 内容，icon));
。。。。。。
appleDialog.addItem(new AppleDialogItem(id, 内容，icon));
appleDialog.setOnItemSelectedListener(new onAppleDialogListener());
appleDialogPlay.show();
3.监听
    /**
     * 仿照ios的底部弹窗返回值
     */
    class onAppleDialogListener implements AppleDialog.OnItemSelectedListener {
        @Override
        public void selected(View view, AppleDialogItem item, int position) {

        }
    }

