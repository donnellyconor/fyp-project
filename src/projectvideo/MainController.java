/*
 * Final Year Project 
 * Name: Conor Donnelly
 * ID: 14145855
 */

package projectvideo;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * Final Year Project 
 * @author Conor Donnelly
 * ID: 14145855
 */
public class MainController implements Initializable {

    // Video Player Tab
    @FXML
    private MediaView mediaView;
    @FXML
    private MediaPlayer mediaPlayer;
    @FXML
    private String filePath;
    @FXML
    private Slider slider;
    @FXML
    private Slider seekSlider;

    private Duration duration;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter
                = new FileChooser.ExtensionFilter("Select your media(*.mp4)", new String[]{"*.mp4"});
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);
        this.filePath = file.toURI().toString();

        if (this.filePath != null) {
            Media media = new Media(this.filePath);
            this.mediaPlayer = new MediaPlayer(media);
            this.mediaView.setMediaPlayer(this.mediaPlayer);

            DoubleProperty width = this.mediaView.fitWidthProperty();
            DoubleProperty hieght = this.mediaView.fitHeightProperty();

            width.bind(Bindings.selectDouble(this.mediaView.sceneProperty(), new String[]{"width"}));
            hieght.bind(Bindings.select(this.mediaView.sceneProperty(), new String[]{"hieght"}));

            this.slider.setValue(this.mediaPlayer.getVolume() * 100.00);
            this.slider.valueProperty().addListener((Observable observable) -> {
                MainController.this.mediaPlayer.setVolume(MainController.this.slider.getValue() / 100.00);
            });
            this.mediaPlayer.currentTimeProperty().addListener((ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) -> {
                MainController.this.seekSlider.setValue(newValue.toSeconds());
            });
            this.seekSlider.setOnMouseClicked((MouseEvent event1) -> {
                MainController.this.mediaPlayer.seek(Duration.seconds(MainController.this.seekSlider.getValue()));
            });

            seekSlider.maxProperty().bind(Bindings.createDoubleBinding(
                    () -> mediaPlayer.getTotalDuration().toSeconds(),
                    mediaPlayer.totalDurationProperty()));

            this.seekSlider.valueProperty().addListener((Observable observable) -> {
                if (seekSlider.isValueChanging()) {
                    // multiply duration by percentage calculated by slider position
                    MainController.this.mediaPlayer.seek(Duration.seconds(MainController.this.seekSlider.getMax() / 100.00));
                }
            });
            this.mediaPlayer.play();
        }
    }

    // Playback function - play, pause etc.        
    @FXML
    private void playVideo(ActionEvent event) {
        mediaPlayer.play();
        mediaPlayer.setRate(1);
    }

    @FXML
    private void pauseVideo(ActionEvent event) {
        mediaPlayer.pause();
    }

    @FXML
    private void stopVideo(ActionEvent event) {
        mediaPlayer.stop();
    }

    @FXML
    private void fastVideo(ActionEvent event) {
        mediaPlayer.setRate(1.5);
    }

    @FXML
    private void fasterVideo(ActionEvent event) {
        mediaPlayer.setRate(2);
    }

    @FXML
    private void slowVideo(ActionEvent event) {
        mediaPlayer.setRate(0.75);
    }

    @FXML
    private void slowerVideo(ActionEvent event) {
        mediaPlayer.setRate(0.5);
    }

    @FXML
    private void exitVideo(ActionEvent event) {
        System.exit(0);
    }

    // Annotation Feature
    @FXML
    private void confirmButtonAction(ActionEvent event) {
        System.out.println("\nEvent was " + eventCombobox.getValue()
                + " and Player was  " + playerCombobox.getValue()
                + " at time  " + this.seekSlider.getValue());

        Players newAnnotation = new Players(playerCombobox.getValue(),
                eventCombobox.getValue(),
                this.seekSlider.getValue());

        tableView.getItems().add(newAnnotation);
        mediaPlayer.play();
    }

    // Player ComboBox
    @FXML
    public ComboBox<String> playerCombobox;
    ObservableList<String> selectPlayerList
            = FXCollections.observableArrayList("D. de Gea", "A. Valencia",
                    "E. Bailey", "C. Smalling", "A. Young", "S. McTominay",
                    "N. Matic", "J. Mata", "A. Sanchez", "M.Rashford",
                    "R.Lukaku", "");

    // Event ComboBox
    @FXML
    public ComboBox<String> eventCombobox;
    ObservableList<String> selectEventList
            = FXCollections.observableArrayList("Goal", "Assist", "Shot On Target",
                    "Shot Off Target", "Completed Pass", "Incompleted Pass",
                    "Tackle Won", "Tackle Lost", "Take On Won", "Take On Lost",
                    "1v1 Defending Won", "1v1 Defending Lost", "Areial Dual Won",
                    "Areial Dual Lost", "Interception", "Clearance",
                    "Shot Blocked", "Recovery", "Foul Committed",
                    "Goalkeeper Save");

    //ComboBoxes for filtering players tableView *Search function not working* 
    @FXML
    public ComboBox<String> playerTableCombobox;
    ObservableList<String> selectPlayerTableList
            = FXCollections.observableArrayList("D. de Gea", "A. Valencia",
                    "E. Bailey", "C. Smalling", "A. Young", "S. McTominay",
                    "N. Matic", "J. Mata", "A. Sanchez", "M.Rashford",
                    "R.Lukaku", "");
    ObservableList<Players> data = FXCollections.observableArrayList();

    //ComboBox for filtering events tableView *Search function not working* 
    @FXML
    public ComboBox<String> eventTableCombobox;
    ObservableList<String> selectEventTableList
            = FXCollections.observableArrayList("Goal", "Assist", "Shot On Target",
                    "Shot Off Target", "Completed Pass", "Incompleted Pass",
                    "Tackle Won", "Tackle Lost", "Take On Won", "Take On Lost",
                    "1v1 Defending Won", "1v1 Defending Lost", "Areial Dual Won",
                    "Areial Dual Lost", "Interception", "Clearance", "Shot Blocked",
                    "Recovery", "Foul Committed", "Goalkeeper Save");

    

    public ObservableList<Players> getPlayers() {
        ObservableList<Players> players = FXCollections.observableArrayList();
        players.add(new Players("", "Match Started", 0.00));

        return players;
    }
    /*
     * Analysis Tab
     */

    @FXML
    private TabPane tabPane;
    @FXML
    private Tab videoAnalysisTab;
    @FXML
    private Tab videoPlayerTab;
    @FXML
    private Tab egvTab;

    @FXML
    private TableView<Players> tableView;
    @FXML
    private TableColumn<Players, String> columnName;
    @FXML
    private TableColumn<Players, String> columnEvent;
    @FXML
    private TableColumn<Players, Double> columnTime;

    // Viewing the selected event
    @FXML
    public void viewSelectedEventButton() {
        Players player = tableView.getSelectionModel().getSelectedItem();
        tabPane.getSelectionModel().select(videoPlayerTab);
        Double start = player.getTime() - 4.00;
        Double stop = player.getTime() + 4.00;
        mediaPlayer.setStartTime(Duration.seconds(start));
        mediaPlayer.setStopTime(Duration.seconds(stop));
        mediaPlayer.play();
    }
    
    //
    @FXML
    private void reset(){
        mediaPlayer.setStartTime(Duration.seconds(0));
        mediaPlayer.setStopTime(Duration.seconds(100000));
        mediaPlayer.play();
    }

    /*
     * Expected Goal Value Tab
     */
    @FXML
    private TextField distance;
    
    @FXML
    private TextField angle;
    
    @FXML
    private TextField rating;
    
    // Calculation of EGV
    @FXML 
    private Label label;
    
    @FXML
    private void egvButton(){
        String dist = distance.getText();
        double d = Double.parseDouble(dist);				
        
        String ang = angle.getText();
        double a = Double.parseDouble(ang);				
        
        String rate = rating.getText();
        double r = Double.parseDouble(rate);			
        
        double maxd = 20; // maximum distance
        double mind = 0;  // minimum distance
        double maxa = 90; // maximum angle
        double mina = 0;  // minimum angle
        double maxr = 100;// maximum rating
        double minr = 0;  // minimum rating
        
        double egv = (((d-mind)/(maxd-mind))+
                ((a - mina)/(maxa - mina))+
                ((r - minr)/(maxr - minr)))/3;
        
        label.setText("EGV is " + egv); 
        
        XYChart.Series series = new XYChart.Series();
        for(int i=1; i<2; i++){
           series.getData().add(new XYChart.Data(Integer.toString(i),egv));
        }
           EPVChart.getData().addAll(series);
    };
      
    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;

    @FXML
    private LineChart<?, ?> EPVChart;
    // View EGV, transfers user to EGV Tab

    @FXML
    public void viewEGVButton() {
        Players player = tableView.getSelectionModel().getSelectedItem();
        tabPane.getSelectionModel().select(egvTab);
        mediaPlayer.play();
    }

    /*
     * Second MediaPlayer in EGV Window
     * Unfortunatly unable to play video on the second media player
     */
    @FXML
    private MediaView mediaView1;
    @FXML
    private MediaPlayer mediaPlayer1;

    @FXML
    private void playVideo1(ActionEvent event) {
        mediaPlayer1.play();
        mediaPlayer1.setRate(1);
    }

    @FXML
    private void pauseVideo1(ActionEvent event) {
        mediaPlayer1.pause();
    }

    @FXML
    private void stopVideo1(ActionEvent event) {
        mediaPlayer1.stop();
    }

    @FXML
    private void fastVideo1(ActionEvent event) {
        mediaPlayer1.setRate(1.5);
    }

    @FXML
    private void fasterVideo1(ActionEvent event) {
        mediaPlayer1.setRate(2);
    }

    @FXML
    private void slowVideo1(ActionEvent event) {
        mediaPlayer1.setRate(0.75);
    }

    @FXML
    private void slowerVideo1(ActionEvent event) {
        mediaPlayer1.setRate(0.5);
    }

    @FXML
    private void exitVideo1(ActionEvent event) {
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ComboBoxes
        playerCombobox.setItems(selectPlayerList);
        eventCombobox.setItems(selectEventList);

        playerTableCombobox.setItems(selectPlayerTableList);
        eventTableCombobox.setItems(selectEventTableList);

        // TableView
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnEvent.setCellValueFactory(new PropertyValueFactory<>("event"));
        columnTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        tableView.setItems(getPlayers());
        tableView.setEditable(true);
    }
}
