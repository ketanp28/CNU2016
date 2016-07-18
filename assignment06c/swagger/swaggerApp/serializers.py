from rest_framework import serializers
from models import *


class ProductSerializer (serializers.ModelSerializer):
    id = serializers.IntegerField(source='product_id',read_only = True)
    code = serializers.CharField(source='product_code',required=True)
    description = serializers.CharField(source='product_description',required=True)
    price = serializers.FloatField(source='cost_price',required=True)
    category_id = serializers.IntegerField(source='category_id.category_id',read_only=True)
    category = serializers.CharField(write_only=True,required=True)

    class Meta:
        model = Product
        fields = ('id', 'code', 'description', 'price', 'category_id', 'category')

    def create(self, validated_data):
        print(validated_data)
        return Product.objects.create(
            product_code = validated_data['product_code'],
            product_description = validated_data['product_description'],
            category_id=CategoryDescription.objects.get_or_create(category_name=validated_data['category'])[0],
            cost_price = validated_data['cost_price']
        )

    def update(self, instance, validated_data):
        if self.partial:
            if 'product_code' in validated_data:
                instance.product_code = validated_data['product_code']
            if 'product_description' in validated_data:
                instance.product_description = validated_data['product_description']
            if 'cost_price' in validated_data:
                instance.cost_price = validated_data['cost_price']
            if 'category' in validated_data:
                instance.category_id = CategoryDescription.objects.get_or_create(category_name=validated_data['category'])[0]
        else:
            instance.product_code = validated_data['product_code']
            instance.product_description = validated_data['product_description']
            instance.cost_price = validated_data['cost_price']
            instance.category_id = CategoryDescription.objects.get_or_create(category_name=validated_data['category'])[0]
        instance.save()
        return instance


class OrdersSerializer (serializers.ModelSerializer):
    id = serializers.IntegerField(source='order_id', read_only = True)
    username = serializers.CharField(source='user.company_name',required=False)
    address = serializers.CharField(source='user.address_line',required=False)
    status = serializers.CharField(required=False)

    class Meta:
        model = Orders
        fields = ('id', 'username', 'address', 'status')


    def create(self, validated_data):
        print(validated_data)
        if 'user' in validated_data:
            if 'address_line' in validated_data['user']:
                userOb = UserDetails.objects.get_or_create(company_name=validated_data['user']['company_name'],
                                                     address_line = validated_data['user']['address_line'])
            else :
                userOb = UserDetails.objects.get_or_create(company_name=validated_data['user']['company_name'])
        status = validated_data['status']
        return Orders.objects.create(
            user = userOb[0],
            status = validated_data['status']
        )

    def update(self, instance, validated_data):
        print(validated_data)
        if self.partial:
            #PATCH
            if 'user' in validated_data:
                if 'company_name' in validated_data['user']:
                    if 'address_line' in validated_data['user']:
                        UserDetails.objects.filter(
                            company_name=validated_data['user']['company_name']).update(
                            address_line=validated_data['user']['address_line'])
                        instance.user = UserDetails.objects.get_or_create(
                            company_name=validated_data['user']['company_name'])[0]
                    else:
                        instance.user = UserDetails.objects.get_or_create(
                            company_name=validated_data['user']['company_name'])[0]
            if 'status' in validated_data:
                instance.status = validated_data['status']
        else:
            #PUT
            if 'user' in validated_data:
                if 'company_name' in validated_data['user']:
                    if 'address_line' in validated_data['user']:
                        UserDetails.objects.filter(
                            company_name=validated_data['user']['company_name']).update(
                            address_line=validated_data['user']['address_line'])
                        instance.user = UserDetails.objects.get_or_create(
                            company_name=validated_data['user']['company_name'])[0]
                    else :
                        userOb = UserDetails.objects.get_or_create(
                            company_name=validated_data['user']['company_name'])[0]
                        userOb.address_line = None
                        instance.user = userOb
            else :
                instance.user = None
            if 'status' in validated_data:
                instance.status = validated_data['status']
            else :
                instance.status = None
        instance.save()
        return instance


class OrderDetailsSerializer (serializers.ModelSerializer):
    id = serializers.IntegerField(source='order_details_id', read_only = True)
    product_id = serializers.IntegerField(source='product.product_id',required= True)
    order_id = serializers.IntegerField(source='order.order_id',required=True)
    price = serializers.CharField(source='selling_price',required=True)

    class Meta:
        model = OrderDetails
        fields = ('id', 'product_id', 'order_id', 'price')

