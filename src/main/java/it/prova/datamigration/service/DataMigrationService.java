package it.prova.datamigration.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import it.prova.datamigration.connection.MyConnection;
import it.prova.datamigration.dao.Constants;
import it.prova.datamigration.dao.assicurato.AssicuratoDaoImpl;
import it.prova.datamigration.dao.notprocessed.NotProcessedDaoImpl;
import it.prova.datamigration.dao.oldschemaobj.OldSchemaObjDaoImpl;
import it.prova.datamigration.model.Assicurato;
import it.prova.datamigration.model.NotProcessed;
import it.prova.datamigration.model.OldSchemaObj;

public class DataMigrationService {

	private AssicuratoDaoImpl assicuratoDaoInstance;
	private NotProcessedDaoImpl notProcessedDaoInstance;
	private OldSchemaObjDaoImpl oldSchemaDaoObjInstance;

	public void inserimentoAssicurati() throws Exception {

		List<OldSchemaObj> oldInstanceList = new ArrayList<>();

		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL_OLD)) {

			oldSchemaDaoObjInstance.setConnection(connection);
			oldInstanceList = oldSchemaDaoObjInstance.list();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		List<Assicurato> assicurati = new ArrayList<>();
		List<NotProcessed> nonProcessati = new ArrayList<>();

		for (OldSchemaObj objItem : oldInstanceList) {
			if (validate(objItem)) {

				Assicurato assicurato = new Assicurato();
				assicurato.setNome(objItem.getNome());
				assicurato.setCognome(objItem.getCognome());
				assicurato.setDataNascita(objItem.getDataNascita());
				assicurato.setCodiceFiscale(objItem.getCodiceFiscale());
				assicurato.setNumeroSinistri(objItem.getNumeroSinistri() == null ? 0 : objItem.getNumeroSinistri());
				assicurati.add(assicurato);

			} else {

				NotProcessed notProcessed = new NotProcessed();
				notProcessed.setCodiceFiscale(objItem.getCodiceFiscale());
				notProcessed.setOldId(objItem.getId().toString());
				nonProcessati.add(notProcessed);

			}
		}

		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL_NEW)) {

			assicuratoDaoInstance.setConnection(connection);
			notProcessedDaoInstance.setConnection(connection);
			notProcessedDaoInstance.insert(nonProcessati);
			assicuratoDaoInstance.insert(assicurati);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	public boolean validate(OldSchemaObj objInstance) {

		if (StringUtils.isBlank(objInstance.getNome())) {
			return false;
		}
		if (StringUtils.isBlank(objInstance.getCognome())) {
			return false;
		}
		if (StringUtils.isBlank(objInstance.getCodiceFiscale()) || objInstance.getCodiceFiscale().length()!=16) {
			return false;
		}
		if (objInstance.getDataNascita() == null) {
			return false;

		}

		return true;
	}

	public void setAssicuratoDaoInstance(AssicuratoDaoImpl assicuratoDaoInstance) {
		this.assicuratoDaoInstance = assicuratoDaoInstance;
	}

	public void setNotProcessedDaoInstance(NotProcessedDaoImpl notProcessedDaoInstance) {
		this.notProcessedDaoInstance = notProcessedDaoInstance;
	}

	public void setOldSchemaDaoObjInstance(OldSchemaObjDaoImpl oldSchemaDaoObjInstance) {
		this.oldSchemaDaoObjInstance = oldSchemaDaoObjInstance;
	}
	
	

}
