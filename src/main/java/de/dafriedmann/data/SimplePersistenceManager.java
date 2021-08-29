package de.dafriedmann.data;

import java.nio.file.Paths;

import javax.inject.Singleton;

import io.quarkus.arc.Lock;
import one.microstream.storage.embedded.types.EmbeddedStorage;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;

@Lock
@Singleton
public class SimplePersistenceManager {

	private final DataRoot root = new DataRoot();
	private final EmbeddedStorageManager storageManager = EmbeddedStorage.start(
			root, // root object
			Paths.get("data") // storage directory
	);

	private SimplePersistenceManager() {
	}
	
	public EmbeddedStorageManager getStorageManager() {
		return this.storageManager;
	}
	
	public void store(Object instance) {
		this.storageManager.store(instance);
	}
	
	public void storeRoot() {
		this.storageManager.storeRoot();
	}
	
	public DataRoot getRoot() {
		return (DataRoot) this.storageManager.root();
	}

}
