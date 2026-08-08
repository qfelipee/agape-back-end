package br.com.igreha.igreja_back_end.exception;

public class LancamentoNotFoundException extends RuntimeException {
    public LancamentoNotFoundException(String message) {
        super(message);
    }
}
