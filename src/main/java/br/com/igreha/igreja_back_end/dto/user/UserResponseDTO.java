package br.com.igreha.igreja_back_end.dto.user;

public class UserResponseDTO {

    private Integer id;
    private String nome;
    private String funcao;
    private String email;
    private String role;

    public UserResponseDTO(Integer id, String nome, String funcao, String email, String role) {
        this.id = id;
        this.nome = nome;
        this.funcao = funcao;
        this.email = email;
        this.role = role;
    }

    public Integer getId() { return id; }
    public String getNome() { return nome; }
    public String getFuncao() { return funcao; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
}