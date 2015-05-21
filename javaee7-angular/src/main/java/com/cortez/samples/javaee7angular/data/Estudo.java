/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cortez.samples.javaee7angular.data;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author p_999170
 */
public class Estudo {

    private Assunto assunto;
    private Materia materia;
    private Date dataEstudo;
    private int qtdeHoras;
    private Status status;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    @SequenceGenerator(name = "id", sequenceName = "id")
    private Long id;

    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQtdeHoras() {
        return qtdeHoras;
    }

    public void setQtdeHoras(int qtdeHoras) {
        this.qtdeHoras = qtdeHoras;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Assunto getAssunto() {
        return assunto;
    }

    public void setAssunto(Assunto assunto) {
        this.assunto = assunto;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Date getDataEstudo() {
        return dataEstudo;
    }

    public void setDataEstudo(Date dataEstudo) {
        this.dataEstudo = dataEstudo;
    }

}
