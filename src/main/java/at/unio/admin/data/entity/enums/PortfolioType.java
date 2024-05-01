package at.unio.admin.data.entity.enums;

import java.util.List;
import java.util.ArrayList;


public enum PortfolioType {
	    RESIDENTIAL_PROPERTIES("Residential Properties"),
	    BUSINESS_PROPERTIES("Business Properties"),
	    MIXED("Mixed");

	    private final String label;

	    PortfolioType(String label) {
	        this.label = label;
	    }

	    public String getLabel() {
	        return label;
	    }
	    public static String[] getAllLabels() {
	        List<String> list = new ArrayList<>();
	        for (PortfolioType portfolioType : PortfolioType.values()) {
	            list.add(portfolioType.getLabel());
	        }
	        return list.toArray(new String[0]);
	    }
	}