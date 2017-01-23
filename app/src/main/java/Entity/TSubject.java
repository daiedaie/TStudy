package Entity;

public class TSubject {
	private int subjectId;
	private String subjectName;
	private int classId;
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}

	@Override
	public String toString() {
		return "TSubject{" +
				"subjectId=" + subjectId +
				", subjectName='" + subjectName + '\'' +
				", classId=" + classId +
				'}';
	}
}
