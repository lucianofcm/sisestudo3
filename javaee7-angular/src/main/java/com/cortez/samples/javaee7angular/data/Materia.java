package com.cortez.samples.javaee7angular.data;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author p_999170
 */
public class Materia {
    
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
	@SequenceGenerator(name = "id", sequenceName = "id")
	private Long id;
   
    private String nome;
    private String descricao;
    private List<Assunto> assuntos;
    
   
    public List<Assunto> getAssuntos() {
        return assuntos;
    }

    public void setAssuntos(List<Assunto> assuntos) {
        this.assuntos = assuntos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
        
    
}
