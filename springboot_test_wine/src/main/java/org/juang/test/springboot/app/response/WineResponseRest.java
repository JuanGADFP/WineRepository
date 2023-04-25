package org.juang.test.springboot.app.response;

public class WineResponseRest extends ResponseRest {

    private WineResponse wineResponse = new WineResponse();

    public WineResponse getWineResponse() {
        return wineResponse;
    }

    public void setWineResponse(WineResponse wineResponse) {
        this.wineResponse = wineResponse;
    }
}
