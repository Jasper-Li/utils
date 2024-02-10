import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

public class CollectionsTest {
    Collection<String> strings = new ArrayList<>();
    @BeforeEach
    void setUp(){
        strings.add("one");
        strings.add("two");
    }
    @Test
    void handleIndividualElements() {
        out.println(STR."strings=\{strings}");
        strings.remove("one");
        out.println(STR."strings=\{strings}");
    }
    @Test
    void handleIndividualElementsContains() {
        assertTrue(strings.contains("one"));
        assertTrue(strings.contains("two"));
        assertFalse(strings.contains("three"));
        class User{};
        var user = new User();
        assertFalse(strings.contains(user));
    }
    @Test
    void observe() {
        assertEquals(2, strings.size());
        assertFalse(strings.isEmpty());
    }

    @Test
    void toArray() {
        var array = strings.toArray(new String[]{});
        for (var element : array){
            out.println(STR."element: \{element}");
        }
    }
}
