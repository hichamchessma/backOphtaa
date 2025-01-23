package com.ophta.service;

import com.ophta.entity.Medecin;
import com.ophta.repository.MedecinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MedecinService {
    
    private final MedecinRepository medecinRepository;

    @Autowired
    public MedecinService(MedecinRepository medecinRepository) {
        this.medecinRepository = medecinRepository;
    }

    public List<Medecin> findAll() {
        return medecinRepository.findAll();
    }

    public Optional<Medecin> findById(Long id) {
        return medecinRepository.findById(id);
    }

    public Medecin save(Medecin medecin) {
        return medecinRepository.save(medecin);
    }

    public void deleteById(Long id) {
        medecinRepository.deleteById(id);
    }
}
