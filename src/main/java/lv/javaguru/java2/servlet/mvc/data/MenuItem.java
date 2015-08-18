package lv.javaguru.java2.servlet.mvc.data;

public class MenuItem {

    String caption = "";
    String url = "/";

    public MenuItem(String url, String caption) {
        this.caption = caption;
        this.url = url;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
