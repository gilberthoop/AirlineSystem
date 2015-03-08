package airlineInterface;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JList;

import airline.AirlineSystem;
import airline.Flight;
import airline.FlightPath;
import airline.Passenger;
import airline.Seat;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JComboBox;

public class CustomerInterface {

	private JFrame frame;

	private JPanel searchFlightTab;
	private JTabbedPane tabbedPane;
	
	private JList allFlightList;
	private JList bookedSeatList;
	private JList reservedSeatList;
	
	private ButtonGroup bgSeatType;
	private JRadioButton rdbtnCoach;
	private JRadioButton rdbtnFirstClass;
	private JRadioButton rdbtnEconomy;
	
	private JTextField textFieldSeatQuantity;
	private JTextField textFieldUserName;
	private JTextField textFieldUserID;
	
	private JLabel labelAvailReserveSeatsFirstClass;
	private JLabel labelAvailReserveCoach;
	private JLabel labelAvailReserveEcon;
	
	private JLabel labelAvailBookSeatFirstClass;
	private JLabel labelAvailBookSeatCoach;
	private JLabel labelAvailableBookSeatEconomy;
	
	private JComboBox comboBoxFlightPathSearch;
	
	private JTextField textFieldSearchDateFinish;
	private JTextField textFieldSearchDateStart;
	private JTextField textFieldSearchString;
	
	
	private Passenger activeCustomer;
	private boolean isLoggedIn;
	private AirlineSystem as;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerInterface window = new CustomerInterface();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void login(){
		//TODO
		
		String name   = textFieldUserName.getText();
		String userID = textFieldUserID.getText();
		activeCustomer = new Passenger(name,userID);
		isLoggedIn = true;

		tabbedPane.setSelectedComponent(searchFlightTab);
		
		updateReservedSeatModel();
		updateBookedSeatModel();
		/*
		 * get the text
		 * make sure all info is filled in
		 * create a new passenger
		 * isLoggedon = true
		 */
	}
	
	private void displayMustBeLoggedIn(){
		//Must be logged in;
	}
	
	private void refreshSearch(){
		List <Flight> tFlight = updateAllFlightModel();
		tFlight = filterSearchPath(tFlight);
		tFlight = filterSearchDate(tFlight);
		tFlight = filterSearchString(tFlight);
		
		
		SetAllFlightListModel(tFlight);
	
		
	}
	
	private List<Flight> filterSearchString(List<Flight> sFlights){
		
		String search = textFieldSearchString.getText();
		List<Flight> flightMatches = new ArrayList<Flight>();
		
		if(!search.contentEquals("")){
			
			for(int i=0;i<sFlights.size();i++)
				if(sFlights.get(i).getFlightNumber().contains(search) || 
						sFlights.get(i).getPath().getDestination().contains(search) ||
						sFlights.get(i).getPath().getSource().contains(search)){
					flightMatches.add(sFlights.get(i));
				}
			return flightMatches;
		}
		
		return sFlights;
	}
	
	private List<Flight> filterSearchDate(List<Flight> sFlight){
		
		
		String endDate = textFieldSearchDateFinish.getText();
		String startDate   = textFieldSearchDateStart.getText();
		
		if(isValidDate(startDate) && isValidDate(endDate)){
			
			
			Date d1 = toDate(startDate);
			Date d2 = toDate(endDate);
			/*
			System.out.println(d1);
			System.out.println(d2);
			*/
			
			List<Flight> flightsDate = new ArrayList<Flight>();
			for(int i=0;i<sFlight.size();i++){
				if( (sFlight.get(i).getDate().after(d1)) && (sFlight.get(i).getDate().before(d2)) ){
					flightsDate.add(sFlight.get(i));
				}
			}
			return flightsDate;
		}
		//TODO
		return sFlight;
	}
	
	private boolean isValidDate(String date){
		
		String flield[] = date.split("-");
		
		if(flield.length == 3)
			return true;
		else return false;
	}
	
	private Date toDate(String stringDate){
		//long longDate = 0;
		
		//System.out.println(stringDate);
		
		String field[] = stringDate.split("-");
		int day   = Integer.parseInt(field[0]);
		int month = Integer.parseInt(field[1])-1;
		int year  = Integer.parseInt(field[2])-1900;
		
		Date tDate = new Date(year,month,day);
		
		return tDate;
		
	}
	
