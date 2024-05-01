package at.unio.admin.data.entity;

import jakarta.persistence.*;
import java.util.List;

import at.unio.admin.data.entity.enums.PortfolioStatus;
import at.unio.admin.data.entity.enums.PortfolioType;

@Entity
public class Portfolio {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String title;
	private String description;
	private double totalSize;
	private double rentalIncome;
	private Long amountOfTenants;
	private double purchasePriceM2;
	private double yield;
    private double assetDistribution;
    private double returnOnInvestment;
    private double riskFactor;
    @Enumerated(EnumType.STRING)
    private PortfolioStatus status = PortfolioStatus.ACTIVE;
    @Enumerated(EnumType.STRING)
    private PortfolioType portfolioTyp = PortfolioType.RESIDENTIAL_PROPERTIES;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "portfolio")
    private List<Property> properties;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAssetDistribution() {
		return assetDistribution;
	}

	public void setAssetDistribution(double assetDistribution) {
		this.assetDistribution = assetDistribution;
	}

	public double getReturnOnInvestment() {
		return returnOnInvestment;
	}

	public void setReturnOnInvestment(double returnOnInvestment) {
		this.returnOnInvestment = returnOnInvestment;
	}

	public double getRiskFactor() {
		return riskFactor;
	}

	public void setRiskFactor(double riskFactor) {
		this.riskFactor = riskFactor;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	
	
	

	

	public double getPurchasePriceM2() {
		return purchasePriceM2;
	}

	public void setPurchasePriceM2(double purchasePriceM2) {
		this.purchasePriceM2 = purchasePriceM2;
	}

	public double getYield() {
		return yield;
	}

	public void setYield(double yield) {
		this.yield = yield;
	}

	public double getRentalIncome() {
		return rentalIncome;
	}

	public void setRentalIncome(double rentalIncome) {
		this.rentalIncome = rentalIncome;
	}

	public double getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(double totalSize) {
		this.totalSize = totalSize;
	}

	public PortfolioStatus getStatus() {
		return status;
	}

	public void setStatus(PortfolioStatus status) {
		this.status = status;
	}

	public PortfolioType getPortfolioTyp() {
		return portfolioTyp;
	}

	public void setPortfolioTyp(PortfolioType portfolioTyp) {
		this.portfolioTyp = portfolioTyp;
	}

	public Long getAmountOfTenants() {
		return amountOfTenants;
	}

	public void setAmountOfTenants(Long amountOfTenants) {
		this.amountOfTenants = amountOfTenants;
	}
	
	
}