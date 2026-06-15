package vista;

import dao.LanzamientoDAO;
import modelo.Lanzamiento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Random;

public class VentanaPrincipal extends JFrame {

    private JTextArea txtResultado;

    private JTable tabla;

    private DefaultTableModel modelo;

    public VentanaPrincipal() {

        setTitle(
                "Probabilidad 6 Piezas Cuadradas");

        setSize(900,600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        iniciar();
    }

    private void iniciar() {

        JPanel superior = new JPanel();

        JButton btnLanzar =
                new JButton("Lanzar");

        superior.add(btnLanzar);

        add(superior,
                BorderLayout.NORTH);

        txtResultado =
                new JTextArea();

        add(
                new JScrollPane(txtResultado),
                BorderLayout.CENTER);

        modelo =
                new DefaultTableModel();

        modelo.addColumn("P1");
        modelo.addColumn("P2");
        modelo.addColumn("P3");
        modelo.addColumn("P4");
        modelo.addColumn("P5");
        modelo.addColumn("P6");
        modelo.addColumn("A");
        modelo.addColumn("B");
        modelo.addColumn("Prob.");

        tabla =
                new JTable(modelo);

        add(
                new JScrollPane(tabla),
                BorderLayout.SOUTH);

        btnLanzar.addActionListener(
                e -> lanzar()
        );
    }

    private void lanzar() {

        Random r = new Random();

        Lanzamiento l =
                new Lanzamiento();

        int[] piezas =
                new int[6];

        int carasA = 0;

        for(int i=0;i<6;i++){

            piezas[i] =
                    r.nextInt(2);

            if(piezas[i]==1)
                carasA++;
        }

        int carasB =
                6 - carasA;

        double prob =
                (double) carasA / 6;

        l.setPieza1(piezas[0]);
        l.setPieza2(piezas[1]);
        l.setPieza3(piezas[2]);
        l.setPieza4(piezas[3]);
        l.setPieza5(piezas[4]);
        l.setPieza6(piezas[5]);

        l.setCarasA(carasA);
        l.setCarasB(carasB);

        l.setProbabilidad(prob);

        new LanzamientoDAO()
                .guardar(l);

        txtResultado.append(
                "Resultado: "
                        + piezas[0] + " "
                        + piezas[1] + " "
                        + piezas[2] + " "
                        + piezas[3] + " "
                        + piezas[4] + " "
                        + piezas[5]
                        + " -> A="
                        + carasA
                        + " B="
                        + carasB
                        + " Prob="
                        + prob
                        + "\n"
        );

        modelo.addRow(
                new Object[]{
                        piezas[0],
                        piezas[1],
                        piezas[2],
                        piezas[3],
                        piezas[4],
                        piezas[5],
                        carasA,
                        carasB,
                        prob
                }
        );
    }
}