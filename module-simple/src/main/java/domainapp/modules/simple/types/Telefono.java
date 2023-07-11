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

@Column(length = Telefono.MAX_LEN, allowsNull = "true")
@Property(editing = Editing.ENABLED, maxLength = Email.MAX_LEN, regexPattern = "[+]?[0-9 ]+", regexPatternReplacement = "Solo se permiten numeros y espacios, el prefijo puede ser '+'.  "
		+ "Por ejemplo '+15467891', o '0299336190'")
@Parameter(maxLength = Telefono.MAX_LEN, optionality = Optionality.OPTIONAL)
@ParameterLayout(named = "Telefono")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)

public @interface Telefono {

	int MAX_LEN = 30;
}