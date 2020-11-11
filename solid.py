def generate_solids_file(gap):
  dfile = open('solid' + str(gap) + '.xyz', 'w')

  j = 0
  

  dfile.write(str(2646) + '\n' + '\n')

  #draw left wall
  for i in range(0, 1000):
    dfile.write('0' + '  ' + str(float(i)/1000) + '  ' + '0.0005' + '\n')
    j += 1

  #draw right wall
  for i in range(0, 1000):
    dfile.write('0.3' + '  ' + str(float(i)/1000) + '  ' + '0.0005' + '\n')
    j += 1

  #draw upper wall  
  for i in range(1, 299):
    dfile.write(str(float(i)/1000) + '  ' + '1.0' + '  ' + '0.0005' + '\n')
    j += 1

  #draw lower wall
  #left part
  for i in range(1, (300/2 - int(gap*1000)/2)):
    dfile.write(str(float(i)/1000) + '  ' + '0.0' + '  ' + '0.0005' + '\n')
    j += 1

  #right part
  for i in range((300/2 + int(gap*1000)/2), 299):
    dfile.write(str(float(i)/1000) + '  ' + '0.0' + '  ' + '0.0005' + '\n')
    j += 1

  print(j)

gap = input("Insert gap size:\n")
generate_solids_file(float(gap))
