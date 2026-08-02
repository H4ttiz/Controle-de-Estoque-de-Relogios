package dev.java10x.desafio.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

@RestControllerAdvice
public class TratadorGlobalException {

    @ExceptionHandler(NaoEncontradoException.class)
    public ResponseEntity<ErroApi> tratarNaoEncontrado(NaoEncontradoException ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroApi(
                Instant.now(),
                404,
                "Não Encontrado",
                ex.getMessage(),
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroApi> tratarRequisicaoInvalida(Exception ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErroApi(
                Instant.now(),
                400,
                "Requisição inválidade",
                ex.getMessage(),
                request.getRequestURI(),
                List.of()
        ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroApi> tratarValidacao(MethodArgumentNotValidException ex, HttpServletRequest request){
        List<ErroApi.ErroCampo> campos = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> new ErroApi.ErroCampo(fe.getField(), ex.getMessage()))
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErroApi(
                Instant.now(),
                400,
                "Requisição inválidade",
                "Erro de Validação",
                request.getRequestURI(),
                campos
        ));
    }
}
