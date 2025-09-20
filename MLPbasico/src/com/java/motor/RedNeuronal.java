package com.java.motor;

import java.util.List;

public class RedNeuronal {
	private List<CapaDeNeuronas> capas;

	public RedNeuronal(List<CapaDeNeuronas> capasDensasListas) { // en el main le pasaremos una lista de capas con sus
																	// propias funciones de activación
		this.capas = capasDensasListas;
	}

	public void entrenar(DatosEntrenamiento datosDeEntrenamiento, int epochs, double tasaDeAprendizaje) { // xd que
																											// optimista
		List<double[]> entradas = datosDeEntrenamiento.getEntradas();
		List<double[]> salidas = datosDeEntrenamiento.getSalidas();

		for (int e = 0; e < epochs; e++) {
			double perdidaMedia = 0.0;
			for (int i = 0; i < entradas.size(); i++) {
				double[] datoEntreno = entradas.get(i);
				double[] loQueTeniaQueSalir = salidas.get(i);
				double[] loQueHaSalido = forward(datoEntreno);

				perdidaMedia += CorrectorDeCadaVuelta.calcularPerdida(loQueTeniaQueSalir, loQueHaSalido);
				double[] gradiente = CorrectorDeCadaVuelta.gradienteRespectoAlIntento(loQueTeniaQueSalir,
						loQueHaSalido);

				// Backprop (desde la última capa hacia la primera)
				for (int k = capas.size() - 1; k >= 0; k--) {
					gradiente = capas.get(k).backward(gradiente, tasaDeAprendizaje);
				}
			}

			perdidaMedia /= Math.max(1, entradas.size());
			if (e % 50 == 0) {
				System.out.println("Época " + e + " | pérdida media = " + perdidaMedia);
			}
		}
	}

	private double[] forward(double[] entrada) {
		double[] a = entrada;
		for (CapaDeNeuronas capa : capas) {
			a = capa.salidaCapa(a);
		}
		return a;
	}

	public String predice(double valor) {
		// Aquí habría que llamar a las capas en orden y usar el forward únicamente,
		// aprovechando que los pesos ya están puestos
		// predice es un ciclo de entrenamiento pero sin backprop
		double[] entrada = new double[] { valor };
		double[] salida = forward(entrada);

		if (salida.length == 1) {
			return Double.toString(salida[0]);
		} else {
			StringBuilder sb = new StringBuilder("[");
			for (int i = 0; i < salida.length; i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(salida[i]);
			}
			sb.append("]");
			return sb.toString();
		}
	}

}
