/**
 * Implements the GUI View for a program that converts temperatures from 
 * Farenheit to Celsius, or Celsius to Farenheit. 
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;  // JFrame, JButton, JLabel


class TemperatureGUI extends JFrame implements SwingConstants,ActionListener,FocusListener
{
	private static final Color ACTIVE = Color.GREEN;
	private static final Color SET_ERROR = Color.RED;
	private static final Color CLR_ERROR = Color.BLACK;

	private static final String INVALID_NUMBER_REXP = ".*[^+0-9.-].*";  // pattern match looking for any occurance of a non-digit, -/+ sign or period
	private static final String EMPTY_STRING = "";
	private static final String INPUT_ERROR = "Input Error";
	private static final String DEGREES_C = "Degrees C";
	private static final String DEGREES_F = "Degrees F";
	private static final String QUIT = "Quit";
	private static final String LEFT_ARROW = "<<<";
	private static final String RIGHT_ARROW = ">>>";
	private static final String FRAME_TITLE = "degF <==> degC";
	

	private static final int PAD_HEIGHT = 40;
	private static final int GRID_WIDTH = 1;
	private static final int WIDE_GRID_WIDTH = 3;
	private static final double GRID_X_WEIGHT = 0.5;
	private static final int MY_FONT_SIZE = 18;
	private static final int MINIMUM_TEXT_COLS = 5;
	
	private JButton toDegreesC,toDegreesF, quit;
	private JTextField celsiusTemperature;
	private JLabel farenheitHeader,celsiusHeader,buttonHeader, conversionLabel;
	private JTextField farenheitTemperature;
	private Color defaultButtonBackground;
	private String conversionMessage = EMPTY_STRING;
	
	private TemperatureController tControl;

	public void focusLost(FocusEvent fe) {
	}
	
	/**
	 * Called when the user clicks in either the farenheitTemperature text field or
	 * the celsiusTemperature text field. Decodes the source of the event, and performs 
	 * the following actions:
	 * 1) clears text in both text fields
	 * 2) resets the foreground color of the event source
	 * 3) mark the corresponding button as active (toDegreesC if farenheitTemperature is the source,
	 * toDegreesF if celsiusTemperature is the source)
	 */
	public void focusGained(FocusEvent fe) {
		if (fe.getSource() == farenheitTemperature) {
			clearTemperatureTextFields();		
			resetFarenheitTextColor();
			activeCInactiveFButton();
		} else if (fe.getSource() == celsiusTemperature) {
			clearTemperatureTextFields();		
			resetCelsiusTextColor();
			activeFInactiveCButton();
		}
		conversionLabel.setText(EMPTY_STRING);
	}	

	/**
	 * Called when the user clicks the toDegreesC, toDegreesF or quit buttons
	 * Decodes the source of "evt", and if one of the conversions buttons was pressed,
	 * performs basic error checking of associated text field and flags an error. If no error,
	 * or the quit button was pressed, calls the corresponding handler in tControl.
	 * If either the toDegreesC or toDegreesF button wass pressed, both buttons are
	 * marked as inactive (button color returns to default)
	 */
	public void actionPerformed (ActionEvent evt) {
		String inputTemperature;
		
		if (evt.getSource() == toDegreesC) {
			inputTemperature = farenheitTemperature.getText();
			if (inputTemperature.isEmpty() || inputTemperature.matches(INVALID_NUMBER_REXP)) {
				flagFarenheitError(INPUT_ERROR);
			} else {
				tControl.handleToDegreesCButton(inputTemperature);
			}
			conversionLabel.setText(conversionMessage);
			inactiveCInactiveFButtons();
		} else if (evt.getSource() == toDegreesF) {
			inputTemperature = celsiusTemperature.getText();
			if (inputTemperature.isEmpty() || inputTemperature.matches(INVALID_NUMBER_REXP)) {
				flagCelsiusError(INPUT_ERROR);
			} else {
				tControl.handleToDegreesFButton(inputTemperature);
			}
			conversionLabel.setText(conversionMessage);
			inactiveCInactiveFButtons();
		} else if (evt.getSource() == quit) {
			tControl.handleQuitButton();
		}
	}
	
	/**
	 * Sets the background color of the toDegreesC button to ACTIVE, and the
	 * background color of the toDegreesF button to "defaultButtonBackground"
	 */
	private void activeCInactiveFButton() {
		toDegreesC.setBackground(ACTIVE);
		toDegreesF.setBackground(defaultButtonBackground);
	}

	/**
	 * Sets the background color of the toDegreesF button to ACTIVE, and the
	 * background color of the toDegreesC button to "defaultButtonBackground"
	 */
	private void activeFInactiveCButton() {
		toDegreesF.setBackground(ACTIVE);
		toDegreesC.setBackground(defaultButtonBackground);
	}

	/**
	 * Sets the background color of both the toDegreesC and toDegreesF buttons 
	 * to "defaultButtonBackground"
	 */
	private void inactiveCInactiveFButtons() {
		toDegreesC.setBackground(defaultButtonBackground);
		toDegreesF.setBackground(defaultButtonBackground);
	}

	/**
	 * Creates and initializes components to be added to this JFrame.
	 */
	private void initializeGUIObjects() {
		Font myFont = new Font("Display",Font.BOLD,MY_FONT_SIZE);
		farenheitHeader = new JLabel(DEGREES_F,CENTER);
		farenheitHeader.setFont(myFont);
		celsiusHeader = new JLabel(DEGREES_C, CENTER);
		celsiusHeader.setFont(myFont);
        buttonHeader = new JLabel(EMPTY_STRING,CENTER);
		buttonHeader.setFont(myFont);
		conversionLabel = new JLabel(conversionMessage);
		conversionLabel.setFont(myFont);
		toDegreesC = new JButton (RIGHT_ARROW);
		defaultButtonBackground = toDegreesC.getBackground();
		toDegreesC.setFont(myFont);
		toDegreesF = new JButton (LEFT_ARROW);
		toDegreesF.setFont(myFont);
		farenheitTemperature = new JTextField(MINIMUM_TEXT_COLS);
		farenheitTemperature.setFont(myFont);
		farenheitTemperature.setHorizontalAlignment(JTextField.CENTER);
        celsiusTemperature = new JTextField(MINIMUM_TEXT_COLS);
        celsiusTemperature.setHorizontalAlignment(JTextField.CENTER);
        celsiusTemperature.setFont(myFont);
		quit = new JButton (QUIT);
		quit.setFont(myFont);		
	}
	
	/**
	 * Assigns specific components to be ActionListeners or FocusListeners
	 */
	private void addEventListeners() {		
		toDegreesC.addActionListener(this); 
		toDegreesF.addActionListener(this); 
		quit.addActionListener(this);	
		farenheitTemperature.addFocusListener(this);
		celsiusTemperature.addFocusListener(this);
	}
	
	/**
	 * Initialize "gbc" with the desired constraints to be applied to the GridBagLayout for the next added component.
	 */
	private void setGridConstraints(GridBagConstraints gbc, int x, int y, int width, int fill) {
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.weightx = GRID_X_WEIGHT;
		gbc.fill = fill;
	}
	
	/**
	 * Adds a row of 3 passed in components to the grid layout. Padding is added on either side to enforce a minimum height and balanced
	 * spacing. The fill modes are used to also balance the components. 
	 */
	private void addGridRow(Container contentPane,int row, Component left, Component middle, Component right) {
		GridBagConstraints c = new GridBagConstraints();
		Component dummyBox1 = Box.createRigidArea(new Dimension(0,PAD_HEIGHT));
		Component dummyBox2 = Box.createRigidArea(new Dimension(0,PAD_HEIGHT));
		int col = 0;
		
		setGridConstraints(c,col++,row,GRID_WIDTH,GridBagConstraints.NONE);
		contentPane.add(dummyBox1,c);
		setGridConstraints(c,col++,row,GRID_WIDTH,GridBagConstraints.HORIZONTAL);
		contentPane.add(left,c);
		setGridConstraints(c,col++,row,GRID_WIDTH,GridBagConstraints.CENTER);
		contentPane.add(middle,c);
		setGridConstraints(c,col++,row,GRID_WIDTH,GridBagConstraints.HORIZONTAL);
		contentPane.add(right,c);
		setGridConstraints(c,col++,row,GRID_WIDTH,GridBagConstraints.NONE);
		contentPane.add(dummyBox2,c);
		
	}

	/**
	 * Adds a single component to the grid layout, taking the full row. Padding is added on either side to 
	 * enforce a minimum height and balanced spacing. The fill modes are used to also balance the components. 
	 */
	private void addSpecialGridRow(Container contentPane,int row, Component wideLabel) {
		GridBagConstraints c = new GridBagConstraints();
		Component dummyBox1 = Box.createRigidArea(new Dimension(0,PAD_HEIGHT));
		Component dummyBox2 = Box.createRigidArea(new Dimension(0,PAD_HEIGHT));
		int col = 0;
		
		setGridConstraints(c,col++,row,GRID_WIDTH,GridBagConstraints.NONE);
		contentPane.add(dummyBox1,c);
		setGridConstraints(c,col,row,WIDE_GRID_WIDTH,GridBagConstraints.CENTER);
		contentPane.add(wideLabel,c);
		col += GRID_WIDTH;
		setGridConstraints(c,col++,row,GRID_WIDTH,GridBagConstraints.NONE);
		contentPane.add(dummyBox2,c);
		
	}

	/**
	 * Adds all required components to the Frame, using the GridBagLayout. 
	 * 1) call to initialize all GUI components
	 * 2) bind the toDegreesC,toDegreesF buttons together to treat as a single entity in the layout
	 * 3) place the components in the grid layout, one row at a time.
	 */
	private void addComponentsToPaneGL (Container contentPane) {	
		Component rigidArea = Box.createRigidArea(new Dimension(0,PAD_HEIGHT));
		Box buttonBox = new Box(BoxLayout.Y_AXIS);
		int row = 0;

		initializeGUIObjects();	
		addEventListeners();
		buttonBox.add(toDegreesC);
		buttonBox.add(toDegreesF);
		
		addGridRow(contentPane,row++,farenheitHeader,buttonHeader,celsiusHeader);
		addGridRow(contentPane,row++,farenheitTemperature,buttonBox,celsiusTemperature);
		addSpecialGridRow(contentPane,row++,conversionLabel);
		addGridRow(contentPane,row++,rigidArea,quit,rigidArea);	
	}

	/**
	 * Creates Components and places them onto the Frame.
	 * Stores the reference to "control" so that code in this class can call the methods in "control" 
	 * when the user interacts with the Components in this class.
	 */
	public TemperatureGUI(TemperatureController control)
	{	
		// added to make my GUI behave on Mac like it does on Windows PC...
		try {
		    UIManager.setLookAndFeel(
		            UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setTitle(FRAME_TITLE);
		
		Container contentPane = this.getContentPane();
		tControl = control;
		contentPane.setLayout(new GridBagLayout());
		addComponentsToPaneGL(contentPane);
		pack();	
		this.setSize(500,250);
		setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * clear both celsius and farenheit temperature text fields
	 */
	private void clearTemperatureTextFields() {
		celsiusTemperature.setText(EMPTY_STRING);
		farenheitTemperature.setText(EMPTY_STRING);		
	}

	/**
	 * displays "newTemperature" in the Celsius text field
	 */
	public void updateCelsiusTemperatureString(String newTemperature)
	{	
		celsiusTemperature.setText(newTemperature);
	}
	
	/**
	 * displays "newTemperature" in the Farenheit text field
	 */
	public void updateFarenheitTemperatureString(String newTemperature)
	{	
		farenheitTemperature.setText(newTemperature);
	}
	
	/**
	 * Sets the text color of the Farenheit text field to RED, indicating that an error occurred during parsing
	 * the text in the text field
	 */
	public void flagFarenheitError(String errorMessage) {
		updateCelsiusTemperatureString(EMPTY_STRING);
		updateConversionMessage(errorMessage);	
		farenheitTemperature.setForeground(SET_ERROR);
	}
	
	/**
	 * Sets the text color of the Celsius text field to RED, indicating that an error occurred during parsing
	 * the text in the text field
	 */
	public void flagCelsiusError(String errorMessage) {
		updateFarenheitTemperatureString(EMPTY_STRING);
		updateConversionMessage(errorMessage);	
		celsiusTemperature.setForeground(SET_ERROR);
	}
	
	/**
	 * Sets the text color of the Farenheit text field to BLACK (default color - no errors)
	 */
	public void resetFarenheitTextColor() {
		farenheitTemperature.setForeground(CLR_ERROR);
	}
	
	/**
	 * Sets the text color of the Celsius text field to BLACK (default color - no errors)
	 */
	public void resetCelsiusTextColor() {
		celsiusTemperature.setForeground(CLR_ERROR);
	}
	
	/**
	 * updates the private String to "message".
	 */
	public void updateConversionMessage(String message) {
		conversionMessage = message;
	}
}
