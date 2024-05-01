package at.unio.admin.views.portofoliooverview;


import at.unio.admin.data.entity.Portfolio;
import at.unio.admin.data.entity.enums.PortfolioType;
import at.unio.admin.data.service.PortfolioService;
import at.unio.admin.data.service.PropertyService;
import at.unio.admin.views.MainLayout;
import at.unio.admin.views.portfolioStatsForm.PortfolioForm;

import com.vaadin.flow.router.*;

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
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextAlignment;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import jakarta.annotation.security.RolesAllowed;
//import com.storedobject.chart.*;

@PageTitle("Portofolio Overview")
@Route(value = "portfolio/edit/:entityId", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class PortofolioOverviewView extends Main implements HasComponents, HasStyle, HasUrlParameter<String> {

	private OrderedList imageContainer;

	private final PortfolioService portfolioService;
	private final PropertyService propertyService;
	private PortfolioForm form;
    private Long portfolioId;
    private Portfolio selectedPortfolio;


	public PortofolioOverviewView(PortfolioService service, PropertyService propertyService) {
		this.portfolioService = service;
		this.propertyService = propertyService;
	}

	private void constructUI() {
		var portfolio = selectedPortfolio;
        addClassNames("portofolio-overview-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        VerticalLayout headerContainer = new VerticalLayout();
        var headerBox = new VerticalLayout();

        
        H2 header = new H2(portfolio.getTitle());
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE,TextAlignment.CENTER );
        Paragraph description = new Paragraph(portfolio.getDescription());
        description.addClassNames(Margin.Bottom.XLARGE, Margin.Top.NONE, TextColor.SECONDARY);
        this.form = new PortfolioForm();
        form.setPortfolio(portfolio);
        var headerBoxH = new HorizontalLayout();
        headerBoxH.add(headerBox);
        headerBoxH.setSizeFull();
        headerBox.add(header,description);
        headerBox.setSizeFull();
        headerContainer.add(headerBoxH,this.form);
        imageContainer = new OrderedList();
        imageContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);
        container.add(headerContainer);
        add(container, imageContainer);

    }


//	private SOChart setupChart() {
//        SOChart soChart = new SOChart();
//        soChart.setSize("500px", "300px");
//        String[] chartLabels = PortfolioType.getAllLabels();
//
//        CategoryData labels = new CategoryData(chartLabels);
//        Data data = new Data(40, 20, 30);
//
//        NightingaleRoseChart nc = new NightingaleRoseChart(labels, data);
//        Position p = new Position();
//        p.setTop(Size.percentage(50));
//        nc.setPosition(p);
//        soChart.add(nc);
//        return soChart;
//	}

    @Override
    public void setParameter(BeforeEvent event, @WildcardParameter String entityId) {
        var first = event.getRouteParameters().get("entityId").get();
        this.portfolioId = Long.valueOf(first);
        selectedPortfolio = portfolioService.getPortfolio(portfolioId);
        constructUI();
        for (var property : propertyService.getAllProperties()) {
            var card = new PropertyOverviewViewCard(property);
            imageContainer.add(card);
        }
    }
}
