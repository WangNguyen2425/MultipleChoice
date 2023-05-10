package com.demo.app.config.mapper;

import com.demo.app.model.Gender;
import com.demo.app.model.Question;
import org.modelmapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        var mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);

        convertLocalDate(mapper);
        convertGender(mapper);
        convertLevel(mapper);

        return mapper;
    }

    private void convertLocalDate(ModelMapper mapper){
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
        mapper.createTypeMap(String.class, LocalDate.class);
        mapper.addConverter(converter);
        mapper.getTypeMap(String.class, LocalDate.class).setProvider(localDateProvider);
    }

    private void convertGender(ModelMapper mapper){
        mapper.createTypeMap(String.class, Gender.class).setConverter(context -> switch (context.getSource()) {
            case "male", "Male" -> Gender.MALE;
            case "female", "Female" -> Gender.FEMALE;
            default -> null;
        });
    }

    private void convertLevel(ModelMapper mapper){
        mapper.createTypeMap(String.class, Question.Level.class).setConverter(context -> switch (context.getSource()) {
            case "easy", "Easy", "EASY" -> Question.Level.EASY;
            case "normal", "Normal", "NORMAL" -> Question.Level.NORMAL;
            case "difficult", "Difficult", "DIFFICULT" -> Question.Level.DIFFICULT;
            default -> null;
        });
    }

}
