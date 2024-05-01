package at.unio.admin.views.portofoliooverview;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BorderRadius;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.FontWeight;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import com.vaadin.flow.theme.lumo.LumoUtility.Width;

import at.unio.admin.data.entity.Property;

public class PropertyOverviewViewCard extends ListItem {

	private static final long serialVersionUID = 1L;
	private Property property;
	

	
    public PropertyOverviewViewCard(Property property) {
        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                BorderRadius.LARGE);

        Div div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
        div.setHeight("160px");
		var firstPicture = property.getTitlePictureUrl() == null ? " " : property.getTitlePictureUrl();
        Image image = new Image();
        image.setWidth("100%");
        image.setSrc(firstPicture);
        div.add(image);

        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
		header.setText(property.getTitle());

        Span subtitle = new Span();
        subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle.setText(property.getDescription());

        Paragraph descriptionField = new Paragraph(
                property.getDescription());
        descriptionField.addClassName(Margin.Vertical.MEDIUM);

        Button badge = new Button("Edit",clickEvent -> {
            UI.getCurrent().navigate("property/edit/" + property.getId());
        });
        badge.getElement().setAttribute("theme", "badge");
        badge.setText("Edit");

        add(div, header, subtitle, descriptionField, badge);
}
}
