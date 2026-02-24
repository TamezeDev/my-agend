package org.zeki.myagend.util;

public class Path {
    private static Path instance;
    //VIEWS
    private final String START_VIEW = "/fxml/first-scene.fxml";
    private final String MAIN_AGEND_VIEW = "/fxml/contacts-scene.fxml";
    private final String ADD_CONTACT_VIEW = "/fxml/detail-contact-scene.fxml";
    private final String THEME_SCENE = "/fxml/themes-scene.fxml";
    //IMG
    private final String MAIN_ICON = "/img/main_icon.png";
    private final String CALL_ICON = "/img/call.png";
    private final String EDIT_ICON = "/img/edit.png";
    private final String DELETE_ICON = "/img/delete.png";
    private final String EMPTY_PHOTO = "/img/empty_photo.png";
    //CSS
    private final String GLOBAL_STYLES = "/css/global-styles.css";
    private final String DEFAULT_THEME_STYLE = "/css/default-theme.css";
    private final String SPRING_THEME_STYLE = "/css/spring-theme.css";
    private final String SUMMER_THEME_STYLE = "/css/summer-theme.css";
    private final String AUTUMN_THEME_STYLE = "/css/autumn-theme.css";
    private final String WINTER_THEME_STYLE = "/css/winter-theme.css";
    //FILE
    private final String CONTACTS_FILE = "data/contacts.bin";
    private final String CONFIG_FILE = "data/config.txt";

    private Path() {
    }

    public static Path getInstance() {
        if (instance == null) {
            instance = new Path();
        }
        return instance;
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

    public String getCALL_ICON() {
        return CALL_ICON;
    }

    public String getEDIT_ICON() {
        return EDIT_ICON;
    }

    public String getDELETE_ICON() {
        return DELETE_ICON;
    }

    public String getEMPTY_PHOTO() {
        return EMPTY_PHOTO;
    }

    public String getADD_CONTACT_VIEW() {
        return ADD_CONTACT_VIEW;

    }

    public String getCONTACTS_FILE() {
        return CONTACTS_FILE;
    }

    public String getTHEME_SCENE() {
        return THEME_SCENE;
    }

    public String getSPRING_THEME_STYLE() {
        return SPRING_THEME_STYLE;
    }

    public String getSUMMER_THEME_STYLE() {
        return SUMMER_THEME_STYLE;
    }

    public String getAUTUMN_THEME_STYLE() {
        return AUTUMN_THEME_STYLE;
    }

    public String getWINTER_THEME_STYLE() {
        return WINTER_THEME_STYLE;
    }

    public String getDEFAULT_THEME_STYLE() {
        return DEFAULT_THEME_STYLE;

    }

    public String getCONFIG_FILE() {
        return CONFIG_FILE;
    }
}
