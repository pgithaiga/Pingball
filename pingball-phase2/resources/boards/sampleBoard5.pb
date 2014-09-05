board name=example5

# define a ball
ball name=BallA x=0.99 y=3.1 xVelocity=10.4 yVelocity=10.3 
ball name=BallB x=18 y=4 xVelocity=6.5 yVelocity=8.2
ball name=BallC x=4.5 y=1 xVelocity=-4.6 yVelocity=-1.5


# define the bumpers

squareBumper name= SquareA x =3  y= 10
triangleBumper name = Tri1 x =4 y =12 orientation=90
triangleBumper name=Tri1 x=4 y=11 orientation =  0
circleBumper name= CircleA x = 10 y = 3

#define the flippers
rightFlipper name=FlipR1 x=3 y=4 orientation=0
leftFlipper name=FlipL1 x=1 y=3 orientation=0

#define key presses
keydown key=left action=FlipL1
keyup key=left action =FlipL1
keydown key=right action = FlipR1
keyup key=right action = FlipR1


#define the absorber
absorber name=Abs x=3 y=14 width=1 height=1
absorber name=Abs2 x=5 y=10 width=6 height=1
absorber name=Abs x=2 y=1 width=4 height=2