package airlineInterface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import java.awt.CardLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextField;

import airline.AirlineSystem;
import airline.Booking;
import airline.Flight;
import airline.FlightPath;
import airline.Passenger;
import airline.Plane;
import airline.Seat;
import airline.Ticket;


public class PassengerInterface {

	private JFrame jfrm;
	private JPanel bookPanel;
	private JPanel passInfoPanel;
	private JPanel seatsPanel1;
	private JPanel passVerifyPanel;
	private JPanel seatsPanel2;
	private JPanel paymentPanel;
	
	private AirlineSystem airline;
	Passenger passenger;
	private String searchPaths = "All Flight Paths";
	private String[] planes;
	private List<String> seatNo;
	private List<String> tags;
	JComboBox<FlightPath> cbbPaths;
	private JList list;
	private String bookFlightInfo;
	private String flightNumber;
	List<Flight> f;
	JComboBox<String> cbbSeats1;
	JComboBox<String> cbbSeats2;
	JComboBox<String> cbbSeats3;
	boolean many;
	boolean found;
	boolean first;
	boolean coach;
	boolean economy;
	String seatPrice;
	JLabel lblNewLabel;
	float price = 0;
	float refund = 0;
	List<String> ticket;
	String totalPrice;
	String refundPrice;

	
	// Create a list of numbers for seats available
	Integer passengerNumber[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
	
		
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PassengerInterface window = new PassengerInterface();
					window.jfrm.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	/**
	 * Create the application.
	 */
	public PassengerInterface() {
		airline = new AirlineSystem();
		loadAirlineInfo();
		initialize();
	}
	
	
	/**
	 * Loads airline information
	 */
	private void loadAirlineInfo() {
		List<Plane> p = airline.getAllPlanes();
		planes = new String[p.size()+1];
		planes[0] = "All Planes";
		for(int i=0;i<p.size();i++){
			planes[i+1] = p.get(i).toString();
		}
		
	}
	
	
	/**
	 ** When a client selects a flight path from the box,
	 *	the list provides the available flights for it.
	 */
	public void performSearch() {
		DefaultListModel listModel;
		listModel = new DefaultListModel();
		System.out.println("Clicked");
		
		if(searchPaths != "All Flight Paths"){
			FlightPath path = (FlightPath) cbbPaths.getModel().getSelectedItem();
			List<Flight> f = airline.getFlights(path);
			for(int i=0;i<f.size();i++)
				listModel.addElement(f.get(i));
			
			list.setModel(listModel);
			System.out.println("chaning list: "+path);
		} else{
			List<Flight> f = airline.getAllFlights();
			for(int i=0;i<f.size();i++)
				listModel.addElement(f.get(i));
		
			list.setModel(listModel);	
		}	
	}
	
	
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		jfrm = new JFrame();
		jfrm.setBounds(100, 100, 711, 472);
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jfrm.getContentPane().setLayout(new CardLayout(0, 0));
		
		// 1st Panel: bookPanel
		final JPanel bookPanel = new JPanel();
		jfrm.getContentPane().add(bookPanel, "name_10263173430284");
		bookPanel.setLayout(null);
		bookPanel.setVisible(true);
		
		// 2nd Panel: passInfoPanel
		final JPanel passInfoPanel = new JPanel();
		jfrm.getContentPane().add(passInfoPanel, "name_10311421652870");
		passInfoPanel.setLayout(null);
		passInfoPanel.setVisible(false);
		
		// 3rd Panel: seatsPanel1
		final JPanel seatsPanel1 = new JPanel();
		jfrm.getContentPane().add(seatsPanel1, "name_10314230259638");
		seatsPanel1.setLayout(null);
		seatsPanel1.setVisible(false);
		
		// 4th Panel: passVerifyPanel
		final JPanel passVerifyPanel = new JPanel();
		jfrm.getContentPane().add(passVerifyPanel, "name_12401425143585");
		passVerifyPanel.setLayout(null);
		passVerifyPanel.setVisible(false);
		
		// 5th Panel: seatsPanel2
		final JPanel seatsPanel2 = new JPanel();
		jfrm.getContentPane().add(seatsPanel2, "name_14475425906523");
		seatsPanel2.setLayout(null);
		seatsPanel2.setVisible(false);
		final JButton cancelBookButton = new JButton("Cancel Booking");
		final JButton cancelResButton = new JButton("Cancel Reservation");
		
		// 6th Panel: paymentPanel
		tags = new ArrayList<String>();
		final JPanel paymentPanel = new JPanel();
		jfrm.getContentPane().add(paymentPanel, "name_26779370232414");
		paymentPanel.setLayout(null);
		paymentPanel.setVisible(false);
		final JButton backButton2 = new JButton("BACK");
		
		
		
		/**TODO
		 *  1st Panel: bookPanel
		 */
		JLabel labelFrom = new JLabel("Choose your flight path:");
		labelFrom.setFont(new Font("Arial", Font.BOLD, 12));
		labelFrom.setForeground(Color.BLACK);
		labelFrom.setBounds(44, 39, 164, 19);
		bookPanel.add(labelFrom);
		
		
		cbbPaths = new JComboBox<FlightPath>();
		cbbPaths.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				searchPaths = cbbPaths.getModel().getSelectedItem().toString();
				performSearch();
			}
		});
		DefaultComboBoxModel m = new DefaultComboBoxModel();
		List<FlightPath> paths = airline.getALlPaths();
		m.addElement("All Flight Paths");
		for(int i=0;i<paths.size();i++){
			m.addElement(paths.get(i));
		}
		cbbPaths.setModel(m);
		cbbPaths.setBounds(44, 57, 200, 31);
		bookPanel.add(cbbPaths);
		
		JLabel passengerLabel = new JLabel("Passengers");
		passengerLabel.setFont(new Font("Arial", passengerLabel.getFont().getStyle() | Font.BOLD, passengerLabel.getFont().getSize() + 1));
		passengerLabel.setBounds(379, 28, 82, 14);
		bookPanel.add(passengerLabel);
		
		final JButton bookButton = new JButton("BOOK NOW!");
		bookButton.setEnabled(false);
		
		final JComboBox allPassengers = new JComboBox(passengerNumber);
		allPassengers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				bookButton.setEnabled(true);
			}
		});
		allPassengers.setBounds(446, 46, 46, 19);
		bookPanel.add(allPassengers);
		
		JLabel cabinType = new JLabel("Cabin Type");
		cabinType.setFont(new Font("Arial", cabinType.getFont().getStyle() | Font.BOLD, cabinType.getFont().getSize() + 1));
		cabinType.setBounds(543, 28, 82, 14);
		bookPanel.add(cabinType);
		
		final JRadioButton firstClass = new JRadioButton("First");
		firstClass.setFont(new Font("Arial", firstClass.getFont().getStyle() | Font.BOLD, firstClass.getFont().getSize() + 1));
		firstClass.setBounds(543, 49, 109, 23);
		bookPanel.add(firstClass);
		
		final JRadioButton coachClass = new JRadioButton("Coach");
		coachClass.setFont(new Font("Arial", coachClass.getFont().getStyle() | Font.BOLD, coachClass.getFont().getSize() + 1));
		coachClass.setBounds(543, 75, 109, 23);
		bookPanel.add(coachClass);
		
		final JRadioButton economyClass = new JRadioButton("Economy");
		economyClass.setFont(new Font("Arial", economyClass.getFont().getStyle() | Font.BOLD, economyClass.getFont().getSize() + 1));
		economyClass.setBounds(543, 101, 109, 23);
		bookPanel.add(economyClass);
		
		final ButtonGroup bg = new ButtonGroup();
		bg.add(firstClass);
		bg.add(coachClass);
		bg.add(economyClass);
	
		final JButton btnReviewTicket = new JButton("REVIEW BOOK");
		btnReviewTicket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				passVerifyPanel.setVisible(true);
				bookPanel.setVisible(false);
			}
		});
		btnReviewTicket.setFont(new Font("Arial", btnReviewTicket.getFont().getStyle() | Font.BOLD, btnReviewTicket.getFont().getSize() + 1));
		btnReviewTicket.setBounds(407, 280, 161, 60);
		btnReviewTicket.setEnabled(true);
		bookPanel.add(btnReviewTicket);
		
		JLabel   availFlights = new JLabel("Choose Available Flights:");
		availFlights.setFont(new Font("Arial", availFlights.getFont().getStyle() | Font.BOLD, availFlights.getFont().getSize() + 1));
		availFlights.setBounds(44, 115, 174, 25);
		bookPanel.add(availFlights);
			
		final DefaultListModel<Flight> listModel;
		listModel = new DefaultListModel<Flight>();
		
		f = airline.getAllFlights();
		for(int i=0;i<f.size();i++)
			listModel.addElement(f.get(i));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 145, 489, 119);
		bookPanel.add(scrollPane);
		
		list = new JList();
		scrollPane.setViewportView(list);
		list.setModel(listModel);
		
		cbbSeats1 = new JComboBox<String>();
		cbbSeats2 = new JComboBox<String>();
		cbbSeats3 = new JComboBox<String>();
		
	
		bookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Flight selectedFlight = (Flight) list.getModel().getElementAt(list.getSelectedIndex());
				flightNumber = selectedFlight.getFlightNumber();	
				String pass = allPassengers.getSelectedItem().toString();
				String seatClass = null;
				
				if (firstClass.isSelected()) { 
					first = true;
					seatNo = new ArrayList<String>();
					seatNo = airline.getNextAvailableFirstClassSeats(flightNumber, allPassengers.getSelectedIndex());
					System.out.println(seatNo);
					
					// Create the combo box containing the booked seats for first class
					cbbSeats2.setVisible(false);
					cbbSeats3.setVisible(false);
					cbbSeats1.setVisible(true);
					for (int i=0; i<seatNo.size(); i++) {
						cbbSeats1.addItem(seatNo.get(i));
					}
					cbbSeats1.setBounds(112, 51, 386, 28);
					seatsPanel2.add(cbbSeats1);

				}
				if (coachClass.isSelected()) { 
					coach = true;
					seatNo = new ArrayList<String>();
					seatNo = airline.getNextAvailableCoachSeats(flightNumber, allPassengers.getSelectedIndex());
					System.out.println(seatNo);
					
					// Create the combo box containing the booked seats for coach class
					cbbSeats1.setVisible(false);
					cbbSeats3.setVisible(false);
					cbbSeats2.setVisible(true);
					for (int i=0; i<seatNo.size(); i++) {
						cbbSeats2.addItem(seatNo.get(i));
					}
					cbbSeats2.setBounds(112, 51, 386, 28);
					seatsPanel2.add(cbbSeats2);
				}
				if (economyClass.isSelected()) { 
					economy = true;
					seatNo = new ArrayList<String>();
					seatNo = airline.getNextAvailableEconomySeats(flightNumber, allPassengers.getSelectedIndex());
					System.out.println(seatNo);
					
					// Create the combo box containing the booked seats for economy class
					cbbSeats1.setVisible(false);
					cbbSeats2.setVisible(false);
					cbbSeats3.setVisible(true);
					for (int i=0; i<seatNo.size(); i++) {
						cbbSeats3.addItem(seatNo.get(i));
					}
					cbbSeats3.setBounds(112, 51, 386, 28);
					seatsPanel2.add(cbbSeats3);
				}
				passInfoPanel.setVisible(true);
				bookPanel.setVisible(false);
				// Disable all selection components
				cbbPaths.setEnabled(false);
				list.setEnabled(false);
				allPassengers.setEnabled(false);
				firstClass.setEnabled(false);
				economyClass.setEnabled(false);
				coachClass.setEnabled(false);
				bookButton.setEnabled(false);
			}
		});
		bookButton.setFont(new Font("Arial", bookButton.getFont().getStyle() | Font.BOLD, bookButton.getFont().getSize() + 1));
		bookButton.setBounds(165, 280, 164, 60);
		bookPanel.add(bookButton);	
		
	
		
		
		
		/**TODO
		 *  2nd Panel: passInfoPanel
		 */
		JLabel lblPassenger = new JLabel("Passenger Information");
		lblPassenger.setFont(new Font("Arial", lblPassenger.getFont().getStyle() | Font.BOLD, lblPassenger.getFont().getSize() + 9));
		lblPassenger.setBounds(213, 54, 240, 35);
		passInfoPanel.add(lblPassenger);
		
		final JTextField tfName = new JTextField();
		tfName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tfName.setBounds(122, 118, 180, 30);
		passInfoPanel.add(tfName);
		tfName.setColumns(10);
		
		final JTextField tfUserID = new JTextField();
		tfUserID.setBounds(122, 177, 190, 30);
		passInfoPanel.add(tfUserID);
		tfUserID.setColumns(10);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Arial", lblName.getFont().getStyle(), lblName.getFont().getSize() + 3));
		lblName.setBounds(38, 124, 46, 14);
		passInfoPanel.add(lblName);
		
		JLabel lblUserID = new JLabel("User ID");
		lblUserID.setFont(new Font("Arial", lblUserID.getFont().getStyle(), lblUserID.getFont().getSize() + 3));
		lblUserID.setBounds(38, 179, 63, 22);
		passInfoPanel.add(lblUserID);
		
		final JButton seatsButton = new JButton("NEXT");
		seatsButton.setEnabled(false);
		seatsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				seatsPanel1.setVisible(true);
				passInfoPanel.setVisible(false);
				seatsButton.setEnabled(false);
			}
		});
		seatsButton.setFont(new Font("Arial", seatsButton.getFont().getStyle() | Font.BOLD, seatsButton.getFont().getSize() + 5));
		seatsButton.setBounds(561, 354, 100, 35);
		passInfoPanel.add(seatsButton);
		
		final JLabel infoLabel = new JLabel("Passenger information will appear here");
		infoLabel.setFont(new Font("Arial", infoLabel.getFont().getStyle() | Font.BOLD, infoLabel.getFont().getSize() + 1));
		infoLabel.setBounds(21, 271, 530, 41);
		passInfoPanel.add(infoLabel);
		
		
		/**
		 * Register the passenger name and ID
		 */
		JButton submitButton = new JButton("SUBMIT");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				infoLabel.setText(tfName.getText() +", " + tfUserID.getText());
				passenger = new Passenger(tfName.getText(), tfUserID.getText());
				System.out.println(passenger.toString());
				seatsButton.setEnabled(true);
			}
		});
		submitButton.setFont(new Font("Arial", submitButton.getFont().getStyle(), submitButton.getFont().getSize() + 1));
		submitButton.setBounds(299, 237, 89, 23);
		passInfoPanel.add(submitButton);
		
		
		
		
		
		/**TODO
		 * 3rd Panel: seatsPanel1
		 */
		JLabel lblOr = new JLabel("Choose your action");
		lblOr.setFont(new Font("Arial", lblOr.getFont().getStyle() | Font.BOLD, lblOr.getFont().getSize() + 3));
		lblOr.setBounds(264, 69, 149, 28);
		seatsPanel1.add(lblOr);
		

		final JLabel responseLabel = new JLabel("");
		responseLabel.setFont(new Font("Arial", responseLabel.getFont().getStyle(), responseLabel.getFont().getSize() + 1));
		responseLabel.setBounds(50, 311, 500, 41);
		seatsPanel1.add(responseLabel);
		
		
		final JButton mainBtn = new JButton("Main");
		mainBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				seatsPanel1.setVisible(false);
				bookPanel.setVisible(true);
				btnReviewTicket.setEnabled(true);
			}
		});
		mainBtn.setBounds(162, 229, 89, 23);
		mainBtn.setEnabled(false);
		seatsPanel1.add(mainBtn);
		
		
		/** 
		 * reservedSeat (Airline)
		 * reservedSeat(String seatID, Passenger p)
		 */
		final JButton reserveButton = new JButton("Reserve");
		reserveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {	
				airline.reserveSeat(seatNo, passenger);
				System.out.println(seatNo); 
				responseLabel.setText("You reserved flight-seat number: " + seatNo);
				
				mainBtn.setEnabled(true);
				cancelBookButton.setEnabled(false);
			}
		});
		reserveButton.setFont(new Font("Arial", reserveButton.getFont().getStyle() | Font.BOLD, reserveButton.getFont().getSize() + 3));
		reserveButton.setBounds(136, 151, 155, 56);
		seatsPanel1.add(reserveButton);
		
		
		JButton confirmButton = new JButton("Confirm and Pay");
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				many = true;
				backButton2.setEnabled(false);
				paymentPanel.setVisible(true);
				seatsPanel1.setVisible(false);
				cancelBookButton.setEnabled(false);
				cancelResButton.setEnabled(true);
				// Get the ticket price for all seats
				if (seatNo != null) {
					for (int i=0; i<seatNo.size(); i++) {
						price += (airline.getSeatPrice(seatNo.get(i)) / 100.0);
						System.out.println((airline.getSeatPrice(seatNo.get(i))));
					}
					totalPrice = String.valueOf(price);
					lblNewLabel.setText("Your total ticket price is: " + totalPrice);
				}
			}
		});
		confirmButton.setFont(new Font("Arial", confirmButton.getFont().getStyle() | Font.BOLD, confirmButton.getFont().getSize() + 3));
		confirmButton.setBounds(403, 151, 155, 56);
		seatsPanel1.add(confirmButton);
		
		
		
		
		
		
		
		/**
		 * TODO
		 * 4th Panel: passVerifyPanel
		 */
		JLabel lblPleaseVerifyYour = new JLabel("Please verify your information");
		lblPleaseVerifyYour.setFont(new Font("Arial", lblPleaseVerifyYour.getFont().getStyle() | Font.BOLD, lblPleaseVerifyYour.getFont().getSize() + 5));
		lblPleaseVerifyYour.setBounds(228, 46, 246, 36);
		passVerifyPanel.add(lblPleaseVerifyYour);
		
		final JLabel lblName_1 = new JLabel("Name");
		lblName_1.setFont(new Font("Arial", lblName_1.getFont().getStyle() | Font.BOLD, lblName_1.getFont().getSize() + 3));
		lblName_1.setBounds(180, 135, 46, 25);
		passVerifyPanel.add(lblName_1);
		
		final JTextField tfVerifyName = new JTextField();
		tfVerifyName.setBounds(250, 136, 207, 25);
		passVerifyPanel.add(tfVerifyName);
		tfVerifyName.setColumns(10);
		
		JLabel lblSeatId = new JLabel("User ID");
		lblSeatId.setFont(new Font("Arial", lblSeatId.getFont().getStyle() | Font.BOLD, lblSeatId.getFont().getSize() + 3));
		lblSeatId.setBounds(178, 196, 62, 25);
		passVerifyPanel.add(lblSeatId);
		
		final JTextField tfVerifyID = new JTextField();
		tfVerifyID.setBounds(250, 197, 154, 25);
		passVerifyPanel.add(tfVerifyID);
		tfVerifyID.setColumns(10);
		
		final JLabel alertLabel = new JLabel("Enter your credentials");
		alertLabel.setBounds(198, 338, 277, 36);
		passVerifyPanel.add(alertLabel);
		
		JButton btnSubmit = new JButton("SUBMIT");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				seatsPanel2.setVisible(true);
				passVerifyPanel.setVisible(false);
