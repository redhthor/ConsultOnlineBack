package com.miconsultorio.app.model.services.impl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.miconsultorio.app.excepciones.InternalException;
import com.miconsultorio.app.model.dao.IConsultaMedicaDao;
import com.miconsultorio.app.model.entities.ConsultaMedica;
import com.miconsultorio.app.model.entities.vo.PacienteVO;
import com.miconsultorio.app.model.requestbody.RequestHistorialConsultas;
import com.miconsultorio.app.model.services.IConsultasService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class ConsultaServiceImpl implements IConsultasService {
	@Autowired
	private IConsultaMedicaDao consultaDao;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	private static final Logger logger = LoggerFactory.getLogger(ConsultaServiceImpl.class);
	
	@Override
	public Mono<ConsultaMedica> guardarConsulta(ConsultaMedica consulta) {
		return consultaDao.save(consulta);
	}

	@Override
	public void eliminarConsulta(String id) {
		ConsultaMedica cm = consultaDao.findById(id).block();
		if(cm == null) {
			return;
		}
		consultaDao.delete(cm);
	}
	
	@Override
	public Mono<ConsultaMedica> buscarConsulta(String id) {
		return consultaDao.buscarPorId(id);
	}
	
	@Override
	public Flux<ConsultaMedica> listarConsultarPorPaciente(String email) {
		return consultaDao.findByPacienteEmail(email);
	}
	
	@Override
	public Page<ConsultaMedica> buscarHistorialDeConsultas(RequestHistorialConsultas req, Pageable pageable) throws InternalException {
		Query query = new Query();
		logger.info("Agregando doctor al query");
		query.addCriteria(Criteria.where("doctor").is(req.getUsuario()));
		query.with(Sort.by(Sort.Direction.DESC, "fecha"));
		if(req.getFecha() != null) {
			logger.info("Agregando la fecha de consulta");
			SimpleDateFormat f1 = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat f2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String fechaString = f1.format(req.getFecha());
			Date fechaInicial = null;
			Date fechaFinal = null;
			try {
				fechaInicial = f2.parse(fechaString+" 00:00:00");
				fechaFinal = f2.parse(fechaString+" 23:59:59");
			} catch(ParseException ex) {
				logger.info("Ocurrió una execpcion al convertir la fecha {}", ex.getMessage());
				throw new InternalException("No se pudo convertir la fecha a rango");
			}
			query.addCriteria(Criteria.where("fecha").gte(fechaInicial).lte(fechaFinal));
		}
		if(req.getPaciente() != null ) {
			PacienteVO p = req.getPaciente();
			if(p.getEmail() != null && !p.getEmail().isBlank()) {
				logger.info("Agregando correo del paciente");
				query.addCriteria(Criteria.where("paciente.email").is(p.getEmail()));
			}
			if(p.getNombre() != null && !p.getNombre().isBlank()) {
				logger.info("Agregando nombre del paciente");
				query.addCriteria(Criteria.where("paciente.nombre").regex(p.getNombre()));
			}
		}
		List<ConsultaMedica> list = mongoTemplate.find(query, ConsultaMedica.class);
		logger.info("Se obtiene resultado de la consulta");
		query.with(pageable);
		List<ConsultaMedica> subList = mongoTemplate.find(query, ConsultaMedica.class);
		logger.info("Se obtiene subconsulta paginada");
		return new PageImpl<>(subList, pageable, list.size());
	}
		
}
