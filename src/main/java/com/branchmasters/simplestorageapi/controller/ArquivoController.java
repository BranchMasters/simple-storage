package com.branchmasters.simplestorageapi.controller;

import com.branchmasters.simplestorageapi.dto.upload.UploadRequestDTO;
import com.branchmasters.simplestorageapi.dto.upload.UploadResponseDTO;
import com.branchmasters.simplestorageapi.service.ArquivoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Log4j2
@RestController
@RequestMapping(value = "arquivo")
public class ArquivoController {

    private final ArquivoService arquivoService;

    @Autowired
    public ArquivoController(ArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    @Operation(summary = "Armazena o arquivo enviado para a API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquivo armazenado com sucesso!"),
            @ApiResponse(responseCode = "500", description = "Ocorreu um erro interno, favor contatar o administrador.")
    })
    @PostMapping("/upload")
    public UploadResponseDTO fazerUploadArquivo(@Valid UploadRequestDTO requestDTO){
        log.info("Iniciando Request POST /arquivo/upload");
        UploadResponseDTO uploadResponseDTO = this.arquivoService.armazenarArquivo(requestDTO);
        log.info("Finalizando Request POST /arquivo/upload");
        return uploadResponseDTO;
    }
}
