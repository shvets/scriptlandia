require 'tiny_prolog_ext'

hanoi[:N] <<=  move[:N,"left","right","center"]
move[0,:X,:Y,:Z] <<= :CUT   # don't look for further solutions
move[:N,:A,:B,:C] <<= [
  is(:M,:N){|n| n - 1}, # reads as "M IS N - 1"
  move[:M,:A,:C,:B],
  write_info[:A,:B],
  move[:M,:C,:B,:A]
]
write_info[:X,:Y] <<= [
  write["move a disc from the "],
  write[:X], write[" pole to the "],
  write[:Y], writenl[" pole "]
]
query hanoi[2]
puts "Now with 4 discs..."
query hanoi[4]
