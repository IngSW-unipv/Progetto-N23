module it.unipv.ingsw {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jsch;
    requires MaterialFX;


    opens it.unipv.ingsw.magstudio.controller to javafx.fxml;
    exports it.unipv.ingsw.magstudio.controller;
}