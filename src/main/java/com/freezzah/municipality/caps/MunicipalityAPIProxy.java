package com.freezzah.municipality.caps;

public class MunicipalityAPIProxy implements IMunicipalityAPI {

    private static final MunicipalityAPIProxy ourInstance = new MunicipalityAPIProxy();
    private IMunicipalityAPI apiInstance;

    public static MunicipalityAPIProxy getInstance() {
        return ourInstance;
    }

    public void setApiInstance(final IMunicipalityAPI apiInstance) {
        this.apiInstance = apiInstance;
    }

//    @Override
//    public IMunicipalityManager getMunicipalityManager() {
//        return apiInstance.getMunicipalityManager();
//    }
}
