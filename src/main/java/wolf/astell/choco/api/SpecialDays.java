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
		if(calendar.get(Calendar.MONTH) == Calendar.APRIL && calendar.get(Calendar.DAY_OF_MONTH) == 1)
		{
			return "APRIL_FOOLS_DAY";
		}
		if(calendar.get(Calendar.MONTH) == Calendar.FEBRUARY && calendar.get(Calendar.DAY_OF_MONTH) == 11)
		{
			return "BIRTHDAY_ASTELL";
		}
		return "A_GOOD_DAY";
	}

}
