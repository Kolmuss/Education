from django.contrib import admin

# Register your models here.
from .models import Category,Post,Post_comment

admin.site.register(Category)
admin.site.register(Post)
admin.site.register(Post_comment)