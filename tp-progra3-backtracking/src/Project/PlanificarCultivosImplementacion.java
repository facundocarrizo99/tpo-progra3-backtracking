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
        return null;
    }

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
                List<CoordenadaCultivo> coordenadasValidas = obtenerCoordenadasValidas(campo, cultivo);

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
        List<CoordenadaCultivo> coordenadasValidas = obtenerCoordenadasValidas(campo, cultivo);
        if (coordenadasValidas.isEmpty()){
            double beneficioActual = MetodoAuxiliarCampo.calcularBeneficio(campo, riesgos);
            if (beneficioActual > mayorBeneficio){
                mayorBeneficio = beneficioActual;

            }
        }
        return 0;
    }

    private Cultivo[][] copiarCampo(Cultivo[][] campo){
        int filas = campo.length;
        int columnas = campo[0].length;

        Cultivo[][] copia = new Cultivo[filas][columnas];
        for (int i = 0; i <= filas; i++){
            for(int j=0; j<= columnas; j++){
                
            }
        }
    }
}