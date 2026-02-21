module org.zeki.myagend {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens org.zeki.myagend to javafx.fxml;
    exports org.zeki.myagend;
    exports org.zeki.myagend.controller.scene;
    opens org.zeki.myagend.controller.scene to javafx.fxml;
}