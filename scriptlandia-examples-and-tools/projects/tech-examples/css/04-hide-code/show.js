function getElement(id) {
  var element = document.getElementById(id);
  return element;
}

function hide(x) {
 var element = getElement(x);
 if(element!=null) {
   element.style.display='none';
 }
}

function show(x) {
 var element = getElement(x);
 if(element!=null) {
   element.style.display='block';
 }
}

function switchDisplay(x) {
   var element = getElement(x);
   if(element!=null) {
     if(element.style.display=='none') {
       show(x);
     }
     else {
       hide(x);
     }
   }
}

