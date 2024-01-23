package dto;

public class DoAn {
    private int id;
    private String name;
    private String deadline;
    private String instructor;

    public DoAn(int id, String name, String deadline, String instructor) {
        this.id = id;
        this.name = name;
        this.deadline = deadline;
        this.instructor = instructor;
    }

    
	public DoAn(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	

    
}