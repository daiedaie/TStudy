package Entity;

import java.io.Serializable;

public class TCourse implements Serializable{
	private int courseId;
	private String courseName;
	private String courseReserve;
	private String courseImg;
	private double courseMath;
	private int subjectId;
	private String courseTime;
	private int coursePeople;
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseReserve() {
		return courseReserve;
	}
	public void setCourseReserve(String courseReserve) {
		this.courseReserve = courseReserve;
	}
	public String getCourseImg() {
		return courseImg;
	}
	public void setCourseImg(String courseImg) {
		this.courseImg = courseImg;
	}
	public double getCourseMath() {
		return courseMath;
	}
	public void setCourseMath(double courseMath) {
		this.courseMath = courseMath;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	public String getCourseTime() {
		return courseTime;
	}
	public void setCourseTime(String courseTime) {
		this.courseTime = courseTime;
	}
	public int getCoursePeople() {
		return coursePeople;
	}
	public void setCoursePeople(int coursePeople) {
		this.coursePeople = coursePeople;
	}

	@Override
	public String toString() {
		return "TCourse{" +
				"courseId=" + courseId +
				", courseName='" + courseName + '\'' +
				", courseReserve='" + courseReserve + '\'' +
				", courseImg='" + courseImg + '\'' +
				", courseMath=" + courseMath +
				", subjectId=" + subjectId +
				", courseTime='" + courseTime + '\'' +
				", coursePeople=" + coursePeople +
				'}';
	}
}
