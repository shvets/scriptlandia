require 'tiny_prolog_ext'

# rules
# read as "X and Y are siblings if Z is the parent of both"
sibling[:X,:Y] <<= [ parent[:Z,:X], parent[:Z,:Y], noteq[:X,:Y] ]
parent[:X,:Y] <<= father[:X,:Y]
parent[:X,:Y] <<= mother[:X,:Y]

# facts: rules with "no preconditions"
father["matz", "Ruby"].fact
mother["Trude", "Sally"].fact
father["Tom", "Sally"].fact
father["Tom", "Erica"].fact
father["Mike", "Tom"].fact

query sibling[:X, "Sally"]
# >> 1 sibling["Erica", "Sally"]

