package com.butlert.PetHealthApp.service;

import com.butlert.PetHealthApp.Entity.Pet;
import com.butlert.PetHealthApp.Entity.VetVisit;
import com.butlert.PetHealthApp.repository.PetRepository;
import com.butlert.PetHealthApp.repository.VetVisitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VetVisitService {

    private final VetVisitRepository vetVisitRepository;
    private final PetRepository petRepository;

    public VetVisitService(VetVisitRepository vetVisitRepository, PetRepository petRepository) {
        this.vetVisitRepository = vetVisitRepository;
        this.petRepository = petRepository;
    }

    public VetVisit addVisit(Long petId, VetVisit visit) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found with id " + petId));
        visit.setPet(pet);
        return vetVisitRepository.save(visit);
    }

    public VetVisit getVisit(Long id) {
        return vetVisitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visit not found with id " + id));
    }

    public List<VetVisit> getVisitsForPet(Long petId) {
        return vetVisitRepository.findByPetId(petId);
    }

    public VetVisit updateVisit(Long id, VetVisit updated) {
        VetVisit existing = getVisit(id);
        existing.setVisitDate(updated.getVisitDate());
        existing.setReason(updated.getReason());
        existing.setVetName(updated.getVetName());
        existing.setNotes(updated.getNotes());
        return vetVisitRepository.save(existing);
    }

    public void deleteVisit(Long id) {
        vetVisitRepository.deleteById(id);
    }
}
