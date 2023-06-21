package it.unipv.ingsw.magstudio.model.util;

import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HiveHubAlert {
    private MFXGenericDialog dialogContent;
    private MFXStageDialog dialog;
    private Stage stage;

    public HiveHubAlert(Stage stage){
        this.stage = stage;
    }

    public void errore(String text){
        this.dialogContent = MFXGenericDialogBuilder.build()
                .setShowMinimize(false)
                .setShowAlwaysOnTop(false)
                .get();
        this.dialog = MFXGenericDialogBuilder.build(dialogContent)
                .toStageDialogBuilder()
                .initOwner(stage)
                .initModality(Modality.APPLICATION_MODAL)
                .setDraggable(true)
                .setTitle("Dialogs Preview")
                .setScrimPriority(ScrimPriority.WINDOW)
                .setScrimOwner(true)
                .get();

        dialogContent.setMinSize(400, 100);

        MFXFontIcon errorIcon = new MFXFontIcon("mfx-exclamation-circle-filled", 25);
        dialogContent.setHeaderIcon(errorIcon);
        dialogContent.setHeaderText("Errore - HiveHub");
        Label textErrore = new Label(text);
        textErrore.setStyle("-fx-font-size: 18;");
        dialogContent.setContent(textErrore);
        dialogContent.getStyleClass().add("mfx-error-dialog");
        stage.setOpacity(0.5);
        dialog.showDialog();
        stage.setOpacity(1);
    }

    public void informazione(String text){
        this.dialogContent = MFXGenericDialogBuilder.build()
                .setShowMinimize(false)
                .setShowAlwaysOnTop(false)
                .get();
        this.dialog = MFXGenericDialogBuilder.build(dialogContent)
                .toStageDialogBuilder()
                .initOwner(stage)
                .initModality(Modality.APPLICATION_MODAL)
                .setDraggable(true)
                .setTitle("Dialogs Preview")
                .setScrimPriority(ScrimPriority.WINDOW)
                .setScrimOwner(true)
                .get();

        dialogContent.setMinSize(400, 100);

        MFXFontIcon errorIcon = new MFXFontIcon("mfx-info-circle-filled", 18);
        dialogContent.setHeaderIcon(errorIcon);
        dialogContent.setHeaderText("Informazione - HiveHub");
        Label textErrore = new Label(text);
        textErrore.setStyle("-fx-font-size: 18;");
        dialogContent.setContent(textErrore);
        dialogContent.getStyleClass().add("mfx-info-dialog");
        stage.setOpacity(0.5);
        dialog.showDialog();
        stage.setOpacity(1);
    }
}
