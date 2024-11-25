Sí, puedo generar el código en un formato como **PlantUML** o **Mermaid**, que puedes pegar en herramientas en línea como [PlantUML](https://plantuml.com/) o [Mermaid Live Editor](https://mermaid-js.github.io/mermaid-live-editor/) para visualizar los diagramas UML. A continuación, te proporciono el código:

### **1. Diagrama de Clases (PlantUML)**

```plaintext
@startuml
class User {
    - direccion: String
    - telefono: String
    + __str__(): String
}

class Installation {
    - name: String
    - capacity: Integer
    - roofed: Boolean
    - area: Integer
    + __str__(): String
}

class Reservation {
    - date: Date
    - is_synced: Boolean
    - created_at: DateTime
    - updated_at: DateTime
    + __str__(): String
}

class Documento {
    - descripcion: String
    - link: URL
    + __str__(): String
}

class Duda {
    - txt_duda: Text
    - txt_respuesta: Text
    + __str__(): String
}

User "1" --> "*" Reservation : hace
Reservation "*" --> "1" Installation : usa
User "1" --> "*" Duda : plantea
Installation "1" --> "*" Reservation : reserva
Documento <-- User : gestiona (solo admin)
@enduml
```

### **2. Diagrama de Casos de Uso (PlantUML)**

```plaintext
@startuml
actor Usuario
actor Administrador

usecase "Registrar usuario" as UC1
usecase "Iniciar sesión" as UC2
usecase "Consultar perfil" as UC3
usecase "Hacer reservas" as UC4
usecase "Gestionar dudas" as UC5
usecase "Gestionar usuarios" as UC6
usecase "Gestionar documentos" as UC7
usecase "Gestionar instalaciones" as UC8

Usuario --> UC1
Usuario --> UC2
Usuario --> UC3
Usuario --> UC4
Usuario --> UC5
Administrador --> UC6
Administrador --> UC7
Administrador --> UC8
@enduml
```

### Cómo Usarlo
1. Copia cualquiera de los bloques anteriores.
2. Ve a [PlantUML Online Editor](https://plantuml.com/plantuml) o cualquier otra herramienta compatible.
3. Pega el código y genera el diagrama.

Si prefieres otro formato como **Mermaid**, avísame y lo convierto. ¡Espero que te sea útil!