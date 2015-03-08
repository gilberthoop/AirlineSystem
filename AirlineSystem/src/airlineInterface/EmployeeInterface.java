package airlineInterface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;

import airline.AirlineSystem;
import airline.Flight;
import airline.FlightNumberGen;
import airline.FlightPath;
import airline.Plane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.Label;
import java.awt.Choice;
import java.awt.Button;
import java.awt.ScrollPane;
import java.awt.TextField;

public class EmployeeInterface {

	private JFrame frame;
	private JTable table;
	private JList flightList;
	private JList tempFlightList;
	
	private JPanel addPlanePathPane;
	private JPanel addFlightPane;
	
	private AirlineSystem airline;
	
	private JTextField textFieldSearch;
	private Label flightPathAddLable;
	
	JComboBox<Plane> comboBoxPlanes;
	JComboBox<FlightPath> comboBoxPaths;
	
	Choice planeChoice, pathChoice, monthChoice, dayChoice, timeChoice;
	
	List<Plane> planes;
	List<FlightPath> paths;
	List<Flight> tempFlights;
	
	
	String[] days = new String[365];
	int[] time    =  new int[24*4];
	Calendar cal;
	
	private String searchBox
				  ,searchPlane = "All Planes"
				  ,searchPath = "All Paths";
	private JPanel addFlightPathP;
	private JButton addFlightPaneButton;
	private JTextField textFieldSource;
	private JTextField textFieldDestination;
	private Label label_1;
	private Label label_2;
	private Label label_3;
	private Label label_4;
	private Label label_5;
	private Label addPlaneLabel;
	private JTextField textFieldPlaneLocation;
	private JTextField textFieldPlaneID;
	private JTextField textFieldPlaneModel;
	private JTextField textFieldCoachSeats;
	private JTextField textFieldEconSeats;
	private JTextField textFieldFirstClassSeats;
	private JTextField textFieldDate;
	private JLabel textLabelBadDate;
	private JTextField textFieldTime;
	private JLabel lblTime;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeInterface window = new EmployeeInterface();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EmployeeInterface() {
		airline = new AirlineSystem();
		
		initialize();
		
		loadAirlineInfo();
	}
	
	private void updatePlanes(){
		DefaultComboBoxModel m = new DefaultComboBoxModel();
		List<Plane> p = airline.getAllPlanes();
		m.addElement("All Planes");
		for(int i=0;i<p.size();i++){
			m.addElement(p.get(i));
		}
		comboBoxPlanes.setModel(m);
		
	}

	
	private void loadAirlineInfo(){
		
		
		DefaultComboBoxModel m = new DefaultComboBoxModel();
		List<Plane> p = airline.getAllPlanes();
		m.addElement("All Planes");
		for(int i=0;i<p.size();i++){
			m.addElement(p.get(i));
		}
		comboBoxPlanes.setModel(m);
		
		m = new DefaultComboBoxModel();
		List<FlightPath> fp = airline.getAllFlightPaths();
		m.addElement("All Paths");
		for(int i=0;i<fp.size();i++){
			m.addElement(fp.get(i));
		}
		comboBoxPaths.setModel(m);
		
		
		planeChoice.removeAll();
		pathChoice.removeAll();
		
		monthChoice.removeAll();
		dayChoice.removeAll();
		timeChoice.removeAll();
		
		planes = airline.getAllPlanes();
		planeChoice.add("Plane");
		for(int i=0;i<planes.size();i++){
			planeChoice.add(planes.get(i).toString());
		}
		
		paths = airline.getAllFlightPaths();
		pathChoice.add("Path");
		for(int i=0;i<paths.size();i++){
			pathChoice.add(paths.get(i).toString());
		}
		
		tempFlights = new ArrayList<Flight>();
	}
	
	private void updateChoice(){
		
		planeChoice.removeAll();
		pathChoice.removeAll();

		
		planes = airline.getAllPlanes();
		planeChoice.add("Plane");
		for(int i=0;i<planes.size();i++){
			planeChoice.add(planes.get(i).toString());
		}
		
		paths = airline.getAllFlightPaths();
		pathChoice.add("Path");
		for(int i=0;i<paths.size();i++){
			pathChoice.add(paths.get(i).toString());
		}
	}
	
	
	
