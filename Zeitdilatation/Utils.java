package Zeitdilatation;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Utils {
	static NumberFormat formatter = new DecimalFormat("0.####");
		
	public static String format(float f) {
		return formatter.format(f);
	}
}
