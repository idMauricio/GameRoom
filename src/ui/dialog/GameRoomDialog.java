package ui.dialog;

import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

/**
 * Created by LM on 06/08/2016.
 */
public abstract class GameRoomDialog<T> extends Dialog<T> {
    protected StackPane rootStackPane;
    protected BorderPane mainPane;

    public GameRoomDialog(){
        super();
        DialogPane dialogPane = new DialogPane();
        mainPane = new BorderPane();
        rootStackPane = new StackPane();
        rootStackPane.getChildren().add(mainPane);
        dialogPane.setContent(rootStackPane);
        dialogPane.getStylesheets().add("res/flatterfx.css");
        dialogPane.getStyleClass().add("custom-choice-dialog");
        initStyle(StageStyle.UNDECORATED);
        initModality(Modality.APPLICATION_MODAL);
        setDialogPane(dialogPane);
    }
}
