package com.branchmasters.simplestorageapi.dto.upload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadRequestDTO {
    private String file;            //Arquivo em binary
    private String expires;         //Data de expiração. Ex: "2022-08-21T19:23:26.791Z"
    private Integer maxDownloads;
    private boolean autoDelete;
}
