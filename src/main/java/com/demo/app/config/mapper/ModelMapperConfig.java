package com.demo.app.config.mapper;

import com.demo.app.model.Gender;
import org.modelmapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        Provider<LocalDate> localDateProvider = new AbstractProvider<>() {
            @Override
            protected LocalDate get() {
                return LocalDate.now();
            }
        };
        Converter<String, LocalDate> converter = new AbstractConverter<>() {
            @Override
            protected LocalDate convert(String source) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return LocalDate.parse(source, formatter);
            }
        };
        modelMapper.createTypeMap(String.class, LocalDate.class);
        modelMapper.addConverter(converter);
        modelMapper.getTypeMap(String.class, LocalDate.class).setProvider(localDateProvider);
        modelMapper.createTypeMap(String.class, Gender.class).setConverter(context -> switch (context.getSource()) {
            case "male", "Male" -> Gender.MALE;
            case "female", "Female" -> Gender.FEMALE;
            default -> null;
        });

        return modelMapper;
    }

}
