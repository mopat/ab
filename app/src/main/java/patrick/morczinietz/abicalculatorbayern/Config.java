package patrick.morczinietz.abicalculatorbayern;

public class Config {
	protected static final int DELETE_ITEM = 2;
	protected static final int REQUEST_CODE = 0;
	protected static final int RESULT_OK = -1;
	public static final String MY_AD_UNIT_ID = "a151618366212ac" ;
	
	private static boolean abiMarksButtonPressed = false;
	private static boolean seminarMarksButtonPressed = false;
	private static boolean elementaryMarksButtonPressed = false;
	private static boolean resultButtonPressed = false;
	private static boolean stopAsyncs = false;

	public static boolean isAbiMarksButtonPressed() {
		return abiMarksButtonPressed;
	}

	public static void setAbiMarksButtonPressed(boolean abiMarksButtonPressed) {
		Config.abiMarksButtonPressed = abiMarksButtonPressed;
	}

	public static boolean isSeminarMarksButtonPressed() {
		return seminarMarksButtonPressed;
	}

	public static void setSeminarMarksButtonPressed(
			boolean seminarMarksButtonPressed) {
		Config.seminarMarksButtonPressed = seminarMarksButtonPressed;
	}

	public static boolean isElementaryMarksButtonPressed() {
		return elementaryMarksButtonPressed;
	}

	public static void setElementaryMarksButtonPressed(
			boolean elementaryMarksButtonPressed) {
		Config.elementaryMarksButtonPressed = elementaryMarksButtonPressed;
	}

	public static boolean isResultButtonPressed() {
		return resultButtonPressed;
	}

	public static void setResultButtonPressed(boolean resultButtonPressed) {
		Config.resultButtonPressed = resultButtonPressed;
	}

	public static boolean isStopAsyncs() {
		return stopAsyncs;
	}

	public static void setStopAsyncs(boolean stopAsyncs) {
		Config.stopAsyncs = stopAsyncs;
	}
}
