package dto;

import java.util.Date;

public class SinhVien {
    private int ID;
    private String name;
    private Date dateOfBirth;
    private String sex;
    private String address;
    private String email;
    private String className;
    private int projectID;

    public SinhVien(int ID, String name, Date dateOfBirth, String sex, String address, String email,
                    String className, int projectID) {
        this.ID = ID;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.address = address;
        this.email = email;
        this.className = className;
        this.projectID = projectID;
    }

    // Getters and setters for each attribute

    public int getID() {
        return ID;
    }

    public SinhVien(String name, Date dateOfBirth, String sex, String address, String email, String className,
			int projectID) {
		super();
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.sex = sex;
		this.address = address;
		this.email = email;
		this.className = className;
		this.projectID = projectID;
	}

	public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

