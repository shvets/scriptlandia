count = session.getAttribute("counter")

if (count == null) {
    count = 1
    session.setAttribute("counter", 1)
} else {
    counter += 1
    session.setAttribute("counter", 1)
}

println "Session counter: ${counter}"

