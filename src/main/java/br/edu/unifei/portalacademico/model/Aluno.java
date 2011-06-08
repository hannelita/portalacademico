package br.edu.unifei.portalacademico.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Aluno {
	
	private Long id;
	private String nome;
	private int anoEntrada;
	private Curso curso;
	private String email;
	private int matricula;
	private StatusAluno stausAluno;
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@NotNull
    @Pattern(regexp = "[A-Za-z ]*", message = "must contain only letters and spaces")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getAnoEntrada() {
		return anoEntrada;
	}
	public void setAnoEntrada(int anoEntrada) {
		this.anoEntrada = anoEntrada;
	}
	public Curso getCurso() {
		return curso;
	}
	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	@Email
	@NotNull
	@NotEmpty
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@NotNull
	public int getMatricula() {
		return matricula;
	}
	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}
	public StatusAluno getStausAluno() {
		return stausAluno;
	}
	public void setStausAluno(StatusAluno stausAluno) {
		this.stausAluno = stausAluno;
	}
	
	
	
	

}
