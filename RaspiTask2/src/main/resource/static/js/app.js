var taskManagerModule = angular.module('taskManagerApp', [ 'ngAnimate' ]);

taskManagerModule.controller("MyController", function($scope, $timeout) {
	console.log("MyController");
	$timeout(callAtTimeout, 5000);
	$scope.showLive = false;
	$scope.showRealTime = function showRealTime() {
		console.log("showRealTime");
		console.log("showRealTime para : " + $scope.showLive);
		$scope.showLive = !($scope.showLive);
		window.location.reload(true);

	};

	function callAtTimeout() {
		
//		if ($scope.showLive) {
//			window.location.reload($scope.showLive);
//			console.log("Timeout occurred");
//		}
		
	}
});

taskManagerModule
		.controller(
				'taskManagerController',
				function($scope, $http) {

					var urlBase = "";
					$scope.reloaded = true;
					$scope.toggle = true;
					$scope.selection = [];
					$scope.days = [ {
						id : 1,
						name : 'Pazartesi'
					}, {
						id : 2,
						name : 'Sali'
					}, {
						id : 3,
						name : 'Carsamba'
					}, {
						id : 4,
						name : 'Persembe'
					}, {
						id : 5,
						name : 'Cuma'
					}, {
						id : 6,
						name : 'Cumartesi'
					}, {
						id : 7,
						name : 'Pazar'
					} ];

					$scope.day = {};
					$scope.day.id = new Date().getDay();
					$scope.startTaskDate = new Date();
					$scope.finishTaskDate = new Date();
					$scope.statuses = [ 'ACTIVE', 'COMPLETED' ];
					$scope.priorities = [ 'RED', 'GREEN', 'OFF' ];
					$http.defaults.headers.post["Content-Type"] = "application/json";

					function findAllTasks() {
						// get all tasks and display initially
						$http
								.get(
										urlBase
												+ '/tasks/search/findByTaskArchived?archivedfalse=0')
								.success(
										function(data) {
											if (data._embedded != undefined) {
												$scope.tasks = data._embedded.tasks;
											} else {
												$scope.tasks = [];
											}
											for (var i = 0; i < $scope.tasks.length; i++) {
												if ($scope.tasks[i].taskStatus == 'COMPLETED') {
													$scope.selection
															.push($scope.tasks[i].taskId);
												}
											}
											$scope.taskName = "";
											$scope.taskDesc = "";
											$scope.taskPriority = "";
											$scope.taskStatus = "";
											$scope.taskDate = "";
											$scope.toggle = '!toggle';
										});
					}

					function findTasksByDate() {
						$http
								.get(
										urlBase
												+ '/tasks/search/findByTaskDayAndTaskArchived?taskDay='
												+ $scope.day.id
												+ '&archivedfalse=0')
								.success(
										function(data) {
											if (data._embedded != undefined) {
												$scope.tasks = data._embedded.tasks;
											} else {
												$scope.tasks = [];
											}
											for (var i = 0; i < $scope.tasks.length; i++) {
												if ($scope.tasks[i].taskStatus == 'COMPLETED') {
													$scope.selection
															.push($scope.tasks[i].taskId);
												}
											}
											$scope.taskName = "";
											$scope.taskDesc = "";
											$scope.taskPriority = "";
											$scope.taskStatus = "";
											$scope.taskDate = "";
											$scope.toggle = '!toggle';
										});
					}

					function cloneTask(taskName) {
						// get all tasks and display initially
						$http.get(
								urlBase + '/taskController?taskName='
										+ taskName).success(function(data) {
						});
					}

					findTasksByDate();

					// add a new task
					$scope.addTask = function addTask() {
						console.log("date " + $scope.startTaskDateString);
						console.log("date " + $scope.finishTaskDateString);

						if ($scope.taskName == "" || $scope.taskPriority == ""
								|| $scope.taskStatus == "") {
							alert("Insufficient Data! Please provide values for task name, description, priortiy and status");
						} else {
							$http.post(urlBase + '/tasks', {
								taskName : $scope.taskName,
								taskPriority : $scope.taskPriority,
								taskStatus : $scope.taskStatus,
								startTaskDate : $scope.startTaskDateString,
								finishTaskDate : $scope.finishTaskDateString,
								taskDay : $scope.day.id,
								dateType : $scope.dateType
							}).success(
									function(data, status, headers) {
										var newTaskUri = headers()["location"];
										console.log("Might be good to GET "
												+ newTaskUri
												+ " and append the task.");
										cloneTask($scope.taskName);
										// Refetching EVERYTHING every time can
										// get expensive over time
										// Better solution would be to
										// $http.get(headers()["location"]) and
										// add it to the list
										findTasksByDate();
										window.location.reload(true);
									});
						}
					};

					// toggle selection for a given task by task id
					$scope.toggleSelection = function toggleSelection(taskUri) {
						var idx = $scope.selection.indexOf(taskUri);

						// is currently selected
						// HTTP PATCH to ACTIVE state
						if (idx > -1) {
							$http.patch(taskUri, {
								taskStatus : 'ACTIVE'
							}).success(function(data) {
								alert("Task unmarked");
								findTasksByDate();
							});
							$scope.selection.splice(idx, 1);
						}

						// is newly selected
						// HTTP PATCH to COMPLETED state
						else {
							$http.patch(taskUri, {
								taskStatus : 'COMPLETED'
							}).success(function(data) {
								alert("Task marked completed");
								findTasksByDate();
							});
							$scope.selection.push(taskUri);
						}
					};

					// Archive Completed Tasks
					$scope.archiveTasks = function archiveTasks() {
						$scope.selection.forEach(function(taskUri) {
							if (taskUri != undefined) {
								$http.patch(taskUri, {
									taskArchived : 1
								});
							}
						});
						alert("Successfully Archived");
						console
								.log("It's risky to run this without confirming all the patches are done. when.js is great for that");
						findTasksByDate();
					};

					$scope.allTasks = function allTasks() {
						console.log("allTasks");
						findAllTasks();
					};

					$scope.getTasksByDay = function getTasksByDay() {
						console.log("getTasksByDay");
						findTasksByDate();
					};

					function timerComplete() {
						console.log("allTasks");
						window.location.reload(true);
						if ($scope.toggle) {
							window.location.reload(true);
							$scope.allTasks;
						}
						setTimeout(timerComplete, 2000);
					}
					;

				});

// Angularjs Directive for confirm dialog box
taskManagerModule.directive('ngConfirmClick', [ function() {
	return {
		link : function(scope, element, attr) {
			var msg = attr.ngConfirmClick || "Are you sure?";
			var clickAction = attr.confirmedClick;
			element.bind('click', function(event) {
				if (window.confirm(msg)) {
					scope.$eval(clickAction);
				}
			});
		}
	};
} ]);