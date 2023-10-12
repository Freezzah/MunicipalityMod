package com.freezzah.municipality.caps;


public interface IMunicipalityAPI {
    static IMunicipalityAPI getInstance() {
        return MunicipalityAPIProxy.getInstance();
    }

    //IMunicipalityManager getMunicipalityManager();
}
