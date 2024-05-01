package at.unio.admin.views.propertyEdit;

import at.unio.admin.data.entity.Property;
import at.unio.admin.data.entity.enums.PortfolioStatus;
import at.unio.admin.data.service.PropertyService;
import at.unio.admin.data.storage.LocalStorage;
import at.unio.admin.util.Notifications;
import at.unio.admin.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.FinishedEvent;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.RolesAllowed;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Route(value = "property/edit/:entityId", layout = MainLayout.class)
@PageTitle("Property Edit")
@RolesAllowed("ADMIN")
@CssImport("./themes/uniotokenizedadmin/views/portfolio-bearbeiten-view.css")
public class PropertyEdit extends Main implements HasComponents, HasUrlParameter<String> {
    public static String server_url = "http://116.203.76.85:8080";

    private H2 pageTitle;
    private final PropertyService service;

    private VerticalLayout singleImageContainer = new VerticalLayout();
    private OrderedList imageContainer;
    private MemoryBuffer imageBuffer;
    private MemoryBuffer documentBuffer;
    private Upload imageUpload = new Upload();
    private Upload documentsUpload = new Upload();
    private Long entityId;
    private Property selectedProperty;
    private TextField title = new TextField("Title");
    private TextField description = new TextField("Description");
    private TextField location = new TextField("Location");
    private NumberField totalSize = new NumberField("Size m2");
    private NumberField buyingPrice = new NumberField("Buying Price/Evaluation");
    private NumberField rentalIncome = new NumberField("Rental Income");

    private NumberField amountOfTenants = new NumberField("Amount of Tenants");
    private NumberField purchasePriceM2 = new NumberField("Purchase Price/m2");
    private NumberField yield = new NumberField("Yield");
    private TextField rendite = new TextField("Rendite");


    private TextField waiverOfTermination = new TextField("Waiver of Termination");

    private Grid<String> imagesGrid = new Grid<>();
    private Grid<String> documentsGrid = new Grid<>();
    private ComboBox<PortfolioStatus> portfolioStatusComboBox = new ComboBox<>("Property Status");
    private Button saveButton;

    private Binder<Property> binder = new Binder<>(Property.class);


    public PropertyEdit(PropertyService service) {
        this.service = service;
        initForm();
        initUploads();
        initGrids();
        bindForm();
    }

    private void initForm() {
        documentsUpload.setUploadButton(new Button("Upload Documents"));
        imageUpload.setUploadButton(new Button("Upload Images"));
        title.setLabel("Title");
        title.setWidth("50%");
        title.getElement().setAttribute("title", "Title of the property");
        title.setPrefixComponent(new Icon(VaadinIcon.HOME)); // add icon
        location.setWidth(50, Unit.PERCENTAGE);
        location.setPrefixComponent(new Icon(VaadinIcon.MAP_MARKER));
        description.setLabel("Description");
        description.setWidth("50%");
        description.getElement().setAttribute("title", "Description of the property");
        description.setPrefixComponent(new Icon(VaadinIcon.CLIPBOARD_TEXT)); // add icon
        waiverOfTermination.setTitle("Waiver of Termination");
        waiverOfTermination.setWidth("50%");
        waiverOfTermination.setPrefixComponent(new Icon(VaadinIcon.EXCLAMATION_CIRCLE));
        totalSize.setLabel("Total Size");
        totalSize.setWidth("50%");
        totalSize.setPrefixComponent(new Icon(VaadinIcon.EXPAND_SQUARE)); // add icon

        buyingPrice.setLabel("Buying Price/Evaluation");
        buyingPrice.setWidth("50%");
        buyingPrice.setPrefixComponent(new Icon(VaadinIcon.DOLLAR));
        rentalIncome.setLabel("Rental Income");
        rentalIncome.setWidth("50%");
        rentalIncome.setPrefixComponent(new Icon(VaadinIcon.COINS)); // add icon

        amountOfTenants.setLabel("Amount of Tenants");
        amountOfTenants.setWidth("50%");
        amountOfTenants.setPrefixComponent(new Icon(VaadinIcon.USERS)); // add icon

        purchasePriceM2.setLabel("Purchase Price/m2");
        purchasePriceM2.setWidth("50%");
        purchasePriceM2.setPrefixComponent(new Icon(VaadinIcon.EURO)); // add icon

        yield.setLabel("Yield");
        yield.setWidth("50%");
        yield.setPrefixComponent(new Icon(VaadinIcon.LINE_CHART)); // add icon

        portfolioStatusComboBox.setLabel("Property Status");
        portfolioStatusComboBox.setWidth("50%");
        portfolioStatusComboBox.setPrefixComponent(new Icon(VaadinIcon.CLIPBOARD_CHECK)); // add icon
        portfolioStatusComboBox.setItems(PortfolioStatus.values());
    }

