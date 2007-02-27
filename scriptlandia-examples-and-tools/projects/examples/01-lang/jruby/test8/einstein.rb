require 'tiny_prolog_ext'

def _
  (@anonymous_var = (@anonymous_var ||= "S_00000").succ).intern
end

member[:Z, cons(_,:L)] <<= member[:Z, :L]
member[:Z, cons(:Z,_)].fact

#query member[:X, list(1,2,3,4,5)]

next_to[:X, :Y, :List] <<= iright[:X, :Y, :List]
next_to[:X, :Y, :List] <<= iright[:Y, :X, :List]

iright[:L, :R, cons(:L, cons(:R , _ ))].fact
iright[:L, :R, cons(_ , :Rest)] <<= iright[:L, :R, :Rest]
einstein[:Houses, :Fish_Owner] <<= [ 
    eq[:Houses, list(list('house', 'norwegian', _, _, _, _), _, list('house', _, _, _, 'milk', _), _, _)],
    member[list('house', 'brit', _, _, _, 'red'), :Houses],
    member[list('house', 'swede', 'dog', _, _, _), :Houses],
    member[list('house', 'dane', _, _, 'tea', _), :Houses],
    iright[list('house', _, _, _, _, 'green'), list('house', _, _, _, _, 'white'), :Houses],
    member[list('house', _, _, _, 'coffee', 'green'), :Houses],
    member[list('house', _, 'bird', 'pallmall', _, _), :Houses],
    member[list('house', _, _, 'dunhill', _, 'yellow'), :Houses],
    next_to[list('house', _, _, 'dunhill', _, _), list('house', _, 'horse', _, _, _), :Houses],
    member[list('house', _, _, _, 'milk', _), :Houses],
    next_to[list('house', _, _, 'marlboro', _, _), list('house', _, 'cat', _, _, _), :Houses],
    next_to[list('house', _, _, 'marlboro', _, _), list('house', _, _, _, 'water', _), :Houses],
    member[list('house', _, _, 'winfield', 'beer', _), :Houses],
    member[list('house', 'german', _, 'rothmans', _, _), :Houses],
    next_to[list('house', 'norwegian', _, _, _, _), list('house', _, _, _, _, 'blue'), :Houses],
    member[list('house', :Fish_Owner, 'fish', _, _, _), :Houses]
  ]

query einstein[:Houses, :Fish_Owner]
