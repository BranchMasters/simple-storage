
package com.branchmasters.simplestorageapi.enumeration;

import com.branchmasters.simplestorageapi.message.MessageUtils;
import lombok.Getter;

@Getter
public enum ErrorEnum {

    ERRO_NEGOCIO(400, MessageUtils.MSG_ERR_NEGOCIO), //
    ERRO_PRECONDICAO(412, MessageUtils.MSG_ERR_PRECONDITION),//
    ERRO_INTERNO(500, MessageUtils.MSG_ERR_ERRO_INTERNO);//

    private Integer codigo;
    private String descricao;

    private ErrorEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

}
