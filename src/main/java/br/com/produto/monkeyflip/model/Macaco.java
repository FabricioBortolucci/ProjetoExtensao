package br.com.produto.monkeyflip.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Macaco implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "monk_id")
    private Long id;

    @Column(name = "monk_nome")
    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @Column(name = "monk_especie")
    @NotBlank(message = "A espécie é obrigatória.")
    private String especie;

    @Column(name = "monk_idade")
    @NotNull(message = "A idade não pode ser nula ou menor que zero.")
    @Min(value = 0)
    private Integer idade;

    @Column(name = "monk_peso")
    @NotNull(message = "O peso não pode ser nulo e deve ser maior que zero.")
    @DecimalMin(value = "0.0", inclusive = false)
    private Double peso;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Macaco macaco = (Macaco) o;
        return Objects.equals(id, macaco.id) && Objects.equals(nome, macaco.nome) && Objects.equals(especie, macaco.especie) && Objects.equals(idade, macaco.idade) && Objects.equals(peso, macaco.peso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, especie, idade, peso);
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
