var app = angular.module('eCommerce', ['ngRoute']);

app.config(function ($routeProvider) {
  $routeProvider
      .when('/',
          {
            controller: 'productsController',
            templateUrl: 'view1/view1.html',
          })
      .when('/:param',
          {
            controller: 'productDetailsController',
            templateUrl: 'view2/view2.html',
          })
      .otherwise({ redirectTo: '/' });
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

    $scope.setCategory = function(cat) {
        $scope.catFilter = cat.category_name;
    };

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