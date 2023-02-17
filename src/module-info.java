module it {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jsch;
    requires MaterialFX;
    requires mysql.connector.java;
    requires de.jensd.fx.glyphs.materialicons;


    opens it.unipv.ingsw.magstudio.controller to javafx.fxml;
    exports it.unipv.ingsw.magstudio.controller;
    exports it.unipv.ingsw.magstudio;
    opens it.unipv.ingsw.magstudio to javafx.fxml;
}