/**
 * TemperatureController is the controller for a GUI-based application which
 * enables the user to convert temperatures from Farenheit to Celsius, and vice versa....
 * It provides the main for initiating both the GUI (view) and the model (which simply performs
 * the algebra for the conversions).
 */
import java.awt.event.*;
import java.awt.*;
import javax.swing.UIManager;


class TemperatureController
{	
	private static final double ABSOLUTE_ZERO = -273.15;
	private static final String NUMBER_FORMAT_ERROR = "Number Format Error";
	private static final String MIN_TEMPERATURE_ERROR = "Temperature < Absolute Zero Error";
	private TemperatureGUI view;

	
	/**
	 * Constructor for TemperatureController Class
	 */
	public TemperatureController()
	{	
		view = new TemperatureGUI(this);
	}
	
	/**
	 * Makes one instance of this class
	 */
	public static void main(String args[])
	{	
		TemperatureController tControl = new TemperatureController();
	}
	
	/**
	 * Called by view actionPerformed when the toDegreesC button is active and pressed.
	 * 1 - calls view to clear any error indications (red text) from the celsius text field
	 * 2 - Attempts to convert "inputTempFarenheit" from a String to a double 
	 *     If successful, calls upon ConvertTemperature to perform the conversion,
	 *     checks the converted Celsius value for minimum temperature, formats the data as a string, 
	 *     and calls view to update the celsius text field and indicate the successful conversion
	 *     If NOT successful, calls view to set an error indication on the farenheit text field,
	 *     and calls view to clear the celsius text field.
	 */
	public void handleToDegreesCButton(String inputTempFarenheit) {
		double degreesF,degreesC;
		view.resetCelsiusTextColor();
		
		try {
			degreesF = Double.parseDouble(inputTempFarenheit);
			degreesC = ConvertTemperature.convertFarenheitToCelsius(degreesF);
			if (degreesC >= ABSOLUTE_ZERO) {
				view.updateCelsiusTemperatureString(String.format("%4.1f",degreesC));
				view.updateConversionMessage("Converted to Celsius");
			} else {
				view.flagFarenheitError(MIN_TEMPERATURE_ERROR);
			}
		} catch (NumberFormatException e) {
			view.flagFarenheitError(NUMBER_FORMAT_ERROR);
		}	
	}
	
	/**
	 * Called by view actionPerformed when the toDegreesF button is active and pressed.
	 * 1 - calls view to clear any error indications (red text) from the farenheit text field
	 * 3 - Attempts to convert "inputTempCelsius" from a String to a double 
	 *     If successful, checks the Celsius value for minimum temperature, calls upon ConvertTemperature to perform the conversion,
	 *     formats the data as a string, and calls view to update the farenheit text field and indicate successful conversion.
	 *     If NOT successful, calls view to set an error indication on the celsius text field,
	 *     and calls view to clear the farenheit text field.
	 */
	public void handleToDegreesFButton(String inputTempCelsius) {
		double degreesF,degreesC;
		view.resetFarenheitTextColor();
		
		try {
			degreesC = Double.parseDouble(inputTempCelsius);
			if (degreesC >= ABSOLUTE_ZERO) {
				degreesF = ConvertTemperature.convertCelsiusToFarenheit(degreesC);
				view.updateFarenheitTemperatureString(String.format("%4.1f",degreesF));
				view.updateConversionMessage("Converted to Farenheit");
			} else {
				view.flagCelsiusError(MIN_TEMPERATURE_ERROR);
			}
		} catch (NumberFormatException e) {
			view.flagCelsiusError(NUMBER_FORMAT_ERROR);
		}
	}
	
	/**
	 * Called by vew actionPerformed when the quit button is pressed. Simply exits the program.
	 */
	public void handleQuitButton() {
		System.exit(0);
	}
}
