package Worker;

public class Worker {
    
    String username, password, name, contactInfo, jobTitle;
    int id;
    double salary;
    boolean is;

    //*****************************************
    //***************Setters*******************
    //*****************************************
    public void setId(int id){
        this.id = id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }

    
    //*****************************************
    //*****************************************

    public void workerInfo(){
        System.out.println("Name: " + name);
        System.out.println("ID: " + id);
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }
    public String getContactInfo() {
        return contactInfo;
    }
    public String getJobTitle() {
        return jobTitle;
    }
    public int getId() {
        return id;
    }
    public double getSalary() {
        return salary;
    }
    
}
