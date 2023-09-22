package com.alcon3sl.cms.model.carrier;

public enum KindCarrier {
    Terrestrial("Terrestrial"),
    Maritime("Maritime"),
    Aerial("Aerial"),
    Multimodal("Multimodal");

    private final String kindCarrier;
    private KindCarrier(String kindCarrier) {
        this.kindCarrier = kindCarrier;
    }

    public String getKindCarrier() {
        return kindCarrier;
    }
}
