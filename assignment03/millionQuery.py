from sqlalchemy import *
import random
from random import randint

engine = create_engine("mysql+pymysql://root:root@localhost/millionQuery")
connection = engine.connect()
metadata = MetaData()
ordersDetails = Table('ORDER_DETAILS', metadata, autoload=True, autoload_with=engine)

for id in range(1  ,1000001):
    price=randint(1,5000)
    diff=randint(1,100)
    quantityordered=randint(1,30)
    orderid=randint(1,100)
    productid=randint(1,10)
    ins = orders.insert().values(ORDER_DETAILS_ID=id, ORDER_ID=orderid,
    	PRODUCT_ID=productid, QUANTITY_ORDERED=quantityordered, 
    	COST_PRICE=price, SELLING_PRICE=price+diff)
    res = connection.execute(ins)