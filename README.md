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
- Android Studio 4.0 o superior.
- Python 3.8 o superior.
- Docker y Docker Compose.

### Backend:
1. Clona el repositorio:
   ```bash
   git clone https://github.com/Outeamultimedia/APP_FullStackPadel.git
   cd backend
   ```
2. Instala dependencias:
   ```bash
   pip install -r requirements.txt
   ```
3. Configura las variables de entorno:
   Crea un archivo `.env` con las siguientes variables:
   ```
   SECRET_KEY=tu_clave_secreta
   DEBUG=True
   DATABASE_URL=mysql://usuario:contraseña@host:puerto/nombre_base_datos
   ```
4. Inicia el servidor:
   ```bash
   python manage.py runserver
   ```

### Frontend:
1. Abre el proyecto en Android Studio.
2. Conéctalo a tu dispositivo/emulador.
3. Ejecuta la aplicación.

## Cómo Contribuir
1. Haz un fork del repositorio.
2. Crea una rama nueva (`git checkout -b feature/nueva-funcionalidad`).
3. Realiza tus cambios y realiza commits descriptivos.
4. Envia un pull request para revisión.

## Licencia

Este proyecto está licenciado bajo la licencia MIT. Consulta el archivo `LICENSE` para más detalles.
