
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;
import java.awt.Font;
import java.awt.FlowLayout;

public class JframeCartas extends JFrame {

    private JButton btnRepartir;
    private JButton btnVerificar;
    private JButton btnVerificarEscalera;
    private JButton btnPuntaje;
    private JPanel pnlJugador1;
    private JPanel pnlJugador2;
    private JTabbedPane tpJugadores;
    Jugadores jugador1, jugador2;
    private Image ImagenFondo;
    private Image ImagenMesa;

    public JframeCartas() {
        btnRepartir = new JButton();
        btnVerificar = new JButton();
        btnVerificarEscalera = new JButton();
        btnPuntaje = new JButton();
        tpJugadores = new JTabbedPane();

        // Cargar la imagen de fondo para los paneles
        ImagenFondo = new ImageIcon("./imagenes/fondo.jpg").getImage();
        ImagenMesa = new ImageIcon("./imagenes/mesa.png").getImage();

        // Crear el panel personalizado para la imagen de fondo del jugador 1
        pnlJugador1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(ImagenMesa, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Crear el panel personalizado para la imagen de fondo del jugador 2
        pnlJugador2 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(ImagenMesa, 0, 0, getWidth(), getHeight(), this);
            }
        };

        setLocation(560, 350);
        setResizable(false);
        setSize(600, 300);
        setTitle("Juego de Cartas");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Ponemos Icono a la ventana para que se vea como profesionales
        ImageIcon icono = new ImageIcon("./imagenes/image.png");
        setIconImage(icono.getImage());

        // Fondo principal
        ImagenFondo = new ImageIcon("./imagenes/fondo.jpg").getImage();

        // Crear el panel personalizado para la imagen de fondo del JFrame
        JPanel panelConImagen = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Dibujar la imagen de fondo en el panel principal
                g.drawImage(ImagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Establecer el panel con imagen como el contentPane de la ventana
        setContentPane(panelConImagen);
        panelConImagen.setLayout(null);

        // Configurar los paneles de los jugadores con layouts nulos
        pnlJugador1.setLayout(null);
        pnlJugador2.setLayout(null);

        // Configurar el TabbedPane para los jugadores
        tpJugadores.setBounds(15, 70, 550, 170);
        tpJugadores.addTab("Martín Estrada Contreras", pnlJugador1);
        tpJugadores.addTab("Raul Vidal", pnlJugador2);

        // Cambiar la fuente de los nombres de las pestañas
        Font playerFont = new Font("Times New Roman", Font.ITALIC | Font.BOLD, 16); // Tipo de letra para nombres
                                                                                    // jugadores
        tpJugadores.setTabComponentAt(0, createTabComponent("Martín Estrada Contreras", playerFont));
        tpJugadores.setTabComponentAt(1, createTabComponent("Raul Vidal", playerFont));

        // Configuración de los botones
        btnRepartir.setBounds(50, 20, 100, 30);
        btnRepartir.setText("Repartir");
        btnRepartir.setFont(new Font("Times New Roman", Font.ITALIC | Font.BOLD, 16));
        btnRepartir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnRepartirClick(evt);
            }
        });

        btnVerificar.setBounds(175, 20, 100, 30);
        btnVerificar.setText("Verificar");
        btnVerificar.setFont(new Font("Times New Roman", Font.ITALIC | Font.BOLD, 16));
        btnVerificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnVerificarClick(evt);
            }
        });

        btnVerificarEscalera.setBounds(300, 20, 100, 30);
        btnVerificarEscalera.setText("Escaleras");
        btnVerificarEscalera.setFont(new Font("Times New Roman", Font.ITALIC | Font.BOLD, 16));
        btnVerificarEscalera.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt2) {
                btnVerificarEscaleraClick(evt2);
            }
        });

        btnPuntaje.setBounds(425, 20, 100, 30);
        btnPuntaje.setText("Puntaje");
        btnPuntaje.setFont(new Font("Times New Roman", Font.ITALIC | Font.BOLD, 16));
        btnPuntaje.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt3) {
                btnPuntajeClick(evt3);
            }
        });

        // Añadir componentes al JFrame
        getContentPane().add(tpJugadores);
        getContentPane().add(btnRepartir);
        getContentPane().add(btnVerificar);
        getContentPane().add(btnVerificarEscalera);
        getContentPane().add(btnPuntaje);

        jugador1 = new Jugadores();
        jugador2 = new Jugadores();
    }

    private JPanel createTabComponent(String title, Font font) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JLabel label = new JLabel(title);
        label.setFont(font);
        panel.add(label);
        return panel;
    }

    private void btnRepartirClick(ActionEvent evt) {
        jugador1.repartir();
        jugador2.repartir();
        jugador1.mostrarCartas(pnlJugador1);
        jugador2.mostrarCartas(pnlJugador2);
    }

    private void btnVerificarClick(ActionEvent evt) {
        switch (tpJugadores.getSelectedIndex()) {
            case 0:
                JOptionPane.showMessageDialog(null, jugador1.getGrupos());
                break;
            case 1:
                JOptionPane.showMessageDialog(null, jugador2.getGrupos());
                break;
        }
    }

    private void btnVerificarEscaleraClick(ActionEvent evt2) {
        switch (tpJugadores.getSelectedIndex()) {
            case 0:
                JOptionPane.showMessageDialog(null, jugador1.getEscaleras());
                break;
            case 1:
                JOptionPane.showMessageDialog(null, jugador2.getEscaleras());
                break;
        }
    }

    private void btnPuntajeClick(ActionEvent evt3) {
        switch (tpJugadores.getSelectedIndex()) {
            case 0:
                JOptionPane.showMessageDialog(null, jugador1.Puntaje());
                break;
            case 1:
                JOptionPane.showMessageDialog(null, jugador2.Puntaje());
                break;
        }
    }
}
