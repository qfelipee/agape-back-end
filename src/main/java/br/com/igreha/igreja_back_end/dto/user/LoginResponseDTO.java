package br.com.igreha.igreja_back_end.dto.user;

public class LoginResponseDTO {
    private Integer id;
    private String token;
    private String nome;
    private String funcao;
    private String email;
    private String role;

    public LoginResponseDTO(Integer id, String token, String nome, String funcao, String email, String role) {
        this.id = id;
        this.token = token;
        this.nome = nome;
        this.funcao = funcao;
        this.email = email;
        this.role = role;
    }

    public Integer getId() { return id; }
    public String getToken() { return token; }
    public String getNome() { return nome; }
    public String getFuncao() { return funcao; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
}