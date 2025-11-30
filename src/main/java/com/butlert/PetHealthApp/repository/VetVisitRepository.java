package com.butlert.PetHealthApp.repository;

import com.butlert.PetHealthApp.Entity.VetVisit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VetVisitRepository extends JpaRepository<VetVisit, Long> {

    List<VetVisit> findByPetId(Long petId);
}
