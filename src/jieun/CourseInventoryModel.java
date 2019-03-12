////////////////////////////////////////////////////////////////////////////////////////////
//      Java 2 : Final Project
//      Task : Sheridan Course Inventory
////////////////////////////////////////////////////////////////////////////////////////////
//     - current src file : CourseInventoryModel.java
//     - current src task : CourseInventoryModel class for Model component
//     - created by : Jieun Kwon
//     - created date : March 25, 2018
//     - modified date : April 11, 2018
////////////////////////////////////////////////////////////////////////////////////////////

package jieun;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author JIEUN KWON
 */
public class CourseInventoryModel {
    
    // member vars
    private ArrayList<Course> courses;
    private ArrayList<String> categories;
    private String selectedCourseId = null;        //  selectedCourseId from search window
    private String newCourseId = null;             //  store course id in order to add new course
    
    //------------------------------------------------------------------------------------------
    // ctor with no args
    public CourseInventoryModel() {

            courses = new ArrayList<Course>();   
            categories = new ArrayList<String>(); 
            
            // add categories name into ArrayList
            categories.add("All Categories"); 
            categories.add("DATABASE"); 
            categories.add("INFORMATION");
            categories.add("MATH");
            categories.add("PROGRAMMING");
            categories.add("SYSTEM"); 
    }

    //------------------------------------------------------------------------------------------
    // ctor with args
    public CourseInventoryModel(ArrayList<Course> courses, ArrayList<String> categories) {
        this.courses = courses;
        this.categories = categories;
    }
    
    //------------------------------------------------------------------------------------------
    // setter Categories
    public void setCategories(ArrayList<String> categories){ 
        this.categories = categories; 
    }
    
    //------------------------------------------------------------------------------------------
    // getter for categoies
    public ArrayList<String> getCategories(){
        return categories;
    }
 
    //------------------------------------------------------------------------------------------
    // return categories without "All Categories"
    public ArrayList<String> getCategoriesBase(){
        
        ArrayList<String> baseCate = new ArrayList<String>();
        
        for(int i=1; i<categories.size(); i++)
        {
            baseCate.add(categories.get(i));
        }
        
        return baseCate;
    }
    
    //------------------------------------------------------------------------------------------
    // setter for Courses
    public void setCourses(ArrayList<Course> courses){
        this.courses = courses;
    }
    
    // getter for Courses
    public ArrayList<Course> getCourses(){
        return courses;
    }
   
    //------------------------------------------------------------------------------------------   
    // when user select course id, set the id
    // getter and setter SelectedCouserId
    public void setSelectedCourseId(String cId){
    
        selectedCourseId = cId;
    }
     
     // return selectedCourseId 
     public String getSelectedCourseId(){
         return selectedCourseId;
     }
     
     // ----------------------------------------------------------------------------------------
     // setter and getter NewCourseId for adding new course
     public void setNewCourseId(String cid){
         
         newCourseId = cid;
     }
     
     public String getNewCourseId(){
         
         return newCourseId;
     }
     
     
    //------------------------------------------------------------------------------------------
    // Read file and add ArrayList
    public void readCourseFile(File file){
  
        // local ArrayList for reading lines
        ArrayList<String> lines = new ArrayList<String>();
        // local var for one line
        String line = null;
        
        // read file using BufferedReader
        try(FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr)){
            
            // read line by line
            while((line = br.readLine()) != null)
                // store lines to ArrayList
                lines.add(line);       
                
        }catch(IOException e){
            System.out.println(e.getMessage());
            return;
        }
        
        ///////////////////////////////
        // Set new courses ArrayList  
        // 1. reset current courses 
        courses.clear();
        
        // 2. Split information from new lines
        for(int i = 0; i < lines.size(); ++i){
            line = lines.get(i);                // one line
            String[] tokens = line.split(";");  // split by ";"
            
            // 3. create a course object and add it to the list
            if(tokens.length == 4){
                courses.add(new Course(tokens[0].trim(), 
                                       tokens[1].trim(), 
                                       Integer.parseInt(tokens[2].trim()), 
                                       tokens[3].trim()));
            }
            
        }
        
