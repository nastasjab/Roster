package lv.javaguru.java2.servlet.mvc;

public class MessageContents {

    String caption;
    String message;
    String url;
    String urlCaption;

    public MessageContents(String caption, String message, String url, String urlCaption) {
        this.url = url;
        this.caption = caption;
        this.message = message;
        this.urlCaption = urlCaption;
    }

    public String getCaption() {
        return caption;
    }

    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlCaption() {
        return urlCaption;
    }
}
