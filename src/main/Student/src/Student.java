public class Student {


    // TODO: khai bao cac thuoc tinh cho Student
    private String name;
    private String id;
    private String group;
    private String email;
    // TODO: khai bao cac phuong thuc getter, setter cho Student

    /**
     * Constructor 1 - Default constructor with no parameters.
     * Initializes attributes with default values.
     */
    Student() {
        // TODO:
        this.name = "Student";
        this.id = "000";
        this.group = "K62CB";
        this.email = "uet@vnu.edu.vn";

    }

    /**
     * Constructor 2 - Constructor with 3 parameters.
     *
     * @param name  Name of the student
     * @param id    Student ID
     * @param email Student's email address
     */
    Student(String name, String id, String email) {
        // TODO:
        this.name = name;
        this.id = id;
        this.group = "K62CB";
        this.email = email;
    }

    /**
     * Constructor 3 - Copy constructor from another Student object.
     *
     * @param s Another Student object to copy information from.
     */
    //Student
    Student(Student s) {
        // TODO:
        this.name = s.getName();
        this.id = s.getId();
        this.group = s.getGroup();
        this.email = s.getEmail();
    }

    /**
     * getInfo method - Returns detailed information about the student as a string.
     *
     * @return A string containing the student's information.
     */
    String getInfo() {
        return this.name + " - " + this.id + " - " + this.group + " - " + this.email;
    }

    public String getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}