package dto;

public class GiangVien {
    private int id;
    private String nameGV;
    private String level;
    private String position;
    private int sdt;
    private int idProject;

    public GiangVien() {
        // Constructor mặc định
    }

    public GiangVien(int id, String nameGV, String level, String position, int sdt, int idProject) {
        this.id = id;
        this.nameGV = nameGV;
        this.level = level;
        this.position = position;
        this.sdt = sdt;
        this.idProject = idProject;
    }

    // Getter và setter cho các trường dữ liệu
    // ...
    

    @Override
    public String toString() {
        return nameGV; // Hiển thị tên giảng viên trong JComboBox hoặc danh sách
    }

	public GiangVien(String nameGV, String level, String position, int sdt, int idProject) {
		super();
		this.nameGV = nameGV;
		this.level = level;
		this.position = position;
		this.sdt = sdt;
		this.idProject = idProject;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNameGV() {
		return nameGV;
	}

	public void setNameGV(String nameGV) {
		this.nameGV = nameGV;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getSdt() {
		return sdt;
	}

	public void setSdt(int sdt) {
		this.sdt = sdt;
	}

	public int getIdProject() {
		return idProject;
	}

	public void setIdProject(int idProject) {
		this.idProject = idProject;
	}
}
