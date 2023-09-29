extends Control

signal game_over

const MAX_TIME: float = 5.0
var current_time:float = 0

func _ready():
	current_time = MAX_TIME

func _process(delta):
	decreate_time(delta)
	check_if_time_ended()
	$CounterLabel.text = "%.2f" % current_time

func check_if_time_ended():
	if current_time <= 0:
		print("Game Over")
		# TODO push signal

func increase_time(amount: float):
	current_time += amount

func decreate_time(amount: float):
	if current_time - amount >= 0:
		current_time -= amount
	else:
		current_time = 0.0
