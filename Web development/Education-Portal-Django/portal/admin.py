from django.contrib import admin

from .models import *

admin.site.register(Profile)
admin.site.register(Course)
admin.site.register(Step)
admin.site.register(Course_comment)
admin.site.register(Step_comment)
admin.site.register(Tag)
admin.site.register(Test)
admin.site.register(Question)
admin.site.register(Choice)
admin.site.register(User_Test)
