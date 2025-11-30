package com.butlert.PetHealthApp;

import com.butlert.PetHealthApp.Entity.Pet;
import com.butlert.PetHealthApp.Entity.VetVisit;
import com.butlert.PetHealthApp.service.PetService;
import com.butlert.PetHealthApp.service.VetVisitService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

@Component
public class PetHealthAppCli {

    private final PetService petService;
    private final VetVisitService vetVisitService;
    private final Scanner scanner = new Scanner(System.in);

    public PetHealthAppCli(PetService petService, VetVisitService vetVisitService) {
        this.petService = petService;
        this.vetVisitService = vetVisitService;
    }

    public void start() {
        System.out.println("=== Pet Health Tracker CLI ===");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> listPets();
                    case "2" -> createPet();
                    case "3" -> viewPet();
                    case "4" -> updatePet();
                    case "5" -> deletePet();
                    case "6" -> createVetVisit();
                    case "7" -> listVetVisitsForPet();
                    case "8" -> viewVetVisit();
                    case "9" -> updateVetVisit();
                    case "10" -> deleteVetVisit();
                    case "0" -> {
                        running = false;
                        System.out.println("Exiting Pet Health Tracker CLI. Goodbye!");
                    }
                    default -> System.out.println("Invalid choice, please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println();
        }
    }

