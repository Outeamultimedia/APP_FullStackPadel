from django.contrib.auth.models import AbstractUser
from django.db import models
from django.conf import settings

class User(AbstractUser):
    direccion = models.CharField(max_length=255, blank=True, null=True)
    telefono = models.CharField(max_length=15, blank=True, null=True)

    def __str__(self):
        return self.username  # Usamos `username` que será el NIP

class Installation(models.Model):
    name = models.CharField(max_length=255)  # Nombre de la instalación
    capacity = models.PositiveIntegerField()  # Capacidad de personas
    roofed = models.BooleanField(default=False)  # Si está techado o no
    area = models.PositiveIntegerField()  # Área en metros cuadrados

    def __str__(self):
        return self.name
      
class Reservation(models.Model):
    user = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.CASCADE, related_name="reservations")
    date = models.DateField()  # Fecha de la reserva
    installation = models.ForeignKey(Installation, on_delete=models.CASCADE, related_name="reservations")  # Relación con instalaciones
    is_synced = models.BooleanField(default=True)  # Campo opcional para sincronización

    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    class Meta:
        unique_together = ('date', 'installation')  # Evita duplicados para la misma instalación en la misma fecha

    def __str__(self):
        return f"Reservation by {self.user.username} on {self.date} for {self.installation.name}"

# Modelo para documentos
class Documento(models.Model):
    descripcion = models.TextField()  # Campo para la descripción del documento
    link = models.URLField()  # Enlace del documento

    def __str__(self):
        return self.descripcion[:50]  # Retorna los primeros 50 caracteres como representación del objeto


# Modelo para dudas
class Duda(models.Model):
    user = models.ForeignKey(
        settings.AUTH_USER_MODEL,
        on_delete=models.CASCADE,
        related_name='dudas'
    )  # Relación con el usuario que creó la duda
    txt_duda = models.TextField()  # Campo para almacenar la duda
    txt_respuesta = models.TextField(blank=True, null=True)  # Campo para almacenar la respuesta, puede ser nulo

    def __str__(self):
        return f"{self.user.username}: {self.txt_duda[:50]}" 