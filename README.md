# Planit Technical Assessment – Automation

## Overview
DO NOT implement a BDD/Gherkin solution, as we will like to assess your experience with
framework implementation below the BDD layer.
Application URL: http://jupiter.cloud.planittesting.com

### Test cases
Test case 1:
1. From the home page go to contact page
2. Click submit button
3. Validate errors
4. Populate mandatory fields
5. Validate errors are gone

Test case 2:
1. From the home page go to contact page
2. Populate mandatory fields
3. Click submit button
4. Validate successful submission message
   Note: Run this test 5 times to ensure 100% pass rate

Test case 3:
1. From the home page go to shop page
2. Click buy button 2 times on “Funny Cow”
3. Click buy button 1 time on “Fluffy Bunny”
4. Click the cart menu

5. Verify the products are in the cart

Test case 4: Advanced

1. Buy 2Stuffed Frog,5Fluffy Bunny, 3Valentine Bear
2. Go to the cart page
3. Verify the price for each product
4. Verify that each product’s sub total = product price * quantity
5. Verify that total = sum(sub totals)

