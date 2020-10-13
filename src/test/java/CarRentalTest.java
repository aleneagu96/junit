import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarRentalTest {
    @Mock
    private Agency agencyMock;

    @Test
    void verifyExample1() {
        agencyMock.findCar(5, "sedan");
        agencyMock.findCar(5, "sedan");
        verify(agencyMock, atLeastOnce()).findCar(5, "sedan");
        verify(agencyMock, atLeast(2)).findCar(5, "sedan");
        verify(agencyMock, atMost(10)).findCar(5, "sedan");
    }

    @Test
    void whenExample1() {
        // given
        Car bamveu = new Car("bmw", "x5", 2.0, 5, "suv", 2.000);
        when(agencyMock.findCar(5,"suv")).thenReturn(Arrays.asList(bamveu));

       // when
        List<Car> cars = agencyMock.findCar(5, "suv");


        // then
        assertNotNull(cars);
        assertFalse(cars.isEmpty());
        assertEquals(5,cars.get(0).getAmountSeats());
        assertEquals("x5",cars.get(0).getModel());
        assertEquals("bmw",cars.get(0).getBrand());
        assertEquals(2.000,cars.get(0).getCostPerDay());
        assertEquals(2.0,cars.get(0).getEngineCapacity());
        assertEquals("suv",cars.get(0).getType());

    }

}