package projectvideo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Final Year Project 
 * @author Conor Donnelly
 * ID: 14145855
 */
public class WelcomeScreenController implements Initializable {

    /**
     * @param stage
     * @throws java.lang.Exception
     */
    public void start(Stage stage) throws Exception {
        Parent root = 
                (Parent) FXMLLoader.load(getClass().getResource("WelcomeScreen.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Welcome Screen");
        stage.show();
    }

    public void changeScreenButtonPushed(ActionEvent event) throws IOException, IOException {
        Parent analysisWindowParent = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene analysisWindowScene = new Scene(analysisWindowParent);

        Stage window;
        window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(analysisWindowScene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
