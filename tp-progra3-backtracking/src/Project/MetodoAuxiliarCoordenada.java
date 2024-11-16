package Project;

import Lib.Coordenada;
import Lib.Cultivo;

import java.util.ArrayList;
import java.util.List;

public class MetodoAuxiliarCoordenada {
    private static Boolean validarCoordenadas(Cultivo[][] campo, CoordenadaCultivo coordenadaCultivo, Cultivo cultivo) {
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

    public static List<CoordenadaCultivo> obtenerCoordenadasValidas(Cultivo[][] campo, Cultivo cultivo){
        List<CoordenadaCultivo> posicionesValidas = new ArrayList<>();
        int filas = campo.length;
        int columnas = campo[0].length;

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Coordenada esquinaSuperiorIzquierda = new Coordenada(i,j);

                for(int limite = 2; limite <= 11; limite++){
                        for (int ancho = 1; ancho < limite; ancho++) {
                            int altura = limite - ancho;
                            int x2 = i + (altura - 1);
                            int y2 = j + (ancho - 1);
                            Coordenada esquinaInferiorDerecha = new Coordenada(x2, y2);
                            CoordenadaCultivo coordenadaCultivo = new CoordenadaCultivo(esquinaSuperiorIzquierda,esquinaInferiorDerecha);
                            if (validarCoordenadas(campo, coordenadaCultivo, cultivo)){
                                posicionesValidas.add(coordenadaCultivo);
                            }
                        }
                }
            }
        }
        return posicionesValidas;
    }
}

