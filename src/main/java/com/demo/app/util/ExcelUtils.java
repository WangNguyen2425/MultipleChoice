package com.demo.app.util;

import com.demo.app.model.Gender;
import com.demo.app.model.Student;
import com.demo.app.model.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class ExcelUtils {

    private static final String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    private static final int COLUMN_INDEX_USERNAME = 0;

    private static final int COLUMN_INDEX_PASSWORD = 1;

    private static final int COLUMN_INDEX_FULLNAME = 2;

    private static final int COLUMN_INDEX_BIRTHDAY = 3;

    private static final int COLUMN_INDEX_GENDER = 4;

    private static final int COLUMN_INDEX_JOIN_DATE = 5;

    private static final int COLUMN_INDEX_PHONE_NUMBER = 6;

    private static final int COLUMN_INDEX_EMAIL = 7;

    public static boolean hasExcelFormat(MultipartFile file) {
        return Objects.equals(file.getContentType(), TYPE);
    }

    public static Map<User, Student> excelFileToUserStudents(MultipartFile file) throws IOException {

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            Map<User, Student> userStudents = new HashMap<>();

            sheet.forEach(row -> {
                if(row.getRowNum() == 0){
                    return;
                }
                User user = new User();
                Student student = new Student();
                row.forEach(cell -> {
                    Object value = getCellValue(cell);
                    if(value == null || value.toString().isBlank()){
                        return;
                    }
                    int columnIndex = cell.getColumnIndex();
                    switch (columnIndex) {
                        case COLUMN_INDEX_USERNAME:
                            user.setUsername(value.toString());
                            break;
                        case COLUMN_INDEX_PASSWORD:
                            user.setPassword(value.toString());
                            break;
                        case COLUMN_INDEX_FULLNAME:
                            student.setFullName(value.toString());
                            break;
                        case COLUMN_INDEX_BIRTHDAY:
                            Date date = cell.getDateCellValue();
                            LocalDate birthday = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                            student.setBirthday(birthday);
                            break;
                        case COLUMN_INDEX_GENDER:
                            if (value.toString().equalsIgnoreCase("male"))
                                student.setGender(Gender.MALE);
                            else if (value.toString().equalsIgnoreCase("female"))
                                student.setGender(Gender.FEMALE);
                            break;
                        case COLUMN_INDEX_JOIN_DATE:
                            break;
                        case COLUMN_INDEX_PHONE_NUMBER:
                            student.setPhoneNumber(cell.getStringCellValue());
                            break;
                        case COLUMN_INDEX_EMAIL:
                            user.setEmail(value.toString());
                    }
                    userStudents.put(user, student);
                });
            });
            return userStudents;
        }

    }

    private static Object getCellValue(Cell cell) {
        CellType type = cell.getCellType();
        Object value = null;
        switch (type) {
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case FORMULA:
                Workbook workbook = cell.getSheet().getWorkbook();
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                value = evaluator.evaluate(cell).getNumberValue();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    value = cell.getDateCellValue();
                } else {
                    value = cell.getNumericCellValue();
                }
                break;
            case STRING:
                value = cell.getStringCellValue();
                break;
            case _NONE:
            case BLANK:
            case ERROR:
            default:
                break;
        }
        return value;
    }

}
