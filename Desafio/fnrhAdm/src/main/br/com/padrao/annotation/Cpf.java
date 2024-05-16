package br.com.padrao.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.hibernate.validator.ValidatorClass;

import br.com.padrao.validator.CpfValidator;

/**
 * Annotation validar cpf.
 * 
 * @author daniel.almeida
 * 
 */
@ValidatorClass(
		value = CpfValidator.class)
@Target(
		value = { METHOD, FIELD })
@Retention(
		value = RUNTIME)
@Documented
public @interface Cpf {

	String message() default "CPF inválido";

}