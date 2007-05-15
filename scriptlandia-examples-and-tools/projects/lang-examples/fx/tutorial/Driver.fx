/*
 *  $Id$
 * 
 *  Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 *  SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package tutorial;
import javafx.ui.*;
import java.lang.System;

Frame {
    onClose: operation() {
        System.exit(0);
    }
    height: 800
    width: 1000
    visible: true
    title: "JavaFX Canvas Tutorial"
    content: new Main()
}
