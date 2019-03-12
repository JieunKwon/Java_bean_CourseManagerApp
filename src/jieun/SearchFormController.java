////////////////////////////////////////////////////////////////////////////////////////////
//      Java 2 : Final Project
//      Task : Sheridan Course Inventory - Search Course Window
////////////////////////////////////////////////////////////////////////////////////////////
//     - current src file : SearchFormController.java
//     - current src task : Controller of SearchForm
//     - created by : Jieun Kwon
//     - created date : April 11, 2018
//     - modified date :  April 17, 2018
////////////////////////////////////////////////////////////////////////////////////////////

package jieun;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle; 
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable; 
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField; 
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author JIEUN KWON
 */
public class SearchFormController implements Initializable {

    // FXML
    @FXML
    private RadioButton rbID;
    @FXML
    private ToggleGroup rbGroup;
    @FXML
    private RadioButton rbTitle;
    @FXML
    private TextField tfKeyword;
    @FXML
    private ListView<String> lvCourseRst;
    @FXML
    private Label lblMessage;
    
    // member vars
    private Stage stage;                    // remember its stage reference here
    private CourseInventoryModel model;     // model part of MVC
    private String srhMode;                 // ID or Title for searching option 

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // set focus to keyword textfield
        tfKeyword.requestFocus();
    
        // id search by default
        srhMode = "id";
        
        // set user data to a control
        rbID.setUserData("id");
        rbTitle.setUserData("title");
        
        // ChangeListener to Search combo box  : id or title
        rbGroup.selectedToggleProperty().addListener((ov, oldValue, newValue) ->
            {
                srhMode = (String)newValue.getUserData();
            }
        );
  
    }    

    /////////////////////////////////////////////////////////////////
    // set Stage
    public void setStage(Stage stage){
        this.stage = stage;
    }

    /////////////////////////////////////////////////////////////////
    // set Model
    public void setModel(CourseInventoryModel model){
        this.model = model;
    }
     
    /////////////////////////////////////////////////////////////////  
    // Event Handler - Cancel Button
    @FXML
    private void handleButtonCancel(ActionEvent event) {
        
        model.setSelectedCourseId(null);       // delete previous value of selectedCourseId
        lvCourseRst.getItems().clear();
        if(stage != null)
            stage.close();
    }

    /////////////////////////////////////////////////////////////////  
    // Event Handler - Select Button
    @FXML
    private void handleButtonSelect(ActionEvent event) {
        
        // selected info
        String crs = lvCourseRst.getSelectionModel().getSelectedItem();
      
        // does not select any course
        if(crs == null){
            
           // add Lable Text with warning
           lblMessage.setText("Please select one course to move");
        
        // move to main window
        }else{
            
            // get only course id from listview by ":" 
            crs = crs.substring(0, crs.indexOf(":"));
 
            // call method to set selectedCourseId
            model.setSelectedCourseId(crs);

            // close stage
            stage.close();
        }
    }

    /////////////////////////////////////////////////////////
    // Handle Search Button Event : set Course information into ListView
    @FXML
    private void handleButtonSearch(ActionEvent event) {
      
        // keyword from textfield
        String key = tfKeyword.getText();
        
        // Clear the previous List
        ObservableList<String> items = lvCourseRst.getItems();
        items.clear();
        
        // no keyword
        if(key == null || key.length() == 0)
            return;
         
        // get result ArrayList from model
        ArrayList<String> listCourseRst = model.getCourseSearch(srhMode, key);

        // convert ArrayList to observableList and set items in ListView
        lvCourseRst.setItems(FXCollections.observableArrayList(listCourseRst));

        // set Lable Text to print search result
        lblMessage.setText(listCourseRst.size() + " courses searched");
    
    }
    
}
