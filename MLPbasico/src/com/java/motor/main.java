package com.java.motor;

import java.util.ArrayList;
import java.util.List;

public class main {

	public static void main(String[] args) {

		// ejemplo de grados a kelvin
		Funcion relu = new ReLU();
		Funcion simoind = new Sigmoid();
		Funcion lineal = new Identidad();
		Funcion leakyRelu = new LeakyRelu();

		DatosEntrenamiento exponenciales = new Datos();
		// Esto son los datos, auqneu estaría mejor normalizarlo si usara Sigmoid o si
		// quisiera ir más clean
		// se suele normalizar siempre. Sólo lso datos de entrada y salida. Porque es
		// mas facil aprender a sumar
		// un niño numeros del 0 al 10 que millones y negativos.
		List<double[]> entradas = new ArrayList<double[]>();
		List<double[]> salidas = new ArrayList<double[]>();

		for (int x = -100; x <= 100; x += 1) { // normalizar las cosas a la vez
			entradas.add(new double[] { (x) });
			salidas.add(new double[] { (x * x) });
		}

		exponenciales.setEntradas(entradas);
		exponenciales.setSalidas(salidas);
		int epochs = 5000;
		double tasaDeAprendizaje = 0.01; // esto es lo que cambian los pesos por vuelta. Mucho es poco preciso, pero
											// poco requiere muchas vueltas

		List<CapaDeNeuronas> capas = new ArrayList<CapaDeNeuronas>();
		// la relu aplana, asique no se usa la primera
		capas.add(new CapaDeNeuronas(1, 16, leakyRelu));
		capas.add(new CapaDeNeuronas(16, 16, leakyRelu));
		capas.add(new CapaDeNeuronas(16, 1, lineal));
		RedNeuronal uno = new RedNeuronal(capas);
		uno.entrenar(exponenciales, epochs, tasaDeAprendizaje);
		System.out.println(uno.predice(5));
	}

}
