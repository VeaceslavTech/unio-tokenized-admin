package at.unio.admin.views.allPortfolios;


import at.unio.admin.data.entity.enums.PortfolioType;
import at.unio.admin.data.service.PortfolioService;
import at.unio.admin.data.service.PropertyService;
import at.unio.admin.views.MainLayout;
import at.unio.admin.views.portfolioStatsForm.PortfolioForm;
//import com.storedobject.chart.Position;
//import com.storedobject.chart.*;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("All Portfolios")
@Route(value = "all-portfolios", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class AllPortfoliosView extends Main implements HasComponents, HasStyle {
    private OrderedList imageContainer;
    private final PortfolioService portfolioService;
    private PortfolioForm form;
    private TextField title;
    private TextField description;
    private NumberField totalSize;
    private NumberField rentalIncome;
    private NumberField amountOfTenants;
    private NumberField purchasePriceM2;
    private NumberField yield;
    private ComboBox<at.unio.admin.data.entity.enums.PortfolioStatus> portfolioTyp;
    private ComboBox<PortfolioType> portolioTyp;

    public AllPortfoliosView(PortfolioService service) {
        this.portfolioService = service;
        constructUI();
        for (var property : portfolioService.getAllPortfolios()) {
            var card = new PortfolioCard(property);
            imageContainer.add(card);
        }
    }

    private void constructUI() {
        addClassNames("portofolio-overview-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        VerticalLayout headerContainer = new VerticalLayout();
        var headerBox = new VerticalLayout();
        imageContainer = new OrderedList();
        imageContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);
        //, sortBy
        container.add(headerContainer);
        add(container, imageContainer);
    }

}
