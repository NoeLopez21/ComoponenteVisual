/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package visual;

import java.awt.Color;
import java.awt.Image;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.imageio.ImageIO;

public class Visual extends javax.swing.JPanel {

    private String tipoEntrada = "alfanumerico";
    private String dominioCorreo = "@gmail.com";
    private boolean etiquetaVisible = true;
    private IconoTipo iconoTipo = IconoTipo.NINGUNO;

    // ComboBox para dominios
    private javax.swing.JComboBox<String> comboDominios;

    // Lista de dominios disponibles
    private static final String[] DOMINIOS = {
        "@gmail.com", "@outlook.com", "@itoaxaca.edu.mx", "@email.com"
    };

    public Visual() {
        initComponents();
        inicializarComponentesPersonalizados();
        configurarEventos();
    }

    private void inicializarComponentesPersonalizados() {
        lblEtiqueta.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        lblEtiqueta.setPreferredSize(new java.awt.Dimension(200, 22));
        txtEntrada.setPreferredSize(new java.awt.Dimension(200, 30));
        lblIcono.setPreferredSize(new java.awt.Dimension(32, 32));
        lblIcono.setOpaque(false);

        // Inicializa el combo de dominios pero lo oculta al principio
        comboDominios = new javax.swing.JComboBox<>(DOMINIOS);
        comboDominios.setVisible(false);
        comboDominios.setPreferredSize(new java.awt.Dimension(150, 25));
        comboDominios.addActionListener(e -> actualizarDominioCorreo());

        actualizarIcono();
        lblIndicadorColor.setOpaque(true);
        lblIndicadorColor.setBackground(Color.GRAY);
        lblIndicadorColor.setPreferredSize(new java.awt.Dimension(0, 2));

        // Añade el combo al panel
        this.add(comboDominios);
    }

    private void configurarEventos() {
        txtEntrada.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { validarEntrada(); }
            @Override
            public void removeUpdate(DocumentEvent e) { validarEntrada(); }
            @Override
            public void changedUpdate(DocumentEvent e) { validarEntrada(); }
        });
    }

    private void validarEntrada() {
        String texto = txtEntrada.getText();
        boolean valido = true;

        switch (tipoEntrada.toLowerCase()) {
            case "numerico":
                valido = texto.matches("\\d+");
                break;
            case "texto":
                valido = texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+");
                break;
            case "alfanumerico":
                valido = texto.matches("[a-zA-Z0-9\\s]+");
                break;
            case "caracteresunicos":
                valido = texto.matches("[^a-zA-Z0-9\\s]+");
                break;
            case "correo":
                valido = texto.matches("[a-zA-Z0-9._%+-]+");
                break;
            default:
                valido = true;
        }

        txtEntrada.setBackground(valido ? Color.WHITE : new Color(255, 204, 204));
        actualizarColorIndicador(texto);
    }

    private void actualizarColorIndicador(String texto) {
        boolean tieneLetras = texto.matches(".*[a-zA-ZáéíóúÁÉÍÓÚñÑ].*");
        boolean tieneNumeros = texto.matches(".*\\d.*");
        boolean tieneCaracteres = texto.matches(".*[^a-zA-Z0-9\\s].*");

        Color color = Color.GRAY;

        if (tieneLetras && !tieneNumeros && !tieneCaracteres) {
            color = Color.BLUE;
        } else if (!tieneLetras && tieneNumeros && !tieneCaracteres) {
            color = Color.GREEN;
        } else if (!tieneLetras && !tieneNumeros && tieneCaracteres) {
            color = Color.RED;
        } else if (tieneLetras && tieneNumeros && !tieneCaracteres) {
            color = new Color(0, 255, 255);
        } else if (tieneLetras && !tieneNumeros && tieneCaracteres) {
            color = new Color(238, 130, 238);
        } else if (!tieneLetras && tieneNumeros && tieneCaracteres) {
            color = new Color(255, 255, 0);
        } else if (tieneLetras && tieneNumeros && tieneCaracteres) {
            color = Color.WHITE;
        }

        lblIndicadorColor.setBackground(color);
    }

