DROP TABLE VIDEOJOCS IF EXISTS;

CREATE TABLE VIDEOJOCS (

	ID				BIGINT			NOT NULL,
	NOMBRE			VARCHAR(150)	,
	GENERO		VARCHAR(250)	,
	PRECIO			DOUBLE	,
	
	PRIMARY KEY (ID)

);