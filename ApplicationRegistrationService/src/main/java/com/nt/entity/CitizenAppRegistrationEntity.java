package com.nt.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CITIZEN_APPLICATION")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitizenAppRegistrationEntity {

    @Id
    @SequenceGenerator(
        name = "gen1_seq",
        sequenceName = "app_id_seq",
        initialValue = 1000,
        allocationSize = 1
    )
    @GeneratedValue(
        generator = "gen1_seq",
        strategy = GenerationType.SEQUENCE
    )
    private Integer appId;

    @Column(length = 30)
    private String fullName;

    @Column(length = 30)
    private String email;

    @Column(length = 1)
    private String gender;

    private Long phoneNo;

    @Column(unique = true, nullable = false)
    private Long ssn;

    @Column(length = 30)
    private String stateName;

    private LocalDate dob;

    @Column(length = 30)
    private String createBy;

    @Column(length = 30)
    private String updatedBy;

    @Column(
        name = "CREATION_DATE",
        nullable = false,
        updatable = false
    )
    private LocalDate creationDate;

    @Column(
        name = "UPDATION_DATE",
        nullable = true
    )
    private LocalDate updationDate;

    @PrePersist
    private void setCreationDate() {

        if (this.creationDate == null) {
            this.creationDate = LocalDate.now();
        }
    }

    @PreUpdate
    private void setUpdationDate() {
        this.updationDate = LocalDate.now();
    }
}