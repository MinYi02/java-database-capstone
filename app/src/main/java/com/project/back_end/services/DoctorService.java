package com.project.back_end.services;

import com.project.back_end.models.Doctor;
import com.project.back_end.repo.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    // Get all doctors
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    // Get doctor by ID
    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    // Get doctor by doctor ID (custom ID)
    public Optional<Doctor> getDoctorByDoctorId(String doctorId) {
        return doctorRepository.findByDoctorId(doctorId);
    }

    // Create new doctor
    public Doctor createDoctor(Doctor doctor) {
        // Validate unique doctor ID
        if (doctorRepository.findByDoctorId(doctor.getDoctorId()).isPresent()) {
            throw new RuntimeException("Doctor ID already exists: " + doctor.getDoctorId());
        }

        // Validate unique email
        if (doctor.getEmail() != null && doctorRepository.findByEmail(doctor.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists: " + doctor.getEmail());
        }

        // Validate unique license number
        if (doctor.getLicenseNumber() != null && doctorRepository.findByLicenseNumber(doctor.getLicenseNumber()).isPresent()) {
            throw new RuntimeException("License number already exists: " + doctor.getLicenseNumber());
        }

        return doctorRepository.save(doctor);
    }

    // Update doctor
    public Doctor updateDoctor(Long id, Doctor doctorDetails) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();

            // Update fields if provided
            if (doctorDetails.getFirstName() != null) {
                doctor.setFirstName(doctorDetails.getFirstName());
            }
            if (doctorDetails.getLastName() != null) {
                doctor.setLastName(doctorDetails.getLastName());
            }
            if (doctorDetails.getSpecialty() != null) {
                doctor.setSpecialty(doctorDetails.getSpecialty());
            }
            if (doctorDetails.getEmail() != null) {
                // Validate unique email
                Optional<Doctor> existingDoctorWithEmail = doctorRepository.findByEmail(doctorDetails.getEmail());
                if (existingDoctorWithEmail.isPresent() && !existingDoctorWithEmail.get().getId().equals(id)) {
                    throw new RuntimeException("Email already exists: " + doctorDetails.getEmail());
                }
                doctor.setEmail(doctorDetails.getEmail());
            }
            if (doctorDetails.getPhone() != null) {
                doctor.setPhone(doctorDetails.getPhone());
            }
            if (doctorDetails.getLicenseNumber() != null) {
                // Validate unique license number
                Optional<Doctor> existingDoctorWithLicense = doctorRepository.findByLicenseNumber(doctorDetails.getLicenseNumber());
                if (existingDoctorWithLicense.isPresent() && !existingDoctorWithLicense.get().getId().equals(id)) {
                    throw new RuntimeException("License number already exists: " + doctorDetails.getLicenseNumber());
                }
                doctor.setLicenseNumber(doctorDetails.getLicenseNumber());
            }
            if (doctorDetails.getYearsExperience() != null) {
                doctor.setYearsExperience(doctorDetails.getYearsExperience());
            }
            if (doctorDetails.getStatus() != null) {
                doctor.setStatus(doctorDetails.getStatus());
            }

            return doctorRepository.save(doctor);
        } else {
            throw new RuntimeException("Doctor not found with id: " + id);
        }
    }

    // Delete doctor
    public void deleteDoctor(Long id) {
        if (doctorRepository.existsById(id)) {
            doctorRepository.deleteById(id);
        } else {
            throw new RuntimeException("Doctor not found with id: " + id);
        }
    }

    // Get doctors by specialty
    public List<Doctor> getDoctorsBySpecialty(String specialty) {
        return doctorRepository.findBySpecialty(specialty);
    }

    // Get active doctors
    public List<Doctor> getActiveDoctors() {
        return doctorRepository.findByStatus("ACTIVE");
    }

    // Search doctors by name
    public List<Doctor> searchDoctorsByName(String name) {
        return doctorRepository.findByFirstNameContainingOrLastNameContaining(name, name);
    }

    // Get doctors with minimum years of experience
    public List<Doctor> getDoctorsWithMinExperience(int minYears) {
        return doctorRepository.findByYearsExperienceGreaterThanEqual(minYears);
    }

    // Update doctor status
    public Doctor updateDoctorStatus(Long id, String status) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            doctor.setStatus(status);
            return doctorRepository.save(doctor);
        } else {
            throw new RuntimeException("Doctor not found with id: " + id);
        }
    }

    // Get doctors by multiple specialties
    public List<Doctor> getDoctorsBySpecialties(List<String> specialties) {
        return doctorRepository.findBySpecialtyIn(specialties);
    }

    // Count doctors by specialty
    public long countDoctorsBySpecialty(String specialty) {
        return doctorRepository.countBySpecialty(specialty);
    }

    // Get all available specialties
    public List<String> getAllSpecialties() {
        return doctorRepository.findAllSpecialties();
    }

    // Check if doctor exists by ID
    public boolean doctorExists(Long id) {
        return doctorRepository.existsById(id);
    }

    // Get doctors with email domain
    public List<Doctor> getDoctorsByEmailDomain(String domain) {
        return doctorRepository.findByEmailEndingWith(domain);
    }
}
