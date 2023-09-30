extends CharacterBody2D

const MOVE_SPEED: float = 100

# Called when the node enters the scene tree for the first time.
func _ready():
	pass # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass


func _physics_process(delta):
	var horizontalDirection = Input.get_action_strength("right") - Input.get_action_strength("left")
	
	velocity = Vector2(horizontalDirection * MOVE_SPEED, velocity.y)
	
	move_and_slide()
