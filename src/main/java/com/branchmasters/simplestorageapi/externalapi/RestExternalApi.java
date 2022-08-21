package com.branchmasters.simplestorageapi.externalapi;

import com.branchmasters.simplestorageapi.enumeration.ErrorEnum;
import com.branchmasters.simplestorageapi.exception.BusinessException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Log4j2
@Component
public class RestExternalApi {
    public static final String ENVIANDO_MENSAGEM_A_API_EXTERNA = "Enviando mensagem a API externa {}";
    public static final String RESPOSTA_RECEBIDA = "Resposta recebida!";
    public static final String OBJETO_DE_RESPOSTA_CRIADO = "Objeto de postagem para resposta criado!";
    private final RestTemplate restTemplate;

    @Autowired
    public RestExternalApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T extends Object> ResponseEntity post(String url, String path, HttpHeaders headers, T body, Class type) {
        String fullPath = url.concat(path);

        log.info(ENVIANDO_MENSAGEM_A_API_EXTERNA, url);
        try {
            final HttpEntity<T> request = new HttpEntity(body, headers);
            final ResponseEntity responseEntity = restTemplate.exchange(fullPath, HttpMethod.POST, request, type);
            log.info(RESPOSTA_RECEBIDA);
            log.info(OBJETO_DE_RESPOSTA_CRIADO);
            return responseEntity;
        } catch (HttpStatusCodeException ex) {
            log.error(url, path);
            log.error(ex.getMessage(), ex);
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
        }
    }

    public ResponseEntity get(String url, String path, HttpHeaders headers, Class type) {
        String fullPath = url.concat(path);

        log.info(ENVIANDO_MENSAGEM_A_API_EXTERNA, url);
        try {
            final HttpEntity request = new HttpEntity(headers);
            final ResponseEntity responseEntity = restTemplate.exchange(fullPath, HttpMethod.GET, request, type);
            log.info(RESPOSTA_RECEBIDA);
            log.info(OBJETO_DE_RESPOSTA_CRIADO);
            return responseEntity;
        } catch (HttpStatusCodeException ex) {
            log.error(url, path);
            log.error(ex.getMessage(), ex);
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
        }
    }

    public <T extends Object> ResponseEntity put(String url, String path, HttpHeaders headers, T body, Class type) {

        log.info(ENVIANDO_MENSAGEM_A_API_EXTERNA, url);
        try {
            final String fullPath = url.concat(path);
            final HttpEntity<T> request = new HttpEntity(body, headers);
            final ResponseEntity responseEntity = restTemplate.exchange(fullPath, HttpMethod.PUT, request, type);
            log.info(RESPOSTA_RECEBIDA);
            log.info(OBJETO_DE_RESPOSTA_CRIADO);
            return responseEntity;
        } catch (HttpStatusCodeException ex) {
            log.error(url, path);
            log.error(ex.getMessage(), ex);
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
        }
    }

    public <T extends Object> ResponseEntity delete(String url, String path, HttpHeaders headers, T body, Class type) {

        log.info(ENVIANDO_MENSAGEM_A_API_EXTERNA, url);
        try {
            final String fullPath = url.concat(path);
            final HttpEntity request = new HttpEntity<>(body, headers);
            final ResponseEntity responseEntity = restTemplate.exchange(fullPath, HttpMethod.DELETE, request, type);
            log.info(RESPOSTA_RECEBIDA);
            log.info(OBJETO_DE_RESPOSTA_CRIADO);
            return responseEntity;
        } catch (HttpStatusCodeException ex) {
            log.error(url, path);
            log.error(ex.getMessage(), ex);
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
        }
    }

    public String desserializarMensagemDeErro(String resposta) {

        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            final JsonNode nodeData = objectMapper.readTree(resposta).get("message");
            return objectMapper.readValue(nodeData.toString(), String.class);

        } catch (IOException ex) {
            throw new BusinessException(ErrorEnum.ERRO_INTERNO,
                    "Erro ao desserializar dados do serviço de validação de código de acesso: " + ex.getMessage(), ex);
        }
    }
}
