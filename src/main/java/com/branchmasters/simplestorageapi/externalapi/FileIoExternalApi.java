package com.branchmasters.simplestorageapi.externalapi;

import com.branchmasters.simplestorageapi.dto.upload.UploadRequestDTO;
import com.branchmasters.simplestorageapi.dto.upload.UploadResponseDTO;
import com.branchmasters.simplestorageapi.enumeration.ErrorEnum;
import com.branchmasters.simplestorageapi.exception.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.io.IOException;

@Component
@Log4j2
public class FileIoExternalApi {

    private final String urlBase;
    private final RestExternalApi restExternalApi;

    @Autowired
    public FileIoExternalApi(@Value("${fileio.url.base}") String urlBase, RestExternalApi restExternalApi) {
        this.urlBase = urlBase;
        this.restExternalApi = restExternalApi;
    }

    public UploadResponseDTO uploadFile(@Valid UploadRequestDTO requestDTO){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);


        ResponseEntity<String> response = restExternalApi.post(urlBase, "",
                headers, requestDTO, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                UploadResponseDTO responseDTO = objectMapper.readValue(response.getBody(), UploadResponseDTO.class);
                return responseDTO;
            } catch (BusinessException | IOException ex) {
                throw new BusinessException("Erro ao converter retorno da file.io!", ex, ErrorEnum.ERRO_INTERNO);
            }
        }

        throw new BusinessException(ErrorEnum.ERRO_NEGOCIO, "Houve algum problema na comunicação com a API externa file.io.");
    }
}
