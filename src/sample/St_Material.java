package sample;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class St_Material {

    @FXML
    private ResourceBundle resources;

    @FXML
    private Label time;

    @FXML
    private URL location;

    @FXML
    private ListView<String> listView;

    @FXML
    private Button chooseButton;

    @FXML
    private Button exitButton;

    @FXML
    private TextArea text_area;

    @FXML
    private Button show_st;

    @FXML
    private Button right_button;

    @FXML
    private Button left_button;

    private Student student;

    @FXML
    private Button controlButton;

    //private long time;

    @FXML
    void initialize() throws IOException {
        //student=new Student();

        //////////////////////////

    }
    void initData(Student st) throws IOException {
        long start = System.currentTimeMillis();
        //Form objects of Student and Material
        //AtomicBoolean control = new AtomicBoolean(false);
        student = new Student(st);
        Material material=new Material();
        material.read_from_file();


        for(int i=0;i<material.getThemes();i++)
        {
            listView.getItems().addAll(material.getHeader(i));
        }

        //////////////////////////

            controlButton.setOnAction(event ->//if control pressed
            {
                //controlButton.getScene().getWindow().hide();
                if(student.getPas()==0) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/sample/control.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ControlController controller = (ControlController) loader.getController();
                    try {
                        controller.initData(student);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.showAndWait();
                    //controlButton.getScene().getWindow().hide();
                    Student stud = null;
                   /* try {///////////////////////////dodelat
                        stud = new Student(student.getLogin(), student.getPassword(),
                                System.currentTimeMillis() - start + student.getTime(),
                                student.getTimeTest(), student.getMark(), student.getPas());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                    try {
                        student.load_data(student.getLogin(),student.getPassword());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //student.changeSt_Mat(student.getLogin(), student.getPassword(), stud);
                    //controlButton.getScene().getWindow().hide();
                }
                else
                {
                    text_area.setText("You have already passed the exam");
                }
            });

            exitButton.setOnAction(event ->
            {
                Student stud= null;
                try {
                    stud = new Student(student.getLogin(),student.getPassword(),
                            System.currentTimeMillis()-start+student.getTime(),
                            student.getTimeTest(),student.getMark(),student.getPas());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                student.changeSt_Mat(student.getLogin(),student.getPassword(),stud);
                exitButton.getScene().getWindow().hide();

            });
            show_st.setOnAction(event ->
            {
                student.show(text_area);
            });
            MultipleSelectionModel<String> selectionModel = listView.getSelectionModel();
            selectionModel.selectedItemProperty().addListener((changed, oldValue, newValue) -> {
                try {
                    Material theme = new Material();
                    theme.copy_theme(material, newValue);
                    AtomicInteger coun = new AtomicInteger();
                    theme.show_part(text_area, coun.get());
                    left_button.setOnAction(event ->
                    {
                        coun.set(theme.prev_part(text_area, coun.get()));
                    });
                    right_button.setOnAction(event ->
                    {
                        coun.set(theme.next_part(text_area, coun.get()));
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
    }
}
