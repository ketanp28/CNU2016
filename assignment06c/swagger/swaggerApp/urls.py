from django.conf.urls import include,url
from . import views
from rest_framework.routers import DefaultRouter


router = DefaultRouter()
router.register(r'products$', views.ProductViewSet)
router.register(r'products/summary',views.ProductSummaryViewSet,'orderlineitem')
router.register(r'orders',views.OrderViewSet)
router.register(r'orders/(?P<order_id>[0-9]+)/orderlineitem', views.OrderDetailsViewSet)



urlpatterns = [
    url(r'^', include(router.urls)),
    url(r'^docs/', include('rest_framework_swagger.urls')),
]