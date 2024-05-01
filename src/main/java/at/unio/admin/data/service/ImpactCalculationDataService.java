package at.unio.admin.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImpactCalculationDataService {

    @Autowired
    private ImpactCalculationDataRepository repository;

    public ImpactCalculationData saveImpactCalculationData(ImpactCalculationData data) {
        return repository.save(data);
    }
}