	private List<Flight> filterSearchPath(List<Flight> sFlight){
	
		if(!comboBoxFlightPathSearch.getModel().getSelectedItem().toString().equalsIgnoreCase("All Paths")){
			FlightPath path = (FlightPath) comboBoxFlightPathSearch.getModel().getSelectedItem();
			List <Flight> tFlight = new ArrayList<Flight>();
			int size = sFlight.size();
			for(int i=0;i<size;i++){
				Flight f = sFlight.get(i);
				if(f.getPath().equals(path)){
					tFlight.add(f);
				}
			}
			
			
			return tFlight;
			//allFlightList.setModel(m);
			
		}
		return sFlight;
			
	}
	
	private void SetAllFlightListModel(List<Flight> sFlight){
		
		DefaultListModel<Flight> m = new <Flight>DefaultListModel();
		for(int i=0;i<sFlight.size();i++){
			m.addElement(sFlight.get(i));
		}
		allFlightList.setModel(m);
		
	}
	
	private void setSelectedListFlightInfo(){
		//TODO
		/*
		 * populate the labes with the seats avail
		 */
		
		Flight tFlight;
		int fcR = 0, fcB = 0, fcT = 0, cR = 0,cB = 0,cT = 0, eR = 0, eB = 0, eT = 0;
		
		int selection = allFlightList.getSelectedIndex();
		if(selection != -1){
			//TODO move the logic into the airline class
			tFlight = (Flight) allFlightList.getModel().getElementAt(selection);
			
			String flightNo = tFlight.getFlightNumber();
			List<Seat> seats = tFlight.getSeats();
			
			for(int i=0;i<seats.size();i++){
				
				if(seats.get(i).getType() == Seat.SeatType.Coach){
					cT++;
					if(seats.get(i).isConfirmed()){
						cB++;
						cR++;
					}else if(seats.get(i).isReserved()){
						cR++;
					}
					
				}else if(seats.get(i).getType() == Seat.SeatType.Economy){
						eT++;
						if(seats.get(i).isConfirmed()){
							eB++;
							eR++;
						}else if(seats.get(i).isReserved()){
							eR++;
						}
					
				}else if(seats.get(i).getType() == Seat.SeatType.FistClass){
						fcT++;
						if(seats.get(i).isConfirmed()){
							fcB++;
							fcR++;
						}else if(seats.get(i).isReserved()){
							fcR++;
							}
						}
				
					}
				}
		
		labelAvailReserveSeatsFirstClass.setText(String.valueOf((fcT - fcR)));
		labelAvailReserveCoach.setText(String.valueOf((cT - cR)));
		labelAvailReserveEcon.setText(String.valueOf((eT - eR)));
		
		labelAvailBookSeatFirstClass.setText(String.valueOf((fcT - fcB)));
		labelAvailBookSeatCoach.setText(String.valueOf((cT - cB)));
		labelAvailableBookSeatEconomy.setText(String.valueOf((eT - eB)));
		
	}
	
	private void reserveFlight(){
		
		
		int selection = allFlightList.getSelectedIndex();
		if(selection != -1){
			//TODO move the logic into the airline class
			Flight tFlight = (Flight) allFlightList.getModel().getElementAt(selection);
			String flightNo = tFlight.getFlightNumber();
			int num = Integer.parseInt(textFieldSeatQuantity.getText());
			List<String> seatNo;
			
			if(rdbtnCoach.isSelected()){
				int avail = Integer.parseInt(labelAvailReserveCoach.getText());
				if(num <= avail){
					seatNo = as.getNextAvailableCoachSeats(flightNo, num);
					as.reserveSeat(seatNo, activeCustomer);
					updateReservedSeatModel();
				}else{
					//say not enough seats
					System.out.println("Not enough seats");
				}
				
			}else if(rdbtnEconomy.isSelected()){
				int avail = Integer.parseInt(labelAvailReserveEcon.getText());
				if(num <= avail){
					seatNo = as.getNextAvailableEconomySeats(flightNo, num);
					as.reserveSeat(seatNo, activeCustomer);
					updateReservedSeatModel();
				}else{
					//say not enough seats
					System.out.println("Not enough seats");
				}
				
			}else if(rdbtnFirstClass.isSelected()){
				int avail = Integer.parseInt(labelAvailReserveSeatsFirstClass.getText());
				if(num <= avail){
					seatNo = as.getNextAvailableFirstClassSeats(flightNo, num);
					as.reserveSeat(seatNo, activeCustomer);
					updateReservedSeatModel();
				}else{
					//say not enough seats
					System.out.println("Not enough seats");
				}
				
			}
			
		}
		
		//To update the fields
		setSelectedListFlightInfo();
		
	}
	
