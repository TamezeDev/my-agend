package org.zeki.myagend.util;

public class Path {
    private static Path instance;
    //VIEWS
    private final String START_VIEW = "/fxml/start-view.fxml";
    private final String MAIN_AGEND_VIEW = "/fxml/main-agend-view.fxml";
    //IMG
    private final String MAIN_ICON = "/img/main_icon.png";
    //CSS
    private final String GLOBAL_STYLES = "/css/global-styles.css";

    private Path(){
    }

    public static Path getInstance(){
        if (instance == null){
            instance = new Path();
        }
        return  instance;
    }

    public String getSTART_VIEW() {
        return START_VIEW;
    }

    public String getMAIN_ICON() {
        return MAIN_ICON;
    }

    public String getGLOBAL_STYLES() {
        return GLOBAL_STYLES;
    }

    public String getMAIN_AGEND_VIEW() {
        return MAIN_AGEND_VIEW;
    }
}
