package br.com.zup.academy.benzaquem.shared.message;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ValidatorMessageResponse extends MessageResponse {

    private List<FieldMessage> fields = new ArrayList<>();

    public ValidatorMessageResponse(Integer status, String message, LocalDateTime instant) {
        super(status, message, instant);
    }

    public List<FieldMessage> getFields() {
        return List.copyOf(fields);
    }

    public void addFieldError(String field, String message){
        fields.add(new FieldMessage(field,message));
    }

}
