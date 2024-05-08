package com.samuel_reginaldo.gestioVideojocs.backend.presentation.config;

public class RespostaError {

	private static final long serialVersionUID = 1L;

	private String error;
	
	public RespostaError(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}
	
}
