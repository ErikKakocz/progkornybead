package persistence;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.apache.log4j.Logger;
  
 
@SuppressWarnings("UnusedDeclaration")
@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<java.time.LocalDate, java.sql.Date> {
  
 
    public java.sql.Date convertToDatabaseColumn(java.time.LocalDate attribute) {
        
    	System.out.print("\n\nconverting into db\n\n");
        return attribute == null ? null : java.sql.Date.valueOf(attribute);
    }
 
    public java.time.LocalDate convertToEntityAttribute(java.sql.Date dbData) {
    	System.out.print("\n\nconverting into java\n\n");
        return dbData == null ? null : dbData.toLocalDate();
    }
}