from django.urls import reverse
from django.shortcuts import render, redirect, get_object_or_404
from django.contrib.auth.models import User
from django.contrib.auth.decorators import login_required
from django.views import generic

import requests
from bs4 import BeautifulSoup as bs

from .forms import *
from .models import *

def home(request):
    return render(request, 'portal/home.html', {'namepage':"Home"})

@login_required
def profile(request):
    level = request.user.profile.level
    exp = request.user.profile.current_exp
    if level == "NEWBIE":
        exp = (exp/1000)*100
    elif level == "MIDDLE":
        exp = (exp/10000)*100
    elif level == "HIGH":
        exp = (exp/100000)*100
    return render(request, 'portal/profile.html', { 'namepage':"Profile", "exp":exp })

@login_required
def settings(request):
    if request.method == "POST":
        u_form = UserUpdateForm(request.POST, instance=request.user)
        p_form = ProfileUpdateForm(request.POST,
                                   request.FILES,
                                   instance=request.user.profile)
        if u_form.is_valid() and p_form.is_valid():
            u_form.save()
            p_form.save()
            return redirect('portal:profile')
    else:
        u_form= UserUpdateForm(instance=request.user)
        p_form= ProfileUpdateForm(instance=request.user.profile)

    context = {
    'u_form':u_form,
    'p_form':p_form,
    'namepage':"Personal information"
    }
    return render(request, 'portal/settings.html', context)

class CourseListView(generic.ListView):
    template_name = 'portal/course_list.html'
    context_object_name = 'courses_list'

    def get_queryset(self):
        return Course.objects.all()

    def get_context_data(self, **kwargs):
        # Call the base implementation first to get a context
        user = self.request.user
        context = super().get_context_data(**kwargs)
        sub_courses = user.profile.courses.all()
        courses = []
        for course in Course.objects.all():
            not_subs = True
            for u in user.profile.courses.all():
                if u == course:
                    not_subs = False
            if not_subs:
                courses.append(course)
        context['sub_courses'] = user.profile.courses.all()
        context['courses'] = courses
        context['namepage'] = "Courses"
        context['tags'] = Tag.objects.all();
        return context

    def post(self, request):
        pass

class CourseDetailView(generic.View):
    model = Course
    template_name = 'portal/course_detail.html'
    context_object_name = 'course'

    def get(self, request, course_slug):
        course = get_object_or_404(Course, slug=course_slug)
        user = request.user
        return render(request, 'portal/course_detail.html', {'course': course, 'namepage':course.short_name})

    def post(self, request, course_slug):
        course = get_object_or_404(Course, slug=course_slug)
        user = request.user
        txt = request.POST.get("comments_text")
        if txt != "":
            try:
                com = Course_comment(course=course, user=user, text=txt)
                com.save();
                course.course_comment_set.add(com)

            except Exception as e:
                pass
        return render(request, 'portal/course_detail.html', {'course': course, 'namepage':course.short_name})


def tags(request, tag_name):
    tag = get_object_or_404(Tag, slug=tag_name)
    user = request.user
    tag.courses.all()

    sub_courses = []
    for c in user.profile.courses.all():
        if c in tag.courses.all():
            sub_courses.append(c)
    courses = []
    for course in tag.courses.all():
        not_subs = True
        for u in sub_courses:
            if u == course:
                not_subs = False
        if not_subs:
            courses.append(course)
    context = {}
    context['sub_courses'] = sub_courses
    context['courses'] = courses
    context['namepage'] = "Courses"
    context['tags'] = Tag.objects.all();
    return render(request, "portal/tag_course_list.html", context)


def step_details(request, course_slug, step_pk):
    course = get_object_or_404(Course, slug=course_slug)
    step = get_object_or_404(Step, course=course, pk=step_pk)
    try:
        prev_step = Step.objects.get(course=course, order=step.order-1)
    except:
        prev_step = "None"
    try:
        next_step = Step.objects.get(course=course, order=step.order+1)
    except:
        next_step = "None"

    if request.method == "POST":
        user = request.user
        txt = request.POST.get("comments_text")
        if txt != "":
            try:
                com = Step_comment(step=step, user=user, text=txt)
                com.save();
                step.step_comment_set.add(com)
            except Exception as e:
                pass
    context = {
        'step': step,
        'prev_step': prev_step,
        'next_step': next_step,
        'namepage':step.title
    }
    return render(request, 'portal/step_detail.html', context)

