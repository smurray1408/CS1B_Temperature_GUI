/**
 * ConvertTemperature is a fully static implementation of the model in the MVC
 * architecture. It performs the actual temperature conversion from Farenheit to 
 * Celsius and vice versa.
 */

class ConvertTemperature	
{	
	private static final double FARENHEIT_OFFSET = 32.0;
	private static final double CELSIUS_TO_FARENHEIT_RATIO = 5.0/9.0;

	/**
	 * calculates and returns the Celsius Temperature equivalent of the passed in Farenheit
	 * Temperature "degreesF"
	 */
	public static double convertFarenheitToCelsius(double degreesF) {
		return (degreesF - FARENHEIT_OFFSET) * CELSIUS_TO_FARENHEIT_RATIO;
	}

	/**
	 * calculates and returns the Farenheit Temperature equivalent of the passed in Celsius
	 * Temperature "degreesC"
	 */
	public static double convertCelsiusToFarenheit(double degreesC) {
		return (degreesC/CELSIUS_TO_FARENHEIT_RATIO + FARENHEIT_OFFSET);
	}
}