//				passenger = new Passenger(tfVerifyName.getText(), tfVerifyID.getText());
//				if (passenger.getName()=="w" && passenger.getID()=="w") {
//					seatsPanel2.setVisible(true);
//					passVerifyPanel.setVisible(false);
//				}
//				else { 
//					alertLabel.setText("Incorrect! Try again"); 
//					System.out.println(passenger);
//				}
			}
		});
		btnSubmit.setFont(new Font("Arial", btnSubmit.getFont().getStyle() | Font.BOLD, btnSubmit.getFont().getSize() + 3));
		btnSubmit.setBounds(271, 260, 133, 36);
		passVerifyPanel.add(btnSubmit);
		
		
		
		
		
		
		
		
		/**
		 * TODO
		 * 5th Panel: seatsPanel2
		 */
		final JLabel lblRefurndinfo = new JLabel();
		lblRefurndinfo.setBounds(150, 280, 500, 40);
		seatsPanel2.add(lblRefurndinfo);
		
		final JLabel lblReserveInfo = new JLabel();
		lblReserveInfo.setBounds(150, 310, 500, 40);
		seatsPanel2.add(lblReserveInfo);
		
		
		/**
		 * refundTicket(String tickedID)
		 * cancel a booked seat and get a refund
		 */
		cancelBookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (first) {
					String item = (String)cbbSeats1.getSelectedItem();
					ticket = airline.getTickets(passenger);
					refund = airline.refundTicket(ticket.get(cbbSeats1.getSelectedIndex()));
					refund /= 100.0;
					System.out.println(refund);
					System.out.println("seat index: " +cbbSeats1.getSelectedIndex());
					System.out.println("ticket: " +ticket.get(cbbSeats1.getSelectedIndex()));
					refundPrice = String.valueOf(refund);
					lblRefurndinfo.setText("You cancelled seat: " + item + " and total refund is: " + refundPrice);
					cbbSeats1.removeItem(cbbSeats1.getSelectedItem());
				}
				if (coach) {
					String item = (String)cbbSeats2.getSelectedItem();
					ticket = airline.getTickets(passenger);
					refund = airline.refundTicket(ticket.get(cbbSeats2.getSelectedIndex()));
					refund /= 100.0;
					System.out.println(refund);
					refundPrice = String.valueOf(refund);
					lblRefurndinfo.setText("You cancelled seat: " + item + " and total refund is: " + refundPrice);
					cbbSeats2.removeItem(cbbSeats2.getSelectedItem());
				}
				if (economy) {
					String item = (String)cbbSeats3.getSelectedItem();
					ticket = airline.getTickets(passenger);
					refund = airline.refundTicket(ticket.get(cbbSeats3.getSelectedIndex()));
					refund /= 100.0;
					System.out.println(refund);
					refundPrice = String.valueOf(refund);
					lblRefurndinfo.setText("You cancelled seat: " + item + " and total refund is: " + refundPrice);
					cbbSeats3.removeItem(cbbSeats3.getSelectedItem());
				}
			}
		});
		cancelBookButton.setFont(new Font("Arial", cancelBookButton.getFont().getStyle() | Font.BOLD, cancelBookButton.getFont().getSize() + 1));
		cancelBookButton.setBounds(60, 207, 143, 55);
		seatsPanel2.add(cancelBookButton);
		
		/**
		 * canceReservation(String seatNo)
		 * cancel a reservation
		 */
		cancelResButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (first) {
					String item = (String)cbbSeats1.getSelectedItem();
					airline.cancelReservation(seatNo.get(cbbSeats1.getSelectedIndex()));
					cbbSeats1.removeItem(cbbSeats1.getSelectedItem());
					lblReserveInfo.setText("You cancelled seat: " + item);
				}
				if (coach) {
					String item = (String)cbbSeats2.getSelectedItem();
					airline.cancelReservation(seatNo.get(cbbSeats2.getSelectedIndex()));
					cbbSeats2.removeItem(cbbSeats2.getSelectedItem());
					lblReserveInfo.setText("You cancelled seat: " + item);
				}
				if (economy) {
					String item = (String)cbbSeats3.getSelectedItem();
					airline.cancelReservation(seatNo.get(cbbSeats3.getSelectedIndex()));
					cbbSeats3.removeItem(cbbSeats3.getSelectedItem());
					lblReserveInfo.setText("You cancelled seat: " + item);
				}
			}
		});
		cancelResButton.setFont(new Font("Arial", cancelResButton.getFont().getStyle() | Font.BOLD, cancelResButton.getFont().getSize() + 1));
		cancelResButton.setBounds(250, 207, 160, 55);
		seatsPanel2.add(cancelResButton);
		
		JLabel lblChooseYourAction = new JLabel("Choose your action");
		lblChooseYourAction.setFont(new Font("Arial", lblChooseYourAction.getFont().getStyle() | Font.BOLD, lblChooseYourAction.getFont().getSize() + 5));
		lblChooseYourAction.setBounds(237, 162, 176, 28);
		seatsPanel2.add(lblChooseYourAction);
		
		final JButton btnConfirmAndPay = new JButton("Confirm and Pay");
		btnConfirmAndPay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				paymentPanel.setVisible(true);
				seatsPanel2.setVisible(false);
