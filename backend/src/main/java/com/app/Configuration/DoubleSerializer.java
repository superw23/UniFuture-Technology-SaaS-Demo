//Made by GRPTeam05
package com.app.Configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.text.DecimalFormat;

public class DoubleSerializer extends JsonSerializer<Double> {
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Override
    public void serialize(Double o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if(o.toString()!=null && !"-".equals(o)){
            Double dd = Double.parseDouble(o.toString());
            jsonGenerator.writeString(decimalFormat.format(dd));
        }else{
            jsonGenerator.writeString(o.toString());
        }
    }
}
