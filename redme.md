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
  Define el tipo de validación del campo de texto.  
  Tipos soportados:  
  - `"alfanumerico"`
  - `"numerico"`
  - `"texto"`
  - `"caracteresunicos"`
  - `"correo"`

- **`setIconoTipo(IconoTipo icono)`**  
  Configura el icono mostrado a la izquierda del campo de texto.  
  Tipos de iconos:  
  - `BUSCAR`
  - `USUARIO`
  - `CORREO`

- **`setDominioCorreo(String dominio)`**  
  Establece el dominio por defecto para autocompletar correos electrónicos.  
  Ejemplo: `"@gmail.com"`

---

## 📖 Instrucciones de uso

1. **Importa el `.jar` en tu proyecto NetBeans**:  
   - Clic derecho en tu proyecto → **Properties** → **Libraries** → **Add JAR/Folder**.  
   - Selecciona el archivo `Componente.jar` dentro de la carpeta `jar/`.

2. **Agrega el componente al JFrame**:  
   - Arrastra el componente desde la paleta de NetBeans al formulario.  
   - Configura las propiedades desde el panel de propiedades o por código.

---

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
