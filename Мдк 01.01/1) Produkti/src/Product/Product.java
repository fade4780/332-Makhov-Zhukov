package products;

public class Product {
    private String name;
    private double proteins;
    private double fats;
    private double carbs;
    private double calories;

    public Product() {
        this("Неизвестный", 0, 0, 0, 0);
    }

    public Product(String name) {
        this(name, 0, 0, 0, 0);
    }

    public Product(String name, double calories) {
        this(name, 0, 0, 0, calories);
    }

    public Product(String name, double proteins, double fats, double carbs, double calories) {
        setName(name);
        setProteins(proteins);
        setFats(fats);
        setCarbs(carbs);
        setCalories(calories);
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Название продукта не может быть пустым!");
        }
        this.name = name;
    }

    public void setProteins(double proteins) {
        if (proteins < 0) throw new IllegalArgumentException("Белки не могут быть отрицательными!");
        this.proteins = proteins;
    }

    public void setFats(double fats) {
        if (fats < 0) throw new IllegalArgumentException("Жиры не могут быть отрицательными!");
        this.fats = fats;
    }

    public void setCarbs(double carbs) {
        if (carbs < 0) throw new IllegalArgumentException("Углеводы не могут быть отрицательными!");
        this.carbs = carbs;
    }

    public void setCalories(double calories) {
        if (calories < 0) throw new IllegalArgumentException("Калории не могут быть отрицательными!");
        this.calories = calories;
    }

    public String getName() { return name; }
    public double getProteins() { return proteins; }
    public double getFats() { return fats; }
    public double getCarbs() { return carbs; }
    public double getCalories() { return calories; }

    @Override
    public String toString() {
        return String.format("%s [Б: %.1f, Ж: %.1f, У: %.1f, Ккал: %.1f]",
                name, proteins, fats, carbs, calories);
    }
}
