#!/usr/bin/env python3

import matplotlib.pyplot as plt

import numpy as np


def plotError(filename):

  x = []
  y = []
  y2 = []
  f = open(filename, "r")
  lines = f.readlines()
  for l in lines:
    array = l.split()
    x.append(float(array[0]))
    y.append(float(array[1]))
    y2.append(float(array[2]))

  plt.plot(x, y, color='blue')
  plt.plot(x, y2, color='red')


  fig = plt.gcf()
  axes = fig.gca()

  plt.xlabel('Tiempo [s]', fontsize=20)
  plt.ylabel('Fuerzas', fontsize=20)
  axes.tick_params(axis='x', labelsize=16)
  axes.tick_params(axis='y', labelsize=16)
  #axes.set_yscale('log')
  plt.show()

  return


plotError("KEoutput.txt")