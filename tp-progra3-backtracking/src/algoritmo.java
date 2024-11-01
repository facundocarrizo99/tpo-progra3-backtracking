import Lib.Cultivo;
import Lib.CultivoSeleccionado;
import Lib.PlanificarCultivos;

import java.util.ArrayList;
import java.util.List;

public class algoritmo implements PlanificarCultivos {
    private int fielSize = 100;


    @Override
    public List<CultivoSeleccionado> obtenerPlanificacion(List<Cultivo> cultivos, double[][] rendimiento, String temporada) {
        List<Cultivo> cultivosTemporada = new ArrayList<>();
        for (Cultivo cultivo : cultivos) {
            if (cultivo.getTemporada().equals(temporada)) {
                cultivosTemporada.add(cultivo);
            }
        }

        List<CultivoSeleccionado> mejorPlanificacion = new ArrayList<>();
        backtrack(cultivosTemporada, rendimiento, 0, new ArrayList<>(), mejorPlanificacion);
        return mejorPlanificacion;
    }

    private void backtrack(List<Cultivo> cultivos, double[][] rendimiento, int index, List<CultivoSeleccionado> currentPlan, List<CultivoSeleccionado> mejorPlanificacion) {
        if (currentPlan.size() == 30) {
            if (mejorPlanificacion.isEmpty() || calcularRendimiento(currentPlan, rendimiento) > calcularRendimiento(mejorPlanificacion, rendimiento)) {
                mejorPlanificacion.clear();
                mejorPlanificacion.addAll(new ArrayList<>(currentPlan));
            }
            return;
        }

        if (index == cultivos.size()) {
            return;
        }

        Cultivo cultivo = cultivos.get(index);
        for (int parcela = 0; parcela < fielSize; parcela++) {
            CultivoSeleccionado cultivoSeleccionado = new CultivoSeleccionado(//todo cambiar a constructor
                           );
            currentPlan.add(cultivoSeleccionado);
            backtrack(cultivos, rendimiento, index + 1, currentPlan, mejorPlanificacion);
            currentPlan.remove(currentPlan.size() - 1);
        }
        backtrack(cultivos, rendimiento, index + 1, currentPlan, mejorPlanificacion);
    }

    private double calcularRendimiento(List<CultivoSeleccionado> plan, double[][] rendimiento) {
        double totalRendimiento = 0;
        for (CultivoSeleccionado cultivoSeleccionado : plan) {
            totalRendimiento += rendimiento[cultivoSeleccionado.getCultivo().getId()][cultivoSeleccionado.getParcela()];
        }
        return totalRendimiento;
    }
}
