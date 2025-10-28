package products;

public class Application {
    public static void main(String[] args) {
        Product apple = new Product("Яблоко", 0.3, 0.2, 14, 52);
        Product chicken = new Product("Курица", 27, 3.6, 0, 165);
        Product chocolate = new Product("Шоколад", 7.3, 30, 61, 546);
        Product milk = new Product("Молоко", 3.2, 3.6, 4.8, 64);
        Product bread = new Product("Хлеб", 8.0, 1.0, 49.0, 265);
        Product cucumber = new Product("Огурец", 0.8, 0.1, 3.0, 15);

        // Лимиты
        MyProducts myDiet = new MyProducts(30, 10, 60, 400);

        // Пробуем добавить продукты
        myDiet.addProduct(apple);
        myDiet.addProduct(chicken);
        myDiet.addProduct(chocolate);
        myDiet.addProduct(milk);
        myDiet.addProduct(bread);
        myDiet.addProduct(cucumber);

        System.out.println();
        myDiet.printProducts();
    }
}
