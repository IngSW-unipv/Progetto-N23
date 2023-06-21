module it {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jsch;
    requires MaterialFX;
    requires de.jensd.fx.glyphs.materialicons;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires jakarta.persistence;

    opens it.unipv.ingsw.magstudio.model.bean to org.hibernate.orm.core, javafx.base;

    opens it.unipv.ingsw.magstudio.controller to javafx.fxml;
    exports it.unipv.ingsw.magstudio.controller;
    exports it.unipv.ingsw.magstudio;
    opens it.unipv.ingsw.magstudio to javafx.fxml;
}