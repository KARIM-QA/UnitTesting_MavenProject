package ClassesOrderDemoTests;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MethodOrderByIndexTest {
    @Order(1)
    @Test
    void testD(){
        System.out.println("test D") ;
    }
    @Order(2)
    @Test
    void testC(){
        System.out.println("test C") ;
    }
    @Order(3)
    @Test
    void testB(){
        System.out.println("test B") ;
    }
    @Order(4)
    @Test
    void testA(){
        System.out.println("test A") ;
    }
}
