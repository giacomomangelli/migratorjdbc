package it.prova.datamigration.dao.oldschemaobj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import it.prova.datamigration.dao.AbstractMySQLDAO;
import it.prova.datamigration.model.OldSchemaObj;

public class OldSchemaObjDaoImpl extends AbstractMySQLDAO {

	private Connection connection;

	public List<OldSchemaObj> list() throws Exception {
		if (isNotActive()) {
			throw new Exception("Connessione chiusa. Impossibile operare con il Dao.");
		}

		String query = "SELECT D.ID, D.CODICE_FISCALE, A.NOME, A.COGNOME, A.DATA_NASCITA, COUNT(S.ID) AS SINISTRI_TOT "
				+ "FROM DATI_FISCALI D " + "INNER JOIN ANAGRAFICA A ON A.FK_DATI_FISCALI = D.ID "
				+ "LEFT JOIN SINISTRI S ON D.ID = S.FK_ANAGRAFICA " + "GROUP BY D.ID";

		try (PreparedStatement statement = connection.prepareStatement(query);

				ResultSet result = statement.executeQuery()) {
			List<OldSchemaObj> oldSchemaObjs = new ArrayList<>();

			while (result.next()) {

				OldSchemaObj oldSchemaObj = new OldSchemaObj();
				oldSchemaObj.setId(result.getLong("ID"));
				oldSchemaObj.setNome(result.getString("NOME"));
				oldSchemaObj.setCognome(result.getString("COGNOME"));
				oldSchemaObj.setDataNascita(result.getDate("DATA_NASCITA"));
				oldSchemaObj.setNumeroSinistri(result.getInt("SINISTRI_TOT"));
				
				oldSchemaObjs.add(oldSchemaObj);
			}
			return oldSchemaObjs;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
