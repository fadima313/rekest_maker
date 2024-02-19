package com.rekest.helpers;

import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import com.github.javafaker.Faker;
import com.rekest.entities.Role;


import com.rekest.entities.employes.Administrateur;
import com.rekest.entities.employes.Utilisateur;
import com.rekest.feature.IFeature;
import com.rekest.feature.impl.Feature;



public class RekestData implements IRekestData {

	public final static Logger logger = LogManager.getLogger(RekestData.class);


	private Faker faker = new Faker();

	private IFeature feature = Feature.getCurrentInstance();

	@Override
	public void initAdmins() {
		Administrateur defaultAdmin = createDefaultAdmin();
		feature.createUtilisateur(defaultAdmin);
	}


	@Override
	public Administrateur createDefaultAdmin() {
		Administrateur admin =  new Administrateur("Administrator", "System", "+221771234500", "rekest.app@rekest.sn",
				"Terrain foyer Rocade Fann Bel Air, BP 10 000 Dakar Liberté – SENEGAL");
		admin.setLogin("admin");
		admin.setPassword("admin");
		return admin;
	}

	@Override
	public void initEmployes() {}


	@Override
	public void initGestionnaire() {}

	@Override
	public void initChefServices() {}

	@Override
	public void initChefDepartement() {}


	@Override
	public void initDirecteurGeneral() {	}

	@Override
	public void initDirection() {}


	@Override
	public void initDepartement() {}


	@Override
	public void initService() {}



	@Override
	public void initRole() {
		Stream.of("ALL",  "LISTER" , "MODIFIER" , "SUPPRIMER")
		.forEach(roleName -> {
			logger.info("{}" ,roleName);
			Boolean resultat = feature.createRole(new Role(roleName));
			if (resultat)
				logger.info("Role {} was created successfully ", roleName);
		});
	}

	@Override
	public void initProduit() {}

	@Override
	public void initDemande() {}

	@Override
	public void initManagers() {}

	@Override
	public void initAllEntity() {
		this.initAdmins();
		this.initUtilisateurs();
		this.initRole();
	}



	@Override
	public void initUtilisateurs() {
		for (int i = 0; i < 4; i++) {
			Utilisateur u = 
					new Utilisateur(faker.name().firstName(), faker.name().lastName(), 
							"user" + i , "user");

			feature.createUtilisateur(u);
		}
	}


}
