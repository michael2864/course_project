package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class Teacher_MatController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane stud_label;

    @FXML
    private ListView<String> listView;

    @FXML
    private TextArea text_area;

    @FXML
    private Button delete_part;

    @FXML
    private Button delete_theme;

    @FXML
    private Button edit_button;

    @FXML
    private Button right_button;

    @FXML
    private Button left_button;

    @FXML
    private Button add_button;

    @FXML
    private Button exitButton;

    @FXML
    private Label label;

    private Teacher teacher;

    @FXML
    private Button add_theme;

    @FXML
    private TextField theme;

    @FXML
    void initialize() {

    }
    void initData(Teacher teach) throws IOException {
        teacher= new Teacher (teach);
        //label.setText(teacher.getLogin());
        Material material=new Material();
        material.read_from_file();

        for(int i=0;i<material.getThemes();i++)
        {
            listView.getItems().addAll(material.getHeader(i));
        }
        MultipleSelectionModel<String> selectionModel = listView.getSelectionModel();
        selectionModel.selectedItemProperty().addListener((changed, oldValue, newValue) -> {


                    try {
                        if (material.getLinesNum() != 0) {
                            Material theme = new Material();
                            theme.copy_theme(material, newValue);
                            AtomicInteger coun = new AtomicInteger();
                            theme.show_part(text_area, coun.get());

                            left_button.setOnAction(event ->
                            {
                                if (listView.getSelectionModel().getSelectedIndex()!=-1&&material.getLinesNum()!=0)
                                    coun.set(theme.prev_part(text_area, coun.get()));
                            });
                            right_button.setOnAction(event ->
                            {
                                if (listView.getSelectionModel().getSelectedIndex()!=-1&&material.getLinesNum()!=0)
                                    coun.set(theme.next_part(text_area, coun.get()));
                            });
                           /* delete_theme.setOnAction(event ->
                            {
                                if (listView.getSelectionModel().getSelectedIndex()!=-1&&material.getLinesNum()!=0) {

                                    material.deleteTheme(listView.getSelectionModel().getSelectedItem(), true);
                                    theme.deleteTheme(listView.getSelectionModel().getSelectedItem(), false);
                                    listView.getItems().remove(listView.getSelectionModel().getSelectedIndex());
                                    //listView.getSelectionModel().selectFirst();
                                }
                                text_area.setText("");
                                //theme.deleteTheme(listView.getSelectionModel().getSelectedItem(),true);
                            });*/
                            edit_button.setOnAction(event ->
                            {
                                if (listView.getSelectionModel().getSelectedIndex()!=-1&&material.getLinesNum()!=0) {
                                    material.change_mat(theme.getHeader(0), coun.intValue(),
                                            text_area.getText().replace('\n', '@'), true);
                                    theme.change_mat(theme.getHeader(0), coun.intValue(),
                                            text_area.getText().replace('\n', '@'), false);
                                    theme.show_part(text_area, coun.intValue());
                                }
                            });
                            add_button.setOnAction(event ->
                            {
                                if (listView.getSelectionModel().getSelectedIndex()!=-1) {
                                    material.addPart(theme.getHeader(0), theme.getLinesNum(), true);
                                    theme.addPart(theme.getHeader(0), theme.getLinesNum(), false);
                                }
                                //listView.getSelectionModel().select(0);
                            });
                        }
                } catch(IOException e){
                    e.printStackTrace();
                }

        });
        exitButton.setOnAction(event ->
        {
            exitButton.getScene().getWindow().hide();

        });
        add_theme.setOnAction(event ->
        {
            boolean exists=false;
            for(int i=0;i<material.getThemes();i++)
            {
                if(theme.getText().equals(material.getHeader(i)))
                {
                    exists=true;
                    break;
                }
            }
            if(theme.getText().equals(""))
            {
                exists=true;
            }
            if(!exists) {
                material.addPart(theme.getText(), 1, true);
                listView.getItems().addAll(theme.getText());
            }
            else
                text_area.setText("Cannot add theme");
            listView.getSelectionModel().selectFirst();
            //text_area.setText("");
        });
    }
}