//				ticket = airline.getTickets(passenger);
				
				if (seatNo != null) {
					if (first) {
						price = airline.getSeatPrice(seatNo.get(cbbSeats1.getSelectedIndex()));
						price /= 100;
					}
					if (coach) {
						price = airline.getSeatPrice(seatNo.get(cbbSeats2.getSelectedIndex()));
						price /= 100;
					}
					if (economy) { 
						price = airline.getSeatPrice(seatNo.get(cbbSeats2.getSelectedIndex()));
						price /= 100;
					}
					totalPrice = String.valueOf(price);
					lblNewLabel.setText("Your total ticket price is: " + totalPrice);
				}
				
			}
		});
		btnConfirmAndPay.setFont(new Font("Arial", btnConfirmAndPay.getFont().getStyle() | Font.BOLD, btnConfirmAndPay.getFont().getSize() + 1));
		btnConfirmAndPay.setBounds(460, 207, 151, 55);
		seatsPanel2.add(btnConfirmAndPay);
		
		JLabel lblSelectYourSeats = new JLabel("Select your seats");
		lblSelectYourSeats.setBounds(237, 11, 135, 29);
		seatsPanel2.add(lblSelectYourSeats);
		
		
		
		
		
		
		/**
		 * TODO
		 * 6th Panel: paymentPanel
		 */
		JLabel lblSummary = new JLabel("Payment Summary");
		lblSummary.setFont(new Font("Arial", lblSummary.getFont().getStyle() | Font.BOLD, lblSummary.getFont().getSize() + 5));
		lblSummary.setBounds(272, 33, 156, 38);
		paymentPanel.add(lblSummary);
		
		lblNewLabel = new JLabel();
		lblNewLabel.setBounds(109, 77, 466, 50);
		paymentPanel.add(lblNewLabel);

		final JLabel lblTicketInfoHere = new JLabel("ticket info will appear here");
		lblTicketInfoHere.setBounds(50, 290, 600, 50);
		paymentPanel.add(lblTicketInfoHere);
		

		final JButton btnBack_2 = new JButton("Cancel Transaction");
		btnBack_2.setFont(new Font("Arial", btnBack_2.getFont().getStyle() | Font.BOLD, btnBack_2.getFont().getSize() + 1));
		btnBack_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				jfrm.dispose();
			}
		});
		btnBack_2.setBounds(405, 189, 170, 60);
		paymentPanel.add(btnBack_2);
		paymentPanel.setVisible(false);
		
		
		backButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				seatsPanel2.setVisible(true);
				paymentPanel.setVisible(false);
				lblTicketInfoHere.setText(" ");
			}
		});
		backButton2.setBounds(60, 380, 89, 23);
		paymentPanel.add(backButton2);
		
		
		/**
		 * bookSeat (Booking)
		 * bookSeat(String seatNumber, Passenger p)
		 */
		JButton btnConfirmPayment = new JButton("Confirm Payment");
		btnConfirmPayment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (many) {
					System.out.println(airline.bookSeats(seatNo, passenger));
					lblTicketInfoHere.setText("Booking payment confirmed for: " +passenger +", " +airline.bookSeats(seatNo, passenger));
					System.out.println(ticket);
					btnConfirmAndPay.setEnabled(false);
					
				}
				else {
					if (first) {
						airline.bookSeat(seatNo.get(cbbSeats1.getSelectedIndex()), passenger);
						lblTicketInfoHere.setText("Booking payment confirmed for: " + passenger +", " 
												+ airline.bookSeat(seatNo.get(cbbSeats1.getSelectedIndex()), passenger));
						
						tags.add((String) cbbSeats1.getSelectedItem());
					}
					if (coach) {
						airline.bookSeat(seatNo.get(cbbSeats2.getSelectedIndex()), passenger);
						lblTicketInfoHere.setText("Booking payment confirmed for: " + passenger +", " 
								+ airline.bookSeat(seatNo.get(cbbSeats2.getSelectedIndex()), passenger));
						
						tags.add((String) cbbSeats2.getSelectedItem());
					}
					if (economy) {
						airline.bookSeat(seatNo.get(cbbSeats3.getSelectedIndex()), passenger);
						lblTicketInfoHere.setText("Booking payment confirmed for: " + passenger +", " 
								+ airline.bookSeat(seatNo.get(cbbSeats3.getSelectedIndex()), passenger));
						
						tags.add((String) cbbSeats3.getSelectedItem());
					}
				}
				btnBack_2.setEnabled(false);
				cancelBookButton.setEnabled(true);
				backButton2.setEnabled(true);
				cancelResButton.setEnabled(false);
			}
		});
		btnConfirmPayment.setFont(new Font("Arial", btnConfirmPayment.getFont().getStyle() | Font.BOLD, btnConfirmPayment.getFont().getSize() + 1));
		btnConfirmPayment.setBounds(109, 189, 170, 60);
		paymentPanel.add(btnConfirmPayment);	
		
		
		
		/**
		 * extra functions to identify booked and reserved seats
		 */
		cbbSeats1.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent ae) {
				System.out.println(tags);
				for (int i=0; i<tags.size(); i++) {
					if (cbbSeats1.getSelectedItem().equals(tags.get(i))) {
						cancelBookButton.setEnabled(true);
						cancelResButton.setEnabled(false);
						btnConfirmAndPay.setEnabled(false);
						break;
					}
					else {
						cancelResButton.setEnabled(true);
						btnConfirmAndPay.setEnabled(true);
						cancelBookButton.setEnabled(false);
						
					}
				}
			}
		});
		
		cbbSeats2.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent ae) {
				for (int i=0; i<tags.size(); i++) {
					if (cbbSeats2.getSelectedItem().equals(tags.get(i))) {
						cancelBookButton.setEnabled(true);
						cancelResButton.setEnabled(false);
						btnConfirmAndPay.setEnabled(false);
						break;
					}
					else {
						cancelResButton.setEnabled(true);
						btnConfirmAndPay.setEnabled(false);
						cancelBookButton.setEnabled(false);
					}
				}
			}
		});
		
		cbbSeats3.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent ae) {
				for (int i=0; i<tags.size(); i++) {
					if (cbbSeats3.getSelectedItem().equals(tags.get(i))) {
						cancelBookButton.setEnabled(true);
						cancelResButton.setEnabled(false);
						btnConfirmAndPay.setEnabled(false);
						break;
					}
					else {
						cancelResButton.setEnabled(true);
						btnConfirmAndPay.setEnabled(true);
						cancelBookButton.setEnabled(false);
					}
				}
			}
		});
	}
}








