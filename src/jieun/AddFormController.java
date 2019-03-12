////////////////////////////////////////////////////////////////////////////////////////////
//      Java 2 : Final Project
//      Task : Sheridan Course Inventory - Add New Course Window
////////////////////////////////////////////////////////////////////////////////////////////
//     - current src file : AddFormController.java
//     - current src task : Controller of AddForm
//     - created by : Jieun Kwon
//     - created date : April 11, 2018
//     - modified date : Atril 17, 2018
////////////////////////////////////////////////////////////////////////////////////////////

package jieun;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author JIEUN KWON
 */
public class AddFormController implements Initializable {

    @FXML
    private TextField tfAddId;
    @FXML
    private TextField tfAddTitle; 
    @FXML
    private ComboBox<String> cbAddCategory;
    @FXML
    private ComboBox<Integer> cbAddCredit;
    
    // member vars     
    private Stage stage;                    // remember its stage reference here
    private CourseInventoryModel model;     // model part of MVC
  
    ////////////////////////////////////////////////////////////
    // Initialize
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
    
        // set credit combobox for integer (1 ~10)
        ArrayList<Integer> credit = new ArrayList<Integer>();
        for(int i=1; i <= 10; i++ )
            credit.add(i);
        
        cbAddCredit.setItems(FXCollections.observableArrayList(credit));    // set credit ArrayList to Combo Box
        cbAddCredit.setValue(3);    // default value is 3
        
        // set focus to textfield
        tfAddId.requestFocus();
  
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
        populateCategory();
    }    
    
    // set Categories in ComboBox
    private void populateCategory(){
        cbAddCategory.setItems(FXCollections.observableArrayList(model.getCategoriesBase())); 
    }
    
    /////////////////////////////////////////////////////////////////
    // cancel button
    @FXML
    private void handleButtonCancel(ActionEvent event) {
        if(stage != null)
            stage.close();
    }

    /////////////////////////////////////////////////////////////////
    // save button
    @FXML
    private void handleButtonSave(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.WARNING);
        
        // validation - Id ( ABCE12345) 
        String nid = tfAddId.getText().trim().toUpperCase();
        if(!model.validateId(nid)){
      
            // open Alert Dialog
            alert = new Alert(Alert.AlertType.WARNING, "Course Id must be 4 alphabets followed by 5 digits"); 
            alert.show();
            // set focus to textfield
            tfAddId.requestFocus();
            return; 
        }  
        
        // validation - exist same course id 
        if(model.validateSameId(nid)){
      
            // open Alert Dialog
            alert = new Alert(Alert.AlertType.WARNING, "Course Id already exist. Please enter other Id."); 
            alert.setHeaderText("Entered Course Id: " + nid);
            // set focus to textfield
            tfAddId.requestFocus();
            alert.show();
            return; 
        }          
                
        // validation - Title 
        if(tfAddTitle.getText().trim().isEmpty()){

             // open Alert Dialog
             alert = new Alert(Alert.AlertType.WARNING, "Course Title can't be empty. Please enter Course Title"); 
             alert.show();
             // set focus to textfield
             tfAddTitle.requestFocus();
             return; 
        }    
   
        // validation - Category
        if(cbAddCategory.getValue() == null){
            
             // open Alert Dialog
             alert = new Alert(Alert.AlertType.WARNING, "Please select Category for new course"); 
             // set focus to category
             cbAddCategory.requestFocus();
             alert.show();
             return; 
        }
        
        // Add Course
        // open Alert Dialog to add 
        alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to add new course?");
        alert.setHeaderText("New Course Id: " + nid);
        Optional<ButtonType> result = alert.showAndWait();

        // if user confirmed to save
        if(result.isPresent() && result.get() == ButtonType.OK){ 
                    
            // call method to add
            model.addNewCourse(nid, tfAddTitle.getText(), cbAddCredit.getValue(), cbAddCategory.getValue());
          
        }
        
        if(stage != null)
            stage.close();
    }
    
}
