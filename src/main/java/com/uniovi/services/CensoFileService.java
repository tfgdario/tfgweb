package com.uniovi.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.User;

@Service
public class CensoFileService {

	@Autowired
	private UserService userService;
	@Autowired
	private PasswordService passwordService;
	@Autowired
	private RolesService rolesService;
	@Autowired
	private EmailService emailService;

	public Map<String, Object> readCensoFile(String fileName) throws Exception {

		List<User> usuarios = null;

		try {
			usuarios = InicializarLecturaFichero(fileName);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		List<User> nuevosUsuarios = new ArrayList<User>();

		int contadorUsuariosCreados = 0;
		StringBuilder sb = new StringBuilder();
		StringBuilder sbError = new StringBuilder();

		for (User user : usuarios) {

			String existe = comprobarUsuario(user, nuevosUsuarios);

			if (existe.equals("")) {
				nuevosUsuarios.add(user);
				// System.out.println(newUser.toString());
				contadorUsuariosCreados++;

			} else {
				// msgRetornoError += existe + "\n";

				sbError.append(" + ").append(existe);
				sbError.append(System.getProperty("line.separator"));
			}
		}

		sb.append("Numero de usuarios creados tras procesar el archivo : " + contadorUsuariosCreados);
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
		if (sbError.length() != 0) {

			sb.append("Problemas encontrados durante el precesamiento del archivo: ");
			sb.append(System.getProperty("line.separator"));
			sb.append(sbError.toString());
			sb.append(System.getProperty("line.separator"));
		} else {
			sb.append("Todos los registros procesados correctamente");
		}

		if (nuevosUsuarios.size() > 0) {
			guardarUsuarios(nuevosUsuarios);
		}

		Map<String, Object> retorno = new HashMap<String, Object>();

		retorno.put("Usuarios", nuevosUsuarios);
		retorno.put("msg", sb.toString());

		return retorno;

		// return null;

	}

	private List<User> InicializarLecturaFichero(String fileName) throws Exception {

		String[] aux = fileName.split(Pattern.quote("."));// substring(fileName.length()-4, fileName.length());
		String extension = aux[aux.length - 1];
		List<User> usuarios = null;

		try {

			switch (extension.toLowerCase()) {
			case "csv":
System.out.println("csv");
				usuarios = readCSV(fileName);
				break;

			case "xlsx":
System.out.println("excel");
				usuarios = readXLSX(fileName);
				break;

			default:
				throw new Exception("Formato no permitido, solo se aceptan .csv y .xlsx");
			}

			return usuarios;

		} catch (FileNotFoundException e) {
			System.err.println("Algo a ocurrido con el archivo: " + e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());

		}

		return null;

	}

	public Map<String, Object> readCensoFileWhileCreatingPoll(String fileName) throws Exception {

		List<User> usuarios = InicializarLecturaFichero(fileName);

		List<User> usuariosComprobados = new ArrayList<User>();

		for (User user : usuarios) {

			boolean correcto = comprobarInfoUsuario(user, usuariosComprobados);

			if (correcto) {
				usuariosComprobados.add(user);
			}
		}

		Map<String, Object> retorno = new HashMap<String, Object>();
		// guardarUsuarios(usuariosComprobados);
		retorno.put("Usuarios", usuariosComprobados);

		return retorno;

	}

	private boolean comprobarInfoUsuario(User user, List<User> usuariosComprobados) {
		if (usuariosComprobados != null && usuariosComprobados.size() > 0) {
			for (User u : usuariosComprobados) {
				if (u.getNumDocumento().equals(user.getNumDocumento())) {
					return false;
				}
			}
		}

		if (!userService.validateDocumentacion(user)) {
			return false;
		}

		return true;
	}

	@SuppressWarnings("resource")
	private List<User> readXLSX(String fileName) throws Exception {

		FileInputStream excelFile = new FileInputStream(new File(fileName));
		Workbook workbook = new XSSFWorkbook(excelFile);
		Sheet datatypeSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = datatypeSheet.iterator();

		List<User> nuevosUsuarios = new ArrayList<User>();
		Row currentRow = iterator.next();
		System.out.println(currentRow.getLastCellNum());
		if (!(currentRow.getLastCellNum() == 13)) {
			System.out.println("error");
			throw new Exception("Archivo .xlsx mal formado");
		}
		while (iterator.hasNext()) {
			System.out.println("iterando");

			currentRow = iterator.next();

			User newUser = new User();
			try {
				newUser.setNombre(currentRow.getCell(0).getStringCellValue());
				newUser.setApellidos(currentRow.getCell(1).getStringCellValue());
				newUser.setNumDocumento(currentRow.getCell(2).getStringCellValue());
				newUser.setNacionalidad(currentRow.getCell(3).getStringCellValue());
				newUser.setEmail(currentRow.getCell(4).getStringCellValue());
				newUser.setTipoDocumento(currentRow.getCell(5).getStringCellValue());
				newUser.setPassword(passwordService.generarPassword(15));
				newUser.setPasswordConfirm(newUser.getPassword());
				newUser.setRole(rolesService.getRoles()[0]);
				newUser.setFechaNacimientoString(currentRow.getCell(6).toString());
				newUser.setCalle(currentRow.getCell(7).toString());
				newUser.setCp(currentRow.getCell(8).toString());
				newUser.setPoblacion(currentRow.getCell(9).toString());
				newUser.setProvincia(currentRow.getCell(10).toString());
				newUser.setPais(currentRow.getCell(11).toString());
				newUser.setGenero(currentRow.getCell(12).toString());
			} catch (Exception e) {
				throw new Exception("Archivo .xlsx mal formado");
			}
			nuevosUsuarios.add(newUser);

		}

		workbook.close();

		return nuevosUsuarios;

	}

	private List<User> readCSV(String fileName) throws Exception {

		List<User> usuarios = new ArrayList<User>();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			br.readLine();

			while ((line = br.readLine()) != null) {
				String[] values = line.split(";");
				if (!(values.length == 13)) {
					throw new Exception("Archivo .xlsx mal formado");
				}
				User newUser = new User();
				try {
					newUser.setNombre(values[0].toString());
					newUser.setApellidos(values[1].toString());
					newUser.setNumDocumento(values[2].toString());
					newUser.setNacionalidad(values[3].toString());
					newUser.setEmail(values[4].toString());
					newUser.setTipoDocumento(values[5].toString());
					newUser.setPassword(passwordService.generarPassword(15));
					newUser.setPasswordConfirm(newUser.getPassword());
					newUser.setRole(rolesService.getRoles()[0]);
					newUser.setFechaNacimientoString(values[6].toString());
					newUser.setCalle(values[7].toString());
					newUser.setCp(values[8].toString());
					newUser.setPoblacion(values[9].toString());
					newUser.setProvincia(values[10].toString());
					newUser.setPais(values[11].toString());
					newUser.setGenero(values[12].toString());
				} catch (Exception e) {
					throw new Exception("Archivo .xlsx mal formado");
				}
				usuarios.add(newUser);

			}
		}
		return usuarios;
	}

	private void guardarUsuarios(List<User> nuevosUsuarios) {
		for (User user : nuevosUsuarios) {
			emailService.sendNewUserEmail(user);
			userService.addUser(user);
		}
	}

	private String comprobarUsuario(User newUser, List<User> nuevosUsuarios) {

		if (userService.getUserByNumDocumento(newUser.getNumDocumento()) != null) {
			return "Número de documento ya esta presente en la base de datos: " + newUser.getNumDocumento();
		} else if (nuevosUsuarios != null && nuevosUsuarios.size() > 0) {
			for (User user : nuevosUsuarios) {
				if (user.getNumDocumento().equals(newUser.getNumDocumento())) {
					return "Número de documento duplicado en el archivo proporcionado: " + newUser.getNumDocumento();
				}
			}
		}

		if (!userService.validateDocumentacion(newUser)) {
			return "Numero de documento mal formado, el numero de documento " + newUser.getNumDocumento()
					+ " no se corresponde con un " + newUser.getTipoDocumento();
		}

		return "";
	}

}
