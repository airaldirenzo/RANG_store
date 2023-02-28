package ar.com.tpfinal.rang_store.data.filter;

import androidx.annotation.NonNull;

public class FilterObject {

    private static FilterObject INSTANCE;

    private String titleFilter;

    private Integer categoryId;
    private String minPriceFilter;
    private String maxPriceFilter;

    public static FilterObject getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FilterObject();
        }
        return INSTANCE;
    }

    public FilterObject() {
        this.titleFilter = "";
        this.categoryId = null;
        this.minPriceFilter = "0.00001";
        this.maxPriceFilter = "99999999";
    }

    public void resetFilter() {
        this.titleFilter = "";
        this.categoryId = null;
        this.minPriceFilter = "0.00001";
        this.maxPriceFilter = "99999999";
    }

    public String getTitleFilter() {
        return titleFilter;
    }
    public void setTitleFilter(String titleFilter) {
        this.titleFilter = titleFilter;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer category) {
        this.categoryId = category;
    }

    public String getMinPriceFilter() {
        return minPriceFilter;
    }

    public void setMinPriceFilter(String minPriceFilter) {
        // Esto es necesario porque el rango minimo tiene que ser mayor que 0.
        // Seteamos default a 0.00001 el inicio y chequeamos esto para mantenerlo.
        if (minPriceFilter.isEmpty()) {
            return;
        }
        else if (Double.parseDouble(minPriceFilter) == 0) {
            return;
        }
        this.minPriceFilter = minPriceFilter;
    }

    public String getMaxPriceFilter() {
        return maxPriceFilter;
    }

    public void setMaxPriceFilter(String maxPriceFilter) {
        if (maxPriceFilter.isEmpty()) {
            return;
        }
        this.maxPriceFilter = maxPriceFilter;
    }

    @NonNull
    @Override
    public String toString() {
        return "FilterObject{" +
                "titleFilter='" + titleFilter + '\'' +
                ", categoryId=" + categoryId +
                ", minPriceFilter='" + minPriceFilter + '\'' +
                ", maxPriceFilter='" + maxPriceFilter + '\'' +
                '}';
    }
}