	private void addNewPlane(){
		Plane tPlane = null;
		
		String location = textFieldPlaneLocation.getText();
		int tnumCoach = Integer.parseInt(textFieldCoachSeats.getText()),
			tnumEcon  = Integer.parseInt( textFieldEconSeats.getText()),
			tnumFC	  = Integer.parseInt(textFieldFirstClassSeats.getText()),
			planeID	  = Integer.parseInt(textFieldPlaneID.getText());
		
		String planeModel = textFieldPlaneModel.getText();
			  
		
		tPlane = new Plane(location,planeModel,tnumCoach,tnumFC,tnumEcon,planeID,200);
		if(!airline.isValidPlane(tPlane)){
			addPlaneLabel.setText("Error Invalid Plane");
		}else{
			if(airline.addPlane(tPlane))
				addPlaneLabel.setText("Plane: " +tPlane+ " was added");
			else 
				addPlaneLabel.setText("Error - Plane: " +tPlane+ " was not added");
		}
		updatePlanes();
		updateChoice();
	}
	private void addFlight(){
		//TODO
		
		Plane tPlane = null;
		FlightPath tPath = null;
		Date tDate = null;
		String tFLightNo = null;
		
		int planeC = planeChoice.getSelectedIndex();
		int pathC = pathChoice.getSelectedIndex();
		
		int monthC = monthChoice.getSelectedIndex();
		int dayC = dayChoice.getSelectedIndex();
		int timeC = timeChoice.getSelectedIndex();
		
		String dateString = textFieldDate.getText();
		String stringHours= textFieldTime.getText();
		
		//Change date field
		//if(planeC != 0 && pathC != 0 && monthC != 0 && dayC != 0 && timeC != 0){
		if(isValidDate(dateString)){
			
			tDate = toDate(dateString);
			int hours = Integer.parseInt(stringHours);
			tDate.setHours(hours);
			
			//System.out.println(longDate);
			/*
			int year = 0
			, month = 0
			,day = 0
			,hours = 0,
			mins = 0;
			*/
			
			tPlane = planes.get(planeC-1);
			tPath = paths.get(pathC-1);
			
			//tFLightNo = FlightNumberGen.getNextFlightNumber();
					
			Flight tFlight = airline.createFlightObject(tPlane,tPath,tDate);
					
			
			tempFlights.add(tFlight);
			updateTempFlightsModel();
			checkConflict();
			textLabelBadDate.setText("");
			//airline.
			//user has selected all the values needed to create a flight
			//use the index to get the value from the list
			//build a flight
			//add the flight to the temp flight list
			//update the temp flight model
			//add the model to the list
			//check for confliction
			//if confliction tell the user
		}else{
			textLabelBadDate.setText("Invalid Format Enter DD-MM-YYYY");
		}
		
		
	}
	private boolean isValidDate(String date){
		
		String flield[] = date.split("-");
		
		if(flield.length == 3)
			return true;
		else return false;
	}
	
	private Date toDate(String stringDate){
		//long longDate = 0;
		
		System.out.println(stringDate);
		
		String field[] = stringDate.split("-");
		int day   = Integer.parseInt(field[0]);
		int month = Integer.parseInt(field[1])-1;
		int year  = Integer.parseInt(field[2])-1900;
		
		Date tDate = new Date(year,month,day);
		
		return tDate;
		
	}
	
	private void checkConflict(){
		//TODO
	}
	
	private void confirmFlights(){
		//TODO
		airline.addFlight(tempFlights);
		cancelTempFlights();
		performSearch();
		//add the list of flights to the system flights
	}
	
	private void cancelTempFlights(){
		//TODO
		//cancel all the temp flights
		tempFlights = new ArrayList<Flight>();
		updateTempFlightsModel();
	}
	
	private void addNewFlightPath(){
		FlightPath p;
		String source = textFieldSource.getText();
		String dest   = textFieldDestination.getText();
		p = new FlightPath(source, dest, 200);
		airline.addNewFlightPath(p);
		p = new FlightPath(dest, source, 200);
		airline.addNewFlightPath(p);
		updateFlightPathModel();
		flightPathAddLable.setText("added: " + source + " to " + dest + " and "+ dest + " to " + source);
		updateChoice();
		
	}
	
	private void updateTempFlightsModel(){
		DefaultListModel listModel;
		listModel = new DefaultListModel();
		
		for(int i=0;i<tempFlights.size();i++)
			listModel.addElement(tempFlights.get(i));
		
		tempFlightList.setModel(listModel);
		
	}
	
	private void updateFlightPathModel(){
		DefaultComboBoxModel m = new DefaultComboBoxModel();
		List<FlightPath> fp = airline.getAllFlightPaths();
		m.addElement("All Paths");
		for(int i=0;i<fp.size();i++){
			m.addElement(fp.get(i));
		}
		comboBoxPaths.setModel(m);
	}
	
