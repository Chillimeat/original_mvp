package indi.ayun.mingwork_all.widgets;

import android.os.CountDownTimer;
import android.widget.TextView;

import indi.ayun.mingwork_all.MingWork_All;


public class CountDownButton {

    // 倒计时timer
    private CountDownTimer countDownTimer;
    // 计时结束的回调接口
    private OnFinishListener listener;

    private TextView button;

    private int endColor;

    /**
     * @param button          需要显示倒计时的Button
     * @param finishString    默认显示的字符串
     * @param max             需要进行倒计时的最大值,单位是秒
     * @param countDownString 例如“（60）秒”
     * @param interval
     */
    public CountDownButton(final TextView button, final String countDownString, final String finishString, int max, int interval, final int startColor, final int endColor) {

        this.button = button;
        this.endColor=endColor;
        // 由于CountDownTimer并不是准确计时，在onTick方法调用的时候，time会有1-10ms左右的误差，这会导致最后一秒不会调用onTick()
        // 因此，设置间隔的时候，默认减去了10ms，从而减去误差。
        // 经过以上的微调，最后一秒的显示时间会由于10ms延迟的积累，导致显示时间比1s长max*10ms的时间，其他时间的显示正常,总时间正常
        countDownTimer = new CountDownTimer(max * 1000, interval * 1000 - 10) {
            @Override
            public void onTick(long time) {
                int b = 0, e = 0;
                for (int i = 0; i < countDownString.length(); i++) {
                    int chr_c = countDownString.charAt(i);

                    if (i == 0) {
                        if (chr_c >= 48 && chr_c <= 57) {
                            b = 0;
                        }
                    }
                    if (i == countDownString.length() - 1) {
                        if (chr_c >= 48 && chr_c <= 57) {
                            e = 0;
                        }
                    }
                    if (i != 0 && i != countDownString.length() - 1) {
                        int chr_a = countDownString.charAt(i + 1);
                        int chr_b = countDownString.charAt(i - 1);
                        if ((chr_c >= 48 && chr_c <= 57) && (chr_b < 48 || chr_b > 58)) b = i;
                        if ((chr_c >= 48 && chr_c <= 57) && (chr_a < 48 || chr_a > 58)) e = i;
                    }
                }
                // 第一次调用会有1-10ms的误差，因此需要+15ms，防止第一个数不显示，第二个数显示2s
                button.setTextColor(MingWork_All.getContext().getResources().getColor(startColor));
                button.setText(
                        countDownString.substring(0, b)
                                + ((time + 15) / 1000)
                                + countDownString.substring(e + 1, countDownString.length())
                );
                if (listener != null) {
                    listener.onCountDown();
                }
            }

            @Override
            public void onFinish() {
                button.setEnabled(true);
                button.setTextColor(MingWork_All.getContext().getResources().getColor(endColor));
                button.setText(finishString);
                if (listener != null) {
                    listener.finish();
                }
            }
        };
    }

    /**
     * 开始倒计时
     */
    public void start() {
        button.setEnabled(false);
        countDownTimer.start();
    }

    /**
     * 设置倒计时结束的监听器
     *
     * @param listener
     */
    public void setOnFinishListener(OnFinishListener listener) {
        this.listener = listener;
    }

    /**
     * 计时结束的回调接口
     */
    public interface OnFinishListener {
        void finish();

        void onCountDown();
    }

}