package Project;

import Lib.Coordenada;
import Lib.Cultivo;
import Lib.CultivoSeleccionado;
import Lib.PlanificarCultivos;
import Project.MetodoAuxiliarCampo;

import java.util.List;

public class PlanificarCultivosImplementacion implements PlanificarCultivos {

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
        Cultivo[][] campoResultado = new Cultivo[filas][columnas];

        obtenerPlan(cultivosDisponibles, riesgos, temporada, indiceCultivo, campo, mayorBeneficio, campoResultado);
        //TODO El metodo obtenerPlan cambia el valor por referencia de campoResultado con el mejor resultado.
        // Hay que tranformar esa matriz en una lista de CultivosSeleccionados,
        // dentro de la matriz campo resultado hay cultivos, hay que recorrerla y hacer la conversion a lo que pide la profe
        return null;
    }
    //TODO hay que hacer los test unitarios para probar el backtraking
    // Se hizo lo posible para que no haya nullpointerExecp pero hay que validar que haga lo que tiene que hacer
    // recomiendo hacer un metodo para imprimir la matriz de cultivos para ver si lo esta haciendo bien.
    private double obtenerPlan(List<Cultivo> cultivosDisponibles, double[][] riesgos, String temporada, int indiceCultivo, Cultivo[][] campo, double mayorBeneficio, Cultivo[][] campoResultado){
        if (indiceCultivo == cultivosDisponibles.size()){ // esta parte deberia comentarse para las pruebas unitarias de obtener plan
            for (Cultivo cultivo : cultivosDisponibles){
                String temporadaOptivaCultivo = cultivo.getTemporadaOptima();
                if (temporadaOptivaCultivo.equals(temporada)){
                    mayorBeneficio = repetirCultivo(cultivo, riesgos,campo,mayorBeneficio,campoResultado);
                }
            }
        }else {
            String temporadaOptivaCultivo = cultivosDisponibles.get(indiceCultivo).getTemporadaOptima();
            if (temporadaOptivaCultivo.equals(temporada)){
                Cultivo cultivo = cultivosDisponibles.get(indiceCultivo);
                List<CoordenadaCultivo> coordenadasValidas = MetodoAuxiliarCoordenada.obtenerCoordenadasValidas(campo, cultivo);

                for (CoordenadaCultivo coordenadas : coordenadasValidas){
                    Coordenada esquinaSuperiorIzquierda = coordenadas.getEsquinaSuperiorIzquierda();
                    Coordenada esquinaInferiorDerecha = coordenadas.getEsquinaInferiorDerecha();
                    MetodoAuxiliarCampo.agregarCultivo(campo,esquinaSuperiorIzquierda,esquinaInferiorDerecha, cultivo);
                    mayorBeneficio = obtenerPlan(cultivosDisponibles, riesgos, temporada, indiceCultivo + 1, campo, mayorBeneficio, campoResultado);
                    MetodoAuxiliarCampo.sacarCultivo(campo,esquinaSuperiorIzquierda,esquinaInferiorDerecha);
                }
            }else{
                mayorBeneficio = obtenerPlan(cultivosDisponibles, riesgos, temporada, indiceCultivo + 1, campo, mayorBeneficio, campoResultado);
            }
        }
        return mayorBeneficio;
    }

    private double repetirCultivo(Cultivo cultivo, double[][] riesgos, Cultivo[][] campo, double mayorBeneficio, Cultivo[][] campoResultado){
        List<CoordenadaCultivo> coordenadasValidas = MetodoAuxiliarCoordenada.obtenerCoordenadasValidas(campo, cultivo);
        if (coordenadasValidas.isEmpty()){
            double beneficioActual = MetodoAuxiliarCampo.calcularBeneficio(campo, riesgos);
            if (beneficioActual > mayorBeneficio){
                mayorBeneficio = beneficioActual;
                campoResultado = copiarCampo(campo); //no se usa porque solo se necesita cambiar su valor por referencia, se utiliza en un metodo mas arriba
            }
        }else{
            for(CoordenadaCultivo coordenadaCultivo : coordenadasValidas){
                Coordenada esquinaSuperiorIzquierda = coordenadaCultivo.getEsquinaSuperiorIzquierda();
                Coordenada esquinaInferiorDerecha = coordenadaCultivo.getEsquinaInferiorDerecha();
                MetodoAuxiliarCampo.agregarCultivo(campo, esquinaSuperiorIzquierda, esquinaInferiorDerecha,cultivo);
                mayorBeneficio = repetirCultivo(cultivo,riesgos,campo,mayorBeneficio,campoResultado);
                MetodoAuxiliarCampo.sacarCultivo(campo,esquinaSuperiorIzquierda, esquinaInferiorDerecha);
            }
        }
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
}