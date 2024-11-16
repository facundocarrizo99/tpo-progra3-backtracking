package Project;

import Lib.Coordenada;
import Lib.Cultivo;

import java.util.HashSet;
import java.util.Set;

public  class  MetodoAuxiliarCampo {

    public static void sacarCultivo(Cultivo[][] campo, Coordenada coordenadaSuperiorIzq, Coordenada coordenadaInferiorDer){

        for(int i = coordenadaSuperiorIzq.getX(); i<= coordenadaInferiorDer.getX();i++){
            for(int j = coordenadaSuperiorIzq.getY(); j<= coordenadaInferiorDer.getY();j++){
                campo[i][j]= null;
            }
        }
    }
    public static void agregarCultivo(Cultivo[][] campo, Coordenada coordenadaSuperiorIzq, Coordenada coordenadaInferiorDer,Cultivo cultivo){
        if (campo == null || campo.length == 0) {
            throw new IllegalArgumentException("El campo no puede ser nulo ni vacío.");
        }
        if (cultivo == null) {
            throw new IllegalArgumentException("El cultivo no puede ser nulo.");
        }
        for(int i = coordenadaSuperiorIzq.getX(); i<= coordenadaInferiorDer.getX();i++){
            for(int j = coordenadaSuperiorIzq.getY(); j<= coordenadaInferiorDer.getY();j++){
                campo[i][j]= cultivo;
            }
        }

    }

    public static double calcularBeneficio(Cultivo[][] campo, double[][] riesgo ){
        double beneficio= 0.0;
        double potencialParcela = 0.0;
        double inversionTotal= 0.0;
        Set<String> CultivosVisitados = new HashSet<>();
        for(int i=0;i<campo.length;i++){
            for(int j=0;j<campo[0].length;j++){
                if(campo[i][j]!= null){
                    potencialParcela = (1-riesgo[i][j]) * (campo[i][j].getPrecioDeVentaPorParcela() - campo[i][j].getCostoPorParcela());
                    beneficio += potencialParcela;
                    if(!CultivosVisitados.contains(campo[i][j].getNombre())){
                        CultivosVisitados.add(campo[i][j].getNombre());
                        inversionTotal+= campo[i][j].getInversionRequerida();
                    }
                }
            }
        }
        beneficio = beneficio -inversionTotal;
        return beneficio;
    }
}
