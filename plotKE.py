#!/usr/bin/env python3

import matplotlib.pyplot as plt

import statistics as sts

import numpy as np


def plotError(filename):

  aux = []
  f = open(filename, "r")
  lines = f.readlines()
  for l in lines:
    array = l.split()
    x = float(array[0])
    y = float(array[1])

    plt.errorbar(x, y,
       yerr=0,
       marker='o',
       color='k',
       ecolor='k',
       # markerfacecolor='g',
       capsize=1,
       linestyle='None',
       fmt=' ')

    # plt.errorbar(n, mean, yerr=std)

  fig = plt.gcf()
  axes = fig.gca()
  # axes.set_ylim([0, 0.16])
  # axes.set_xlim([0, 0.035])

  plt.xlabel('Tiempo (s)')
  plt.ylabel('Energia cinetica (J)')
  plt.show()
  return


plotError("KEoutput.txt")