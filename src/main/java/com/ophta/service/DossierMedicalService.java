package com.ophta.service;

import com.ophta.entity.DossierMedical;
import com.ophta.repository.DossierMedicalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DossierMedicalService {
    
    private final DossierMedicalRepository dossierMedicalRepository;

    @Autowired
    public DossierMedicalService(DossierMedicalRepository dossierMedicalRepository) {
        this.dossierMedicalRepository = dossierMedicalRepository;
    }

    public List<DossierMedical> findAll() {
        return dossierMedicalRepository.findAll();
    }

    public Optional<DossierMedical> findById(Long id) {
        return dossierMedicalRepository.findById(id);
    }

    public DossierMedical save(DossierMedical dossierMedical) {
        return dossierMedicalRepository.save(dossierMedical);
    }

    public void deleteById(Long id) {
        dossierMedicalRepository.deleteById(id);
    }
}