private void actualizarIcono() {
    String ruta = null;
    switch (iconoTipo) {
        case BUSCAR:
            ruta = "/visual/buscador.png";
            break;
        case USUARIO:
            ruta = "/visual/usuario.png";
            break;
        case CORREO:
            ruta = "/visual/correo.png";
            break;
        default:
            ruta = null;
            break;
    }

    if (ruta != null) {
        try {
            java.net.URL imageUrl = Visual.class.getResource(ruta);
            if (imageUrl == null) {
                // Fallback para desarrollo
                imageUrl = new java.io.File("src/visual" + ruta).toURI().toURL();
            }

            if (imageUrl != null) {
                Image iconImage = ImageIO.read(imageUrl);
                // ✅ Escalado fijo para evitar error
                Image scaledImage = iconImage.getScaledInstance(
                    32, 32, Image.SCALE_SMOOTH
                );
                lblIcono.setIcon(new javax.swing.ImageIcon(scaledImage));
            } else {
                System.err.println("No se encontró el recurso: " + ruta);
                lblIcono.setIcon(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            lblIcono.setIcon(null);
        }
    } else {
        lblIcono.setIcon(null);
    }

    // Mostrar u ocultar el combo según el tipo
    if (iconoTipo == IconoTipo.CORREO) {
        comboDominios.setVisible(true);
        actualizarDominioCorreo();
    } else {
        comboDominios.setVisible(false);
    }
}


    private void actualizarDominioCorreo() {
        dominioCorreo = (String) comboDominios.getSelectedItem();
        String textoUsuario = txtEntrada.getText();
        if (textoUsuario.contains("@")) {
            textoUsuario = textoUsuario.substring(0, textoUsuario.indexOf("@"));
        }
        txtEntrada.setText(textoUsuario + dominioCorreo);
    }

    // === GETTERS & SETTERS ===

    public String getDominioCorreo() {
        return dominioCorreo;
    }

    public void setDominioCorreo(String dominioCorreo) {
        this.dominioCorreo = dominioCorreo;
        actualizarPlaceholderCorreo();
        if (iconoTipo == IconoTipo.CORREO) {
            txtEntrada.setText("usuario" + dominioCorreo);
        }
    }

    public IconoTipo getIconoTipo() {
        return iconoTipo;
    }

    public void setIconoTipo(IconoTipo iconoTipo) {
        this.iconoTipo = iconoTipo;
        actualizarIcono();
    }

    private void actualizarPlaceholderCorreo() {
        setPlaceholder("usuario" + dominioCorreo);
    }

    public void setPlaceholder(String texto) {
        txtEntrada.setText(texto);
        txtEntrada.setForeground(Color.GRAY);
    }

    // COMPONENTES
    private javax.swing.JLabel lblEtiqueta;
    private javax.swing.JTextField txtEntrada;
    private javax.swing.JLabel lblIndicadorColor;
    private javax.swing.JLabel lblIcono;

    public enum IconoTipo {
        NINGUNO, BUSCAR, USUARIO, CORREO
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        lblEtiqueta = new javax.swing.JLabel();
        txtEntrada = new javax.swing.JTextField();
        lblIndicadorColor = new javax.swing.JLabel();
        lblIcono = new javax.swing.JLabel();

        lblEtiqueta.setText("Etiqueta:");
        lblEtiqueta.setPreferredSize(new java.awt.Dimension(200, 22));

        lblIcono.setPreferredSize(new java.awt.Dimension(32, 32));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(36, 36, 36)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblEtiqueta, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lblIcono, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(lblIndicadorColor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(50, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(50, 50, 50)
                    .addComponent(lblEtiqueta, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(15, 15, 15)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblIcono, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(lblIndicadorColor, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(50, Short.MAX_VALUE))
        );
    }
}


