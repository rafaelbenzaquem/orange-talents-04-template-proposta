package br.com.zup.academy.benzaquem.shared;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;

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

    @Value("${config.encrypt.password}")
    private String password;
    @Value("${config.encrypt.salt}")
    private String salt;

    public void initialize(CampoUnico constraint) {
        this.domainClass = constraint.domainClass();
        this.fieldName = constraint.fieldName();
    }

    public boolean isValid(Object valor, ConstraintValidatorContext context) {
        Query q = em.createQuery("select d from " + domainClass.getSimpleName() + " d where " + fieldName + "=:valor");
        if (fieldName.equals("documento")) {
            String encriptado = Encryptors.queryableText(password, salt).encrypt((String) valor);
            q.setParameter("valor", encriptado);
        } else {
            q.setParameter("valor", valor);
        }
        List<?> list = q.getResultList();
        return list.isEmpty();
    }
}
