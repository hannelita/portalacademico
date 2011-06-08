package br.edu.unifei.portalacademico.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

import br.edu.unifei.portalacademico.model.Disciplina;

@Model
public class DisciplinaRegistration {

	@Inject
	@Category("portalacademico")
	private Logger log;

	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserTransaction utx;

	@Inject
	private Event<Disciplina> disciplinaEventSrc;

	private Disciplina disciplina;

	@Produces
	@Named
	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public void register() throws Exception {
		log.info("Registering " + disciplina.getNome());
		// UserTransaction only needed when bean is not an EJB
		utx.begin();
		em.joinTransaction();
		em.persist(disciplina);
		utx.commit();
		disciplinaEventSrc.fire(disciplina);
		initDisciplina();
	}

	@PostConstruct
	public void initDisciplina() {
		disciplina = new Disciplina();
	}

}
