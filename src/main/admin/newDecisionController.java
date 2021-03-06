package main.admin;

import Class_folder.Decisions_repo;
import Class_folder.Discussion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;
import java.util.ResourceBundle;

public class newDecisionController implements Initializable {
    @FXML
    private Button fileUploadButton;
    @FXML
    private Text textFile;
    @FXML
    private long fileSize;
    @FXML
    private TextField decTitle;
    @FXML
    private CheckBox cbT;
    @FXML
    private CheckBox cbS;
    @FXML
    private CheckBox cbP;
    @FXML
    private ComboBox<String> cmbPrior;

    boolean newFileName = false;
    public MenuItem menu_id;
    public SplitMenuButton menuB_id;
    int pr; //priority
    Decisions_repo new_dec = new Decisions_repo();

    ObservableList<String> priorityList = FXCollections.observableArrayList("Υψηλή", "Κανονική");

    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbPrior.setItems(priorityList);
    }

    public void onFileButton(javafx.event.ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);

        if(selectedFile != null){
            textFile.setText(selectedFile.getName());
            fileSize = selectedFile.length();
            newFileName = true;
        }
    }

    @FXML
    public void forwordingDec(javafx.event.ActionEvent actionEvent) throws IOException {
        if(decTitle.getText().equals("") || !newFileName || decTitle.getText().equals("") || cmbPrior.getSelectionModel() == null || ((!(cbT.isSelected()))&&(!(cbS.isSelected()))&&(!(cbP.isSelected())))){ //check for empty fields
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Μη συμπληρωμένα πεδία");
            alert.setContentText("Παρακαλώ συμπληρώστε όλα τα πεδία.");

            alert.showAndWait();
        }
        else{
            Alert alertQ = new Alert(Alert.AlertType.WARNING);
            alertQ.setTitle("Warning Dialog");
            alertQ.setHeaderText("Ερώτηση");
            alertQ.setContentText("Θέλετε σίγουρα να προωθήσετε τη νέα απόφαση;");

            ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alertQ.getDialogPane().getButtonTypes().add(cancel);

            Optional<ButtonType> result = alertQ.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                String decisionTitle = decTitle.getText();
                String fileName = textFile.getText();
                String priority = cmbPrior.getSelectionModel().getSelectedItem().toString();
                boolean teachersb = cbT.isSelected();
                boolean studentsb = cbS.isSelected();
                boolean parentsb = cbP.isSelected();
                String teachers;
                String students;
                String parents;
                LocalDate date = LocalDate.now();

                if (priority.equals("Υψηλή")) {
                    pr = 1;
                } else {
                    pr = 0;
                }

                if (teachersb) {
                    teachers = "ΝΑΙ";
                } else {
                    teachers = "OXI";
                }

                if (studentsb) {
                    students = "ΝΑΙ";
                } else {
                    students = "OXI";
                }

                if (parentsb) {
                    parents = "ΝΑΙ";
                } else {
                    parents = "OXI";
                }

                new_dec = new Decisions_repo("newdec", decisionTitle, fileName, fileSize, pr, teachers, students, parents, date);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Επιτυχία");
                alert.setHeaderText("Επιτυχής προώθηση");
                alert.setContentText("Η απόφαση προωθήθηκε με επιτυχία.");

                alert.showAndWait();

                Node node = (Node) actionEvent.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                Scene scene = stage.getScene();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("all_decisions.fxml"));
                Parent root = (Parent) fxmlLoader.load();

                AllDecisionsController pd = fxmlLoader.getController();

                pd.refreshList(new_dec);

                scene.setRoot(root);
            }
        }
    }

    public Decisions_repo getDec() {
        return new_dec;
    }

    @FXML
    private void click_allClasses(javafx.event.ActionEvent actionEvent) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) menuB_id.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("export_data.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }

    public void click_communication(javafx.event.ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = stage.getScene();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("recent_messages.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        scene.setRoot(root);
    }

    public void click_calendar(javafx.event.ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = stage.getScene();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("calendar.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        scene.setRoot(root);
    }

    public void click_Timetable(javafx.event.ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = stage.getScene();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("timetable.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        scene.setRoot(root);
    }

    public void click_decisions(javafx.event.ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = stage.getScene();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("all_decisions.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        scene.setRoot(root);
    }
    public void click_Votings(javafx.event.ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = stage.getScene();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("all_Votes.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        scene.setRoot(root);
    }
}
