from django.contrib import admin
from django.contrib.auth.admin import UserAdmin
from backend.models import User, Reservation, Installation, Duda, Documento

@admin.register(User)
class CustomUserAdmin(UserAdmin):
    # Añadimos los campos personalizados al panel
    fieldsets = UserAdmin.fieldsets + (
        (None, {'fields': ('direccion', 'telefono')}),
    )
    list_display = ('username', 'email', 'direccion', 'telefono', 'is_staff', 'is_superuser')
    search_fields = ('username', 'email', 'direccion', 'telefono')

@admin.register(Reservation)
class ReservationAdmin(admin.ModelAdmin):
    list_display = ('user', 'date', 'installation_id', 'is_synced', 'created_at')  # Campos visibles en la lista
    list_filter = ('is_synced', 'date')  # Filtros laterales para búsqueda rápida
    search_fields = ('user__username', 'date', 'installation_id')  # Campos para la barra de búsqueda

@admin.register(Installation)
class InstallationAdmin(admin.ModelAdmin):
    list_display = ('id', 'name', 'capacity', 'roofed', 'area')

@admin.register(Documento)
class DocumentoAdmin(admin.ModelAdmin):
    list_display = ('id', 'descripcion', 'link')  # Campos visibles en la lista
    search_fields = ('descripcion', 'link')  # Campos para la barra de búsqueda

@admin.register(Duda)
class DudaAdmin(admin.ModelAdmin):
    list_display = ('id', 'user', 'txt_duda', 'txt_respuesta')  # Muestra el usuario relacionado
    search_fields = ('user__username', 'txt_duda', 'txt_respuesta')  # Permite buscar por usuario y texto
    list_filter = ('user',)  # Filtro por usuario