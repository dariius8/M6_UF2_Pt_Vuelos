package main;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		menuPrincipal();
	}

	public static void menuPrincipal() {
		Scanner lector = new Scanner(System.in);
		int i = 0;
		while (i != 4) {
			System.out.println("\nMENU PRINCIPAL");
			System.out.println("   1. Insertar un vuelo y mostrarlo");
			System.out.println("   2. Listar al personal con puesto piloto");
			System.out.println("   3. Listar todos los vuelos posteriores al 17-feb-2020 (Stored Procedure)");
			System.out.println("   4. Salir");
			System.out.print("Escriu una opcio: ");
			i = lector.nextInt();
			if (i > 0 && i < 5) {
				switch (i) {
				case 1:
					HibernateManager.insertarVuelo();
					break;
				case 2:
					HibernateManager.mostrarPersonalPiloto();
					break;
				case 3:
					HibernateManager.mostrarVuelosSP();
					break;
				default:
					System.out.println("\nAdios!");
					break;
				}
			} else
				System.out.println("\nError! Valor incorrecto.");
		}
	}
}
