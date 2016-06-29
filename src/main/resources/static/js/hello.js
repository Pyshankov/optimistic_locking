/**
 * Created by pyshankov on 29.06.16.
 */
angular.module('hello', [])

    .controller('home', function($http,$scope) {
    var self = this;

     var updatedataCallback = function() {
         $http.get('car').then(function (response) {
             self.cars = response.data._embedded.car;
             console.log(self.cars);
         });
     }

        updatedataCallback();

        $scope.update = function(index){

            var json = JSON.stringify(self.cars[index]);
            var resultUpdate = $http.patch("update",json);


            resultUpdate.success(function(data, status) {

                updatedataCallback();

                console.log(status);
            });

            resultUpdate.error(function(data, status) {
                console.log(status);
                if (status===409) {
                    alert("this field have already updated");
                    $http.patch("car/"+ (index+1) ,json).success(function() {

                        updatedataCallback();

                    }).error(function(){

                        updatedataCallback();

                    });
                }
            });
        }

});