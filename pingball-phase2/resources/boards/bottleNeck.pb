board name = bottleNeck gravity = 5 friction1=0.02 friction2 = 0.1
#Board a bottle neck. Good to check if the balls actually collide with each other or not.

# Balls

ball name = ballA x = 1.5 y = 0.5 xVelocity = 0.0 yVelocity = 0.0
ball name = ballB x = 2.5 y = 0.5 xVelocity = 0.0 yVelocity = 0.0
ball name = ballC x = 3.5 y = 0.5 xVelocity = 0.0 yVelocity = 0.0
ball name = ballD x = 4.5 y = 0.5 xVelocity = 0.0 yVelocity = 0.0
ball name = ballE x = 5.5 y = 0.5 xVelocity = 0.0 yVelocity = 0.0

# Gadgets
# left side
triangleBumper name = tri1 x = 0 y = 2  orientation = 270
triangleBumper name = tri2 x = 2 y = 3 orientation = 270
triangleBumper name = tri3 x = 4 y = 4 orientation = 270
triangleBumper name = tri4 x = 6 y = 5 orientation = 270
triangleBumper name = tri5 x = 8 y = 6 orientation = 270
squareBumper name = sq1 x = 1 y = 3 
squareBumper name = sq2 x = 3 y = 4 
squareBumper name = sq3 x = 5 y = 5 
squareBumper name = sq4 x = 7 y = 6 

#right side
triangleBumper name = tri6 x = 18 y = 2  orientation = 180
triangleBumper name = tri7 x = 16 y = 3 orientation = 180
triangleBumper name = tri8 x = 14 y = 4 orientation = 180
triangleBumper name = tri9 x = 12 y = 5 orientation = 180
triangleBumper name = tri10 x = 10 y = 6 orientation = 180
squareBumper name = sq5 x = 17 y = 3 
squareBumper name = sq6 x = 15 y = 4 
squareBumper name = sq7 x = 13 y = 5 
squareBumper name = sq8 x = 11 y = 6 

# down
squareBumper name = sq9 x = 10 y = 7 
squareBumper name = sq10 x = 10 y = 8 
triangleBumper name = tri11 x = 9 y = 9 orientation = 180
squareBumper name = sq12 x = 10 y = 10 
squareBumper name = sq13 x = 8 y = 7 

#left
squareBumper name = sq15 x = 9 y = 10 
squareBumper name = sq16 x = 8 y = 10 
squareBumper name = sq17 x = 7 y = 10 
squareBumper name = sq18 x = 6 y = 10 
squareBumper name = sq19 x = 5 y = 10 
squareBumper name = sq20 x = 4 y = 10 
squareBumper name = sq21 x = 3 y = 10 
squareBumper name = sq22 x = 2 y = 10 
squareBumper name = sq23 x = 1 y = 10 

#bounce
triangleBumper name=Bounce x=19 y=0 orientation=90

#abs
 absorber name=Abs x=0 y=18 width=20 height=1 

fire trigger=Abs action=Abs 