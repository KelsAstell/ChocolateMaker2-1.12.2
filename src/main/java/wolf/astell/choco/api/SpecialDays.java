/*
Licenced under the [Choco Licence] https://emowolf.fun/choco
So let's build something awesome from this!
Author: Kels_Astell
GitHub: https://github.com/KelsAstell
*/
package wolf.astell.choco.api;

import java.util.Calendar;

public class SpecialDays {
	public static String getToday(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		if(calendar.get(Calendar.MONTH) == Calendar.FEBRUARY && calendar.get(Calendar.DAY_OF_MONTH) == 11)
		{
			return "BIRTHDAY_ASTELL";
		}
		if(calendar.get(Calendar.MONTH) == Calendar.AUGUST && calendar.get(Calendar.DAY_OF_MONTH) == 17)
		{
			return "BIRTHDAY_GUAZI";
		}
		if(calendar.get(Calendar.MONTH) == Calendar.MARCH && calendar.get(Calendar.DAY_OF_MONTH) == 27)
		{
			return "BIRTHDAY_CONGYUN";
		}
		if(calendar.get(Calendar.MONTH) == Calendar.JUNE && calendar.get(Calendar.DAY_OF_MONTH) == 17)
		{
			return "BIRTHDAY_LINGHU";
		}
		if(calendar.get(Calendar.MONTH) == Calendar.FEBRUARY && calendar.get(Calendar.DAY_OF_MONTH) == 14)
		{
			return "VALENTINES_DAY";
		}
		if(calendar.get(Calendar.MONTH) == Calendar.APRIL && calendar.get(Calendar.DAY_OF_MONTH) == 1)
		{
			return "APRIL_FOOLS_DAY";
		}
		return "A_GOOD_DAY";
	}

}
