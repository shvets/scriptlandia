MAXLENGTH = 70

ARGF.each{ |line|
   if line.chomp.length > MAXLENGTH
      puts "#{ARGF.filename} line=#{ARGF.lineno} chars=#{line.length}"
   end
}