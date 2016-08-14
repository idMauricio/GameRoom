package ui.dialog.test;

import com.mashape.unirest.http.exceptions.UnirestException;
import data.http.key.KeyChecker;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONObject;
import ui.Main;
import ui.dialog.ActivationKeyDialog;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static ui.Main.*;

/**
 * Created by LM on 05/08/2016.
 */
public class ActivationKeyDialogTest extends Application {
    private static StackPane contentPane;
    private static Scene scene;


    public static void initScene() {
        //test key : 57a49ece72e10
        Button launchButton = new Button("Launch");
        ActivationKeyDialog dialog = new ActivationKeyDialog();

        launchButton.setOnAction(e -> {
                    Optional<ButtonType> result = dialog.showAndWait();
                    result.ifPresent(letter -> {
                        if(letter.getText().contains(Main.RESSOURCE_BUNDLE.getString("donation_key_buy_one"))){
                            try {
                                Desktop.getDesktop().browse(new URI("https://gameroom.me/downloads/donation-key"));
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            } catch (URISyntaxException e1) {
                                e1.printStackTrace();
                            }
                            //TODO open gameroom.me at donation key url
                        }else if(letter.getText().equals(Main.RESSOURCE_BUNDLE.getString("activate"))){
                            try {
                                JSONObject response = KeyChecker.activateKey(dialog.getDonationKey());
                                String message = Main.RESSOURCE_BUNDLE.getString(response.getString(KeyChecker.FIELD_MESSAGE).replace(' ','_'));

                                switch (response.getString(KeyChecker.FIELD_RESULT)){
                                    case KeyChecker.RESULT_SUCCESS:
                                        Alert successDialog = new Alert(Alert.AlertType.INFORMATION,message);
                                        successDialog.getDialogPane().getStylesheets().add("res/flatterfx.css");
                                        successDialog.getDialogPane().getStyleClass().add("custom-choice-dialog");
                                        successDialog.initStyle(StageStyle.UNDECORATED);
                                        successDialog.setHeaderText(null);
                                        successDialog.showAndWait();
                                        //TODO write key in settings file and change settings scene
                                        break;
                                        /*switch (response.getString(KeyChecker.FIELD_STATUS)){
                                            case KeyChecker.STATUS_ACTIVE:
                                                Alert successDialog = new Alert(Alert.AlertType.INFORMATION,message);
                                                successDialog.getDialogPane().getStylesheets().add("res/flatterfx.css");
                                                successDialog.getDialogPane().getStyleClass().add("custom-choice-dialog");

                                                successDialog.setHeaderText(null);
                                                successDialog.showAndWait();
                                                //TODO write key in settings file and change settings scene
                                                break;
                                            case KeyChecker.STATUS_BLOCKED:
                                            case KeyChecker.STATUS_EXPIRED:
                                            default:
                                                Alert warningDialog = new Alert(Alert.AlertType.WARNING,message);
                                                warningDialog.getDialogPane().getStylesheets().add("res/flatterfx.css");
                                                warningDialog.getDialogPane().getStyleClass().add("custom-choice-dialog");
                                                warningDialog.setHeaderText(null);
                                                warningDialog.showAndWait();
                                                break;

                                        }
                                        break;*/
                                    case KeyChecker.RESULT_ERROR:
                                        Alert errorDialog = new Alert(Alert.AlertType.ERROR,message);
                                        errorDialog.getDialogPane().getStylesheets().add("res/flatterfx.css");
                                        errorDialog.getDialogPane().getStyleClass().add("custom-choice-dialog");
                                        errorDialog.initStyle(StageStyle.UNDECORATED);
                                        errorDialog.setHeaderText(null);
                                        errorDialog.showAndWait();
                                        break;
                                    default:
                                        break;
                                }
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            } catch (UnirestException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
                }
        );
        contentPane.getChildren().addAll(launchButton);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        contentPane = new StackPane();
        scene = new Scene(contentPane, SCREEN_WIDTH, SCREEN_HEIGHT);
        initScene();
        primaryStage.setTitle("UITest");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        SCREEN_WIDTH = (int) 1920;
        SCREEN_HEIGHT = (int) 1080;
        RESSOURCE_BUNDLE = ResourceBundle.getBundle("strings", Locale.forLanguageTag("fr_FR"));
        launch(args);
    }
}