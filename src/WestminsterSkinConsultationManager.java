import java.io.*;
import java.util.*;

public class WestminsterSkinConsultationManager implements SkinConsultationManager {

    //creating the global array lists
    private final ArrayList<Doctor> doctors = new ArrayList<>();
    private final ArrayList<Consultation> consultations = new ArrayList<>();

    public WestminsterSkinConsultationManager() {
    }

    @Override
    public void addDoctor(Doctor doctor) {
        //checking the doctor already registered
        if (doctors.contains(doctor)) {
            System.out.println("Doctor is already registered!!!...");
        } else if (doctors.size() == 10) {
            System.out.println("Maximum Doctor count reached!!!...");
        } else {
            //adding the doctor into the list
            doctors.add(doctor);
            System.out.println(doctor.getFullName() + " - " + doctor.getMedicalLicenceNumber()
                    + " registered successfully as a Doctor.");
        }
    }

    @Override
    public void deleteDoctor(String name, String medicalLicenceNumber) {
        //checking the doctor list is empty
        if (doctors.isEmpty()) {
            System.out.println("No doctors in the list!!!...");
            return;
        }

        boolean checker = false;
        //finding the relevant doctor from the  list
        for (Doctor doctor : doctors) {
            if (doctor.getFullName().equalsIgnoreCase(name)) {
                if (doctor.getMedicalLicenceNumber().equalsIgnoreCase(medicalLicenceNumber)) {
                    //removing the relevant doctor
                    doctors.remove(doctor);
                    System.out.println("Dr. " + doctor.getFullName() + " successfully removed.");
                    checker = true;
                    break;
                }
            }
        }

        //if no matching results found
        if (!checker) {
            System.out.println("Entered details does not match!!!...");
        }
    }

    @Override
    public void printDoctorsList() {
        //checking list is empty
        if (doctors.isEmpty()) {
            System.out.println("No doctors available!!!...");
            return;
        }
        // Sorting the doctors list alphabetically by surname
        List<Doctor> sortedDoctors = new ArrayList<>(doctors);
        sortedDoctors.sort(Comparator.comparing(Doctor::getSurname));

        // Printing the sorted list of doctors
        System.out.println("List of doctors in the consultation center:");
        for (Doctor doctor : sortedDoctors) {
            System.out.println("Dr. " + doctor.getFullName() + " - " + doctor.getMedicalLicenceNumber() + " - " +
                    doctor.getSpecialisation() + " - " + doctor.getMobile());
        }
    }

    @Override
    public ArrayList<Doctor> getDoctorsData() {
        return doctors;
    }

    @Override
    public Consultation addConsultation(Consultation consultation) {
        //checking the consultation already available
        if (consultations.contains(consultation)) {
            System.out.println("The consultation is already in the added!!!...");
        } else {
            List<Consultation> patientConsultations = new ArrayList<>();
            for (Consultation booked : consultations) {
                if (booked.getPatient().getPatientId().equalsIgnoreCase(consultation.getPatient().getPatientId())) {
                    patientConsultations.add(booked);
                }
            }
            double cost;
            if (patientConsultations.isEmpty()) {
                cost = 15.0; // No previous consultations, set cost to 15
            } else {
                cost = 25.0; // Previous consultations exist, set cost to 25
            }
            consultation.setCost(cost);
            consultations.add(consultation);
        }
        return consultation;
    }

    @Override
    public void saveDataToFile() {
        if (doctors.size() == 0) {
            return;
        }

        try {
            //creating the file output stream and setting the file for the doctors list
            FileOutputStream fileOutputStreamDoctors = new FileOutputStream("./DataInTheArray.txt");
            //creating the object output stream for the doctors list
            ObjectOutputStream objectOutputStreamDoctors = new ObjectOutputStream(fileOutputStreamDoctors);
            //getting objects from doctors list
            for (Doctor doctor : doctors) {
                if (doctor != null) {
                    //adding the object to the file
                    objectOutputStreamDoctors.writeObject(doctor);
                }
            }
            System.out.println("Data saved to the file Successfully!!");

            //closing object output streams
            objectOutputStreamDoctors.close();
            //flushing the streams
            objectOutputStreamDoctors.flush();
            //closing the file output streams
            fileOutputStreamDoctors.close();

        } catch (FileNotFoundException e) {
            //if file not found
            System.out.println("File not Found");
            //e.printStackTrace();
        } catch (Exception e) {
            //if any other error occurred
            System.out.println("Something went Wrong");
        }
    }

    @Override
    public void loadDataFromFile() throws IOException {
        try {
            //creating file input stream for doctors list and accessing the file
            FileInputStream fileOutputStreamDoctors = new FileInputStream("./DataInTheArray.txt");
            //creating object input stream for doctors list
            ObjectInputStream objectInputStreamDoctors = new ObjectInputStream(fileOutputStreamDoctors);
            //creating an infinite for loop
            for (; ; ) {
                try {
                    //reading the doctor objects from the file
                    Doctor doctor = (Doctor) objectInputStreamDoctors.readObject();
                    //adding doctor objects to the list
                    doctors.add(doctor);
                } catch (IOException e) {
                    break;
                }
            }
            //closing the streams
            objectInputStreamDoctors.close();
            System.out.println("Data imported Successfully!!!");
        } catch (IOException | ClassNotFoundException e) {
            //e.printStackTrace();
            System.out.println("Something went Wrong");
        }
    }

    @Override
    public Doctor randomDoctorPicker(Date date) {
        List<Doctor> availableDoctors = getAvailableDoctors(date);
        Random random = new Random();
        if (availableDoctors.size() > 0) {
            int index = random.nextInt(availableDoctors.size()); // Ensure doctors list is not empty
            return availableDoctors.get(index);
        } else {
            return null;
        }
    }

    public List<Doctor> getAvailableDoctors(Date date) {
        List<Doctor> availableDoctors = new ArrayList<>();

        for (Doctor doctor : doctors) {
            boolean isAvailable = true;

            for (Consultation consultation : consultations) {
                Date consultationStartTime = consultation.getDateTime();
                Date consultationEndTime = addHours(consultationStartTime); // Add one hour to the consultation start time

                if (date.after(consultationStartTime) && date.before(consultationEndTime) && consultation.getDoctor() == doctor) {
                    isAvailable = false;
                    break;
                }
            }

            if (isAvailable) {
                availableDoctors.add(doctor);
            }
        }

        return availableDoctors;
    }


    @Override
    public boolean checkDoctorRegistered(String id) {
        //checking the doctor already registered
        for (Doctor doc : doctors) {
            if (doc.getMedicalLicenceNumber().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkDoctorAvailability(Date date, Doctor doctor) {
        for (Consultation consultation : consultations) {
            if (consultation.getDoctor().equals(doctor)) {
                Date startTime = consultation.getDateTime();
                Date endTime = addHours(startTime); // Add one hour to the start time
                return !date.after(startTime) || !date.before(endTime);
            }
        }

        // No consultations found for the doctor at the given time
        return true;
    }

    private Date addHours(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        return calendar.getTime();
    }
}
