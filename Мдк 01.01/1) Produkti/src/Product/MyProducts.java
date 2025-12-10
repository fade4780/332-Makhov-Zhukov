package products;

import java.util.ArrayList;
import java.util.List;

public class MyProducts {
    private double maxProteins;
    private double maxFats;
    private double maxCarbs;
    private double maxCalories;

    private List<Product> productList;

    public MyProducts(double maxProteins, double maxFats, double maxCarbs, double maxCalories) {
        if (maxProteins < 0 || maxFats < 0 || maxCarbs < 0 || maxCalories < 0) {
            throw new IllegalArgumentException("Максимальные значения не могут быть отрицательными!");
        }
        this.maxProteins = maxProteins;
        this.maxFats = maxFats;
        this.maxCarbs = maxCarbs;
        this.maxCalories = maxCalories;
        this.productList = new ArrayList<>();
    }

    public void addProduct(Product product) {
        if (product.getProteins() > maxProteins) {
            System.out.println("❌ Превышено количество белков у продукта: " + product.getName());
            return;
        }
        if (product.getFats() > maxFats) {
            System.out.println("❌ Превышено количество жиров у продукта: " + product.getName());
            return;
        }
        if (product.getCarbs() > maxCarbs) {
            System.out.println("❌ Превышено количество углеводов у продукта: " + product.getName());
            return;
        }
        if (product.getCalories() > maxCalories) {
            System.out.println("❌ Слишком калорийный продукт: " + product.getName());
            return;
        }
        productList.add(product);
        System.out.println("✅ Продукт добавлен: " + product.getName());
    }

    public void printProducts() {
        System.out.println("📋 Мои продукты:");
        if (productList.isEmpty()) {
            System.out.println("   (Список пуст)");
        } else {
            for (Product p : productList) {
                System.out.println("   - " + p.getName());
            }
        }
    }
}
