package com.uniovi.services;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.entities.OpcionesVotacion;
import com.uniovi.entities.Pais;
import com.uniovi.entities.User;
import com.uniovi.entities.Votacion;
import com.uniovi.entities.Voto;
import com.uniovi.repositories.PaisRepository;

@Service
public class InsertSampleDataService {

	@Autowired
	private PaisRepository paisRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private RolesService rolesService;
	@Autowired
	private VotacionService votacionService;
	@Autowired
	private VotoService votoService;
	@Autowired
	private OpcionesVotacionService opcionesService;

	@PostConstruct
	public void init() {
		
		System.out.println("Cargando valores por defecto");

		String[] locales = Locale.getISOCountries();

		for (String countryCode : locales) {
			Locale obj = new Locale("", countryCode);
			paisRepository.save(new Pais(obj.getDisplayCountry(), obj.getISO3Country()));
		}
	
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());

	
		Votacion votacion = new Votacion();

		votacion.setNombre("Referendum 1");
		votacion.setConsulta("¿Ganará España la Eurocopa 2024?");
		votacion.setFechaInicio(c.getTime());
		c.add(Calendar.HOUR, 1);
		votacion.setFechaFin(c.getTime());
		votacion.setForAll(true);

		votacionService.addVotacion(votacion);

		
		Votacion votacion2 = new Votacion();
		
		votacion2.setNombre("Referendum 2");
		votacion2.setConsulta("¿Qué equipo ganará la Eurocopa 2024?");
		votacion2.setFechaInicio(c.getTime());
		c.add(Calendar.HOUR, 1);
		votacion2.setFechaFin(c.getTime());
		votacion2.setForAll(true);

		votacionService.addVotacion(votacion2);
		
		
		Votacion votacion3 = new Votacion();
		
		votacion3.setNombre("Referendum 3");
		votacion3.setConsulta("¿Crees que habrá que repetir las elecciones catalanas?");
		votacion3.setFechaInicio(c.getTime());
		c.add(Calendar.HOUR, 1);
		votacion3.setFechaFin(c.getTime());
		votacion3.setForAll(false);

		votacionService.addVotacion(votacion3);
		
		
		Votacion votacion4 = new Votacion();
		
		votacion4.setNombre("Referendum 4");
		votacion4.setConsulta("¿Cuántas medallas de oro conseguirá España en los JJOO de París 2024?");
		votacion4.setFechaInicio(c.getTime());
		c.add(Calendar.HOUR, 1);
		votacion4.setFechaFin(c.getTime());
		votacion4.setForAll(true);

		votacionService.addVotacion(votacion4);
		
		
		Votacion votacion5 = new Votacion();
		
		votacion5.setNombre("Referendum 5");
		votacion5.setConsulta("¿A qué partido político votarás en las próximas elecciones generales?");
		votacion5.setFechaInicio(c.getTime());
		c.add(Calendar.HOUR, 1);
		votacion5.setFechaFin(c.getTime());
		votacion5.setForAll(false);

		votacionService.addVotacion(votacion5);
		
		//--------------------------------
		Votacion votacion6 = new Votacion();
		
		votacion6.setNombre("Referendum 6");
		votacion6.setConsulta("¿A qué partido político votarás en las próximas elecciones generales?");
		votacion6.setFechaInicio(c.getTime());
		c.add(Calendar.HOUR, 1);
		votacion6.setFechaFin(c.getTime());
		votacion6.setForAll(true);

		votacionService.addVotacion(votacion6);
		
		Votacion votacion7 = new Votacion();
		
		votacion7.setNombre("Referendum 6");
		votacion7.setConsulta("¿A qué partido político votarás en las próximas elecciones generales?");
		votacion7.setFechaInicio(c.getTime());
		c.add(Calendar.HOUR, 1);
		votacion7.setFechaFin(c.getTime());
		votacion7.setForAll(true);

		votacionService.addVotacion(votacion7);
		
		
		
		Votacion votacion8 = new Votacion();
		
		votacion8.setNombre("Referendum 6");
		votacion8.setConsulta("¿A qué partido político votarás en las próximas elecciones generales?");
		votacion8.setFechaInicio(c.getTime());
		c.add(Calendar.HOUR, 1);
		votacion8.setFechaFin(c.getTime());
		votacion8.setForAll(true);

