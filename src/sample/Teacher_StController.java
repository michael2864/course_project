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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Teacher_StController {

    @FXML
    private ResourceBundle resources;

    private Teacher teacher;

    @FXML
    private URL location;

    @FXML
    private AnchorPane stud_label;

    @FXML
    private ListView<String> listView;

    @FXML
    private TextArea text_area;

    @FXML
    private Button show_st;

    @FXML
    private Button exitButton;


    @FXML
    private Button write;


    @FXML
    private Button controlButton;

    @FXML
    private Label label;

    @FXML
    private Button q_button;


    @FXML
    private Button delete;

    @FXML
    private Button add_st;

    @FXML
    private TextField log_field;

    @FXML
    private TextField pas_fieald;

    @FXML
    void initialize() {

    }
    String pathThemes="E:\\paths\\themes.txt";
    String pathTeachers="E:\\paths\\teachers.txt";
    String pathQuestions="E:\\paths\\questions.txt";
    String pathLogins="E:\\paths\\logins.txt";
    String pathAnswers="E:\\paths\\answers.txt";

    void copy()
    {
        ArrayList<String> temp = new ArrayList<String>();
        try {
            Files.lines(Paths.get("themes.txt")).forEach(line -> {
                temp.add(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(FileWriter writer = new FileWriter(pathThemes, false))
        {
            // запись всей строки
            for(int j=0;j<temp.size();j++)
            {
                writer.write(temp.get(j)+"\n");
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        ///////////////////////////
        temp.clear();
        try {
            Files.lines(Paths.get("teachers.txt")).forEach(line -> {
                temp.add(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(FileWriter writer = new FileWriter(pathTeachers, false))
        {
            // запись всей строки
            for(int j=0;j<temp.size();j++)
            {
                writer.write(temp.get(j)+"\n");
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        /////////////////////////////////
        temp.clear();
        try {
            Files.lines(Paths.get("questions.txt")).forEach(line -> {
                temp.add(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(FileWriter writer = new FileWriter(pathQuestions, false))
        {
            // запись всей строки
            for(int j=0;j<temp.size();j++)
            {
                writer.write(temp.get(j)+"\n");
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        /////////////////////////////////////////
        temp.clear();
        try {
            Files.lines(Paths.get("logins.txt")).forEach(line -> {
                temp.add(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(FileWriter writer = new FileWriter(pathLogins, false))
        {
            // запись всей строки
            for(int j=0;j<temp.size();j++)
            {
                writer.write(temp.get(j)+"\n");
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        //////////////////////////
        temp.clear();
        try {
            Files.lines(Paths.get("answers.txt")).forEach(line -> {
                temp.add(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(FileWriter writer = new FileWriter(pathAnswers, false))
        {
            // запись всей строки
            for(int j=0;j<temp.size();j++)
            {
                writer.write(temp.get(j)+"\n");
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }


    void initData(Teacher teach) throws IOException {
        //String pathThemes="";
        teacher=new Teacher(teach);
       // label.setText(teacher.getLogin()+"");
        ArrayList<Student> students=new ArrayList<Student>();
        for(int i=0;!teacher.load_student(i).getLogin().equals("-");i++)
        {
            students.add(teacher.load_student(i));
        }
        for(int i=0;i<students.size();i++)
        {
            listView.getItems().addAll(students.get(i).getLogin());
        }
        write.setOnAction(event ->
        {
            copy();
        });
        controlButton.setOnAction(event ->
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/teach_mat.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Teacher_MatController controller = (Teacher_MatController) loader.getController();
            try {
                controller.initData(teacher);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });
        q_button.setOnAction(event ->
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/questions.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            QuestionsController controller = (QuestionsController) loader.getController();
            try {
                controller.initData(teacher);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });
        exitButton.setOnAction(event ->
        {
            exitButton.getScene().getWindow().hide();

        });
        MultipleSelectionModel<String> selectionModel = listView.getSelectionModel();
        selectionModel.selectedItemProperty().addListener((changed, oldValue, newValue) -> {
            try {
                Student s=new Student();
                int i;
                for(i=0;i<students.size();i++)
                {
                    if(students.get(i).getLogin().equals(newValue)) {
                        s.cpySt(students.get(i));
                        s.show(text_area);
                        break;
                    }
                }
                int finalI = i;
                show_st.setOnAction(event ->
                {
                    if(!students.isEmpty()) {
                        s.setPassed(0);
                        s.setMark(0);
                        s.setTimeTest(0);
                        s.changeSt_Con(s.getLogin(), s.getPassword(), s);
                        students.set(finalI, s);
                        s.show(text_area);
                    }
                });
              /*  delete.setOnAction(event ->
                {
                    if(!students.isEmpty()&&listView.getSelectionModel().getSelectedIndex()!=-1) {
                        students.remove(listView.getSelectionModel().getSelectedIndex());
                        try (FileWriter writer = new FileWriter("logins.txt", false)) {
                            // запись всей строки
                            for (int k = 0; k < students.size(); k++) {
                                writer.write(students.get(k) + "\n");
                            }
                        } catch (IOException ex) {

                            System.out.println(ex.getMessage());
                        }
                        listView.getItems().remove(listView.getSelectionModel().getSelectedIndex());
                        text_area.setText("");
                    }
                });*/
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        add_st.setOnAction(event ->
        {
           // listView.getSelectionModel().select(0);
            //if(!students.isEmpty()) {
                boolean exists = false;
                for (int i = 0; i < students.size(); i++) {
                    if (log_field.getText().equals(students.get(i).getLogin())) {
                        exists = true;
                        break;
                    }
                    if (log_field.getText().equals("") || pas_fieald.getText().equals("")) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    try {
                        teacher.addSt(log_field.getText(), pas_fieald.getText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        Student s = new Student(log_field.getText(), pas_fieald.getText(), 0, 0, 0, 0);
                        students.add(s);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    listView.getItems().addAll(log_field.getText());
                    text_area.setText("");
                }
           // listView.getSelectionModel().selectFirst();
            //}
        });
    }
}
