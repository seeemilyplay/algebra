package seeemilyplay.core.international.text;

import java.util.HashMap;
import java.util.Map;

import seeemilyplay.core.international.LocalFactory;

/**
 * A singleton that stores all of the phrases used throughout
 * a program.
 */
public final class PhraseBook {

	private static final PhraseBook instance = new PhraseBook();

	private final Map<Class<?>,Section<?>> sections =
		new HashMap<Class<?>,Section<?>>();

	private PhraseBook() {
		super();
	}

	public static <P> void registerPhrases(
			Class<P> definition,
			LocalFactory<P> factory) {
		instance.savePhraseInformation(definition, factory);
	}

	private <P> void savePhraseInformation(
			Class<P> definition,
			LocalFactory<P> factory) {
		Section<P> section = createSection(factory);
		saveSection(definition, section);
	}

	private <P> Section<P> createSection(LocalFactory<P> factory) {
		return new Section<P>(factory);
	}

	private <P> void saveSection(Class<P> definition, Section<P> section) {
		sections.put(definition, section);
	}

	public static <P> P getPhrases(Class<P> definition) {
		return instance.loadPhrases(definition);
	}

	public <P> P loadPhrases(Class<P> definition) {
		if (!containsSection(definition)) {
			return null;
		}
		Section<P> section = loadSection(definition);
		return section.getPhrases();
	}

	private <P> boolean containsSection(Class<P> definition) {
		return sections.containsKey(definition);
	}

	@SuppressWarnings("unchecked")
	private <P> Section<P> loadSection(Class<P> definition) {
		return (Section<P>)sections.get(definition);
	}

	public static <P> void unregisterPhrases(Class<P> definition) {
		instance.deletePhraseInformation(definition);
	}

	private <P> void deletePhraseInformation(Class<P> definition) {
		sections.remove(definition);
	}
}
