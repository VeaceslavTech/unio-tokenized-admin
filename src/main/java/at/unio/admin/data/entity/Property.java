package at.unio.admin.data.entity;

import java.util.ArrayList;
import java.util.List;

import at.unio.admin.data.entity.enums.PortfolioStatus;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String title;
    @Column
    private String location;
    @Column
    private double evaluation;
    @Column
    private String description;
    @Column
    private double totalSize;
    @Column
    private double rentalIncome;
    @Column
    private double amountOfTenants;
    @Column(name = "purchase_price_m2")
    private double purchasePriceM2;
    @Column
    private double yield;
    @Column
    private double assetDistribution;
    @Column
    private double returnOnInvestment;
    @Column
    private String waiverOfTermination;
    @Column
    private String titlePictureUrl;


    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @Enumerated(EnumType.STRING)
    private PortfolioStatus status = PortfolioStatus.ACTIVE;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "property_picture_urls", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "picture_urls")
    private List<String> pictureUrls = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "property_document_urls", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "document_urls")
    private List<String> documentUrls = new ArrayList<>();


}