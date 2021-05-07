package br.com.zup.academy.benzaquem.shared;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class CampoUnicoValidator implements ConstraintValidator<CampoUnico, Object> {

    private Class<?> domainClass;
    private String fieldName;

    @PersistenceContext
    private EntityManager em;

    public void initialize(CampoUnico constraint) {
        this.domainClass = constraint.domainClass();
        this.fieldName = constraint.fieldName();
    }

    public boolean isValid(Object valor, ConstraintValidatorContext context) {
        Query q = em.createQuery("select d from "+domainClass.getSimpleName()+ " d where "+fieldName+"=:valor");
        q.setParameter("valor",valor);
        List<?> list = q.getResultList();
        return list.isEmpty();
    }
}
