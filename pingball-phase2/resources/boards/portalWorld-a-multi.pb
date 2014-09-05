board name = portalWorldA gravity = 10 friction1=0.02 friction2 = 0.02
#Board portalWorld good to check if portal works correctly with other board B
#PRESS A TO ACTIVATE FLIPPERS 
#PRESS Q TO SHOOT A BALL FROM ABSORBER

# Balls

ball name = ballA x = 1.5 y = 0.5 xVelocity = 0.5 yVelocity = 0.0
ball name = ballB x = 2.5 y = 0.5 xVelocity = -0.4 yVelocity = 0.0
ball name = ballC x = 3.5 y = 0.5 xVelocity = 0.3 yVelocity = 0.0
ball name = ballD x = 4.5 y = 0.5 xVelocity = 1.2 yVelocity = 0.0

# Portals
portal name=portalA x=4 y=11 otherBoard = portalWorldB otherPortal=portalB
portal name=portalB x=7 y=3 otherBoard = nothing otherPortal=portalA
portal name=portalC x=10 y=10 otherPortal=portalA
portal name=portalD x=16 y=10 otherPortal=Nothing
portal name=portalE x=18 y=1 otherBoard = portalWorldB otherPortal=portalD

# Gadgets
# left side
triangleBumper name = tri1 x = 0 y = 9  orientation = 270
triangleBumper name = tri2 x = 1 y = 10 orientation = 270
triangleBumper name = tri3 x = 2 y = 11 orientation = 270
triangleBumper name = tri4 x = 3 y = 12 orientation = 270
triangleBumper name = tri5 x = 4 y = 13 orientation = 270
triangleBumper name = tri6 x = 5 y = 14 orientation = 270
triangleBumper name = tri7 x = 6 y = 15 orientation = 270


#right side
triangleBumper name = tri10 x = 18 y = 9 orientation = 180
triangleBumper name = tri11 x = 17 y = 10 orientation = 180
triangleBumper name = tri12 x = 16 y = 11 orientation = 180
triangleBumper name = tri13 x = 15 y = 12 orientation = 180
triangleBumper name = tri14 x = 14 y = 13 orientation = 180
triangleBumper name = tri15 x = 13 y = 14 orientation = 180
triangleBumper name = tri16 x = 12 y = 15 orientation = 180

#circleBump
circleBumper name=cir1 x=10 y=2
circleBumper name=cir2 x=3 y=5
circleBumper name=cir3 x=15 y=4
circleBumper name=cir4 x=8 y=8

#bounce
leftFlipper name = FlipL x = 7 y = 16 orientation = 0
rightFlipper name = FlipR x = 10 y = 16 orientation = 0

#bounce top
triangleBumper name=Bounce x=19 y=0 orientation=90

#abs
 absorber name=Abs x=0 y=19 width=20 height=1 

#key
keydown key=a action=FlipL
keydown key=a action=FlipR
keyup key=a action=FlipL
keyup key=a action=FlipR
keyup key=q action=Abs