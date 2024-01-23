package dto;

public class ThongKe {
    private int ID;
    private int ID_Project;
    private String instructor;
    private double pointGV;
    private double pointHD;
    private double pointHDC;
    private double tongDiem;
    private double pointTB;

    // Constructors, getters, and setters

    public ThongKe(int ID, int ID_Project, String instructor, double pointGV, double pointHD, double pointHDC, double tongDiem, double pointTB) {
        this.ID = ID;
        this.ID_Project = ID_Project;
        this.instructor = instructor;
        this.pointGV = pointGV;
        this.pointHD = pointHD;
        this.pointHDC = pointHDC;
        this.tongDiem = tongDiem;
        this.pointTB = pointTB;
    }

    public ThongKe(int iD_Project, String instructor, double pointGV, double pointHD, double pointHDC, double tongDiem,
			double pointTB) {
		super();
		ID_Project = iD_Project;
		this.instructor = instructor;
		this.pointGV = pointGV;
		this.pointHD = pointHD;
		this.pointHDC = pointHDC;
		this.tongDiem = tongDiem;
		this.pointTB = pointTB;
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

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public double getPointGV() {
        return pointGV;
    }

    public void setPointGV(double pointGV) {
        this.pointGV = pointGV;
    }

    public double getPointHD() {
        return pointHD;
    }

    public void setPointHD(double pointHD) {
        this.pointHD = pointHD;
    }

    public double getPointHDC() {
        return pointHDC;
    }

    public void setPointHDC(double pointHDC) {
        this.pointHDC = pointHDC;
    }

    public double getTongDiem() {
        return tongDiem;
    }

    public void setTongDiem(double tongDiem) {
        this.tongDiem = tongDiem;
    }

    public double getPointTB() {
        return pointTB;
    }

    public void setPointTB(double pointTB) {
        this.pointTB = pointTB;
    }
}
