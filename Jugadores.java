import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class Jugadores {
    private final int Total_Cartas = 10;
    private final int margen = 10;
    private final int distancia = 50;
    private Random r = new Random();
    private Carta[] cartas = new Carta[Total_Cartas];

    public void repartir() {
        int i = 0;
        for (Carta c : cartas) {
            cartas[i++] = new Carta(r);
        }
    }

    public void mostrarCartas(JPanel pnl) {
        pnl.removeAll();
        int p = 1;
        for (Carta c : cartas) {
            c.mostrar(pnl, margen + p++ * distancia, margen);
        }

        pnl.repaint();

    }

    public String getGrupos() {
        String mensaje = "No hay grupos";
        int[] contadores = new int[NombreCarta.values().length];
        for (Carta c : cartas) {
            // ordinal nos dice la posicion del elemento en el enum//
            contadores[c.getNombre().ordinal()]++;
        }
        boolean HayGrupos = false;
        for (int i = 0; i < contadores.length; i++) {
            if (contadores[i] >= 2) {
                if (!HayGrupos) {
                    HayGrupos = true;
                    mensaje = "Se encontraron los siguiente grupos\n";
                }
                mensaje += Grupo.values()[contadores[i]] + " de " + NombreCarta.values()[i] + "\n";
            }
        }
        return mensaje;
    }

    public String getEscaleras() {
        String mensaje = "";
        int residuo;
        String[] nombreDeCarta = new String[cartas.length];
        int i = 0;
        int numeroDeCarta[][] = new int[cartas.length][2];
        boolean hayEscalera = false;
        // obtenemos el valor y la pinta de la carta//
        for (Carta c : cartas) {
            residuo = c.getIndice() % 13;
            if (residuo == 0) {
                residuo = 13 - 1;
            }
            numeroDeCarta[i][0] = residuo - 1;
            numeroDeCarta[i][1] = c.getPinta().ordinal();
            ;
            nombreDeCarta[i] = c.getNombre().toString();
            i = i + 1;
        }
        // ordenamos el array//
        for (i = 0; i < numeroDeCarta.length; i++) {
            for (int j = 0; j < numeroDeCarta.length - 1; j++) {
                if (numeroDeCarta[j][1] > numeroDeCarta[j + 1][1] || (numeroDeCarta[j][1] == numeroDeCarta[j + 1][1]
                        && numeroDeCarta[j][0] > numeroDeCarta[j + 1][0])) {
                    int auxiliarNumero = numeroDeCarta[j + 1][0];
                    int auxiliarPinta = numeroDeCarta[j + 1][1];
                    String auxiliarString = nombreDeCarta[j + 1];
                    numeroDeCarta[j + 1][0] = numeroDeCarta[j][0];
                    numeroDeCarta[j][0] = auxiliarNumero;
                    numeroDeCarta[j + 1][1] = numeroDeCarta[j][1];
                    numeroDeCarta[j][1] = auxiliarPinta;
                    nombreDeCarta[j + 1] = nombreDeCarta[j];
                    nombreDeCarta[j] = auxiliarString;
                }
            }
        }

        // verificamos que haya escaleras//

        for (i = 0; i < numeroDeCarta.length; i++) {
            int longitudEscalera = 1;
            String primeraCarta = nombreDeCarta[i];
            String ultimaCarta = nombreDeCarta[i];
            for (int j = i; j < numeroDeCarta.length - 1; j++) {
                if (numeroDeCarta[j][0] + 1 == numeroDeCarta[j + 1][0]
                        && numeroDeCarta[j][1] == numeroDeCarta[j + 1][1]) {
                    longitudEscalera++;
                    ultimaCarta = nombreDeCarta[j + 1];

                } else {
                    break;
                }
            }
            if (longitudEscalera > 1) {
                hayEscalera = true;
                mensaje += "un/a " + Grupo.values()[longitudEscalera] + " de " + Pinta.values()[numeroDeCarta[i][1]]
                        + " que empieza " + primeraCarta + " y termina en " + ultimaCarta + "\n";
                i += longitudEscalera - 1;

            }
        }
        if (!hayEscalera) {
            mensaje = "no hay escaleras";
        }

        return mensaje;
    }

    public int Puntaje() {
        // antes tomamos el enfoque de sacar el puntaje total y luego restarle los
        // puntajes de las parejas y escaleras//
        // pero nos dimos cuenta de el enfoque que ver cuales cartas no formaban parte
        // de una pareja o escalera era mas facil//
        int puntaje = 0;
        int residuo;
        int i = 0;
        int[] contadores1 = new int[NombreCarta.values().length];
        int[][] valoresCartas = new int[Total_Cartas][2];
        ArrayList<Carta> cartasParejas = new ArrayList<>();
        ArrayList<Carta> cartasEscalera = new ArrayList<>();
        ArrayList<Carta> cartasIndividuales = new ArrayList<>();

        for (Carta c : cartas) {
            residuo = c.getIndice() % 13;
            if (residuo == 0) {
                residuo = 13;
            }
            valoresCartas[i][0] = residuo;
            valoresCartas[i][1] = c.getPinta().ordinal();
            i += 1;
        }
        // sacamos las cartas que estan en las parejas//
        for (Carta c : cartas) {
            contadores1[c.getNombre().ordinal()]++;
            if (contadores1[c.getNombre().ordinal()] >= 2) {
                for (Carta carta : cartas) {
                    if (carta.getNombre().ordinal() == c.getNombre().ordinal() && !cartasParejas.contains(carta)) {
                        cartasParejas.add(carta);
                    }
                }
            }
        }
        for (Carta c : cartasParejas) {
            System.out.println(c.getNombre() + " " + c.getPinta());
        }
        // vemos que cartas estan en escalera//
        for (i = 0; i < valoresCartas.length; i++) {
            for (int j = 0; j < valoresCartas.length - 1; j++) {
                if (valoresCartas[j][1] > valoresCartas[j + 1][1] || (valoresCartas[j][1] == valoresCartas[j + 1][1]
                        && valoresCartas[j][0] > valoresCartas[j + 1][0])) {
                    int numeroTemporal = valoresCartas[j + 1][0];
                    int pintaTemporal = valoresCartas[j + 1][1];
                    valoresCartas[j + 1][0] = valoresCartas[j][0];
                    valoresCartas[j][0] = numeroTemporal;
                    valoresCartas[j + 1][1] = valoresCartas[j][1];
                    valoresCartas[j][1] = pintaTemporal;

                }
            }
        }
        for (i = 0; i < valoresCartas.length; i++) {
            int longitudEscalera = 1;
            ArrayList<Carta> escaleraTemporal = new ArrayList<>();
            for (Carta carta : cartas) {
                if (carta.getIndice() % 13 == valoresCartas[i][0] && carta.getPinta().ordinal() == valoresCartas[i][1]
                        && !cartasEscalera.contains(carta)) {
                    escaleraTemporal.add(carta);
                    break;
                }
            }
            for (int j = i; j < valoresCartas.length - 1; j++) {
                if (valoresCartas[j][0] + 1 == valoresCartas[j + 1][0]
                        && valoresCartas[j][1] == valoresCartas[j + 1][1]) {
                    longitudEscalera++;
                    for (Carta carta : cartas) {
                        if (carta.getIndice() % 13 == valoresCartas[j][0] + 1
                                && carta.getPinta().ordinal() == valoresCartas[j][1]
                                && !cartasEscalera.contains(carta)) {
                            escaleraTemporal.add(carta);
                        }
                    }
                } else {
                    break;
                }
            }
            if (longitudEscalera > 1) {
                cartasEscalera.addAll(escaleraTemporal);
                i += longitudEscalera - 1;
            }
        }
        for (Carta c : cartasEscalera) {
            System.out.println(c.getNombre() + " " + c.getPinta());
        }
        for (Carta carta : cartas) {
            if (!cartasEscalera.contains(carta) && !cartasParejas.contains(carta)) {
                cartasIndividuales.add(carta);
            }
        }
        System.out.println("Cartas individuales");
        for (Carta c : cartasIndividuales) {
            System.out.println(c.getNombre() + " " + c.getPinta());
        }

        int valorCartasIndividuales = 0;
        for (Carta c : cartasIndividuales) {
            int valorCarta = c.getIndice() % 13;
            if (valorCarta == 0) {
                valorCarta = 13;
            }
            if (valorCarta == 1 || valorCarta >= 11) {
                valorCartasIndividuales += 10;
            } else {
                valorCartasIndividuales += valorCarta;
            }
        }

        puntaje = valorCartasIndividuales;
        return puntaje;
    }
}
