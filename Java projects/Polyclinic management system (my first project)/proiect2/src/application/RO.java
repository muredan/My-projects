package application;

public class RO {

	public static String convertToRO(String day)
	{
		switch (day.toLowerCase()) {
		case "monday":
			return "luni";

		case "tuesday":
			return "marti";

		case "wednesday":
			return "miercuri";

		case "thursday":
			return "joi";

		case "friday":
		 	return "vineri";

		case "saturday":
			return "sambata";

		case "sunday":
			return "duminica";

		default:
			return null;
		}
	}
}
