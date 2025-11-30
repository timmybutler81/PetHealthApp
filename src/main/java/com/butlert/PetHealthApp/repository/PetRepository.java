package com.butlert.PetHealthApp.repository;

import com.butlert.PetHealthApp.Entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
