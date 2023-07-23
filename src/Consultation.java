import java.util.Date;

public class Consultation {

    private Patient patient;
    private Doctor doctor;
    private Date dateTime;
    private Double cost;
    private String notes;

    public Consultation(Patient patient, Doctor doctor, Date dateTime, Double cost, String notes) {
        this.patient = patient;
        this.doctor = doctor;
        this.dateTime = dateTime;
        this.cost = cost;
        this.notes = notes;
    }

    public Consultation() {
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
