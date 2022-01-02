package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class QuestionsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane stud_label;

    @FXML
    private TextArea text_area;

    @FXML
    private Button Edit;

    @FXML
    private Button delete;

    @FXML
    private RadioButton choise_1;

    @FXML
    private ToggleGroup Choice;

    @FXML
    private RadioButton choise_2;

    @FXML
    private RadioButton choice_3;

    @FXML
    private Button add;

 //   @FXML
    //private Button right_b;
//
   // @FXML
    //private Button left_b;

    @FXML
    private Button exitButton;

    private Teacher teacher;


    @FXML
    private ListView<String> list;


    @FXML
    void initialize() {

    }
    void initData(Teacher t) throws IOException {
        teacher=new Teacher(t);
        Control control=new Control();
        control.read_q_from_file();
        control.read_a_from_file();
        for(int i=0;i<control.getQ_num();i++)
        {
            list.getItems().addAll(i+1+"");
        }
        exitButton.setOnAction(event ->
        {
            exitButton.getScene().getWindow().hide();

        });
        add.setOnAction(event ->
        {
            control.addQ("Empty&","1&",true);
            list.getItems().addAll(control.getQ_num()+"");
            list.getSelectionModel().selectFirst();
            text_area.setText("");
        });
        MultipleSelectionModel<String> selectionModel = list.getSelectionModel();
        selectionModel.selectedItemProperty().addListener((changed, oldValue, newValue) -> {
            if(control.getQ_num()!=0) {
                AtomicInteger coun = new AtomicInteger();
                //System.out.println(newValue);
                control.show_question(text_area, list.getSelectionModel().getSelectedIndex());
                System.out.println(list.getSelectionModel().getSelectedIndex()+"");
            }
           /* delete.setOnAction(event ->
            {
                if(control.getQ_num()!=0) {
                    int ind=list.getSelectionModel().getSelectedIndex();
                    for(int i=(control.getQ_num()-1);i>=0;i--)
                    {
                        //System.out.println(control.getQ_num()-1+"");
                        list.getItems().remove(i);
                    }
                    control.deleteQ(ind, true);
                    for(int i=0;i<control.getQ_num();i++)
                    {
                        list.getItems().addAll(i+1+"");
                    }
                    if(control.getQ_num()!=0)
                    list.getSelectionModel().selectFirst();
                    text_area.setText("");
                }
            });*/
            Edit.setOnAction(event ->
            {
                if(control.getQ_num()!=0) {
                    if (choise_1.isSelected()) {
                        control.changeQ(Integer.parseInt(newValue), "1",
                                text_area.getText().replace('\n', '@'), true);
                        control.show_question(text_area, Integer.parseInt(newValue) - 1);
                    } else if (choise_2.isSelected()) {
                        control.changeQ(Integer.parseInt(newValue), "2",
                                text_area.getText().replace('\n', '@'), true);
                        control.show_question(text_area, Integer.parseInt(newValue) - 1);
                    } else if (choice_3.isSelected()) {
                        control.changeQ(Integer.parseInt(newValue), "3",
                                text_area.getText().replace('\n', '@'), true);
                        control.show_question(text_area, Integer.parseInt(newValue) - 1);
                    } else {
                        control.changeQ(Integer.parseInt(newValue), "1",
                                text_area.getText().replace('\n', '@'), true);
                        control.show_question(text_area, Integer.parseInt(newValue) - 1);
                    }
                }
            });
        });
    }
}
