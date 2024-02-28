
// AmazonAPP

// This Java application simulates a simplified version of an Amazon-like e-commerce platform. 
// It allows users to input product details, categorize products, and perform searches based on 
// category names and product descriptions.

import java.util.*;

public class AmazonAPP {

    static class Product {
        private String id;
        private String name;
        private String description;
        private String details;
        private String category;
        private double price;

        // Constructor, getters, setters
        public Product(String id, String name, String description, String details, String category, double price){
            this.id = id;
            this.name = name;
            this.description = description;
            this.details = details;
            this.category = category;
            this.price = price;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getCategory() {
            return category;
        }

        public double getPrice() {
            return price;
        }
    }

    static class Category {
        private String name;
        private List<Product> products;

        // Constructor, getters, setters
        public Category(String name) {
            this.name = name;
            products = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public List<Product> getProducts() {
            return products;
        }
    }

    private Map<String, List<Product>> productsByCategory;
    private Map<String, Product> productsById;
    private Map<String, List<Product>> productsByName;
    private Map<String, List<Product>> productsByDescription;

    public Main() {
        productsByCategory = new HashMap<>();
        productsById = new HashMap<>();
        productsByName = new HashMap<>();
        productsByDescription = new HashMap<>();
        // Initialize other maps if needed
    }

    public void addProduct(Product product) {
        // Add product to productsByCategory
        productsByCategory.computeIfAbsent(product.getCategory().toLowerCase(), k -> new ArrayList<>()).add(product);
        // Add product to productsById
        productsById.put(product.getId(), product);
        // Add product to productsByName
        productsByName.computeIfAbsent(product.getName().toLowerCase(), k -> new ArrayList<>()).add(product);
        // Add product to productsByDescription
        productsByDescription.computeIfAbsent(product.getDescription().toLowerCase(), k -> new ArrayList<>()).add(product);
        // Add to other maps as needed
    }

    // Implement methods for removing, updating, and retrieving products
    public List<Product> searchByName(String query) {
        return productsByName.getOrDefault(query.toLowerCase(), Collections.emptyList());
    }

    public List<Product> searchByDescription(String query) {
        return productsByDescription.getOrDefault(query.toLowerCase(), Collections.emptyList());
    }

    public List<Product> searchByCategories (String query){
        return productsByCategory.getOrDefault(query.toLowerCase(), Collections.emptyList());
    }


    // Implement similar methods for other search criteria
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Main config = new Main();

        // Input categories
        System.out.println("Enter category names (press Enter after each category, type 'done' to finish):");
        String categoryName;
        while (!(categoryName = scanner.nextLine()).equalsIgnoreCase("done")) {
            // config.addCategory(categoryName);
            Category x = new Category(categoryName);
        }

        // Input products
        System.out.println("Enter product details (press Enter after each product, type 'done' to finish):");
        String id, name, description, details, category;
        double price;
        while (true) {
            System.out.println("Product ID:");
            id = scanner.nextLine();
            if (id.equalsIgnoreCase("done")) break;
            System.out.println("Product Name:");
            name = scanner.nextLine();
            System.out.println("Product Description:");
            description = scanner.nextLine();
            System.out.println("Product Details:");
            details = scanner.nextLine();
            System.out.println("Product Category:");
            category = scanner.nextLine();
            System.out.println("Product Price:");
            price = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            Product pd = new Product(id, name, description, details, category, price);
            config.addProduct(pd);
        }

        // Example searches
        System.out.println("Enter category to search:");
        String searchCategory = scanner.nextLine();
        List<Product> resultsByCategory = config.searchByCategories(searchCategory);

        System.out.println("Enter description to search:");
        String searchDescription = scanner.nextLine();
        List<Product> resultsByDescription = config.searchByDescription(searchDescription);

        // Display results
        displayResults("Search by Category: " + searchCategory, resultsByCategory);
        displayResults("Search by Description: " + searchDescription, resultsByDescription);
        // we can add more lines are print all the things related to the search keyword making it work like an amazon search
    }

    private static void displayResults(String query, List<Product> results) {
        System.out.println(query);
        if (results.isEmpty()) {
            System.out.println("No results found.");
        } else {
            for (Product product : results) {
                System.out.println(product.getName() + " - $" + product.getPrice());
            }
        }
        System.out.println();
    }
}
