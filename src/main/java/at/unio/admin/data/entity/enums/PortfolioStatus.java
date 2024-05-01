package at.unio.admin.data.entity.enums;

public enum PortfolioStatus {
	    ACTIVE("Active"),
	    INACTIVE("Inactive");

	    private final String label;

	    PortfolioStatus(String label) {
	        this.label = label;
	    }

	    public String getLabel() {
	        return label;
	    }
	}