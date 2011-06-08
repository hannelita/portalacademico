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

import br.edu.unifei.portalacademico.model.Professor;

@Model
public class ProfessorRegistration {
	
	
	@Inject
	@Category("portalacademico")
	private Logger log;

	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserTransaction utx;

	@Inject
	private Event<Professor> professorEventSrc;

	private Professor professor;

	@Produces
	@Named
	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public void register() throws Exception {
		log.info("Registering " + professor.getNome());
		// UserTransaction only needed when bean is not an EJB
		utx.begin();
		em.joinTransaction();
		em.persist(professor);
		utx.commit();
		professorEventSrc.fire(professor);
		initProfessor();
	}
	
	public void remove() throws Exception{
		log.info("Deleting " + professor.getNome());
		utx.begin();
		em.joinTransaction();
		em.remove(professor);
		utx.commit();
		professorEventSrc.fire(professor);
		initProfessor();
	}

	@PostConstruct
	public void initProfessor() {
		professor = new Professor();
	}

}