    private void uploadImage(FinishedEvent e) {
        var filename = cleanUp(e.getFileName());
        String keyName = filename;
        try {
            var localImageUrl = LocalStorage.storeFile(System.getProperty("user.dir") + "/storage/images/", keyName, imageBuffer.getInputStream());
            var imageUrl = server_url + "/images/" + keyName;
            selectedProperty.getPictureUrls().add(imageUrl);
            imagesGrid.getDataProvider().refreshAll();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void uploadDocument(FinishedEvent e) {
        try {
            var filename = cleanUp(e.getFileName());
            String keyName = filename;
            var localDocumentUrl = LocalStorage.storeFile(System.getProperty("user.dir") + "/storage/documents/", keyName, documentBuffer.getInputStream());
            var documentUrl = server_url + "/documents/" + keyName;
            selectedProperty.getDocumentUrls().add(documentUrl);
            documentsGrid.getDataProvider().refreshAll();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void initUploads() {
        imageBuffer = new MemoryBuffer();
        documentBuffer = new MemoryBuffer();
        imageUpload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        imageUpload.setReceiver(imageBuffer);
        imageUpload.addSucceededListener(this::uploadImage);

        documentsUpload.setAcceptedFileTypes("application/pdf", ".doc", ".docx");
        documentsUpload.setReceiver(documentBuffer);
        documentsUpload.addSucceededListener(this::uploadDocument);
    }

    String cleanUp(String fileName) {
        return fileName.replace(" ", "");
    }

    private void initGrids() {
        imagesGrid.addColumn(this::getFileName).setHeader("Image Files");
        imagesGrid.addComponentColumn(this::createSetAsTitleButton).setHeader("Set as Title");
        imagesGrid.addComponentColumn(this::createShowButtonForImage).setHeader("Show");
        imagesGrid.addComponentColumn(this::createRemoveButtonForImage).setHeader("Remove");
        documentsGrid.addColumn(this::getFileName).setHeader("Document Files");
        documentsGrid.addComponentColumn(this::createShowButtonForDocument).setHeader("Show");
        documentsGrid.addComponentColumn(this::createRemoveButtonForDocument).setHeader("Remove");
    }

    private void bindForm() {
        binder.bind(title, Property::getTitle, Property::setTitle);
        binder.bind(description, Property::getDescription, Property::setDescription);
        binder.bind(location, Property::getLocation, Property::setLocation);
        binder.bind(totalSize, Property::getTotalSize, Property::setTotalSize);
        binder.bind(rentalIncome, Property::getRentalIncome, Property::setRentalIncome);
        binder.bind(purchasePriceM2, Property::getPurchasePriceM2, Property::setPurchasePriceM2);
        binder.bind(yield, Property::getYield, Property::setYield);
        binder.bind(portfolioStatusComboBox, Property::getStatus, Property::setStatus);
        binder.bind(amountOfTenants, Property::getAmountOfTenants, Property::setAmountOfTenants);
        binder.bind(buyingPrice, Property::getEvaluation, Property::setEvaluation);
        binder.bind(waiverOfTermination, Property::getWaiverOfTermination, Property::setWaiverOfTermination);
        binder.bind(portfolioStatusComboBox, Property::getStatus, Property::setStatus);
    }

    @Override
    public void setParameter(BeforeEvent event, @WildcardParameter String entityId) {
        this.entityId = Long.valueOf(event.getRouteParameters().get("entityId").get());
        this.selectedProperty = service.getProperty(this.entityId);
        binder.readBean(selectedProperty);
        imagesGrid.setItems(selectedProperty.getPictureUrls());
        documentsGrid.setItems(selectedProperty.getDocumentUrls());
        pageTitle = new H2("Edit Property");
        setupFormLayout();
    }

    private void setupFormLayout() {
        saveButton = new Button("Save", click -> save());
        saveButton.getElement().setAttribute("theme", "primary");
        var layout = new VerticalLayout(title, description, location, totalSize, buyingPrice, rentalIncome,
                purchasePriceM2, amountOfTenants, waiverOfTermination, yield, portfolioStatusComboBox,
                imageUpload, documentsUpload, documentsGrid, imagesGrid, saveButton);
        layout.setSizeFull();
        layout.setAlignItems(Alignment.CENTER);
        var verticalLayout = new VerticalLayout();
        imageContainer = new OrderedList();
        imageContainer.addClassName("image-container");
        verticalLayout.setAlignItems(Alignment.STRETCH);
        verticalLayout.add(imageContainer);
        add(verticalLayout);
        setupTitlePicture();
        add(layout);
    }
    private Component createSetAsTitleButton(String url) {
        Button setAsTitleButton = new Button("Set as Title");
        setAsTitleButton.addClickListener(click -> setTitlePicture(url));
        return setAsTitleButton;
    }
    private void setTitlePicture(String url) {
        selectedProperty.setTitlePictureUrl(url);  // Replace with your method
        service.saveProperty(selectedProperty); // Persist the changes
        setupTitlePicture();                    // Refresh the displayed title picture
    }
    public void setupTitlePicture() {
        imageContainer.removeAll(); // Clear previous title picture
        String url = selectedProperty.getTitlePictureUrl(); // Replace with your method
        if (url != null && !url.isEmpty()) {
            Image image = new Image(url, "Title Image");
            image.setWidth("80%");
            singleImageContainer.add(image);
            singleImageContainer.setWidthFull();
            imageContainer.add(singleImageContainer);
        }
    }


    private Button createRemoveButtonForImage(String url) {
        return new Button("Remove", clickEvent -> removeImage(url));
    }

    private Button createRemoveButtonForDocument(String documentUrl) {
        return new Button("Remove", clickEvent -> removeDocument(documentUrl));
    }

    private void removeImage(String url) {
        selectedProperty.getPictureUrls().remove(url);
    }

    private void removeDocument(String document) {
        selectedProperty.getDocumentUrls().remove(document);
    }

    private String getFileName(String pathOrUrl) {
        try {
            URI uri = new URI(pathOrUrl);
            String path = uri.getPath();
            return path.substring(path.lastIndexOf('/') + 1);
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }


    private void save() {
        if (binder.validate().isOk()) {
            try {
                binder.writeBean(selectedProperty);
                service.saveProperty(selectedProperty);
                Notifications.getSuccess("Property successfully saved!").open();
            } catch (ValidationException e) {
                e.printStackTrace(); // Log error
            }
        }
    }

    private Component createShowButtonForImage(String url) {
        Image image = new Image();
        image.setSrc(url);
        image.setWidth("500px"); // Set the width of the image
        image.setHeight("500px"); // Set the height of the image
        Button showButton = new Button(new Icon(VaadinIcon.EYE));
        showButton.addClickListener(event -> {
            Dialog dialog = new Dialog();
            dialog.add(image);
            dialog.open();
        });
        return showButton;
    }

    private Component createShowButtonForDocument(String documentUrl) {
        Button showButton = new Button(new Icon(VaadinIcon.EYE));
        showButton.addClickListener(event -> {
            showButton.getUI().ifPresent(ui -> ui.getPage().open(documentUrl, "_blank"));
        });
        HorizontalLayout layout = new HorizontalLayout(showButton);
        return layout;
    }


}

