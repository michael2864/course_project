package sample;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class Controller {

    @FXML
    private ResourceBundle resources;


    @FXML
    private RadioButton file;

    @FXML
    private URL location;

    @FXML
    private TextField loginField;

    @FXML
    private Button exit;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button enterButton;

    @FXML
    private RadioButton teacher;
    String pathThemes="E:\\paths\\themes.txt";
    String pathTeachers="E:\\paths\\teachers.txt";
    String pathQuestions="E:\\paths\\questions.txt";
    String pathLogins="E:\\paths\\logins.txt";
    String pathAnswers="E:\\paths\\answers.txt";
    void upload()
    {
        ArrayList<String> temp = new ArrayList<String>();
        try {
            Files.lines(Paths.get(pathThemes)).forEach(line -> {
                temp.add(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(FileWriter writer = new FileWriter("themes.txt", false))
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
            Files.lines(Paths.get(pathTeachers)).forEach(line -> {
                temp.add(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(FileWriter writer = new FileWriter("teachers.txt", false))
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
            Files.lines(Paths.get(pathQuestions)).forEach(line -> {
                temp.add(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(FileWriter writer = new FileWriter("questions.txt", false))
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
            Files.lines(Paths.get(pathLogins)).forEach(line -> {
                temp.add(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(FileWriter writer = new FileWriter("logins.txt", false))
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
            Files.lines(Paths.get(pathAnswers)).forEach(line -> {
                temp.add(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(FileWriter writer = new FileWriter("answers.txt", false))
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

    @FXML
    void initialize() {
        exit.setOnAction(event ->
        {
            exit.getScene().getWindow().hide();

        });
        enterButton.setOnAction(event->
        {
            //System.out.print("Enter button pressed");
            if(!teacher.isSelected()) {
                try {
                    Student student = new Student();
                    if (student.load_data(loginField.getText().trim(), passwordField.getText().trim()) == true) {
                       // enterButton.getScene().getWindow().hide();

                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/sample/st_material.fxml"));
                        try {
                            loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        St_Material controller = (St_Material) loader.getController();
                        controller.initData(student);
                        Parent root = loader.getRoot();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.showAndWait();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                try {
                    Teacher teacher = new Teacher();
                    if (teacher.load_data(loginField.getText().trim(), passwordField.getText().trim()) == true) {
                      //  enterButton.getScene().getWindow().hide();
                        if(file.isSelected())
                            upload();
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/sample/teacher_st.fxml"));
                        try {
                            loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Teacher_StController controller = (Teacher_StController) loader.getController();
                        controller.initData(teacher);
                        Parent root = loader.getRoot();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.showAndWait();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }
}

