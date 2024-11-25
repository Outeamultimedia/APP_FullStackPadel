# APPadel

APPadel es una aplicación móvil diseñada para la gestión de reservas en un club de pádel. Esta herramienta proporciona funcionalidades para facilitar la interacción entre los socios y administradores del club, mejorando la experiencia del usuario y optimizando las operaciones del club.

## Funcionalidades

### Para usuarios (socios):
- Registro e inicio de sesión.
- Reserva de pistas en tiempo real.
- Gestión de perfil y visualización de historial de reservas.
- Envío de dudas a los administradores.
- Acceso a documentos del club (reglamentos, políticas, etc.).

### Para administradores:
- Gestión de socios y reservas.
- Respuesta a dudas de los usuarios.
- Administración de documentos disponibles para los socios.
- Generación de reportes sobre el uso de las instalaciones.

## Tecnologías

- **Frontend (Android):**
  - Desarrollado en Java utilizando Android Studio.
  - SQLite para almacenamiento local.
  - Diseño de interfaz basado en Material Design.
  - Patrón de diseño: MVP (Model-View-Presenter).
- **Backend:**
  - Framework Django con Django REST Framework.
  - Base de datos relacional: MySQL.
  - Autenticación mediante JWT.
  - Contenerización con Docker y despliegue en Nginx.
- **Infraestructura:**
  - Docker Compose para gestionar contenedores.
  - Certificados SSL con Certbot para comunicación segura.

## Instalación

### Requisitos:
- Python 3.8 o superior.
- Docker y Docker Compose (opcional).
- Android Studio 4.0 o superior.

---

### Opción 1: Arrancar la API con Docker Compose

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/Outeamultimedia/APP_FullStackPadel.git
   cd APP_FullStackPadel
   ```

2. **Configurar las variables de entorno**:
   Crea un archivo `.env` en el directorio `backend` con el siguiente contenido:
   ```env
   SECRET_KEY=tu_clave_secreta
   DEBUG=True
   DATABASE_URL=mysql://usuario:contraseña@host:puerto/nombre_base_datos
   ```

3. **Iniciar los servicios con Docker Compose**:
   ```bash
   docker-compose up
   ```

   Esto levantará la API de Django, la base de datos MySQL y Nginx (si está configurado en el archivo `docker-compose.yml`).

4. **Acceder a la API**:
   La API estará disponible en `http://localhost:8000`.

---

### Opción 2: Arrancar la API de forma manual

1. **Instalar dependencias del backend**:
   ```bash
   cd backend
   pip install -r requirements.txt
   ```

2. **Configurar las variables de entorno**:
   Crea un archivo `.env` en el directorio `backend` con el siguiente contenido:
   ```env
   SECRET_KEY=tu_clave_secreta
   DEBUG=True
   DATABASE_URL=mysql://usuario:contraseña@host:puerto/nombre_base_datos
   ```

3. **Aplicar migraciones**:
   ```bash
   python manage.py migrate
   ```

4. **Iniciar el servidor**:
   ```bash
   python manage.py runserver
   ```

   La API estará disponible en `http://localhost:8000`.

---

### Frontend (Android)

1. Abre el proyecto en Android Studio.
2. Conéctalo a un dispositivo/emulador.
3. Configura el archivo `base_url` del proyecto Android con la URL donde está corriendo la API.
4. Ejecuta la aplicación desde Android Studio.

---

## Cómo Contribuir

1. Haz un fork del repositorio.
2. Crea una rama nueva (`git checkout -b feature/nueva-funcionalidad`).
3. Realiza tus cambios y realiza commits descriptivos.
4. Envia un pull request para revisión.

## Licencia

Este proyecto está licenciado bajo la licencia MIT. Consulta el archivo `LICENSE` para más detalles.
