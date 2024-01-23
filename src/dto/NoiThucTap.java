// Trong gói dto
package dto;

public class NoiThucTap {
    private int ID;
    private int ID_Project;
    private String InternshipAddress;
    private String TimeLine;
    private String Instructor;

    public NoiThucTap(int ID, int ID_Project, String InternshipAddress, String TimeLine, String Instructor) {
        this.ID = ID;
        this.ID_Project = ID_Project;
        this.InternshipAddress = InternshipAddress;
        this.TimeLine = TimeLine;
        this.Instructor = Instructor;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_Project() {
        return ID_Project;
    }

    public void setID_Project(int ID_Project) {
        this.ID_Project = ID_Project;
    }

    public String getInternshipAddress() {
        return InternshipAddress;
    }

    public void setInternshipAddress(String InternshipAddress) {
        this.InternshipAddress = InternshipAddress;
    }

    public String getTimeLine() {
        return TimeLine;
    }

    public void setTimeLine(String TimeLine) {
        this.TimeLine = TimeLine;
    }

    public String getInstructor() {
        return Instructor;
    }

    public void setInstructor(String Instructor) {
        this.Instructor = Instructor;
    }

    @Override
    public String toString() {
        return "NoiThucTap [ID=" + ID + ", ID_Project=" + ID_Project + ", InternshipAddress=" + InternshipAddress
                + ", TimeLine=" + TimeLine + ", Instructor=" + Instructor + "]";
    }
}
