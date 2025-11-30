package com.butlert.PetHealthApp.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vet_visits")
public class VetVisit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @Column(nullable = false)
    private LocalDate visitDate;

    @Column(nullable = false)
    private String reason;

    private String vetName;

    @Column(length = 1000)
    private String notes;

    public VetVisit() {
    }

    public VetVisit(Pet pet, LocalDate visitDate, String reason, String vetName, String notes) {
        this.pet = pet;
        this.visitDate = visitDate;
        this.reason = reason;
        this.vetName = vetName;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getVetName() {
        return vetName;
    }

    public void setVetName(String vetName) {
        this.vetName = vetName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "VetVisit{" +
                "id=" + id +
                ", pet=" + pet +
                ", visitDate=" + visitDate +
                ", reason='" + reason + '\'' +
                ", vetName='" + vetName + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
