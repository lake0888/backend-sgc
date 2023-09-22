package com.alcon3sl.cms.model.carrier;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter
public class KindCarrierConverter implements AttributeConverter<KindCarrier, String> {

    @Override
    public String convertToDatabaseColumn(KindCarrier kindCarrier) {
        return kindCarrier.getKindCarrier();
    }

    @Override
    public KindCarrier convertToEntityAttribute(String kindCarrier) {
        return Arrays.stream(KindCarrier.values())
                .filter(kC -> kC.getKindCarrier().equals(kindCarrier))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
