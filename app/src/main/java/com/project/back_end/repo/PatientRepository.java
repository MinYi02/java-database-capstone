package com.project.back_end.repo;

import com.project.back_end.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Find patient by email
    Optional<Patient> findByEmail(String email);
    
    // Find patient by phone number
    Optional<Patient> findByPhone(String phone);
    
    // Find patients by last name
    List<Patient> findByLastName(String lastName);
    
    // Find patients by first name and last name
    List<Patient> findByFirstNameAndLastName(String firstName, String lastName);
    
    // Find patients by gender
    List<Patient> findByGender(String gender);
    
    // Find patients born after a specific date
    List<Patient> findByDateOfBirthAfter(LocalDate date);
    
    // Find patients born before a specific date
    List<Patient> findByDateOfBirthBefore(LocalDate date);
    
    // Search patients by name (first name or last name containing)
    List<Patient> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
    
    // Find patients by city (if address field contains city information)
    @Query("SELECT p FROM Patient p WHERE p.address LIKE %:city%")
    List<Patient> findByCity(@Param("city") String city);
    
    // Count patients by gender
    @Query("SELECT p.gender, COUNT(p) FROM Patient p GROUP BY p.gender")
    List<Object[]> countPatientsByGender();
    
    // Find patients with upcoming birthdays (next 30 days)
    @Query("SELECT p FROM Patient p WHERE FUNCTION('DAYOFYEAR', p.dateOfBirth) BETWEEN FUNCTION('DAYOFYEAR', CURRENT_DATE) AND FUNCTION('DAYOFYEAR', CURRENT_DATE) + 30")
    List<Patient> findPatientsWithUpcomingBirthdays();
    
    // Find patients registered in a specific year
    @Query("SELECT p FROM Patient p WHERE YEAR(p.registrationDate) = :year")
    List<Patient> findByRegistrationYear(@Param("year") int year);
    
    // Find patients by status
    List<Patient> findByStatus(String status);
    
    // Find patients with emergency contact containing specific text
    List<Patient> findByEmergencyContactContaining(String emergencyContact);
    
    // Custom query to find patients with no email
    @Query("SELECT p FROM Patient p WHERE p.email IS NULL OR p.email = ''")
    List<Patient> findPatientsWithoutEmail();
    
    // Find patients by age range
    @Query("SELECT p FROM Patient p WHERE FUNCTION('DATEDIFF', CURRENT_DATE, p.dateOfBirth)/365.25 BETWEEN :minAge AND :maxAge")
    List<Patient> findByAgeRange(@Param("minAge") int minAge, @Param("maxAge") int maxAge);
    
    // Count total patients
    @Query("SELECT COUNT(p) FROM Patient p")
    long countTotalPatients();
    
    // Find patients with similar names using soundex (if supported)
    @Query(value = "SELECT * FROM patients WHERE SOUNDEX(first_name) = SOUNDEX(:name) OR SOUNDEX(last_name) = SOUNDEX(:name)", nativeQuery = true)
    List<Patient> findPatientsBySoundexName(@Param("name") String name);
}
