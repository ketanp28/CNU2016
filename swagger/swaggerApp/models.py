# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey has `on_delete` set to the desired behavior.
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
from __future__ import unicode_literals

from django.db import models


class CategoryDescription(models.Model):
    category_id = models.AutoField(db_column='CATEGORY_ID', primary_key=True)  # Field name made lowercase.
    category_name = models.CharField(db_column='CATEGORY_NAME', unique=True, max_length=255, blank=True, null=True)  # Field name made lowercase.
    description = models.CharField(db_column='DESCRIPTION', max_length=255, blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'CATEGORY_DESCRIPTION'


class Feedback(models.Model):
    message = models.CharField(db_column='MESSAGE', max_length=1000, blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'FEEDBACK'


class LogData(models.Model):
    timestamp = models.DateTimeField(db_column='TIMESTAMP', blank=True, null=True)  # Field name made lowercase.
    url = models.CharField(db_column='URL', max_length=1000, blank=True, null=True)  # Field name made lowercase.
    parameters = models.CharField(db_column='PARAMETERS', max_length=1000, blank=True, null=True)  # Field name made lowercase.
    responsecode = models.IntegerField(db_column='RESPONSECODE', blank=True, null=True)  # Field name made lowercase.
    ipaddress = models.CharField(db_column='IPADDRESS', max_length=1000, blank=True, null=True)  # Field name made lowercase.
    executiontime = models.IntegerField(db_column='EXECUTIONTIME', blank=True, null=True)  # Field name made lowercase.
    requesttype = models.CharField(db_column='REQUESTTYPE', max_length=255, blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'LOG_DATA'


class Orders(models.Model):
    order_id = models.AutoField(db_column='ORDER_ID', primary_key=True)  # Field name made lowercase.
    user = models.ForeignKey('UserDetails', models.DO_NOTHING, db_column='USER_ID', blank=True, null=True)  # Field name made lowercase.
    order_date = models.DateField(db_column='ORDER_DATE', blank=True, null=True)  # Field name made lowercase.
    status = models.CharField(db_column='STATUS', max_length=100, blank=True, null=True)  # Field name made lowercase.
    deleted = models.IntegerField(db_column='DELETED', blank=True, null=True)  # Field name made lowercase. This field type is a guess.

    class Meta:
        managed = False
        db_table = 'ORDERS'


class OrderDetails(models.Model):
    order_details_id = models.AutoField(db_column='ORDER_DETAILS_ID', primary_key=True)  # Field name made lowercase.
    order = models.ForeignKey(Orders, models.DO_NOTHING, db_column='ORDER_ID')  # Field name made lowercase.
    product = models.ForeignKey('Product', models.DO_NOTHING, db_column='PRODUCT_ID')  # Field name made lowercase.
    quantity_ordered = models.IntegerField(db_column='QUANTITY_ORDERED', blank=True, null=True)  # Field name made lowercase.
    cost_price = models.FloatField(db_column='COST_PRICE', blank=True, null=True)  # Field name made lowercase.
    selling_price = models.FloatField(db_column='SELLING_PRICE', blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'ORDER_DETAILS'


class Product(models.Model):
    product_id = models.AutoField(db_column='PRODUCT_ID', primary_key=True)  # Field name made lowercase.
    product_description = models.CharField(db_column='PRODUCT_DESCRIPTION', max_length=1000, blank=True, null=True)  # Field name made lowercase.
    cost_price = models.FloatField(db_column='COST_PRICE', blank=True, null=True)  # Field name made lowercase.
    selling_price = models.FloatField(db_column='SELLING_PRICE', blank=True, null=True)  # Field name made lowercase.
    quantity_in_stock = models.IntegerField(db_column='QUANTITY_IN_STOCK', blank=True, null=True)  # Field name made lowercase.
    product_code = models.CharField(db_column='PRODUCT_CODE', max_length=100, blank=True, null=True)  # Field name made lowercase.
    product_name = models.CharField(db_column='PRODUCT_NAME', max_length=100, blank=True, null=True)  # Field name made lowercase.
    is_available = models.IntegerField(db_column='IS_AVAILABLE', blank=True, null=True)  # Field name made lowercase. This field type is a guess.
    category_id = models.ForeignKey(CategoryDescription, models.DO_NOTHING, db_column='CATEGORY_ID', blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'PRODUCT'
        unique_together = (('product_code', 'product_name'),)



class UserDetails(models.Model):
    user_id = models.AutoField(db_column='USER_ID', primary_key=True)  # Field name made lowercase.
    company_name = models.CharField(db_column='COMPANY_NAME', unique=True, max_length=100, blank=True, null=True)  # Field name made lowercase.
    contact_first_name = models.CharField(db_column='CONTACT_FIRST_NAME', max_length=100, blank=True, null=True)  # Field name made lowercase.
    phone = models.CharField(db_column='PHONE', max_length=100, blank=True, null=True)  # Field name made lowercase.
    contact_last_name = models.CharField(db_column='CONTACT_LAST_NAME', max_length=100, blank=True, null=True)  # Field name made lowercase.
    address_line = models.CharField(db_column='ADDRESS_LINE', max_length=100, blank=True, null=True)  # Field name made lowercase.
    city = models.CharField(db_column='CITY', max_length=100, blank=True, null=True)  # Field name made lowercase.
    state = models.CharField(db_column='STATE', max_length=100, blank=True, null=True)  # Field name made lowercase.
    postal_code = models.CharField(db_column='POSTAL_CODE', max_length=100, blank=True, null=True)  # Field name made lowercase.
    country = models.CharField(db_column='COUNTRY', max_length=100, blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = False
        db_table = 'USER_DETAILS'


class AuthGroup(models.Model):
    name = models.CharField(unique=True, max_length=80)

    class Meta:
        managed = False
        db_table = 'auth_group'


class AuthGroupPermissions(models.Model):
    group = models.ForeignKey(AuthGroup, models.DO_NOTHING)
    permission = models.ForeignKey('AuthPermission', models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'auth_group_permissions'
        unique_together = (('group', 'permission'),)


class AuthPermission(models.Model):
    name = models.CharField(max_length=255)
    content_type = models.ForeignKey('DjangoContentType', models.DO_NOTHING)
    codename = models.CharField(max_length=100)

    class Meta:
        managed = False
        db_table = 'auth_permission'
        unique_together = (('content_type', 'codename'),)


class AuthUser(models.Model):
    password = models.CharField(max_length=128)
    last_login = models.DateTimeField(blank=True, null=True)
    is_superuser = models.IntegerField()
    username = models.CharField(unique=True, max_length=30)
    first_name = models.CharField(max_length=30)
    last_name = models.CharField(max_length=30)
    email = models.CharField(max_length=254)
    is_staff = models.IntegerField()
    is_active = models.IntegerField()
    date_joined = models.DateTimeField()

    class Meta:
        managed = False
        db_table = 'auth_user'


class AuthUserGroups(models.Model):
    user = models.ForeignKey(AuthUser, models.DO_NOTHING)
    group = models.ForeignKey(AuthGroup, models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'auth_user_groups'
        unique_together = (('user', 'group'),)


class AuthUserUserPermissions(models.Model):
    user = models.ForeignKey(AuthUser, models.DO_NOTHING)
    permission = models.ForeignKey(AuthPermission, models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'auth_user_user_permissions'
        unique_together = (('user', 'permission'),)


class DjangoAdminLog(models.Model):
    action_time = models.DateTimeField()
    object_id = models.TextField(blank=True, null=True)
    object_repr = models.CharField(max_length=200)
    action_flag = models.SmallIntegerField()
    change_message = models.TextField()
    content_type = models.ForeignKey('DjangoContentType', models.DO_NOTHING, blank=True, null=True)
    user = models.ForeignKey(AuthUser, models.DO_NOTHING)

    class Meta:
        managed = False
        db_table = 'django_admin_log'


class DjangoContentType(models.Model):
    app_label = models.CharField(max_length=100)
    model = models.CharField(max_length=100)

    class Meta:
        managed = False
        db_table = 'django_content_type'
        unique_together = (('app_label', 'model'),)


class DjangoMigrations(models.Model):
    app = models.CharField(max_length=255)
    name = models.CharField(max_length=255)
    applied = models.DateTimeField()

    class Meta:
        managed = False
        db_table = 'django_migrations'


class DjangoSession(models.Model):
    session_key = models.CharField(primary_key=True, max_length=40)
    session_data = models.TextField()
    expire_date = models.DateTimeField()

    class Meta:
        managed = False
        db_table = 'django_session'
