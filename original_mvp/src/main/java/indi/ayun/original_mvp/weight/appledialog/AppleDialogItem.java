package indi.ayun.original_mvp.weight.appledialog;

/**
 * 菜单项.
 */
public class AppleDialogItem {
    public int id;
    public int resId;
    public String text;
    public int rescource;

    public AppleDialogItem(int id, String text) {
        this.id = id;
        this.resId = 0;
        this.text = text;
    }

    public AppleDialogItem(int id, int resId, String text, int rescource) {
        this.id = id;
        this.resId = resId;
        this.text = text;
        this.rescource=rescource;
    }

    public int getRescource() {
        return rescource;
    }

    public void setRescource(int rescource) {
        this.rescource = rescource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}