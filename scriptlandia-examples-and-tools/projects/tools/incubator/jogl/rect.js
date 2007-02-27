// import JSR-231 stuff
importPackage(javax.media.opengl);
importClass(javax.swing.JFrame);

var frame = new JFrame("hello");
frame.setSize(400, 400);

//create GLCanvas and add that to frame
var glcanvas = new GLCanvas();
frame.add(glcanvas);

glcanvas.addGLEventListener(new GLEventListener() {
   init: function(drawable) {
   },

   display: function(drawable) {
     var gl = drawable.getGL();

     // with this "with" statement, we don't need to prefix
     // "gl." before GL method calls -- so that it feels like
     // writing in C :-)

     with(gl) {
         glClearColor(0.0, 0.0, 0.0, 0.0);
         glClear(GL_COLOR_BUFFER_BIT);
         glColor3f(1.0, 0.0, 0.0);
         glBegin(GL_POLYGON);
         glVertex3f(0.25, 0.25, 0.0);
         glVertex3f(0.75, 0.25, 0.0);
         glVertex3f(0.75, 0.75, 0.0);
         glVertex3f(0.25, 0.75, 0.0);
         glEnd();
         glFlush();
     }     
   },

   reshape: function() {},
   displayChanged: function() {}
});

frame.setVisible(true);
