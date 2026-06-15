# -_sistema_de_probabilidad_6_piezas_cuadradas_oracle_19c_- :.
# Sistema de Probabilidad de 6 Piezas Cuadradas con Oracle 19c:

<img width="1024" height="1024" alt="image" src="https://github.com/user-attachments/assets/b2598824-d6c4-4797-864a-44d06c306aa2" />  

```

Proyecto completo en **Java 21 + IntelliJ IDEA + Swing + Oracle Database 19c** que simula el lanzamiento de **6 piezas cuadradas** (dos caras posibles por pieza), calcula las probabilidades obtenidas y almacena cada simulación en Oracle 19c .

---

# Descripción

Cada pieza puede caer en:

- **Cara A = 1**
- **Cara B = 0**

Al lanzar 6 piezas se generan:

**2⁶ = 64 combinaciones posibles**

El sistema permite:

- Simular el lanzamiento de 6 piezas.
- Contar cuántas caras A y B aparecen.
- Calcular la probabilidad observada.
- Mostrar el resultado en una interfaz gráfica.
- Guardar los resultados en Oracle 19c.
- Consultar el historial de simulaciones.

---

# Estructura del Proyecto

```text
ProbabilidadPiezasOracle/

├── src/
│   ├── conexion/
│   │   └── ConexionOracle.java
│   │
│   ├── modelo/
│   │   └── Lanzamiento.java
│   │
│   ├── dao/
│   │   └── LanzamientoDAO.java
│   │
│   ├── vista/
│   │   └── VentanaPrincipal.java
│   │
│   └── Main.java
│
└── lib/
    └── ojdbc11.jar
```

---

# Script Oracle 19c

```sql
CREATE TABLE LANZAMIENTO_PIEZAS (

    ID NUMBER GENERATED ALWAYS AS IDENTITY,

    PIEZA1 NUMBER(1),
    PIEZA2 NUMBER(1),
    PIEZA3 NUMBER(1),
    PIEZA4 NUMBER(1),
    PIEZA5 NUMBER(1),
    PIEZA6 NUMBER(1),

    CARAS_A NUMBER(2),
    CARAS_B NUMBER(2),

    PROBABILIDAD NUMBER(8,4),

    FECHA_REGISTRO DATE DEFAULT SYSDATE,

    CONSTRAINT PK_LANZAMIENTO
    PRIMARY KEY(ID)
);
```

---

# Clase de Conexión Oracle

## ConexionOracle.java

```java
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionOracle {

    private static final String URL =
            "jdbc:oracle:thin:@localhost:1521:XE";

    private static final String USER =
            "SYSTEM";

    private static final String PASSWORD =
            "oracle";

    public static Connection conectar() {

        try {

            Class.forName(
                    "oracle.jdbc.driver.OracleDriver");

            return DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }
}
```

---

# Modelo

## Lanzamiento.java

```java
package modelo;

public class Lanzamiento {

    private int pieza1;
    private int pieza2;
    private int pieza3;
    private int pieza4;
    private int pieza5;
    private int pieza6;

    private int carasA;
    private int carasB;

    private double probabilidad;

    public int getPieza1() {
        return pieza1;
    }

    public void setPieza1(int pieza1) {
        this.pieza1 = pieza1;
    }

    public int getPieza2() {
        return pieza2;
    }

    public void setPieza2(int pieza2) {
        this.pieza2 = pieza2;
    }

    public int getPieza3() {
        return pieza3;
    }

    public void setPieza3(int pieza3) {
        this.pieza3 = pieza3;
    }

    public int getPieza4() {
        return pieza4;
    }

    public void setPieza4(int pieza4) {
        this.pieza4 = pieza4;
    }

    public int getPieza5() {
        return pieza5;
    }

    public void setPieza5(int pieza5) {
        this.pieza5 = pieza5;
    }

    public int getPieza6() {
        return pieza6;
    }

    public void setPieza6(int pieza6) {
        this.pieza6 = pieza6;
    }

    public int getCarasA() {
        return carasA;
    }

    public void setCarasA(int carasA) {
        this.carasA = carasA;
    }

    public int getCarasB() {
        return carasB;
    }

    public void setCarasB(int carasB) {
        this.carasB = carasB;
    }

    public double getProbabilidad() {
        return probabilidad;
    }

    public void setProbabilidad(double probabilidad) {
        this.probabilidad = probabilidad;
    }
}
```

---

# DAO

## LanzamientoDAO.java

```java
package dao;

import conexion.ConexionOracle;
import modelo.Lanzamiento;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class LanzamientoDAO {

    public void guardar(Lanzamiento l) {

        String sql = """
                INSERT INTO LANZAMIENTO_PIEZAS
                (
                PIEZA1,
                PIEZA2,
                PIEZA3,
                PIEZA4,
                PIEZA5,
                PIEZA6,
                CARAS_A,
                CARAS_B,
                PROBABILIDAD
                )
                VALUES
                (?,?,?,?,?,?,?,?,?)
                """;

        try(
                Connection cn =
                        ConexionOracle.conectar();

                PreparedStatement ps =
                        cn.prepareStatement(sql)
        ) {

            ps.setInt(1,l.getPieza1());
            ps.setInt(2,l.getPieza2());
            ps.setInt(3,l.getPieza3());
            ps.setInt(4,l.getPieza4());
            ps.setInt(5,l.getPieza5());
            ps.setInt(6,l.getPieza6());

            ps.setInt(7,l.getCarasA());
            ps.setInt(8,l.getCarasB());

            ps.setDouble(9,l.getProbabilidad());

            ps.executeUpdate();

        } catch(Exception e){

            e.printStackTrace();
        }
    }
}
```

---

# Interfaz Gráfica

## VentanaPrincipal.java

```java
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
```

---

# Clase Principal

## Main.java

```java
import vista.VentanaPrincipal;

public class Main {

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(
                () -> {

                    new VentanaPrincipal()
                            .setVisible(true);

                }
        );
    }
}
```

---

# Dependencia Oracle JDBC

Agregar a IntelliJ IDEA el archivo:

```text
ojdbc11.jar
```

Compatible con:

- Java 21
- Oracle Database 19c

---

# Ejemplos de Simulación

## Ejemplo 1

### Piezas

```text
1 0 1 1 0 1
```

### Resultado

```text
Caras A = 4
Caras B = 2
```

### Probabilidad observada

```text
4 / 6 = 0.6667
```

---

## Ejemplo 2

### Piezas

```text
0 0 0 1 0 0
```

### Resultado

```text
Caras A = 1
Caras B = 5
```

### Probabilidad observada

```text
1 / 6 = 0.1667
```

---

# Mejoras Posibles

- Botón **Consultar Historial**.
- Botón **Eliminar Historial**.
- Gráfica estadística con JFreeChart.
- Simulación masiva de:
  - 1.000 lanzamientos
  - 10.000 lanzamientos
  - 100.000 lanzamientos
- Cálculo de probabilidad teórica usando distribución binomial.
- Exportación de resultados a Excel.
- Exportación de resultados a PDF.
- Implementación completa del patrón MVC.
- Capa de servicios y repositorios.
- Reportes estadísticos avanzados.

---

# Tecnologías Utilizadas

| Tecnología | Versión |
|------------|----------|
| Java | 21 |
| IntelliJ IDEA | Última |
| Swing | Incluido en Java |
| Oracle Database | 19c |
| JDBC | ojdbc11.jar |

---

# Autor

Proyecto académico y demostrativo para simulación de probabilidad mediante lanzamiento de piezas cuadradas utilizando Java Swing y Oracle Database 19c .

:. . / .
