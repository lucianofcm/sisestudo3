/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cortez.samples.javaee7angular.data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author p_999170
 */
public class Assunto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
	@SequenceGenerator(name = "id", sequenceName = "id")
	private Long id;

	private String nome;
	private String descricaoAssunto;

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

	public String getDescricaoAssunto() {
		return descricaoAssunto;
	}

	public void setDescricaoAssunto(String descricaoAssunto) {
		this.descricaoAssunto = descricaoAssunto;
	}

}
