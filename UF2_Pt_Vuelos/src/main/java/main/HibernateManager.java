package main;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.persistence.StoredProcedureQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;

import models.Personal;
import models.Vuelo;
import persistencia.HibernateUtil;

public class HibernateManager {

	public static void insertarVuelo() {
		Scanner lector = new Scanner(System.in);
		// session y transaction
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		// creamos un objeto vuelo
		Vuelo v = new Vuelo();
		try {
			System.out.println("\nInserta identificador:");
			String identificador = lector.nextLine();
			System.out.println("Inserta aeropuerto_origen:");
			String aeropuerto_origen = lector.nextLine();
			System.out.println("Inserta aeropuerto_destino:");
			String aeropuerto_destino = lector.nextLine();
			System.out.println("Inserta tipo_vuelo:");
			String tipo_vuelo = lector.nextLine();
			System.out.println("Inserta fecha_vuelo (yyyy-MM-dd):");
			String fecha = lector.nextLine();
			DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
			Date fecha_vuelo = formato.parse(fecha);
			v.setIdentificador(identificador);
			v.setAeropuertoOrigen(aeropuerto_origen);
			v.setAeropuertoDestino(aeropuerto_destino);
			v.setTipoVuelo(tipo_vuelo);
			v.setFechaVuelo(fecha_vuelo);
			// save y commit sino rollback
			session.save(v);
			transaction.commit();
			System.out.println("\nVuelo '" + v.getIdentificador() + "' insertado.");
		} catch (RuntimeException e) {
			if (transaction != null)
				transaction.rollback();
			System.out.println("\nError! No se ha podido insertar.");
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("\nError! No se ha podido insertar.");
		} finally {
			// cerramos sesion
			session.close();
		}
	}

	public static void mostrarPersonalPiloto() {
		// session
		Session session = HibernateUtil.getSessionFactory().openSession();
		// list de todo el personal con cargo piloto
		List<Personal> result = (List) session.createQuery("from Personal where categoria = 'piloto'").list();
		System.out.println("\n-Personal con puesto piloto-");
		// foreach recorriendo y printando los datos
		for (Personal p : result)
			System.out.println(p.getCodigo() + "\t" + p.getNombre() + "\t" + p.getCategoria());
		session.close();
	}

	public static void mostrarVuelosSP() {
		// session y stored procedure
		Session session = HibernateUtil.getSessionFactory().openSession();
		StoredProcedureQuery storedProcedure = session.createStoredProcedureQuery("mostrarVuelos", Vuelo.class);
		// list vuelo
		List<Vuelo> result = (List) storedProcedure.getResultList();
		System.out.println("\n-Vuelos posteriores al 17 de feberro de 2020-");
		// foreach recorriendo y printando los datos
		for (Vuelo v : result)
			System.out.println(v.getIdentificador() + "\t" + v.getAeropuertoOrigen() + "\t" + v.getAeropuertoDestino()
					+ "\t" + v.getTipoVuelo() + "\t" + v.getFechaVuelo());
		session.close();
	}
}