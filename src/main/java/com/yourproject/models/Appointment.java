package com.yourproject.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "patient_name")
    private String patientName;
    
    @Column(name = "doctor_name")
    private String doctorName;
    
    @Column(name = "appointment_date")
    private LocalDateTime appointmentDate;
    
    @Column(name = "purpose")
    private String purpose;
    
    @Column(name = "status")
    private String status; // SCHEDULED, COMPLETED, CANCELLED
    
    @Column(name = "patient_email")
    private String patientEmail;
    
    @Column(name = "patient_phone")
    private String patientPhone;
    
    // Constructors
    public Appointment() {}
    
    public Appointment(String patientName, String doctorName, LocalDateTime appointmentDate, String purpose, String status) {
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.appointmentDate = appointmentDate;
        this.purpose = purpose;
        this.status = status;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    
    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
    
    public LocalDateTime getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDateTime appointmentDate) { this.appointmentDate = appointmentDate; }
    
    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getPatientEmail() { return patientEmail; }
    public void setPatientEmail(String patientEmail) { this.patientEmail = patientEmail; }
    
    public String getPatientPhone() { return patientPhone; }
    public void setPatientPhone(String patientPhone) { this.patientPhone = patientPhone; }
}
