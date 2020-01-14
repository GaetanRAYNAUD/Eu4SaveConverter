package fr.graynaud.eu4saveconverter.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Configuration
public class JacksonConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            builder.simpleDateFormat("yyyy-MM-dd");
            builder.dateFormat(new SimpleDateFormat("yyyy-MM-dd"));
            builder.serializerByType(Double.class, new CustomDoubleSerializer());
        };
    }

    public static class CustomDoubleSerializer extends JsonSerializer<Double> {
        @Override
        public void serialize(Double value, JsonGenerator generator, SerializerProvider provider) throws IOException {
            int scale = Math.max(3, BigDecimal.valueOf(value).scale());
            generator.writeString(String.format(Locale.ENGLISH, "%." + scale + "f", value));
        }
    }
}
