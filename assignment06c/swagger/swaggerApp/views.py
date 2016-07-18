from rest_framework import viewsets,mixins
from rest_framework.response import Response
from serializers import *
from models import *
from django.http import Http404, request,JsonResponse
from rest_framework import status
from django.db.models import F, FloatField, Sum, Count, Min, Q

# Create your views here.


class ProductViewSet(viewsets.ModelViewSet):
    queryset = Product.objects.filter(is_available=1)
    serializer_class = ProductSerializer

    def perform_destroy(self, instance):
        try:
            instance.is_available=0
            instance.save()
        except Http404:
            return Response(status=status.HTTP_404_NOT_FOUND)
        return Response(status=status.HTTP_204_NO_CONTENT)

class OrderViewSet(viewsets.ModelViewSet):
    queryset = Orders.objects.all()
    serializer_class = OrdersSerializer

class ProductSummaryViewSet(mixins.ListModelMixin, viewsets.GenericViewSet):

    def list(self, request, *args, **kwargs):
        code = request.query_params.get('code',None)
        category_name = request.query_params.get('category_id',None)
        group_by = request.query_params.get('group_by',None)
        queryset = Product.objects.all()
        if code:
            queryset = queryset.filter(product_code=code)
        if category_name:
            queryset = queryset.filter(category_id__category_name=category_name)
        if group_by:
            if(queryset.count()!=0):
                queryset = queryset.values('category_id__category_name').annotated(
                    count = Count('product_id'),category_id = F('category_id__category_id'))
                return JsonResponse([list(queryset)], safe=False)
            else:
                queryset = {'count': 0}
                if (category_name):
                    queryset['category_id'] = CategoryDescription.objects.get(category_name=category_name).category_id
                return JsonResponse([queryset], safe=False)

        else :
            queryset = queryset.aggregate(count = Count('product_id'))
            return JsonResponse([queryset], safe=False)





class OrderDetailsViewSet(mixins.RetrieveModelMixin, mixins.CreateModelMixin,
                          mixins.ListModelMixin, viewsets.GenericViewSet):
    queryset = OrderDetails.objects.all()
    serializer_class = OrderDetailsSerializer

    def list(self, request, *args, **kwargs):
        print(self.kwargs['order_id'])
        queryset = OrderDetails.objects.filter(order__order_id=kwargs['order_id']).all()
        serializer = OrderDetailsSerializer(queryset,many=True)
        return Response(serializer.data)


    # def retrieve(self, request, *args, **kwargs):
    #     print(self.kwargs['pk'])
    #     print(OrderDetailsSerializer(OrderDetails.objects.all()).data)
    #     queryset = OrderDetails.objects.filter(order__order_id=self.kwargs['order_id'], product__product_id=(int)(self.kwargs['pk'])).all()
    #     serializer = OrderDetailsSerializer(queryset,True)
    #     return Response(serializer.data)

    def create(self, request, *args, **kwargs):

        orderdetailsSerializer = serializers.OrderLineItemSerializer(data=request.data)
        data = request.data
        if orderdetailsSerializer.is_valid():
            product = models.Product.objects.get(id=data['product_id'])
            order = models.Orders.objects.get(order_id=kwargs['order_id'])
            orderdetails = models.Orderdetails.objects.create(product=product, order=order, price=data['price'])
            serializer = self.get_serializer(orderdetails)
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        else:
            return Response(status=status.HTTP_400_BAD_REQUEST)
