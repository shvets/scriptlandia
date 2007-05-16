import java.lang.System;

import javafx.ui.*;

class Item {
  attribute id: String;
  attribute productId: String;
  attribute description: String;
  attribute inStock: Boolean;
  attribute quantity: Number;
  attribute listPrice: Number;
  attribute totalCost: Number;
}

attribute Item.totalCost = bind quantity*listPrice;

class Cart {
  attribute items: Item*;
  attribute subTotal: Number;
}

operation sumItems(itemList:Item*) {
  var result = 0.00;
  for (item in itemList) {
      result += item.totalCost;
  }
  return result;
}

attribute Cart.subTotal = bind sumItems(items);

var cart = Cart {
  items:
  [Item {
      id: "UGLY"
      productId: "D100"
      description: "BullDog"
      inStock: true
      quantity: 1
      listPrice: 97.50
  },
  Item {
      id: "BITES"
      productId: "D101"
      description: "Pit Bull"
      inStock: true
      quantity: 1
      listPrice: 127.50
  }]
};

Frame {
  onClose: operation() {
      System.exit(0);
  }

   content: Label {

      text: bind

         "<html>
             <h2 align='center'>Shopping Cart</h2>
             <table align='center' border='0' bgcolor='#008800' cellspacing='2' cellpadding='5'>
                <tr bgcolor='#cccccc'>
                   <td><b>Item ID</b></td>
                   <td><b>Product ID</b></td>
                   <td><b>Description</b></td>
                   <td><b>In Stock?</b></td>
                   <td><b>Quantity</b></td>
                   <td><b>List Price</b></td>
                   <td><b>Total Cost</b></td>
                   <td> </td>
                 </tr>

                 {
                   if (sizeof cart.items == 0)
                   then "<tr bgcolor='#FFFF88'><td colspan='8'><b>Your cart is empty.</b></td></tr>"
                   else foreach (item in cart.items)
                       "<tr bgcolor='#FFFF88'>
                        <td>{item.id}</td>
                        <td>{item.productId}</td>
                        <td>{item.description}</td>
                        <td>{if item.inStock then "Yes" else "No"}</td>
                        <td>{item.quantity}</td>
                        <td align='right'>{item.listPrice}</td>
                        <td align='right'>{item.totalCost}</td>
                        <td> </td>
                        </tr>"
                 }

                 <tr bgcolor='#FFFF88'>
                      <td colspan='7' align='right'>
                         <b>Sub Total: ${cart.subTotal}</b>
                     </td>
                     <td> </td>
                  </tr>
               </table>
            </html>"
      }

      visible: true

}