	private void updateFlightsModel(){
		performSearch();
	}
	
	private void performSearch(){
		
		DefaultListModel listModel;
		listModel = new DefaultListModel();
		System.out.println("Clicked");
		List<Flight> f = new ArrayList<Flight>();
		
		
		if(!searchPlane.equalsIgnoreCase("All Planes") && !searchPath.equalsIgnoreCase("All Paths")){
			//System.out.println("in 1");
			Plane plane = (Plane) comboBoxPlanes.getModel().getSelectedItem();
			List<Flight> t = airline.getFlights(plane);
			FlightPath path = (FlightPath) comboBoxPaths.getModel().getSelectedItem();
			for(int i=0;i<t.size();i++){
				if(t.get(i).getPath().equals(path))
					f.add(t.get(i));
			}
			
			
		}else if(!searchPlane.equalsIgnoreCase("All Planes")){
			//System.out.println("in 2");
			Plane plane = (Plane) comboBoxPlanes.getModel().getSelectedItem();
			f = airline.getFlights(plane);
			
			
		}else if(!searchPath.equalsIgnoreCase("All Paths")){
			//System.out.println("in 3");
			FlightPath path = (FlightPath) comboBoxPaths.getModel().getSelectedItem();
			f = airline.getFlights(path);
			
		}else{
			System.out.println("in 4");
			f = airline.getAllFlights();
		}
		
		if(textFieldSearch.getText() != ""){
			for(int i=0;i<f.size();i++){
				if(f.get(i).getFlightNumber().contains(textFieldSearch.getText()))
					listModel.addElement(f.get(i));
			}
				
		}
			
		else{
			
			for(int i=0;i<f.size();i++)
				listModel.addElement(f.get(i));
		}
		
		
		flightList.setModel(listModel);
		
		
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		/*
		cal = new GregorianCalendar();
		cal.get
		*/
		
		
		
		
		
		
		frame = new JFrame();
		frame.setBounds(100, 100, 750, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		DefaultComboBoxModel m = new DefaultComboBoxModel();
		List<Plane> p = airline.getAllPlanes();
		m.addElement("All Planes");
		for(int i=0;i<p.size();i++){
			m.addElement(p.get(i));
		}
		
		m = new DefaultComboBoxModel();
		List<FlightPath> fp = airline.getAllFlightPaths();
		m.addElement("All Paths");
		for(int i=0;i<fp.size();i++){
			m.addElement(fp.get(i));
		}
		
		
		/*
		comboBoxDestination = new JComboBox();
		comboBoxDestination.setBounds(223, 13, 80, 22);
		main.add(comboBoxDestination);
		*/
		
		DefaultListModel<Flight> listModel;
		listModel = new DefaultListModel<Flight>();
		
		List<Flight> f = airline.getAllFlights();
		for(int i=0;i<f.size();i++)
			listModel.addElement(f.get(i));
		
		addFlightPane = new JPanel();
		frame.getContentPane().add(addFlightPane, "name_286351424781653");
		addFlightPane.setLayout(null);
		
		
		
		
		comboBoxPlanes = new JComboBox<Plane>();
		comboBoxPlanes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg) {
				//int idx = comboBoxPlanes.getSelectedIndex();
				searchPlane = comboBoxPlanes.getModel().getSelectedItem().toString();
				performSearch();
			}
		});
		comboBoxPlanes.setModel(m);
		comboBoxPlanes.setBounds(12, 38, 80, 22);
		
			addFlightPane.add(comboBoxPlanes);
			
			comboBoxPaths = new JComboBox();
			comboBoxPaths.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg) {
					//int idx = comboBoxPlanes.getSelectedIndex();
					searchPath = comboBoxPaths.getModel().getSelectedItem().toString();
					performSearch();
				}
			});
			
			comboBoxPaths.setBounds(121, 38, 80, 22);
			comboBoxPaths.setModel(m);
			addFlightPane.add(comboBoxPaths);
			
			textFieldSearch = new JTextField();
			textFieldSearch.setBounds(372, 38, 129, 22);
			addFlightPane.add(textFieldSearch);
			textFieldSearch.setColumns(10);
			
			JLabel lblFlightNo = new JLabel("Flight No.");
			lblFlightNo.setBounds(314, 41, 56, 16);
			addFlightPane.add(lblFlightNo);
			
			JLabel lblFlights = new JLabel("FLIGHTS");
			lblFlights.setHorizontalAlignment(SwingConstants.CENTER);
			lblFlights.setBounds(12, 73, 489, 22);
			addFlightPane.add(lblFlights);
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(12, 99, 489, 119);
			addFlightPane.add(scrollPane);
			
			flightList = new JList();
			scrollPane.setViewportView(flightList);
			
				flightList.setModel(listModel);
				
				Label label = new Label("ADD FLIGHT");
				label.setAlignment(Label.CENTER);
				label.setBounds(12, 224, 489, 32);
				addFlightPane.add(label);
				
				JScrollPane scrollPaneAddFlights = new JScrollPane();
				scrollPaneAddFlights.setBounds(12, 355, 489, 107);
				addFlightPane.add(scrollPaneAddFlights);
				
				tempFlightList = new JList();
				scrollPaneAddFlights.setViewportView(tempFlightList);
				
				JLabel lblConflictionNotice = new JLabel("Confliction Notice");
				lblConflictionNotice.setBounds(12, 475, 489, 29);
				addFlightPane.add(lblConflictionNotice);
				
				planeChoice = new Choice();
				planeChoice.setBounds(119, 284, 82, 22);
				addFlightPane.add(planeChoice);
				
				monthChoice = new Choice();
				monthChoice.setBounds(584, 224, 82, 22);
				addFlightPane.add(monthChoice);
				
				dayChoice = new Choice();
				dayChoice.setBounds(584, 284, 82, 22);
				addFlightPane.add(dayChoice);
				
				timeChoice = new Choice();
				timeChoice.setBounds(586, 153, 80, 22);
				addFlightPane.add(timeChoice);
				
				pathChoice = new Choice();
				pathChoice.setBounds(12, 284, 80, 22);
				addFlightPane.add(pathChoice);
				
				Button AddFlightButton = new Button("Add Flight");
				AddFlightButton.setActionCommand("Add Flight");
				AddFlightButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						addFlight();
						
						
					}
				});
				AddFlightButton.setBounds(12, 312, 79, 24);
				addFlightPane.add(AddFlightButton);
				
				JButton btnConfirm = new JButton("confirm");
				btnConfirm.setBounds(12, 517, 97, 25);
				btnConfirm.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						confirmFlights();	
					}
				});
				addFlightPane.add(btnConfirm);
				
				JButton btnCancel = new JButton("cancel");
				btnCancel.setBounds(121, 517, 97, 25);
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						cancelTempFlights();
					}
				});
				addFlightPane.add(btnCancel);
				
				JButton btnAddPlanePath = new JButton("Add Plane and Path");
				btnAddPlanePath.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						addPlanePathPane.setVisible(true);
						addFlightPane.setVisible(false);
						
					}
				});
				btnAddPlanePath.setBounds(12, 0, 168, 25);
				addFlightPane.add(btnAddPlanePath);
				
				textFieldDate = new JTextField();
				textFieldDate.setBounds(277, 284, 116, 22);
				addFlightPane.add(textFieldDate);
				textFieldDate.setColumns(10);
				
				JLabel lblDdmmyyyy = new JLabel("DD-MM-YYYY");
				lblDdmmyyyy.setHorizontalAlignment(SwingConstants.CENTER);
				lblDdmmyyyy.setBounds(277, 262, 116, 16);
				addFlightPane.add(lblDdmmyyyy);
				
				JLabel lblDate = new JLabel("Date:");
				lblDate.setBounds(236, 290, 49, 16);
				addFlightPane.add(lblDate);
				
				textLabelBadDate = new JLabel("");
				textLabelBadDate.setBounds(405, 290, 96, 16);
				addFlightPane.add(textLabelBadDate);
				
				textFieldTime = new JTextField();
				textFieldTime.setBounds(277, 312, 116, 22);
				addFlightPane.add(textFieldTime);
				textFieldTime.setColumns(10);
				
				lblTime = new JLabel("Time:");
				lblTime.setBounds(236, 312, 56, 16);
				addFlightPane.add(lblTime);
				
				JButton btnSearch = new JButton("search");
				btnSearch.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						performSearch();
						
					}
				});
				btnSearch.setBounds(404, 63, 97, 25);
				addFlightPane.add(btnSearch);
		
		addPlanePathPane = new JPanel();
		frame.getContentPane().add(addPlanePathPane, "name_286356337105514");
		addPlanePathPane.setLayout(null);
		
		addFlightPaneButton = new JButton("Add Flight Page");
		addFlightPaneButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						addPlanePathPane.setVisible(false);
						addFlightPane.setVisible(true);
						
					}
				});
		addFlightPaneButton.setBounds(12, 13, 149, 25);
		addPlanePathPane.add(addFlightPaneButton);
		
		JLabel lblSource = new JLabel("Source");
		lblSource.setBounds(12, 90, 75, 16);
		addPlanePathPane.add(lblSource);
		
		textFieldSource = new JTextField();
		textFieldSource.setBounds(91, 87, 116, 22);
		addPlanePathPane.add(textFieldSource);
		textFieldSource.setColumns(10);
		
		JLabel lblDestination = new JLabel("Destination");
		lblDestination.setBounds(12, 125, 75, 16);
		addPlanePathPane.add(lblDestination);
		
		textFieldDestination = new JTextField();
		textFieldDestination.setBounds(91, 122, 116, 22);
		addPlanePathPane.add(textFieldDestination);
		textFieldDestination.setColumns(10);
		
		JButton btnAddFlightPath = new JButton("Add Flight Path");
		btnAddFlightPath.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						addNewFlightPath();
					}
				});
		btnAddFlightPath.setBounds(70, 157, 137, 25);
		addPlanePathPane.add(btnAddFlightPath);
		
		flightPathAddLable = new Label("");
		flightPathAddLable.setBounds(12, 188, 195, 24);
		addPlanePathPane.add(flightPathAddLable);
		
		label_1 = new Label("Add New Plane");
		label_1.setAlignment(Label.CENTER);
		label_1.setBounds(10, 218, 280, 24);
		addPlanePathPane.add(label_1);
		
		label_2 = new Label("Add New Flight Path");
		label_2.setAlignment(Label.CENTER);
		label_2.setBounds(10, 44, 280, 24);
		addPlanePathPane.add(label_2);
		
		label_3 = new Label("Plane Model");
		label_3.setBounds(12, 248, 86, 24);
		addPlanePathPane.add(label_3);
		
		label_4 = new Label("Plane ID");
		label_4.setBounds(12, 278, 70, 24);
		addPlanePathPane.add(label_4);
		
		label_5 = new Label("location");
		label_5.setBounds(12, 308, 70, 24);
		addPlanePathPane.add(label_5);
		
		textFieldPlaneLocation = new JTextField();
		textFieldPlaneLocation.setBounds(105, 308, 116, 22);
		addPlanePathPane.add(textFieldPlaneLocation);
		textFieldPlaneLocation.setColumns(10);
		
		textFieldPlaneID = new JTextField();
		textFieldPlaneID.setBounds(105, 280, 116, 22);
		addPlanePathPane.add(textFieldPlaneID);
		textFieldPlaneID.setColumns(10);
		
		textFieldPlaneModel = new JTextField();
		textFieldPlaneModel.setBounds(105, 248, 116, 22);
		addPlanePathPane.add(textFieldPlaneModel);
		textFieldPlaneModel.setColumns(10);
		
		JLabel lblCoachSeats = new JLabel("Coach Seats");
		lblCoachSeats.setBounds(233, 248, 86, 16);
		addPlanePathPane.add(lblCoachSeats);
		
		JLabel lblEconomySeats = new JLabel("Economy Seats");
		lblEconomySeats.setBounds(233, 278, 103, 16);
		addPlanePathPane.add(lblEconomySeats);
		
		JLabel lblFirstClassSeats = new JLabel("First Class Seats");
		lblFirstClassSeats.setBounds(233, 308, 103, 16);
		addPlanePathPane.add(lblFirstClassSeats);
		
		JButton btnAddNewPlane = new JButton("Add Plane");
		btnAddNewPlane.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addNewPlane();
			}
		});
		btnAddNewPlane.setBounds(70, 343, 137, 25);
		addPlanePathPane.add(btnAddNewPlane);
		
		textFieldCoachSeats = new JTextField();
		textFieldCoachSeats.setBounds(355, 248, 116, 22);
		addPlanePathPane.add(textFieldCoachSeats);
		textFieldCoachSeats.setColumns(10);
		
		textFieldEconSeats = new JTextField();
		textFieldEconSeats.setBounds(355, 280, 116, 22);
		addPlanePathPane.add(textFieldEconSeats);
		textFieldEconSeats.setColumns(10);
		
		textFieldFirstClassSeats = new JTextField();
		textFieldFirstClassSeats.setBounds(355, 308, 116, 22);
		addPlanePathPane.add(textFieldFirstClassSeats);
		textFieldFirstClassSeats.setColumns(10);
		
		addPlaneLabel = new Label("");
		addPlaneLabel.setBounds(12, 394, 459, 24);
		addPlanePathPane.add(addPlaneLabel);
		
	}
}
