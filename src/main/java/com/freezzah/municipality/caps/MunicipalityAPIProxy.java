package com.freezzah.municipality.caps;

public class MunicipalityAPIProxy implements IMunicipalityAPI {

    private static final MunicipalityAPIProxy ourInstance = new MunicipalityAPIProxy();
    @SuppressWarnings("FieldCanBeLocal")
    private IMunicipalityAPI apiInstance;

    public static MunicipalityAPIProxy getInstance() {
        return ourInstance;
    }

    public void setApiInstance(final IMunicipalityAPI apiInstance) {
        this.apiInstance = apiInstance;
    }
}
