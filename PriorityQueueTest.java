import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.PriorityQueue;
import java.util.stream.Stream;
import java.util.ConcurrentModificationException;

public class PriorityQueueTest {
    private final PriorityQueue<Integer> priorityQueue = new PriorityQueue();

    @BeforeEach
    void SetUp() {
        priorityQueue.clear();
    }

    @Test
    public void Constructor_IllegalInitialCapacity_ThrowsIllegalArgumentException() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            PriorityQueue<Integer> Q = new PriorityQueue(-1);
        });
    }

    @Test
    public void Add_Null_ThrowsNullPointerException() {
        Exception exception = Assertions.assertThrows(NullPointerException.class, () -> {
            priorityQueue.add(null);
        });
    }

    @Test
    public void ForEach_Poll_ThrowsConcurrentModificationException() {
        Exception exception = Assertions.assertThrows(ConcurrentModificationException.class, () -> {
            priorityQueue.add(2);
            priorityQueue.add(3);
            priorityQueue.add(1);
            for (int element:priorityQueue) {
                priorityQueue.poll();
            }
        });
    }

    @ParameterizedTest
    @MethodSource("GenerateData")
    void Poll_RandomArray_CorrectArray(int[] testData, int[] expected) {
        for (int element: testData) {
            priorityQueue.add(element);
        }
        for (int element: expected) {
            Assertions.assertEquals((Object)element, (Object)priorityQueue.poll());
        }
    }

    static Stream<Arguments> GenerateData() {
        return Stream.of(
                Arguments.of((Object) new int[]{0, 0, 0},
                             (Object) new int[]{0, 0, 0}),

                Arguments.of((Object) new int[]{1, 2, 3},
                             (Object) new int[]{1, 2, 3}),

                Arguments.of((Object) new int[]{3, 2, 1},
                             (Object) new int[]{1, 2, 3}),

                Arguments.of((Object) new int[]{10, -3, 6, 0, -1},
                             (Object) new int[]{-3, -1, 0, 6, 10}),

                Arguments.of((Object) new int[]{13, 18, -92, 49, 3},
                             (Object) new int[]{-92, 3, 13, 18, 49})
        );
    }
}
