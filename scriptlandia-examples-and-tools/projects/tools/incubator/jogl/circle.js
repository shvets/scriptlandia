importPackage(Packages.javax.media.opengl);
importPackage(Packages.javax.swing);

var frame = new JFrame("hello");
frame.setSize(400, 400);
var glcanvas = new GLCanvas();
frame.add(glcanvas);

glcanvas.addGLEventListener(new GLEventListener() {
   init: function(drawable) {
   },

   display: function(drawable) {
     var gl = drawable.getGL();

     with(gl) {
         glClearColor(0.0, 0.0, 0.0, 0.0);
         glClear(GL_COLOR_BUFFER_BIT);
         glColor3f(0.0, 1.0, 0.0);
         glBegin(GL_LINE_LOOP);
         for (var i = 0; i < 100; i++) {
           var angle = 2*Math.PI*i / 100;
           glVertex2f(Math.cos(angle), Math.sin(angle));
         }
         glEnd();
         glFlush();
     }     
   },

   reshape: function() {},
   displayChanged: function() {}
});

frame.setVisible(true);
