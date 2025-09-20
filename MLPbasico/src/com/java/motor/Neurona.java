package com.java.motor;

//QUE HACE?: Le pasas un vector (las salidas de la capa anterior en fila) y  te devuelve un número.
//Sirve en capas DENSAS: (todas las neuronas de la capa estan conectadas con todas las de la siguiente)

public class Neurona {

	private double[] pesos; // va a haber la misma cantidad de pesos que de entradas de la capa anterior
	private double sesgo;

	// ---- Cache para backprop ----
	private double[] anteriorEntrada;
	private double ultimaSumaPonderada;
	private double ultimoValorDeActivacion;

	public Neurona(int entradas) {
		this.pesos = new double[entradas];
		java.util.Random dado = new java.util.Random();
		// Relleno aleatorio. Porque si se estan creando es que aún n osabe nada
		double scale = Math.sqrt(2.0 / entradas); // He init. Es un modo de randomizar mejorado
		// Si tienes muchas neuronas, los pesos deben ser pequeños, por eso randomizamos
		// según las entradas
		for (int i = 0; i < entradas; i++) {
			// Box–Muller para normal(0,1). Esto es lo del he init. Que al parecer es asi
			double u = Math.max(1e-12, dado.nextDouble());
			double v = Math.max(1e-12, dado.nextDouble());
			double z = Math.sqrt(-2.0 * Math.log(u)) * Math.cos(2 * Math.PI * v);
			this.pesos[i] = z * scale;
		}
		this.sesgo = 0.0;
	}

	static double clip(double v, double lim) { // limita el tamaño maximo del gradiente
		if (v > lim)
			return lim;
		if (v < -lim)
			return -lim;
		return v;
	}

	public double activar(double[] entrada, Funcion f) {// basicamente, usar la neurona. Se le llama forward.
		double suma = 0.0;
		this.anteriorEntrada = entrada.clone();
		for (int i = 0; i < entrada.length; i++) {
			suma += pesos[i] * entrada[i];
		}
		this.ultimaSumaPonderada = suma + sesgo;
		// todas las funciones van a tenr un método desLinearizar por la interfaz
		// funcion, así le puedo pasar cualquier función y que funcione
		this.ultimoValorDeActivacion = f.desLinealizar(this.ultimaSumaPonderada);
		return this.ultimoValorDeActivacion;
	}

	// El backdrop se ejecuta cuando la red llega al final y comprueba el resultado.
	// Entonces todas las capas lo hacen y se lo mandan hacer a las anteriores.
	public double[] backward(double gradSalida, double tasaAprendizaje, Funcion f) {
		// Gradietne es basiamente la direccion y maginitud en la que hay que cambiar
		// los pesos
		// gradSalida viene del error global que haya dado la prediccion
		double gradEntradaActivacion = f.derivada(ultimaSumaPonderada); // f'(z). Esto no da un gradiente completo, pero
																		// sirve para luego
		// Esto es: ¿Que parte del error global le corresponde a esta neurona?
		double gradSumaPonderada = gradSalida * gradEntradaActivacion; // multiplica el fallo que nos envía la capa
																		// siguiente * la derivada que tenga esta

		gradSumaPonderada = clip(gradSumaPonderada, 15.0); // esto evita que un gradiente enorme joda el resto de la
															// red, por eso le pones un limite flow 100

		double[] pesosAnteriores = pesos.clone();// guardamos los viejos

		actualizarPesos(gradSumaPonderada, tasaAprendizaje);

		double[] gradEntradas = new double[pesos.length];
		for (int i = 0; i < pesos.length; i++) {
			gradEntradas[i] = pesosAnteriores[i] * gradSumaPonderada; // aqui chekeamos los pesos viejos para ver cuanto
																		// habían divergido
		}

		return gradEntradas; // esto se pasa a la capa anterior para que haga lo mismo. Así saben cuanto la
								// cagó esta neurona y se ajustas en consecuencia
	}

	public void actualizarPesos(double error, double tasaAprendizaje) { // esto es para que aprenda
		for (int i = 0; i < pesos.length; i++) {
			pesos[i] -= tasaAprendizaje * error * anteriorEntrada[i];
		}
		sesgo -= tasaAprendizaje * error;
	}

	public double[] getAnteriorEntrada() {
		return anteriorEntrada;
	}

	public void setAnteriorEntrada(double[] anteriorEntrada) {
		this.anteriorEntrada = anteriorEntrada;
	}

	public double getUltimaSumaPonderada() {
		return ultimaSumaPonderada;
	}

	public void setUltimaSumaPonderada(double ultimaSumaPonderada) {
		this.ultimaSumaPonderada = ultimaSumaPonderada;
	}

	public double getUltimoValorDeActivacion() {
		return ultimoValorDeActivacion;
	}

	public void setUltimoValorDeActivacion(double ultimoValorDeActivacion) {
		this.ultimoValorDeActivacion = ultimoValorDeActivacion;
	}

	public double[] getPesos() {
		return pesos;
	}

	public void setPesos(double[] pesos) {
		this.pesos = pesos;
	}

	public double getSesgo() {
		return sesgo;
	}

	public void setSesgo(double sesgo) {
		this.sesgo = sesgo;
	}

}
