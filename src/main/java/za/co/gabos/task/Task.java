package za.co.gabos.task;

/**
 * This class describes a task that need to be done.s
 * e.g 1, Workout, need to go run for 5 km, not complete
 */
public class Task {

    private Integer id; //id needs to default to 0, so that when i add it to the database it just adds a id number.
    private String title;
    private String description;
    private boolean isCompleted;

    public Task(String title, String description){
        this.title = title;
        this.description = description;
        this.id = null;
        this.isCompleted = false;
    }

    // jackson constructor
    public Task(){};



    // getters
    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return isCompleted;
    }


    // setters
    public void setId(Integer id) {
        this.id = id;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
