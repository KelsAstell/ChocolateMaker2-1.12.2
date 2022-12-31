package wolf.astell.choco.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import wolf.astell.choco.Main;

public class Log {
	
	private static final Logger l = LogManager.getLogger(Main.MODID);
	private static final boolean dbg = false;
	
	public static void i(Object o) {
		l.info(o);
	}
	
	public static void d(Object o) {
		if (dbg) i("\t[DEBUG]\t\t"+o.toString());
		else l.debug(o);
	}

	public static void w(Object o) {
		l.warn(o);
	}

	public static void e(Object o) {
		l.error(o);
	}
	
	public static void f(Object o) {
		l.fatal(o);
	}
}
