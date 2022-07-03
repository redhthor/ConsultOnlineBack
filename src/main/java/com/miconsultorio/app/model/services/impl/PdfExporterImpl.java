package com.miconsultorio.app.model.services.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.miconsultorio.app.excepciones.NoEncontradoException;
import com.miconsultorio.app.model.entities.ConsultaMedica;
import com.miconsultorio.app.model.entities.Usuario;
import com.miconsultorio.app.model.services.IConsultasService;
import com.miconsultorio.app.model.services.IPdfExporter;
import com.miconsultorio.app.model.services.IUsuarioService;

@Service
public class PdfExporterImpl implements IPdfExporter {
	@Autowired
	private IConsultasService consultaService;
	@Autowired
	private IUsuarioService usuarioService;
	private Font font = FontFactory.getFont(FontFactory.HELVETICA);
	private Font fontBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	private static final Logger logger = LoggerFactory.getLogger(PdfExporterImpl.class);
	
	@Override
	public void exportarConsultaAPdf(HttpServletResponse response, String id) throws NoEncontradoException {
		ConsultaMedica consulta = consultaService.buscarConsulta(id).block();
		if(consulta == null) {
			throw new NoEncontradoException("No se encontró la consulta solicitada");
		}
		Usuario doc = usuarioService.findByCorreo(consulta.getDoctor()).block();
		if(doc == null) {
			throw new NoEncontradoException("No se encontró los datos del médico");
		}
		try (Document document = new Document(PageSize.LETTER)) {
			PdfWriter.getInstance(document, response.getOutputStream());
			document.open();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Paragraph p = new Paragraph("Fecha de la consulta: " + sdf.format(consulta.getFecha()),fontBold);
			p.setAlignment(Element.ALIGN_RIGHT);
			document.add(p);
			p = new Paragraph("Receta Médica",fontBold);
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);
			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(100f);
			table.setSpacingBefore(10);
			table.setWidths(new float[] {1f, 4f});
			PdfPCell cell = new PdfPCell();
			cell.setPadding(5);
			cell.setPhrase(new Phrase("Médico:",font));
			table.addCell(cell);
			cell.setPhrase(new Phrase(doc.fullName(),font));
			table.addCell(cell);
			cell.setPhrase(new Phrase("Paciente:",font));
			table.addCell(cell);
			cell.setPhrase(new Phrase(consulta.getPaciente().getFullName(),font));
			table.addCell(cell);
			document.add(table);
			table = new PdfPTable(4);
			table.setWidthPercentage(100f);
			cell.setPhrase(new Phrase("Edad: " + getEdad(consulta.getPaciente().getFechaNacimiento())+ " años"));
			table.addCell(cell);
			cell.setPhrase(new Phrase("Estatura: "));
			table.addCell(cell);
			cell.setPhrase(new Phrase("Peso: " + consulta.getPeso() + " Kg"));
			table.addCell(cell);
			cell.setPhrase(new Phrase("Temperatura: " + consulta.getTemperatura() + "°C"));
			table.addCell(cell);
			document.add(table);
			p = new Paragraph("Diagnóstico: ", fontBold);
			p.setAlignment(Element.ALIGN_RIGHT);
			p.add(new Phrase(consulta.getDiagnostico(), font));
			p.setSpacingBefore(20);
			document.add(p);
			agregarMedicamentos(document, consulta);
			p = new Paragraph("Firma del médico");
			p.setSpacingBefore(30);
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);
			p = new Paragraph("_______________________________");
			p.setSpacingBefore(30);
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);
			p = new Paragraph(doc.fullName());
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);
			sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			p = new Paragraph("Fecha de impresión: " + sdf.format(new Date()), font);
			p.setSpacingBefore(30);
			p.setAlignment(Element.ALIGN_RIGHT);
			document.add(p);
		} catch(IOException io) {
			logger.error("Ocurrió una excepción al crear el PDF: {}",io.getMessage());
		}
	}
	
	private void agregarMedicamentos(Document document, ConsultaMedica consulta) {
		Paragraph p = new Paragraph("Indicaciones / Medicamentos", fontBold);
		p.setAlignment(Element.ALIGN_CENTER);
		p.setSpacingBefore(15);
		document.add(p);
		PdfPTable table = new PdfPTable(4);
		table.setSpacingBefore(25);
		table.setWidthPercentage(100f);
		PdfPCell cell = new PdfPCell();
		// Headers
		cell.setBorder(0);
		cell.setPhrase(new Phrase("Medicamento",fontBold));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Cantidad",fontBold));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Unidad de medida",fontBold));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Cada",fontBold));
		table.addCell(cell);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		consulta.getMedicacion().forEach(med -> {
			cell.setPhrase(new Phrase(med.getMedicamento()));
			table.addCell(cell);
			cell.setPhrase(new Phrase(med.getCantidad()+""));
			table.addCell(cell);
			cell.setPhrase(new Phrase(med.getUnidadMedida()));
			table.addCell(cell);
			cell.setPhrase(new Phrase(med.getFrecuencia()+" " + med.getPeriodoTiempo()));
			table.addCell(cell);
		});
		document.add(table);
	}

	private Integer getEdad(Date fechaNacimiento) {
		Calendar fechaN = Calendar.getInstance();
		fechaN.setTime(fechaNacimiento);
		Calendar now = Calendar.getInstance();
		long edadEnDias = (now.getTimeInMillis() - fechaN.getTimeInMillis()) / 1000 / 60 / 60 / 24;
		return (int)(edadEnDias / 365.25d);
	}
	
}
