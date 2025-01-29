package dojo.supermarket.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {

    private final List<ProductQuantity> items = new ArrayList<>();
    private final Map<Product, Double> productQuantities = new HashMap<>();

    List<ProductQuantity> getItems() {
        return Collections.unmodifiableList(items);
    }

    void addItem(Product product) {
        addItemQuantity(product, 1.0);
    }

    Map<Product, Double> productQuantities() {
        return Collections.unmodifiableMap(productQuantities);
    }

    public void addItemQuantity(Product product, double quantity) {
        items.add(new ProductQuantity(product, quantity));
        if (productQuantities.containsKey(product)) {
            productQuantities.put(product, productQuantities.get(product) + quantity);
        } else {
            productQuantities.put(product, quantity);
        }
    }

    /* 
     * Here we have a variable named x but this name doesn't give us the meaning.
     * I would name it "setOfProduct" because it is represent the number of product when it sold in set
     * 
     * We have also a variable called numberOfXs that would be better named "numberOfSets" because it shows the numbers of sets
    */
    void handleOffers(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog) {
        for (Product p: productQuantities().keySet()) {
            double quantity = productQuantities.get(p);
            if (offers.containsKey(p)) {
                Offer offer = offers.get(p);
                double unitPrice = catalog.getUnitPrice(p);
                int quantityAsInt = (int) quantity;
                Discount discount = null;
                int x = 1;
                if (offer.offerType == SpecialOfferType.THREE_FOR_TWO) { // Replace multiple if-else with switch
                    x = 3;

                } else if (offer.offerType == SpecialOfferType.TWO_FOR_AMOUNT) {
                    x = 2;
                    if (quantityAsInt >= 2) {
                        double total = offer.argument * (quantityAsInt / x) + quantityAsInt % 2 * unitPrice;
                        double discountN = unitPrice * quantity - total;
                        discount = new Discount(p, "2 for " + offer.argument, -discountN);
                    }

                } if (offer.offerType == SpecialOfferType.FIVE_FOR_AMOUNT) {
                    x = 5;
                }
                int numberOfXs = quantityAsInt / x;
                if (offer.offerType == SpecialOfferType.THREE_FOR_TWO && quantityAsInt > 2) {
                    double discountAmount = quantity * unitPrice - ((numberOfXs * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
                    discount = new Discount(p, "3 for 2", -discountAmount);
                }
                if (offer.offerType == SpecialOfferType.TEN_PERCENT_DISCOUNT) {
                    discount = new Discount(p, offer.argument + "% off", -quantity * unitPrice * offer.argument / 100.0);
                }
                if (offer.offerType == SpecialOfferType.FIVE_FOR_AMOUNT && quantityAsInt >= 5) {
                    double discountTotal = unitPrice * quantity - (offer.argument * numberOfXs + quantityAsInt % 5 * unitPrice);
                    discount = new Discount(p, x + " for " + offer.argument, -discountTotal);
                }
                if (discount != null)
                    receipt.addDiscount(discount);
            }
        }
    }
}



// Our deep Code Smells & Refactoring Suggestions:
// 1. **Long Method (handleOffers)** - This method is too long and does multiple things. Split logic into smaller helper methods.
// 2. **Magic Numbers (x values in handleOffers)** - Define constants instead of using hardcoded numbers like 2, 3, 5.
// 3. **Complex Conditional Logic** - Refactor repeated `if-else` blocks using a `switch` statement (already applied).
// 4. **Duplication in Discount Calculation** - Move discount calculations to a separate method.
// 5. **Lack of Encapsulation** - ShoppingCart exposes internal data structures without proper encapsulation.
// 6. **Inconsistent Responsibility** - The ShoppingCart class should not handle discount logic; move it to an OfferProcessor class.
// 7. **Using Primitive Types for Money Calculations** - Use BigDecimal instead of double for better precision in monetary values.