	private void cancelReservationdSeats(){
		
		int indx[];
		String[] seats;
		
		indx = reservedSeatList.getSelectedIndices();
		seats = new String[indx.length];
		
		for(int i=0;i<seats.length;i++){
			String seatID =
					(String) reservedSeatList.getModel().getElementAt(indx[i]);
			as.cancelReservation(seatID);
		}
		
		updateReservedSeatModel();
		setSelectedListFlightInfo();
	}
	
	private void refundSeats(){
		
		int idnx[] = bookedSeatList.getSelectedIndices();
		ArrayList<String> tickets = new ArrayList<String>();
		int total = 0;
		String msg = "Refund Ticket: ";
		
		for(int i=0;i<idnx.length;i++){
			
			String ticket = 
					(String) bookedSeatList.getModel().getElementAt(idnx[i]);
	
			msg += ticket+" ";
			total += as.refundTicketPrice(ticket);
		}
		String eol = System.getProperty("line.separator");  
		msg += eol +"Total: $"+total/100.00;
		int result = JOptionPane.showConfirmDialog(frame, msg);
		if(result == JOptionPane.YES_OPTION)
			for(int i=0;i<idnx.length;i++)
				as.refundTicket((String) bookedSeatList.getModel().getElementAt(idnx[i]));

		updateReservedSeatModel();
		updateBookedSeatModel();
		setSelectedListFlightInfo();
	}
	
	private void bookSeats(){
		
		int selection = allFlightList.getSelectedIndex();
		if(selection != -1){
			//TODO move the logic into the airline class
			Flight tFlight = (Flight) allFlightList.getModel().getElementAt(selection);
			String flightNo = tFlight.getFlightNumber();
			int num = Integer.parseInt(textFieldSeatQuantity.getText());
			List<String> seatNo;
			
			if(rdbtnCoach.isSelected()){
				int avail = Integer.parseInt(labelAvailBookSeatCoach.getText());
				if(num <= avail){
					
					seatNo = as.getNextAvailableCoachBookSeats(flightNo, num);
					int total = 0;
					String msg = "Book Seats: ";
					for(int i=0;i<seatNo.size();i++){
						msg += seatNo.get(i)+" ";
						total += as.getSeatPrice(seatNo.get(i));
					}
					String eol = System.getProperty("line.separator");  
					msg += eol +"Total: $"+total/100.00;
					int result = JOptionPane.showConfirmDialog(frame, msg);
					if(result == JOptionPane.YES_OPTION)
						as.bookSeats(seatNo, activeCustomer);
					//updateReservedSeatModel();
					
				}else{
					//say not enough seats
					System.out.println("Not enough seats");
				}
				
			}else if(rdbtnEconomy.isSelected()){
				int avail = Integer.parseInt(labelAvailableBookSeatEconomy.getText());
				if(num <= avail){
					seatNo = as.getNextAvailableEconomyBookSeats(flightNo, num);
					int total = 0;
					String msg = "Book Seats: ";
					for(int i=0;i<seatNo.size();i++){
						msg += seatNo.get(i)+" ";
						total += as.getSeatPrice(seatNo.get(i));
					}
					String eol = System.getProperty("line.separator");  
					msg += eol +"Total: $"+total/100.00;
					int result = JOptionPane.showConfirmDialog(frame, msg);
					if(result == JOptionPane.YES_OPTION)
						as.bookSeats(seatNo, activeCustomer);
					//updateReservedSeatModel();
				}else{
					//say not enough seats
					System.out.println("Not enough seats");
				}
				
			}else if(rdbtnFirstClass.isSelected()){
				int avail = Integer.parseInt(labelAvailBookSeatFirstClass.getText());
				if(num <= avail){
					seatNo = as.getNextAvailableFirstClassBookSeats(flightNo, num);
					int total = 0;
					String msg = "Book Seats: ";
					for(int i=0;i<seatNo.size();i++){
						msg += seatNo.get(i)+" ";
						total += as.getSeatPrice(seatNo.get(i));
					}
					String eol = System.getProperty("line.separator");  
					msg += eol +"Total: $"+total/100.00;
					int result = JOptionPane.showConfirmDialog(frame, msg);
					if(result == JOptionPane.YES_OPTION)
						as.bookSeats(seatNo, activeCustomer);
					//updateReservedSeatModel();
				}else{
					//say not enough seats
					System.out.println("Not enough seats");
				}
				
			}
			
		}
		
		//To update the fields
		updateBookedSeatModel();
		updateReservedSeatModel();
		setSelectedListFlightInfo();
		
		
	}
	
