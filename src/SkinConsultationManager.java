import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public interface SkinConsultationManager {
    Consultation addConsultation(Consultation consultation);

    void addDoctor(Doctor doctor);

    void deleteDoctor(String name, String medicalLicenceNumber);

    void printDoctorsList();

    ArrayList<Doctor> getDoctorsData();

    void saveDataToFile();

    void loadDataFromFile() throws IOException;

    Doctor randomDoctorPicker(Date date);

    boolean checkDoctorRegistered(String id);

    boolean checkDoctorAvailability(Date date, Doctor doctor);
}
