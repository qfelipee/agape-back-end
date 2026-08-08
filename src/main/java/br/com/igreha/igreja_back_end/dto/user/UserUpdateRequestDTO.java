package br.com.igreha.igreja_back_end.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserUpdateRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    private String funcao;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    // Senha é opcional na atualização — só muda se o campo vier preenchido
    private String senha;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getFuncao() { return funcao; }
    public void setFuncao(String funcao) { this.funcao = funcao; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}