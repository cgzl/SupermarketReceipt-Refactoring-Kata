Code Review Report
Rayane DAHDAH ER1801
Clément GONZALES ER1891
Mathias PENAVAIRE ER1899

Recommended Changes

1. Lack of Unit Tests

Class Concerned: Entire Codebase

Identified Issues

The project lacks unit tests, making it difficult to verify the correctness of the code and hindering future refactoring efforts. Without proper tests, regressions may occur when modifying existing code, and debugging issues becomes more time-consuming.

Solution

A comprehensive suite of unit tests should be implemented to cover the core functionalities, including product addition, receipt generation, and discount application. Using a testing framework like JUnit and applying Test-Driven Development (TDD) principles will improve code reliability. These tests should verify scenarios such as product price calculations, discount applications, and receipt formatting under different conditions.

2. Separation of Concerns

Class Concerned: Teller, ShoppingCart, Receipt

Identified Issues

The current implementation mixes different responsibilities within the same classes, leading to low cohesion and high coupling. The Teller class, for instance, handles both the processing of shopping carts and the application of discounts, making it harder to maintain and extend.

Solution

To adhere to the Single Responsibility Principle, the code should be refactored to separate concerns properly. A DiscountCalculator class should be created to handle discount-related logic, a ReceiptFormatter class should be introduced to format and display receipts, and the Teller class should focus only on processing customer transactions. This approach improves maintainability and allows for easier modifications in the future.

3. Method Length and Complexity

Class Concerned: Teller, ShoppingCart

Identified Issues

Some methods are excessively long and handle multiple responsibilities, making them hard to read and maintain. For example, checksOutArticlesFrom in Teller performs multiple actions in a single block of code.

Solution

The Extract Method refactoring technique should be applied to improve readability and structure. In Teller, the checksOutArticlesFrom method should be split into smaller methods:

scanProducts(): Handles product scanning and adding them to the cart.

applyDiscounts(): Checks and applies available discounts.

generateReceipt(): Creates and formats the receipt.

Similarly, in ShoppingCart, product addition logic should be divided into:

addProductToCart(): Adds an individual product.

updateCartTotal(): Recalculates the total price.

validateCartItems(): Ensures the cart contains valid products before checkout.

4. Naming Conventions

Class Concerned: Various Classes (e.g., Product, ReceiptItem)

Identified Issues

Some variable and method names are not descriptive enough, which can lead to misunderstandings. For example, a variable named p does not convey meaningful information about its purpose, and method names like calculate() lack context.

Solution

Standard naming conventions should be followed to improve clarity. The variable p should be renamed to product, and price in ReceiptItem should be updated to unitPrice to explicitly indicate what it represents. Generic method names like calculate() should be changed to more descriptive ones such as calculateTotalPrice() to improve readability and maintainability.

5. Use of Magic Numbers

Class Concerned: Teller, Receipt

Identified Issues

The code contains hard-coded values (magic numbers) that lack context, making it less readable and harder to maintain. These values are used directly in conditions and calculations, making it difficult to modify logic without hunting for all occurrences.

Solution

Magic numbers should be replaced with named constants. Instead of using if (discountType == 1), a constant like PERCENTAGE_DISCOUNT = 1 should be defined. Similarly, VAT_RATE = 0.20 should replace 0.20 in calculations. Storing discount thresholds as static final variables in a dedicated DiscountRules class will improve maintainability and clarity.

6. Immutable Data Structures

Class Concerned: Product, ReceiptItem

Identified Issues

Some classes have fields that can be modified after initialization, which may lead to unintended side effects and make debugging difficult. Mutable data structures increase the risk of accidental modification, especially in multi-threaded environments.

Solution

Fields should be made immutable by declaring them as final and initializing them via constructors. Setter methods should be removed where unnecessary, ensuring that object states remain unchanged after creation. For collections, unmodifiable views such as Collections.unmodifiableList() should be used to prevent unintended modifications.

7. Encapsulation of Collections

Class Concerned: Receipt, ShoppingCart

Identified Issues

Methods that return collections currently provide direct access to internal data structures, potentially allowing external modification and breaking encapsulation.

Solution

To improve encapsulation, getter methods returning collections should return unmodifiable views using Collections.unmodifiableList(items). Defensive copying should be used where necessary to return a new instance of a list instead of exposing internal data directly. This ensures that the internal structure of classes remains protected from unintended modifications.

8. Enum Naming Conventions

Class Concerned: ProductUnit

Identified Issues

Enumeration values do not follow standard naming conventions, which can lead to inconsistencies and confusion when reading the code.

Solution

The ProductUnit enum should be updated to follow standard uppercase naming conventions. Kilo should be renamed to KILO and Each to EACH. Ensuring that all enums follow this convention improves consistency across the codebase and aligns with common Java practices.

