-- https://www.h2database.com/html/grammar.html

CREATE TABLE VIDEOJOCS (

	ID				BIGINT			NOT NULL,
	NOMBRE			VARCHAR(150)	,
	GENERO		VARCHAR(250)	,
	PRECIO			DOUBLE	,
	
	PRIMARY KEY (ID)

);