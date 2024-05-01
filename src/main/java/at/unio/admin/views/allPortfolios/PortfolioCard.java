package at.unio.admin.views.allPortfolios;

import at.unio.admin.data.entity.Portfolio;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.theme.lumo.LumoUtility.*;

public class PortfolioCard extends ListItem {

	private static final long serialVersionUID = 1L;

	
    public PortfolioCard(Portfolio portfolio) {
        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                BorderRadius.LARGE);

        Div div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
        div.setHeight("160px");
//        var pictures = property.getPictureUrls();
//		var firstPicture = pictures.isEmpty() ? " " : pictures.get(0); // TODO empty picture
//        Image image = new Image();
//        image.setWidth("100%");
//        image.setSrc(firstPicture);
//        div.add(image);

        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
		header.setText(portfolio.getTitle());

        Span subtitle = new Span();
        subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle.setText(portfolio.getDescription());

        Paragraph descriptionField = new Paragraph(
                "Total size : "+portfolio.getTotalSize()+" m2");
        descriptionField.addClassName(Margin.Vertical.SMALL);

        Paragraph rentalIncome = new Paragraph(
                "Rental Income : "+portfolio.getRentalIncome() + " €");
        descriptionField.addClassName(Margin.Vertical.SMALL);

        Paragraph amountOfTenats = new Paragraph(
                "Amount of tenants : "+portfolio.getAmountOfTenants());
        descriptionField.addClassName(Margin.Vertical.SMALL);

        Paragraph valuation = new Paragraph(
                "Purchase price /m2 : "+portfolio.getPurchasePriceM2());
        descriptionField.addClassName(Margin.Vertical.SMALL);
        Paragraph yield = new Paragraph(
                "Yield : "+portfolio.getYield());
        Paragraph type = new Paragraph(
                "Type : "+portfolio.getPortfolioTyp().toString());
        descriptionField.addClassName(Margin.Vertical.SMALL);


        Button badge = new Button("Edit",clickEvent -> {
            UI.getCurrent().navigate("portfolio/edit/" + portfolio.getId());
        });
        badge.getElement().setAttribute("theme", "badge");
        badge.setText("Edit");

        add(div, header, subtitle, descriptionField,rentalIncome,amountOfTenats,valuation,yield,type, badge);
}
}
