import java.lang.System;

import javafx.ui.*;

class Model {
  attribute firstName: String;
  attribute lastName: String;
}

var model = Model {
  firstName: "Joe"
  lastName: "Smith"
};

Frame {
  onClose: operation() {
      System.exit(0);
  }

  content: GroupPanel {
      var firstNameRow = Row { alignment: BASELINE }
      var lastNameRow = Row { alignment: BASELINE }
      var labelsColumn = Column {
          alignment: TRAILING
      }
      var fieldsColumn = Column {
          alignment: LEADING
          resizable: true
      }
      rows: [firstNameRow, lastNameRow]
      columns: [labelsColumn, fieldsColumn]
      content:
      [SimpleLabel {
          row: firstNameRow
          column: labelsColumn
          text: "First Name:"
      },
      TextField {
          row: firstNameRow
          column: fieldsColumn

          columns: 25
          value: bind model.firstName
      },
      SimpleLabel {
          row: lastNameRow
          column: labelsColumn
          text: "Last Name:"
      },
      TextField {
          row: lastNameRow
          column: fieldsColumn
          columns: 25
          value: bind model.lastName
      }]
  }
  visible: true
};