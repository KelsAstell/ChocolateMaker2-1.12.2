package wolf.astell.choco.api;

import java.util.Calendar;

public class SpecialDays {
	public static String getToday(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		if(calendar.get(Calendar.MONTH) == Calendar.APRIL && calendar.get(Calendar.DAY_OF_MONTH) == 1)
		{
			return "APRIL_FOOLS_DAY";
		}
		if(calendar.get(Calendar.MONTH) == Calendar.FEBRUARY && calendar.get(Calendar.DAY_OF_MONTH) == 11)
		{
			return "BIRTHDAY";
		}
		if(calendar.get(Calendar.MONTH) == Calendar.DECEMBER && calendar.get(Calendar.DAY_OF_MONTH) == 31)
		{
			return "APRIL_FOOLS_DAY";//TODO:Remove DEBUGGING
		}
		return "A_PLAIN_NICE_DAY";
	}

}
