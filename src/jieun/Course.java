////////////////////////////////////////////////////////////////////////////////////////////
//      Java 2 : Final Project
//      Task : Sheridan Course Inventory
////////////////////////////////////////////////////////////////////////////////////////////
//     - current src file : Course.java
//     - current src task : Course class 
//     - created by : Jieun Kwon
//     - created date : March 25, 2018
//     - modified date : March 25, 2018
////////////////////////////////////////////////////////////////////////////////////////////

package jieun;

import java.util.Objects;


/**
 *
 * @author JIEUN KWON
 */
public class Course {
    
    // member vars
    private String id;
    private String title;
    private int credit;
    private String category;

    // ctor with no argument
    public Course() {
        this("", "" , 0, "NONE");  // defalut category is NONE 
        
    }

    // ctor with all 4 arguments
    public Course(String id, String title, int credit, String category) {
        set(id, title, credit, category);
         
    }

    // set with all 4 arguments
    public void set(String id, String title, int credit, String category) {
        this.id = id;
        this.title = title;
        this.credit = credit;
        this.category = category;
    }
    
    // getter : id
    public String getId() {
        return id;
    }
   
    // setter : id
    public void setId(String id) {
        this.id = id;
    }

    // getter : title
    public String getTitle() {
        return title;
    }

    // setter : title
    public void setTitle(String title) {
        this.title = title;
    }

    // getter : credit
    public int getCredit() {
        return credit;
    }

    // setter : credit
    public void setCredit(int credit) {
        this.credit = credit;
    }

    // getter : category
    public String getCategory() {
        return category;
    }

    // setter : category
    public void setCategory(String category) {
        this.category = category;
    }

    // method override: toString 
    @Override
    public String toString() {
        return "Course{" + "id=" + id + ", title=" + title + ", credit=" + credit + ", category=" + category + '}';
    }

    // method override : equals
    @Override
    public boolean equals(Object o) {
        
        if (o instanceof Course) 
        {
            Course course = (Course)o;              //downcating
            return id.equals(course.getId());       // if course id is equal 
        
        }else{
                return false;
        }
    }

    // method override : hashCode
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }   
 
}