        // sort order by courseId 
        courses.sort(new Comparator<Course>( ) {
            @Override
            public int compare(Course c1, Course c2) {
                return c1.getId().compareTo(c2.getId());
            }
        });
   
    }
    
    //------------------------------------------------------------------------------------------
    // save DAT file 
    public void saveCourseFile(File file){
         
        // local var for one line
        String line = null;
        
        // write to file
        try{        // FileNotFoundException
            
            // define writer
            PrintWriter output = new PrintWriter(file);

            // make a line from ArrayList courses
            for(int i = 0; i < courses.size(); ++i){
                
                // line feature : courseID ; courseTitle ; credit ; category
                line = courses.get(i).getId() + " ; " 
                     + courses.get(i).getTitle() + " ; " 
                     + courses.get(i).getCredit() + " ; " 
                     + courses.get(i).getCategory() ; 
                
                // write one line to file
                output.println(line); 
            }
           
            // close writer
            output.close();
            
        }catch(FileNotFoundException e){
            System.out.println(e.getMessage()); 
        }
         
    }
    
    //------------------------------------------------------------------------------------------
    // Method getCourseCount : return length of course id 
    public int getCourseCount(){
        
        return courses.size();
    
    }
    
 
    //------------------------------------------------------------------------------------------
    // Method getCourseIds : return ArrayList for course ids
    public ArrayList<String> getCourseIds(){
    
        // create local ArrayList for ids
        ArrayList<String> ids = new ArrayList<String>();
        
        // store ids from courses
        for(int i = 0; i < courses.size(); i++)
            ids.add(courses.get(i).getId());
        
        // return ids ArrayList 
        return ids;
         
    }
    
    //------------------------------------------------------------------------------------------
    // Overloaded Method getCourseIds : return IDs ArrayList by searching to category name
    public ArrayList<String> getCourseIds(String category){
     
        // create local ArrayList for ids
        ArrayList<String> ids = new ArrayList<String>();
        
        // store ids from courses
        for(int i = 0; i < courses.size(); i++){
            
            // valid to equal
            if (courses.get(i).getCategory().equals(category)){
                 // save ids of the same category
                 ids.add(courses.get(i).getId());
            }
               
        }
        
        // return ids ArrayList 
        return ids;
    }
    
    //------------------------------------------------------------------------------------------
    // Method getCourseInfo: return Course matched to Course ID
    public Course getCourseInfo(String courseId){
     
        // create local Course var
        Course crs = new Course();
        
        // find Course from ArrayList
        for(int i = 0; i < courses.size(); i++){
            
            // valid to equal
            if (courses.get(i).getId().equals(courseId)){
                 // save ids of the same category
                 crs.set(courses.get(i).getId(), courses.get(i).getTitle(), courses.get(i).getCredit(),courses.get(i).getCategory());
                 return crs;
            }
               
        }
        
        // return course 
        return crs;
    }
    
    //------------------------------------------------------------------------------------------  
    // Method editCourseInfo: update Course information from Info Pane's values  
    public void editCourseInfo(String cId, String cTitle, int cCredit, String cCategory){
      
        // create local Course var
        Course crs = new Course(cId, cTitle, cCredit, cCategory);
        
        // find Course from ArrayList
        for(int i = 0; i < courses.size(); i++){
            
            // valid to equal
            if (courses.get(i).getId().equals(cId)){
                 
                // update course elements as new information
                 courses.set(i,crs);

                 return; 
            }
               
        }
  
    }
 
    //------------------------------------------------------------------------------------------
    // Method delCourseInfo: delete Course information 
    public void delCourseInfo(String cId){
 
        // find Course from ArrayList
        for(int i = 0; i < courses.size(); i++){
            
            // valid to equal
            if (courses.get(i).getId().equals(cId)){
                 // delete course from ArrayList
                 courses.remove(i);
                 return; 
            }
               
        }
 
    }
    
    //------------------------------------------------------------------------------------------   
    // SearchForm -> Search Course
    // search Course ArrayList and return result 
    public ArrayList<String> getCourseSearch(String mode, String key){
         
        // create local ArrayList for "id : title"
        ArrayList<String> rst = new ArrayList<String>();
        
        // null check
        if (key == null || key.isEmpty())
            return rst;
        
        // var for result
        String crs = "";
        key = key.toUpperCase();
        
        // search with ID
        if(mode.equalsIgnoreCase("id")){
            
            // store ids from courses
            for(int i = 0; i < courses.size(); i++){

                // string search after converting uppercase
                crs = courses.get(i).getId().toUpperCase();
                
                if (crs.contains(key)){  
                     // id : title
                     crs = courses.get(i).getId() + ": " + courses.get(i).getTitle();
                     // save to ArrayList
                     rst.add(crs);
                }

            }
            
        // search with title 
        }else{
            
            // store ids from courses
            for(int i = 0; i < courses.size(); i++){

                // string search after converting lowercase
                crs = courses.get(i).getTitle().toUpperCase();
                
                if (crs.contains(key)){
                     // id : title
                     crs = courses.get(i).getId() + ": " + courses.get(i).getTitle();
                     // save to ArrayList
                     rst.add(crs);
                }

            } 
            
        }

        // return ArrayList 
        return rst;
       
    }
 
     // --------------------------------------------------------------------------------------
     // add new course into ArrayList courses
     public void addNewCourse(String cId, String cTitle, int cCredit, String cCategory){
      
        // create local Course var
        Course crs = new Course(cId, cTitle, cCredit, cCategory);
   
        // add ArrayList
        courses.add(crs);
        
        // define new Course Id
        setNewCourseId(cId);
        
        // course sorting by Id
        courses.sort(new Comparator<Course>( ) {
            @Override
            public int compare(Course c1, Course c2) {
                return c1.getId().compareTo(c2.getId());
            }
        });
     }
 
     // --------------------------------------------------------------------------------------
     // Course Id calidation
     public boolean validateId(String nid){
        
         // pattern is 4 alphas & 5 digis : ABCD12345 
        final String PATTERN = "^[A-Za-z]{4}[0-9]{5}$";
         
        return nid != null && nid.matches(PATTERN);
     }
     
     // --------------------------------------------------------------------------------------
     // find if new course id already existed into ArrayList courses
     public boolean validateSameId(String nId){
  
        // find Course from ArrayList
        for(int i = 0; i < courses.size(); i++){
            
            // return true if exist
            return courses.get(i).getId().equals(nId);
               
        }
        
        return false;
     }
     
}
