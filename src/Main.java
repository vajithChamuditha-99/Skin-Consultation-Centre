import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.Font;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    //creating a global variables
    private static JFrame mainFrame;
    private static boolean isMenuDisplayed;
    private static final Scanner scanner = new Scanner(System.in);
    static SkinConsultationManager consultationManager = new WestminsterSkinConsultationManager();

    public static void main(String[] args) throws IOException {
        String cyan = "\u001B[36m"; //for colors in text
        String defaultColor = "\u001B[0m";

        System.out.println(cyan + "Welcome to Westminster Skin Consultation Management System \n" + defaultColor);

        //Loading the previous data
        consultationManager.loadDataFromFile();
        isMenuDisplayed = true;
        displayMenu();
    }

    private static void displayMenu() {
        String resetColor = "\u001B[0m";
        String purple = "\u001B[35m";
        if (isMenuDisplayed) {
            menu:
            // Infinite loop
            while (true) {
                System.out.println("");
                System.out.println(purple + "Press 1 to Register a Doctor");
                System.out.println("Press 2 to Delete a Doctor");
                System.out.println("Press 3 to Print Doctors List");
                System.out.println("Press 4 to Display GUI");
                System.out.println("Press 5 to Save Details in a Text File");
                System.out.println("Press 0 to Quit from the Application" + resetColor);
                System.out.println("Enter your choice: ");

                int choice = 0;
                boolean flag = true;
                while (flag) {
                    //checking input is an integer or not using try catch
                    try {
                        choice = scanner.nextInt();

                        switch (choice) {
                            case 1:
                                addDoctor();
                                break;
                            case 2:
                                deleteDoctor();
                                break;
                            case 3:
                                printDoctorsList();
                                break;
                            case 4:
                                isMenuDisplayed = false; // Set the flag to indicate the menu is not displayed
                                createAndShowGUI();
                                break;
                            case 5:
                                consultationManager.saveDataToFile();
                                break;
                            case 0:
                                System.out.println(resetColor + "Thank You for using the System. Have a Pleasant Day!");
                                System.exit(0);
                                break menu;
                            default:
                                System.out.println("You Selected an Invalid Option. Please Try Again!");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Please enter a valid option!");
                        scanner.next(); // Consume invalid input
                    }
                    flag = false;
                }
            }
        }
    }

    private static void addDoctor() {
        Doctor doctor = new Doctor();
        SimpleDateFormat dateInput = new SimpleDateFormat("yyyy-MM-dd");
        //getting user inputs
        if (consultationManager.getDoctorsData().size() >= 10) {
            System.out.println("Maximum Doctor count reached!");
            return;
        }
        System.out.println("Enter the licence number of the Doctor : ");
        String licence = scanner.next();
        if (consultationManager.checkDoctorRegistered(licence)) {
            System.out.println("Doctor with given licence number already registered!!!...");
            return;
        } else {
            doctor.setMedicalLicenceNumber(licence);
        }
        System.out.println("Enter the first name of the Doctor : ");
        String name = scanner.next();
        name += scanner.nextLine(); // accept inputs with spaces
        //setting values using setters
        doctor.setName(name);
        System.out.println("Enter the surname of the Doctor : ");
        String surname = scanner.next();
        doctor.setSurname(surname);
        System.out.println("Enter the specialization of the Doctor : ");
        String specialization = scanner.next();
        specialization += scanner.nextLine();
        //setting values using setters
        doctor.setSpecialisation(specialization);
        System.out.println("Enter the date of birth of the Doctor (yyyy-MM-dd) : ");
        String dob = scanner.next();
        try {
            Date date = dateInput.parse(dob);
            doctor.setDateOfBirth(date);
        } catch (ParseException e) {
            System.out.println("Invalid input!!!...");
            return;
        }
        System.out.println("Enter the mobile number of the Doctor : ");
        String mobile = scanner.next();
        doctor.setMobile(mobile);

        consultationManager.addDoctor(doctor);
    }

    private static void deleteDoctor() {
        System.out.println("Enter the Doctor name you want to Delete (with surname) : ");
        String fullName = scanner.next();
        fullName += scanner.nextLine(); // accept inputs with spaces
        System.out.println("Enter the licence number of the Doctor you want to Delete : ");
        String licenceNo = scanner.next();
        consultationManager.deleteDoctor(fullName, licenceNo);
    }

    private static void printDoctorsList() {
        consultationManager.printDoctorsList();
    }

    private static void createAndShowGUI() {
        mainFrame = new JFrame("Welcome");
        mainFrame.setSize(737, 550);
        mainFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        mainFrame.setResizable(false);

        JLabel lbl1 = new JLabel("<html><div style='text-align: center;'>Welcome to Westminster Skin Consultation<br>Management System</div></html>");
        lbl1.setFont(lbl1.getFont().deriveFont(Font.BOLD, 24));
        lbl1.setHorizontalAlignment(SwingConstants.CENTER);
        lbl1.setBounds(0, 20, 737, 80);

        // Create a JLabel for the image
        JLabel imageLabel = new JLabel();
        imageLabel.setBounds(198, 120, 340, 230);
        ImageIcon imageIcon = new ImageIcon("191skinlab.png");
        Image image = imageIcon.getImage().getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
        imageLabel.setIcon(imageIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton displayDoctors = new JButton("Display doctors");
        displayDoctors.setBounds(145, 375, 180, 55);
        displayDoctors.setFont(displayDoctors.getFont().deriveFont(Font.BOLD, 16));
        displayDoctors.setForeground(Color.WHITE);
        displayDoctors.setBackground(new Color(10, 153, 153));

        JButton checkAvailability = new JButton("Check Availability");
        checkAvailability.setBounds(410, 375, 180, 55);
        checkAvailability.setFont(checkAvailability.getFont().deriveFont(Font.BOLD, 16));
        checkAvailability.setForeground(Color.WHITE);
        checkAvailability.setBackground(new Color(10, 153, 153));

        JButton backToMenu = new JButton("Back to Menu");
        backToMenu.setBounds(550, 462, 120, 35);
        backToMenu.setFont(backToMenu.getFont().deriveFont(Font.BOLD, 12));
        backToMenu.setForeground(Color.WHITE);
        backToMenu.setBackground(new Color(207, 84, 31));

        backToMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
            }
        });

        displayDoctors.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createDoctorsTable();
            }
        });

        checkAvailability.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openTimePicker();
            }
        });

        JPanel contentPane = new JPanel();
        contentPane.setBackground(new Color(179, 179, 204));
        contentPane.setLayout(null);
        contentPane.add(lbl1);
        contentPane.add(imageLabel);
        contentPane.add(displayDoctors);
        contentPane.add(checkAvailability);
        contentPane.add(backToMenu);

        mainFrame.setContentPane(contentPane);
        mainFrame.setVisible(true);
    }

    private static void createDoctorsTable() {
        JFrame doctorsFrame = new JFrame("Doctors Table");
        doctorsFrame.setSize(780, 650);
        doctorsFrame.setResizable(false);
        doctorsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lbl1 = new JLabel("<html><div style='text-align: center;'>All Doctors</div></html>");
        lbl1.setFont(lbl1.getFont().deriveFont(Font.BOLD, 30));
        lbl1.setHorizontalAlignment(SwingConstants.CENTER);
        lbl1.setBounds(0, 20, 780, 50);

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Name");
        tableModel.addColumn("Surname");
        tableModel.addColumn("Specialisation");
        tableModel.addColumn("Medical Licence Number");
        tableModel.addColumn("Mobile");

        final ArrayList<Doctor> data = consultationManager.getDoctorsData();

        JComboBox<String> sortDropdown = new JComboBox<>();
        sortDropdown.setBounds(40, 60, 140, 30);
        sortDropdown.addItem("Default Order");
        sortDropdown.addItem("Sort by Surname");
        sortDropdown.setSelectedItem("Default Order"); // Set the default sorting option
        displayDoctorsData(data, tableModel);
        sortDropdown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) sortDropdown.getSelectedItem();
                tableModel.setRowCount(0); // Clear the table model

                assert selectedOption != null;
                if (selectedOption.equals("Sort by Surname")) {
                    ArrayList<Doctor> sortedData = new ArrayList<>(data);
                    sortedData.sort(Comparator.comparing(Doctor::getSurname));
                    displayDoctorsData(sortedData, tableModel); // Display the sorted data
                } else {
                    displayDoctorsData(data, tableModel); // Display the default order data
                }
            }
        });

        JTable doctorsTable = new JTable(tableModel);
        doctorsTable.setBounds(40, 92, 670, 428);

        JScrollPane scrollPane = new JScrollPane(doctorsTable);
        scrollPane.setBounds(40, 92, 670, 428);

        JButton buttonClose = new JButton("Back to main GUI");
        buttonClose.setBounds(495, 550, 150, 35);
        buttonClose.setFont(buttonClose.getFont().deriveFont(Font.BOLD, 12));
        buttonClose.setForeground(Color.WHITE);
        buttonClose.setBackground(new Color(207, 84, 31));

        buttonClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doctorsFrame.dispose();
                mainFrame.setVisible(true);
            }
        });

        JPanel doctorsPanel = new JPanel();
        doctorsPanel.setLayout(null);
        doctorsPanel.setBackground(Color.WHITE);
        doctorsPanel.add(lbl1);
        doctorsPanel.add(sortDropdown);
        doctorsPanel.add(scrollPane);
        doctorsPanel.add(buttonClose);
        doctorsFrame.setContentPane(doctorsPanel);
        doctorsFrame.setVisible(true);
    }

    // Display the doctors data based on the given list and table model
    private static void displayDoctorsData(ArrayList<Doctor> doctorsList, DefaultTableModel tableModel) {
        for (Doctor doctor : doctorsList) {
            Object[] rowData = new Object[5];
            rowData[0] = doctor.getName();
            rowData[1] = doctor.getSurname();
            rowData[2] = doctor.getSpecialisation();
            rowData[3] = doctor.getMedicalLicenceNumber();
            rowData[4] = doctor.getMobile();
            tableModel.addRow(rowData);
        }
    }

    private static void openTimePicker() {
        TimePicker timePicker = new TimePicker(mainFrame);
        Date pickedTime = timePicker.setPickedDate();
        if (pickedTime != null) {
            openAvailabilityGUI(pickedTime);
        }
    }

    private static void openAvailabilityGUI(Date pickedTime) {
        Consultation consultation = new Consultation();
        consultation.setDateTime(pickedTime);
        JFrame availabilityFrame = new JFrame("Check Availability");
        availabilityFrame.setSize(400, 300);
        availabilityFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        availabilityFrame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel dateLabel = new JLabel("Select Date:");
        dateLabel.setBounds(20, 20, 150, 20);
        JLabel dateTimeLabel = new JLabel(pickedTime.toString());
        dateTimeLabel.setBounds(180, 20, 200, 20);

        final ArrayList<Doctor> doctors = consultationManager.getDoctorsData();

        // Doctor selection dropdown
        JLabel doctorLabel = new JLabel("Select Doctor:");
        doctorLabel.setBounds(20, 60, 150, 20);
        JComboBox<String> doctorDropdown = new JComboBox<>();
        doctorDropdown.setBounds(180, 60, 200, 20);
        // Populate the doctor dropdown with available doctors
        for (Doctor doctor : doctors) {
            doctorDropdown.addItem(doctor.getFullName());
        }

        // Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(120, 120, 100, 30);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get selected date and doctor
                Date selectedDate = null;
                try {
                    selectedDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(dateTimeLabel.getText());
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                Doctor selectedDoctor = doctors.get(doctorDropdown.getSelectedIndex());
                Doctor bookedDoctor;

                // Perform availability check and book consultation
                boolean isAvailable = consultationManager.checkDoctorAvailability(selectedDate, selectedDoctor);
                String message = "";
                if (isAvailable) {
                    message = "Doctor is available";
                    bookedDoctor = selectedDoctor;
                } else {
                    bookedDoctor = consultationManager.randomDoctorPicker(selectedDate);
                    if (bookedDoctor != null) {
                        message = "Doctor is not available. Dr. " + bookedDoctor.getFullName() + " can be allocated.";
                    } else {
                        JOptionPane.showMessageDialog(null, "No doctors available", "Doctor Availability", JOptionPane.WARNING_MESSAGE);
                    }
                }
                if (bookedDoctor != null) {
                    consultation.setDoctor(bookedDoctor);
                    showAvailabilityMessage(availabilityFrame, message, consultation);
                }
                availabilityFrame.dispose();
            }
        });

        // Go back button
        JButton goBackButton = new JButton("Go Back");
        goBackButton.setBounds(240, 120, 100, 30);
        goBackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                availabilityFrame.dispose();
                mainFrame.setVisible(true);
            }
        });

        panel.add(dateLabel);
        panel.add(dateTimeLabel);
        panel.add(doctorLabel);
        panel.add(doctorDropdown);
        panel.add(submitButton);
        panel.add(goBackButton);

        availabilityFrame.setContentPane(panel);
        availabilityFrame.setVisible(true);
    }

    private static void showAvailabilityMessage(JFrame availabilityFrame, String message, Consultation consultation) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JLabel(message), BorderLayout.CENTER);

        JButton bookButton = new JButton("Book Consultation");

        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{bookButton}, bookButton);
        JDialog dialog = optionPane.createDialog(availabilityFrame, "Availability");

        bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                bookConsultationDialog(consultation);
            }
        });
        dialog.setVisible(true);
    }

    private static void bookConsultationDialog(Consultation consultation) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Book Consultation");
        dialog.setSize(600, 400);
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(null);

        JLabel doctorLabel = new JLabel("Doctor: " + consultation.getDoctor().getFullName());
        JLabel timeLabel = new JLabel("Time: " + consultation.getDateTime());

        // Set the font size and alignment for doctorLabel and timeLabel
        Font labelFont = doctorLabel.getFont().deriveFont(doctorLabel.getFont().getSize() + 2f); // Increase font size by 2px
        doctorLabel.setFont(labelFont);
        timeLabel.setFont(labelFont);
        doctorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel doctorTimePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        doctorTimePanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding
        doctorTimePanel.add(doctorLabel);
        doctorTimePanel.add(timeLabel);

        JTextField nameField = new JTextField(20);
        JTextField surnameField = new JTextField(20);
        JTextField nicField = new JTextField(20);
        JTextField mobileField = new JTextField(20);
        JTextArea notesArea = new JTextArea(5, 20);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5); // Add padding

        inputPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Surname:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(surnameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("NIC:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(nicField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Mobile Number:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(mobileField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(new JLabel("Notes:"), gbc);
        gbc.gridx = 1;
        gbc.gridheight = 2;
        inputPanel.add(new JScrollPane(notesArea), gbc);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the user inputs
                String name = nameField.getText();
                String surname = surnameField.getText();
                String nic = nicField.getText();
                String mobile = mobileField.getText();
                String notes = notesArea.getText();

                Patient patient = new Patient();
                patient.setName(name);
                patient.setSurname(surname);
                 patient.setPatientId(nic);
                patient.setMobile(mobile);
                patient.setNoOfConsultations(patient.getNoOfConsultations() + 1);

                consultation.setPatient(patient);
                consultation.setNotes(notes);

                Consultation bookedConsultation = consultationManager.addConsultation(consultation);

                String[] options = {"View Consultation"};
                int result = JOptionPane.showOptionDialog(dialog, "Consultation successfully booked!", "Confirmation",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                if (result == 0) {
                    dialog.dispose();
                    viewConsultationGUI(bookedConsultation);
                }
            }
        });

        JButton clearButton = new JButton("Clear All");
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear all the input fields
                nameField.setText("");
                surnameField.setText("");
                nicField.setText("");
                mobileField.setText("");
                notesArea.setText("");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(confirmButton);
        buttonPanel.add(clearButton);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding
        contentPanel.add(doctorTimePanel, BorderLayout.NORTH);
        contentPanel.add(inputPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setContentPane(contentPanel);
        dialog.setVisible(true);
    }

    private static void viewConsultationGUI(Consultation consultation) {
        JDialog dialog = new JDialog();
        dialog.setTitle("View Consultation");
        dialog.setSize(400, 300);
        dialog.setResizable(false);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(null);

        JTextArea detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);
        detailsArea.setFont(detailsArea.getFont().deriveFont(Font.PLAIN, 14));
        detailsArea.append("Doctor: " + consultation.getDoctor().getFullName() + "\n");
        detailsArea.append("Time: " + consultation.getDateTime() + "\n");
        detailsArea.append("Patient: " + consultation.getPatient().getFullName() + "\n");
        detailsArea.append("Notes: " + consultation.getNotes() + "\n");
        detailsArea.append("Bill: " + consultation.getCost() + "\n");

        // Add left and top padding to the details area
        detailsArea.setBorder(BorderFactory.createEmptyBorder(50, 50, 0, 0));

        JScrollPane scrollPane = new JScrollPane(detailsArea);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Close the dialog
                dialog.dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);

        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private static void backToMenu() {
        System.out.println("Press \"Y\" to continue with main menu, press any other key to exit:..");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("y")) {
            displayMenu();
        } else {
            System.exit(0);
        }
    }
}
