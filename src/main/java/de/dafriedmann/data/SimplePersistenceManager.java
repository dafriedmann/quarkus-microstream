package de.dafriedmann.data;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import io.quarkus.arc.Lock;
import one.microstream.storage.embedded.types.EmbeddedStorage;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import java.nio.file.FileSystem;
import java.nio.file.Paths;

@Lock
@ApplicationScoped
public class SimplePersistenceManager {

    private static final String LOCAL_FILE_SYSTEM = "fs";
    private static final String INMEMORY_FILE_SYSTEM = "mfs";

    @ConfigProperty(name = "microstream.storage.dir")
    String storageDir;

    @ConfigProperty(name = "microstream.storage.type")
    String storageType;

    private EmbeddedStorageManager storageManager;

    private SimplePersistenceManager() {
    }

    @PostConstruct
    void init() {
        // Idea for in memory file system taken from https://github.com/belu/microquark
        if (this.storageType.equals(INMEMORY_FILE_SYSTEM)) {
            // use in memory file system
            FileSystem fs = Jimfs.newFileSystem(Configuration.unix());
            this.storageManager = EmbeddedStorage.start(new DataRoot(), fs.getPath(storageDir));
        } else if (this.storageType.equals(LOCAL_FILE_SYSTEM)) {
            // Use on disk file system
            this.storageManager = EmbeddedStorage.start(new DataRoot(), Paths.get(storageDir));
        }
    }

    @PreDestroy
    void shutdown() {
        storageManager.shutdown();
    }

    public void store(Object instance) {
        storageManager.store(instance);
    }

    public void storeRoot() {
        storageManager.storeRoot();
    }

    public DataRoot getRoot() {
        return (DataRoot) storageManager.root();
    }

    public void setRoot(DataRoot root) {
        storageManager.setRoot(root);
    }

}
