var app = angular.module('eCommerce', ['ngRoute','ngCart']);


var orderId = 0;



app.config(function ($routeProvider) {
  $routeProvider
      .when('/',
          {
            controller: 'productsController',
            templateUrl: 'view1/view1.html',
          })
      .when('/products/:param',
          {
            controller: 'productDetailsController',
            templateUrl: 'view2/view2.html',
          })
      .when('/cart',
          {
            controller: 'cartController',
            templateUrl: 'view3/cartPage.html',
          })
      .when('/submit',
          {
            controller: 'submitController',
            templateUrl: 'view4/submit.html',
          })
      .when('/contactUs',
          {
              controller: 'feedbackController',
              templateUrl: 'view5/contactUs.html',
          })
      .otherwise({ redirectTo: '/' });
    });

    app.controller('feedbackController', function($scope,$http,ngCart) {

        $scope.feedback=function () {
            console.log("submit clicked")
            var data = JSON.stringify({message : $scope.user_name});
            var config = {headers : {'Content-Type': 'application/json;'}}

            $http.post('http://127.0.0.1:8000/api/feedback/', data, config);
            alert("Feedback Submitted Succesfully!!");
            ngCart.empty();
            $scope.orderId=0;
        }


        $scope.products = ngCart;
    });


    app.controller('cartController', function($scope,$http,ngCart) {


        $scope.addToDB=function () {

            var data = JSON.stringify({status : "In Progress"});
            var config = {headers : {'Content-Type': 'application/json;'}}
            console.log(ngCart.getItems());
            $http.post('http://127.0.0.1:8000/api/orders/',data,config)
            .success(function (data, status, headers, config) {
                $scope.orderId= data.id;
                console.log($scope.orderId);
                console.log("orderId generated");
                var orderList = ngCart.getItems();
                for(var i=0;i<ngCart.getTotalItems();i++){
                    var data = JSON.stringify({product_id : orderList[i].getId(),
                                                qty : orderList[i].getQuantity(),
                                                price : orderList[i].getPrice()})
                    console.log($scope.orderId);

                    $http.post('http://127.0.0.1:8000/api/orders/'+$scope.orderId+'/orderlineitem/', data, config);
                    console.log("product ordered");
                }
            });

        };

        $scope.items = function() {
            return ngCart.getTotalItems();
        };


    });

    app.controller('submitController', function($scope, $http) {

        $scope.submitToDB=function () {
            console.log("submit clicked")
            var data = JSON.stringify({username : $scope.user_name,address : $scope.user_address ,status : "Completed"});
            var config = {headers : {'Content-Type': 'application/json;'}}
            console.log($scope.orderId)
            $http.patch('http://127.0.0.1:8000/api/orders/'+$scope.orderId + '/', data, config);
        }
     });


    app.controller('productsController', function($scope, $http) {
        $http({
            method: 'GET',
            url: 'http://localhost:8000/api/products'
        }).then(function successCallback(response) {
            $scope.products = response;
        }, function errorCallback(response) {
            console.log("Error in GET products");
        });

        $http({
            method: 'GET',
            url: 'http://localhost:8000/api/category'
        }).then(function successCallback(response) {
            $scope.category = response;
        }, function errorCallback(response) {
            console.log("Error in GET products");
        });


});


app.controller('productDetailsController', function($scope, $http, $routeParams) {
    $http({
        method: 'GET',
        url: 'http://localhost:8000/api/products/' + $routeParams.param
    }).then(function successCallback(response) {
        $scope.productDetails = response;
        console.log($scope.productDetails)
    }, function errorCallback(response) {
        console.log("Error in GET products");
    });
});