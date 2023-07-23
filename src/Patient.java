public class Patient extends Person {

    private String patientId;
    private int noOfConsultations = 0;

    public Patient() {
        super();
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public int getNoOfConsultations() {
        return noOfConsultations;
    }

    public void setNoOfConsultations(int noOfConsultations) {
        this.noOfConsultations = noOfConsultations;
    }
}
