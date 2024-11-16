package Project;

import Lib.Coordenada;
import Lib.Cultivo;

import java.util.List;

public class MetodoAuxiliarCoordenada {
    public static Boolean validarCoordenadas(Cultivo[][] campo, CoordenadaCultivo x, CoordenadaCultivo y) {
        Coordenada esquinaSuperior = x.getEsquinaSuperiorIzquierda();
        Coordenada esquinaInferior = y.getEsquinaInferiorDerecha();

        if (esquinaSuperior.getX() < 0 || esquinaSuperior.getY() < 0) {
            return false;
        }
        if (esquinaInferior.getX() >= campo.length || esquinaInferior.getY() >= campo[0].length) {
            return false;
        }

        for(int i = esquinaSuperior.getX(); i<= esquinaInferior.getX();i++){
            for(int j = esquinaSuperior.getY(); j<= esquinaInferior.getY();j++){
                if(campo[i][j]!=null){
                    return false;
                }
            }
        }
        for(int i = esquinaSuperior.getX();i<=esquinaInferior.getX();i++){
            if(i-1<0 || i+1> campo.length ){

            }
            else{

            }
        }

        return true;
    }

    ///// Obtener coordenadas validas 
    public static List<CoordenadaCultivo> obtenerCoordenadasValidas(Cultivo[][] campo, Cultivo){

    }
}

