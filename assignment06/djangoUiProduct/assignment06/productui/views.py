from django.shortcuts import render
from django.http import HttpResponse, JsonResponse
from django.db.models import F, Sum, Count, FloatField
from django.core.serializers import serialize
import json
import datetime
from models import *

from django.http import HttpResponse
from django.core import serializers
import json

# Create your views here.


def index(request):
    return HttpResponse("hello world")


def result(request):
    startDate = request.GET.get('startDate', '01/31/2000')
    startDate = datetime.datetime.strptime(startDate, '%m/%d/%Y').strftime('%Y-%m-%d')
    endDate = request.GET.get('endDate', '01/31/2050')
    endDate = datetime.datetime.strptime(endDate, '%m/%d/%Y').strftime('%Y-%m-%d')  # <view logic>

    results = OrderDetails.objects.filter(order__order_date__gte=startDate, order__order_date__lte=endDate).values(
        'order__order_date').order_by('-order__order_date').annotate(orders=Count('order__order_id'), qty=Sum('quantity_ordered'),
                                      sale_price=Sum(F('selling_price') * F('quantity_ordered'),output_field=FloatField()),
                                      buy_price=Sum(F('quantity_ordered') * F('cost_price'),output_field=FloatField()),
                                      profit=Sum(F('selling_price')*F('quantity_ordered'),output_field=FloatField()) - Sum(F('quantity_ordered')*F('cost_price'),output_field=FloatField()))

    resp_list = []
    for obj in results:
        obj['order__order_date'] = datetime.date.strftime(obj['order__order_date'], "%m/%d/%Y")
        daily_data = {
            'date': obj['order__order_date'],
            "orders": obj['orders'],
            "qty": obj['qty'],
            "buy_price": obj['buy_price'],
            "sale_price": obj['sale_price'],
            "profit": obj['profit']
        }
        resp_list.append(daily_data)

    return JsonResponse({"data": resp_list})
