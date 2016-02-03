package patrick.morczinietz.abicalculatorbayern;

import java.util.ArrayList;

public class SelectedCoursesSavior {
	private static ArrayList<String> allSelectedCoursesList = new ArrayList<String>();

	public static void addCourse(String course){
		allSelectedCoursesList.add(course);
		System.out.println(allSelectedCoursesList.toString());
	}
	public static ArrayList<String> getAllSelectedCoursesList() {
		return allSelectedCoursesList;
	}

	public void setAllSelectedCoursesList(ArrayList<String> allSelectedCoursesList) {
		SelectedCoursesSavior.allSelectedCoursesList = allSelectedCoursesList;
	}
}
