board name=juggler gravity = 10.0

# define a ball
ball name=BallB x=18 y=4.5 xVelocity=0 yVelocity=10 
ball name=BallB x=17 y=5.5 xVelocity=0 yVelocity=10 
ball name=BallB x=16 y=6.5 xVelocity=0 yVelocity=10 
ball name=BallB x=15 y=7.5 xVelocity=0 yVelocity=10 

squareBumper name=Square0 x=0 y=12
  squareBumper name=Square1 x=1 y=12
  squareBumper name=Square2 x=2 y=12
  squareBumper name=Square3 x=3 y=12
  squareBumper name=Square4 x=4 y=12
  squareBumper name=Square5 x=5 y=12
  squareBumper name=Square6 x=6 y=12
  

triangleBumper name=Tri1 x=19 y=3 orientation=90

leftFlipper name = lfA x = 3 y = 8 orientation = 270
rightFlipper name = rfA x = 5 y = 8 orientation = 90

# define an absorber to catch the ball
absorber name=Abs1 x=0 y=19 width=20 height=1  
 
# make the absorber self-triggering
fire trigger=Abs1 action=lfA
fire trigger=Abs1 action=rfA
fire trigger=Abs1 action=Abs1