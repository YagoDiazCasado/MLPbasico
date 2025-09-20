package com.java.motor;

import java.util.ArrayList;
import java.util.List;

public class CapaDeNeuronas {

	private Funcion f;
	private List<Neurona> neuronas = new ArrayList<Neurona>(); // la longitud de esto es la enrada de las neuronas de la
	private int numDeNeuronasCapaAnterior;

	public CapaDeNeuronas(int numSalidaCapaAnterior, int numeroDeNeuronasEnEstaCapa, Funcion f) {
		this.f = f;
		for (int i = 0; i < numeroDeNeuronasEnEstaCapa; i++) {
			neuronas.add(new Neurona(numSalidaCapaAnterior));
		}
	}

	public double[] salidaCapa(double[] entrada) {
		this.numDeNeuronasCapaAnterior = entrada.length;
		double[] salida = new double[neuronas.size()];
		for (int i = 0; i < neuronas.size(); i++) {
			salida[i] = neuronas.get(i).activar(entrada, f);
		}
		return salida;
	}

	// Para que la movida aprenda hace falta lo que se llama Backpropagation o
	// Backprop.
	// Es el que va a calcular el error y cambia los pesos
	public double[] backward(double[] gradienteSalidasDeLaSiguiente, double tasaAprendizaje) {
		double[] gradienteEntradas = new double[numDeNeuronasCapaAnterior];
		for (int i = 0; i < neuronas.size(); i++) {
			// cada neurona devuelve sus gradientes hacia atrás
			double[] gradienteNeurona = neuronas.get(i).backward(gradienteSalidasDeLaSiguiente[i], tasaAprendizaje, f);
			// los acumulamos porque todas las neuronas de esta capa usan las mismas
			// entradas
			for (int j = 0; j < numDeNeuronasCapaAnterior; j++) {
				gradienteEntradas[j] += gradienteNeurona[j];
			}
		}
		return gradienteEntradas; // pa la capa anterior
	} // Estudair, porque no entiendo porque es una lista lo que se pasa, como sabe
		// que peso tocar etc

	public List<Neurona> getNeuronas() {
		return neuronas;
	}

	public void setNeuronas(List<Neurona> neuronas) {
		this.neuronas = neuronas;
	}

}