    private void printMenu() {
        System.out.println("-------------------------------------------------");
        System.out.println("1. List all pets");
        System.out.println("2. Create a new pet");
        System.out.println("3. View pet by ID");
        System.out.println("4. Update pet");
        System.out.println("5. Delete pet");
        System.out.println("6. Create vet visit for a pet");
        System.out.println("7. List vet visits for a pet");
        System.out.println("8. View vet visit by ID");
        System.out.println("9. Update vet visit");
        System.out.println("10. Delete vet visit");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    // --- PET OPERATIONS ---

    private void listPets() {
        List<Pet> pets = petService.getAllPets();
        if (pets.isEmpty()) {
            System.out.println("No pets found.");
        } else {
            System.out.println("Pets:");
            for (Pet p : pets) {
                System.out.printf("ID: %d | Name: %s | Species: %s | Breed: %s | DOB: %s | Notes: %s%n",
                        p.getId(),
                        p.getName(),
                        p.getSpecies(),
                        p.getBreed(),
                        p.getDateOfBirth(),
                        p.getNotes());
            }
        }
    }

    private void createPet() {
        System.out.print("Pet name: ");
        String name = scanner.nextLine();

        System.out.print("Species: ");
        String species = scanner.nextLine();

        System.out.print("Breed (optional): ");
        String breed = scanner.nextLine();

        LocalDate dob = readDateOrNull("Date of birth (YYYY-MM-DD, blank to skip): ");

        System.out.print("Notes (optional): ");
        String notes = scanner.nextLine();

        Pet pet = new Pet(name, species, blankToNull(breed), dob, blankToNull(notes));
        Pet saved = petService.addPet(pet);
        System.out.println("Created pet with ID: " + saved.getId());
    }

    private void viewPet() {
        Long id = readLong("Enter pet ID: ");
        Pet pet = petService.getPet(id);
        System.out.println("Pet details:");
        System.out.println("ID: " + pet.getId());
        System.out.println("Name: " + pet.getName());
        System.out.println("Species: " + pet.getSpecies());
        System.out.println("Breed: " + pet.getBreed());
        System.out.println("Date of Birth: " + pet.getDateOfBirth());
        System.out.println("Notes: " + pet.getNotes());
    }

    private void updatePet() {
        Long id = readLong("Enter pet ID to update: ");
        Pet existing = petService.getPet(id);

        System.out.println("Leave field blank to keep existing value.");
        System.out.println("Current name: " + existing.getName());
        System.out.print("New name: ");
        String name = scanner.nextLine();
        if (!name.isBlank()) existing.setName(name);

        System.out.println("Current species: " + existing.getSpecies());
        System.out.print("New species: ");
        String species = scanner.nextLine();
        if (!species.isBlank()) existing.setSpecies(species);

        System.out.println("Current breed: " + existing.getBreed());
        System.out.print("New breed: ");
        String breed = scanner.nextLine();
        if (!breed.isBlank()) existing.setBreed(breed);

        System.out.println("Current DOB: " + existing.getDateOfBirth());
        LocalDate dob = readDateOrNull("New DOB (YYYY-MM-DD, blank to keep): ");
        if (dob != null) existing.setDateOfBirth(dob);

        System.out.println("Current notes: " + existing.getNotes());
        System.out.print("New notes: ");
        String notes = scanner.nextLine();
        if (!notes.isBlank()) existing.setNotes(notes);

        Pet updated = petService.updatePet(id, existing);
        System.out.println("Updated pet: " + updated.getId());
    }

    private void deletePet() {
        Long id = readLong("Enter pet ID to delete: ");
        petService.deletePet(id);
        System.out.println("Deleted pet with ID: " + id);
    }

    // --- VET VISIT OPERATIONS ---

    private void createVetVisit() {
        Long petId = readLong("Enter pet ID for this visit: ");
        System.out.println("Creating visit for pet ID " + petId);

        LocalDate visitDate = readRequiredDate("Visit date (YYYY-MM-DD): ");

        System.out.print("Reason: ");
        String reason = scanner.nextLine();

        System.out.print("Vet name (optional): ");
        String vetName = scanner.nextLine();

        System.out.print("Notes (optional): ");
        String notes = scanner.nextLine();

        VetVisit visit = new VetVisit();
        visit.setVisitDate(visitDate);
        visit.setReason(reason);
        visit.setVetName(blankToNull(vetName));
        visit.setNotes(blankToNull(notes));

        VetVisit saved = vetVisitService.addVisit(petId, visit);
        System.out.println("Created vet visit with ID: " + saved.getId());
    }

    private void listVetVisitsForPet() {
        Long petId = readLong("Enter pet ID: ");
        List<VetVisit> visits = vetVisitService.getVisitsForPet(petId);
        if (visits.isEmpty()) {
            System.out.println("No visits found for pet ID " + petId);
        } else {
            System.out.println("Vet visits for pet ID " + petId + ":");
            for (VetVisit v : visits) {
                System.out.printf("ID: %d | Date: %s | Reason: %s | Vet: %s | Notes: %s%n",
                        v.getId(),
                        v.getVisitDate(),
                        v.getReason(),
                        v.getVetName(),
                        v.getNotes());
            }
        }
    }

    private void viewVetVisit() {
        Long id = readLong("Enter vet visit ID: ");
        VetVisit v = vetVisitService.getVisit(id);
        System.out.println("Vet visit details:");
        System.out.println("ID: " + v.getId());
        System.out.println("Pet ID: " + v.getPet().getId());
        System.out.println("Date: " + v.getVisitDate());
        System.out.println("Reason: " + v.getReason());
        System.out.println("Vet: " + v.getVetName());
        System.out.println("Notes: " + v.getNotes());
    }

    private void updateVetVisit() {
        Long id = readLong("Enter vet visit ID to update: ");
        VetVisit existing = vetVisitService.getVisit(id);

        System.out.println("Leave field blank to keep existing value.");

        System.out.println("Current date: " + existing.getVisitDate());
        LocalDate newDate = readDateOrNull("New date (YYYY-MM-DD, blank to keep): ");
        if (newDate != null) existing.setVisitDate(newDate);

        System.out.println("Current reason: " + existing.getReason());
        System.out.print("New reason: ");
        String reason = scanner.nextLine();
        if (!reason.isBlank()) existing.setReason(reason);

        System.out.println("Current vet name: " + existing.getVetName());
        System.out.print("New vet name: ");
        String vetName = scanner.nextLine();
        if (!vetName.isBlank()) existing.setVetName(vetName);

        System.out.println("Current notes: " + existing.getNotes());
        System.out.print("New notes: ");
        String notes = scanner.nextLine();
        if (!notes.isBlank()) existing.setNotes(notes);

        VetVisit updated = vetVisitService.updateVisit(id, existing);
        System.out.println("Updated vet visit: " + updated.getId());
    }

    private void deleteVetVisit() {
        Long id = readLong("Enter vet visit ID to delete: ");
        vetVisitService.deleteVisit(id);
        System.out.println("Deleted vet visit with ID: " + id);
    }

    // --- Helper methods ---

    private Long readLong(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Long.parseLong(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private LocalDate readDateOrNull(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (input.isBlank()) {
            return null;
        }
        try {
            return LocalDate.parse(input);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format, expected YYYY-MM-DD. Keeping existing / using null.");
            return null;
        }
    }

    private LocalDate readRequiredDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format, expected YYYY-MM-DD. Please try again.");
            }
        }
    }

    private String blankToNull(String s) {
        return (s == null || s.isBlank()) ? null : s;
    }
}
