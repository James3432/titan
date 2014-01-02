package atlas.kingj.roi;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Font;

/*
 *  This class provides both a general purpose unit converter for use by other classes, as well as a small toolbox window which is displayed to the user if required
 */
public class UnitConverter {
	
	public JTextField txtInput;
	public JTextField txtOutput;
	public JComboBox cmbInput;
	public JComboBox cmbOutput;
	public JLabel lblFrom;
	public JLabel lblTo;
	public JLabel lblConvert;

	private JFrame frame;
	public JLabel lblThisWindowCan;
	public JLabel lblResult;

	/**
	 * Create the application.
	 */
	public UnitConverter() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
		frame.setVisible(true);
		
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setResizable(false);
		try{
			frame.setIconImage(ImageIO.read(FrmMain.class.getResourceAsStream("/atlas/logo.png")));
		}catch(NullPointerException e){
			System.out.println("Image load error");
		} catch (IOException e) {
			e.printStackTrace();
		}
		frame.setTitle("Unit Converter");
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
	
		frame.setBounds(100, 100, 279, 220);
		
		txtInput = new JTextField();
		txtInput.setToolTipText("Enter the quantity to be converted");
		txtInput.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				Update();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				Update();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});
		txtInput.setBounds(87, 22, 153, 20);
		frame.getContentPane().add(txtInput);
		txtInput.setColumns(10);
		
		txtOutput = new JTextField();
		txtOutput.setToolTipText("Conversion result");
		txtOutput.setEditable(false);
		txtOutput.setBounds(87, 120, 153, 20);
		frame.getContentPane().add(txtOutput);
		txtOutput.setColumns(10);
		
		cmbInput = new JComboBox();
		cmbInput.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cmbInput.setToolTipText("Unit to convert from");
		cmbInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Update();
			}
		});
		cmbInput.setModel(new DefaultComboBoxModel(new String[] {"Length (mm)", "Length (m)", "Length (micron)", "Length (in)", "Length (ft)", "Length (mil)", "Weight (kg)", "Weight (tonne)", "Weight (lbs)", "Weight (ton)"}));
		cmbInput.setBounds(87, 52, 153, 20);
		frame.getContentPane().add(cmbInput);
		
		cmbOutput = new JComboBox();
		cmbOutput.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cmbOutput.setToolTipText("Unit to convert to");
		cmbOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Update();
			}
		});
		cmbOutput.setModel(new DefaultComboBoxModel(new String[] {"Length (mm)", "Length (m)", "Length (micron)", "Length (in)", "Length (ft)", "Length (mil)", "Weight (kg)", "Weight (tonne)", "Weight (lbs)", "Weight (ton)"}));
		cmbOutput.setBounds(87, 82, 153, 20);
		frame.getContentPane().add(cmbOutput);
		
		lblFrom = new JLabel("From:");
		lblFrom.setToolTipText("Unit to convert from");
		lblFrom.setBounds(20, 55, 46, 14);
		frame.getContentPane().add(lblFrom);
		
		lblTo = new JLabel("To:");
		lblTo.setToolTipText("Unit to convert to");
		lblTo.setBounds(20, 85, 46, 14);
		frame.getContentPane().add(lblTo);
		
		lblConvert = new JLabel("Value:");
		lblConvert.setToolTipText("Enter the quantity to be converted");
		lblConvert.setBounds(20, 25, 46, 14);
		frame.getContentPane().add(lblConvert);
		
		lblThisWindowCan = new JLabel("This window can remain open in the background");
		lblThisWindowCan.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblThisWindowCan.setForeground(Color.DARK_GRAY);
		lblThisWindowCan.setBounds(20, 160, 234, 14);
		frame.getContentPane().add(lblThisWindowCan);
		
		lblResult = new JLabel("Result:");
		lblResult.setToolTipText("Conversion result");
		lblResult.setBounds(20, 123, 46, 14);
		frame.getContentPane().add(lblResult);

	}
	
	private void Update(){
		Double input = 0.;
		try{ 
			input = Double.parseDouble(txtInput.getText());
		}catch(NumberFormatException e){
			txtOutput.setText("");
			return;
		}
		
		if(input == 0){
			txtOutput.setText("0");
			return;
		}
		
		txtOutput.setText("");
		Double output = 0.;
		
		Units to = null;
		switch(cmbOutput.getSelectedIndex()){
			case 0 : to = Units.MM; break;
			case 1 : to = Units.M; break;
			case 2 : to = Units.Micron; break;
			case 3 : to = Units.In; break;
			case 4 : to = Units.Ft; break;
			case 5 : to = Units.Mil; break;
			case 6 : to = Units.Kg; break;
			case 7 : to = Units.Tonne; break;
			case 8 : to = Units.Lbs; break;
			case 9 : to = Units.Ton; break;
			default : to = Units.M; break;
		}
		
		Units from = null;
		switch(cmbInput.getSelectedIndex()){
			case 0 : from = Units.MM; break;
			case 1 : from = Units.M; break;
			case 2 : from = Units.Micron; break;
			case 3 : from = Units.In; break;
			case 4 : from = Units.Ft; break;
			case 5 : from = Units.Mil; break;
			case 6 : from = Units.Kg; break;
			case 7 : from = Units.Tonne; break;
			case 8 : from = Units.Lbs; break;
			case 9 : from = Units.Ton; break;
			default : from = Units.M; break;
		}
		
		output = Convert(input, from, to);
		
		if(cmbOutput.getSelectedIndex() == 0){ // output mm
			txtOutput.setText(Double.toString(roundNDecimals(output, 8)) + " mm");
		}
		
		if(cmbOutput.getSelectedIndex() == 1){ // output m
			txtOutput.setText(Double.toString(roundNDecimals(output, 8)) + " m");
		}
		
		if(cmbOutput.getSelectedIndex() == 2){ // output microns
			txtOutput.setText(Double.toString(roundNDecimals(output, 8)) + " microns");
		}
		
		if(cmbOutput.getSelectedIndex() == 3){ // output in
			txtOutput.setText(Double.toString(roundNDecimals(output, 8)) + " in");
		}
		
		if(cmbOutput.getSelectedIndex() == 4){ // output feet
			txtOutput.setText(Double.toString(roundNDecimals(output, 8)) + " feet");
		}
		
		if(cmbOutput.getSelectedIndex() == 5){ // output mils
			txtOutput.setText(Double.toString(roundNDecimals(output, 8)) + " mils");
		}
		
		if(cmbOutput.getSelectedIndex() == 6){ // output kg
			txtOutput.setText(Double.toString(roundNDecimals(output, 8)) + " kg");
		}
		
		if(cmbOutput.getSelectedIndex() == 7){ // output tonne
			txtOutput.setText(Double.toString(roundNDecimals(output, 8)) + " tonnes");
		}
		
		if(cmbOutput.getSelectedIndex() == 8){ // output ton
			txtOutput.setText(Double.toString(roundNDecimals(output, 8)) + " lbs");
		}
		
		if(cmbOutput.getSelectedIndex() == 9){ // output ton
			txtOutput.setText(Double.toString(roundNDecimals(output, 8)) + " tons");
		}
		
	}
	
	double roundNDecimals(double d, int n) {
		DecimalFormat twoDForm;
		switch(n){
		case 1: twoDForm = new DecimalFormat("#.#"); break;
		case 2: twoDForm = new DecimalFormat("#.##"); break;
		case 3: twoDForm = new DecimalFormat("#.###"); break;
		case 4: twoDForm = new DecimalFormat("#.####"); break;
		case 5: twoDForm = new DecimalFormat("#.#####"); break;
		case 6: twoDForm = new DecimalFormat("#.######"); break;
		case 7: twoDForm = new DecimalFormat("#.#######"); break;
		case 8: twoDForm = new DecimalFormat("#.########"); break;
		case 9: twoDForm = new DecimalFormat("#.#########"); break;
		case 10: twoDForm = new DecimalFormat("#.##########"); break;
		default: twoDForm = new DecimalFormat("#.##"); break;
		}
		
		return Double.valueOf(twoDForm.format(d));
	}
	
	public double Convert(Double amount, Units from, Units to){
		double output = 0.;
		double input = amount;
		
		if(to == Units.KM){ // output km
			if(from == Units.KM){
				output = input;
			}
			if(from == Units.M){
				output = input / 1000;
			}
			if(from == Units.MM){
				output = input / 1000000;
			}
			if(from == Units.Micron){
				output = input / 1000000000;
			}
			if(from == Units.Mi){
				output = input * 5280 * 12 * 25.4 / 1000000;
			}
			if(from == Units.Ft){
				output = input * 12 * 25.4 / 1000000;
			}
			if(from == Units.In){
				output = input * 25.4 / 1000000;
			}
			if(from == Units.Mil){
				output = (input / 1000) * 25.4 / 1000000;
			}
		}
		
		if(to == Units.M){ // output m
			if(from == Units.KM){
				output = input * 1000;
			}
			if(from == Units.M){
				output = input;
			}
			if(from == Units.MM){
				output = input / 1000;
			}
			if(from == Units.Micron){
				output = input / 1000000;
			}
			if(from == Units.Mi){
				output = input * 5280 * 12 * 25.4 / 1000;
			}
			if(from == Units.Ft){
				output = input * 12 * 25.4 / 1000;
			}
			if(from == Units.In){
				output = input * 25.4 / 1000;
			}
			if(from == Units.Mil){
				output = (input / 1000000) * 25.4;
			}
		}
		
		if(to == Units.MM){ // output mm
			if(from == Units.KM){
				output = input * 1000000;
			}
			if(from == Units.M){
				output = input * 1000;
			}
			if(from == Units.MM){
				output = input;
			}
			if(from == Units.Micron){
				output = input / 1000;
			}
			if(from == Units.Mi){
				output = input * 5280 * 12 * 25.4;
			}
			if(from == Units.Ft){
				output = input * 12 * 25.4;
			}
			if(from == Units.In){
				output = input * 25.4;
			}
			if(from == Units.Mil){
				output = (input / 1000) * 25.4;
			}
		}
		
		if(to == Units.Micron){ // output microns
			if(from == Units.KM){
				output = input * 1000000000;
			}
			if(from == Units.M){
				output = input * 1000000;
			}
			if(from == Units.MM){
				output = input * 1000;
			}
			if(from == Units.Micron){
				output = input;
			}
			if(from == Units.Mi){
				output = input * 5280 * 12 * 25.4 * 1000;
			}
			if(from == Units.Ft){
				output = input * 12 * 25.4 * 1000;
			}
			if(from == Units.In){
				output = input * 25.4 * 1000;
			}
			if(from == Units.Mil){
				output = input * 25.4;
			}
		}
		
		if(to == Units.Mi){ // output miles
			if(from == Units.KM){
				output = 1000000*input / (25.4*12*5280);
			}
			if(from == Units.M){
				output = 1000*input / (25.4*12*5280);
			}
			if(from == Units.MM){
				output = input / (25.4*12*5280);
			}
			if(from == Units.Micron){
				output = input / (25.4*12*5280*1000);
			}
			if(from == Units.Mi){
				output = input;
			}
			if(from == Units.Ft){
				output = input / 5280;
			}
			if(from == Units.In){
				output = input / (12*5280);
			}
			if(from == Units.Mil){
				output = (input / 1000)  / (12*5280);
			}
		}
		
		if(to == Units.Ft){ // output feet
			if(from == Units.KM){
				output = 1000000*input / (25.4*12);
			}
			if(from == Units.M){
				output = 1000*input / (25.4*12);
			}
			if(from == Units.MM){
				output = input / (25.4*12);
			}
			if(from == Units.Micron){
				output = input / (25.4*12*1000);
			}
			if(from == Units.Mi){
				output = input * 5280;
			}
			if(from == Units.Ft){
				output = input;
			}
			if(from == Units.In){
				output = input / (12);
			}
			if(from == Units.Mil){
				output = (input / 1000)  / (12);
			}
		}
		
		if(to == Units.In){ // output in
			if(from == Units.KM){
				output = 1000000*input / (25.4);
			}
			if(from == Units.M){
				output = 1000*input / (25.4);
			}
			if(from == Units.MM){
				output = input / (25.4);
			}
			if(from == Units.Micron){
				output = input / (25.4*1000);
			}
			if(from == Units.Mi){
				output = input * 5280 * 12;
			}
			if(from == Units.Ft){
				output = input * 12;
			}
			if(from == Units.In){
				output = input;
			}
			if(from == Units.Mil){
				output = input / 1000;
			}
		}
		
		if(to == Units.Mil){ // output mils
			if(from == Units.KM){
				output = 1000000000*input / (25.4);
			}
			if(from == Units.M){
				output = 1000000*input / (25.4);
			}
			if(from == Units.MM){
				output = 1000*input / (25.4);
			}
			if(from == Units.Micron){
				output = input / 25.4;
			}
			if(from == Units.Mi){
				output = input * 5280 * 12 * 1000;
			}
			if(from == Units.Ft){
				output = input * 12 * 1000;
			}
			if(from == Units.In){
				output = input * 1000;
			}
			if(from == Units.Mil){
				output = input;
			}
		}
		
		if(to == Units.Kg){ // output kg
			if(from == Units.g){
				output = input / 1000;
			}
			if(from == Units.Kg){
				output = input;
			}
			if(from == Units.Tonne){
				output = input * 1000;
			}
			if(from == Units.Lbs){
				output = Core.LbsToKg(input);
			}
			if(from == Units.Ton){
				output = input * 907;
			}
		}
		
		if(to == Units.g){ // output kg
			if(from == Units.g){
				output = input;
			}
			if(from == Units.Kg){
				output = input * 1000;
			}
			if(from == Units.Tonne){
				output = input * 1000 * 1000;
			}
			if(from == Units.Lbs){
				output = Core.LbsToKg(input) * 1000;
			}
			if(from == Units.Ton){
				output = input * 907 * 1000;
			}
		}
		
		if(to == Units.Tonne){ // output tonne
			if(from == Units.g){
				output = input / 1000000;
			}
			if(from == Units.Kg){
				output = input / 1000;
			}
			if(from == Units.Tonne){
				output = input;
			}
			if(from == Units.Lbs){
				output = Core.LbsToKg(input) / 1000;
			}
			if(from == Units.Ton){
				output = input * 907 / 1000;
			}
		}
		
		if(to == Units.Lbs){ // output tonne
			if(from == Units.g){
				output = Core.KgToLbs(input / 1000);
			}
			if(from == Units.Kg){
				output = Core.KgToLbs(input);
			}
			if(from == Units.Tonne){
				output = Core.KgToLbs(input*1000);
			}
			if(from == Units.Ton){
				output = Core.KgToLbs(Core.TonToKg(input));
			}
		}
		
		if(to == Units.Ton){ // output ton
			if(from == Units.g){
				output = input / (907*1000);
			}
			if(from == Units.Kg){
				output = input / 907;
			}
			if(from == Units.Tonne){
				output = input * 1000 / 907;
			}
			if(from == Units.Lbs){
				output = Core.KgToTon(Core.LbsToKg(input));
			}
			if(from == Units.Ton){
				output = input;
			}
		}
		
		if(to == Units.Day){ // output day
			if(from == Units.Day){
				output = input;
			}
			if(from == Units.Hr){
				output = input / 24;
			}
			if(from == Units.Min){
				output = input / (24 * 60);
			}
			if(from == Units.S){
				output = input / (24 * 3600);
			}
		}
		
		if(to == Units.Hr){ // output hour
			if(from == Units.Day){
				output = input * 24;
			}
			if(from == Units.Hr){
				output = input;
			}
			if(from == Units.Min){
				output = input / 60;
			}
			if(from == Units.S){
				output = input / 3600;
			}
		}
		
		if(to == Units.Min){ // output min
			if(from == Units.Day){
				output = input * 24 * 60;
			}
			if(from == Units.Hr){
				output = input * 60;
			}
			if(from == Units.Min){
				output = input;
			}
			if(from == Units.S){
				output = input / 60;
			}
		}
		
		if(to == Units.S){ // output sec
			if(from == Units.Day){
				output = input * 24 * 3600;
			}
			if(from == Units.Hr){
				output = input * 3600;
			}
			if(from == Units.Min){
				output = input * 60;
			}
			if(from == Units.S){
				output = input;
			}
		}
		
		return output;
	}
	
	public enum Units {
		KM,
		M,
		MM,
		Micron,
		Mi,
		Ft,
		In,
		Mil,
		Kg,
		g,
		Tonne,
		Lbs,
		Ton,
		Day,
		Hr,
		Min,
		S
	}

}
