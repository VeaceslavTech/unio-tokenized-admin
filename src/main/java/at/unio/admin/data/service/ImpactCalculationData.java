package at.unio.admin.data.service;

import at.unio.admin.data.entity.ImpactCalculation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "impact_calculation_data")
@Getter
@Setter
public class ImpactCalculationData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer investmentDuration;
    private Double availableAnnualContribution;
    private Double desiredAssetAllocation;
    private Double totalInvestment;
    private Double totalValue;
    private Double profit;
    private Double profitPercentage;
    private Double totalValueGainPercentage;
    @OneToOne(mappedBy = "investmentPlanData",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private ImpactCalculation impactCalculation;
}
