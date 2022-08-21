package com.branchmasters.simplestorageapi.service;

import com.branchmasters.simplestorageapi.dto.upload.UploadRequestDTO;
import com.branchmasters.simplestorageapi.dto.upload.UploadResponseDTO;
import com.branchmasters.simplestorageapi.externalapi.FileIoExternalApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Log4j2
@Service
public class ArquivoService {

    private final FileIoExternalApi fileIoExternalApi;

    @Autowired
    public ArquivoService(FileIoExternalApi fileIoExternalApi) {
        this.fileIoExternalApi = fileIoExternalApi;
    }

    public UploadResponseDTO armazenarArquivo(@Valid UploadRequestDTO requestDTO) {
        log.info("Chamando api file.io para armazenar arquivo.");
        UploadResponseDTO uploadResponseDTO = this.fileIoExternalApi.uploadFile(requestDTO);
        log.info("API externa obteve sucesso ao salvar arquivo? R: {}", uploadResponseDTO.isSuccess() ? "Sim" : "Não");
        return uploadResponseDTO;
    }
}