		votacionService.addVotacion(votacion8);
		
		
		
		//--------------------------------------------
		
		opcionesService.addOpcion(new OpcionesVotacion(null,"En Blanco"));
		opcionesService.addOpcion(new OpcionesVotacion(votacion,"Si"));
		opcionesService.addOpcion(new OpcionesVotacion(votacion,"No"));
		
		opcionesService.addOpcion(new OpcionesVotacion(votacion2,"España"));
		opcionesService.addOpcion(new OpcionesVotacion(votacion2,"Alemania"));
		opcionesService.addOpcion(new OpcionesVotacion(votacion2,"Portugal"));
		opcionesService.addOpcion(new OpcionesVotacion(votacion2,"Francia"));
		opcionesService.addOpcion(new OpcionesVotacion(votacion2,"Inglaterra"));
		opcionesService.addOpcion(new OpcionesVotacion(votacion2,"Suiza"));
		opcionesService.addOpcion(new OpcionesVotacion(votacion2,"Holanda"));
		opcionesService.addOpcion(new OpcionesVotacion(votacion2,"Turquia"));
		
		opcionesService.addOpcion(new OpcionesVotacion(votacion3,"Sí"));
		opcionesService.addOpcion(new OpcionesVotacion(votacion3,"No"));
		opcionesService.addOpcion(new OpcionesVotacion(votacion3,"No sabe / No contesta"));
		
		opcionesService.addOpcion(new OpcionesVotacion(votacion4,"Menos de 5"));
		opcionesService.addOpcion(new OpcionesVotacion(votacion4,"Entre 5 y 10"));
		opcionesService.addOpcion(new OpcionesVotacion(votacion4,"Entre 10 y 20"));
		opcionesService.addOpcion(new OpcionesVotacion(votacion4,"Más de 20"));
		
		opcionesService.addOpcion(new OpcionesVotacion(votacion5,"PP"));
		opcionesService.addOpcion(new OpcionesVotacion(votacion5,"PSOE"));
		opcionesService.addOpcion(new OpcionesVotacion(votacion5,"Sumar"));
		opcionesService.addOpcion(new OpcionesVotacion(votacion5,"Podemos"));
		opcionesService.addOpcion(new OpcionesVotacion(votacion5,"Vox"));
	
		
		
		

		User user = new User();

		user.setNombre("admin");
		user.setPassword("admin");
		user.setNumDocumento("0");
		user.setTipoDocumento("DNI");
		user.setEmail("evoteapp.tfg@gmail.com");
		user.setRole(rolesService.getRoles()[1]);

		userService.addUser(user);

		user = new User();

		user.setNumDocumento("53548918L");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 321");
		user.setCp("33686");
		user.setPoblacion("Serrapio");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Dario");
		user.setApellidos("Suarez");
		user.setPassword("dario");
		user.setGenero("NB");
		user.setRole(rolesService.getRoles()[0]);
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(1991, 11, 6);
		user.setFechaNacimiento(gc.getTime());

		user.getEdad();

		
		userService.addUser(user);

		Voto voto = new Voto();
		voto.setUser(user);
		voto.setVotacion(votacion3);

		votoService.addVoto(voto);
		
		
		voto = new Voto();
		voto.setUser(user);
		voto.setVotacion(votacion5);

		votoService.addVoto(voto);
		
		
		user = new User();

