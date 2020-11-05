#!/usr/bin/env python3

import random
from math import sqrt

#rx ry radius 

height = 1.0
width = 0.4

file = open("input", 'w')

particles = []
for i in range(0, 500000):
  flag = True
  j = 0
  radius = random.uniform(0.02, 0.03)
  x = random.uniform(0.001 + radius, width - 0.001 - radius)
  y = random.uniform(0.001 + radius, height - 0.001 - radius)

  while (j < len(particles) and flag):
    if(sqrt((particles[j][0] - x)**2 + (particles[j][1] - y)**2) < radius + particles[j][2]):
      flag = False
    else:
      j = j+1

  if(flag):
    particles.append([x,y,radius])

file.write(str(len(particles)) + '\n' + '\n')
for p in particles:
  file.write(str(p[0]) + '  ' + str(p[1]) + '  ' + str(p[2]) + '\n')


