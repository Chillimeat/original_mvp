package indi.ayun.mingwork_all.weight.popmenu;

/**
 * 菜单项.
 */
public class PopMenuMoreItem {
    public int id;
    public int resId;
    public String text;
    public int rescource;

    public PopMenuMoreItem(int id, String text,int rescource) {
        this.id = id;
        this.resId = 0;
        this.text = text;
        this.rescource=rescource;
    }

    public PopMenuMoreItem(int id, int resId, String text,int rescource) {
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