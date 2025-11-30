package com.butlert.PetHealthApp.service;

import com.butlert.PetHealthApp.Entity.Pet;
import com.butlert.PetHealthApp.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet addPet(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet getPet(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet not found with id " + id));
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public Pet updatePet(Long id, Pet updated) {
        Pet existing = getPet(id);
        existing.setName(updated.getName());
        existing.setSpecies(updated.getSpecies());
        existing.setBreed(updated.getBreed());
        existing.setDateOfBirth(updated.getDateOfBirth());
        existing.setNotes(updated.getNotes());
        return petRepository.save(existing);
    }

    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }
}

