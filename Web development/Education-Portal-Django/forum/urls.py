from django.urls import path
from django.conf.urls import url

from . import views

app_name = 'forum'

urlpatterns = [
    path('', views.forum,name='post-list'),
    path('search/', views.search, name='search'),
    path('post/<int:id>/', views.post,name='post-detail'),
]
