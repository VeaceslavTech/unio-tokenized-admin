package at.unio.admin.data.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import at.unio.admin.data.entity.ImpactCalculation;

@Service
public class ImpactCalculationService {

    private final ImpactCalculationRepository repository;

    public ImpactCalculationService(ImpactCalculationRepository repository) {
        this.repository = repository;
    }

    public Optional<ImpactCalculation> get(Long id) {
        return repository.findById(id);
    }

    public ImpactCalculation update(ImpactCalculation entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<ImpactCalculation> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<ImpactCalculation> list(Pageable pageable, Specification<ImpactCalculation> filter) {
        return repository.findAll(filter, pageable);
    }
    public int count() {
        return (int) repository.count();
    }

}
