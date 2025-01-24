package com.ophta.controller;

import com.ophta.entity.DossierMedical;
import com.ophta.service.DossierMedicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dossier-medical")
@CrossOrigin(origins = "*")
public class DossierMedicalController {

    private final DossierMedicalService dossierMedicalService;

    @Autowired
    public DossierMedicalController(DossierMedicalService dossierMedicalService) {
        this.dossierMedicalService = dossierMedicalService;
    }

    @GetMapping
    public List<DossierMedical> getAllDossiersMedicaux() {
        return dossierMedicalService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DossierMedical> getDossierMedicalById(@PathVariable Long id) {
        return dossierMedicalService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public DossierMedical createDossierMedical(@RequestBody DossierMedical dossierMedical) {
        return dossierMedicalService.save(dossierMedical);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DossierMedical> updateDossierMedical(@PathVariable Long id, @RequestBody DossierMedical dossierMedical) {
        return dossierMedicalService.findById(id)
                .map(existingDossierMedical -> {
                    dossierMedical.setId(id);
                    return ResponseEntity.ok(dossierMedicalService.save(dossierMedical));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDossierMedical(@PathVariable Long id) {
        return dossierMedicalService.findById(id)
                .map(dossierMedical -> {
                    dossierMedicalService.deleteById(id);
                     return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
