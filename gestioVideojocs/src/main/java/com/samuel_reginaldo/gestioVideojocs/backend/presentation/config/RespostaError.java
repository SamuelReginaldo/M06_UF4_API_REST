package com.samuel_reginaldo.gestioVideojocs.backend.presentation.config;

import java.io.Serializable;

public class RespostaError implements Serializable {
	private static final long serialVersionUID = 1L;

	private String error;
	
	public RespostaError(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}
	
}

