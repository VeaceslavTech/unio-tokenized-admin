package at.unio.admin.views.portfolioStatsForm;
import at.unio.admin.util.Notifications;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import at.unio.admin.data.entity.enums.*;
import at.unio.admin.data.entity.Portfolio;


public class PortfolioForm extends FormLayout {
	
	private TextField title = new TextField("Title");
    private TextField description = new TextField("Description");
    private NumberField totalSize = new NumberField("Total Size");
    private NumberField rentalIncome = new NumberField("Rental Income");
    private NumberField amountOfTenants = new NumberField("Amount of Tenants");
    private NumberField purchasePriceM2 = new NumberField("Purchase Price/m2");
    private NumberField yield = new NumberField("Yield");
    private ComboBox<PortfolioType> portfolioTypeComboBox = new ComboBox<>("Portfolio Type");
    private ComboBox<PortfolioStatus> portfolioStatusComboBox = new ComboBox<>("Portfolio Status");
    private Button saveButton;
    private Portfolio portfolio;

    private Binder<Portfolio> binder = new Binder<>(Portfolio.class);

    public PortfolioForm() {
        binder.bind(title, Portfolio::getTitle, Portfolio::setTitle);
        binder.bind(description, Portfolio::getDescription, Portfolio::setDescription);
        binder.bind(totalSize, Portfolio::getTotalSize, Portfolio::setTotalSize);
        binder.bind(rentalIncome, Portfolio::getRentalIncome, Portfolio::setRentalIncome);
        binder.bind(purchasePriceM2, Portfolio::getPurchasePriceM2, Portfolio::setPurchasePriceM2);
        binder.bind(yield, Portfolio::getYield, Portfolio::setYield);
        binder.bind(portfolioTypeComboBox, Portfolio::getPortfolioTyp, Portfolio::setPortfolioTyp);
        binder.bind(portfolioStatusComboBox, Portfolio::getStatus, Portfolio::setStatus);

        portfolioTypeComboBox.setItems(PortfolioType.values());
        portfolioStatusComboBox.setItems(PortfolioStatus.values());
        saveButton = new Button();
        saveButton.getElement().setAttribute("theme", "primary");
        saveButton.setText("Save");
        add(title, description, totalSize, rentalIncome, amountOfTenants,
                purchasePriceM2, yield, portfolioTypeComboBox, portfolioStatusComboBox,saveButton);
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
        binder.readBean(portfolio);
    }


}