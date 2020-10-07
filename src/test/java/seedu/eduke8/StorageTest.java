package seedu.eduke8;

import org.junit.jupiter.api.Test;
import seedu.eduke8.storage.Storage;
import seedu.eduke8.topic.TopicInterface;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNull;

class StorageTest {
    @Test
    public void emptyTest() {
        Storage storage = new Storage("data/test/empty.json");
        ArrayList<TopicInterface> topics = storage.load();
        assertNull(topics);
    }

    @Test
    public void loadTest() {
        Storage storage = new Storage("data/test/example.json");
        ArrayList<TopicInterface> topics = storage.load();

        TopicInterface firstTopic = topics.get(0);
        assertNull(firstTopic);
        // TODO check content in firstTopic when classes are made
    }
}