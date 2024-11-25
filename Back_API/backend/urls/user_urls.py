from django.urls import path
from backend.views import user_views as views

urlpatterns = [
    path('login/', views.MyTokenObtainPairView.as_view(),
        name='token_obtain_pair'),
        
    path('register/', views.registerUser, name='register'),
    path('profile/', views.getUserProfile, name="users-profile"),
    path('profile/update/', views.updateUserProfile, name="users-profile-update"),
    path('all/', views.getUsers, name="users"),  # Obtener todos los usuarios
    path('update/<str:pk>/', views.updateUser, name="user-update"),  # Actualizar usuario
    path('delete/<str:pk>/', views.deleteUser, name="user-delete"),  # Eliminar usuario

]
