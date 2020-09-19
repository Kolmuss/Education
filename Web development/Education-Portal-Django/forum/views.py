from django.db.models import Count, Q
from django.contrib import messages
from django.core.paginator import Paginator, EmptyPage, PageNotAnInteger
from django.shortcuts import render, get_object_or_404, redirect, reverse
from django.views.generic import View, ListView, DetailView, CreateView, UpdateView, DeleteView

from .models import *

def search(request):
    category_count=get_category_count()
    most_recent= Post.objects.order_by('-timestamp')[:3]
    post_list = Post.objects.all()
    paginator  = Paginator(post_list,4)
    page_request_var='page'
    page = request.GET.get(page_request_var)
    try:
        paginated_queryset=paginator.page(page)
    except PageNotAnInteger:
        paginated_queryset=paginator.page(1)
    except EmptyPage:
        paginated_queryset=paginator.page(paginator.num_pages)
    queryset=Post.objects.all()
    query  = request.GET.get('q')
    if query:
        queryset=queryset.filter(
            Q(title__icontains=query)|
            Q(overview__icontains=query)
        ).distinct()
    
    context={
        'queryset':queryset,
        'queryset':paginated_queryset,
        'most_recent':most_recent,
        'page_request_var':page_request_var,
        'category_count':category_count
    }
    return render(request,'search_results.html',context)


def get_category_count():
    queryset = Post \
        .objects \
        .values('categories__title') \
        .annotate(Count('categories__title'))
    return queryset

def forum(request):
    category_count=get_category_count()
    most_recent= Post.objects.order_by('-timestamp')[:3]
    post_list = Post.objects.all()
    paginator  = Paginator(post_list,4)
    page_request_var='page'
    page = request.GET.get(page_request_var)
    try:
        paginated_queryset=paginator.page(page)
    except PageNotAnInteger:
        paginated_queryset=paginator.page(1)
    except EmptyPage:
        paginated_queryset=paginator.page(paginator.num_pages)
    
    context={
        'queryset':paginated_queryset,
        'most_recent':most_recent,
        'page_request_var':page_request_var,
        'category_count':category_count
    }
    return render(request,'blog.html',context)

def post(request,id):
    category_count=get_category_count()
    most_recent= Post.objects.order_by('-timestamp')[:3]
    post_list = Post.objects.all()
    paginator  = Paginator(post_list,4)
    page_request_var='page'
    page = request.GET.get(page_request_var)

    if request.method == "POST":
        user = request.user
        txt = request.POST.get("comments_text")
        if txt != "":
            try:
                com = Post_comment(step=step, user=user, text=txt)
                com.save();
                step.step_comment_set.add(com)
            except Exception as e:
                pass

    context={
        'queryset':paginated_queryset,
        'most_recent':most_recent,
        'page_request_var':page_request_var,
        'category_count':category_count
    }
    return render(request,'post.html',context)