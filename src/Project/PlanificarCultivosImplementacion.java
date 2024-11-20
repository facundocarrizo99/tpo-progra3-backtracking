package Project;

import Lib.Coordenada;
import Lib.Cultivo;
import Lib.CultivoSeleccionado;
import Lib.PlanificarCultivos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlanificarCultivosImplementacion implements PlanificarCultivos {
    private Cultivo[][] campoResultado;

    @Override
    public List<CultivoSeleccionado> obtenerPlanificacion(List<Cultivo> cultivosDisponibles, double[][] riesgos, String temporada) {
        if (riesgos == null || riesgos.length == 0) {
            throw new IllegalArgumentException("La matriz riesgos no puede ser nula ni vacía.");
        }

        int filas = riesgos.length;
        int columnas = riesgos[0].length;
        int indiceCultivo = 0;
        double mayorBeneficio = 0;
        Cultivo[][] campo = new Cultivo[filas][columnas];
        campoResultado = new Cultivo[filas][columnas];
        Set<Coordenada> coordenadasVisitadas = new HashSet<>();

        mayorBeneficio = obtenerPlan(cultivosDisponibles, riesgos, temporada, indiceCultivo, campo, mayorBeneficio, campoResultado);
        for (Cultivo cultivo: cultivosDisponibles){
            if(cultivo.getTemporadaOptima().equals(temporada)){
                repetirCultivo(cultivo,riesgos,campo, mayorBeneficio, this.campoResultado,coordenadasVisitadas);
            }
        }
        imprimirMatriz(campoResultado);

        return interpretarMatrizCampo(campoResultado, riesgos);
    }

    //TODO hay que hacer los test unitarios para probar el backtraking
    // Se hizo lo posible para que no haya nullpointerExecp pero hay que validar que haga lo que tiene que hacer
    // recomiendo hacer un metodo para imprimir la matriz de cultivos para ver si lo esta haciendo bien.
    private double obtenerPlan(List<Cultivo> cultivosDisponibles, double[][] riesgos, String temporada, int indiceCultivo, Cultivo[][] campo, double mayorBeneficio, Cultivo[][] campoResultado) {
        System.out.println(indiceCultivo);
        if (indiceCultivo == cultivosDisponibles.size()) { // esta parte deberia comentarse para las pruebas unitarias de obtener plan
            double beneficioActual = MetodoAuxiliarCampo.calcularBeneficio(campo,riesgos);
            if(beneficioActual > mayorBeneficio){
                mayorBeneficio = beneficioActual;
                this.campoResultado = copiarCampo(campo);
                System.out.println(mayorBeneficio);
            }
        } else {
            String temporadaOptivaCultivo = cultivosDisponibles.get(indiceCultivo).getTemporadaOptima();
            if (temporadaOptivaCultivo.equals(temporada)) {
                Cultivo cultivo = cultivosDisponibles.get(indiceCultivo);
                List<CoordenadaCultivo> coordenadasValidas = MetodoAuxiliarCoordenada.obtenerCoordenadasValidas(campo, cultivosDisponibles.get(indiceCultivo));
                for (CoordenadaCultivo coordenadas : coordenadasValidas) {
                    Coordenada esquinaSuperiorIzquierda = coordenadas.getEsquinaSuperiorIzquierda();
                    Coordenada esquinaInferiorDerecha = coordenadas.getEsquinaInferiorDerecha();
                    MetodoAuxiliarCampo.agregarCultivo(campo, esquinaSuperiorIzquierda, esquinaInferiorDerecha, cultivo);
                    mayorBeneficio = obtenerPlan(cultivosDisponibles, riesgos, temporada, indiceCultivo + 1, campo, mayorBeneficio, campoResultado);
                    MetodoAuxiliarCampo.sacarCultivo(campo, esquinaSuperiorIzquierda, esquinaInferiorDerecha);
                }
            } else {
                mayorBeneficio = obtenerPlan(cultivosDisponibles, riesgos, temporada, indiceCultivo + 1, campo, mayorBeneficio, campoResultado);
            }
        }
        return mayorBeneficio;
    }

    private double repetirCultivo(Cultivo cultivo, double[][] riesgos, Cultivo[][] campo, double mayorBeneficio, Cultivo[][] campoResultado, Set<Coordenada> coordenadasProbadas) {
        List<CoordenadaCultivo> coordenadasValidas = MetodoAuxiliarCoordenada.obtenerCoordenadasValidasParaRepetir(campo, cultivo);
        if (coordenadasValidas.isEmpty()) {
            double beneficioActual = MetodoAuxiliarCampo.calcularBeneficio(campo, riesgos);
            if (beneficioActual > mayorBeneficio) {
                mayorBeneficio = beneficioActual;
                this.campoResultado = copiarCampo(campo); //no se usa porque solo se necesita cambiar su valor por referencia, se utiliza en un metodo mas arriba
                System.out.println(mayorBeneficio);
            }
        } else {
            for (CoordenadaCultivo coordenadaCultivo : coordenadasValidas) {
                Coordenada esquinaSuperiorIzquierda = coordenadaCultivo.getEsquinaSuperiorIzquierda();
                Coordenada esquinaInferiorDerecha = coordenadaCultivo.getEsquinaInferiorDerecha();
                if (coordenadasProbadas.contains(esquinaSuperiorIzquierda)) {
                    continue; // Saltar si ya se probó esta coordenada
                }
                // Marcar coordenada como probada
                coordenadasProbadas.add(esquinaSuperiorIzquierda);
                MetodoAuxiliarCampo.agregarCultivo(campo, esquinaSuperiorIzquierda, esquinaInferiorDerecha, cultivo);
                mayorBeneficio = repetirCultivo(cultivo, riesgos, campo, mayorBeneficio, campoResultado, coordenadasProbadas);
                MetodoAuxiliarCampo.sacarCultivo(campo, esquinaSuperiorIzquierda, esquinaInferiorDerecha);
            }
        }
        imprimirMatriz(campo);
        System.out.println("\n\n");
        return mayorBeneficio;
    }

    private Cultivo[][] copiarCampo(Cultivo[][] campo) {
        int filas = campo.length;
        int columnas = campo[0].length;

        Cultivo[][] copia = new Cultivo[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                copia[i][j] = campo[i][j];
            }
        }
        return copia;
    }

    public static List<CultivoSeleccionado> interpretarMatrizCampo(Cultivo[][] campoResultado, double[][] riesgos) {
        List<CultivoSeleccionado> cultivosSeleccionados = new ArrayList<>();
        List<Cultivo> cultivos = new ArrayList<>();
        int filas = campoResultado.length;
        int columnas = campoResultado[0].length;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Cultivo cultivo = campoResultado[i][j];
                if (cultivo != null) {
                    if (cultivos.contains(cultivo)) {
                        for (CultivoSeleccionado cultivoSeleccionado : cultivosSeleccionados) {
                            if (cultivoSeleccionado.getNombreCultivo().equals(cultivo.getNombre())) {
                                if (cultivoSeleccionado.getEsquinaInferiorDerecha().getX() == i) {
                                    cultivoSeleccionado.setEsquinaInferiorDerecha(new Coordenada(i, cultivoSeleccionado.getEsquinaInferiorDerecha().getY() + 1));
                                } else if (cultivoSeleccionado.getEsquinaInferiorDerecha().getY() == j) {
                                    cultivoSeleccionado.setEsquinaInferiorDerecha(new Coordenada(cultivoSeleccionado.getEsquinaInferiorDerecha().getX() + 1, j));
                                }
                                cultivoSeleccionado.setMontoInvertido(cultivoSeleccionado.getMontoInvertido()  + cultivo.getCostoPorParcela());
                                cultivoSeleccionado.setRiesgoAsociado((int) (cultivoSeleccionado.getRiesgoAsociado() + riesgos[i][j]));
                                cultivoSeleccionado.setGananciaObtenida(cultivoSeleccionado.getGananciaObtenida() + calcularGananciaDeUnaPArcela(j, cultivo, riesgos[i]));
                            }
                        }
                    } else {
                        Coordenada esquinaSuperiorIzquierda = new Coordenada(i, j);
                        Coordenada esquinaInferiorDerecha = new Coordenada(i, j);
                        CultivoSeleccionado cultivoSeleccionado = new CultivoSeleccionado(cultivo.getNombre(), esquinaSuperiorIzquierda, esquinaInferiorDerecha, cultivo.getInversionRequerida() + cultivo.getCostoPorParcela(), (int) riesgos[i][j], calcularGananciaDeUnaPArcela(j, cultivo, riesgos[i]));
                        cultivosSeleccionados.add(cultivoSeleccionado);
                        cultivos.add(cultivo);
                    }
                }
            }
        }
        return cultivosSeleccionados;
    }

    private static double calcularGananciaDeUnaPArcela(int j, Cultivo cultivo, double[] riesgos) {
        return (1 - riesgos[j]) * (cultivo.getPrecioDeVentaPorParcela() - cultivo.getCostoPorParcela());
    }

    private void imprimirMatriz(Cultivo[][] campo){
        for (int i = 0; i < campo.length; i++) {
            for (int j = 0; j < campo[0].length; j++) {
                if(campo[i][j] != null){
                    System.out.print(" ["+campo[i][j].getNombre() + "] ");
                }else{
                    System.out.print(" [Vacio] ");
                }
            }
            System.out.println();
        }
    }


}