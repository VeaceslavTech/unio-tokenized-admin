package at.unio.admin.data.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import at.unio.admin.data.entity.ImpactCalculation;

public interface ImpactCalculationRepository
        extends
            JpaRepository<ImpactCalculation, Long>,
            JpaSpecificationExecutor<ImpactCalculation> {

}
