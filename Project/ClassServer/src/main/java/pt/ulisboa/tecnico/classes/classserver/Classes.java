package pt.ulisboa.tecnico.classes.classserver;

import pt.ulisboa.tecnico.classes.contract.ClassesDefinitions;
import pt.ulisboa.tecnico.classes.contract.ClassesDefinitions.ClassState;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Classes {

    private static final Logger LOGGER = Logger.getLogger(Classes.class.getName());
    private Integer capacity = 0;
    private boolean serverStatus = true; 
    private boolean enrollmentsOpen = false;
    private Map<String, StudentDomain> enrolledStudents = new ConcurrentHashMap<>();
    private Map<String, StudentDomain> discardedStudents = new ConcurrentHashMap<>();
    private boolean inMaintenance = false;
    private boolean gossipActivated = true;
    
    public Classes(){}

    public boolean getServerStatus() {
        return this.serverStatus;
    }

    public void setServerStatus(boolean status) {
        this.serverStatus = status;
    }

    public Integer getCapacity() {
        return this.capacity;
    }

    public Integer getEnrolledSize(){
        return this.enrolledStudents.size();
    }

    public boolean areEnrollmentsOpen() {
        return this.enrollmentsOpen;
    }

    public void setOpenEnrollmentsStatus(boolean enrollmentsOpen) {
        this.enrollmentsOpen = enrollmentsOpen;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Map<String, StudentDomain> getEnrolledStudents() {
        return this.enrolledStudents;
    }

    public Map<String, StudentDomain> getDiscardedStudents() {
        return this.discardedStudents;
    }

    public boolean inMaintenance() {
        return this.inMaintenance;
    }

    public void setMaintenance(boolean inMaintenance) {
        this.inMaintenance = inMaintenance;
    }

    public boolean isGossipActivated() {
        return this.gossipActivated;
    }

    public void setGossipActivated(boolean gossipActivated) {
        this.gossipActivated = gossipActivated;
    }

    /**
     * Open enrollments with a given capacity, unless they are already open
     * @param capacity
     * @return True if it can open, False if it's already open
     */
    public boolean openEnrollments(Integer capacity){
        if (this.enrollmentsOpen == true)
            return false;

        this.enrollmentsOpen = true;
        this.capacity = capacity;
        return true;
    }

    /**
     * Close enrollments, unless they are already closed
     * @return True if it can close, False if it's already closed
     */
    public boolean closeEnrollments(){
        if (this.enrollmentsOpen == false)
            return false;

        this.enrollmentsOpen = false;
        return true;
    }

    /**
     * Enroll a student given its ID and name
     * @param studentID
     * @param studentName
     * @return false if student already enrolled, true if it can be enrolled
     */
    public boolean enrollStudent(String studentID, String studentName) {
        if (enrolledStudents.containsKey(studentID))
            return false;

        this.discardedStudents.remove(studentID);
        StudentDomain student = new StudentDomain(studentID, studentName);
        this.enrolledStudents.put(studentID, student);

        return true;
    }

    /**
     * Cancel a student's enrollment given its ID
     * @param studentID
     * @return false if there is no student with that ID in the class, true if it can be removed
     */
    public boolean cancelStudentEnrollment(String studentID){
        StudentDomain student = this.enrolledStudents.remove(studentID);
        if (student == null)
            return false;
        this.discardedStudents.put(studentID, student);
        return true;
    }

    /**
     * Add a student to the discarded students' list
     * @param studentID the student's ID
     * @param studentName the student's name
     */
    public void addDiscardedStudent(String studentID, String studentName){
        StudentDomain student = new StudentDomain(studentID, studentName);
        this.discardedStudents.put(studentID, student);
    }

    /**
     * Create a ClassState, used in the gRPC protocol.
     * @return a ClassState instance, containing the capacity, enrollment status,
     *         the list of enrolled students and the list of discarded students.
     */
    public ClassState getClassState(){
        ClassState.Builder stateBuilder = ClassState.newBuilder()
                .setCapacity(getCapacity())
                .setOpenEnrollments(areEnrollmentsOpen());

        // Add each enrolled student to the ClassState instance.
        for (StudentDomain s: getEnrolledStudents().values()){
            stateBuilder.addEnrolled(ClassesDefinitions.Student.newBuilder()
                    .setStudentId(s.getID())
                    .setStudentName(s.getName()));
        }

        // Add each discarded student to the ClassState instance.
        for (StudentDomain s: getDiscardedStudents().values()){
            stateBuilder.addDiscarded(ClassesDefinitions.Student.newBuilder()
                    .setStudentId(s.getID())
                    .setStudentName(s.getName()));
        }

        return stateBuilder.build();
    }

    /**
     * Set the class' parameters, given a ClassState sent by a primary server to its secondary servers.
     * @param classState a ClassState instance, containing the capacity, enrollment status,
     *                   the list of enrolled students and the list of discarded students.
     */

    public void setClassState(ClassState classState){
        setCapacity(classState.getCapacity());
        setOpenEnrollmentsStatus(classState.getOpenEnrollments());

        enrolledStudents.clear();
        for (ClassesDefinitions.Student s: classState.getEnrolledList())
            enrolledStudents.put(s.getStudentId(), new StudentDomain(s.getStudentId(), s.getStudentName()));

        discardedStudents.clear();
        for (ClassesDefinitions.Student s: classState.getDiscardedList())
            discardedStudents.put(s.getStudentId(), new StudentDomain(s.getStudentId(), s.getStudentName()));
    }

    public boolean inClass(String studentID){
        return enrolledStudents.containsKey(studentID) || discardedStudents.containsKey(studentID);
    }
}
