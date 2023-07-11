package domainapp.modules.simple.types;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.jdo.annotations.Column;

import org.apache.causeway.applib.annotation.Editing;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.annotation.Property;
import org.apache.causeway.applib.annotation.PropertyLayout;
import org.apache.causeway.applib.annotation.Publishing;

@Column(length = Email.MAX_LEN, allowsNull = "true")
@Property(editing = Editing.ENABLED, maxLength = Telefono.MAX_LEN, regexPattern = "[^@]+@[^@]+[.][^@]+", regexPatternReplacement = "Direccion de E-MAIL invalida",optionality = Optionality.OPTIONAL)
@Parameter(maxLength = Email.MAX_LEN, optionality = Optionality.OPTIONAL)
@ParameterLayout(named = "Email")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)

public @interface Email {

	int MAX_LEN = 100;
}