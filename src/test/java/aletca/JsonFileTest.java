package aletca;

import aletca.domain.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonFileTest {
    @Test
    void jsonTest() {
        String pathFileJson = "src/test/resources/user.json";

        ObjectMapper mapper = new ObjectMapper();
        try {
            File file = new File(pathFileJson);
            assertTrue(file.exists());
            Student student = mapper.readValue(file,Student.class);
            assertThat(student.name).isEqualTo("Elena");
            assertThat(student.nick).isEqualTo("Aletca");
            assertThat(student.address.street).isEqualTo("Lenina");
            assertThat(student.address.house).isEqualTo(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
