package at.unio.admin.views.impactplans;

import at.unio.admin.data.entity.ImpactCalculation;
import at.unio.admin.data.service.ImpactCalculationService;
import at.unio.admin.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.function.SerializableSupplier;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

@PageTitle("Impact Plans")
@Route(value = "impact-plans", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@Uses(Icon.class)
public class ImpactPlansView extends Div {

    private Grid<ImpactCalculation> grid;

    private Filters filters;
    private final ImpactCalculationService samplePersonService;



    public ImpactPlansView(ImpactCalculationService SamplePersonService) {
        this.samplePersonService = SamplePersonService;
        setSizeFull();
        addClassNames("impact-plans-view");

        // Ensure grid is created before setting up filters that might refresh it
        Component grid = createGrid();
        filters = new Filters(() -> refreshGrid());

        VerticalLayout layout = new VerticalLayout(createMobileFilters(), filters, grid);
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        add(layout);
    }

    private HorizontalLayout createMobileFilters() {
        // Mobile version
        HorizontalLayout mobileFilters = new HorizontalLayout();
        mobileFilters.setWidthFull();
        mobileFilters.addClassNames(LumoUtility.Padding.MEDIUM, LumoUtility.BoxSizing.BORDER,
                LumoUtility.AlignItems.CENTER);
        mobileFilters.addClassName("mobile-filters");

        Icon mobileIcon = new Icon("lumo", "plus");
        Span filtersHeading = new Span("Filters");
        mobileFilters.add(mobileIcon, filtersHeading);
        mobileFilters.setFlexGrow(1, filtersHeading);
        mobileFilters.addClickListener(e -> {
            if (filters.getClassNames().contains("visible")) {
                filters.removeClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:plus");
            } else {
                filters.addClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:minus");
            }
        });
        return mobileFilters;
    }

    public static class Filters extends Div implements Specification<ImpactCalculation> {

        private final TextField email = new TextField("Email");
        private final DatePicker startDate = new DatePicker("Date of Creation");
        private final DatePicker endDate = new DatePicker();

        public Filters(Runnable onSearch) {

            setWidthFull();
            addClassName("filter-layout");
            addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.MEDIUM,
                    LumoUtility.BoxSizing.BORDER);
            email.setPlaceholder("Investor Email");
            // Action buttons
            Button resetBtn = new Button("Reset");
            resetBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            resetBtn.addClickListener(e -> {
                email.clear();
                startDate.clear();
                endDate.clear();
                onSearch.run();
            });
            Button searchBtn = new Button("Search");
            searchBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            searchBtn.addClickListener(e -> onSearch.run());
            Div actions = new Div(resetBtn, searchBtn);
            actions.addClassName(LumoUtility.Gap.SMALL);
            actions.addClassName("actions");

            add(email, createDateRangeFilter(),actions);
        }

        private Component createDateRangeFilter() {
            startDate.setPlaceholder("From");

            endDate.setPlaceholder("To");

            // For screen readers
            setAriaLabel(startDate, "From date");
            setAriaLabel(endDate, "To date");

            FlexLayout dateRangeComponent = new FlexLayout(startDate, new Text(" – "), endDate);
            dateRangeComponent.setAlignItems(FlexComponent.Alignment.BASELINE);
            dateRangeComponent.addClassName(LumoUtility.Gap.XSMALL);

            return dateRangeComponent;
        }

        private void setAriaLabel(DatePicker datePicker, String label) {
            datePicker.getElement().executeJs("const input = this.inputElement;" //
                    + "input.setAttribute('aria-label', $0);" //
                    + "input.removeAttribute('aria-labelledby');", label);
        }

        @Override
        public Predicate toPredicate(Root<ImpactCalculation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<>();

            if (!email.isEmpty()) {
                String lowerCaseFilter = email.getValue().toLowerCase();
                Predicate firstNameMatch = criteriaBuilder.like(criteriaBuilder.lower(root.get("email")),
                        lowerCaseFilter + "%");
                predicates.add(criteriaBuilder.or(firstNameMatch));
            }
            
            if (startDate.getValue() != null) {
                String databaseColumn = "createDt";
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(databaseColumn),
                        criteriaBuilder.literal(startDate.getValue())));
            }
            if (endDate.getValue() != null) {
                String databaseColumn = "createDt";
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.literal(endDate.getValue()),
                        root.get(databaseColumn)));
            }
            
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        }

        private String ignoreCharacters(String characters, String in) {
            String result = in;
            for (int i = 0; i < characters.length(); i++) {
                result = result.replace("" + characters.charAt(i), "");
            }
            return result;
        }

        private Expression<String> ignoreCharacters(String characters, CriteriaBuilder criteriaBuilder,
                Expression<String> inExpression) {
            Expression<String> expression = inExpression;
            for (int i = 0; i < characters.length(); i++) {
                expression = criteriaBuilder.function("replace", String.class, expression,
                        criteriaBuilder.literal(characters.charAt(i)), criteriaBuilder.literal(""));
            }
            return expression;
        }



    }

    private Component createGrid() {
        grid = new Grid<>(ImpactCalculation.class, false);
        grid.addColumn(ImpactCalculation::getName).setHeader("name").setAutoWidth(true);
        grid.addColumn(ImpactCalculation::getEmail).setHeader("email").setAutoWidth(true);
        grid.addColumn(ImpactCalculation::getStatus).setHeader("status").setAutoWidth(true);
        grid.addColumn(new LocalDateTimeRenderer<>(
                        ImpactCalculation::getCreateDt,
                        "dd-MM-yyyy HH:mm"
                ))
                .setHeader("Create Date")
                .setAutoWidth(true);
        grid.setItems(query -> samplePersonService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)),
                filters).stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        return grid;
    }

    private void refreshGrid() {
        grid.getDataProvider().refreshAll();
    }

}