		user.setNumDocumento("33333333P");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 14");
		user.setCp("33002");
		user.setPoblacion("Oviedo");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Cersei");
		user.setApellidos("Lannister");
		user.setPassword("Tfg#2024");
		user.setGenero("F");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1990, 11, 26);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);

		
		
		user = new User();

		user.setNumDocumento("11111112L");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 14");
		user.setCp("33002");
		user.setPoblacion("Oviedo");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Jaime");
		user.setApellidos("Lannister");
		user.setPassword("Tfg#2024");
		user.setGenero("M");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1985, 7, 14);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);		
		
		
		
		user = new User();

		user.setNumDocumento("X1234567L");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("VEN");
		user.setTipoDocumento("NIE");
		user.setCalle("Calle falsa 13");
		user.setCp("33002");
		user.setPoblacion("Oviedo");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Daenerys");
		user.setApellidos("Targaryen");
		user.setPassword("Tfg#2024");
		user.setGenero("F");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1990, 6, 23);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);
		
		
		
		user = new User();

		user.setNumDocumento("11111111H");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 127");
		user.setCp("33206");
		user.setPoblacion("Gijón");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Tyrion");
		user.setApellidos("Lannister");
		user.setPassword("Tfg#2024");
		user.setGenero("M");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1991, 5, 5);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);

		
		
		user = new User();

		user.setNumDocumento("11111113C");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 36");
		user.setCp("33206");
		user.setPoblacion("Gijón");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Arya");
		user.setApellidos("Stark");
		user.setPassword("Tfg#2024");
		user.setGenero("F");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(2005, 3, 21);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);
	
		
		
		user = new User();

		user.setNumDocumento("22222222J");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 123");
		user.setCp("33206");
		user.setPoblacion("Gijón");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Jon");
		user.setApellidos("Nieve");
		user.setPassword("Tfg#2024");
		user.setGenero("M");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1991, 5, 5);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);

		
		
		user = new User();

		user.setNumDocumento("55555555K");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 25");
		user.setCp("33206");
		user.setPoblacion("Gijón");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Sansa");
		user.setApellidos("Stark");
		user.setPassword("Tfg#2024");
		user.setGenero("F");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(2003, 12, 15);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);
		
		
		
		user = new User();

		user.setNumDocumento("11111114K");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 25");
		user.setCp("33206");
		user.setPoblacion("Gijón");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Bran");
		user.setApellidos("Stark");
		user.setPassword("Tfg#2024");
		user.setGenero("M");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(2005, 6, 18);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);
		
		
		
		user = new User();

		user.setNumDocumento("11111115E");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 1");
		user.setCp("33002");
		user.setPoblacion("Oviedo");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Eddard");
		user.setApellidos("Stark");
		user.setPassword("Tfg#2024");
		user.setGenero("M");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1986, 10, 14);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);

		
		
		user = new User();

		user.setNumDocumento("11111116T");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 1");
		user.setCp("33002");
		user.setPoblacion("Oviedo");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Samwell");
		user.setApellidos("Tarly");
		user.setPassword("Tfg#2024");
		user.setGenero("M");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1976, 8, 21);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);
		
		
		
		user = new User();

		user.setNumDocumento("66666666Q");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 127");
		user.setCp("33002");
		user.setPoblacion("Oviedo");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Theon");
		user.setApellidos("Greyjoy");
		user.setPassword("Tfg#2024");
		user.setGenero("M");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1990, 11, 26);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);
		
		
		
		user = new User();

		user.setNumDocumento("44444445G");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 32");
		user.setCp("33403");
		user.setPoblacion("Avilés");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Joffrey");
		user.setApellidos("Baratheon");
		user.setPassword("Tfg#2024");
		user.setGenero("M");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1992, 11, 16);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);
		
		
		
		user = new User();

		user.setNumDocumento("54444446L");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 11");
		user.setCp("33002");
		user.setPoblacion("Oviedo");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Brienne");
		user.setApellidos("de Tarth");
		user.setPassword("Tfg#2024");
		user.setGenero("F");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1989, 4, 1);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);
		
		
		
		user = new User();

		user.setNumDocumento("44444444A");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 173");
		user.setCp("33403");
		user.setPoblacion("Avilés");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Pethyr");
		user.setApellidos("Baelish");
		user.setPassword("Tfg#2024");
		user.setGenero("NB");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1992, 5, 12);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);
		
		

		user = new User();

		user.setNumDocumento("77777777B");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 55");
		user.setCp("33403");
		user.setPoblacion("Avilés");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Jorah");
		user.setApellidos("Mormont");
		user.setPassword("Tfg#2024");
		user.setGenero("M");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1958, 12, 14);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);

		
		
		user = new User();

		user.setNumDocumento("56444447D");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 98");
		user.setCp("33403");
		user.setPoblacion("Avilés");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Robb");
		user.setApellidos("Stark");
		user.setPassword("Tfg#2024");
		user.setGenero("M");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1993, 9, 8);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);		
		
		
		
		user = new User();

		user.setNumDocumento("66444447T");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 111");
		user.setCp("33002");
		user.setPoblacion("Oviedo");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Davos");
		user.setApellidos("Seaworth");
		user.setPassword("Tfg#2024");
		user.setGenero("M");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1960, 11, 10);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);
		
		
		
		user = new User();

		user.setNumDocumento("75644447T");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 46");
		user.setCp("33206");
		user.setPoblacion("Gijón");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Sandor");
		user.setApellidos("Clegane");
		user.setPassword("Tfg#2024");
		user.setGenero("M");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1979, 6, 23);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);
		
		
		
		user = new User();

		user.setNumDocumento("99999999R");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 63");
		user.setCp("33206");
		user.setPoblacion("Gijón");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Catelyn");
		user.setApellidos("Tully");
		user.setPassword("Tfg#2024");
		user.setGenero("F");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1975, 7, 24);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);		
		

		
		user = new User();

		user.setNumDocumento("75644487V");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 5");
		user.setCp("33002");
		user.setPoblacion("Oviedo");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Yara");
		user.setApellidos("Greyjoy");
		user.setPassword("Tfg#2024");
		user.setGenero("F");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(2002, 3, 18);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);

		
		
		user = new User();

		user.setNumDocumento("53555221C");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 10");
		user.setCp("33403");
		user.setPoblacion("Avilés");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Margaery");
		user.setApellidos("Tyrell");
		user.setPassword("Tfg#2024");
		user.setGenero("F");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(2000, 5, 8);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);		

		
		
		user = new User();

		user.setNumDocumento("10818562Y");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 101");
		user.setCp("33403");
		user.setPoblacion("Avilés");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Stannis");
		user.setApellidos("Baratheon");
		user.setPassword("Tfg#2024");
		user.setGenero("M");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1961, 10, 28);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);
		
		
		
		user = new User();

		user.setNumDocumento("71552365D");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 16");
		user.setCp("33002");
		user.setPoblacion("Oviedo");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Dickon");
		user.setApellidos("Tarly");
		user.setPassword("Tfg#2024");
		user.setGenero("M");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1996, 8, 14);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);
		
		
		
		user = new User();

		user.setNumDocumento("10818566Y");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 7");
		user.setCp("33206");
		user.setPoblacion("Gijón");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Lysa");
		user.setApellidos("Arryn");
		user.setPassword("Tfg#2024");
		user.setGenero("F");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1968, 12, 12);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);	
		
		
		
		user = new User();

		user.setNumDocumento("53456852E");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 19");
		user.setCp("33206");
		user.setPoblacion("Gijón");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Robert");
		user.setApellidos("Baratheon");
		user.setPassword("Tfg#2024");
		user.setGenero("M");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1959, 7, 7);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);	
		
		
		
		user = new User();

		user.setNumDocumento("11235647D");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 22");
		user.setCp("33002");
		user.setPoblacion("Oviedo");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Viserys");
		user.setApellidos("Targaryen");
		user.setPassword("Tfg#2024");
		user.setGenero("M");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1960, 1, 15);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);
		
		
		
		user = new User();

		user.setNumDocumento("10854236F");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 6");
		user.setCp("33206");
		user.setPoblacion("Gijón");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Khal");
		user.setApellidos("Drogo");
		user.setPassword("Tfg#2024");
		user.setGenero("M");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1965, 4, 17);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);	
		
		
		
		user = new User();

		user.setNumDocumento("10445998L");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 21");
		user.setCp("33206");
		user.setPoblacion("Gijón");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Tywin");
		user.setApellidos("Lannister");
		user.setPassword("Tfg#2024");
		user.setGenero("M");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1959, 10, 21);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);	
		
		
		
		user = new User();

		user.setNumDocumento("71256874E");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 3");
		user.setCp("33403");
		user.setPoblacion("Avilés");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Talisa");
		user.setApellidos("Maegyr");
		user.setPassword("Tfg#2024");
		user.setGenero("F");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1988, 10, 1);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);
		
		
		
		user = new User();

		user.setNumDocumento("10553268V");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 102");
		user.setCp("33002");
		user.setPoblacion("Oviedo");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Renly");
		user.setApellidos("Baratheon");
		user.setPassword("Tfg#2024");
		user.setGenero("M");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1989, 2, 28);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);
		
		
		
		user = new User();

		user.setNumDocumento("09546879Q");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 73");
		user.setCp("33206");
		user.setPoblacion("Gijón");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Jeor");
		user.setApellidos("Mormont");
		user.setPassword("Tfg#2024");
		user.setGenero("M");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1957, 9, 20);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);	
		
		
		
		user = new User();

		user.setNumDocumento("11058654R");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 12");
		user.setCp("33002");
		user.setPoblacion("Oviedo");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Oberyn");
		user.setApellidos("Martell");
		user.setPassword("Tfg#2024");
		user.setGenero("M");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1963, 12, 2);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);
		
		
		
		user = new User();

		user.setNumDocumento("71589632Q");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 28");
		user.setCp("33403");
		user.setPoblacion("Avilés");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Euron");
		user.setApellidos("Greyjoy");
		user.setPassword("Tfg#2024");
		user.setGenero("M");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1985, 1, 2);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);
		
		
		
		user = new User();

		user.setNumDocumento("53547621X");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 112");
		user.setCp("33002");
		user.setPoblacion("Oviedo");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Randyll");
		user.setApellidos("Tarly");
		user.setPassword("Tfg#2024");
		user.setGenero("M");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1959, 1, 27);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);
		
		
		
		user = new User();

		user.setNumDocumento("10523888P");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 25");
		user.setCp("33206");
		user.setPoblacion("Gijón");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Podrick");
		user.setApellidos("Payne");
		user.setPassword("Tfg#2024");
		user.setGenero("M");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1985, 8, 21);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);	
		
		
		
		user = new User();

		user.setNumDocumento("53546221J");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 16");
		user.setCp("33002");
		user.setPoblacion("Oviedo");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Alys");
		user.setApellidos("Karstark");
		user.setPassword("Tfg#2024");
		user.setGenero("F");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1994, 6, 28);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);
		
		
		
		user = new User();

		user.setNumDocumento("10222336D");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 51");
		user.setCp("33403");
		user.setPoblacion("Avilés");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Selyse");
		user.setApellidos("Baratheon");
		user.setPassword("Tfg#2024");
		user.setGenero("F");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1969, 11, 30);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);
		
		
		
		user = new User();

		user.setNumDocumento("53555662R");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 168");
		user.setCp("33002");
		user.setPoblacion("Oviedo");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Obara");
		user.setApellidos("Sand");
		user.setPassword("Tfg#2024");
		user.setGenero("F");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1996, 12, 31);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);
		
		
		
		user = new User();

		user.setNumDocumento("10886224W");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 27");
		user.setCp("33403");
		user.setPoblacion("Avilés");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Illyrio");
		user.setApellidos("Mopatis");
		user.setPassword("Tfg#2024");
		user.setGenero("M");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1961, 12, 8);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);

		
		
		user = new User();

		user.setNumDocumento("53212323Y");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 42");
		user.setCp("33403");
		user.setPoblacion("Avilés");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Tommen");
		user.setApellidos("Baratheon");
		user.setPassword("Tfg#2024");
		user.setGenero("M");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(2002, 6, 18);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);		
		
		

		user = new User();

		user.setNumDocumento("11456987C");
		user.setEmail("tfgpruebas2024@gmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 142");
		user.setCp("33403");
		user.setPoblacion("Avilés");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Harald");
		user.setApellidos("Karstark");
		user.setPassword("Tfg#2024");
		user.setGenero("M");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(1976, 7, 24);
		user.setFechaNacimiento(gc.getTime());
		user.getEdad();
		userService.addUser(user);		
		
		
		user = new User();

		user.setNumDocumento("53548918S");
		user.setEmail("dariosuarez_@hotmail.com");
		user.setNacionalidad("ESP");
		user.setTipoDocumento("DNI");
		user.setCalle("Calle falsa 321");
		user.setCp("33686");
		user.setPoblacion("Serrapio");
		user.setProvincia("Asturias");
		user.setPais("España");
		user.setNombre("Kobe");
		user.setApellidos("Bryant");
		user.setPassword("dario");
		user.setGenero("F");
		user.setRole(rolesService.getRoles()[0]);
		gc.set(2000, 2, 3);
		user.setFechaNacimiento(gc.getTime());
		
		userService.addUser(user);
		
		
		
	}

}
