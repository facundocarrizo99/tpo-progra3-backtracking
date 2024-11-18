package Project;

import Lib.Coordenada;

public class CoordenadaCultivo {
    private Coordenada esquinaSuperiorIzquierda;
    private Coordenada esquinaInferiorDerecha;


    public CoordenadaCultivo(Coordenada esquinaSuperiorIzquierda, Coordenada esquinaInferiorDerecha) {
        this.esquinaSuperiorIzquierda = esquinaSuperiorIzquierda;
        this.esquinaInferiorDerecha = esquinaInferiorDerecha;
    }


    public Coordenada getEsquinaSuperiorIzquierda() {
        return esquinaSuperiorIzquierda;
    }

    public Coordenada getEsquinaInferiorDerecha() {
        return esquinaInferiorDerecha;
    }
}
