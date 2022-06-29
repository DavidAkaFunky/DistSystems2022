package pt.ulisboa.tecnico.classes.classserver;

public class StudentDomain {
    private String id;
    private String name;

    /**
     * Creates a student domain given their ID and name
     * @param id
     * @param name
     */
    public StudentDomain(String id, String name){
        this.id = id;
        this.name = name;
    }

    public String getID(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }
}
