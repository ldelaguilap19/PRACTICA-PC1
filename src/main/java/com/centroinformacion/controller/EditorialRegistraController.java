package com.centroinformacion.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.centroinformacion.entity.Editorial;
import com.centroinformacion.entity.Usuario;
import com.centroinformacion.service.EditorialService;
import com.centroinformacion.service.EjemploService;
import com.centroinformacion.util.AppSettings;

@RestController
@RequestMapping("/url/editorial")
@CrossOrigin(AppSettings.URL_CROSS_ORIGIN)
public class EditorialRegistraController {
	
	@Autowired
	private EditorialService editorialService;
	
	@GetMapping
	public ResponseEntity<List<Editorial>> lista(){
		List<Editorial> lstSalida = editorialService.listaEditorial();
		return ResponseEntity.ok(lstSalida);
	}

	@PostMapping
	public ResponseEntity<?> registra(@RequestBody Editorial objEditorial){
		HashMap<String, Object> salida = new HashMap<>();
		objEditorial.setFechaRegistro(new Date());
		objEditorial.setFechaActualizacion(new Date());
		objEditorial.setEstado(AppSettings.ACTIVO);
		
		Usuario objUsuario = new Usuario();
		objUsuario.setIdUsuario(1);
		
		objEditorial.setUsuarioRegistro(objUsuario);
		objEditorial.setUsuarioActualiza(objUsuario);
		
		Editorial objSalida = editorialService.insertaActualizaEditorial(objEditorial);
		if (objSalida == null) {
			salida.put("mensaje", "Error en el registro");
		}else {
			salida.put("mensaje", "Registro de ejemplo con el ID >>> " + objEditorial.getIdEditorial() + 
					" >>> DES >> "+ objEditorial.getRazonSocial());
		}
		return ResponseEntity.ok(salida);
	}

}
