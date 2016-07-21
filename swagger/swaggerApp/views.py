from rest_framework import viewsets,mixins
from rest_framework.response import Response
from serializers import *
from models import *
from django.http import Http404, request,JsonResponse
from rest_framework import status
from django.db.models import F, FloatField, Sum, Count, Min, Q

# Create your views here.

class MiddleWare(object):
    def process_response(self, request, response):
        if(response is not None):
            if(response._container is not None and response._container is not ['']):
                response._container = ['{"data":'+response._container[0]+'}']

        return response


class ProductViewSet(viewsets.ModelViewSet):
    queryset = Product.objects.filter(is_available=1).all().prefetch_related('category_id')
    serializer_class = ProductSerializer

    def destroy(self, request, *args, **kwargs):
        instance = self.get_object()
        instance.is_available = 0
        instance.save()
        return Response(status=status.HTTP_204_NO_CONTENT)

class FeedbackViewSet(viewsets.ModelViewSet):
    queryset = Feedback.objects.all()
    serializer_class = FeedbackSerializer

class OrderViewSet(viewsets.ModelViewSet):
    queryset = Orders.objects.filter(deleted=0).all()
    serializer_class = OrdersSerializer

    def destroy(self, request, *args, **kwargs):
        instance = self.get_object()
        instance.deleted=1
        instance.save()
        return Response(status=status.HTTP_204_NO_CONTENT)

class CatergoryViewSet(viewsets.ModelViewSet):
    queryset = CategoryDescription.objects.all()
    serializer_class = CategorySerializer

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


    def retrieve(self, request, *args, **kwargs):
        order_id = self.kwargs['order_id']
        pk = self.kwargs['pk']

        queryset = OrderDetails.objects.filter(order__order_id=order_id, product__product_id=pk)
        return Response(OrderDetailsSerializer(queryset, many=True).data)

    def create(self, request, *args, **kwargs):

        orderdetailsSerializer = OrderDetailsSerializer(data=request.data)
        data = request.data
        print(data)
        if orderdetailsSerializer.is_valid():
            product = Product.objects.get(product_id=data['product_id'])
            order = Orders.objects.get(order_id=kwargs['order_id'])
            orderdetails = OrderDetails.objects.create(product=product, order=order,
                                                       selling_price=data['price'],quantity_ordered=data['qty'])
            return Response(OrderDetailsSerializer(orderdetails).data, status=status.HTTP_201_CREATED)
        else:
            return Response(status=status.HTTP_400_BAD_REQUEST)




