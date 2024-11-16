package Project;

import Lib.Coordenada;
import Lib.Cultivo;

public class MetodoAuxiliarCoordenada {
    public static Boolean validarCoordenadas(Cultivo[][] campo, CoordenadaCultivo coordenadaCultivo, Cultivo cultivo) {
        Coordenada esquinaSuperior = coordenadaCultivo.getEsquinaSuperiorIzquierda();
        Coordenada esquinaInferior = coordenadaCultivo.getEsquinaInferiorDerecha();

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
        int x1 = esquinaSuperior.getX();
        int y1 = esquinaSuperior.getY();
        int x2 = esquinaInferior.getX();
        int y2 = esquinaInferior.getY();

        for (int j = y1; j <= y2; j++) {
            if (x1 > 0 && campo[x1 - 1][j].getNombre().equals(cultivo.getNombre())) {
                return false;
            }
        }
        for (int j = y1; j <= y2; j++) {
            if (x2 < campo.length - 1 && campo[x2 + 1][j].getNombre().equals(cultivo.getNombre())) {
                return false;
            }
        }
        for (int i = x1; i <= x2; i++) {
            if (y1 > 0 && campo[i][y1 - 1].getNombre().equals(cultivo.getNombre())) {
                return false;
            }
        }
        for (int i = x1; i <= x2; i++) {
            if (y2 < campo[0].length - 1 && campo[i][y2 + 1].getNombre().equals(cultivo.getNombre())) {
                return false;
            }
        }
        return true;
    }
}