	private void bookReservationdSeats(){
		
		int indx[];
		List<String> seats;
		
		indx = reservedSeatList.getSelectedIndices();
		seats = new ArrayList<String>();
		
		int total = 0;
		String msg = "Book Seats: ";
		
		for(int i=0;i<indx.length;i++){
			
			String seatID =
					(String) reservedSeatList.getModel().getElementAt(indx[i]);
			
			seats.add(seatID);
			
			msg += seatID+" ";
			total += as.getSeatPrice(seatID);
	
		}
		
		if(indx.length != 0){		
			String eol = System.getProperty("line.separator");  
			msg += eol +"Total: $"+total/100.00;
			int result = JOptionPane.showConfirmDialog(frame, msg);
			if(result == JOptionPane.YES_OPTION)
				as.bookSeats(seats, activeCustomer);
		}
		
		updateReservedSeatModel();
		updateBookedSeatModel();
		setSelectedListFlightInfo();
	}
	
	
	private void setPathsComboBox(){
		
		DefaultComboBoxModel m = new DefaultComboBoxModel();
		
		List<FlightPath> fp = as.getAllFlightPaths();
		m.addElement("All Paths");
		for(int i=0;i<fp.size();i++){
			m.addElement(fp.get(i));
		}
		comboBoxFlightPathSearch.setModel(m);
	}
	
	private void updateReservedSeatModel(){
		DefaultListModel m = new DefaultListModel();

		List<String> seatIDs = as.getPassangerReservedSeats(activeCustomer);
		
		for(int i=0;i<seatIDs.size();i++){
			
			m.addElement(seatIDs.get(i));
		}
		
		reservedSeatList.setModel(m);
	}
	
	private List<Flight> updateAllFlightModel(){
		DefaultListModel<Flight> m = new <Flight>DefaultListModel();
		/*
		List<Flight> tFlights = as.getAllFlights();
		for(int i=0;i<tFlights.size();i++){
			m.addElement(tFlights.get(i));
		}
		allFlightList.setModel(m);
		*/
		return as.getAllFlights();
	}
	private void updateBookedSeatModel(){
		DefaultListModel m = new DefaultListModel();
		List<String> seatIDs = as.getTickets(activeCustomer);
		
		for(int i=0;i<seatIDs.size();i++){
			m.addElement(seatIDs.get(i));
		}
		bookedSeatList.setModel(m);
	}
	
	
	/**
	 * 
	 * @return the selected seatIDs from the reserved seat list
	 */
	private List<String> getSelectedReservedSeats(){
		//TODO
		return null;
	}
	
	/**
	 * 
	 * @return the selected tickeIDs from the booked seat list
	 */
	private List<String> getSelectedBookedSeats(){
		//TODO
		return null;
	}
	
	/**
	 * 
	 * @return the selected flightNo from the AllFLightsList seat list
	 */
	private List<String> getSelectedFlightNumbers(){
		//TODO
		return null;
	}
	
	

