import java.lang.System;

import javafx.ui.*;

class EmployeeModel {
  attribute employees: String*;
  attribute selectedEmployee: Number;
  attribute newHireName: String;
}

var model = EmployeeModel {
  employees:
  ["Alan Sommerer",
   "Alison Huml",
   "Kathy Walrath",
   "Lisa Friendly",
   "Mary Campione",
   "Sharon Zakhour"]
};

Frame {
  onClose: operation() {
      System.exit(0);
  }

  title: "ListBox Example"
  content: BorderPanel {
      center: ListBox {
          selection: bind model.selectedEmployee
          cells: bind foreach (emp in model.employees)
              ListCell {
                 text: emp
              }
      }
      bottom: FlowPanel {
          content:
          [Button {
             text: "Fire"
             action: operation() {
                 delete model.employees[model.selectedEmployee];
             }
          },
          RigidArea {
              width: 5
          },
          TextField {
              columns: 30
              value: bind model.newHireName
          },
          RigidArea {
              width: 5
          },
          Button {
              text: "Hire"
              enabled: bind model.newHireName.length() > 0
              action: operation() {
                  insert model.newHireName
                     after model.employees[model.selectedEmployee];
                  model.newHireName = "";
                  if (sizeof model.employees == 1) {
                      model.selectedEmployee = 0;
                  } else {
                      model.selectedEmployee++;
                  }
              }
          }]
      }
  }
  visible: true
}