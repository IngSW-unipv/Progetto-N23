package it.unipv.ingsw.magstudio;

import it.unipv.ingsw.magstudio.controller.LoginController;
import it.unipv.ingsw.magstudio.model.facade.ConnectionFacade;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("HiveHub");

        stage.initStyle(StageStyle.DECORATED);
        stage.setResizable(false);

        LoginController controller = fxmlLoader.getController();

        stage.setScene(scene);
        controller.setStage(stage);
        stage.show();
    }

    public static void main(String[] args) {
        ConnectionFacade.getIstance().setStrategy(ConnectionFacade.ConnectionStrategy.MYSQL_OVER_SSH);
        launch();
    }
}