	/**
	 * Create the application.
	 */
	public CustomerInterface() {
		as = new AirlineSystem();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		bgSeatType = new ButtonGroup();
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JPanel loginTab = new JPanel();
		tabbedPane.addTab("Login", null, loginTab, null);
		loginTab.setLayout(null);
		
		JLabel lblUserName = new JLabel("User Name:");
		lblUserName.setBounds(68, 79, 78, 16);
		loginTab.add(lblUserName);
		
		textFieldUserName = new JTextField();
		textFieldUserName.setBounds(158, 76, 116, 22);
		loginTab.add(textFieldUserName);
		textFieldUserName.setColumns(10);
		
		JLabel lblUserId = new JLabel("User ID:");
		lblUserId.setBounds(68, 131, 56, 16);
		loginTab.add(lblUserId);
		
		textFieldUserID = new JTextField();
		textFieldUserID.setBounds(158, 128, 116, 22);
		loginTab.add(textFieldUserID);
		textFieldUserID.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				login();
				//Set the next pane
			}
		});
		btnLogin.setBounds(158, 199, 97, 25);
		loginTab.add(btnLogin);
		
		searchFlightTab = new JPanel();
		tabbedPane.addTab("Flights", null, searchFlightTab, null);
		searchFlightTab.setLayout(null);
		
		
		//****************************************************************************
		
		JScrollPane scrollPaneFlights = new JScrollPane();
		scrollPaneFlights.setBounds(12, 40, 429, 120);
		searchFlightTab.add(scrollPaneFlights);
		
		allFlightList = new JList();
		allFlightList.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				setSelectedListFlightInfo();
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				
				
			}
		});
		scrollPaneFlights.setViewportView(allFlightList);
		
		JButton btnReserveFlight = new JButton("Reserve");
		btnReserveFlight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(isLoggedIn)
					reserveFlight();
				else displayMustBeLoggedIn();
			}
		});
		btnReserveFlight.setBounds(12, 326, 97, 25);
		searchFlightTab.add(btnReserveFlight);
		
		JButton btnBookFlight = new JButton("Book");
		btnBookFlight.setBounds(123, 326, 97, 25);
		btnBookFlight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(isLoggedIn)
					bookSeats();
				else displayMustBeLoggedIn();
				
			}
		});
		searchFlightTab.add(btnBookFlight);
		
		rdbtnFirstClass = new JRadioButton("First Class");
		rdbtnFirstClass.setBounds(12, 227, 127, 25);
		searchFlightTab.add(rdbtnFirstClass);
		
		rdbtnEconomy = new JRadioButton("Economy");
		rdbtnEconomy.setBounds(12, 251, 127, 25);
		searchFlightTab.add(rdbtnEconomy);
		
		rdbtnCoach = new JRadioButton("Coach");
		rdbtnCoach.setBounds(12, 277, 127, 25);
		searchFlightTab.add(rdbtnCoach);
		bgSeatType.add(rdbtnEconomy);
		bgSeatType.add(rdbtnFirstClass);
		bgSeatType.add(rdbtnCoach);
		
		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setBounds(147, 231, 59, 16);
		searchFlightTab.add(lblQuantity);
		
		textFieldSeatQuantity = new JTextField();
		textFieldSeatQuantity.setBounds(147, 260, 59, 22);
		searchFlightTab.add(textFieldSeatQuantity);
		textFieldSeatQuantity.setColumns(10);
		
		JLabel lblAvailableFirstClass = new JLabel("First Class");
		lblAvailableFirstClass.setBounds(273, 197, 78, 16);
		searchFlightTab.add(lblAvailableFirstClass);
		
		JLabel lblNewLabel = new JLabel("Available For Reservation");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(273, 173, 168, 16);
		searchFlightTab.add(lblNewLabel);
		
		labelAvailReserveSeatsFirstClass = new JLabel("0");
		labelAvailReserveSeatsFirstClass.setBounds(363, 197, 56, 16);
		searchFlightTab.add(labelAvailReserveSeatsFirstClass);
		
		JLabel lblCoach = new JLabel("Coach");
		lblCoach.setBounds(273, 223, 56, 16);
		searchFlightTab.add(lblCoach);
		
		labelAvailReserveCoach = new JLabel("0");
		labelAvailReserveCoach.setBounds(363, 223, 56, 16);
		searchFlightTab.add(labelAvailReserveCoach);
		
		JLabel lblEcon = new JLabel("Economy");
		lblEcon.setBounds(273, 252, 56, 16);
		searchFlightTab.add(lblEcon);
		
		labelAvailReserveEcon = new JLabel("0");
		labelAvailReserveEcon.setBounds(363, 252, 56, 16);
		searchFlightTab.add(labelAvailReserveEcon);
		
		JLabel lblNewLabel_1 = new JLabel("Available For Booking");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setBounds(273, 281, 168, 16);
		searchFlightTab.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("First Class");
		lblNewLabel_2.setBounds(273, 310, 78, 16);
		searchFlightTab.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Coach");
		lblNewLabel_3.setBounds(273, 339, 56, 16);
		searchFlightTab.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Economy");
		lblNewLabel_4.setBounds(273, 370, 56, 16);
		searchFlightTab.add(lblNewLabel_4);
		
		labelAvailBookSeatFirstClass = new JLabel("0");
		labelAvailBookSeatFirstClass.setBounds(363, 310, 56, 16);
		searchFlightTab.add(labelAvailBookSeatFirstClass);
		
		labelAvailBookSeatCoach = new JLabel("0");
		labelAvailBookSeatCoach.setBounds(363, 339, 56, 16);
		searchFlightTab.add(labelAvailBookSeatCoach);
		
		labelAvailableBookSeatEconomy = new JLabel("0");
		labelAvailableBookSeatEconomy.setBounds(363, 370, 56, 16);
		searchFlightTab.add(labelAvailableBookSeatEconomy);
		
		comboBoxFlightPathSearch = new JComboBox();
		comboBoxFlightPathSearch.setBounds(12, 13, 97, 22);
		comboBoxFlightPathSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshSearch();
			}
		});
		searchFlightTab.add(comboBoxFlightPathSearch);
		
		textFieldSearchDateFinish = new JTextField();
		textFieldSearchDateFinish.setBounds(363, 13, 78, 22);
		searchFlightTab.add(textFieldSearchDateFinish);
		textFieldSearchDateFinish.setColumns(10);
		
		JLabel lblTo = new JLabel("to");
		lblTo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTo.setBounds(331, 16, 20, 16);
		searchFlightTab.add(lblTo);
		
		textFieldSearchDateStart = new JTextField();
		textFieldSearchDateStart.setBounds(251, 13, 78, 22);
		searchFlightTab.add(textFieldSearchDateStart);
		textFieldSearchDateStart.setColumns(10);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setBounds(217, 16, 37, 16);
		searchFlightTab.add(lblDate);
		
		textFieldSearchString = new JTextField();
		textFieldSearchString.setBounds(22, 170, 116, 22);
		searchFlightTab.add(textFieldSearchString);
		textFieldSearchString.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshSearch();
			}
		});
		btnSearch.setBounds(147, 169, 97, 25);
		searchFlightTab.add(btnSearch);
		
		JPanel bookedFlightsTab = new JPanel();
		tabbedPane.addTab("Booked Flights", null, bookedFlightsTab, null);
		frame.getContentPane().setLayout(groupLayout);
		
		JPanel reservedFlightsTab = new JPanel();
		tabbedPane.addTab("Reserved Flights", null, reservedFlightsTab, null);
		frame.getContentPane().setLayout(groupLayout);
		
		
		
		//****************************************************************************
		
		JScrollPane scrollPaneReservedSeats = new JScrollPane();
		scrollPaneReservedSeats.setBounds(12, 98, 429, 120);
		reservedFlightsTab.setLayout(null);
		reservedFlightsTab.add(scrollPaneReservedSeats);
		
		reservedSeatList = new JList();
		scrollPaneReservedSeats.setViewportView(reservedSeatList);
		
		JButton btnBook = new JButton("Book");
		btnBook.setBounds(12, 231, 97, 25);
		btnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(isLoggedIn)
					bookReservationdSeats();
				else displayMustBeLoggedIn();
				
				//TODO
			}
		});
		reservedFlightsTab.add(btnBook);
		
		JButton btnCancelReservation = new JButton("Cancel Reservation");
		btnCancelReservation.setBounds(121, 231, 153, 25);
		btnCancelReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				 if(isLoggedIn)
					 cancelReservationdSeats();
				else displayMustBeLoggedIn();
				 
				//TODO
			}
		});
		reservedFlightsTab.add(btnCancelReservation);
			
		
		//****************************************************************************
		
		JScrollPane scrollPaneBookedSeats = new JScrollPane();
		scrollPaneBookedSeats.setBounds(12, 98, 429, 120);
		bookedFlightsTab.setLayout(null);
		bookedFlightsTab.add(scrollPaneBookedSeats);
		
		bookedSeatList = new JList();
		scrollPaneBookedSeats.setViewportView(bookedSeatList);
		
		
		
		JButton btnRefund = new JButton("Refund");
		btnRefund.setBounds(12, 231, 97, 25);
		btnRefund.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				 if(isLoggedIn)
					refundSeats();
				 else displayMustBeLoggedIn();
			
				//TODO
			}
		});
		bookedFlightsTab.add(btnRefund);
		
		
		
		/*
		 * populate all the lists:
		 */
		setPathsComboBox();
		refreshSearch();
		
		
		
	
		
	}
}
