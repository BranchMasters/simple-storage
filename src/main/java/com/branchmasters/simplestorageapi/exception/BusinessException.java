
package com.branchmasters.simplestorageapi.exception;

import com.branchmasters.simplestorageapi.enumeration.ErrorEnum;
import com.branchmasters.simplestorageapi.exception.base.BaseException;
import com.branchmasters.simplestorageapi.message.MessageManager;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class BusinessException extends BaseException {
    private static final long serialVersionUID = 1L;

    private final List<String> messages = new ArrayList<>();
    private final ErrorEnum erroEnum;

    public BusinessException(String string, Throwable thrwbl, ErrorEnum erroEnum, Object... args) {
        super(MessageManager.getMessage(string, args), thrwbl);
        this.erroEnum = erroEnum;
    }

    public BusinessException(ErrorEnum erroEnum, String message, Throwable thrwbl, Object... args) {
        super(message, thrwbl);
        this.messages.add(MessageManager.getMessage(message, args));
        this.erroEnum = erroEnum;
    }

    public BusinessException(ErrorEnum erroEnum, String message, Object... args) {
        super(MessageManager.getMessage(message, args));
        this.messages.add(MessageManager.getMessage(message, args));
        this.erroEnum = erroEnum;
    }

    public BusinessException(ErrorEnum erroEnum, List<String> messages) {
        super(Arrays.toString(messages.toArray()));
        this.messages.addAll(messages);
        this.erroEnum = erroEnum;
    }

    public BusinessException(ErrorEnum erroEnum, List<String> messages, Throwable ex) {
        super(Arrays.toString(messages.toArray()), ex);
        this.messages.addAll(messages);
        this.erroEnum = erroEnum;
    }

    public BusinessException(ErrorEnum erroEnum, String message) {
        super(MessageManager.getMessage(message));
        this.messages.add(MessageManager.getMessage(message));
        this.erroEnum = erroEnum;
    }

    public static IntermediateContext assertThat(boolean condition) {
        return new IntermediateContext(condition);
    }

    public static IntermediateContext assertNotNull(Object object) {
        return new IntermediateContext(object != null);
    }

    public static IntermediateContext assertNull(Object object) {
        return new IntermediateContext(object == null);
    }

    public static IntermediateContext assertNotEmpty(String string) {
        return new IntermediateContext(string != null && !string.isEmpty());
    }

    public static IntermediateContext assertCollectionNotEmpty(Collection collection) {
        return new IntermediateContext(collection != null && !collection.isEmpty());
    }

    public static IntermediateContext assertCollectionEmpty(Collection collection) {
        return new IntermediateContext(collection == null || collection.isEmpty());
    }

    public static BusinessException throwException(ErrorEnum errorEnum, String mensagem, Throwable ex) {
        throw new BusinessException(errorEnum, mensagem, ex);
    }

    public static BusinessException throwException(ErrorEnum errorEnum, String mensagem) {
        throw new BusinessException(errorEnum, mensagem);
    }

    public static void assertExceptionListEmpty(ErrorEnum errorEnum, List<String> messages) {
        if (!(messages == null || messages.isEmpty())) {
            throw new BusinessException(errorEnum, messages);
        }
    }

    public static class IntermediateContext {

        private final boolean assertIsFalse;

        public IntermediateContext(boolean condition) {
            this.assertIsFalse = !condition;
        }

        public void orRegister(ErrorEnum errorEnum, String mensage, Object... parametros) {
            if (assertIsFalse) {
                throw new BusinessException(errorEnum, mensage, parametros);
            }
        }

        public void orRegister(ErrorEnum errorEnum, List<String> messages) {
            if (assertIsFalse) {
                throw new BusinessException(errorEnum, messages);
            }
        }
    }

}
