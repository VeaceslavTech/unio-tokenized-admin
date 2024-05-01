package at.unio.admin.data.entity;

import at.unio.admin.data.service.ImpactCalculationData;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "saved_impact_calculation")
@Getter
@Setter
public class ImpactCalculation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email
    private String email;
    private String name;
    private Long status;
    private LocalDateTime createDt;
    @OneToOne
    @JoinColumn(name = "investment_plan_id")
    private ImpactCalculationData investmentPlanData;
}
