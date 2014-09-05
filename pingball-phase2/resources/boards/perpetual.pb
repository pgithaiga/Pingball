board name = perpetual gravity = 25.0

# Balls

ball name = ballA x = 1.5 y = 2.5 xVelocity = 0.0 yVelocity = 0.0

ball name = ballB x = 6.5 y = 2.5 xVelocity = 0.0 yVelocity = 0.0
ball name = ballC x = 8.5 y = 11.5 xVelocity = 0.0 yVelocity = 0.0
ball name = ballD x = 11.5 y = 9.5 xVelocity = 0.0 yVelocity = 0.0
ball name = ballE x = 2.5 y = 15.5 xVelocity = 0.0 yVelocity = 0.0

# Gadgets

squareBumper name = sqA x = 0 y = 2
squareBumper name = sqB x = 2 y = 2
squareBumper name = sqC x = 0 y = 1
squareBumper name = sqD x = 2 y = 1
squareBumper name = sqE x = 0 y = 0
squareBumper name = sqF x = 2 y = 0
squareBumper name = sqG x = 1 y = 0
absorber name = absA x = 1 y = 3 width = 1 height = 1
squareBumper name = sqH x = 0 y = 3
squareBumper name = sqI x = 2 y = 3
squareBumper name = sqJ x = 1 y = 4

triangleBumper name = triA x = 17 y = 5 orientation = 90
triangleBumper name = triB x = 5 y = 9 orientation = 270
circleBumper name = circX x = 13 y = 3

leftFlipper name = lfA x = 8 y = 3 orientation = 0

circleBumper name = circA x = 15 y = 12

circleBumper name = circB x = 12 y = 8

squareBumper name = sqK x = 4 y = 17
squareBumper name = sqL x = 5 y = 17
squareBumper name = sqM x = 6 y = 17
squareBumper name = sqN x = 3 y = 17
squareBumper name = sqO x = 1 y = 17
squareBumper name = sqP x = 0 y = 17
triangleBumper name = triC x = 6 y = 16 orientation = 180
triangleBumper name = triD x = 7 y = 15 orientation = 180
triangleBumper name = triE x = 8 y = 14 orientation = 180
circleBumper name = circC x = 9 y = 13

absorber name = absB x = 0 y = 19 width = 18 height = 1
squareBumper name = sqQ x = 19 y = 19
squareBumper name = sqR x = 18 y = 19
squareBumper name = sqS x = 19 y = 18
triangleBumper name = triG x = 18 y = 18 orientation = 180
triangleBumper name = triH x = 19 y = 17 orientation = 180

# Triggers

fire trigger = absA action = absA
fire trigger = sqG action = absB
fire trigger = sqK action = lfA
fire trigger = sqL action = lfA
