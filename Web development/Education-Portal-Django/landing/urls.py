from django.urls import path
from django.contrib.auth import views as auth_views
from . import views

app_name = 'landing'

urlpatterns = [
    path('', views.home, name='home'),
    path('registration/', views.registration, name='registration'),

    path('login/', auth_views.LoginView.as_view(template_name='landing/login.html'), name='login'),
    path('logout/', auth_views.LogoutView.as_view(template_name='landing/home.html'), name='logout'),

    path('events/', views.events, name='events'),
    path('courses/', views.courses, name='courses'),
    path('about/', views.about, name='about'),
]
