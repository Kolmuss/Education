from django.db import models
from django.urls import reverse
from django.contrib.auth.models import User

from portal.models import Profile

class Category(models.Model):
    title = models.CharField(max_length=20)

    def __str__(self):
        return self.title

class Post(models.Model):
    title = models.CharField(max_length = 100)
    overview = models.TextField()
    timestamp = models.DateTimeField(auto_now_add=True)
    view_count = models.IntegerField(default=0)
    comment_count = models.IntegerField(default = 0)
    author = models.ForeignKey(User,on_delete=models.CASCADE)
    categories = models.ManyToManyField(Category)
    featured = models.BooleanField()

    def __str__(self):
        return self.title

    def get_absolute_url(self):
        return  reverse('forum:post-detail',kwargs={
            'id':self.id
        })


class Post_comment(models.Model):
    dop = models.DateTimeField(auto_now_add=True, null=True)
    step = models.ForeignKey(Post, on_delete=models.CASCADE)
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    text = models.TextField(max_length=300)

    def __str__(self):
        return f'{self.user.first_name} {self.dop}'
