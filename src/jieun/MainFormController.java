////////////////////////////////////////////////////////////////////////////////////////////
//      Java 2 : Final Project
//      Task : Sheridan Course Inventory
////////////////////////////////////////////////////////////////////////////////////////////
//     - current src file : MainFormController.java
//     - current src task : Controller of MainForm
//     - created by : Jieun Kwon
//     - created date : March 25, 2018
//     - modified date : April 3, 2018

//      - updated : Alert Dialog for window close button and Exit menu 
//      - updated : find Course Information and set into information pane
//      - updated : Delete course information
//      - updated : Edit course information
//      - updated : Control disabled menus, buttons and information pane
////////////////////////////////////////////////////////////////////////////////////////////

package jieun;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections; 
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label; 
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage; 

/**
 *
 * @author JIEUN KWON
 */
public class MainFormController implements Initializable {
    
    // member vars
    private Stage stage;                    // remember its stage reference here
    private File file;                      // file  
    private CourseInventoryModel model;     // model part of MVC
    
    // FXML
    @FXML
    private Label lblMessage;
    @FXML
    private ListView<String> listCourse;  
    @FXML
    private ComboBox<String> cboxCategory;
    @FXML
    private AnchorPane paneCourseView;
    @FXML
    private TextField tfTitleInfo;
    @FXML
    private TextField tfCreditInfo;
    @FXML
    private ComboBox<String> cbCategoryInfo;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private MenuItem menuDelete;
    @FXML
    private MenuItem menuAdd;
    @FXML
    private MenuItem menuSearch;
    @FXML
    private MenuItem menuEdit;
    @FXML
    private Label lblResult;
     
    /////////////////////////////////////////////////////////////////
    // initialize
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // create model
        model = new CourseInventoryModel();     
 
        // make some nodes disable
        ctlMenuDisable("init");
   
        // populate categoies  
        cboxCategory.setItems(FXCollections.observableArrayList(model.getCategories())); 
          
        // make combo box - select "All Categories" by default 
        cboxCategory.setValue("All Categories");
            
        // open default file : courses.dat 
        file = new File("courses.dat");
        
        // call method to read file
        model.readCourseFile(file);
        
        // call method to set Course IDs in ListView by default 
        if(model.getCourseCount() > 0){ 
            populateCourseIds("All Categories"); 
            
        // No data - listview, label make disable
        }else{
            ctlMenuDisable("nodata");
        }
        
