package Project;

import Lib.Cultivo;
import Lib.CultivoSeleccionado;
import Lib.PlanificarCultivos;

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

    private double obtenerPlan(List<Cultivo> CultivosDisponibles, double[][] riesgos, String temporada, int indiceCultivo, Cultivo[][] campo, double mayorBeneficio, Cultivo[][] campoResultado){

        return 0;
    }
}