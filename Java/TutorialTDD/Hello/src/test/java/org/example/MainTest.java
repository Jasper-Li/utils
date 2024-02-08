package org.example;

import org.junit.jupiter.api.Test;
import static java.lang.System.out;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void initial () {
        final int a;
        final int b;
        boolean status = true;
        if (status) {
            a = 1;
            b = 1;
        } else {
            a = 1;
            b = 1;
        }
        out.println(STR."\{a} \{b}");
    }
    @Test
    void inherit() {
        var animal = new Animal();
        var sheep = new Sheep();
        var dog = new Dog();
        out.println("Origin");
        out.println(animal.sound());
        out.println(sheep.sound());
        out.println(dog.sound());

        out.println("\nreference:");
        Animal animal2 = sheep;
        out.println(animal2.sound());
        animal2 = dog;
        out.println(animal2.sound());
    }

    @Test
    void useChar() {

    }

}
class Animal{
    String sound() {
        return "Animal Sound";
    }
}
class Sheep extends  Animal{
    String sound() {
        return "mee..";
    }
}
class Dog extends  Animal{
    String sound() {
        return "wow..";
    }
}
