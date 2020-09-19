from django.db import models
from django.contrib.auth.models import User

class Course(models.Model):
    slug = models.SlugField(null=False, unique=True)
    created_at = models.DateTimeField(auto_now_add=True)
    title = models.CharField(max_length=255)
    short_name = models.CharField(max_length=25,default='None')
    description = models.TextField()
    image = models.ImageField(default='default-course.png', upload_to='image_courses')
    level = models.CharField(max_length=255,default='None')
    banner= models.ImageField(default='banner-course.png', upload_to='banner_courses')
    likes = models.ManyToManyField(User)

    def __str__(self):
    	return self.short_name

    def get_absolute_url(self):
        return reverse('article_detail', kwargs={'slug': self.slug})

class Profile(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE)
    image = models.ImageField(default='default.jpg', upload_to='profile_pics')
    phone_number = models.CharField(max_length=15, blank=True)
    biography = models.TextField(max_length=500, blank=True)
    location = models.CharField(max_length=30, blank=True)
    gender = models.CharField(max_length=10, blank=True)
    birth_date = models.DateField(null=True, blank=True)
    specialty = models.CharField(max_length=20, blank=True)
    level = models.CharField(max_length=50, null=True, default='NEWBIE') # Newbie, Middle, High, Guru
    current_exp = models.IntegerField(default=0)
    courses = models.ManyToManyField(Course, blank=True)

    def __str__(self):
        return f'{self.user.username} Profile'

class Step(models.Model):
    title = models.CharField(max_length=255)
    description = models.CharField(max_length=300, blank=True)
    content = models.TextField(blank=True, default='')
    order = models.IntegerField(default=0)
    course = models.ForeignKey(Course, on_delete=models.CASCADE)
    video = models.CharField(max_length=255,null=True,default='None')
    class Meta:
	       ordering = ['order',]

    def __str__(self):
           return f'{self.course.short_name} Step'

class Step_comment(models.Model):
    dop = models.DateTimeField(auto_now_add=True, null=True)
    step = models.ForeignKey(Step, on_delete=models.CASCADE)
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    text = models.TextField(max_length=300)

    def __str__(self):
        return f'{self.user.first_name} {self.dop}'

class Course_comment(models.Model):
    dop = models.DateTimeField(auto_now_add=True, null=True)
    course = models.ForeignKey(Course, on_delete=models.CASCADE)
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    text = models.TextField(max_length=300)

    def __str__(self):
        return f'{self.user.first_name} {self.dop}'

class Tag(models.Model):
    slug = models.SlugField(null=True, unique=True)
    courses = models.ManyToManyField(Course, blank=True)
    name = models.CharField(max_length=50, null=True)

    def __str__(self):
        return f'{self.name} Tag'


class Test(models.Model):
    course = models.OneToOneField(Course, on_delete=models.CASCADE)
    exp = models.IntegerField(default=0)
    def __str__(self):
        return " Test"

class User_Test(models.Model):
    student = models.ForeignKey(User, on_delete=models.CASCADE)
    test = models.ForeignKey(
        Test, on_delete=models.SET_NULL, blank=True, null=True)
    exp = models.FloatField()
    solved = models.BooleanField(null=True)

    def __str__(self):
        return self.student.username + " " + str(self.exp)

class Choice(models.Model):
    title = models.CharField(max_length=50)
    def __str__(self):
        return self.title

class Question(models.Model):
    question = models.CharField(max_length=200)
    choices = models.ManyToManyField(Choice)
    answer = models.ForeignKey(
        Choice, on_delete=models.CASCADE, related_name = "answer", blank=True, null=True)
    test = models.ForeignKey(
        Test, on_delete=models.CASCADE, related_name = "test", blank=True, null=True)

    def __str__(self):
        return self.question
