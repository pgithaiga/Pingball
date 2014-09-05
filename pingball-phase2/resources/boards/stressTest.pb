board name=stressTest gravity = 20.0
# Test for situation when we have many many balls

# define a ball
ball name=Ball0 x=1.8 y=4.5 xVelocity=10.4 yVelocity=10.3
ball name=Ball1 x=2.8 y=4.5 xVelocity=10.4 yVelocity=10.3
ball name=Ball2 x=3.8 y=4.5 xVelocity=10.4 yVelocity=10.3
ball name=Ball3 x=4.8 y=4.5 xVelocity=10.4 yVelocity=10.3
ball name=Ball4 x=5.8 y=4.5 xVelocity=10.4 yVelocity=10.3
ball name=Ball5 x=6.8 y=4.5 xVelocity=10.4 yVelocity=10.3
ball name=Ball6 x=7.8 y=4.5 xVelocity=10.4 yVelocity=10.3
ball name=Ball7 x=8.8 y=4.5 xVelocity=10.4 yVelocity=10.3
ball name=Ball8 x=9.8 y=4.5 xVelocity=10.4 yVelocity=10.3
ball name=Ball9 x=10.8 y=4.5 xVelocity=10.4 yVelocity=10.3
ball name=Balla x=11.8 y=4.5 xVelocity=10.4 yVelocity=10.3
ball name=Ballb x=12.8 y=4.5 xVelocity=10.4 yVelocity=10.3
ball name=Ballc x=13.8 y=4.5 xVelocity=10.4 yVelocity=10.3
ball name=Balld x=14.8 y=4.5 xVelocity=10.4 yVelocity=10.3
ball name=Balle x=15.8 y=4.5 xVelocity=10.4 yVelocity=10.3
ball name=Ballf x=16.8 y=4.5 xVelocity=10.4 yVelocity=10.3
ball name=Ballg x=17.8 y=4.5 xVelocity=10.4 yVelocity=10.3
ball name=Ballh x=18.8 y=4.5 xVelocity=10.4 yVelocity=10.3
ball name=Balli x=19.8 y=4.5 xVelocity=10.4 yVelocity=10.3

# define some bumpers
squareBumper name=Square x=0 y=10
squareBumper name=SquareB x=1 y=10
squareBumper name=SquareC x=2 y=10
squareBumper name=SquareD x=3 y=10

circleBumper name=Circle x=4 y=3
circleBumper name=Circle2 x=6 y=6
circleBumper name=Circle3 x=10 y=7
circleBumper name=Circle4 x=7 y=10
circleBumper name=Circle5 x=12 y=12
circleBumper name=Circle6 x=16 y=5
circleBumper name=Circle7 x=17 y=14
circleBumper name=Circle8 x=15 y=2
circleBumper name=Circle9 x=11 y=14
triangleBumper name=Tri x=19 y=3 orientation=90
triangleBumper name=Tri2 x=0 y=0 orientation=0
triangleBumper name=Tri3 x=3 y=11 orientation=0

# define an absorber to catch the ball
 absorber name=Abs x=12 y=17 width=8 height=3
 absorber name=Abs2 x=0 y=18 width=4 height=1


# make the absorber self-triggering
 fire trigger=Abs action=Abs
 fire trigger=Square action=Abs
 fire trigger=SquareB action=Abs
 fire trigger=SquareC action=Abs
 fire trigger=SquareD action=Abs
 fire trigger=Abs2 action=Abs2
 fire trigger=Square action=Abs2
 fire trigger=SquareB action=Abs2
 fire trigger=SquareC action=Abs2
 fire trigger=SquareD action=Abs2