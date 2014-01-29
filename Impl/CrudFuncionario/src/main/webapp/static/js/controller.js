function mainController($scope, $http) {
	var urlBase = '/CrudFuncionario/employee';

	$scope.listar = function() {
		$http.get(urlBase).success(
			function(data) {
				$scope.funcionarios = data;
			}
		).error(
			function(data, status) {
				console.log('API retornou um erro ['+status+ '] com a mensagem ['+data+']');
			}
		);
	};

	$scope.excluir = function(funcionario) {
		$http({method: 'DELETE', url: urlBase + "/" + funcionario.id}).
			success(
				function(data) {
					var index = $scope.funcionarios.indexOf(funcionario);
					$scope.funcionarios.splice(index, 1);
				}
			).error(
				function(data, status) {
					console.log('API retornou um erro ['+status+ '] com a mensagem ['+data+']');
				}
			);
	};

	$scope.adicionar = function(funcionario) {
		$http.post(urlBase, funcionario).success(
			function(data, status, headers, config) {
				$scope.funcionarios.push(funcionario);
				$scope.funcionario = new Funcionario({});
			}
		).error(
			function(data, status) {
				console.log('API retornou um erro ['+status+ '] com a mensagem ['+data+']');
			}
		);
	};

	$scope.funcionario = new Funcionario({});
	$scope.listar();
}

function Funcionario(args) {
	this.id = args.id;
	this.firstName = args.firstName || "";
	this.lastName = args.lastName || "";
	this.salary = args.salary || 00;
	this.role = args.role || "";
}