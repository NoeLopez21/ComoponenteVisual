readme.md
# 🖋️ Componente Visual

Este es un componente personalizado para Java Swing, diseñado para facilitar la entrada de datos con validaciones y un icono configurable.

---

## 📂 Estructura del proyecto

- **`jar/`**  
  Contiene el archivo `.jar` exportado del componente para su uso en otros proyectos.

- **`componente/`**  
  Proyecto completo de NetBeans con el código fuente del componente.

- **`UsoComponente/`**  
  Proyecto de ejemplo que muestra cómo implementar y utilizar el componente.

---

## 📸 Capturas de pantalla
*(Aquí puedes añadir capturas de cómo se ve el componente en NetBeans y en ejecución)*

---

## 🛠️ Métodos y propiedades relevantes

### 🖋️ Métodos principales
- **`setTipoEntrada(String tipo)`**  


---

## 📖 Instrucciones de uso


## ✅ Ejemplo de uso

```java
import campo_texto_icono.Visual;

public class MainFrame extends javax.swing.JFrame {
    public MainFrame() {
        initComponents();
        Visual campo = new Visual();
        campo.setTipoEntrada("correo");
        campo.setIconoTipo(Visual.IconoTipo.CORREO);
        campo.setDominioCorreo("@gmail.com");
        add(campo);
        campo.setBounds(50, 50, 300, 50);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}

## 👥 Créditos

- Lopez martinez noe javier  
- oliver gildardo enrriquez valencia
