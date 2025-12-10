package quru.qa.data;

public enum WineFilters {
    PRICE("Цена"),
    TYPE_OF_SUGAR("Содержание сахара"),
    COUNTRY("Страна"),
    ADDITIONAL_VINE("Ещё вина"),
    TYPE_GRAPE("Сорт винограда");

    public final String wineFiltersName;
    WineFilters(String wineFiltersName) {
        this.wineFiltersName = wineFiltersName;
    }


}
