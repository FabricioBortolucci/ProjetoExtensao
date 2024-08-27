package br.com.produto.monkeyflip.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "usu_nome")
    private String usuNome;

    @Column(name = "usu_senha")
    private String usuSenha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuNome() {
        return usuNome;
    }

    public void setUsuNome(String usuNome) {
        this.usuNome = usuNome;
    }

    public String getUsuSenha() {
        return usuSenha;
    }

    public void setUsuSenha(String usuSenha) {
        this.usuSenha = usuSenha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) && Objects.equals(usuNome, usuario.usuNome) && Objects.equals(usuSenha, usuario.usuSenha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usuNome, usuSenha);
    }

    @Override
    public String toString() {
        return id.toString();
    }
}

