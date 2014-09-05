board name = flipperWorld gravity = 9.8 friction1=0.02 friction2 = 0.0020
#Board with a lot of flippers triggering each other.
#Good for using to observe if some flippers behve incorrectly

# Balls

ball name = ballA x = 1.5 y = 0.5 xVelocity = 0.0 yVelocity = 0.0
ball name = ballB x = 2.5 y = 1.5 xVelocity = 1.0 yVelocity = 0.0
ball name = ballC x = 3.5 y = 2.5 xVelocity = 2.3 yVelocity = -1.0
ball name = ballD x = 4.5 y = 3.5 xVelocity = 5.9 yVelocity = 1.6
ball name = ballE x = 5.5 y = 4.5 xVelocity = -0.5 yVelocity = 0.0
ball name = ballF x = 6.5 y = 5.5 xVelocity = 0.0 yVelocity = 2.2
ball name = ballG x = 7.5 y = 0.5 xVelocity = -1 yVelocity = -0.2
ball name = ballF x = 8.5 y = 5.5 xVelocity = 0.2 yVelocity = 0.2
ball name = ballG x = 9.5 y = 0.5 xVelocity = 0.3 yVelocity = 2.4

# Flippers

rightFlipper name = flipR1 x = 8 y=2 orientation = 270
rightFlipper name = flipR2 x = 12 y=14 orientation = 90
rightFlipper name = flipR3 x = 14 y=16 orientation = 90
rightFlipper name = flipR4 x = 0 y=3 orientation = 0
rightFlipper name = flipR5 x = 2 y=16 orientation = 90
rightFlipper name = flipR6 x = 4 y=11 orientation = 180
rightFlipper name = flipR7 x = 16 y=2 orientation = 270
rightFlipper name = flipR8 x = 0 y=7 orientation = 0

leftFlipper name = flipL1 x = 6 y = 2 orientation = 270
leftFlipper name = flipL2 x = 14 y = 14 orientation = 180
leftFlipper name = flipL3 x = 8 y = 15 orientation = 90
leftFlipper name = flipL4 x = 6 y = 12 orientation = 0
leftFlipper name = flipL5 x = 10 y = 15 orientation = 90
leftFlipper name = flipL6 x = 12 y = 8 orientation = 180
leftFlipper name = flipL7 x = 14 y = 5 orientation = 0
leftFlipper name = flipL8 x = 10 y = 6 orientation = 0


# CircleBumper
circleBumper name=Circle1 x=5  y=6
circleBumper name=Circle2 x=1 y=15
circleBumper name=Circle3 x=11 y=12

# TriangleBumper
triangleBumper name=Tri1 x=19 y=0 orientation=90
triangleBumper name=Tri2 x=0 y=1 orientation=270

#  absorber to catch the ball
 absorber name=Abs x=0 y=18 width=20 height=1 

fire trigger=Abs action=Abs 
fire trigger=Circle1 action=flipR1
fire trigger=Circle1 action=flipR2
fire trigger=Circle1 action=flipR3
fire trigger=Circle1 action=flipR4
fire trigger=Circle2 action=flipR5
fire trigger=Circle2 action=flipR6
fire trigger=Circle2 action=flipR7
fire trigger=Circle2 action=flipR8
fire trigger=Circle3 action=flipL1
fire trigger=Circle3 action=flipL2
fire trigger=Tri1 action=flipL3
fire trigger=Tri1 action=flipL4
fire trigger=Tri1 action=flipL5
fire trigger=Tri2 action=flipL6
fire trigger=Tri2 action=flipL7
fire trigger=Tri2 action=flipL8

fire trigger=flipR1 action=flipR2
fire trigger=flipR2 action=flipR3
fire trigger=flipR3 action=flipR4
fire trigger=flipR4 action=flipR5
fire trigger=flipR5 action=flipR6
fire trigger=flipR6 action=flipR7
fire trigger=flipR7 action=flipR8
fire trigger=flipR8 action=flipR1

fire trigger=flipL1 action=flipL2
fire trigger=flipL2 action=flipL3
fire trigger=flipL3 action=flipL4
fire trigger=flipL4 action=flipL5
fire trigger=flipL5 action=flipL6
fire trigger=flipL6 action=flipL7
fire trigger=flipL7 action=flipL8
fire trigger=flipL8 action=flipL1

keyup key=a action=flipL1