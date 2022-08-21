package com.branchmasters.simplestorageapi.dto.upload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadResponseDTO {
    private boolean success;
    private Integer status;
    private String id;
    private String key;
    private String name;
    private String link;
    private String expires;
    private String expiry;
    private Integer downloads;
    private Integer maxDownloads;
    private String autoDelete;
    private Integer size;
    private String mimeType;
    private String created;
    private String modified;
}
