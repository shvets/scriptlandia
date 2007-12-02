response.setContentType("text/html")
n = param.n
if (n == null)
    n = 3
else
    n = Integer.parseInt(n)

println """
<html>
<body>
"""

for (x in 1..9) {
    println "${n} X ${x} = ${n * x} <br />"
}

println """
</body>
</html>
"""