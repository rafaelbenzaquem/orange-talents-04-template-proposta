package br.com.zup.academy.benzaquem.shared;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CampoUnicoValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CampoUnico {
    String message() default "{br.com.zup.academy.benzaquem.shared.CampoUnico.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<?> domainClass();
    String fieldName();
}
