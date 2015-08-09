package lv.javaguru.java2.servlet.mvc;


public class MVCModel {

    private Object data;
    private String jspName;

    public MVCModel(Object data, String jspName) {
        this.data = data;
        this.jspName = jspName;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getJspName() {
        return jspName;
    }

    public void setJspName(String jspName) {
        this.jspName = jspName;
    }
}
