package kaptainwutax.seedutils.mc;

import java.util.*;

public class VersionMap<V> extends AbstractMap<MCVersion, V> {

	protected List<Entry<V>> entries = new ArrayList<>();

	@Override
	public Set<Map.Entry<MCVersion, V>> entrySet() {
		return new LinkedHashSet<>(this.entries);
	}

	public VersionMap<V> add(MCVersion key, V value) {
		this.put(key, value);
		return this;
	}

	@Override
	public V put(MCVersion key, V value) {
		for (Entry<V> e : this.entries) {
			if (e.getKey() == key) return e.setValue(value);
		}

		this.entries.add(new Entry<>(key, value));
		this.sort();
		return null;
	}

	public V getAsOf(MCVersion version) {
		V value = null;

		for (Entry<V> e : this.entries) {
			if (e.getKey().isNewerThan(version)) break;
			value = e.getValue();
		}

		return value;
	}

	public V getLatest() {
		return this.entries.get(this.entries.size() - 1).getValue();
	}

	public MCVersion getLatestVersion() {
		return this.entries.get(this.entries.size() - 1).getKey();
	}

	public V getOldest() {
		return this.entries.get(0).getValue();
	}

	public MCVersion getOldestVersion() {
		return this.entries.get(0).getKey();
	}

	protected void sort() {
		this.entries.sort((o1, o2) -> o1.getKey().isOlderThan(o2.getKey()) ? -1 : 1);
	}

	public static class Entry<V> implements Map.Entry<MCVersion, V> {
		private final MCVersion key;
		private V value;

		public Entry(MCVersion key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public MCVersion getKey() {
			return this.key;
		}

		@Override
		public V getValue() {
			return this.value;
		}

		@Override
		public V setValue(V value) {
			V old = this.getValue();
			this.value = value;
			return old;
		}

		@Override
		public boolean equals(Object o) {
			return false;
		}

		@Override
		public int hashCode() {
			return this.key.hashCode() * 31 + this.value.hashCode();
		}
	}

}
