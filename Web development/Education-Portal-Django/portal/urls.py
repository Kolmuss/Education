from django.urls import path
from django.conf.urls import url

from . import views

app_name = 'portal'

urlpatterns = [
    path('', views.home, name='home'),
    path('profile/', views.profile, name='profile'),
    path('profile/settings/', views.settings, name='settings'),

    path('courses/', views.CourseListView.as_view(), name='course_list'),
    path('courses/<slug:course_slug>/', views.CourseDetailView.as_view(), name='course_details'),
	path('courses/<slug:course_slug>/<int:step_pk>/', views.step_details, name='course_step'),
    path('courses/<slug:course_slug>/test/<int:test_pk>/', views.test, name='course_test'),
    path('tags/<slug:tag_name>/', views.tags, name='tag'),
    # path('results/', views.results, name='results'),
	# path('<int:pk>/courses/<int:course_id>/final', views.course_final, name='course_final'),
    path('courses/<slug:course_slug>/subscribing/', views.subscribe, name='subscribe'),
    # path('<int:pk>/courses/like/<int:course_id>/', views.like, name='like'),
    # path('<int:pk>/cases/', views.all_cases, name='all_cases'),
    # path('<int:pk>/cases/<int:case_id>/', views.case_page, name='case_page'),
    path('vacancy/',views.search_vacancy,name='vacancy'),
]
