import java.lang.System;

import javafx.ui.*;

class Model {
  attribute tabPlacement: TabPlacement;
  attribute tabLayout: TabLayout;
  attribute tabCount: Integer;
  attribute selectedTab: Integer;
}

var model = Model {
  tabPlacement: TOP
  tabLayout: WRAP
  selectedTab: 3
  tabCount: 5
};

Frame {
  onClose: operation() {
      System.exit(0);
  }

  height: 300
  width: 400
  content: TabbedPane {
      tabPlacement: bind model.tabPlacement
      tabLayout: bind model.tabLayout
      tabs: bind foreach (i in [1..model.tabCount])
          Tab {
              title: "Tab {i}"
              toolTipText: "Tooltip {i}"
          }
      selectedIndex: bind model.selectedTab

  }
  visible: true
}

