package br.com.padrao.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.hibernate.validator.ValidatorClass;

import br.com.padrao.validator.NotWhiteSpaceValidator;

/**
 * Annotation para espaço em branco.
 * 
 * @author daniel.almeida
 * 
 */
@ValidatorClass(
		value = NotWhiteSpaceValidator.class)
@Target(
		value = { METHOD, FIELD })
@Retention(
		value = RUNTIME)
@Documented
public @interface NotWhiteSpace {

	String message() default "Não é permitido espaços em branco";

}
