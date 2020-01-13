DELIMITER //
	CREATE PROCEDURE mostrarVuelos()
	BEGIN
		SELECT * FROM Vuelo WHERE fecha_vuelo > '2020-02-17';
	END;
//