        // ChangeListener to CourseIDs ListView
        listCourse.getSelectionModel().selectedIndexProperty().addListener((ov, oldValue, newValue) ->
            { 
                // call method to populate course info into right pane
                populateCourseInfo();
            }
        );
                
    }    
    
    /////////////////////////////////////////////////////////////////
    // set Stage to remember reference for sub window or dialog
    public void setStage(Stage stage){ 
        
        this.stage = stage;
         
        // event handler using lambda expression 
        stage.setOnCloseRequest(e -> { 
           
            // confirm from user before closing 
            Alert alert = new Alert(AlertType.CONFIRMATION, "Course Inventory file saved automatically.");
            alert.setHeaderText("Are you sure you want to leave?");
            Optional<ButtonType> result = alert.showAndWait();
           
            // if ok, close
            if(result.isPresent() && result.get() == ButtonType.OK)
                Platform.exit();    // terminate program
            else
                e.consume();
        });
                
    }

    /////////////////////////////////////////////////////////////////
    // Method populateCourseIds : set Course Ids into ListView 
    // default : All categories
    // searched by category name
    private void populateCourseIds(String category){
     
            ArrayList<String> ids;
            
            // select IDs by all categories
            if(category == null || category.equals("All Categories")){       

                // get ids from model without param
                ids = model.getCourseIds();
 
            // select IDs by category
            }else{
            
                // get ids from model wieh category param
                ids = model.getCourseIds(category);

            }
            
            // search course count
            if(ids.size() > 0){
            
                // set Lable Text to print search result
                lblMessage.setText( ids.size() + " Courses");

                // convert ArrayList to observableList and set items in ListView
                listCourse.setItems(FXCollections.observableArrayList(ids));
 
                // ctlMenuInit()
                ctlMenuDisable("init");         // make node able to use
                
            // no data
            }else{ 
                lblMessage.setText("No Courses");   // message
                listCourse.getItems().clear();      // make listview clear
                ctlMenuDisable("nodata");           // all node block
            }
    }
   
    /////////////////////////////////////////////////////////////////
    // Method populateCourseInfo : show the course information on the right pane 
    private void populateCourseInfo()
    {  
        // selected course Id
        String courseId = listCourse.getSelectionModel().getSelectedItem();
       
        if(courseId != null){
            
            // find Course Information 
            Course courseInfo = model.getCourseInfo(courseId);

            // set textField value - title
            tfTitleInfo.setText(courseInfo.getTitle());

            // set textField value - Credit
            tfCreditInfo.setText(Integer.toString(courseInfo.getCredit()));

            // populate categoies 
            cbCategoryInfo.setItems(FXCollections.observableArrayList(model.getCategoriesBase())); 

            // select ComboBox value as this course's category
            cbCategoryInfo.setValue(courseInfo.getCategory());

            // Result message 
            lblResult.setText(" [" + courseId + "] was selected");
       
            // button and menu control to edit mode 
            btnDelete.setDisable(false);             // Delete button
            btnEdit.setDisable(false);             // Delete button
            menuEdit.setDisable(false);            // Edit Menu 
            menuDelete.setDisable(false);            // Delete Menu
        
        }else{
            
            // Result message 
            lblResult.setText("Course was not selected");
            
            // set textField value - title
            tfTitleInfo.setText("");

            // set textField value - Credit
            tfCreditInfo.setText("");

            // select ComboBox value as this course's category
            cbCategoryInfo.setValue("");
            
            // button and menu control to list mode
             ctlMenuDisable("init");
        }
    }
  
    /////////////////////////////////////////////////////////////////
    // ActionEvent : Menu Handle Event when clicked Open...
    @FXML
    private void handleMenuOpen(ActionEvent event) {
        
        // create filechooser dialog
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Course List File...");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"),
                                                 new ExtensionFilter("DAT", "*.dat"),
                                                 new ExtensionFilter("TXT", "*.txt"));
        // show file Dialog
        file = fileChooser.showOpenDialog(stage);
    
        // no file
        if(file == null)
            return;
        
        // read the file using model component
        model.readCourseFile(file);
        
        // rerurn - combo box select "All Categories" by default 
        cboxCategory.setValue("All Categories");
            
        // call method to set ListView by default
        if(model.getCourseCount() > 0){  
            
            populateCourseIds("All Categories"); 
            
            if(listCourse.disableProperty().getValue())     // if disable
                listCourse.setDisable(false);        
            
        // no data 
        }else{
            
            // call method to not open menus
            ctlMenuDisable("nodata");
        }
    }
    
    // when open empty file or no courseid, make nodes disable 
    private void ctlMenuDisable(String mode){
        
        // mode - init : when hava course data or initialize
        if(mode.equalsIgnoreCase("init")){    
            
            // open
            listCourse.setDisable(false);            // course Listview
            menuSearch.setDisable(false);            // Search Menu 
            btnSearch.setDisable(false);             // Search button 
            menuAdd.setDisable(false);               // Add Menu
            btnAdd.setDisable(false);                // Add button
            
            // block
            paneCourseView.setDisable(true);        // Course View Pane
            btnDelete.setDisable(true);             // Delete button
            btnEdit.setDisable(true);               // Delete button
            menuEdit.setDisable(true);              // Search Menu 
            menuDelete.setDisable(true);            // Delete Menu
       
        // mode - nodata : not available status
        }else if(mode.equalsIgnoreCase("nodata")){    
            
            // block
            listCourse.getItems().clear();      // make clear
            listCourse.setDisable(true);        // make disable
            lblMessage.setText("No Courses");   // print no data             
            tfTitleInfo.setText("");            // title text field
            tfCreditInfo.setText("");           // credit text field
            cbCategoryInfo.setValue("");        // category combobox
            paneCourseView.setDisable(true);    // Information Pane
            btnSearch.setDisable(false);         // Search button 
            btnEdit.setDisable(true);           // Edit button
            btnDelete.setDisable(true);         // Delete button            
            menuSearch.setDisable(false);        // Search Menu 
            menuEdit.setDisable(true);          // Edit Menu
            menuDelete.setDisable(true);        // Delete Menu 
            menuAdd.setDisable(false);          // Add Menu
            btnAdd.setDisable(false);           // Add button
     
        // Mode - search : success to Open ListView Course IDs 
        }else if(mode.equalsIgnoreCase("search")){ 
              
            cboxCategory.setDisable(false);           // Main Category Combo Box            
            listCourse.setDisable(false);             // Course ID List            
            paneCourseView.setDisable(true);          // Information Pane
            btnSearch.setDisable(false);             // Search button
            btnAdd.setDisable(false);                // Add button
            btnDelete.setDisable(false);             // Delete button
            menuSearch.setDisable(false);            // Search Menu
            menuAdd.setDisable(false);               // Add Menu
            menuDelete.setDisable(false);            // Delete Menu            
            menuEdit.setDisable(false);             // Edit Menu
            btnEdit.setDisable(false);              // Edit Button
        
        // Mode  - Edit and Delete mode  
        }else if(mode.equalsIgnoreCase("edit")){ 
            
            cboxCategory.setDisable(true);           // Main Category Combo Box            
            listCourse.setDisable(true);             // Course ID List            
            paneCourseView.setDisable(false);       // Information Pane
            btnSearch.setDisable(true);             // Search button
            btnAdd.setDisable(true);                // Add button
            btnDelete.setDisable(true);             // Delete button
            menuSearch.setDisable(true);            // Search Menu
            menuAdd.setDisable(true);               // Add Menu
            menuDelete.setDisable(true);            // Delete Menu            
            menuEdit.setDisable(true);              // Edit Menu            
            btnEdit.setDisable(true);               // Edit button        
        }
    }
    
    /////////////////////////////////////////////////////////////////
    // ActionEvent : Menu Handle Event when clicked Save As..
    @FXML
    private void handleMenuSave(ActionEvent event) {
        
        
        // create fileChooser dialog
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Course List File As...");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"),
                                                 new ExtensionFilter("DAT", "*.dat"),
                                                 new ExtensionFilter("TXT", "*.txt"));
        // show dialog
        file = fileChooser.showSaveDialog(stage);
        if(file == null)
            return;
      
        // save a file
        model.saveCourseFile(file);
        
    }

    /////////////////////////////////////////////////////////////////
    // ActionEvent: Menu Handle Event when Exit clicked 
    @FXML
    private void handleMenuExit(ActionEvent event) { 
        
        // confirm from user before closing 
        Alert alert = new Alert(AlertType.CONFIRMATION, "Course Inventory file saved automatically.");
        alert.setHeaderText("Are you sure you want to leave?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK)
            Platform.exit();    // terminate program
        else
            event.consume();    // ignore close event 
                
    }
    
    /////////////////////////////////////////////////////////////////
    // ActionEvent : selected categories
    @FXML
    private void handleCBoxSelect(ActionEvent event) {
         
        // popolate course Ids with category selected
         populateCourseIds(cboxCategory.getValue());
    }

 
    // ActionEvent : Edit Button clicked
    @FXML
    private void handleButtonEdit(ActionEvent event) {

        // open course info pane - call method to make UI disable or able 
        ctlMenuDisable("edit");

    }

    /////////////////////////////////////////////////////////////////
    // ActionEvent : Delete Button clicked
    @FXML
    private void handleButtonDelete(ActionEvent event) {
        
        // selected Course ID
        String cId = listCourse.getSelectionModel().getSelectedItem();
        
         // open Alert Dialog
        Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to delete?");
        alert.setHeaderText( "Selected Course : " + cId );
        Optional<ButtonType> result = alert.showAndWait();

        // if user confirmed to save
        if(result.isPresent() && result.get() == ButtonType.OK){
            
            // delete ArrayList
            model.delCourseInfo(cId);   

            // reload CourseIds ListView
             populateCourseIds(cboxCategory.getValue());
             
            // Information Pane, make blank
            tfTitleInfo.setText("");
            tfCreditInfo.setText("");  
            cbCategoryInfo.setValue("All Categories");
            
            // Result message 
            lblResult.setText("[" + cId + "] was deleted");
            
            // save file
             model.saveCourseFile(file);
             
        // delete-cancel
        }else{
            // Result message 
            lblResult.setText("[" + cId + "] was canceled");
        }

    }
    
    /////////////////////////////////////////////////////////////////
    // ActionEvent : Search Button 
    @FXML
    private void handleButtonSearch(ActionEvent event) {
        
        // Open Modal window for Search Course
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SearchForm.fxml"));
        
        Parent root;
        try {
            root = (Parent)fxmlLoader.load();
            
            // create scene
            Scene scene = new Scene(root);

            // Stage
            Stage stage = new Stage();

            stage.initModality(Modality.APPLICATION_MODAL);

            // Stage title
            stage.setTitle("Search Course");
            
            // no resizable
            stage.setResizable(false);
            
            // set scene
            stage.setScene(scene);
             
            // remember the stage, so can close it later
            SearchFormController ctrlSearch = fxmlLoader.getController();
            ctrlSearch.setStage(stage);
            ctrlSearch.setModel(model);
            
            // Stage show
            stage.showAndWait();
            
            // user response ---------------------------------------
            String selectedId = model.getSelectedCourseId();
         
            if(selectedId != null){
                
                    // get category of selectedId, and then reset ListView of the category
                   String cate  = model.getCourseInfo(selectedId).getCategory();

                   // category select
                   cboxCategory.setValue(cate);

                   // reset CourseIds in ListView
                   populateCourseIds(cate);

                   // select ListView with selectedId
                   listCourse.getSelectionModel().select(selectedId);

                   // find index of Id, then make a focus and scroll 
                   listCourse.getFocusModel().focus(listCourse.getSelectionModel().getSelectedIndex());
                   listCourse.scrollTo(listCourse.getSelectionModel().getSelectedIndex());
                
            }else{
                   // clear selection  
                   listCourse.getSelectionModel().clearSelection();
            }

        
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    
    }


    /////////////////////////////////////////////////////////////////
    // ActionEvent : Add New Course button clicked
    @FXML
    private void handleButtonAdd(ActionEvent event) {
        
        // Open Modal window for Add Course
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddForm.fxml"));
        
        Parent root;
        try {
            root = (Parent)fxmlLoader.load();
            
            // create scene
            Scene scene = new Scene(root);

            // Stage
            Stage stage = new Stage();

            stage.initModality(Modality.APPLICATION_MODAL);

            // Stage title
            stage.setTitle("Add New Course");
            
            // no resizable
            stage.setResizable(false);
            
            // set scene
            stage.setScene(scene);
             
            // remember the stage, so can close it later
            AddFormController ctrlAdd = fxmlLoader.getController();
            
            ctrlAdd.setStage(stage);
            
            ctrlAdd.setModel(model);
            
            // Stage show
            stage.showAndWait();
            
            // User response --------------------------------- 
            String nId = model.getNewCourseId();    // new courseid added
            
            if(nId == null)  return; 
                
           // get category of selectedId, and then reset ListView of the category
           String cate  = model.getCourseInfo(nId).getCategory();

           // category select
           cboxCategory.setValue(cate);

           // reload CourseIds in ListView
           populateCourseIds(cate);

           // select ListView with new id
           listCourse.getSelectionModel().select(nId);

           // find index of Id, then make a focus and scroll 
           listCourse.getFocusModel().focus(listCourse.getSelectionModel().getSelectedIndex());
           listCourse.scrollTo(listCourse.getSelectionModel().getSelectedIndex());

           // save file added new id
           model.saveCourseFile(file); 
             
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
 
    }

    /////////////////////////////////////////////////////////////////
    // ActionEvent : Save Button clicked
    @FXML
    private void handleButtonSave(ActionEvent event) {
         
        // check null
        if(listCourse.getSelectionModel().getSelectedItem() == null)
            return;
        
        // open Alert Dialog
        Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to save?");
        alert.setHeaderText("Selected Course : " + listCourse.getSelectionModel().getSelectedItem());
        Optional<ButtonType> result = alert.showAndWait();

        // if user confirmed to save
        if(result.isPresent() && result.get() == ButtonType.OK){
            
            // validation - Title 
           if(tfTitleInfo.getText().trim().isEmpty()){
              
                // open Alert Dialog
                alert = new Alert(AlertType.WARNING, "Course title can't be empty. Please enter Course Title"); 
                alert.show();
                tfTitleInfo.requestFocus();
                return; 
                
            // validation - Credit 
            }else if(tfCreditInfo.getText().isEmpty() || !tfCreditInfo.getText().matches("^[0-9]*$")){    
                
                // open Alert Dialog
                alert = new Alert(AlertType.WARNING, "Please enter Course Credit as a positive number");  
                alert.show(); 
                tfCreditInfo.requestFocus();
                return; 
            
            // update new information
            }else { 
                    
                // call method to edit
                model.editCourseInfo(listCourse.getSelectionModel().getSelectedItem(), 
                                    tfTitleInfo.getText(), 
                                    Integer.parseInt(tfCreditInfo.getText()), 
                                    cbCategoryInfo.getValue());

                // save file 
                model.saveCourseFile(file);
                
                // Result message 
                lblResult.setText(" [" + listCourse.getSelectionModel().getSelectedItem() + "] was saved");
                
                // reload CourseIds ListView
                populateCourseIds(cboxCategory.getValue());
                 
                // call menu and button controller to make disable
                ctlMenuDisable("search");
            }
        }             
           
    }
    
    /////////////////////////////////////////////////////////////////
    // ActionEvent : Cancel clicked
    @FXML
    private void handleButtonCancel(ActionEvent event) {
        
        // Result message 
        lblResult.setText(" [" + listCourse.getSelectionModel().getSelectedItem() + "] was cancelled");
        
        // re-select courseId
        listCourse.getSelectionModel().select(listCourse.getSelectionModel().getSelectedItem());
        
        // menu disable to return Course IDs search mode 
         ctlMenuDisable("search");
        
    }
    
    
    /////////////////////////////////////////////////////////////////
    // ActionEvent : About Menu selected
    @FXML
    private void handleMenuAbout(ActionEvent event) {
        
        // Open Modal window for About menu
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AboutForm.fxml"));
        
        Parent root;
        try {
            root = (Parent)fxmlLoader.load();
            
            // create scene
            Scene scene = new Scene(root);

            // create stage
            Stage stage = new Stage();

            stage.initModality(Modality.APPLICATION_MODAL);
            // set scene
            stage.setScene(scene);

            // Stage title
            stage.setTitle("About");

            // no resizable
            stage.setResizable(false);
            
            // Stage show
            stage.show();

            // remember the stage, so can close it later
            AboutFormController ctrlAbout = fxmlLoader.getController();
            ctrlAbout.setStage(stage);
        
        } catch (IOException ex) {
            Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    
}
