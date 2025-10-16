# MySQL Database Design - Smart Clinic Management System

## Database Schema

### Tables Overview

#### Doctors Table
```sql
CREATE TABLE doctors (
    id VARCHAR(20) PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    specialty VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(20),
    license_number VARCHAR(50) UNIQUE,
    years_experience INT,
    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE appointments (
    id VARCHAR(20) PRIMARY KEY,
    patient_id VARCHAR(20),
    doctor_id VARCHAR(20),
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    status ENUM('SCHEDULED', 'CONFIRMED', 'COMPLETED', 'CANCELLED', 'NO_SHOW'),
    purpose TEXT,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);

CREATE INDEX idx_doctor_specialty ON doctors(specialty);
CREATE INDEX idx_appointment_date ON appointments(appointment_date);
CREATE INDEX idx_patient_name ON patients(last_name, first_name);

