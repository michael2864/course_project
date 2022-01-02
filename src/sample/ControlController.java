package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class ControlController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane stud_label;

    @FXML
    private TextArea text_area;

   /* @FXML
    private Button materialButton;*/

    @FXML
    private Button right_button;

    @FXML
    private RadioButton choise_1;

    @FXML
    private RadioButton choise_2;

    @FXML
    private RadioButton choice_3;

    @FXML
    private Button exitButton;

    private Student student;

    @FXML
    void initialize() throws IOException {

    }
    void initData(Student st) throws IOException {
        long start = System.currentTimeMillis();
        Control control=new Control();
        control.read_q_from_file();
        control.read_a_from_file();
        student = new Student(st);

        AtomicInteger coun= new AtomicInteger();
        if(control.getQ_num()!=0)
            control.show_question(text_area, coun.get());///////////////////
        else {
            text_area.setText("Your mark is:0");
            coun.set(-1);
        }
        //control.estimate(coun.get(),choise_1,choise_2,choice_3);
        // coun.getAndIncrement();
        right_button.setOnAction(event ->
        {
            if(coun.get()>-1&&coun.intValue()!=control.getQ_num()-1) {
                    control.estimate(coun.get(), choise_1, choise_2, choice_3);
                    control.show_question(text_area, coun.get()+1);
                    coun.getAndIncrement();
                }
                else if(coun.get()>-1&&coun.intValue()==control.getQ_num()-1)
                {
                    control.estimate(coun.get(), choise_1, choise_2, choice_3);
                    text_area.setText("Your mark is:" + control.getMark());
                    coun.set(-1);
                }



        });
        exitButton.setOnAction(event1 ->
        {
            try {
                Student stud = new Student("log","pas",0,System.currentTimeMillis()-start,control.getMark(),1);
                stud.changeSt_Con(student.getLogin(),student.getPassword(),stud);
            } catch (IOException e) {
                e.printStackTrace();
            }
            exitButton.getScene().getWindow().hide();
        });
    }
}