@login_required
def subscribe(request, course_slug):
    course = get_object_or_404(Course, slug=course_slug)
    request.user.profile.courses.add(course)
    course.save()
    return redirect('portal:course_list')

@login_required
def search_vacancy(request):
    user = request.user
    my_vacancy=[]
    search=user.profile.specialty
    if request.method == "POST":
        search=request.POST.get("search")

    search.replace(' ','+')
    headers={'accept':'*/*',
                'user-agent':'Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.121 Safari/537.36 Vivaldi/2.8.1664.44'}
    base_url='https://hh.kz/search/vacancy?L_is_autosearch=false&area=160&clusters=true&currency_code=KZT&enable_snippets=true&search_period=7&text={}&page=0'.format(search)
    urls=[]
    urls.append(base_url)
    session = requests.Session()
    request1 = session.get(base_url,headers=headers)
    if request1.status_code == 200:
        soup=bs(request1.content,'lxml')
        divs = soup.find_all('div',attrs={'data-qa':'vacancy-serp__vacancy'})
        try:
            page=soup.find_all('a',attrs={'data-qa':'pager-page'})
            count=int(page[-1].text)
            for  i in range(count):
                url=f'https://hh.kz/search/vacancy?L_is_autosearch=false&area=160&clusters=true&currency_code=KZT&enable_snippets=true&search_period=7&text={search}&page={i}'
                if url not in urls:
                    urls.append(url)
        except:
            pass
    print(urls)
    for url in urls:
        request1 = session.get(url,headers=headers)
        soup=bs(request1.content,'lxml')
        divs = soup.find_all('div',attrs={'data-qa':'vacancy-serp__vacancy'})
        for div in divs:
            try:
                title=div.find('a',attrs={'data-qa':'vacancy-serp__vacancy-title'}).text
                href=div.find('a',attrs={'data-qa':'vacancy-serp__vacancy-title'})['href']
                company=div.find('a',attrs={'data-qa':'vacancy-serp__vacancy-employer'}).text
                city=div.find('span',attrs={'data-qa':'vacancy-serp__vacancy-address'}).text
                text1=div.find('div',attrs={'data-qa':'vacancy-serp__vacancy_snippet_responsibility'}).text
                text2=div.find('div',attrs={'data-qa':'vacancy-serp__vacancy_snippet_requirement'}).text
                text=text1+'\n'+text2
                date=div.find('span',attrs={'class':'vacancy-serp-item__publication-date'}).text
                tot = {
                    'title':title,
                    'href':href,
                    'company':company,
                    'city':city,
                    'text':text,
                    'date':date
                }
                my_vacancy.append(tot)
            except:
                pass

    context = {
        'my_vacancy':my_vacancy,
        'namepage':"Vacancy"
    }
    return render(request, 'portal/search_vacancy.html',context)

@login_required
def test(request,course_slug, test_pk):
    test = get_object_or_404(Test, pk=test_pk)
    questions = Question.objects.filter(test=test)
    b=False
    count = 0
    if request.method == "POST":
        if((len(request.POST.keys())-1) ==len(questions)):
            b=True
        for (q,a) in request.POST.items():
            try:
                question = Question.objects.get(pk=int(q))
                if question.answer.title != a:
                    b=False
                else:
                    count+=1
            except Exception as e:
                pass
        if(b):
            uT = User_Test(student=request.user, test=test, exp=count, solved=b)
            uT.save()
            request.user.user_test_set.add(uT)
            request.user.profile.current_exp += test.exp
            print(request.user.profile.current_exp)
            if(request.user.profile.current_exp >= 100000):
                request.user.profile.level = "GURU"
                request.user.profile.current_exp = request.user.profile.current_exp - 100000
            elif(request.user.profile.current_exp >= 10000):
                request.user.profile.level = "HIGH"
                request.user.profile.current_exp = request.user.profile.current_exp - 10000
            elif(request.user.profile.current_exp >= 1000):
                request.user.profile.level = "MIDDLE"
                request.user.profile.current_exp = request.user.profile.current_exp - 1000
            request.user.profile.save()

    try:
        if User_Test.objects.get(student=request.user):
            count=User_Test.objects.get(student=request.user).exp
            b = True
    except Exception as e:
        pass

    context = {'questions': questions,
                'namepage':'Test',
                'results':count,
                'solved':b}
    return render(request, 'portal/test.html', context)
