package Project;

import Lib.Coordenada;
import Lib.Cultivo;

public class  MetodoAuxiliarCampo {

    public static void sacarCultivo(Cultivo[][] campo, Coordenada coordenadaSuperiorIzq, Coordenada coordenadaInferiorDer){

        for(int i = coordenadaSuperiorIzq.getX(); i<= coordenadaInferiorDer.getX();i++){
            for(int j = coordenadaSuperiorIzq.getY(); j<= coordenadaInferiorDer.getY();j++){
                campo[i][j]= null;
            }
        }
    }
    public static void agregarCultivo(Cultivo[][] campo, Coordenada coordenadaSuperiorIzq, Coordenada coordenadaInferiorDer,Cultivo cultivo){
        for(int i = coordenadaSuperiorIzq.getX(); i<= coordenadaInferiorDer.getX();i++){
            for(int j = coordenadaSuperiorIzq.getY(); j<= coordenadaInferiorDer.getY();j++){
                campo[i][j]= cultivo;
            }
        }

    }

    public static Double calcularBeneficio(Cultivo[][] campo, Double[][] riego ){
        Double beneficio= 0.0;
        for(int i=0;i<campo.length;i++){
            for(int j=0;j<campo.length;j++){


            }

        }
        return beneficio;

    }



}

