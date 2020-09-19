from django.shortcuts import render, redirect
from django.contrib import messages
from django.contrib.auth.models import User

from .forms import CreateUser

def registration(request):
    form = CreateUser()
    if request.method == "POST":
        form = CreateUser(request.POST)
        if form.is_valid():
            form.save()
            return redirect('landing:login')
    context = {
    "title": "Registration",
    "form": form,
    }
    return render(request, "landing/registration.html", context)


def home(request):
    return render(request, 'landing/home.html', {"title": "Homepage"})

def events(request):
    path = "admissions"
    return render(request, 'landing/home.html', {"title": "Events"})

def courses(request):
    path = "courses"
    return render(request, 'landing/home.html', {"title": "Courses"})

def about(request):
    path = "about"
    return render(request, 'landing/about.html', {"title": "About us"})
